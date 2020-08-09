package spring.api.rest.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import spring.api.rest.model.Users;

//annotation is used to indicate that the class provides the mechanism to operation on objects.
@Repository
public interface UserRepository extends CrudRepository<Users, Long>{
	
	@Query("select u from Users u where u.login = ?1")
	Users findUserByLogin(String login);
	
	@Query(value = "SELECT constraint_name from information_schema.constraint_column_usage where table_name = 'usuarios_role' and column_name = 'role_id' and constraint_name <> 'unique_role_user'", nativeQuery = true)
	String consultarConstraintRole();	
	
	@Transactional
	@Modifying
	@Query(value = "insert into user_role (user_id, role_id) values (?1, (select id from role where nome_role = 'ROLE_USER'));", nativeQuery = true)
	void instAcessRolePadrao(Long idUsuario);
	
}
