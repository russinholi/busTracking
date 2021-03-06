package core;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.thetransactioncompany.cors.CORSFilter;

import core.model.Bus;
import core.utils.CVSImporter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Felipe Cousin
 */
public class WSBusApplication extends Application<WSBusConfiguration> {
    
    public Bus onibus;
    
    public static void main(String[] args) throws Exception {
        new WSBusApplication().run(args);
    }   

    @Override
    public void initialize(Bootstrap<WSBusConfiguration> btstrp) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run(WSBusConfiguration t, Environment environment) throws Exception {
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        FilterRegistration.Dynamic c = environment.servlets().addFilter(CORSFilter.class.getName(), new CORSFilter());
                      
        
        c.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
       // environment.servlets().addFilter(CORSFilter.class.getName(), "/*")
        
        
        c.setInitParameter("allowedOrigins", "*");
        c.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        c.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        
        c.setInitParameter("Access-Control-Max-Age", "3600");
        
        
        boolean shouldImportFile = Boolean.getBoolean("bus.import");
        if (shouldImportFile) {
        	CVSImporter cvsImporter = context.getBean(CVSImporter.class);
        	cvsImporter.importDataFromFile();
        }
        
        
        ConfigResource configResource = context.getBean(ConfigResource.class);
		AtualizarSistema thread = new AtualizarSistema(10000, configResource);
        thread.start();
        
        //environment.jersey().register(new BusResource(onibus));
        environment.jersey().register(configResource);
        
//        IntParam param = new IntParam("4");
//        resource.getOnibusNoPonto(param);
//        System.out.println("teste");
    }
    
    
    
}
