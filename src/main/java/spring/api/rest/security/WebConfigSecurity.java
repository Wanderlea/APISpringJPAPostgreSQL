package spring.api.rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import spring.api.rest.service.ImplementationUserDetailsService;

/*Performs the maps of, adresses authorizes or blocks access to URLs*/
@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private ImplementationUserDetailsService implementationUserDetailsService;
	
	/*Configures HTTP access requests*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*Enables protection against users who are not token-validated*/
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		
		/*Enables permission to access the system's home page*/
		.disable().authorizeRequests().antMatchers("/").permitAll()
		.antMatchers("/index").permitAll()
		
		/*Logout URL - redirects after user logs out of the system*/
		.anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")
		
		/*Maps logout URL and invalidates user*/
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		
		/*Filters login requests for authentication*/
		
		
		/*Filtra as requisições para verificar a presença do TOKEN JWT*/
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		/*service that will query user in the database*/
		auth.userDetailsService(implementationUserDetailsService)
		
		/*Password encoding standard*/
		.passwordEncoder(new BCryptPasswordEncoder());
	}

}
