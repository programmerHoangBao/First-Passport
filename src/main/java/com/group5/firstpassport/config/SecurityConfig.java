package com.group5.firstpassport.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.group5.firstpassport.filter.JwtAuthFilter;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthFilter jwtAuthFilter;
  private final UserDetailsService userDetailsService;


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            // Disable CSRF (not needed for stateless JWT)
            .csrf(csrf -> csrf.disable())

            // Configure endpoint authorization
            .authorizeHttpRequests(auth -> auth
                    // Public endpoints
                    .requestMatchers("/api/login").permitAll()
                    .requestMatchers("/api/register").permitAll()
                    .requestMatchers("/api/xt/view-all-registration").permitAll()
                    .requestMatchers("/api/xd/approval").permitAll()
                    .requestMatchers("/api/xt/view-detail-registration").permitAll()
                    .requestMatchers("/api/xt/view-detail-resident").permitAll()
                    .requestMatchers("/api/xd//all-approval-by-result").permitAll()
                    .requestMatchers("/api/xt/send-from").permitAll()
                    .requestMatchers("/api/xd/all-send-from-to-xd").permitAll()
                    .requestMatchers("/api/xd/view-detail-approval").permitAll()
                    .requestMatchers("/api/lt/create-passport").permitAll()
                    .requestMatchers("/api/xt/reject-form").permitAll()
                    .requestMatchers("/api/lt/all-request-store").permitAll()


                    // Role-based endpoints
                    .requestMatchers("/api/xt/**").hasRole("XT")
                    .requestMatchers("/api/xd/**").hasRole("XD")
                    .requestMatchers("/api/lt/**").hasRole("LT")
                    .requestMatchers("/api/gs/**").hasRole("GS")


                    // All other endpoints require authentication
                    .requestMatchers("/api/users/**").authenticated()
                    .anyRequest().authenticated()
            )

            // Stateless session (required for JWT)
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Set custom authentication provider
            .authenticationProvider(authenticationProvider())

            // Add JWT filter before Spring Security's default filter
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  /*
   * Password encoder bean (uses BCrypt hashing)
   * Critical for secure password storage
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /*
   * Authentication provider configuration
   * Links UserDetailsService and PasswordEncoder
   */
  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
  }

  /*
   * Authentication manager bean
   * Required for programmatic authentication (e.g., in /generateToken)
   */
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }
}
