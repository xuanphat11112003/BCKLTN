package com.dxp.HeThongChuoiCungUng.Config;

import com.dxp.HeThongChuoiCungUng.Utils.JwtTokenFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class JWTSecurityConfig {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenFilter jwtAuthenticationFilter;
    @Autowired
    private UserDetailsService userDetailsService;




    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }



    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain jwtSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.securityMatcher("/api/**")
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/user/me").authenticated()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/products/").permitAll()
                        .requestMatchers("/api/products/**").permitAll()
                        .requestMatchers("/api/material/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/sales-history/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/orders/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/category/**").permitAll()
                        .requestMatchers("/api/updateStatus/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/calculateDistance/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/getRoute/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/getCoordinates/**").permitAll()
                        .requestMatchers("/api/getMaterialsBySupplier/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/**").hasAnyAuthority("ROLE_AGENCY", "ROLE_EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/api/**").hasAnyAuthority("ROLE_AGENCY", "ROLE_EMPLOYEE")
                        .requestMatchers(HttpMethod.PUT, "/api/**").hasAnyAuthority("ROLE_AGENCY", "ROLE_EMPLOYEE")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasAnyAuthority("ROLE_AGENCY", "ROLE_EMPLOYEE")

                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getLocalizedMessage()) // Xử lý lỗi không xác thực
                        )
                );

        return http.build();
    }

}
