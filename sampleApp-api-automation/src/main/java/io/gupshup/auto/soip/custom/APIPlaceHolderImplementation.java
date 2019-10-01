package io.gupshup.auto.soip.custom;

import java.util.HashMap;
import java.util.Map;

import io.gupshup.auto.soip.utils.APIEnvBO;
import io.gupshup.auto.lib.RegexImplementation;
import io.gupshup.auto.lib.testcase.PlaceHolderImplementation;

public class APIPlaceHolderImplementation extends PlaceHolderImplementation {

	// private Logger logger =
	// Logger.getLogger(APIPlaceHolderImplementation.class.getName());

	@Override
	public String getPlaceHolderGlobalValue(String placeHolder) {
		String newValue = super.getPlaceHolderGlobalValue(placeHolder);
		if (newValue == null) {
			switch (placeHolder) {
//			 case "AUTH_USER_NAME":
//				newValue = APIEnvBO.getAuthUserName();
//				break;
//			 case "AUTH_PASS":
//				newValue = APIEnvBO.getAuthPassword();
//				break;
			default:
				if (APIEnvBO.isGlobalVariableAvailable(placeHolder)) {
					newValue = String.valueOf(APIEnvBO.getGlobalVariable(placeHolder));
				} else
					newValue = placeHolder;
			}
		}

		return newValue;
	}

	@Override
	public Map<String, Object> getPlaceHolderGlobalValueMap(String key, String value, String placeHolder) {
		Map<String, Object> pairTestCalculatedPairs = new HashMap<String, Object>();
		String newValue = getPlaceHolderGlobalValue(placeHolder);

		pairTestCalculatedPairs.put(key, RegexImplementation
				.replaceRegexWithAllGroup(RegexImplementation.PLACEHOLDER_GS_PATTERN, value, newValue));

		return pairTestCalculatedPairs;
	}
}
