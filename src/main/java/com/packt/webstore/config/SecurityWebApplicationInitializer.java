package com.packt.webstore.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * By extending the AbstractSecurityWebApplicationInitializer class, we can
 * instruct Spring MVC to pick up our SecurityConfig class during bootup.
 */
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

}
