package lv.venta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lv.venta.repo.IMyAuthorityRepo;
import lv.venta.repo.IMyUserRepo;
import lv.venta.service.impl.config.MyUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final IMyUserRepo myUserRepo;
	private final IMyAuthorityRepo myAuthorityRepo;
	public SecurityConfig(IMyUserRepo myUserRepo,IMyAuthorityRepo myAuthorityRepo) {
			this.myUserRepo = myUserRepo;
			this.myAuthorityRepo = myAuthorityRepo;
			}
	
	@Bean
	public MyUserDetailsManager createManager() {
		return new MyUserDetailsManager(myUserRepo, myAuthorityRepo);
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
				
				.requestMatchers("/home").permitAll()
				//PROFESSOR PIEEJAMĀS DAĻAS
				.requestMatchers("/professor/**").hasAuthority("LECT")
				//ADMIN PIEEJAMĀS DAĻAS
				.requestMatchers("/admin/**").hasAuthority("ADMIN")
				//STUDENT PIEEJAMĀS DAĻAS
				.requestMatchers("/student/**").hasAuthority("STUDENT")
				//---------------------------------------------------------------
				);
		http.formLogin(form -> form
			    .permitAll()
			    .defaultSuccessUrl("/home", true)
			);
	//http.formLogin(auth -> auth.permitAll());
	return http.build();
	}
	
	
}
