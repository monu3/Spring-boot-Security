package org.com.springsecuritydemo.config;


import org.com.springsecuritydemo.service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private UserDetailsService userDetailsService ;
    private JwtFilter jwtFilter;

    public SecurityConfig(MyUserDetailsService userDetailsService,JwtFilter jwtFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
    }


    //this is used to pass by the default login method or spring security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(customizer-> customizer.disable())
                .authorizeHttpRequests(request-> request
                        .requestMatchers("register","login")  //this two does not need any security layer
                        .permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(Customizer.withDefaults())        //this is for google,github and other login
                .httpBasic(Customizer.withDefaults())  //you can use other restapi instead of brower only
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }



    @Bean
    //used to work with the database
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }



    //this is to generate and verify the JWT token in the system
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return  config.getAuthenticationManager();  //this is the way to extract the object of authenticationManger because it is interface
    }



    //this are the hard coded user
//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        UserDetails user1 = User.withDefaultPasswordEncoder()
//                .username("monu")
//                .password("iloveu")
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user2 = User.withDefaultPasswordEncoder()
//                .username("sonu")
//                .password("hi")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1, user2);
//    }
}
