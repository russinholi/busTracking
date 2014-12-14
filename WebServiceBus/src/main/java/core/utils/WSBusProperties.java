package core.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class WSBusProperties {

	
	public static final String PROPERTY_FILE = "bustracking.properties";
	
	private Properties properties;
	
	private static WSBusProperties instance;
	
	private WSBusProperties() {
		try {
			properties = new Properties();
			properties.load(new FileInputStream(PROPERTY_FILE));
		} catch (IOException e) {
			Logger.getRootLogger().fatal("Problema ao carregar o arquivo de configurações "+PROPERTY_FILE);
		}
	}

	public static WSBusProperties getInstance() {
		if (instance == null) {
			instance = new WSBusProperties();
		}
		
		return instance;
	}

	public int getAppConectorPort() {
		return getIntegerProperty("applicationConnector.port", 9090);
	}

	public int getAdmConectorPort() {
		return getIntegerProperty("adminConnector.port", 9190);
	}

	public String getDatabaseName() {
		String value = properties.getProperty("database.name");
		if (StringUtils.isBlank(value)) {
			value = "busTracking";
		}
		return value;
		
	}
	private int getIntegerProperty(String propertyName, int defaultValue) {
		String value = properties.getProperty(propertyName);
		int integerValue = defaultValue;
		try {
			integerValue = Integer.valueOf(value);
		} catch (NumberFormatException e) {
		}
		return integerValue;
	}
}
