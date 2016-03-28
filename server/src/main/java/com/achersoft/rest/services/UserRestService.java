package com.achersoft.rest.services;

import com.achersoft.user.UserService;
import com.achersoft.user.dao.User;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/users")
public class UserRestService {

    private @Inject UserService userProvider; 

    @GET 
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON})	
    public List<User> getUsers() throws Exception {
        return userProvider.getUsers();	
    }
    
    @GET 
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})	
    public User getUser(@PathParam("id")int id) throws Exception {
        return userProvider.getUser(id);	
    }

    @POST 
    @Path("/create")
    @Produces({MediaType.APPLICATION_JSON})	
    @Consumes({MediaType.APPLICATION_JSON})
    public User createUser(@Valid @NotNull User user) throws Exception {
        return userProvider.createUser(user);
    }
    
    @PUT 
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})	
    @Consumes({MediaType.APPLICATION_JSON})
    public User editUser(@PathParam("id")String id, @Valid @NotNull User user) throws Exception {
        return userProvider.editUser(user);
    }
    
    @DELETE 
    @Path("/{id}")
    public void deleteUser(@PathParam("id")int id) throws Exception {
        userProvider.deleteUser(id);	
    }
}