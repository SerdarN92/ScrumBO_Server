package dto;

import java.io.Serializable;

public class UserDTO implements Serializable {
	
	private Integer	id;
	private String	prename;
	private String	lastname;
	private String	password;
	private String	email;
					
	public UserDTO() {
	}
	
	public UserDTO(Integer id, String prename, String lastname, String password, String email) {
		this.id = id;
		this.prename = prename;
		this.lastname = lastname;
		this.password = password;
		this.email = email;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
		
	}
	
	public String getPrename() {
		return prename;
	}
	
	public void setPrename(String prename) {
		this.prename = prename;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
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
		UserDTO other = (UserDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}