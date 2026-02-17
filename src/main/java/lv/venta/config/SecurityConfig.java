package lv.venta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public UserDetailsManager createInMemoryUsers() {
	 PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	 
	 UserDetails ud1 = User.builder().username("admin").password(encoder.encode("12345")).authorities("ADMIN").build();
	 UserDetails ud2 = User.builder().username("TestUser").password(encoder.encode("testPassword")).authorities("LECT").build();
	 UserDetails ud3 = User.builder().username("KarinaSkirmante").password(encoder.encode("54321")).authorities("LECT").build();
	 UserDetails ud4 = User.builder().username("EstereVitola").password(encoder.encode("qwerty")).authorities("LECT").build();
	 UserDetails ud5 = User.builder().username("KristapsBlumbergs").password(encoder.encode("ytrewq")).authorities("LECT").build();
	 UserDetails ud6 = User.builder().username("student").password(encoder.encode("12345")).authorities("STUDENT").build();
	 
	 InMemoryUserDetailsManager imUserDetailsMan = new InMemoryUserDetailsManager(ud1, ud2, ud3, ud4, ud5, ud6);
	 
	 return imUserDetailsMan;
	}
	
	@Bean // Function automatically starts after system starts working
	public SecurityFilterChain createConfigForEndpoints(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests(
				auth -> auth
				.anyRequest().authenticated()

				);
		
		http.formLogin(auth->auth.permitAll());
		return http.build();
	}
	
	
}
