package com.examly.healthCareManagement.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class HealthAppInitilizer extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		Class configFiles[] = {HealthAppConfig.class};
		return configFiles;
	}

	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		String mappings[] = {"/"};
		return mappings;
	}

}
