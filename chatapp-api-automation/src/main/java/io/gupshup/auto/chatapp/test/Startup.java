package io.gupshup.auto.chatapp.test;

import io.gupshup.auto.init.InitTest;
import io.gupshup.auto.lib.ConfigFile;
import io.gupshup.auto.chatapp.custom.APIPlaceHolderImplementation;
import io.gupshup.auto.chatapp.custom.CustomKeywordUtils;
import io.gupshup.auto.chatapp.utils.APIEnvBO;

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
