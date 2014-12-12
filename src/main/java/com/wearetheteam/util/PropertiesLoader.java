/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.wearetheteam.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.wearetheteam.task.ScheduleExecutor;
import com.wearetheteam.task.ScheduleJob;

/**
 *
 * ClassName: PropertiesLoader <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2014年11月27日 下午5:08:27 <br/>
 *
 * @author Administrator
 * @version
 * @since JDK 1.6
 */
public class PropertiesLoader implements ScheduleJob {

	private static class LazyHolder {
		private static final PropertiesLoader INSTANCE = new PropertiesLoader(getClassPath() + DYNAMIC_CONFIG_FILENAME);
	}

	private static String[] configProperties;

	private static int configReloadPeriod = 3000 * 1000;

	private final static String DYNAMIC_CONFIG_FILENAME = "dynamic-config.properties";

	private final static String DYNAMIC_CONFIG_KEY_PROPERTIES = "configProperties";

	private final static String DYNAMIC_CONFIG_KEY_RELOADPERIOD = "configReloadPeriod";

	private final static Logger logger = Logger.getLogger(PropertiesLoader.class);

	private static Properties properties = new Properties();

	public static String getClassPath() {
		final String path = PropertiesLoader.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String rootStr = "";
		if (path.indexOf("WEB-INF") > 0) {
			rootStr = new String(path.substring(0, path.indexOf("/WEB-INF/classes") + 16));
		} else {
			rootStr = path;
		}
		return rootStr;
	}

	public static PropertiesLoader getInstance() {
		return LazyHolder.INSTANCE;
	}

	public static void main(String[] args) {
		PropertiesLoader.getInstance();
		MonitorJvm.getInstance();
	}

	public PropertiesLoader(final String resourcesPaths) {

		final String dynamicConfigFilePath = getClassPath() + DYNAMIC_CONFIG_FILENAME;
		final Properties dynamicConfigProp = loadProperties(dynamicConfigFilePath);
		final String configPropertiesStr = dynamicConfigProp.getProperty(DYNAMIC_CONFIG_KEY_PROPERTIES);

		try {
			configReloadPeriod = Integer.parseInt(dynamicConfigProp.getProperty(DYNAMIC_CONFIG_KEY_RELOADPERIOD)) * 1000;
		} catch (final Exception e) {
			logger.error("RootReloadPeriod is not integer,configReloadPeriod:" + dynamicConfigProp.getProperty(DYNAMIC_CONFIG_KEY_RELOADPERIOD) + " configReloadPeriod use the default value:"
					+ configReloadPeriod);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Begin display all of the dynamicConfigProp");
			for (final Map.Entry<Object, Object> entry : dynamicConfigProp.entrySet()) {
				logger.debug(entry.getKey() + ":" + entry.getValue());
			}
			logger.debug("Finish display all of the dynamicConfigProp");
		}

		if (configPropertiesStr == null) {
			logger.error("There is no " + DYNAMIC_CONFIG_FILENAME + " file on the classpath,init the property to blank!");
		} else {
			configProperties = configPropertiesStr.split(",");
			for (int i = 0; i < configProperties.length; i++) {
				configProperties[i] = getClassPath() + configProperties[i];
			}
			properties = loadProperties(configProperties);
		}

		/*
		 * if configReloadPeriod great than or equal to 0 then launch the reload
		 * thread
		 */
		if (configReloadPeriod > 0) {
			new ScheduleExecutor(this);
		}

	}

	public void doJob() {
		properties = loadProperties(configProperties);
	}

	/**
	 * 取出Boolean类型的Property.如果都為Null抛出异常,如果内容不是true/false则返回false.
	 */
	public Boolean getBoolean(final String key) {
		final String value = getValue(key);
		if (value == null) {
			throw new NoSuchElementException();
		}
		return Boolean.valueOf(value);
	}

	/**
	 * 取出Boolean类型的Propert.如果都為Null則返回Default值,如果内容不为true/false则返回false.
	 */
	public Boolean getBoolean(final String key, final boolean defaultValue) {
		final String value = getValue(key);
		return value != null ? Boolean.valueOf(value) : defaultValue;
	}

	/**
	 * 取出Double类型的Property.如果都為Null或内容错误则抛出异常.
	 */
	public Double getDouble(final String key) {
		final String value = getValue(key);
		if (value == null) {
			throw new NoSuchElementException();
		}
		return Double.valueOf(value);
	}

	/**
	 * 取出Double类型的Property.如果都為Null則返回Default值，如果内容错误则抛出异常
	 */
	public Double getDouble(final String key, final Integer defaultValue) {
		final String value = getValue(key);
		return value != null ? Double.valueOf(value) : defaultValue;
	}

	public int getExecuteInterval() {
		return configReloadPeriod;
	}

	/**
	 * 取出Integer类型的Property.如果都為Null或内容错误则抛出异常.
	 */
	public Integer getInteger(final String key) {
		final String value = getValue(key);
		if (value == null) {
			throw new NoSuchElementException();
		}
		return Integer.valueOf(value);
	}

	/**
	 * 取出Integer类型的Property.如果都為Null則返回Default值，如果内容错误则抛出异常
	 */
	public Integer getInteger(final String key, final Integer defaultValue) {
		final String value = getValue(key);
		return value != null ? Integer.valueOf(value) : defaultValue;
	}

	public Properties getProperties() {
		return properties;
	}

	/**
	 * 取出String类型的Property,如果都為Null则抛出异常.
	 */
	public String getProperty(final String key) {
		final String value = getValue(key);
		if (value == null) {
			throw new NoSuchElementException();
		}
		return value;
	}

	/**
	 * 取出String类型的Property.如果都為Null則返回Default值.
	 */
	public String getProperty(final String key, final String defaultValue) {
		final String value = getValue(key);
		return value != null ? value : defaultValue;
	}

	/**
	 * 取出Property。
	 */
	private String getValue(final String key) {
		final String systemProperty = System.getProperty(key);
		if (systemProperty != null) {
			return systemProperty;
		}
		return properties.getProperty(key);
	}

	/**
	 * 载入多个文件, 文件路径使用Spring Resource格式.
	 */
	private Properties loadProperties(final String... resourcesPaths) {
		final Properties props = new Properties();
		logger.debug("Begin load all of the properties");
		for (final String location : resourcesPaths) {
			logger.debug("Loading properties file from path:{" + location + "}");
			InputStream is = null;
			try {
				is = new FileInputStream(location);
				props.load(is);
			} catch (final IOException ex) {
				logger.error("Could not load properties from path:{" + location + "},errorMessage:{" + ex.getMessage() + "}");
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (final IOException e) {
						e.printStackTrace();
					}
				}
			}
			if (logger.isDebugEnabled()) {
				for (final Map.Entry<Object, Object> entry : props.entrySet()) {
					logger.debug(entry.getKey() + ":" + entry.getValue());
				}
			}
		}
		logger.debug("Finish load all of the properties");
		return props;
	}

}
