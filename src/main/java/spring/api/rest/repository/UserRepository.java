package spring.api.rest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import spring.api.rest.model.Users;

//annotation is used to indicate that the class provides the mechanism to operation on objects.
@Repository
public interface UserRepository extends CrudRepository<Users, Long>{

}
