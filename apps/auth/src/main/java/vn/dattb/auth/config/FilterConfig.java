package vn.dattb.auth.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<LoggingFilter> loggingFilter() {
        FilterRegistrationBean<LoggingFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new LoggingFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);

        //add order

        return registrationBean;
    }

//    @Bean
//    public FilterRegistrationBean<RedirectRequestHeaderFilter> redirectRequestHeaderFilter() {
//        FilterRegistrationBean<RedirectRequestHeaderFilter> registrationBean = new FilterRegistrationBean<>();
//
//        registrationBean.setFilter(new RedirectRequestHeaderFilter());
//        registrationBean.addUrlPatterns("/*");
//        registrationBean.setOrder(2);
//
//        //add order
//
//        return registrationBean;
//    }
}
