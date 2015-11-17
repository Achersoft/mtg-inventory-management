package com.achersoft.rest.services;

import com.achersoft.mtg.importer.CardImporterService;
import com.achersoft.mtg.importer.dao.CardImport;
import com.achersoft.mtg.importer.dao.SetImport;
import com.achersoft.mtg.importer.dao.SetsImport;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.context.ApplicationContext;

@Path("/importer")
public class ImporterRestService {

    private @Inject CardImporterService cardImporter; 
    //private @Inject ApplicationContext ctx;

    @POST 
    @Path("/upload")
    @Produces({MediaType.APPLICATION_JSON})	
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void getCandidates(@FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition contentDispositionHeader) throws Exception {
        cardImporter.importSets(new ObjectMapper().readValue(fileInputStream, SetsImport.class));
    }
    
/*
    @GET 
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})	
    public CandidateDTO getCandidate(@PathParam("id")String id) throws Exception {
        return ctx.getBean(CandidateDTO.class).fromDAO(candidateProvider.getCandidate(id));	
    }
    */
    //@GET 
   // @Path("/{id}/resume")
    //@Produces({"*/*"})
/*    public Response getCandidateResume(@PathParam("id")String id) throws Exception {
        CandidateResume candidateResume = candidateProvider.getCandidateResume(id);
        if(candidateResume == null)
            return null;
        return Response.ok(candidateResume.getDocument())
                .type(candidateResume.getContentType())
                .tag(candidateResume.getFileName())
                .build();
    }
    
    @POST 
    @Path("/{id}/highlightedresume")
    @Produces({MediaType.APPLICATION_JSON})	
    @Consumes({MediaType.APPLICATION_JSON})
    public CandidateResumeDTO getCandidateHighlightedResume(@PathParam("id")String id, CandidateListSearch candidateSearch) throws Exception {
        return CandidateResumeDTO.builder().resume(candidateProvider.getCandidateHighlightedResume(id, candidateSearch)).build();
    }

    @PUT 
    @Produces({MediaType.APPLICATION_JSON})	
    @Consumes({MediaType.APPLICATION_JSON})
    public CandidateDTO addCandidate(CandidateDTO candidate) throws Exception {
        return ctx.getBean(CandidateDTO.class).fromDAO(candidateProvider.addCandidate(candidate.toDAO(null), null));	 
    }
    
    @RolesAllowed("estaff")
    @PUT 
    @Path("/{id}/comments/")
    @Produces({MediaType.APPLICATION_JSON})	
    @Consumes({MediaType.APPLICATION_JSON})
    public List<CandidateCommentDTO> addComment(@PathParam("id")String id, CandidateCommentDTO comment) throws Exception {
        List<CandidateCommentDTO> dtos = new ArrayList();
        if(comment != null) {
            for(CandidateComment dao : candidateProvider.addComment(comment.toDAO(id)))
                dtos.add(ctx.getBean(CandidateCommentDTO.class).fromDAO(dao));
        }
        return dtos;	
    }
    
    @RolesAllowed("estaff")
    @PUT 
    @Path("/interviews/{interviewId}/comments/")
    @Produces({MediaType.APPLICATION_JSON})	
    @Consumes({MediaType.APPLICATION_JSON})
    public List<CandidateInterviewCommentDTO> addInterviewComment(@PathParam("interviewId")String interviewId, CandidateInterviewCommentDTO comment) throws Exception {
        List<CandidateInterviewCommentDTO> dtos = new ArrayList();
        if(comment != null) {
            for(CandidateInterviewComment dao : candidateProvider.addInterviewComment(comment.toDAO(interviewId)))
                dtos.add(ctx.getBean(CandidateInterviewCommentDTO.class).fromDAO(dao));
        }
        return dtos;	
    }
    
    @PUT 
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})	
    @Consumes({MediaType.APPLICATION_JSON})
    public CandidateDTO editCandidate(@PathParam("id")String id, CandidateDTO candidate) throws Exception {
        return ctx.getBean(CandidateDTO.class).fromDAO(candidateProvider.editCandidate(candidate.toDAO(id)));	
    }
    
    @RolesAllowed("estaff")
    @PUT 
    @Path("/{candidateId}/comments/{commentId}")
    @Produces({MediaType.APPLICATION_JSON})	
    @Consumes({MediaType.APPLICATION_JSON})
    public List<CandidateCommentDTO> editComment(@PathParam("candidateId")String candidateId, 
            @PathParam("commentId")String commentId, CandidateCommentDTO comment) throws Exception {
        List<CandidateCommentDTO> dtos = new ArrayList();
        for(CandidateComment dao : candidateProvider.editComment(comment.toDAO(commentId, candidateId)))
            dtos.add(ctx.getBean(CandidateCommentDTO.class).fromDAO(dao));
        return dtos;	
    }
    
    @RolesAllowed("estaff")
    @PUT 
    @Path("/interviews/{interviewId}/comments/{commentId}")
    @Produces({MediaType.APPLICATION_JSON})	
    @Consumes({MediaType.APPLICATION_JSON})
    public List<CandidateInterviewCommentDTO> editInterviewComment(@PathParam("interviewId")String interviewId, 
            @PathParam("commentId")String commentId, CandidateInterviewCommentDTO comment) throws Exception {
        List<CandidateInterviewCommentDTO> dtos = new ArrayList();
        for(CandidateInterviewComment dao : candidateProvider.editInterviewComment(comment.toDAO(commentId, interviewId)))
            dtos.add(ctx.getBean(CandidateInterviewCommentDTO.class).fromDAO(dao));
        return dtos;	
    }
    
    @DELETE 
    @Path("/{id}")
    public void deleteCandidate(@PathParam("id")String id) {
        candidateProvider.deleteCandidate(id);	
    }
    
    @RolesAllowed("estaff")
    @DELETE 
    @Path("/{candidateId}/comments/{commentId}")
    @Produces({MediaType.APPLICATION_JSON})	
    @Consumes({MediaType.APPLICATION_JSON})
    public List<CandidateCommentDTO> deleteComment(@PathParam("candidateId")String candidateId, 
            @PathParam("commentId")String commentId, CandidateCommentDTO comment) throws Exception {
        List<CandidateCommentDTO> dtos = new ArrayList();
        for(CandidateComment dao : candidateProvider.deleteComment(comment.toDAO(commentId, candidateId)))
            dtos.add(ctx.getBean(CandidateCommentDTO.class).fromDAO(dao));
        return dtos;	
    }
    
    @RolesAllowed("estaff")
    @DELETE 
    @Path("/interviews/{interviewId}/comments/{commentId}")
    @Produces({MediaType.APPLICATION_JSON})	
    @Consumes({MediaType.APPLICATION_JSON})
    public List<CandidateInterviewCommentDTO> deleteInterviewComment(@PathParam("interviewId")String interviewId, 
            @PathParam("commentId")String commentId, CandidateInterviewCommentDTO comment) throws Exception {
        List<CandidateInterviewCommentDTO> dtos = new ArrayList();
        for(CandidateInterviewComment dao : candidateProvider.deleteInterviewComment(comment.toDAO(commentId, interviewId)))
            dtos.add(ctx.getBean(CandidateInterviewCommentDTO.class).fromDAO(dao));
        return dtos;	
    }*/
}