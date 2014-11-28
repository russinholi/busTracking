Ext.define('BusTracking.store.BusStopStore', {
    extend : 'Ext.data.Store',

    alias : 'store.busstore',
    requires : ['BusTracking.model.PontoOnibus'],

    config : {
        model : 'BusTracking.model.PontoOnibus',
       /* data: [
            { id: '1',    multimidia: 'https://i.ytimg.com/vi/K7UiEEjpf4Y/maxresdefault.jpg', isVideo: false, title: 'Bebados se Batendo', description: ' descricao', scope: 0, radius: 500, coorLat:-18.4079 , coorLong:-58.4079 , datePost: '2014-8-21 10:34:00 AM' , likes: 260, unlikes: 2, numberComments: 20, views: 500,   userPost: 'chuck.jpg'},
            { id: '2',    multimidia: 'https://i.ytimg.com/vi/K7UiEEjpf4Y/maxresdefault.jpg', isVideo: false, title: 'Renata Ingrata Trocou o meu Amor por um Preto', description: ' descricao', scope: 0, radius: 500, coorLat:-18.4079 , coorLong:-58.4079 , datePost: '2014-8-23 10:34:00 AM' , likes: 26, unlikes: 30, numberComments: 20, views: 500,   userPost: 'chuck.jpg'},
            { id: '3',    multimidia: 'https://i.ytimg.com/vi/K7UiEEjpf4Y/maxresdefault.jpg', isVideo: false, title: 'Bebados se Batendo', description: ' descricao', scope: 0, radius: 500, coorLat:-18.4079 , coorLong:-58.4079 , datePost: '2014-8-2 10:34:00 AM' , likes: 26, unlikes: 2, numberComments: 200, views: 500,   userPost: 'chuck.jpg'},
            { id: '4',    multimidia: 'https://i.ytimg.com/vi/K7UiEEjpf4Y/maxresdefault.jpg', isVideo: false, title: 'Bebados se Batendo', description: ' descricao', scope: 0, radius: 500, coorLat:-18.4079 , coorLong:-58.4079 , datePost: '2012-4-12 10:34:00 PM' , likes: 26, unlikes: 2, numberComments: 20, views: 500,   userPost: 'chuck.jpg'}
           
            
        ],*/
        autoLoad : false,
        proxy: {
            type: "ajax",
            useDefaultXhrHeader : false,
            withCredentials: true,
            //url : 'http://localhost:8080/linha/',//"http://192.168.0.10:8080/post?longitude=-58.4079&latitude=-18.4079", ?longitude=-58.4079&latitude=-18.4079
            //callbackKey: 'callback',            
            reader: {
                type: "json",
                rootProperty: "pontos"
            }
        }       
    }
});