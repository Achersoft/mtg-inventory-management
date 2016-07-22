package com.achersoft.rest.services;

import com.achersoft.order.OrderService;
import com.achersoft.order.dao.Order;
import com.achersoft.security.annotations.RequiresPrivilege;
import com.achersoft.security.type.Privilege;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/orders")
public class OrderRestService {

    private @Inject OrderService orderService; 

    @RequiresPrivilege({Privilege.CUSTOMER,Privilege.EMPLOYEE})
    @PUT 
    @Path("/create")
    @Consumes({MediaType.APPLICATION_JSON})	
    public void createOrder(Order order) throws Exception {
	orderService.createOrder(order);
    }
    
    @RequiresPrivilege({Privilege.ADMIN,Privilege.EMPLOYEE})
    @GET 
    @Produces({MediaType.APPLICATION_JSON})	
    public List<Order> getOrders() throws Exception {
	return orderService.getOrders();
    }
    
    @RequiresPrivilege({Privilege.ADMIN,Privilege.EMPLOYEE})
    @GET 
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})	
    public Order getOrder(@PathParam("id") String id) throws Exception {
	return orderService.getOrder(id);
    }
}

