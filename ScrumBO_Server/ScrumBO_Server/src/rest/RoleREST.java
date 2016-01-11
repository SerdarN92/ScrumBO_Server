package rest;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;

import com.google.gson.Gson;

import dto.RoleDTO;
import model.Role;
import service.RoleService;

@Path("/role")
public class RoleREST {
	
	@Path("/all/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response getAllRoles(@PathParam("hibernateconfigfilename") String hibernateconfigfilename)
			throws JSONException {
		RoleService roleService = new RoleService(hibernateconfigfilename);
		List<Role> role = roleService.findAll();
		List<RoleDTO> roleDTOList = new LinkedList<>();
		for (int i = 0; i < role.size(); i++) {
			RoleDTO roleDTO = new RoleDTO(role.get(i).getId(), role.get(i).getDescription());
			roleDTOList.add(roleDTO);
		}
		
		Gson gson = new Gson();
		String output = gson.toJson(roleDTOList);
		
		return Response.status(200).entity(output).build();
	}
	
}
