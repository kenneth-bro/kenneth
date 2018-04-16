package com.kenneth.boot.service.databse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 处理excel数据
 * @author Kenneth-IVS
 * @date 2018年4月13日
 *
 */
public class ResolveExcel {
	
	//xls
	private HSSFWorkbook hssfWorkbook = null;
	
	//xlsx
	private XSSFWorkbook xssfWorkbook = null;
	
	/**
	 * 数据写入excel中
	 * @author Kenneth-IVS
	 * @date 2018年4月13日
	 * @param data
	 */
	public void writeDataToExcel(List<List<Object>> datas){
		String directoryPath = "d:/";
		String filePath = directoryPath + "数据字典(好股网)2018-04-14-最终.xls";
		String sheetName = "默认";
		//目录判断
		isDirsExist(directoryPath);
		File file = new File(filePath);
		FileOutputStream fos = null;
		try {
			//创建workbook
			hssfWorkbook = new HSSFWorkbook();
			if(!isSheetExist(hssfWorkbook, sheetName)){
				hssfWorkbook.createSheet(sheetName);
			}
			HSSFSheet sheet = hssfWorkbook.getSheet(sheetName);
			//获取表格的总行数,+1
			int rowCount = sheet.getLastRowNum() + 1;
			for (int i = 0; i < datas.size(); i++) {
				List<Object> data = datas.get(i);
				//最新的添加的一行
				Row row = sheet.createRow(rowCount + i);
				for(int j=0; j < data.size(); j++){
					String value = String.valueOf(data.get(j));
					Cell cell = row.createCell(j);
					cell.setCellValue(value);
				}
			}
			fos = new FileOutputStream(file);
			hssfWorkbook.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("excel生成完毕");
	}
	
	/**
	 * 创建新的sheet并写入第一行数据
	 * @author Kenneth-IVS
	 * @date 2018年4月13日
	 * @param filePath
	 * @param sheetName
	 * @param titleRows
	 */
	public void createSheet(String filePath, String sheetName, List<String> titleRows){
		try {
			FileOutputStream fos = null;
			File excel = new File(filePath);
			hssfWorkbook = new HSSFWorkbook(new FileInputStream(excel));
			hssfWorkbook.createSheet(sheetName);
			//添加表头
			if(titleRows != null){
				Row row = hssfWorkbook.getSheet(sheetName).createRow(0);
				for (int i = 0; i < titleRows.size(); i++) {
					Cell cell = row.createCell(i);
					cell.setCellValue(titleRows.get(i));
				}
				fos = new FileOutputStream(filePath);
				hssfWorkbook.write(fos);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 工作簿是否存在
	 * @author Kenneth-IVS
	 * @date 2018年4月13日
	 * @param filePath 文件路径
	 * @param sheetName 工作簿名
	 * @return
	 */
	public boolean isSheetExist(HSSFWorkbook workbook, String sheetName){
		boolean isExist = false;
		try {
			if(workbook != null){
				HSSFSheet sheet = workbook.getSheet(sheetName);
				if (sheet != null) {
					isExist = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isExist;
	}
	
	/**
	 * 文件是否存在
	 * @author Kenneth-IVS
	 * @date 2018年4月13日
	 * @param filePath
	 * @return
	 */
	public boolean isFileExist(String filePath){
		boolean isExist = false;
		File file = new File(filePath);
		isExist = file.exists();
		return isExist;
	}
	
	/**
	 * 判断文件目录是否存在
	 * @author liq
	 * @date 2017年11月23日
	 * @param file
	 */
	public static void isDirsExist(String directoryPath) {
		File file = new File(directoryPath);
		if (file.exists()) {
			if (file.isDirectory()) {
				
			} else {
				System.out.println("the same name file exists, can not create dir");
			}
		} else {
			file.mkdirs();
			System.out.println("目录:" + directoryPath + " 已创建.");
		}
	}
	
	public static void main(String[] args) {
		ResolveExcel resolveExcel = new ResolveExcel();
		List<List<Object>> test = new ArrayList<List<Object>>();
		resolveExcel.writeDataToExcel(test);
	}
}
