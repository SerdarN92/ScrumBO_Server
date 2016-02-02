package rest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;

import com.google.gson.Gson;

import dto.UserDTO;
import model.Role;
import model.User;
import model.UserStoryTask;
import model.User_Role_Project;
import service.RoleService;
import service.UserService;
import service.UserStoryTaskService;
import service.User_Role_ProjectService;

@Path("/benutzer")
public class BenutzerREST {
	
	@GET
	@Path("/alle/{hibernateconfigfilename}")
	@Produces("application/json" + ";charset=utf-8")
	public Response getAllUser(@PathParam("hibernateconfigfilename") String hibernateconfigfilename)
			throws JSONException {
		UserService userService = new UserService(hibernateconfigfilename);
		
		List<UserDTO> userDTOList = new LinkedList<UserDTO>();
		List<User> userList = userService.findAll();
		for (int i = 0; i < userList.size(); i++) {
			UserDTO userDTO = new UserDTO(userList.get(i).getId(), userList.get(i).getPrename(),
					userList.get(i).getLastname(), userList.get(i).getPassword(), userList.get(i).getEmail());
			userDTOList.add(userDTO);
		}
		
		Gson gson = new Gson();
		String output = gson.toJson(userDTOList);
		
		return Response.status(200).entity(output).build();
	}
	
	@GET
	@Path("/alleOhneProjektZugriff/{userId}/{projectId}/{hibernateconfigfilename}")
	@Produces("application/json" + ";charset=utf-8")
	public Response getAllUsersWithoutAssignForProject(@PathParam("userId") Integer userId,
			@PathParam("projectId") Integer projectId,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) throws JSONException {
		UserService userService = new UserService(hibernateconfigfilename);
		User_Role_ProjectService urpService = new User_Role_ProjectService(hibernateconfigfilename);
		List<User_Role_Project> urpList = urpService.findAll();
		List<User_Role_Project> urpListProject = urpService.findListByProjectId(projectId);
		
		List<UserDTO> userDTOList = new LinkedList<UserDTO>();
		List<User> userList = userService.findAll();
		List<Integer> ids = new LinkedList<Integer>();
		
		for (int i = 0; i < urpList.size(); i++) {
			for (int j = 0; j < userList.size(); j++) {
				if (urpList.get(i).getPk().getRoleId().equals(4)) {
					if (userList.get(j).getId().equals(urpList.get(i).getPk().getUserId())) {
						userList.remove(j);
					}
				}
				if (urpList.get(i).getPk().getUserId().equals(userId)) {
					if (userList.get(j).getId().equals(urpList.get(i).getPk().getUserId())) {
						userList.remove(j);
					}
				}
				if (urpList.get(i).getPk().getProjectId().equals(projectId)) {
					if (userList.get(j).getId().equals(urpList.get(i).getPk().getUserId())) {
						ids.add(userList.get(j).getId());
						userList.remove(j);
					}
				}
			}
		}
		
		for (int i = 0; i < userList.size(); i++) {
			for (int j = 0; j < ids.size(); j++) {
				if (userList.get(i).getId().equals(ids.get(j))) {
					userList.remove(i);
				}
			}
		}
		
		for (int j = 0; j < userList.size(); j++) {
			UserDTO userDTO = new UserDTO(userList.get(j).getId(), userList.get(j).getPrename(),
					userList.get(j).getLastname(), userList.get(j).getPassword(), userList.get(j).getEmail());
			userDTOList.add(userDTO);
		}
		
		// Set<UserDTO> setList = new LinkedHashSet<>(userDTOList);
		// userDTOList.clear();
		// userDTOList.addAll(setList);
		
		Gson gson = new Gson();
		String output = gson.toJson(userDTOList);
		
		return Response.status(200).entity(output).build();
		
	}
	
	@GET
	@Path("/alle/scrumprojekt/{projectId}/{hibernateconfigfilename}")
	@Produces("application/json" + ";charset=utf-8")
	public Response getAllUsersOfProject(@PathParam("projectId") Integer projectId,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) throws JSONException {
		User_Role_ProjectService urpService = new User_Role_ProjectService(hibernateconfigfilename);
		UserService userService = new UserService(hibernateconfigfilename);
		List<User_Role_Project> urpList = urpService.findListByProjectId(projectId);
		List<User> userList = new LinkedList<User>();
		for (int i = 0; i < urpList.size(); i++) {
			
			userList.add(userService.findById(urpList.get(i).getPk().getUserId()));
		}
		
		List<UserDTO> userDTOList = new LinkedList<UserDTO>();
		for (int i = 0; i < userList.size(); i++) {
			UserDTO userDTO = new UserDTO(userList.get(i).getId(), userList.get(i).getPrename(),
					userList.get(i).getLastname(), userList.get(i).getPassword(), userList.get(i).getEmail());
			userDTOList.add(userDTO);
		}
		
		Gson gson = new Gson();
		String output = gson.toJson(userDTOList);
		return Response.status(200).entity(output).build();
	}
	
