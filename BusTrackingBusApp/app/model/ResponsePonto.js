Ext.define('BusTrackingBusApp.model.ResponsePonto', {
    extend: 'Ext.data.Model',
    config: {
        //idProperty: 'id',
        fields: [
            { name: 'idPonto', type: 'int' },
            { name: 'idOnibus', type: 'int' },
            { name: 'linha', type: 'string' },
            { name: 'tempo', type: 'int' },
            { name: 'crowdness', type: 'int' }
        ]
    },


    initialize: function() {
        
    },

    getIdPonto: function() {
        return this.get('idPonto');
    },

    getIdOnibus: function() {
        return this.get('idOnibus');
    },

    getLinha: function() {
        return this.get('linha');
    },

    getTempo: function() {
        return this.get('tempo');
    },

    getCrowdness: function() {
        return this.get('crowdness');
    }

});
