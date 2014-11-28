Ext.define('BusTrackingBusApp.view.MyMap', {
	extend: 'Ext.Map',
 	config: {
 		latitude: '-23.4422695',
 		longitude: '-51.9342396',
 		marker: '',
  		mapOptions: {
   			center: new google.maps.LatLng(-23.4422695,-51.9342396),
        mapTypeControl: false,
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        minZoom: 13,
        overviewMapControl: false,
        streetViewControl: false,
        zoom: 15,
        zoomControl: false,
        styles: [
            {
                featureType: "poi",
                elementType: "all",
                stylers: [
                      { visibility: "off" }
                ]
            }
        ]
  		}
 	},

  andarMarcador: function() {
    //alert('ta aquiiii1');
    var latlong = new google.maps.LatLng (this.get('latitude'),this.get('longitude'));
    
    //alert('ta aquiiii2');
    //alert(marker.getPosition());
    marker.setPosition(latlong);;
    //alert(marker.getPosition());
    //alert('ta aquiiii3');
    //alert('latitude MyMap.js = ' + this.get('latitude') + ' longitude = ' + this.get('longitude'));
    this.getMap().panTo(latlong);
    
    //alert('ta aquiiii4');
    
  },

  andarMarcadorUsuario: function() {
    //alert('ta aquiiii1');
    var latlong = new google.maps.LatLng (this.get('latitude'),this.get('longitude'));    
    //alert('ta aquiiii2');
    //alert(marker.getPosition());
    marker.setPosition(latlong);;
    //alert(marker.getPosition());
    //alert('ta aquiiii3');
    //alert('latitude MyMap.js = ' + this.get('latitude') + ' longitude = ' + this.get('longitude'));
  
    //alert('ta aquiiii4');
    
  },

  verPosicaoUsuario: function() {
    this.getMap().setZoom(16);
    this.getMap().panTo(new google.maps.LatLng (this.get('latitude'),this.get('longitude')));
  },
 	
 	initialize: function() {
 		 //alert('aquiii1');
   		var gMap = this.getMap();

   		//alert('aquiii2');
   
   		// add traffic layer
   		var trafficLayer = new google.maps.TrafficLayer();
   		trafficLayer.setMap(gMap);
   
   		//alert('aquiii3');
   
   		// drop map marker
   		marker = new google.maps.Marker({
    		map: gMap,
    		animation: google.maps.Animation.DROP,
    		//position: new google.maps.LatLng (-23.421035,-51.933817999999995),
    		position: new google.maps.LatLng (-23.421035,-51.933817999999995),
        	//draggable: true,
    		icon: 'resources/icons/bus.png'
   		});

      //this.andarMarcador();
 	}

 	



});