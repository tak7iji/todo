package org.sample.test.app.welcome;

import javax.validation.Configuration;

import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class CustomValidatorFactoryBean extends LocalValidatorFactoryBean {

	@Override
	protected void postProcessConfiguration(Configuration<?> configuration) {
		configuration.ignoreXmlConfiguration();
	}

}
