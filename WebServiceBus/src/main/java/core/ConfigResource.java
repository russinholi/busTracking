package core;


import static com.google.common.base.Preconditions.checkNotNull;
import io.dropwizard.jersey.params.IntParam;

import java.net.HttpURLConnection;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import Response.BusList;
import Response.LinhaList;
import Response.PontoList;
import Response.ResponsePonto;
import Response.ResponsePontoList;
import core.model.Bus;
import core.model.BusRepository;
import core.model.Linha;
import core.model.LinhaRepository;
import core.model.PontoRepository;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Felipe Cousin
 */
@Component
@Path("/get")
public class ConfigResource {
    
    @Autowired
    private BusRepository busRepository;

    @Autowired
    private LinhaRepository linhaRepository;
    
    @Autowired
	private PontoRepository pontoRepository;
    
    private int idAtual = 10;
    
    public ConfigResource() {
        
    }

    public void atualizarTemposSistema() {

    	atualizarOnibus();
    }

	private void atualizarOnibus() {
		for (Bus onibus : busRepository.findAll()) {
            onibus.atualizarCrowdness();
            busRepository.save(onibus);
        }
	}
    
    @GET    // retorna a linha do onibus {id}
    @Path("/bus/{id}/linha")    
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Linha getLinhaBusById(@PathParam("id") @Valid IntParam id)
    {
    	Bus bus = busRepository.findById(id.get());
        return linhaRepository.findById(bus.getLinhaId());
    }
    
    @GET    // retorna a linha do onibus {id}
    @Path("/todas_linhas")    
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public LinhaList getLinhas()
    {
    	return new LinhaList(linhaRepository.findAll());
    }
    
    @GET   
    @Path("/inicializar_id")    
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Integer teste()
    {        
        return idAtual++;
    }
    
    @GET    // retorna o onibus {id}
    @Path("/bus/{id}")    
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Bus getPosicaoBusById(@PathParam("id") @Valid IntParam id)
    {
        return busRepository.findById(id.get());
    }
    
    @GET    // retorna todos os onibus na linha {id}
    @Path("/linha/{id}")    
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public BusList getOnibusNaLinha(@PathParam("id") @Valid IntParam id)
    {
        //System.out.println(listaOnibus.toString());    
        if(id.get() == 0) return new BusList(busRepository.findAll());
        return new BusList(busRepository.findAllByLinha(id.get()));
    }
    
    @GET    
    @Path("/ponto/{idPonto}/linha/{idLinha}")    
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponsePontoList getOnibusNoPonto(@PathParam("idPonto") @Valid IntParam varIdPonto,
                                              @PathParam("idLinha") @Valid IntParam varIdLinha)
    {
        int idPonto = varIdPonto.get();
        int idLinha = varIdLinha.get();
        List<Bus> onibusPonto = busRepository.findAllByPontoAndLinha(idPonto, idLinha);
        Linha linha = linhaRepository.findById(idLinha);
        
		List<ResponsePonto> responsePontoList = ResponsePontoList.convertBusListToResponsePonto(onibusPonto, linha);
        return new ResponsePontoList(responsePontoList);
    }
    
//    @GET    
//    @Path("/crowd/{idBus}/{value}")    
//    @Consumes(MediaType.APPLICATION_JSON)
//    public void teste(@PathParam("idBus") @Valid IntParam idBus,
//            @PathParam("value") @Valid IntParam value) {
//        
//        int crowd = value.get();
//        int idOnibus = idBus.get();
//        if(crowd >= 1 && crowd <= 5) {
//            if(mapaOnibus.containsKey(idOnibus)) {
//                Bus bus = mapaOnibus.get(idOnibus);
//                bus.adicionarCrowdness(crowd);
//            }
//        }
//
//    }
    
//    @GET    
//    @Path("/pontos/{id}")    
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public PontoList getPontosNaLinha(@PathParam("id") @Valid IntParam id)
//    {
//        System.out.println(listaOnibus.toString());
//        ArrayList<Bus> list = new ArrayList<Bus>();
//        System.out.println("\n"+listaOnibus.containsKey(id)+" id = "+id.get()+"\n");
//        if(listaOnibus.containsKey(id.get())) {
//            list = listaOnibus.get(id.get()); 
//            System.out.println(list);
//        }        
//        return new BusList(list);
//    }
    
    @GET    
    @Path("/pontos_linha/{idLinha}")    
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PontoList getPontos(@PathParam("idLinha") @Valid IntParam varIdLinha)
    {     
    	long idLinha = varIdLinha.get();
        if(idLinha == 0) {
        	return new PontoList(pontoRepository.findAll());
        }
        
        return new PontoList(pontoRepository.findByLinha(idLinha));
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
    
    @GET
    @Path("/setarlinha/{idBus}/{idLinha}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response setLinhaBus(
            @PathParam("idBus") @Valid IntParam idBus,
            @PathParam("idLinha")  @Valid IntParam  idLinha) {

        try {
            checkNotNull(idBus);
            checkNotNull(idLinha);
        } catch(Exception e) {
            throw new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("parameters are mandatory").build());
        }      
        Linha linha = linhaRepository.findById(idLinha.get());
        
        Bus onibus = busRepository.findById(idBus.get());
        onibus.setLinhaId(linha);
    	busRepository.save(onibus);
        
        return Response.created(UriBuilder.fromResource(BusResource.class).build()).build();       
    }
    
//    @POST
//    @Path("/{id}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response setBusPositionPost(
//            @PathParam("id") @Valid IntParam id,
//            @FormParam("latitude")  @Valid Double  latitude, 
//            @FormParam("longitude")  @Valid Double longitude) {
//        System.out.println("latitude = "+latitude+" e longitude = "+longitude);
//        try {
//            checkNotNull(latitude);
//            checkNotNull(longitude);
//        } catch(Exception e) {
//            throw new WebApplicationException(Response.status(HttpURLConnection.HTTP_BAD_REQUEST).entity("parameters are mandatory").build());
//        }      
//        
//        onibus.setPosicaoAtual(latitude,longitude);
//        
//        return Response.created(UriBuilder.fromResource(BusResource.class).build()).build();
//       
//    }

    

    
        
}
