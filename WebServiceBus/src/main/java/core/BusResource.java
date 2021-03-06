package core;


import static com.google.common.base.Preconditions.checkNotNull;
import io.dropwizard.jersey.params.IntParam;

import java.net.HttpURLConnection;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.springframework.beans.factory.annotation.Autowired;

import core.model.Bus;
import core.model.BusRepository;
import core.model.LinhaRepository;
import core.model.PontoRepository;


//import static com.google.common.base.Preconditions.checkNotNull;
//import com.sun.jersey.api.ConflictException;
//import io.dropwizard.jersey.params.IntParam;
//import io.dropwizard.jersey.params.IntParam;
//import com.sun.jersey.multipart.FormDataParam;
//import io.dropwizard.hibernate.UnitOfWork;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.nio.file.FileSystems;
//import java.nio.file.Files;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.GregorianCalendar;
//import javax.validation.Valid;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.WebApplicationException;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.UriBuilder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Felipe Cousin
 */
@Path("/bus")
public class BusResource {
    
    @Autowired
    private BusRepository busRepository;

    @Autowired
    private LinhaRepository linhaRepository;
    
    @Autowired
	private PontoRepository pontoRespository;

//    private Bus onibus;
//    private ArrayList<Bus> listaOnibus = new ArrayList<Bus>();
//    
//    public BusResource(Bus onibus) {
//        this.onibus = onibus;
//        listaOnibus.add(onibus);
//    }
    
//    @GET    
//    @Path("/{id}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public String getBus(@PathParam("id") @Valid IntParam id,
//            @QueryParam("callback") String callback) {
//        System.out.println("ta no get");
//        //return onibus.toString();
//        return callback+"("+onibus+")";
//    }
    
    @GET    
    @Path("/{id}")    
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Bus getBusById(@PathParam("id") @Valid IntParam id)
    {
        return busRepository.findById(id.get());
    }
    
    
    @GET
    @Path("/setargps/{id}/{latitude}/{longitude}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response setBusPosition(
            @PathParam("id") @Valid IntParam id,
            @PathParam("latitude")  @Valid Double  latitude, 
            @PathParam("longitude")  @Valid Double longitude) {
        System.out.println("latitude = "+latitude+" e longitude = "+longitude);
        try {
            checkNotNull(latitude);
            checkNotNull(longitude);
        } catch(Exception e) {
            throw new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("parameters are mandatory").build());
        }      
        Bus onibus = busRepository.findById(id.get());
        onibus.setPosicaoAtual(latitude, longitude);
    	busRepository.save(onibus);

        return Response.created(UriBuilder.fromResource(BusResource.class).build()).build();
       
    }
    
    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response setBusPositionPost(
            @PathParam("id") @Valid IntParam id,
            @FormParam("latitude")  @Valid Double  latitude, 
            @FormParam("longitude")  @Valid Double longitude) {
        System.out.println("latitude = "+latitude+" e longitude = "+longitude);
        try {
            checkNotNull(latitude);
            checkNotNull(longitude);
        } catch(Exception e) {
            throw new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("parameters are mandatory").build());
        }      
        
        Bus onibus = busRepository.findById(id.get());
        onibus.setPosicaoAtual(latitude, longitude);
    	busRepository.save(onibus);
        
        return Response.created(UriBuilder.fromResource(BusResource.class).build()).build();
       
    }
        
}
