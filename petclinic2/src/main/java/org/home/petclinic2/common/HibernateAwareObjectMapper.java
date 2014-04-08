package org.home.petclinic2.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

/**
 * JSON does not support Hibernate LazyLoading and it tries to serialize
 * collections that are not yet loaded. This ObjectMapper does
 * <p>
 * See: https://github.com/FasterXML/jackson-datatype-hibernate
 * 
 * @author phil
 * 
 */
@SuppressWarnings("serial")
public class HibernateAwareObjectMapper extends ObjectMapper {
	private static final Logger logger = LoggerFactory
			.getLogger(HibernateAwareObjectMapper.class);

	public HibernateAwareObjectMapper() {
		logger.info("Registering Hibernate 4 Object mapper to support JSON lazy loading");
		registerModule(new Hibernate4Module());
		logger.info("Registered Hibernate 4 Object mapper to support JSON lazy loading");
	}

}
