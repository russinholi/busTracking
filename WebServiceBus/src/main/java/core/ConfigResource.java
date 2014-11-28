package core;


import static com.google.common.base.Preconditions.checkNotNull;
import io.dropwizard.jersey.params.IntParam;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

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

import Response.BusList;
import Response.LinhaList;
import Response.PontoList;
import Response.ResponsePonto;
import Response.ResponsePontoList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Felipe Cousin
 */
@Path("/get")
public class ConfigResource {
    
    private HashMap<Integer, Bus> mapaOnibus = new HashMap<Integer, Bus>(); // chave é o id do onibus, e o onibus
    private HashMap<Integer, Linha> mapaOnibusLinha = new HashMap<Integer, Linha>(); // chave é o id do onibus, e a sua linha
    private HashMap<Integer, Linha> mapaLinha = new HashMap<Integer, Linha>(); // chave é o id da linha, e a linha       
    private HashMap<Integer, ArrayList<Bus>> listaOnibus = new HashMap<Integer, ArrayList<Bus>>(); // chave é id da linha, arraylist de todos onibus na linha
    private HashMap<Integer, ArrayList<Linha>> linhasNoPonto = new HashMap<Integer, ArrayList<Linha>>(); // chave é id do ponto, arraylist de todas as linhas que passam por ele
    //private HashMap<Integer, ArrayList<Bus>> onibusNoPonto = new HashMap<Integer, ArrayList<Bus>>(); // chave é id do ponto, arraylist de todos os pontos que passam por ele
    private HashMap<Integer, ArrayList<ResponsePonto>> responseTodosOnibusNoPonto = new HashMap<Integer, ArrayList<ResponsePonto>>(); // chave é id do ponto, arraylist de todos as respostas de onibus que passam por ele
    private HashMap<Integer, Ponto> listaPontos = new HashMap<Integer, Ponto>();
    private ArrayList<Linha> linhasOrdenadas = new ArrayList<Linha>();
    private HashMap<Integer, HashMap<Integer, ArrayList<ResponsePonto>>> matrizResponsePonto = new HashMap<Integer, HashMap<Integer, ArrayList<ResponsePonto>>>();
    
    public ConfigResource() {
        
    }

    public void adicionarBus(Bus onibus) {
        Linha linha = onibus.pegarLinha();
        mapaOnibus.put(onibus.getId(), onibus);
        mapaOnibusLinha.put(onibus.getId(), linha);
        if(listaOnibus.containsKey(linha.getIdLinha())) listaOnibus.get(linha.getIdLinha()).add(onibus);
        else {
            ArrayList<Bus> list = new ArrayList<Bus>();
            list.add(onibus);
            listaOnibus.put(linha.getIdLinha(), list);
        }
        if(!mapaLinha.containsKey(linha.getIdLinha())) { 
            linhasOrdenadas.add(linha);
            Collections.sort(linhasOrdenadas);
            mapaLinha.put(linha.getIdLinha(), linha);
        }            
    }
    
    public void adicionarPonto(Ponto ponto) {
        if(!listaPontos.containsKey(ponto.getId()) && ponto.isPontoDeOnibus()) listaPontos.put(ponto.getId(), ponto);        
    }
    
    public void configurarPontos() {
        Iterator<Ponto> iteradorPontos = listaPontos.values().iterator();
        Date inicio = Calendar.getInstance().getTime();
        while(iteradorPontos.hasNext()) {            
            Ponto ponto = (Ponto) iteradorPontos.next();
            Iterator<Linha> iteradorLinhas = mapaLinha.values().iterator();
            ArrayList<Linha> listaLinhas = new ArrayList<Linha>();
            ArrayList<Bus> listaBuses = new ArrayList<Bus>();
            ArrayList<ResponsePonto> listaResponse = new ArrayList<ResponsePonto>();
            HashMap<Integer, ArrayList<ResponsePonto>> mapa = new HashMap<Integer, ArrayList<ResponsePonto>>();            

            while(iteradorLinhas.hasNext()) {
                Linha linha = iteradorLinhas.next();
                ArrayList<ResponsePonto> listaMatriz = new ArrayList<ResponsePonto>();
                if(linha.pontoOnibusEstaNaLinha(ponto)) {
                    listaLinhas.add(linha);
                    Iterator<Bus> iteradorOnibus = listaOnibus.get(linha.getIdLinha()).iterator();
                    while(iteradorOnibus.hasNext()) {
                        Bus b = iteradorOnibus.next();
                        listaBuses.add(b);
                        ResponsePonto resp = new ResponsePonto(ponto, linha, b);
                        listaResponse.add(resp);
                        listaMatriz.add(resp);
                    }
                    Collections.sort(listaMatriz);
                    mapa.put(linha.getIdLinha(), listaMatriz);
                }
            }
            
            linhasNoPonto.put(ponto.getId(), listaLinhas);
            //onibusNoPonto.put(ponto.getId(), listaBuses);
            responseTodosOnibusNoPonto.put(ponto.getId(), listaResponse);
            matrizResponsePonto.put(ponto.getId(), mapa);
            
        }
        
        Date termino = Calendar.getInstance().getTime();
        System.out.println("tempo = "+(termino.getTime() - inicio.getTime()));
        
    }
    
