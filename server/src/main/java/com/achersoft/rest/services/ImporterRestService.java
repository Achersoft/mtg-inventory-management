package com.achersoft.rest.services;

import com.achersoft.mtg.importer.CardImporterService;
import com.achersoft.mtg.importer.dao.SetsImport;
import com.achersoft.security.annotations.RequiresPrivilege;
import com.achersoft.security.type.Privilege;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/importer")
public class ImporterRestService {

    private @Inject CardImporterService cardImporter; 

    @RequiresPrivilege({Privilege.ADMIN})
    @POST 
    @Path("/upload")
    @Produces({MediaType.APPLICATION_JSON})	
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void importAllSets(@FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition contentDispositionHeader) throws Exception {
        cardImporter.importSets(new ObjectMapper().readValue(fileInputStream, SetsImport.class));
    }
}