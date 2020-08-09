package spring.api.rest.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Users implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String login;
	private String passwordUser;
	private String name;
	
	@OneToMany(mappedBy = "users", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Telephone> telephones = new ArrayList<Telephone>();
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "user_role", 
			uniqueConstraints = @UniqueConstraint (
			columnNames = {"user_id","role_id"}, 
			name = "unique_role_user"),
			joinColumns = @JoinColumn(
					name = "user_id", 
					referencedColumnName = "id", 
					table = "user", 
					unique = false,
					foreignKey = @ForeignKey(
							name = "user_fk", 
							value = ConstraintMode.CONSTRAINT)),
			inverseJoinColumns = @JoinColumn(
					name = "role_id",
					referencedColumnName = "id",
					table = "role", 
					unique = false,
					updatable = false,
					foreignKey = @ForeignKey(
							name="role_fk",
							value = ConstraintMode.CONSTRAINT)))
	
	private List<Role> roles = new ArrayList<>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPasswordUser() {
		return passwordUser;
	}
	public void setPassword(String passwordUser) {
		this.passwordUser = passwordUser;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Telephone> getTelephones() {
		return telephones;
	}
	public void setTelephones(List<Telephone> telephones) {
		this.telephones = telephones;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Users other = (Users) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	/*User Acess ROLE_ */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}
	
	@Override
	public String getPassword() {
		return this.passwordUser;
	}
	
	@Override
	public String getUsername() {
		return this.login;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
}