    private void alterarLinhaOnibus(Bus bus, Linha nova) {
        Linha anterior = bus.pegarLinha();
        System.out.println("anterior = "+anterior.getIdLinha()+", nova = "+nova.getIdLinha());
        if(anterior.getIdLinha() != nova.getIdLinha()) {
            linhasNoPonto.clear();
            responseTodosOnibusNoPonto.clear();
            matrizResponsePonto.clear();
            mapaOnibusLinha.put(bus.getId(), nova);
            listaOnibus.get(anterior.getIdLinha()).remove(bus);
            listaOnibus.get(nova.getIdLinha()).add(bus);
            bus.setLinha(nova);
            System.out.println("ta aqui1");
            configurarPontos();
            System.out.println("ta aqui5");
        }        
    }
    
    public void atualizarTemposSistema() {
        Iterator<Bus> iteradorOnibus = mapaOnibus.values().iterator();
        while(iteradorOnibus.hasNext()) {
            Bus b = iteradorOnibus.next();
            b.atualizarCrowdness();
        }
        
        Iterator<HashMap<Integer, ArrayList<ResponsePonto>>> iteradorPontos = matrizResponsePonto.values().iterator();
        
        while(iteradorPontos.hasNext()) {            
            HashMap<Integer, ArrayList<ResponsePonto>> mapa = iteradorPontos.next();
            Iterator<ArrayList<ResponsePonto>> iteradorLinhas = mapa.values().iterator();    

            while(iteradorLinhas.hasNext()) {                
                ArrayList<ResponsePonto> listaMatriz = iteradorLinhas.next();
                Iterator<ResponsePonto> iteradorResponse = listaMatriz.iterator();
                while(iteradorResponse.hasNext()) {
                    ResponsePonto resp = iteradorResponse.next();
                    resp.atualizar();
                }
                Collections.sort(listaMatriz);
            }            
        }
        
        Iterator<ArrayList<ResponsePonto>> iteradorLinhas2 = responseTodosOnibusNoPonto.values().iterator();
        while(iteradorLinhas2.hasNext()) {
            ArrayList<ResponsePonto> lista = iteradorLinhas2.next();
            Collections.sort(lista);
        }
        
        
    }
    
    @GET    // retorna a linha do onibus {id}
    @Path("/bus/{id}/linha")    
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Linha getLinhaBusById(@PathParam("id") @Valid IntParam id)
    {
        return mapaOnibusLinha.get(id);
    }
    
    @GET    // retorna a linha do onibus {id}
    @Path("/todas_linhas")    
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public LinhaList getLinhaBusById()
    {
        return new LinhaList(linhasOrdenadas);
    }
    
    @GET    // retorna o onibus {id}
    @Path("/bus/{id}")    
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Bus getPosicaoBusById(@PathParam("id") @Valid IntParam id)
    {
        return mapaOnibus.get(id);
    }
    
    @GET    // retorna todos os onibus na linha {id}
    @Path("/linha/{id}")    
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public BusList getOnibusNaLinha(@PathParam("id") @Valid IntParam id)
    {
        //System.out.println(listaOnibus.toString());    
        if(id.get() == 0) return new BusList(new ArrayList<Bus>(mapaOnibus.values()));
        return new BusList(listaOnibus.get(id.get()));
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
        Collections.sort(responseTodosOnibusNoPonto.get(idPonto));
        if(idLinha == 0) return new ResponsePontoList(responseTodosOnibusNoPonto.get(idPonto));
        return new ResponsePontoList(matrizResponsePonto.get(idPonto).get(idLinha));
    }
    
    @GET    
    @Path("/crowd/{idBus}/{value}")    
    @Consumes(MediaType.APPLICATION_JSON)
    public void teste(@PathParam("idBus") @Valid IntParam idBus,
            @PathParam("value") @Valid IntParam value) {
        
        int crowd = value.get();
        int idOnibus = idBus.get();
        if(crowd >= 1 && crowd <= 5) {
            if(mapaOnibus.containsKey(idOnibus)) {
                Bus bus = mapaOnibus.get(idOnibus);
                bus.adicionarCrowdness(crowd);
            }
        }

    }
    
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
        int idLinha = varIdLinha.get();
        if(idLinha == 0) return new PontoList(new ArrayList<Ponto>(listaPontos.values()));
        return new PontoList(mapaLinha.get(idLinha).pegarPontosNaLinha());
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
        
        mapaOnibus.get(id.get()).setPosicaoAtual(latitude,longitude);
        
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
        
        if(mapaLinha.containsKey(idLinha.get())) {
            Linha l = mapaLinha.get(idLinha.get());
            if(mapaOnibus.containsKey(idBus.get())) {
                Bus b = mapaOnibus.get(idBus.get());            
                alterarLinhaOnibus(b, l);
            }
            else {
                Bus b = new Bus(idBus.get(), l);
                adicionarBus(b);
                linhasNoPonto.clear();
                responseTodosOnibusNoPonto.clear();
                matrizResponsePonto.clear();
                configurarPontos();
            }
        }
        
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
