package org.workshop.microphoneschedulerapi.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.workshop.microphoneschedulerapi.service.CustomUserDetailService;

import static org.workshop.microphoneschedulerapi.domain.model.UserRole.ADMINISTRATOR;
import static org.workshop.microphoneschedulerapi.domain.model.UserRole.DIRECTOR;
import static org.workshop.microphoneschedulerapi.domain.model.UserRole.ACTOR;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private CustomUserDetailService customUserDetailService;
    private AuthEntryPointJwt authEntryPointJwt;

    @Autowired
    public SecurityConfig(final CustomUserDetailService customUserDetailService, AuthEntryPointJwt authEntryPointJwt) {
        this.customUserDetailService = customUserDetailService;
        this.authEntryPointJwt = authEntryPointJwt;
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults()) // //This is needed for me to add in order to resolve Cors issue after adding Spring security also adding WebConfig
                .csrf(AbstractHttpConfigurer::disable) //Enabling this to get post request working, should maybe be otherwise later?
                /*.formLogin(httpSecurityFormLoginConfigurer -> {
                    httpSecurityFormLoginConfigurer.loginProcessingUrl("/api/v1/project/login").permitAll(); //Not using this, but maybe Ill try to add later
                })
                .logout(httpSecurityLogoutConfigurer -> {
                    httpSecurityLogoutConfigurer.logoutUrl("/api/v1/project/logout").permitAll(); //Not using this, but maybe Ill try to add later
                })

                 */
                .authorizeHttpRequests(registry ->{
                    registry.requestMatchers("/api/v1/scene/scenes",
                            "/api/v1/scene/completePlay",
                            "/api/v1/scene/microphonesInScene",
                            "/api/v1/microphone/createMicrophone",
                            "/api/v1/microphone/deleteMicrophone",
                            "/api/v1/microphone/updateMicrophone",
                            "/api/v1/user/register",
                            "/api/v1/user/login").permitAll();
                })
                .authorizeHttpRequests(registry ->
                        registry.requestMatchers(
                                "/api/v1/admin/hello",
                                "/api/v1/admin/listAllPlay",
                                "api/v1/admin/createPlay",
                                "api/v1/admin/deletePlay",
                                "api/v1/admin/updatePlay",
                                "api/v1/admin/allScenesInPlay"
                        ).hasRole(ADMINISTRATOR.name()))
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
