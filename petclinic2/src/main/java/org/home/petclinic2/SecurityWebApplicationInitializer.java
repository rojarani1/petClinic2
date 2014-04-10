package org.home.petclinic2;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Registers the security filter into the filter chain
 * <p>
 * Without this, the SecurityConfig is useless, this filter needs to be
 * registered so that spring security can do its work
 * 
 * <pre>
 * See: (https://github.com/rwinch/gs-spring-security-3.2/tree/master/src/main/java/sample/config)
 * </pre>
 * 
 * @author phil
 * 
 */
public class SecurityWebApplicationInitializer extends
		AbstractSecurityWebApplicationInitializer {

}
