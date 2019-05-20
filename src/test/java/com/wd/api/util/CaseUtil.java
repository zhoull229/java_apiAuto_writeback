package com.wd.api.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.wd.api.cases.Case;

/**获取测试数据
 * @author Lenovo
 *
 */
public class CaseUtil {
	//数据保存在cases集合
public static List<Case> cases=new ArrayList<Case>();
static{
	ExcelUtil.lode("src/test/resources/wd.xls","用例",Case.class);
}

/**根据接口编号拿对应接口的测试数据
 * @param apiId:指定接口编号
 * @param cellNames：要获取的数据对应的列名
 * @return
 */
public static Object[][] getCaseDatasByApiId(String apiId,String [] cellNames) {
	Class<Case> clazz=Case.class;
		//保存指定接口编号的case对象的集合
	ArrayList<Case>  csList=new ArrayList<Case>();
	//通过循环找出接口编号指定的接口编号对应的这些用例数据
	for (Case cs: cases) {
		//循环处理
		if (cs.getApiId().equals(apiId)) {
			csList.add(cs);
		}
	}
	Object[][] datas=new Object[csList.size()][cellNames.length];
	for (int i = 0; i < csList.size(); i++) {
		Case cs=csList.get(i);
		for (int j = 0; j < cellNames.length; j++) {
			//要反射的方法名
			String methodName="get"+cellNames[j];
			//获取要反射的方法对象
			Method method;
			try {
				method = clazz.getMethod(methodName);
				String value=(String) method.invoke(cs);
				datas[i][j]=value;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	return datas;
}

}
