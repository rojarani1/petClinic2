package org.home.petclinic2;

import javax.servlet.Filter;

import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Initializes the web application as a spring secured application
 * <p>
 * Typically we use WebApplicationInitializer for unsecured applications. This
 * implementation registers the web application with its container (websphere,
 * tomcat, etc...) and creates a dispatch servlet
 * 
 * @author phil
 * 
 */
public class SecuredApplicationInitializer extends
		AbstractAnnotationConfigDispatcherServletInitializer {

	/**
	 * Loads given @Configuration classes into the spring context
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { SecurityConfig.class, DataConfig.class,
				RootConfig.class };
	}

	/**
	 * Loads Web MVC config
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebMvcConfig.class };

	}

	/**
	 * Defines the servlet mappings for accessing the dispatcher servlet
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	/**
	 * Defines (one way of doing so) servlet filters for the dispatcher servlet
	 * <p>
	 * This particular allows for us to use PUT and DELETE HTTP functions,
	 * browsers do not consistently support these HTTP functions and typically
	 * override them with POST functions
	 */
	@Override
	protected Filter[] getServletFilters() {
		return new Filter[] { new HiddenHttpMethodFilter() };
	}

}
