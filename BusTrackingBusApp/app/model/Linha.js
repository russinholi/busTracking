Ext.define('BusTrackingBusApp.model.Linha', {
    extend: 'Ext.data.Model',
    config: {
        idProperty: 'id',
        fields: [
            { name: 'id', type: 'int' },
           // { name: 'multimidia', type: 'string' },
            { name: 'nome', type: 'string' },
            { name: 'polyline', type: 'undefined'},
            { name: 'objetoPolyline', type: 'undefined'}

        ]
        /*validations: [
            {type: 'presence', field: 'multimidia', message:"Enter multimidia"},
            {type: 'presence', field: 'isVideo', message:"Enter isVideo"} ,
            {type: 'presence', field: 'title', message:"Enter title"},
            {type: 'presence', field: 'scope', message:"Enter scope"} ,
            {type: 'presence', field: 'radius', message:"Enter radius"},
            {type: 'presence', field: 'coorLat', message:"Enter coorLat"} ,
            {type: 'presence', field: 'coorLong', message:"Enter coorLong"},
            {type: 'presence', field: 'datePost', message:"Enter datePost"} ,
            {type: 'presence', field: 'userPost', message:"Enter userPost"}
        ],*/
    },

    getId: function() {
        return this.get('id');
    },

    getNome: function() {
        return this.get('nome');
    },

    getPolyline: function() {
        return this.get('polyline');
    },

    setObjetoPolyline: function(value) {
        return this.set('objetoPolyline', value);
    }
});
