package org.home.petclinic2;

import java.util.List;

import javax.inject.Inject;

import org.home.petclinic2.common.HibernateAwareObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
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

	@Inject
	private List<Formatter<?>> formatters;

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
		SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
		simpleMappingExceptionResolver.setDefaultErrorView("exception");
		simpleMappingExceptionResolver.setWarnLogCategory("warn");
		logger.info("Created simple mapping exception resolver (where to take users when an exception/warning occurs). Simple Mapping exception resolver: "
				+ simpleMappingExceptionResolver);
		return simpleMappingExceptionResolver;
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
			registry.addFormatter(formatter);
		}

		// add in joda datetime formatter so that joda datetime objects are
		// formatted back and forth between objects and html forms
		DateFormatterRegistrar registrar = new DateFormatterRegistrar();
		registrar.setFormatter(new DateFormatter("MM/dd/yyyy"));
		registrar.registerFormatters(registry);
		logger.info("Added formatters: " + registry);
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

}
