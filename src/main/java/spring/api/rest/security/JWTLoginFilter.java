package spring.api.rest.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import spring.api.rest.model.Users;

/*Establish our token manager*/
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter{
	
	/*Configure the authentication manager*/
	protected JWTLoginFilter(String url, AuthenticationManager authenticationManager) {
		
		/*Requires URL authentication*/
		super(new AntPathRequestMatcher(url));
		
		/*Authentication generator*/
		setAuthenticationManager(authenticationManager);
	}
	
	/*Returns the user when processing authentication*/
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		/*Get the token to validate*/
		Users users = new ObjectMapper().readValue(request.getInputStream(), Users.class);
		
		/*return user*/
		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
				users.getLogin(), users.getPasswordUser()));
		
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		new JWTTokenAutenticationService().addAuthentication(response, authResult.getName());
	}

}
