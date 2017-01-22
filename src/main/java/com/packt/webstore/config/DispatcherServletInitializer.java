package com.packt.webstore.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { RootApplicationContextConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebApplicationContextConfig.class };
	}

	/**
	 * Here we return only "/" which indicates that the DispatcherServlet is the
	 * default servlet of the application. More specific, all the http requestes
	 * that start with http://localhost:8080/webstore/ will be directed to this
	 * DispatcherServlet. Similiarly, if we change the return value to new
	 * String[] { "/app/*" }, then instead of http://localhost:8080/webstore/,
	 * all http requestes that start with http://localhost:8080/webstore/app/
	 * will be directed to this DispatcherServlet. For example, the URL
	 * http://localhost:8080/webstore/app/welcome will be directed to the
	 * welcome.jsp page.
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
}