	@GET
	@Path("/alle/scrumprojekt/entwickler/{projectId}/{hibernateconfigfilename}")
	@Produces("application/json" + ";charset=utf-8")
	public Response getDeveloperOfProject(@PathParam("projectId") Integer projectId,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) throws JSONException {
		User_Role_ProjectService urpService = new User_Role_ProjectService(hibernateconfigfilename);
		UserService userService = new UserService(hibernateconfigfilename);
		List<User_Role_Project> urpList = urpService.findListByProjectId(projectId);
		List<User> userList = new LinkedList<User>();
		for (int i = 0; i < urpList.size(); i++) {
			if (urpList.get(i).getPk().getRoleId() == 3)
				userList.add(userService.findById(urpList.get(i).getPk().getUserId()));
		}
		
		List<UserDTO> userDTOList = new LinkedList<UserDTO>();
		for (int i = 0; i < userList.size(); i++) {
			UserDTO userDTO = new UserDTO(userList.get(i).getId(), userList.get(i).getPrename(),
					userList.get(i).getLastname(), userList.get(i).getPassword(), userList.get(i).getEmail());
			userDTOList.add(userDTO);
		}
		
		Gson gson = new Gson();
		String output = gson.toJson(userDTOList);
		return Response.status(200).entity(output).build();
	}
	
	@Path("/suche/{email}/rolle/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response getRoleByEmail(@PathParam("email") String email,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		UserService userService = new UserService(hibernateconfigfilename);
		User user = userService.findByEmail(email);
		String output = "User ist kein Scrum Master";
		if (user != null) {
			User_Role_ProjectService urpService = new User_Role_ProjectService(hibernateconfigfilename);
			List<User_Role_Project> urpList = urpService.findListByUserId(user.getId());
			for (int i = 0; i < urpList.size(); i++) {
				if (urpList.get(i).getPk().getRoleId() == 1)
					output = "User ist Scrum Master";
				if (urpList.get(i).getPk().getRoleId() == 2)
					output = "User ist Product Owner";
				if (urpList.get(i).getPk().getRoleId() == 3)
					output = "User ist Entwickler";
				if (urpList.get(i).getPk().getRoleId() == 4)
					output = "User ist Admin";
			}
		}
		return Response.status(200).entity(output).build();
	}
	
	@Path("/suche/{email}/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response getUserByEmail(@PathParam("email") String email,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		UserService userService = new UserService(hibernateconfigfilename);
		User user = userService.findByEmail(email);
		userService.checkIfEmailAlreadyExists(email);
		String output = "User ist nicht vorhanden";
		if (user != null) {
			UserDTO userDTO = new UserDTO(user.getId(), user.getPrename(), user.getLastname(), user.getPassword(),
					user.getEmail());
					
			Gson gson = new Gson();
			output = gson.toJson(userDTO);
		}
		return Response.status(200).entity(output).build();
	}
	
	@Path("/sucheDoppelteMail/{email}/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response checkEmail(@PathParam("email") String email,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		UserService userService = new UserService(hibernateconfigfilename);
		String output = "Email frei";
		if (userService.checkIfEmailAlreadyExists(email)) {
			output = "Email bereits vergeben";
		}
		return Response.status(200).entity(output).build();
	}
	
