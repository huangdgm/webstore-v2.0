package com.packt.webstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class HomeController {
	@RequestMapping
	/**
	 * Default mapping method in the HomeController class, since there is no
	 * parameter for the @RequestMapping.
	 * 
	 * Usually, when we perform an HTTP request redirection, the data stored in
	 * the original request is lost, making it impossible for the next GET
	 * request to access it after redirection. Flash attributes can help in such
	 * cases. Flash attributes provide a way for us to store information that is
	 * intended to be used in another request. Flash attributes are saved
	 * temporarily in a session to be available for an immediate request after
	 * redirection.
	 */
	public String welcome(Model model, RedirectAttributes redirectAttributes) {
		model.addAttribute("greeting", "Welcome to Web Store!");
		model.addAttribute("tagline", "The one and only amazing web store");

		redirectAttributes.addFlashAttribute("greeting", "Welcome to Web Store!");
		redirectAttributes.addFlashAttribute("tagline", "The one and only amazing web store");

		return "redirect:/welcome/greeting";
	}

	@RequestMapping("/welcome/greeting")
	public String greeting() {
		return "welcome";
	}
}