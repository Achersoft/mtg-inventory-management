package com.achersoft.rest.services;

import com.achersoft.mtg.enums.EnumService;
import com.achersoft.mtg.enums.dao.EnumAPI;
import com.achersoft.security.annotations.RequiresPrivilege;
import com.achersoft.security.type.Privilege;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/enums")
public class EnumRestService {

    private @Inject EnumService enumService; 

    @RequiresPrivilege({Privilege.ADMIN,Privilege.CUSTOMER,Privilege.EMPLOYEE})
    @GET 
    @Path("/sets")
    @Produces({MediaType.APPLICATION_JSON})	
    public List<EnumAPI> getSets() throws Exception {
        return enumService.getSets();
    }
    
    @RequiresPrivilege({Privilege.ADMIN,Privilege.CUSTOMER,Privilege.EMPLOYEE})
    @GET 
    @Path("/languages")
    @Produces({MediaType.APPLICATION_JSON})	
    public List<EnumAPI> getLanguages() throws Exception {
        return enumService.getLanguages();
    }
}

