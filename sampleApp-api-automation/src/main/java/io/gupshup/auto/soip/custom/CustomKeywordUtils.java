package io.gupshup.auto.soip.custom;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import io.gupshup.auto.lib.RegexImplementation;
import io.gupshup.auto.lib.testcase.APIBuild;
import io.gupshup.auto.lib.testcase.RestAssuredRequest;
import io.gupshup.auto.lib.testcase.StepKeywordUtils;
import io.gupshup.auto.lib.testcase.TestCaseBO;
import io.gupshup.auto.lib.verification.APIResponse;
import io.gupshup.auto.soip.utils.APIEnvBO;

public class CustomKeywordUtils extends StepKeywordUtils {

	private static Logger logger = Logger.getLogger(CustomKeywordUtils.class.getName());
	
	public void testNewKeyword(TestCaseBO testcase, ArrayList<Object> paramTest) {

	}

	@Override
	public void updateQueryParam(TestCaseBO testcase, ArrayList<Object> paramQuery) {
		super.updateQueryParam(testcase, paramQuery);
	}

	public void getGlobalVariable(TestCaseBO testcase, ArrayList<Object> variableDetails) {
		Object fromObject = testcase.getTestComputationalParam(String.valueOf(variableDetails.get(1)));
		Object valueKey = null;
		String nameVariable = (String) variableDetails.get(0);

		if (String.valueOf(variableDetails.get(1)).contains("RequestAPI")) {
			// fetch variable from Request API Request
			if (((String) variableDetails.get(2)).equalsIgnoreCase("QueryParams")) {
				String keyRequest = (String) variableDetails.get(3);
				RestAssuredRequest request = (RestAssuredRequest) fromObject;
				if (request.getQueryParams().get(keyRequest) != null)
					valueKey = String.valueOf(request.getQueryParams().get(keyRequest));
				logger.debug(nameVariable + " : " + valueKey);
			} else if (((String) variableDetails.get(2)).equalsIgnoreCase("PathParams")) {
				String keyRequest = (String) variableDetails.get(3);
				RestAssuredRequest request = (RestAssuredRequest) fromObject;
				if (request.getPathParams().get(keyRequest) != null)
					valueKey = String.valueOf(request.getPathParams().get(keyRequest));
				logger.debug(nameVariable + " : " + valueKey);
			} else if (((String) variableDetails.get(2)).equalsIgnoreCase("RequestHeaders")) {
				String keyRequest = (String) variableDetails.get(3);
				RestAssuredRequest request = (RestAssuredRequest) fromObject;
				if (request.getHeaders().get(keyRequest) != null)
					valueKey = String.valueOf(request.getHeaders().get(keyRequest));
				logger.debug(nameVariable + " : " + valueKey);
			} else if (((String) variableDetails.get(2)).equalsIgnoreCase("FormParams")) {
				String keyRequest = (String) variableDetails.get(3);
				RestAssuredRequest request = (RestAssuredRequest) fromObject;
				if (request.getFormParams().get(keyRequest) != null)
					valueKey = String.valueOf(request.getFormParams().get(keyRequest));
				logger.debug(nameVariable + " : " + valueKey);
			} else if (((String) variableDetails.get(2)).equalsIgnoreCase("MultipartParams")) {
				String keyRequest = (String) variableDetails.get(3);
				RestAssuredRequest request = (RestAssuredRequest) fromObject;
				if (request.getMultiPartParams().get(keyRequest) != null)
					valueKey = String.valueOf(request.getMultiPartParams().get(keyRequest));
				logger.debug(nameVariable + " : " + valueKey);
			}
		} else if (String.valueOf(variableDetails.get(1)).contains("Instance_Scope")) {
			// fetch value from Global Variable
			valueKey = (new APIBuild()).getValuesFromInstanceScope((String) variableDetails.get(2), testcase);

		} else if (fromObject instanceof APIResponse) {
			// fetch variable from API Response
			APIResponse response = (APIResponse) fromObject;

			String splitPath[] = ((String) variableDetails.get(2)).split("\\|");
			Object valueFromResponsePath = null;
			for (String path : splitPath) {
				valueFromResponsePath = response.getResponse(path);
				if (valueFromResponsePath != null)
					break;
			}
			String usePathValue = (String) variableDetails.get(3);
			if (usePathValue.equalsIgnoreCase("value"))
				valueKey = String.valueOf(valueFromResponsePath);
			else if (usePathValue.equalsIgnoreCase("regex")) {
				String pattern = ((String) variableDetails.get(4)).split("::")[0];
				String fetchGroup = ((String) variableDetails.get(4)).split("::")[1];
				valueKey = RegexImplementation
						.getRegexGroup(pattern, String.valueOf(valueFromResponsePath), Integer.parseInt(fetchGroup))
						.get(0);
			}

		} else if (fromObject == null) {
			valueKey = String.valueOf(variableDetails.get(1));
		}

		logger.debug("Global Var -  " + nameVariable + "   :   " + valueKey);
		APIEnvBO.setGlobalVariable(nameVariable, valueKey);
	}
}
