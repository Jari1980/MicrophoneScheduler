package org.workshop.microphoneschedulerapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults()) // //This is needed for me to add in order to resolve Cors issue after adding Spring security also adding WebConfig
                //.csrf(AbstractHttpConfigurer::disable)
                /*.formLogin(httpSecurityFormLoginConfigurer -> {
                    httpSecurityFormLoginConfigurer.loginProcessingUrl("/api/v1/project/login").permitAll(); //Not using this, but maybe Ill try to add later
                })
                .logout(httpSecurityLogoutConfigurer -> {
                    httpSecurityLogoutConfigurer.logoutUrl("/api/v1/project/logout").permitAll(); //Not using this, but maybe Ill try to add later
                })

                 */
                .authorizeHttpRequests(registry ->{
                    registry.requestMatchers("/api/v1/project/scenes",  //Not using this, but maybe Ill try to add later
                            "/api/v1/project/hello",
                            "/api/v1/project/completePlay",
                            "/api/v1/project/microphonesInScene").permitAll();
                })
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

}
