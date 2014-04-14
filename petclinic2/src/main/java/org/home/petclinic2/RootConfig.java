package org.home.petclinic2;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Initializes services (control) layer
 * 
 * @author Phillip
 * 
 */
@Configuration
// wiring in services as well as formatters, validators, etc... simply because I
// haven't found a better way of wiring formatters nor validators in
@ComponentScan({ "**.service", "**.validator", "**.formatter", "**.resolver",
		"**.interceptor" })
public class RootConfig {

}
