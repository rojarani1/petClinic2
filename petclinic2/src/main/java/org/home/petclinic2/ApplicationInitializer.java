package org.home.petclinic2;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Initializers the web application (alternate to web.xml)
 * <p>
 * When your web container starts this application it looks for a web.xml file
 * or it looks for a class which implements the ServletContainerInitializer (EE
 * 6) interface
 * 
 * @author phil
 * 
 */
public class ApplicationInitializer implements WebApplicationInitializer {
	private static final Logger logger = LoggerFactory
			.getLogger(ApplicationInitializer.class);

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		logger.info("########################################");
		logger.info("Starting application");
		logger.info("Initializing servlet context");
		AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
		webApplicationContext.register(DataConfig.class, RootConfig.class,
				WebMvcConfig.class);

		// register HiddenFieldFilter
		registerHiddenFieldFilter(servletContext);

		Dynamic dynamc = servletContext.addServlet("dispatcherServlet",
				new DispatcherServlet(webApplicationContext));
		dynamc.addMapping("/");
		dynamc.setLoadOnStartup(1);
		logger.info("Initialized servlet context");
	}

	/**
	 * Add Servlet Filter to correct HTTP method to PUT or DELETE
	 * <p>
	 * Browsers don't universally support HTTP methods such as PUT and DELETE
	 * and just replace them as POST. This servlet filter will switch to the
	 * correct method so long as spring places a hidden _method field on the
	 * form with the desired HTTP method (Spring 4.x.x does)
	 * 
	 * <pre>
	 * See: http://stackoverflow.com/questions/18056045/hiddenhttpmethodfilter-configuration-without-xml
	 * </pre>
	 * 
	 * @param servletContext
	 */
	private void registerHiddenFieldFilter(ServletContext servletContext) {
		servletContext.addFilter("hiddenHttpMethodFilter",
				new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null,
				true, "/*");
	}

}
