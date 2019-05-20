package com.wd.api.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.wd.api.cases.Case;

/**��ȡ��������
 * @author Lenovo
 *
 */
public class CaseUtil {
	//���ݱ�����cases����
public static List<Case> cases=new ArrayList<Case>();
static{
	ExcelUtil.lode("src/test/resources/wd.xls","����",Case.class);
}

/**���ݽӿڱ���ö�Ӧ�ӿڵĲ�������
 * @param apiId:ָ���ӿڱ��
 * @param cellNames��Ҫ��ȡ�����ݶ�Ӧ������
 * @return
 */
public static Object[][] getCaseDatasByApiId(String apiId,String [] cellNames) {
	Class<Case> clazz=Case.class;
		//����ָ���ӿڱ�ŵ�case����ļ���
	ArrayList<Case>  csList=new ArrayList<Case>();
	//ͨ��ѭ���ҳ��ӿڱ��ָ���Ľӿڱ�Ŷ�Ӧ����Щ��������
	for (Case cs: cases) {
		//ѭ������
		if (cs.getApiId().equals(apiId)) {
			csList.add(cs);
		}
	}
	Object[][] datas=new Object[csList.size()][cellNames.length];
	for (int i = 0; i < csList.size(); i++) {
		Case cs=csList.get(i);
		for (int j = 0; j < cellNames.length; j++) {
			//Ҫ����ķ�����
			String methodName="get"+cellNames[j];
			//��ȡҪ����ķ�������
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
