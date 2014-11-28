Ext.define('BusTracking.utils.Global', {
    singleton : true,
    alias : 'widget.global',
    
    config : {
            mapa: undefined,
            markers: undefined,
            markersParadas: undefined,
            picker: undefined,
            linhaSelecionadaId: undefined,
            linhaSelecionadaNome: undefined,
            button: undefined,
            linhaSelecionadaPolyline: undefined
           // baseUrl : 'http://192.168.0.10:8080/'
        //   baseUrl : 'http://localhost:8080/'
        //   baseUrl : 'http://couto.ddns.net:8080/'
       //  baseUrl : 'http://timestampserver.ddns.net:8080/'
    },
    
    constructor: function(config) {
        this.initConfig(config);
        this.callParent([config]);
    }
});