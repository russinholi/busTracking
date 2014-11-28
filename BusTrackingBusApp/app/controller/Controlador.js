Ext.define('BusTrackingBusApp.controller.Controlador', {
    extend: 'Ext.app.Controller',

    /*config : {
        statics: {
            stopWatchInt: undefined        
        }
 
    },*/

    launch: function()
    {        
       console.log('teste aqui');
       // this.startTimer(this,10000);
          
        //postControl = TimeStamp.app.getController('PostList');         
    },


    startTimerMapa: function(me,delayTime) 
    {
        console.log('teste aqui0');
        setInterval(function() {
                me.atualizarMapa();
            }, delayTime); //self.delay
    }

});