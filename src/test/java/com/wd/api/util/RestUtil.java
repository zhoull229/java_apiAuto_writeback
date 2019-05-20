package com.wd.api.util;

import java.util.ArrayList;
import java.util.List;

import com.wd.api.cases.Rest;

/**��ȡurl��type
 * @author Lenovo
 *
 */
public class RestUtil {
public static List<Rest> rests=new ArrayList<Rest>();
static{
	ExcelUtil.lode("src/test/resources/wd.xls","�ӿ���Ϣ",Rest.class);
}

public static String getUrlByApiId(String apiId) {
	for (Rest rest : rests) {
		if (rest.getApiId().equals(apiId)) {
			return rest.getUrl();
		}
	}
	return "";
}

public static String getTypeByApiId(String apiId) {
	for (Rest rest : rests) {
		if (rest.getApiId().equals(apiId)) {
			return rest.getType();
		}
	}
	return "";
}

}
