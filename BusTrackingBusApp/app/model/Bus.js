Ext.define('BusTrackingBusApp.model.Bus', {
    extend: 'Ext.data.Model',
    config: {
        //idProperty: 'id',
        fields: [
            { name: 'id', type: 'int' },
            { name: 'nome', type: 'string' },
            { name: 'latitudeAtual', type: 'auto' },
            { name: 'longitudeAtual', type: 'auto' },
            { name: 'crowdness', type: 'int'}
            //{ name: 'linhaAtual', type: 'BusTrackingBusApp.model.Linha'}
        ]
    },


    initialize: function() {
        
    },

    getLocation: function() {
        var position = new google.maps.LatLng (this.get('latitudeAtual'),this.get('longitudeAtual'));
        return position;
    },

    getId: function() {
        return this.get('id');
    },

    getNome: function() {
        return this.get('nome');
    },

    getCrowdness: function() {
        return this.get('crowdness');
    }

});
