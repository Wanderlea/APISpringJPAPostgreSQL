package spring.api.rest.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Telephone {
	
	@Id
	@GeneratedValue(generator = "public.telephone_id_seq1", strategy = GenerationType.AUTO)
	public Long id;
	private String numero;
	
	//avoid json recursion
	@JsonIgnore
	@org.hibernate.annotations.ForeignKey(name = "users_id")
	@ManyToOne(optional = false)
	private Users users;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	} 
	
}
