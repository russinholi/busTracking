package core;


import Geocoding.GeoCalculator;
import Geocoding.GeoPoint;
import Geocoding.PolylineEncoder;
import Geocoding.RouteBoxer.LatLng;
import Geocoding.RouteBoxer.LatLngBounds;
import Geocoding.RouteBoxer.RouteBoxer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Felipe Cousin
 */
public class Linha implements Comparable<Linha> {
    
    private String nome;
    private int idLinha;
    private ArrayList<Ponto> rota = new ArrayList<Ponto>();
    private ArrayList<Ponto> pontosNaLinha = new ArrayList<Ponto>();
    private ArrayList<Integer> distancias = new ArrayList<Integer>();
    private ArrayList<Integer> tempos = new ArrayList<Integer>();
    private String polyline = "";
    private HashMap<Ponto, ArrayList<LatLngBounds>> listaBounds = new HashMap<Ponto, ArrayList<LatLngBounds>>();

    public Linha(String nome, int idLinha, ArrayList<Ponto> rota) {
        this.nome = nome;
        this.idLinha = idLinha;
        this.rota = rota;
        Ponto anterior = null;
        int i = -1;
        Iterator<Ponto> iterador = rota.iterator();
        Integer[] resposta;
        RouteBoxer rb = new RouteBoxer();
        ArrayList<GeoPoint> listaGeo = new ArrayList<GeoPoint>();        
        while(iterador.hasNext()) {
            Ponto prox = iterador.next();
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
                listaBounds.put(anterior, bounds);
            }
            anterior = prox;
            i++;
            if(prox.isPontoDeOnibus()) pontosNaLinha.add(prox);
            
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
            listaBounds.put(anterior, bounds);
            //polyline = GeoCalculator.getPolyline(rota.get(0).getAddress(), waypoints);        
        }
        polyline = PolylineEncoder.encode(listaGeo, 10);
        System.out.println(polyline);
        
    }

    public String getNome() {
        return nome;
    }

    public int getIdLinha() {
        return idLinha;
    }

    public ArrayList<Ponto> pegarPontosNaLinha() {
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

    
}
