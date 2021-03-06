package core;


import core.utils.WSBusProperties;
import io.dropwizard.Configuration;
import io.dropwizard.jetty.HttpConnectorFactory;
import io.dropwizard.server.DefaultServerFactory;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Felipe Cousin
 */
public class WSBusConfiguration extends Configuration{
    
	public WSBusConfiguration() {
		WSBusProperties properties = WSBusProperties.getInstance();
		DefaultServerFactory serverFactory = (DefaultServerFactory) getServerFactory();
		
		HttpConnectorFactory appConnectorFactory = (HttpConnectorFactory) serverFactory.getApplicationConnectors().get(0);
		appConnectorFactory.setPort(properties.getAppConectorPort());
		
		HttpConnectorFactory admConnectorFactory = (HttpConnectorFactory) serverFactory.getAdminConnectors().get(0);
		admConnectorFactory.setPort(properties.getAdmConectorPort());
		
	}
}
