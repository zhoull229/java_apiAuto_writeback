package com.wd.api.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import com.wd.api.cases.Case;
import com.wd.api.cases.Rest;
import com.wd.api.cases.WriteBackData;

/**����Excel
 * @author Lenovo
 *
 */
public class ExcelUtil {
	//��������������е�����
	public static Map<String,Integer> caseIdRownumMapping=new HashMap<String,Integer>();
	//���������õ��е�����
	public static Map<String,Integer> cellNameCellnumMapping=new HashMap<String,Integer>();
	public static List<WriteBackData> writeBackDatas =new ArrayList<WriteBackData>();
	//������������������ӳ�������
	static{
		loadRownumAndCellnumMapping("src/test/resources/wd.xls","����");
		
	}
	
/**����Excel�ı������ݣ���װΪ����
 * @param <T>:����,֧�ִ��벻ͬ���͵Ķ���
 * @param clazz 
 * @param excelPath��Excel�ļ������·��
 * @param sheetName��Excel����
 */
public static <T> void lode(String excelPath, String sheetName, Class<T> clazz) {
	try {
		// ����workbook����
		Workbook workbook=WorkbookFactory.create(new File(excelPath));
		Sheet sheet=workbook.getSheet(sheetName);
		//��ȡ��һ��
		Row titleRow=sheet.getRow(0);
		//��ȡ�ж�����(�õ������к�),������ֵ��1
		int lastCellNum=titleRow.getLastCellNum();
		//����һ���ַ������飬������
		String [] fields=new String[lastCellNum];
		//ѭ������ÿһ��
		for (int i = 0; i < lastCellNum; i++) {
			//������������ȡ��Ӧ����
			Cell cell=titleRow.getCell(i,MissingCellPolicy.CREATE_NULL_AS_BLANK);
			//�����е�����Ϊ�ַ���
			cell.setCellType(CellType.STRING);
			//��ȡ�е�ֵ�����⣩
			String title=cell.getStringCellValue();
			//��ȡǰ���Ӣ�Ĳ���
			title=title.substring(0, title.indexOf("("));
			//�����ݷŵ�����
			fields[i]=title;
		
		}
		//��ȡ����,�õ�����������ֵ
		int lastRowIndex=sheet.getLastRowNum();
		//ѭ������ÿһ��������
		for (int i = 1; i <=lastRowIndex; i++) {
			//ͨ���ֽ��봴��case��Ķ���
			Object obj=clazz.newInstance();
			//�õ�һ��������
			Row dataRow=sheet.getRow(i);
			if (dataRow==null||isEmptyRow(dataRow)) {
				continue;
			}
			//�õ����������ϵ�ÿһ��,�����ݷ�װ��cs����
			for (int j = 0; j < lastCellNum; j++) {
				Cell cell=dataRow.getCell(j,MissingCellPolicy.CREATE_NULL_AS_BLANK);
				cell.setCellType(CellType.STRING);
				String value=cell.getStringCellValue();
				//��ȡҪ����ķ�����
				String methodName="set"+fields[j];
				//��ȡҪ����ķ�������
				Method method=clazz.getMethod(methodName, String.class);
				//��ɷ������
				method.invoke(obj, value);

			}
			if (obj instanceof Case) {//instanceof�ж϶������͵��﷨
				Case cs=(Case) obj;
				//�Ѷ�����ӵ�����
				CaseUtil.cases.add(cs);
			}else if (obj instanceof Rest) {
				Rest rest=(Rest) obj;
				//�Ѷ�����ӵ�����
				RestUtil.rests.add(rest);
			}
			
		}
		
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	
}

/**��ȡcaseid�Լ�����Ӧ��������
 * ��ȡcellname�Լ�����Ӧ��������
 * @param excelPath
 * @param sheetName
 */
private static void loadRownumAndCellnumMapping(String excelPath, String sheetName) {
	InputStream inputStream=null;
	try {
		//��ȡworkbook��������������Ϊ��ҪƵ���Ĳ����ļ������������������Թر�
		inputStream = new FileInputStream(new File(excelPath));
		Workbook workbook=WorkbookFactory.create(inputStream);
		Sheet sheet=workbook.getSheet(sheetName);
		//��ȡ������
		Row titleRow=sheet.getRow(0);
		if (titleRow!=null&&!isEmptyRow(titleRow)) {
			//��ȡ�еĸ���
			int lastCellnum=titleRow.getLastCellNum();
			//ѭ����������е�ÿһ��
			for (int i = 0; i < lastCellnum;i++) {
				Cell cell=titleRow.getCell(i,MissingCellPolicy.CREATE_NULL_AS_BLANK);
				cell.setCellType(CellType.STRING);
				//��ȡ�����е�Ӣ������
				String title=cell.getStringCellValue();
				title=title.substring(0,title.indexOf("("));
				//��õ�ǰ�е�����
				int cellnum=cell.getAddress().getColumn();
				//������������ֵӳ�䵽����
				cellNameCellnumMapping.put(title, cellnum);
				
			}
		}
		//�ӵڶ��п�ʼ��ȡ���е�������
		//��ȡ�����������ֵ
		int lastRownum=sheet.getLastRowNum();
		//ѭ���õ�ÿ��������
		for (int i = 1; i <= lastRownum; i++) {
			Row dataRow=sheet.getRow(i);
			//��ȡ��һ��
			Cell firstCellOfRow=dataRow.getCell(0,MissingCellPolicy.CREATE_NULL_AS_BLANK);
			firstCellOfRow.setCellType(CellType.STRING);
			//�õ���һ�����������
			String caseId=firstCellOfRow.getStringCellValue();
			//��ȡ������
			int rownum=dataRow.getRowNum();
			//���б�ź�����ӳ�䵽����
			caseIdRownumMapping.put(caseId, rownum);
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		//��׽�ļ��Ƿ��ǿ�ֵ
		if (inputStream!=null) {
			try {
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}

/**�п�
 * @param dataRow
 * @return
 */
private static boolean isEmptyRow(Row dataRow) {
	
	int lastCellNum =dataRow.getLastCellNum();
	for (int i = 0; i < lastCellNum; i++) {
		Cell cell=dataRow.getCell(i,MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cell.setCellType(CellType.STRING);
		String value=cell.getStringCellValue();
		if (value!=null&&value.trim().length()>0) {
			return false;
		}
	}
	return true;
}

/**������д���ݵķ���
 * @param string
 */
public static void batchWriteBaceDatas(String excelPath) {
	InputStream inputStream=null;
	OutputStream outputStream=null;
	try {
		inputStream = new FileInputStream(new File(excelPath));
		Workbook workbook=WorkbookFactory.create(inputStream);
		for (WriteBackData writeBackData : writeBackDatas) {
			//��ȡsheetname
			String sheetName=writeBackData.getSheetName();
			Sheet sheet=workbook.getSheet(sheetName);
			//��ȡ������
			String caseId=writeBackData.getCaseId();
			int rownum=caseIdRownumMapping.get(caseId);
			//��ȡ������
			String cellName=writeBackData.getCellName();
			int cellnum=cellNameCellnumMapping.get(cellName);
			//д��������ȥ
			Row row=sheet.getRow(rownum);
			Cell cell=row.getCell(cellnum,MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cell.setCellType(CellType.STRING);
			//��Ҫд��ȥ��ֵ�Ž�excel
			String result=writeBackData.getResult();
			cell.setCellValue(result);
			
		}
		
		outputStream=new FileOutputStream(new File(excelPath));
		workbook.write(outputStream);
		
		
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		try {
			if (outputStream!=null) {
				outputStream.close();
			}
			if (inputStream!=null) {
				inputStream.close();
			}
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
}

}
