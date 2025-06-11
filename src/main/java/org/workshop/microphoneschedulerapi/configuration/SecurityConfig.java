package org.workshop.microphoneschedulerapi.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
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
                            "/api/v1/user/login",
                            "api/v1/actor/actorScenes").permitAll();
                })
                .authorizeHttpRequests(registry ->
                        registry.requestMatchers(
                                "/api/v1/admin/hello",
                                "api/v1/admin/createPlay",
                                "api/v1/admin/deletePlay",
                                "api/v1/admin/updatePlay",
                                "api/v1/admin/allScenesInPlay",
                                "api/v1/admin/createScene",
                                "api/v1/admin/editScene",
                                "api/v1/admin/deleteScene",
                                "api/v1/admin/addPersonageToScene",
                                "api/v1/admin/removePersonageFromScene",
                                "api/v1/admin/assignActorToPersonage",
                                "api/v1/admin/createPersonage",
                                "api/v1/admin/getAllPersonageInPlay",
                                "api/v1/admin/getAllPersonageInDb",
                                "api/v1/admin/getAllPersonageInScene",
                                "api/v1/admin/suggestMicrophoneSchedule",
                                "api/v1/admin/getusersandroles",
                                "api/v1/admin/setuserrole",
                                "api/v1/admin/deleteuser",
                                "api/v1/admin/editPersonage",
                                "api/v1/admin/deletePersonage",
                                "api/v1/aadmin/getActorsConnectedToUsers"
                        ).hasRole(ADMINISTRATOR.name()))

                .authorizeHttpRequests(registry ->
                        registry.requestMatchers(
                                "/api/v1/admin/listAllPlay"
                        ).hasAnyRole(ADMINISTRATOR.name(), DIRECTOR.name()))


                .authorizeHttpRequests(registry ->
                        registry.requestMatchers(
                                "/api/v1/director/getoverview"
                        ).hasRole(DIRECTOR.name()))



                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
