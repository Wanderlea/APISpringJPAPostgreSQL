package spring.api.rest.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import spring.api.rest.ApplicationContextLoad;
import spring.api.rest.model.Users;
import spring.api.rest.repository.UserRepository;

@Service
@Component
public class JWTTokenAutenticationService {

	/* Token expiration time in milliseconds two days */
	private static final long EXPIRATION_TIME = 172800000;
	/* unique password to compose authentication */
	private static final String SECRET = "SecretPassword";
	/* default token prefix */
	private static final String TOKEN_PREFIX = "Bearer";
	private static final String HEADER_STRING = "Authorization";

	/* Generating authentication token and adding to the header and response Http */
	public void addAuthentication(HttpServletResponse response, String username) throws IOException {

		/* Token assembly */
		String JWT = Jwts.builder()/* Calls the token generator */
				.setSubject(username)/* Add User */
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))/* Daly expiration time */
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();/* compression and password generation algorithm */

		/* Join the token with the prefix */
		String token = TOKEN_PREFIX + " " + JWT;

		/* Add to header http */
		response.addHeader(HEADER_STRING, token); /* Autorization: Beare token */
		
		//Releasing response to different ports using the API or Web clients
		liberarCORS(response);

		/* Writes token as a response in the http body */
		response.getWriter().write("{\"Authorization\": \"" + token + "\"}");

	}

	/* Returns the user validated with the token and if not valid, returns null */
	public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {

		/* Get the token sent in the http header */
		String token = request.getHeader(HEADER_STRING);
		
		try {
			if (token != null) {

				String finalToken = token.replace(TOKEN_PREFIX, "").trim();

				/* Validates the user's token in the request */
				String user = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(finalToken).getBody().getSubject();
				if (user != null) {
					Users users = ApplicationContextLoad.getApplicationContext().getBean(UserRepository.class)
							.findUserByLogin(user);
					/* Returns logged use */
					if (users != null) {

						// Compare token sent with database token
						if (finalToken.equalsIgnoreCase(users.getToken())) {

							return new UsernamePasswordAuthenticationToken(users.getLogin(), users.getPasswordUser(),
									users.getAuthorities());
						}
					}
				}
			}
		} catch (io.jsonwebtoken.ExpiredJwtException e) {
			try {
				response.getOutputStream().println("Your token is expired, please enter a new token.");
			} catch (IOException e1) {}
		}
		
		liberarCORS(response);
		return null; /* Not authorized */

	}

	// CORS policy
	private void liberarCORS(HttpServletResponse response) {
		if (response.getHeader("Access-Control-Allow-Origin") == null) {
			response.addHeader("Access-Control-Allow-Origin", "*");
		}

		if (response.getHeader("Access-Control-Allow-Headers") == null) {
			response.addHeader("Access-Control-Allow-Headers", "*");
		}

		if (response.getHeader("Access-Control-Request-Headers") == null) {
			response.addHeader("Access-Control-Request-Headers", "*");
		}

		if (response.getHeader("Access-Control-Allow-Methods") == null) {
			response.addHeader("Access-Control-Allow-Methods", "*");
		}
	}

}
