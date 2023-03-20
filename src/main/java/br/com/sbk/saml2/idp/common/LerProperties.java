package br.com.sbk.saml2.idp.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LerProperties {

	private static Logger logger = LoggerFactory.getLogger(LerProperties.class);

	public static Configuration lerArquivo(String nomeArquivo) throws FileNotFoundException, ConfigurationException {

		for (String name : System.getProperties().stringPropertyNames()) {
			logger.info(name + " - " + System.getProperty(name));
		}

		final File file1 = new File(Paths.get("", nomeArquivo).toFile().getAbsolutePath());
		final File file2 = new File(nomeArquivo);
		File file = null;
		if (file1.exists()) {
			file = file1;
		} else if (file2.exists()) {
			file = file2;
		} else {
			logger.error(
					"Nao foi possivel ler o arquivo de propriedades, verifique se o arquivo se encontra nos diretorios da aplicacao!");
			System.exit(128);
		}

		logger.info("Carregando arquivo");
		Parameters params = new Parameters();
		FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
				PropertiesConfiguration.class).configure(params.fileBased().setFile(file));
		final Configuration config = builder.getConfiguration();

		return config;

	}

}