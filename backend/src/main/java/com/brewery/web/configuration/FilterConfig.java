package com.brewery.web.configuration;

import com.brewery.web.security.ServiceAuthenticationFilter;
import com.brewery.web.security.LoginRateLimitFilter;
import com.brewery.web.security.AuthSessionFilter;
import com.brewery.web.security.ServiceCredentialVerifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<LoginRateLimitFilter> loginRateLimitFilter() {
        FilterRegistrationBean<LoginRateLimitFilter> registration = new FilterRegistrationBean<>();

        registration.setFilter(new LoginRateLimitFilter());
        registration.addUrlPatterns("/account/login");
        registration.setOrder(0);

        return registration;
    }

    @Bean
    public FilterRegistrationBean<AuthHook> loggingFilter() {
        FilterRegistrationBean<AuthHook> registrationBean = new FilterRegistrationBean<AuthHook>();

        registrationBean.setFilter(new AuthHook());
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<ServiceAuthenticationFilter> serviceAuthFilter(ServiceCredentialVerifier verifier) {
        FilterRegistrationBean<ServiceAuthenticationFilter> registration = new FilterRegistrationBean<ServiceAuthenticationFilter>();

        registration.setFilter(new ServiceAuthenticationFilter(verifier));
        registration.addUrlPatterns("/auth/*");
        registration.setOrder(1);

        return registration;
    }

    @Bean
    public FilterRegistrationBean<AuthSessionFilter> authSessionFilter() {
        FilterRegistrationBean<AuthSessionFilter> registration = new FilterRegistrationBean<AuthSessionFilter>();

        registration.setFilter(new AuthSessionFilter());
        registration.addUrlPatterns("/auth/*");
        registration.setOrder(2);

        return registration;
    }
}