	@Path("/updatePassword/{email}/{hibernateconfigfilename}")
	@POST
	@Consumes("application/json" + ";charset=utf-8")
	public Response updateUserPasswordByEmail(@PathParam("email") String email,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename, InputStream input) {
		StringBuilder stringBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while ((line = in.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String userDetails = stringBuilder.toString();
		
		Gson gson = new Gson();
		UserDTO userDTO = gson.fromJson(userDetails, UserDTO.class);
		User user = new User();
		UserService userService = new UserService(hibernateconfigfilename);
		user = userService.findByEmail(email);
		user.setPassword(userDTO.getPassword());
		userService.update(user);
		
		String output = "Passwort vom Benutzer geupdated";
		return Response.status(200).entity(output).build();
	}
	
	@POST
	@Path("/create/{roleId}/{hibernateconfigfilename}")
	@Consumes("application/json" + ";charset=utf-8")
	public Response createScrumMaster(@PathParam("roleId") Integer roleId,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename, InputStream input) {
		StringBuilder stringBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while ((line = in.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String userDetails = stringBuilder.toString();
		
		Gson gson = new Gson();
		UserDTO userDTO = gson.fromJson(userDetails, UserDTO.class);
		Role role = new Role();
		User user = new User(userDTO.getId(), userDTO.getPrename(), userDTO.getLastname(), userDTO.getPassword(),
				userDTO.getEmail());
				
		UserService userService = new UserService(hibernateconfigfilename);
		userService.persist(user);
		
		RoleService roleService = new RoleService(hibernateconfigfilename);
		role = roleService.findById(roleId);
		
		User_Role_ProjectService urpService = new User_Role_ProjectService(hibernateconfigfilename);
		User_Role_Project urp = new User_Role_Project();
		User_Role_Project.Pk pk = new User_Role_Project.Pk(user.getId(), role.getId(), 0);
		urp.setPk(pk);
		urpService.persist(urp);
		
		String output = "User erfolgreich erstellt";
		return Response.status(200).entity(output).build();
		
	}
	
	@POST
	@Path("/create/{roleId}/{projectId}/{hibernateconfigfilename}")
	@Consumes("application/json" + ";charset=utf-8")
	public Response createUserForProject(@PathParam("projectId") Integer projectId, @PathParam("roleId") Integer roleId,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename, InputStream input) {
		StringBuilder stringBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while ((line = in.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String userDetails = stringBuilder.toString();
		
		Gson gson = new Gson();
		UserDTO userDTO = gson.fromJson(userDetails, UserDTO.class);
		
		User user = new User(userDTO.getId(), userDTO.getPrename(), userDTO.getLastname(), userDTO.getPassword(),
				userDTO.getEmail());
				
		UserService bs = new UserService(hibernateconfigfilename);
		bs.persist(user);
		
		RoleService brs = new RoleService(hibernateconfigfilename);
		Role benutzerrolle = new Role();
		benutzerrolle = brs.findById(roleId);
		
		User_Role_ProjectService urpService = new User_Role_ProjectService(hibernateconfigfilename);
		User_Role_Project urp = new User_Role_Project();
		User_Role_Project.Pk pk = new User_Role_Project.Pk(user.getId(), benutzerrolle.getId(), projectId);
		urp.setPk(pk);
		urpService.persist(urp);
		
		String output = "User erfolgreich erstellt";
		return Response.status(200).entity(output).build();
		
	}
	
	@GET
	@Path("/checkAdmission/{userId}/{projectId}/{hibernateconfigfilename}")
	@Produces("application/json" + ";charset=utf-8")
	public Response checkAdmission(@PathParam("hibernateconfigfilename") String hibernateconfigfilename,
			@PathParam("userId") Integer userId, @PathParam("projectId") Integer projectId) throws JSONException {
		String output = "Nein";
		User_Role_ProjectService urpService = new User_Role_ProjectService(hibernateconfigfilename);
		if (urpService.checkAdmission(userId, projectId)) {
			output = "Ja";
		}
		
		return Response.status(200).entity(output).build();
	}
	
	@GET
	@Path("/checkAdmin/{userId}/{hibernateconfigfilename}")
	@Produces("application/json" + ";charset=utf-8")
	public Response checkAdmin(@PathParam("hibernateconfigfilename") String hibernateconfigfilename,
			@PathParam("userId") Integer userId) throws JSONException {
		String output = "Nein";
		User_Role_ProjectService urpService = new User_Role_ProjectService(hibernateconfigfilename);
		if (urpService.checkAdmin(userId)) {
			output = "Ja";
		}
		
		return Response.status(200).entity(output).build();
	}
	
	@POST
	@Path("/delete/{hibernateconfigfilename}")
	@Consumes("application/json" + ";charset=utf-8")
	public Response deleteDefinitionOfDone(InputStream input,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		StringBuilder b = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while ((line = in.readLine()) != null) {
				b.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String userDetails = b.toString();
		Gson gson = new Gson();
		UserDTO userDTO = gson.fromJson(userDetails, UserDTO.class);
		UserService userService = new UserService(hibernateconfigfilename);
		User_Role_ProjectService urpService = new User_Role_ProjectService(hibernateconfigfilename);
		UserStoryTaskService userstorytaskService = new UserStoryTaskService(hibernateconfigfilename);
		
		User user = userService.findById(userDTO.getId());
		for (int i = 0; i < user.getUserstorytask().size(); i++) {
			UserStoryTask userstorytask = userstorytaskService.findById(user.getUserstorytask().get(i).getId());
			userstorytask.setUser(null);
			userstorytaskService.update(userstorytask);
			user.getUserstorytask().get(i).setUser(null);
		}
		
		String output = "";
		try {
			userService.update(user);
			userService.delete(userDTO.getId());
			urpService.deleteBenutzer(userDTO.getId());
			output = "Benutzer erfolgreich gelöscht";
		} catch (Exception e) {
			e.printStackTrace();
			output = "Benutzer wurde nicht erfolgreich gelöscht";
		}
		return Response.status(200).entity(output).build();
	}
	
	@GET
	@Path("/assign/{userId}/{roleId}/{projectId}/{hibernateconfigfilename}")
	@Produces("application/json" + ";charset=utf-8")
	public Response assignUserForProject(@PathParam("hibernateconfigfilename") String hibernateconfigfilename,
			@PathParam("userId") Integer userId, @PathParam("roleId") Integer roleId,
			@PathParam("projectId") Integer projectId) throws JSONException {
		String output = "Fail";
		try {
			User_Role_ProjectService urpService = new User_Role_ProjectService(hibernateconfigfilename);
			User_Role_Project urp = new User_Role_Project();
			User_Role_Project.Pk pk = new User_Role_Project.Pk(userId, roleId, projectId);
			urp.setPk(pk);
			urpService.persist(urp);
			output = "Ok";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Response.status(200).entity(output).build();
	}
	
}