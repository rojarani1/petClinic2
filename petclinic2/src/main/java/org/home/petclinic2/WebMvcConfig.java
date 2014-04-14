package org.home.petclinic2;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.home.petclinic2.common.HibernateAwareObjectMapper;
import org.home.petclinic2.controller.exception.resolver.EmailSupportSimpleMappingExceptionResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Initializes presentation (view, control) layer
 * 
 * @author Phillip
 * 
 */
@Configuration
@EnableWebMvc
@ComponentScan("org.home.petclinic2.controller")
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	private static final Logger logger = LoggerFactory
			.getLogger(WebMvcConfig.class);

	// it may not be obvious but to get a bean that's been loaded via the
	// @ComponentScan annotations you just @Inject or @Autowire them in. Just
	// like below but you don't have to ask for everything of a specific type, I
	// just like to make things automatic so I don't have to autowire them
	// individually

	// neat trick which grabs ALL Formatter objects in context and injects them
	// into this list.They are in the context because they are annotated
	// with @Component and they are within one of the @ComponentScan packages
	// Just showing that autowiring and injecting do the same thing
	@Autowired
	private List<Formatter<?>> formatters;

	// neat trick which grabs ALL HandlerInterceptors in context and injects
	// them into this list. They are in the context because they are annotated
	// with @Component and they are within one of the @ComponentScan packages
	@Inject
	private List<HandlerInterceptor> interceptors;

	@Autowired
	private EmailSupportSimpleMappingExceptionResolver exceptionResolver;

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}

	@Bean
	public ViewResolver viewResolver() {
		logger.info("Creating view resolver");
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		logger.info("Created view resolver: " + viewResolver);
		return viewResolver;
	}

	@Bean(name = "messageSource")
	public MessageSource configureMessageSource() {
		logger.info("Creating message source");
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("/WEB-INF/messages/messages");
		messageSource.setCacheSeconds(5);
		messageSource.setDefaultEncoding("UTF-8");
		logger.info("Created message source: " + messageSource);
		return messageSource;
	}

	@Bean
	public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
		logger.info("Creating simple mapping exception resolver (where to take users when an exception/warning occurs)");
		exceptionResolver.setDefaultErrorView("exception");
		exceptionResolver.setWarnLogCategory("error");

		// TODO: this should be done with a properties entry
		List<String> supportEmailAddresses = new ArrayList<String>();
		supportEmailAddresses.add("phillip.williams@kyfb.com");
		exceptionResolver.setSupportEmailAddresses(supportEmailAddresses);

		logger.info("Created email support simple mapping exception resolver (view to show users when an exception/warning occurs). Exception resolver: "
				+ exceptionResolver);
		return exceptionResolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		logger.info("Creating resource handler and adding resource locations");
		registry.addResourceHandler("/resources/").addResourceLocations(
				"/resources/**");
		logger.info("Created resource handler and adding resource locations. Resources: "
				+ registry);
	}

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		logger.info("Configure DefaultServletHandling");
		// if the spring dispatcher is mapped to / then forward non handled
		// requests
		// (e.g. static resource) to the container's "default servlet"
		configurer.enable();
		logger.info("Configured DefaultServletHandling");
	}

	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		logger.info("Configure Message converters");
		converters.add(getMappingJackson2HttpMessageConverter());
		logger.info("Configured Message converters");
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		logger.info("Adding formatters");
		for (Formatter<?> formatter : formatters) {
			logger.info("Adding formatter: " + formatter);
			registry.addFormatter(formatter);
		}

		// add in joda datetime formatter so that joda datetime objects are
		// formatted back and forth between objects and html forms
		DateFormatterRegistrar registrar = new DateFormatterRegistrar();
		registrar.setFormatter(new DateFormatter("MM/dd/yyyy"));
		registrar.registerFormatters(registry);
		logger.info("Added formatters");
	}

	/**
	 * Jackson doesn't play nicely with Lazy loaded collections. This jackson 2
	 * http message converter does
	 * <p>
	 * See: https://github.com/FasterXML/jackson-datatype-hibernate
	 * 
	 * @return
	 */
	@Bean
	public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		mappingJackson2HttpMessageConverter
				.setObjectMapper(new HibernateAwareObjectMapper());
		return mappingJackson2HttpMessageConverter;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		logger.info("Adding interceptors");
		for (HandlerInterceptor handlerInterceptor : interceptors) {
			logger.info("Adding interceptor: " + handlerInterceptor);
			registry.addInterceptor(handlerInterceptor);
		}

		logger.info("Added interceptors");
	}

}
