package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/*
 * Entity for the Table "user_role_project".
 */
@Entity
@Table(name = "USER_ROLE_PROJECT")
public class User_Role_Project {
	
	@Embeddable
	public static class Pk implements Serializable {
		@Column(nullable = true, updatable = true)
		private Integer	userId;
						
		@Column(nullable = true, updatable = true)
		private Integer	roleId;
						
		@Column(nullable = true, updatable = true)
		private Integer	projectId;
						
		public Pk() {
		}
		
		public Pk(Integer userId, Integer roleId, Integer projectId) {
			this.userId = userId;
			this.roleId = roleId;
			this.projectId = projectId;
		}
		
		public Integer getUserId() {
			return userId;
		}
		
		public void setUserId(Integer userId) {
			this.userId = userId;
		}
		
		public Integer getRoleId() {
			return roleId;
		}
		
		public void setRoleId(Integer roleId) {
			this.roleId = roleId;
		}
		
		public Integer getProjectId() {
			return projectId;
		}
		
		public void setProjectId(Integer projectId) {
			this.projectId = projectId;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((userId == null) ? 0 : userId.hashCode());
			result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
			result = prime * result + ((projectId == null) ? 0 : projectId.hashCode());
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
			Pk other = (Pk) obj;
			if (userId == null) {
				if (other.userId != null)
					return false;
			} else if (!userId.equals(other.userId))
				return false;
			if (roleId == null) {
				if (other.roleId != null)
					return false;
			} else if (!roleId.equals(other.roleId))
				return false;
			if (projectId == null) {
				if (other.projectId != null)
					return false;
			} else if (!projectId.equals(other.projectId))
				return false;
			return true;
		}
		
	}
	
	@EmbeddedId
	private Pk pk;
	
	public Pk getPk() {
		return pk;
	}
	
	public void setPk(Pk pk) {
		this.pk = pk;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pk == null) ? 0 : pk.hashCode());
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
		User_Role_Project other = (User_Role_Project) obj;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		return true;
	}
	
}
