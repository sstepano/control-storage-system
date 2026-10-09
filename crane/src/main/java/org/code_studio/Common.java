package org.code_studio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Properties;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Component;
import org.springframework.util.DefaultPropertiesPersister;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("Common")
public class Common implements ApplicationContextAware{
	
	public static Properties applicationProperties = null;
	public static Properties externalProperties = null;
	private static ApplicationContext applicationContext;
	private static Boolean connectionDayStartEstablished = false;
	

	public Common() {
		try {
			applicationProperties = PropertiesLoader.loadProperties("application.properties");
			externalProperties = PropertiesLoader.loadExternalProperties();
		} catch (Exception ex) {
			log(getClass(), ex, "ERROR");
		}
	}

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Common.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

	public static String getApplicationHome() {
		ApplicationHome applicationHome = new ApplicationHome();
		return applicationHome.getDir().toString();
	}
	
	/*
	 * Logs runtime messages to the file as INFO message
	 * Uses underlying logging
	 */
	public static void log (Class<?> clazz, Object message) {
		log(clazz, message, "INFO");
	}
	
	/*
	 * Logs runtime messages to the file
	 * We use Object for message, because sometimes it is String,
	 * sometimes it is Exception
	 */
	public static void log (Class<?> clazz, Object message, String level) {
		Logger logger = LoggerFactory.getLogger(clazz);
		switch(level.toUpperCase()) {
			case "ERROR":
				logger.error("{}", message);
			break;
			case "WARN":
				logger.warn("{}", message);
			break;
			case "DEBUG":
				logger.debug("{}", message);
			break;
			case "INFO":
				logger.info("{}", message);
			break;
			default:
				logger.info("{}", message);
			break;
		}
	}
	
	
	
	/*
	 * PROPERTIES LOADER, FOR USING PROPERTIES VALUES WITHOUT SPRING BOOT
	 * , WHERE WE CANNOT USE @COMPONENT ON CLASS, BUT WE NEED VALUE FROM PROPS.
	 * */
	public class PropertiesLoader {
	    public static Properties loadProperties(String resourceFileName) throws IOException {
	        Properties configuration = new Properties();
	        InputStream inputStream = PropertiesLoader.class
	          .getClassLoader()
	          .getResourceAsStream(resourceFileName);
	        configuration.load(inputStream);
	        inputStream.close();
	        configuration.load(new FileInputStream("./crane.properties")); // TODO: Promeniti tako da cita iz prosledjenog argumenta metoda
	        return configuration;
	    }
	    
	    public static Properties loadExternalProperties() throws IOException {
	    	Properties configuration = new Properties();
	    	configuration.load(new FileInputStream("./crane.properties"));
	    	return configuration;
	    }
	    
	    public static String encodeProperty(String originalString) {
	    	return Base64.getEncoder().encodeToString(originalString.getBytes());
	    }
	    
	    public static String decodeProperty(String propertyName) {
	    	byte[] decodedBytes = Base64.getDecoder().decode(applicationProperties.getProperty(propertyName));
	    	return new String(decodedBytes);
	    }
	    
	    /**
	     * TODO: Nedovrsena implementacija
	     *
	    public static void persist() {
	    	try {
	    	String resourceFileName = "./crane.properties";
	        File propertiesFile = new File(resourceFileName);
	        OutputStream outputStream = new FileOutputStream(propertiesFile);
	        DefaultPropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
	        propertiesPersister.store(applicationProperties, outputStream, "Header Comment");
	    	} catch (Exception e) {
	    		e.printStackTrace();
			}
	    }
	    **/
	    
	    public static void persistExternalProperties() {
	    	try {
	    	String resourceFileName = "./crane.properties";
	        File propertiesFile = new File(resourceFileName);
	        OutputStream outputStream = new FileOutputStream(propertiesFile);
	        DefaultPropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
	        propertiesPersister.store(externalProperties, outputStream, null);
	    	} catch (Exception e) {
	    		e.printStackTrace();
			}
	    }

	} // PropertiesLoader class END
	
	
    /**
     * returns CURRENT integer value for message ordinals that are sent to crane 
     * @return
     */
    public static Integer getCurrentMessageOrdinalNumber() {
    	Integer res = Integer.parseInt(Common.externalProperties.getProperty("tcpclient.ordinalNumber"));
    	return res;
    }

    
    /**
     * Sets integer value for message ordinals that are sent to crane
     * and persists it in the file
     */
    public static Integer getNextMessageOrdinalNumber() {
    	Integer currentOrdinalNumber = getCurrentMessageOrdinalNumber();
    	
    	Common.externalProperties.setProperty("tcpclient.ordinalNumber"
    			, currentOrdinalNumber >= 99 
    				? "01"
    				: StringUtils.leftPad(
    						String.valueOf(currentOrdinalNumber + 1),
    						2,
    						"0"
					));
    	PropertiesLoader.persistExternalProperties();	    	
    	return getCurrentMessageOrdinalNumber();
    }


	/**
     * Gets Boolean, when we establish connection to crane and succesffully set DAY START this value is TRUE, else FALSE
     * @return Boolean
     */
	public static Boolean getConnectionDayStartEstablished() {
		return connectionDayStartEstablished;
	}

    /**
     * Sets to TRUE when we establish connection to crane and succesffully set DAY START
     * @return void
     */
	public static void setConnectionDayStartEstablished(Boolean connectionDayStartEstablished) {
		Common.connectionDayStartEstablished = connectionDayStartEstablished;
	}
		
	
} // class END
