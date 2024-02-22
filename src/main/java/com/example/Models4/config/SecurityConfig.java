package com.example.Models4.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authRequest ->
                                               authRequest
//                                                       ROOM
                                                       .requestMatchers(
                                                               "/room/search").permitAll()
                                                       .requestMatchers(
                                                               "/room" +
                                                                       "/reservedDates/{id}" ).permitAll()
                                                       .requestMatchers(
                                                               "/room" +
                                                                       "/newReservedDates/{id}" ).permitAll()

//                                                       HOTEL
                                                       .requestMatchers(
                                                               "/hotel/get" ).permitAll()
//                                                       HOTEL BOOKING
                                                       .requestMatchers(
                                                               "/hotelBooking" +
                                                                       "/create/{roomId}" ).permitAll()

//                                                       FLIGHT
                                                       .requestMatchers(
                                                               "/flight" +
                                                                       "/onewayReturn" ).permitAll()
                                                       .requestMatchers(
                                                               "/flight" +
                                                                       "/oneway" ).permitAll()

//                                                       FLIGHT BOOKING
                                                       .requestMatchers(
                                                               "/flightBooking" +
                                                                       "/create" ).permitAll()

                                                       .anyRequest().authenticated()

                ).formLogin().permitAll()
                .and()
                .httpBasic()
                .and()
                .build();
    }
}
