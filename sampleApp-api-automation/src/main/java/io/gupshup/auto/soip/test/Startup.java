package io.gupshup.auto.soip.test;

import io.gupshup.auto.init.InitTest;
import io.gupshup.auto.lib.ConfigFile;
import io.gupshup.auto.soip.custom.APIPlaceHolderImplementation;
import io.gupshup.auto.soip.custom.CustomKeywordUtils;
import io.gupshup.auto.soip.utils.APIEnvBO;

public class Startup {

	public static void main(String[] args) throws Exception {


		// fetch configuration files
		new ConfigFile("configuration.properties");

		// fetch environment variables
		new APIEnvBO();

		// execute automation with specified CustomKeywordUtils and APIPlaceholderUtils
		InitTest.initTest(CustomKeywordUtils.class, APIPlaceHolderImplementation.class);
	}

}
