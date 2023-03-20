package br.com.sbk.saml2.idp.common;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Propriedades {

	private static final Logger log = LoggerFactory.getLogger(Propriedades.class);

	private Map<String, String[]> lista = new HashMap<String, String[]>();

	public String[] getArray(String key) {
		return lista.get(key);
	}

	public String[] getArraySuffix(String keySuffix) {
		Set<String> result = new HashSet<String>();
		for (String key : lista.keySet())
			if (key.endsWith(keySuffix))
				result.add(key.replace(keySuffix, ""));
		return result.toArray(new String[0]);
	}

	public String getString(String key) {
		String[] array = lista.get(key);
		if (array == null)
			return null;
		return lista.get(key)[0];
	}

	public int getInt(String key, int defaultValue) {
		String value = getString(key);
		if (value == null)
			return defaultValue;
		return Integer.parseInt(value);
	}

	public void setProperty(String key, String[] value) {
		lista.put(key, value);
	}

	public void loadFile(File arquivo) {
		try {
			log.info("Lendo arquivo de propriedade: " + arquivo.getName());
			Parameters params = new Parameters();
			FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
					PropertiesConfiguration.class).configure(params.fileBased().setFile(arquivo));
		    Configuration props = builder.getConfiguration();
			Iterator<String> keys = props.getKeys();
			while (keys.hasNext()) {
				String key = keys.next();
				log.info("Obtendo propriedade: " + key);
				String[] value = props.getStringArray(key);
				setProperty(key, value);
			}
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

}