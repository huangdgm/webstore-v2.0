package com.packt.webstore.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class PromoCodeInterceptor extends HandlerInterceptorAdapter {
	private String promoCode;
	private String errorRedirect;
	private String offerRedirect;

	/**
	 * The Bean of PromoCodeInterceptor class is initialized before the
	 * preHandle() method got called.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String givenPromoCode = request.getParameter("promo");

		/**
		 * request.getContextPath() will return
		 * "http://localhost:8080/webstore".
		 * 
		 * The sendRedirect() method can accept relative URLs;the servlet
		 * container must convert the relative URL to an absolute URL before
		 * sending the response to the client. If the location is relative
		 * without a leading '/' the container interprets it as relative to the
		 * current request URI. If the location is relative with a leading '/'
		 * the container interprets it as relative to the servlet container
		 * root.
		 * 
		 * In this example, errorRedirect, as the parameter, is a relative URL.
		 * The servlet container convert it to
		 * http://localhost:8080/webstore/market/products/invalidPromoCode
		 * 
		 * because of the fact:
		 * 
		 * http://localhost:8080/webstore/market/products/specialOffer?promo=offer
		 * 
		 * is the request URL. So, the front part
		 * 
		 * http://localhost:8080/webstore/market/products
		 * 
		 * should be the same.
		 * 
		 * For demonstration purpose, we use two different styles of using
		 * sendRedirect() method.
		 */
		if (promoCode.equals(givenPromoCode)) {
			response.sendRedirect(request.getContextPath() + "/" + offerRedirect);
		} else {
			response.sendRedirect(errorRedirect);
		}

		return false;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public void setErrorRedirect(String errorRedirect) {
		this.errorRedirect = errorRedirect;
	}

	public void setOfferRedirect(String offerRedirect) {
		this.offerRedirect = offerRedirect;
	}
}
