package vn.dattb.auth.config;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.management.HttpSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import vn.dattb.auth.service.AuthorizationService;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Supplier;

@KeycloakConfiguration
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(keycloakAuthenticationProvider());
    }

    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public KeycloakSpringBootConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http
                .authorizeRequests()
                .antMatchers("/auth/login/**","/test/public/**","/test/login/**")
                .permitAll()
                .antMatchers("/test/secure/**").hasRole("USER")
                .anyRequest().authenticated().accessDecisionManager(customAuthManager().check());
    }

    @Bean
    @Override
    protected HttpSessionManager httpSessionManager() {
        return new HttpSessionManager();
    }


    AuthorizationManager<RequestAuthorizationContext> customAuthManager() {
        return new AuthorizationManager<RequestAuthorizationContext>() {
            @Override
            public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
                // make authorization decision
            }
        };
    }
    private boolean hasAccess(HttpServletRequest request, Authentication authentication) {
        String username = ((KeycloakPrincipal<?>) authentication.getPrincipal()).getName();
        String resource = request.getRequestURI();
        String action = request.getMethod();
        Long organizationId = getOrganizationIdFromRequest(request);

        return authorizationService.hasAccess(username, resource, action, organizationId);
    }

    private Long getOrganizationIdFromRequest(HttpServletRequest request) {
        // Implement logic to extract organization ID from request, e.g., from a request parameter or header
        return Long.parseLong(request.getParameter("organizationId"));
    }

}