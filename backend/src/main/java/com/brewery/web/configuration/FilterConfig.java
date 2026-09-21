package com.brewery.web.configuration;

import com.brewery.web.security.ServiceAuthenticationFilter;
import com.brewery.web.security.ServiceCredentialVerifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<AuthHook> loggingFilter() {
        FilterRegistrationBean<AuthHook> registrationBean = new FilterRegistrationBean<AuthHook>();

        registrationBean.setFilter(new AuthHook());
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<ServiceAuthenticationFilter> serviceAuthFilter(
            ServiceCredentialVerifier verifier) {
        FilterRegistrationBean<ServiceAuthenticationFilter> registration = new FilterRegistrationBean<>();

        registration.setFilter(new ServiceAuthenticationFilter(verifier));
        registration.addUrlPatterns("/auth/*");
        registration.setOrder(1);
        return registration;
    }

}
