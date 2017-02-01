package com.packt.webstore.controller;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.packt.webstore.domain.Product;
import com.packt.webstore.exception.NoProductsFoundUnderCategoryException;
import com.packt.webstore.exception.ProductNotFoundException;
import com.packt.webstore.service.ProductService;

@Controller
@RequestMapping("market") // relative request mapping
public class ProductController {
	@Autowired
	private ProductService productService;

	@RequestMapping("/products")
	public String list(Model model) {
		model.addAttribute("products", productService.getAllProducts());

		return "products";
	}

	@RequestMapping("/update/stock")
	public String updateStock(Model model) {
		productService.updateAllStock();

		return "redirect:/market/products";
	}

	@RequestMapping("/products/{category}")
	public String getProductsByCategory(Model model, @PathVariable("category") String productCategory) {
		List<Product> products = productService.getProductsByCategory(productCategory);

		if (products == null || products.isEmpty()) {
			throw new NoProductsFoundUnderCategoryException();
		}

		model.addAttribute("products", products);

		return "products";
	}

	/**
	 * The '{params}' here is not used for the @PathVariable, instead, it's used
	 * for the @MatrixVariable
	 * 
	 * A URL can have multiple matrix variables; each matrix variable will be
	 * separated with a ; (semicolon). To assign multiple values to a single
	 * variable, each value must be separated by a ¡°,¡± (comma).
	 *
	 * If we mix matrix variable and request parameter together, we can use the
	 * following pattern:
	 *
	 * http://localhost:8080/webstore/products/filter/category/tablet/price;low=100
	 * ;high=1000?manufacturer=google
	 * 
	 * Be careful:
	 * 
	 * 1. The key=value pairs are separated by semicolon. 2. The value(s) are
	 * evaluated as a collection of string. So we have to deal with the value
	 * specified in the URL as the collection, even there is only one value
	 * specified. In this example, low=100, the key is "low", the value is a
	 * collection, which only contains a single element - "100".
	 */
	@RequestMapping("/products/filter/{params}")
	public String getProductsByFilter(@MatrixVariable(pathVar = "params") Map<String, List<String>> filterParams,
			Model model) {
		model.addAttribute("products", productService.getProductsByFilter(filterParams));

		return "products";
	}

	@RequestMapping("/product")
	public String getProductById(@RequestParam("id") String productId, Model model) {
		model.addAttribute("product", productService.getProductById(productId));

		return "product";
	}

	/**
	 * Be careful of the URL that can match the filterProducts method. Below is
	 * a valid URL that can match this method:
	 * 
	 * http://localhost:8080/webstore/market/products/Laptop/price;low=200;high=400?brand=Dell
	 * 
	 * 1. The brand value 'Google' should be double quoted, like brand="Dell";
	 * 2. The variable value in the URL is case sensitive. The 'Laptop' should
	 * not be 'laptop', and 'Dell' should not be 'dell'. All the values in the
	 * URL should strictly match the value in the database;
	 */
	@RequestMapping("/products/{category}/{price}")
	public String filterProducts(@PathVariable String category, @RequestParam String brand,
			@MatrixVariable Map<String, List<String>> price, Model model) {

		Set<Product> products = new HashSet<Product>();

		products.addAll(productService.getProductsByCategory(category));
		products.retainAll(productService.getProductsByManufacturer(brand));
		products.retainAll(productService.getProductsByPriceRange(price));

		model.addAttribute("products", products);

		return "products";
	}

	/**
	 * We create a new empty Product object - newProduct, which serves as the
	 * form backing bean. Please be noticed that we need to bind this form
	 * backing bean with the form. The way that can implement the bind is to
	 * assign the same name to the modelAttribute in the form:form tag in the
	 * addProduct.jsp.
	 * 
	 * The overflow of the form binding:
	 * 
	 * 1. Upon the opening of the addProduct.jsp, a new Product object was
	 * created; 2. Bind this object with the form through the modelAttribute in
	 * the form:form tag; 3. Enter values in the addProduct.jsp page; 4. Click
	 * submit which can trigger the invocation of the processAddNewProductForm
	 * method. Meanwhile, the values that the user enters are populated into
	 * that empty Product object; 5. The back end database was updated and a new
	 * jsp page was finally popped up.
	 */
	@RequestMapping(value = "/products/addProduct", method = RequestMethod.GET)
	public String getAddNewProductForm(Model model) {
		Product newProduct = new Product();

		model.addAttribute("newProduct", newProduct);

		return "addProduct";
	}

	/**
	 * Here if you look at the value attribute of the @ModelAttribute
	 * annotation, you can observe a pattern. Yes, the @ModelAttribute
	 * annotation's value and the value of the modelAttribute from the
	 * <form:form> tag are the same. So Spring MVC knows that it should assign
	 * the form bounded newProduct object to the processAddNewProductForm
	 * method's newProduct parameter.
	 * 
	 * Spring MVC will fill this object, result, with the result of the binding.
	 */
	@RequestMapping(value = "/products/addProduct", method = RequestMethod.POST)
	public String processAddNewProductForm(@ModelAttribute("newProduct") Product newProduct, BindingResult result,
			HttpServletRequest request) {
		String[] suppressedFields = result.getSuppressedFields();

		if (suppressedFields.length > 0) {
			throw new RuntimeException("Attempting to bind disallowed fields: "
					+ StringUtils.arrayToCommaDelimitedString(suppressedFields));
		}

		MultipartFile productImage = newProduct.getProductImage();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		// String rootDirectory =
		// "F:\\p2\\workspace_j2ee_sts\\webstore\\src\\main\\webapp\\";

		if (productImage != null && !productImage.isEmpty()) {
			try {
				productImage.transferTo(
						new File(rootDirectory + "resources\\images\\" + newProduct.getProductId() + ".png"));
			} catch (Exception e) {
				throw new RuntimeException("Product Image saving failed", e);
			}
		}

		productService.addProduct(newProduct);

		return "redirect:/market/products";
	}

	/**
	 * To put restriction on binding HTTP parameters with the form baking bean.
	 */
	@InitBinder
	public void initialiseBinder(WebDataBinder binder) {
		// It is possible to use '*' pattern to match fields in a flexible way.
		binder.setAllowedFields("productId", "name", "unitPrice", "description", "manufacturer", "category",
				"unitsInStock", "condition", "productImage", "language");
	}

	@ExceptionHandler(ProductNotFoundException.class)
	public ModelAndView handleError(HttpServletRequest req, ProductNotFoundException exception) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("invalidProductId", exception.getProductId());
		mav.addObject("exception", exception);
		mav.addObject("url", req.getRequestURL() + "?" + req.getQueryString());

		mav.setViewName("productNotFound");

		return mav;
	}
}
