package core.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import Geocoding.GeoCalculator;
import Geocoding.GeoPoint;
import Geocoding.PolylineEncoder;
import Geocoding.RouteBoxer.LatLng;
import Geocoding.RouteBoxer.LatLngBounds;
import Geocoding.RouteBoxer.RouteBoxer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Felipe Cousin
 */
@Document
public class Linha implements Comparable<Linha> {
    
	@Id
	private int id;
    private String nome;
    private List<Bus> listaOnibus = new ArrayList<Bus>();
    private List<Ponto> rota = new ArrayList<Ponto>();
    private List<Ponto> pontosNaLinha = new ArrayList<Ponto>();
    private List<Integer> distancias = new ArrayList<Integer>();
    private List<Integer> tempos = new ArrayList<Integer>();
    private String polyline = "";
    private HashMap<Integer, ArrayList<LatLngBounds>> listaBounds = new HashMap<Integer, ArrayList<LatLngBounds>>();

    public Linha(){}
    
    public Linha(String nome, int id, List<Ponto> rota) {
        this.nome = nome;
        this.id = id;
        this.rota = rota;
        Ponto anterior = null;
        int i = -1;
        Integer[] resposta;
        RouteBoxer rb = new RouteBoxer();
        ArrayList<GeoPoint> listaGeo = new ArrayList<GeoPoint>();        
        for (Ponto prox : rota) {
            if(i >= 0) {
                resposta = GeoCalculator.getDistanciaTempo(anterior.getAddress(), prox.getAddress());
                distancias.add(i, resposta[0]);
                tempos.add(i, resposta[1]);
//                distancias.add(i, 200);
//                tempos.add(i, 45);
                String polyTrecho = GeoCalculator.getPolylineDestino(anterior.getAddress(), prox.getAddress());
                ArrayList<GeoPoint> listaGeo2 = PolylineEncoder.decode(polyTrecho, 10);
                listaGeo.addAll(listaGeo2);
                ArrayList<LatLngBounds> bounds = (ArrayList<LatLngBounds>) rb.box(rb.decodePath(polyTrecho), 20); // 0.01 milhas, ou 16 metros
                listaBounds.put(anterior.getId(), bounds);
            }
            anterior = prox;
            i++;
            if(prox.isPontoDeOnibus()) {
            	pontosNaLinha.add(prox);
            	prox.setLinhaId(this.id);
            }
            
        }
        if(rota.size()>1) {
            resposta = GeoCalculator.getDistanciaTempo(anterior.getAddress(), rota.get(0).getAddress());
            distancias.add(i, resposta[0]);
            tempos.add(i, resposta[1]);
//            distancias.add(i, 200);
//            tempos.add(i, 45);
            String polyTrecho = GeoCalculator.getPolylineDestino(anterior.getAddress(), rota.get(0).getAddress());
            ArrayList<GeoPoint> listaGeo2 = PolylineEncoder.decode(polyTrecho, 10);
            listaGeo.addAll(listaGeo2);
            ArrayList<LatLngBounds> bounds = (ArrayList<LatLngBounds>) rb.box(rb.decodePath(polyTrecho), 20); // 0.01 milhas, ou 16 metros
            listaBounds.put(anterior.getId(), bounds);
            //polyline = GeoCalculator.getPolyline(rota.get(0).getAddress(), waypoints);        
        }
        polyline = PolylineEncoder.encode(listaGeo, 10);
//        System.out.println(polyline);
        
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public List<Ponto> getPontosNaLinha() {
        return pontosNaLinha;
    }
    
    public boolean pontoOnibusEstaNaLinha(Ponto p) {
        return pontosNaLinha.contains(p);
    }

    public int calcularTempo(Bus b, Ponto ponto) {
        int tempoProximoPonto;
        Ponto p = b.pegarUltimoPonto();
        if(b.dadosAtualizados()) {
            tempoProximoPonto = b.pegarTempoProximoPonto();
            int ind = rota.indexOf(p) + 1;
            if(ind >= rota.size()) ind = 0;
            p = rota.get(ind);
        }
        else {        
            LatLng posicaoOnibus = new LatLng(b.getLatitudeAtual(), b.getLongitudeAtual());
            int indexFinal = rota.indexOf(p);
            int indice = rota.indexOf(p);
            Ponto anterior;

            boolean achou = false;
            do {
                ArrayList<LatLngBounds> b1 = listaBounds.get(p);
                if(b1 == null) {
                    System.out.println("ta aqui");
                }
                Iterator<LatLngBounds> iterator = b1.iterator();
                while(iterator.hasNext() && !achou) {
                    LatLngBounds bound = iterator.next();
                    achou = bound.contains(posicaoOnibus);
                }
                indice++;
                if(indice >= rota.size()) indice = 0;
                anterior = p;
                p = rota.get(indice);
            } while(!achou && indice != indexFinal);

            if(!achou) return -1;
            
            // achou qual trecho esta, vai requisitar o tempo até o próximo ponto
            
            //Integer[] distTempo = GeoCalculator.getDistanciaTempo(b.getLatitudeAtual()+","+b.getLongitudeAtual(), p.getAddress());
            //tempoProximoPonto = distTempo[1];
            tempoProximoPonto = 35;
            
            b.setTempoProximoPonto(tempoProximoPonto, anterior);
        }
        
        int indexInicial = rota.indexOf(p);
        boolean achou = p.equals(ponto);
        long tempoTotal = tempoProximoPonto;
        
        while(!achou) {
            tempoTotal += tempos.get(indexInicial);
            indexInicial++;
            if(indexInicial >= tempos.size()) indexInicial = 0;
            if(rota.get(indexInicial).equals(ponto)) achou = true;
            else if(rota.get(indexInicial).isPontoDeOnibus()) tempoTotal += 30; //definir aqui quantos segundos!            
        }
        
        int retorno = Math.round(tempoTotal / 60);
        return retorno;
        
        //listaBounds.get(0).get(0).contains(null)
        //return (int) (Math.random()*15);
    }

    @Override
    public int compareTo(Linha other) {
        return this.getNome().compareToIgnoreCase(other.getNome());
    }
    
    public String getPolyline() {
        return polyline;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int)id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Linha other = (Linha) obj;
		if (id != other.id)
			return false;
		return true;
	}
    
    public void adicionarOnibus(Bus onibus) {
    	this.listaOnibus.add(onibus);
    	onibus.setLinhaId(this);
    }

    public List<Bus> getListaOnibus() {
    	return listaOnibus;
    }
}
