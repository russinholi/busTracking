/*
    This file is generated and updated by Sencha Cmd. You can edit this file as
    needed for your application, but these edits will have to be merged by
    Sencha Cmd when it performs code generation tasks such as generating new
    models, controllers or views and when running "sencha app upgrade".

    Ideally changes to this file would be limited and most work would be done
    in other places (such as Controllers). If Sencha Cmd cannot merge your
    changes and its generated code, it will produce a "merge conflict" that you
    will need to resolve manually.
*/

Ext.application({
    name: 'BusTracking',

    requires: [
        'Ext.MessageBox',
        'Ext.Map',
        'Ext.Ajax',
        'Ext.util.JSONP',
        'BusTracking.utils.Global'
    ],

    views: [
        'Main',
        'MyMap'
    ],

    stores: [
        'BusStore',
        'BusStopStore',
        'ResponsePontoStore',
        'LinhaStore'
    ],

    models: [
        'Bus',
        'Linha',
        'PontoOnibus',
        'ResponsePonto'
    ],


    icon: {
        '57': 'resources/icons/Icon.png',
        '72': 'resources/icons/Icon~ipad.png',
        '114': 'resources/icons/Icon@2x.png',
        '144': 'resources/icons/Icon~ipad@2x.png'
    },

    isIconPrecomposed: true,

    startupImage: {
        '320x460': 'resources/startup/320x460.jpg',
        '640x920': 'resources/startup/640x920.png',
        '768x1004': 'resources/startup/768x1004.png',
        '748x1024': 'resources/startup/748x1024.png',
        '1536x2008': 'resources/startup/1536x2008.png',
        '1496x2048': 'resources/startup/1496x2048.png'
    },

    

    onUpdated: function() {
        Ext.Msg.confirm(
            "Application Update",
            "This application has just successfully been updated to the latest version. Reload now?",
            function(buttonId) {
                if (buttonId === 'yes') {
                    window.location.reload();
                }
            }
        );
    },

    launch: function() {
        // Destroy the #appLoadingIndicator element
        Ext.fly('appLoadingIndicator').destroy();

        //var baseURL = "felipecousin.ddns.net:8080";
        var baseURL = "localhost:9090";

        var markersArray = [];
        BusTracking.utils.Global.setMarkers(markersArray);



        // Initialize the main view
        //Ext.Viewport.add(Ext.create('BusTracking.view.Main'));

        //alert('teste');

        //if(Ext.browser.is.PhoneGap) {
            /*navigator.camera.getPicture(onSuccess, onFail, { quality : 50,
                      destinationType : Camera.DestinationType.FILE_URI,
                      sourceType : Camera.PictureSourceType.CAMERA,
                      allowEdit : true,
                      encodingType: Camera.EncodingType.JPEG,
                      targetWidth: 100,
                      targetHeight: 100,
                      popoverOptions: CameraPopoverOptions,
                      correctOrientation: true,
                      saveToPhotoAlbum: true };

            function onSuccess(imageData) {
                var image = document.getElementById('myImage');
                image.src = "data:image/jpeg;base64," + imageData;
            }

            function onFail(message) {
                alert('Failed because: ' + message);
            }*/

            //Ext.Viewport.setMasked({xtype:'loadmask', message:'Please Wait...'});

            //alert('teste');

            // Sets the map on all markers in the array.
            var setAllMap = function(markers, map) {
                for (var i = 0; i < markers.length; i++) {
                    markers[i].setMap(map);
                }
            }

            // Removes the markers from the map, but keeps them in the array.
            var clearMarkers = function(markers) {
                setAllMap(markers, null);
            }

            // Shows any markers currently in the array.
            var showMarkers = function(markers) {
                var mapa = BusTracking.utils.Global.getMapa().getMap();
                setAllMap(markers, mapa);
            }

            // Deletes all markers in the array by removing references to them.
            var deleteMarkers = function() {
                var markers = BusTracking.utils.Global.getMarkers();
                clearMarkers(markers);
                var markersEmpty = [];
                BusTracking.utils.Global.setMarkers(markersEmpty);
            }

            var atualizarMapa = function() {

                var busStore = Ext.create('BusTracking.store.BusStore');
                busStore.getProxy().setUrl('http://'+baseURL+'/get/linha/'+BusTracking.utils.Global.getLinhaSelecionadaId());
                var mapa = BusTracking.utils.Global.getMapa().getMap();

                // busStore.load();
                // busStore.sync();
                busStore.load(function(records, operation, success) 
                    {
                     //  alert('loaded records: '+success);
                        deleteMarkers();
                        var markers = [];
                        busStore.each(function (item, index, length) {
                            // item.andarMarcador();
                            var location = item.getLocation();
                            var name = item.getNome();
                            var string = '<table width=220 height=60><tr><td>'+name+'<br><a href="http://'+baseURL+'/get/crowd/'
                                            +item.getId()+'/1"><img src="resources/icons/crowd'+(item.getCrowdness()-1)+'.png"></a> <a href="http://'+baseURL+'/get/crowd/'
                                            +item.getId()+'/2"><img src="resources/icons/crowd'+(item.getCrowdness()-2)+'.png"></a> <a href="http://'+baseURL+'/get/crowd/'
                                            +item.getId()+'/3"><img src="resources/icons/crowd'+(item.getCrowdness()-3)+'.png"></a> <a href="http://'+baseURL+'/get/crowd/'
                                            +item.getId()+'/4"><img src="resources/icons/crowd'+(item.getCrowdness()-4)+'.png"></a> <a href="http://'+baseURL+'/get/crowd/'
                                            +item.getId()+'/5"><img src="resources/icons/crowd'+(item.getCrowdness()-5)+'.png"></a></td></tr>';
                            //console.log(location);
                            var infowindow = new google.maps.InfoWindow({
                                content: string
                            });
                            var marker = new google.maps.Marker({
                                position: location,
                                icon: 'resources/icons/bus.png',
                                title: name
                            });
                            google.maps.event.addListener(marker, 'click', function() {
                                infowindow.open(mapa, marker);
                            });
                            markers.push(marker);
                        });
                        BusTracking.utils.Global.setMarkers(markers);
                        showMarkers(markers);
                    }, this);

                //console.log(busStore);
            };

            var atualizarPontos = function() {

                var busStopStore = Ext.create('BusTracking.store.BusStopStore');
                var idLinhaSelecionada = BusTracking.utils.Global.getLinhaSelecionadaId();
                busStopStore.getProxy().setUrl('http://'+baseURL+'/get/pontos_linha/'+idLinhaSelecionada);
                var mapa = BusTracking.utils.Global.getMapa().getMap();

                // busStore.load();
                // busStore.sync();
                busStopStore.load(function(records, operation, success) 
                    {
                     //  alert('loaded records: '+success);
                        var markersParadasOld = BusTracking.utils.Global.getMarkersParadas();
                        clearMarkers(markersParadasOld);
                        var markersParadas = [];
                        busStopStore.each(function (item, index, length) {
                            // item.andarMarcador();
                            var location = item.getLocation();
                            //console.log(location);                            
                            var marker = new google.maps.Marker({
                                position: location,
                                icon: 'resources/icons/paradaOnibus.png',
                                title: 'Bus Stop '+item.getId()
                            });
                            google.maps.event.addListener(marker, 'click', function() {
                                var responseStore = Ext.create('BusTracking.store.ResponsePontoStore');
                                responseStore.getProxy().setUrl('http://'+baseURL+'/get/ponto/'+item.getId()+'/linha/'+idLinhaSelecionada);

                                // busStore.load();
                                // busStore.sync();
                                
                                responseStore.load(function(records, operation, success) 
                                    {
                                        var string = '<table width=220 height=30><tr><td valign=top>Bus Stop '+item.getId()+'<br>';
                                        responseStore.each(function (resp, index, length) {
                                            // item.andarMarcador();
                                            //console.log('aqui passou');
                                            //console.log(string);
                                            if(resp.getTempo() < 0) string += '<br><b>FORA DA ROTA!</b>';
                                            else string += '<br><b>'+resp.getTempo()+'mins</b>';

                                            string += 'Onibus '+resp.getIdOnibus()+' - '+resp.getLinha()+'<br><a href="http://'+baseURL+'/get/crowd/'
                                            +resp.getIdOnibus()+'/1"><img src="resources/icons/crowd'+(resp.getCrowdness()-1)+'.png"></a> <a href="http://'+baseURL+'/get/crowd/'
                                            +resp.getIdOnibus()+'/2"><img src="resources/icons/crowd'+(resp.getCrowdness()-2)+'.png"></a> <a href="http://'+baseURL+'/get/crowd/'
                                            +resp.getIdOnibus()+'/3"><img src="resources/icons/crowd'+(resp.getCrowdness()-3)+'.png"></a> <a href="http://'+baseURL+'/get/crowd/'
                                            +resp.getIdOnibus()+'/4"><img src="resources/icons/crowd'+(resp.getCrowdness()-4)+'.png"></a> <a href="http://'+baseURL+'/get/crowd/'
                                            +resp.getIdOnibus()+'/5"><img src="resources/icons/crowd'+(resp.getCrowdness()-5)+'.png"></a>';
                                        });
                                        string += '</td></tr></table>';
                                        var infowindow = new google.maps.InfoWindow({
                                            content: string
                                        });
                                        infowindow.open(mapa, marker);
                                    }, this);

                                
                            });
                            var idParada = item.getId();
                            markersParadas.push(marker);
                        });
                        BusTracking.utils.Global.setMarkersParadas(markersParadas);
                        showMarkers(markersParadas);
                    }, this);
                

            };

            var adicionarPontos = function() {

                var busStopStore = Ext.create('BusTracking.store.BusStopStore');
                busStopStore.getProxy().setUrl('http://'+baseURL+'/get/pontos_linha/0');
                var mapa = BusTracking.utils.Global.getMapa().getMap();
                var idLinhaSelecionada = BusTracking.utils.Global.getLinhaSelecionadaId();                

                


                // busStore.load();
                // busStore.sync();
                busStopStore.load(function(records, operation, success) 
                    {
                     //  alert('loaded records: '+success);
                        var markersParadas = [];
                        busStopStore.each(function (item, index, length) {
                            // item.andarMarcador();
                            var location = item.getLocation();
                            //console.log(location);                            
                            var marker = new google.maps.Marker({
                                position: location,
                                icon: 'resources/icons/paradaOnibus.png',
                                title: 'Bus Stop 2'
                            });
                            google.maps.event.addListener(marker, 'click', function() {
                                var responseStore = Ext.create('BusTracking.store.ResponsePontoStore');
                                responseStore.getProxy().setUrl('http://'+baseURL+'/get/ponto/'+item.getId()+'/linha/0');

                                // busStore.load();
                                // busStore.sync();
                                
                                responseStore.load(function(records, operation, success) 
                                    {
                                        var string = 'Bus Stop '+item.getId()+'<br>';
                                        responseStore.each(function (resp, index, length) {
                                            // item.andarMarcador();
                                            //console.log('aqui passou');
                                            //console.log(string);

                                            string += '<br><b>'+resp.getTempo()+'mins</b> Onibus '+resp.getIdOnibus()+' / '+resp.getLinha()+'<br><a href="http://'+baseURL+'/get/crowd/'+resp.getIdOnibus()+'/1"><img src="resources/icons/crowd'+(resp.getCrowdness()-1)+'.png"></a> <a href="http://'+baseURL+'/get/crowd/'+resp.getIdOnibus()+'/2"><img src="resources/icons/crowd'+(resp.getCrowdness()-2)+'.png"></a> <a href="http://'+baseURL+'/get/crowd/'+resp.getIdOnibus()+'/3"><img src="resources/icons/crowd'+(resp.getCrowdness()-3)+'.png"></a> <a href="http://'+baseURL+'/get/crowd/'+resp.getIdOnibus()+'/4"><img src="resources/icons/crowd'+(resp.getCrowdness()-4)+'.png"></a> <a href="http://'+baseURL+'/get/crowd/'+resp.getIdOnibus()+'/5"><img src="resources/icons/crowd'+(resp.getCrowdness()-5)+'.png"></a>';
                                        });
                                        var infowindow = new google.maps.InfoWindow({
                                            content: string
                                        });
                                        infowindow.open(mapa, marker);
                                    }, this);

                                
                            });
                            var idParada = item.getId();
                            markersParadas.push(marker);
                        });
                        BusTracking.utils.Global.setMarkersParadas(markersParadas);
                        showMarkers(markersParadas);
                    }, this);
                

            };

            var adicionarLinhas = function() {

                var linhaStore = Ext.create('BusTracking.store.LinhaStore');
                linhaStore.getProxy().setUrl('http://'+baseURL+'/get/todas_linhas');

                // busStore.load();
                // busStore.sync();
                linhaStore.listeners= {
         	   exception: function(proxy, response, operation) {
	                Ext.MessageBox.show({
        	            title: 'REMOTE EXCEPTION',
                	    msg: operation.getError(),
	                    icon: Ext.MessageBox.ERROR,
        	            buttons: Ext.Msg.OK
                	});
	            }
        	};
                linhaStore.load(function(records, operation, success) 
                    {
                       alert('loaded records: '+success);
                        var array = [];
                        array.push({text: 'Todas Linhas', value: {text: 'Todas Linhas', value: 0, polyline: undefined}});

                        linhaStore.each(function (item, index, length) {
                            // item.andarMarcador();                           

                            var flightPathCoordinates = [];
                            var index = 0;
                            var poly = item.getPolyline();
                            //var poly = "|~`nC`x~{HwIZsGVkI^wDLuAH??mDZ_BTeAVsCx@{DxAuAn@}FjDy@d@sA]a@`Ao@|@WTa@@}@^A@??WDaAFiDA{JI{A???qD@uDCyBAiE?yCCsAK_Ek@I???{SC}I?iCCg@Ae@B[LiAAaDEuAEwEo@??oCy@_D|MvIhC`D~@ENDJt@lCt@hDpA]dD}@hD_Aj@OXWf@EIa@eB}H{@oDcAsEOy@MmAGyABwBPeBVyAdBqHxDaPaHyB}@YK\yCxLsB|HhDdAxC`A??RJRHbCB`CBdB@rE?jFDhKBjDc@xAEfAG|A@t@???vFBdB?tDDzADrAA`BA|D@??rE@fA@d@Fj@Nv@^p@d@^h@Xf@X`ABJDA|Bu@??`Bg@??tEyAn@MPAHFNDN?TGhAe@VOLORYXQt@UlCw@jH_CI[sC~@iF`B??aCl@YFGEQGSAi@FeA\OLINCTDZJVPJPBNC`Aa@`@SNKNYVQXO`AYjGmB~@YzFiBz@Y??vAc@pBeAdCiAH@F@RAFELKDO@InDaApDi@|BI??hIG`DCbDExCE?[q@?iDB}@B??_B@?^z@AzBAZtF?B??d@nI{BRqF\yGX";
                            var len = poly.length;
                            var lat = 0;
                            var lng = 0;

                            while (index < len) {
                                var b;
                                var shift = 0;
                                var result = 0;
                                do {
                                    b = poly.charCodeAt(index++) - 63;
                                    result |= (b & 0x1f) << shift;
                                    shift += 5;
                                } while (b >= 0x20);

                                var dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                                lat += dlat;

                                shift = 0;
                                result = 0;
                                do {
                                    b = poly.charCodeAt(index++) - 63;
                                    result |= (b & 0x1f) << shift;
                                    shift += 5;
                                } while (b >= 0x20);

                                var dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                                lng += dlng;

                                var p = new google.maps.LatLng(((lat / 100000)),
                                        ((lng / 100000)));
                                flightPathCoordinates.push(p);
                            }

                            var flightPath = new google.maps.Polyline({
                                path: flightPathCoordinates,
                                strokeColor: "#0000FF",
                                strokeOpacity: 0.4,
                                strokeWeight: 3
                            });
                            //flightPath.getPath().push("|~`nC`x~{HwIZsGVkI^wDLcGd@_BTeAVsCx@{DxAuAn@}FjDy@d@sA]a@`Ao@|@WTa@@}@^GBg@HcBAaC?}FCcCCwF@qEEqG?eCAeAEuC]kBU{SC}I?iCCg@Ae@B[LiAAaDEuAEyDg@]GgA[gA]oAnFoAlFdAZjDdAtDfApA^ENJXn@~Bt@hDbD}@hHmBd@Jd@A|DElFD`FCfFFpAA|CNtBGfDAxJBzDBhG?fEBtE?~CCnBO@DFHPHVCXS~@}@tEgCzEqCnGoDlAg@vAe@lDo@~AQh@EhKa@vK]VAz@SRCn@I`BOfAOx@QQkESeECgBCgB");
                            //flightPath.getPath().push("|~`nC`x~{HwIZsGVkI^wDLuAHvx_nCl{~{HmDZ_BTeAVsCx@{DxAuAn@}FjDy@d@sA]a@`Ao@|@WTa@@}@^A@dn~mCho_|HWDaAFiDA{JI{A?hw}mCjo_|HqD@uDCyBAiE?yCCsAK_Ek@I?dt|mCjm_|H{SC}I?iCCg@Ae@B[LiAAaDEuAEwEo@|{zmCtk_|HoCy@_D|MvIhC`D~@ENDJt@lCt@hDpA]dD}@hD_Aj@OXWf@EIa@eB}H{@oDcAsEOy@MmAGyABwBPeBVyAdBqHxDaPaHyB}@YK\yCxLsB|HhDdAxC`Adn{mCdy~{HRJRHbCB`CBdB@rE?jFDhKBjDc@xAEfAG|A@t@?dd}mC~x~{HvFBdB?tDDzADrAA`BA|D@hc~mCly~{HrE@fA@d@Fj@Nv@^p@d@^h@Xf@X`ABJDA|Bu@|x~mCv__|H`Bg@~{~mCn~~{HtEyAn@MPAHFNDN?TGhAe@VOLORYXQt@UlCw@jH_CI[sC~@iF`B|n_nCpu~{HaCl@YFGEQGSAi@FeA\OLINCTDZJVPJPBNC`Aa@`@SNKNYVQXO`AYjGmB~@YzFiBz@Yfb`nCln~{HvAc@pBeAdCiAH@F@RAFELKDO@InDaApDi@|BIz}`nCxc~{HhIG`DCbDExCE?[q@?iDB}@BjnanClb~{H_B@?^z@AzBAZtF?B~qanCdk~{Hd@nI{BRqF\yGX
                            //flightPath.setMap(BusTracking.utils.Global.getMapa().getMap());

                            array.push({text: item.getNome(), value: {text: item.getNome(), value: item.getId(), polyline: flightPath}});
                       alert('linha: '+item.getNome());

                        });
                        var picker = Ext.create('Ext.Picker', {
                            doneButton: 'Confirmar',
                            cancelButton: false,
                            id: 'LinhaPicker',
                            slots: [
                                {
                                    name : 'linhaSelecionada',
                                    title: 'Linhas',
                                    data : array
                                }
                            ]
                        });
                        Ext.Viewport.add(picker);
                        BusTracking.utils.Global.setPicker(picker);
                        //picker.show();
                        picker.addListener('change', function(picker, value, eOpts) {

                            var idAnterior = BusTracking.utils.Global.getLinhaSelecionadaId();
                            if(idAnterior != 0) BusTracking.utils.Global.getLinhaSelecionadaPolyline().setMap(null);

                            BusTracking.utils.Global.setLinhaSelecionadaId(value.linhaSelecionada['value']);
                            BusTracking.utils.Global.setLinhaSelecionadaNome(value.linhaSelecionada['text']);
                            BusTracking.utils.Global.setLinhaSelecionadaPolyline(value.linhaSelecionada['polyline']);

                            var idSelecionado = BusTracking.utils.Global.getLinhaSelecionadaId();
                            if(idSelecionado != 0) BusTracking.utils.Global.getLinhaSelecionadaPolyline().setMap(BusTracking.utils.Global.getMapa().getMap());

                            BusTracking.utils.Global.getButton().setText(value.linhaSelecionada['text']);

                            atualizarPontos();
                            atualizarMapa();


                        });
                    }, this);

                
            }

            /*var trackingButton = Ext.create('Ext.Button', {
                iconCls: 'locate'
            });

            var trafficButton = Ext.create('Ext.Button', {
                pressed: true,
                iconCls: 'maps'
            });*/

            var button = Ext.create('Ext.Button', {
                id: 'def_linhas',
                text: 'Todas Linhas',
                width: '50%',
                handler: function() {
                    BusTracking.utils.Global.getPicker().show();
                }
            });

            BusTracking.utils.Global.setButton(button);

            var toolbar = Ext.create('Ext.Toolbar', {
                docked: 'top',
                ui: 'light',
                items: [
                    {
                        iconCls: 'locate',
                        handler: function() {
                            var mapa = BusTracking.utils.Global.getMapa();
                            mapa.verPosicaoUsuario();
                        }
                    },
                    {
                        xtype: 'spacer'
                    },
                    button
                ]
            });
            var mapa = Ext.create('BusTracking.view.MyMap');

            Ext.create('Ext.Panel', {
                fullscreen: true,
                layout: 'fit',
                items: [toolbar, mapa]
            });
            //alert('teste2');
            //Ext.Viewport.add(mapa);

            BusTracking.utils.Global.setLinhaSelecionadaId(0);
            BusTracking.utils.Global.setMapa(mapa);

            setInterval(function() {
                atualizarMapa();
            }, 10000);
            

            atualizarMapa();
            adicionarPontos();
            adicionarLinhas();

            
            
            //navigator.geolocation.getCurrentPosition(geolocationSuccess, geolocationError,{ maximumAge: 5000, timeout: 10000, enableHighAccuracy: true });
            
            setInterval(function() {
                navigator.geolocation.getCurrentPosition(geolocationSuccess, geolocationError,{ maximumAge: 5000, timeout: 10000, enableHighAccuracy: true });
            }, 5000);

            var geolocationSuccess = function(position) {
                //Ext.Viewport.setMasked(false);
                /*alert('Latitude: '          + position.coords.latitude          + '\n' +
                      'Longitude: '         + position.coords.longitude         + '\n' +
                      'Altitude: '          + position.coords.altitude          + '\n' +
                      'Accuracy: '          + position.coords.accuracy          + '\n' +
                      'Altitude Accuracy: ' + position.coords.altitudeAccuracy  + '\n' +
                      'Heading: '           + position.coords.heading           + '\n' +
                      'Speed: '             + position.coords.speed             + '\n' +
                      'Timestamp: '         + position.timestamp                + '\n');*/

                mapa.setLatitude(position.coords.latitude);
                mapa.setLongitude(position.coords.longitude);   

                //alert('latitude app.js = ' + position.coords.latitude + ' longitude = ' + position.coords.longitude);


                mapa.andarMarcadorUsuario();

                //mapa.andarMarcador();

                
                /*var post_json = { latitude: position.coords.latitude, longitude: position.coords.longitude } ;  
                
                Ext.util.JSONP.request({ 
                    url: 'http://'+baseURL+'/bus/setargps/1/'+position.coords.latitude+'/'+position.coords.longitude, 
                }); 

                var txt = ""+Ext.util.JSONP.request({url: 'http://'+baseURL+'/bus/1'});
                var decoded = Ext.JSON.decode(txt);

                var onibus = Ext.create('BusTracking.model.Bus', decoded);

                var string = ''+Ext.util.JSONP.request({url: 'http://'+baseURL+'/bus/1'});
                var array = string.split(';');

                alert(string);
                alert(array[0]);
                alert(array[1]);

                //alert(onibus.get('latitudeAtual'));
                */

                
            };

            // onError Callback receives a PositionError object
            //
            function geolocationError(error) {
                //alert('code: '    + error.code    + '\n' +
                //      'message: ' + error.message + '\n');
                //navigator.geolocation.getCurrentPosition(onSuccess, onErrorMessage,{ maximumAge: 3000, timeout: 10000, enableHighAccuracy: true });
            }

            

            //var watchId = navigator.geolocation.watchPosition(geolocationSuccess, geolocationError,{ maximumAge: 3000, timeout: 10000, enableHighAccuracy: true });



        //} 
    }
});
