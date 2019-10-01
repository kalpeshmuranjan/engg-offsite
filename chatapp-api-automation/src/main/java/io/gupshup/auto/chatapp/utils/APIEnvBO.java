package io.gupshup.auto.chatapp.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

import io.gupshup.auto.lib.ConfigFile;
import io.gupshup.auto.lib.ReportBO;

public class APIEnvBO {

	private static Logger logger = Logger.getLogger(APIEnvBO.class.getName());
//	private static String authUserName = "";
//	private static String authPassword = "";
	private static Map<String, Object> globalVariables = new HashMap<String, Object>();
	
	public APIEnvBO() throws FileNotFoundException, IOException {
		String configFileName = "";
		
		
		// -----------------------------------------------------------------------
		// Config Logic
		// -----------------------------------------------------------------------

		String env = System.getProperty("env");
		if(env.equalsIgnoreCase("QA")) {
			configFileName = ConfigFile.getConfigFilePath("qa_", "config");
		}
		
		Properties prop = new Properties();
		prop.load(new FileInputStream(new File(configFileName)));
		

		// -----------------------------------------------------------------------
		// Set Global Variable for future use
		// -----------------------------------------------------------------------
//		authUserName = prop.getProperty("auth_username");
//		authPassword = prop.getProperty("auth_password");
		
		// -----------------------------------------------------------------------
		// Report Logic
		// -----------------------------------------------------------------------

		String listEmails = System.getProperty("email_list");
		if (listEmails == null || listEmails.equals(""))
			listEmails = prop.getProperty("report_email");

		// LinkedHashMap<String, String> mailBodyTabularFormat = new LinkedHashMap<String, String>();
		// mailBodyTabularFormat.put("Enterprise URL : ", getENTPlatformURL());
		// mailBodyTabularFormat.put("Account Id : ", getAccountID());

		ReportBO report = ReportBO.getInstance();
		report.setProjectName("SOIP-API");
		report.setEmailAddresses(listEmails);
		report.setJenkinsUrl(System.getProperty("jenkins_url"));
		report.setSendReportFlag("true".equalsIgnoreCase(System.getProperty("sendReport")));
		report.setSuiteType(System.getProperty("flagSanity").contains("true") ? "sanity" : "regression");
		report.setReportFileName("gupshup-soip-api-report.html");
		// report.setMailDetailsTabularFormat(mailBodyTabularFormat);
		// -----------------------------------------------------------------------
	}

	public static Map<String, Object> getGlobalVariables() {
		return globalVariables;
	}

	public static void setGlobalVariable(String key, Object objData) {
		globalVariables.put(key, objData);
	}

	public static Object getGlobalVariable(String key) {
		return globalVariables.get(key);
	}

	public static boolean removeGlobalVariable(String key) {
		globalVariables.remove(key);

		return !globalVariables.containsKey(key);
	}
	
	public static boolean isGlobalVariableAvailable(String key) {
		return globalVariables.containsKey(key);
	}
	
//	public static String getAuthUserName() {
//		return authUserName;
//	}
//
//	public static String getAuthPassword() {
//		return authPassword;
//	}
}
