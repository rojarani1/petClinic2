package org.home.petclinic2;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Initializes services (control) layer
 * 
 * @author Phillip
 * 
 */
@Configuration
// wiring in services as well as formatters and validators simply because I
// haven't found a better way of wiring formatters nor validators in
@ComponentScan({ "org.home.petclinic2.service",
		"org.home.petclinic2.validator", "org.home.petclinic2.formatter" })
public class RootConfig {

}
