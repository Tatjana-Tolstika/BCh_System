package lv.venta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lv.venta.service.impl.config.MyUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public MyUserDetailsManager createManager() {
		MyUserDetailsManager manager = new MyUserDetailsManager();
		return manager;
	}
	
	@Bean
	public DaoAuthenticationProvider createProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		provider.setPasswordEncoder(encoder);
		provider.setUserDetailsService(createManager());
		return provider;
		
	}
	
	
	@Bean
	public SecurityFilterChain createConfigForEndpoints (HttpSecurity http) throws Exception{
		http.authorizeHttpRequests(auth -> auth

				//---------------------------------------------------------------
				//VISIEM PIEEJAMĀS DAĻAS
				

				//PROFESSOR PIEEJAMĀS DAĻAS
				.requestMatchers("/professor/courses","/professor/courses/**/tests").hasAnyAuthority("LECT")
				//ADMIN PIEEJAMĀS DAĻAS
				.requestMatchers("/courses/CRUD/add", "/courses/CRUD/update/**", "/courses/CRUD/delete/**", "/courses/CRUD/all", "/courseTests/crud/add", "/courseTests/crud/delete/**",
						"/courseTests/crud/update/**", "/courseTests/crud/all", "/lecturers/crud/add", "/lecturers/crud/delete/**", "/lecturers/crud/update/**", "/lecturers/crud/all",
						"/programs/crud/all", "/programs/crud/add", "/programs/crud/update/**", "/programs/crud/delete/**", "/spc/crud/add", "/spc/crud/delete/**",
						"/spc/crud/update/**", "/spc/crud/all", "/studentProgram/crud/add", "/studentProgram/crud/delete/**/**", "/studentProgram/crud/update/**", "/studentProgram/crud/all",
						"/studentProgram/crud/all/**", "/students/crud/all","/students/crud/add", "/students/crud/update/**", "/students/crud/delete/**", "/testResult/crud/add/**", 
						"/testResult/crud/delete/**", "/testResult/crud/update/**", "/testResult/crud/all", "/testTask/crud/add", "/testTask/crud/delete/**/**", "/testTask/crud/update/**/**",
						"/testTask/crud/all/**").hasAnyAuthority("ADMIN")
				//---------------------------------------------------------------
				);
	http.formLogin(auth -> auth.permitAll());
	return http.build();
	}
	
	
}
