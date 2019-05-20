package com.wd.api.test;

import java.util.HashMap;
import java.util.Map;

import com.wd.api.util.HttpUtil;

public class test {
	public static void main(String[] args) {
		String url = "http://47.105.238.105:18888/wd-question-server/school/conceptQuestion/search";
		Map<String, String> params = new HashMap<String, String>();

		String value = "{\"page\":\"1\",\"pageSize\":\"20\",\"chapterId\":\"\",\"conceptTypeId\":\"\",\"conceptId\":\"\", \"countOrderRule\":\"\",\"isOrderCount\":\"\",\"timeOrderRule\":\"\",\"isOrderTime\":\"\",\"abilityDegreeId\":\"\",\"labelType\":\"0\",\"fuzzyContent\":\"\",\"pointSet\":[],\"jySubjectId\":\"1f551407-e561-4b6a-8120-cdf93e5c9034\",\"wdQuestionTypeId\":\"\",\"schoolId\":\"test5\"}";
		params.put("params", value);
		System.out.println(HttpUtil.doService(url, "post", params));
	}
}
