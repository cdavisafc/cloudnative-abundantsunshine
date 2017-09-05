package com.corneliadavis.cloudnative;

import com.corneliadavis.cloudnative.config.CloudnativeHelloworldApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CloudnativeHelloworldApplication.class);
	}

}
