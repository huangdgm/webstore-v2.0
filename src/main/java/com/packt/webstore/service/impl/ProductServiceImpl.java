package com.packt.webstore.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.ProductRepository;
import com.packt.webstore.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;

	@Override
	public void updateAllStock() {
		List<Product> allProducts = productRepository.getAllProducts();

		for (Product product : allProducts) {
			if (product.getUnitsInStock() < 500) {
				productRepository.updateStock(product.getProductId(), product.getUnitsInStock() + 1000);
			}
		}
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.getAllProducts();
	}

	@Override
	public List<Product> getProductsByCategory(String category) {
		return productRepository.getProductsByCategory(category);
	}

	@Override
	public List<Product> getProductsByFilter(Map<String, List<String>> filterParams) {
		return productRepository.getProductsByFilter(filterParams);
	}

	@Override
	public Product getProductById(String productID) {
		return productRepository.getProductById(productID);
	}

	@Override
	public List<Product> getProductsByManufacturer(String brand) {
		return productRepository.getProductsByManufacturer(brand);
	}

	@Override
	public List<Product> getProductsByPriceRange(Map<String, List<String>> price) {
		return productRepository.getProductsByPriceRange(price);
	}

	@Override
	public void addProduct(Product product) {
		productRepository.addProduct(product);
	}
}
