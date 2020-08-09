package spring.api.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import spring.api.rest.model.Users;
import spring.api.rest.repository.UserRepository;

@Service
public class ImplementationUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		/*Query the user in the database*/
		
		Users users = userRepository.findUserByLogin(username);
		
		if(users == null) {
			throw new UsernameNotFoundException("Usuário não foi encontrado.");		
		}
		
		return new User(users.getLogin(), users.getPasswordUser(), users.getAuthorities());
	}
	
	public void inserirAcessoPadrao(Long idUsuario) {
		String constraint = userRepository.consultarConstraintRole();
		
		if (constraint != null)
			jdbcTemplate.execute("alter table usuarios_role DROP CONSTRAINT " + constraint);
		
		userRepository.instAcessRolePadrao(idUsuario);
	}

}
