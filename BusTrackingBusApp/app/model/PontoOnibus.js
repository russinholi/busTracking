Ext.define('BusTrackingBusApp.model.PontoOnibus', {
    extend: 'Ext.data.Model',
    config: {
        idProperty: 'id',
        fields: [
            { name: 'id', type: 'int' },
           // { name: 'multimidia', type: 'string' },
            { name: 'latitude', type: 'double' },
            { name: 'longitude', type: 'double' }
        ],
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

    getLocation: function() {
        var position = new google.maps.LatLng (this.get('latitude'),this.get('longitude'));
        return position;
    },

    getId: function() {
        return this.get('id');
    }
});
