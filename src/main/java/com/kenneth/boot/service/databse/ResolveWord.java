package com.kenneth.boot.service.databse;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 * 解析Word文档
 * @author Kenneth-IVS
 * @date 2018年4月13日
 *
 */
public class ResolveWord {
	
	/**
	 * 解析docx的表格
	 * @author Kenneth-IVS
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @date 2018年4月13日
	 */
	public void resolveDocxTable() throws FileNotFoundException, IOException{
		XWPFDocument xwpfDocument = new XWPFDocument(new FileInputStream("D:/数据字典(好股网)2018-04-14.docx"));
		//最终的内容
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		
		//获取文档中的全部标题
		List<XWPFParagraph> xwpfParagraphs = xwpfDocument.getParagraphs();
		//1-5的数据
		List<String> oneToFiveParagraphs = new ArrayList<String>();
		for (XWPFParagraph xwpfParagraph : xwpfParagraphs) {
			//标题级别 一级标题、二级标题、三级标题
			String style = xwpfParagraph.getStyle();
			//标题内容
			String text = xwpfParagraph.getParagraphText();
			if(text.contains("、")){
				text = text.substring(text.indexOf("、") + 1, text.length());
			}
			if(style == null){
				continue;
			}
			switch (style) {
				case "1":
					oneToFiveParagraphs.add(text);
					break;
				case "2":
					oneToFiveParagraphs.add(text);
					break;
				case "3":
					oneToFiveParagraphs.add(text);
					break;
				case "4":
					oneToFiveParagraphs.add(text);
					break;
				case "5":
					oneToFiveParagraphs.add(text);
					break;
			}
		}
		
		//获取文档中所有表格
		List<XWPFTable> xwpfTables = xwpfDocument.getTables();
		for (int i=0; i < xwpfTables.size(); i++) {
			Map<String, Object> everyTable = new HashMap<String, Object>();
			//表格序号
			everyTable.put("currenTableIndex", "当前表格序号:" + i);
			
			//表格属性值
			List<Map<String, Object>> tableAttributeValues = new ArrayList<Map<String,Object>>();
			
			XWPFTable xwpfTable = xwpfTables.get(i);
			//获取表格的行
			List<XWPFTableRow> xwpfTableRows = xwpfTable.getRows();
			for (int j=0; j < xwpfTableRows.size(); j++) {
				XWPFTableRow xwpfTableRow = xwpfTableRows.get(j);
				
				//获取表格的每个单元格
				List<XWPFTableCell> xwpfTableCells = xwpfTableRow.getTableCells();
				/**
				 * 第一行为表名
				 * 第二行为属性中文名
				 * 第三行 and more 为属性值
				 */
				
				switch (j) {
					case 0:
						String tableName = xwpfTableRow.getTableCells().get(0).getText();
						String tableChineseName = null;
						everyTable.put("tableName", tableName);
						//根据表名获取表的中文名
						for (String str : oneToFiveParagraphs) {
							str = str.toUpperCase();
							tableName = tableName.toUpperCase();
							if(str.contains(tableName)){
								//截取中文部分
								tableChineseName = str.substring(0, str.indexOf("（"));
								break;
							}
						}
						everyTable.put("tableChineseName", tableChineseName);
						break;
					case 1:
						List<String> attributeChineseName = resolveTableCellValue(xwpfTableCells);
						everyTable.put("attributeChineseName", attributeChineseName);
						break;
					default:
						List<String> attributeValues = resolveTableCellValue(xwpfTableCells);
						Map<String, Object> attributeMap = new HashMap<String, Object>();
						//字段中文名
						attributeMap.put("columnChineseName", attributeValues.get(0));
						//字段名
						attributeMap.put("columnName", attributeValues.get(1));
						//参数值
						attributeMap.put("params", attributeValues.get(2));
						//备注
						String mark = "";
						if(attributeValues.size() > 3){
							mark = attributeValues.get(3);
						}
						attributeMap.put("mark", mark);
						tableAttributeValues.add(attributeMap);
						break;
				}
			}
			everyTable.put("columns", tableAttributeValues);
			result.add(everyTable);
		}
		
		//输出并生成excel
		List<List<Object>> toExcelData = new ArrayList<List<Object>>();
		List<Object> firstRow = new ArrayList<Object>();
		firstRow.add("table");
		firstRow.add("tableName");
		firstRow.add("column");
		firstRow.add("cloumnName");
		firstRow.add("params");
		firstRow.add("mark");
		toExcelData.add(firstRow);
		
		for (Map<String, Object> subResult : result) {
			System.out.println(subResult);
			List<Map<String, Object>> columns = (List<Map<String, Object>>) subResult.get("columns");
			for (Map<String, Object> column : columns) {
				List<Object> data = new ArrayList<Object>();
				data.add(subResult.get("tableName"));
				data.add(subResult.get("tableChineseName"));
				data.add(column.get("columnName"));
				data.add(column.get("columnChineseName"));
				data.add(column.get("params"));
				data.add(column.get("mark"));
				toExcelData.add(data);
			}
		}
		
		ResolveExcel resolveExcel = new ResolveExcel();
		resolveExcel.writeDataToExcel(toExcelData);
	}
	
	/**
	 * 解析单元格的值
	 * @author Kenneth-IVS
	 * @date 2018年4月13日
	 * @param xwpfTableCells
	 * @return
	 */
	public static List<String> resolveTableCellValue(List<XWPFTableCell> xwpfTableCells){
		List<String> result = new ArrayList<String>();
		int xwpfTableCellSize = xwpfTableCells.size();
		for(int i=0; i < xwpfTableCellSize; i++){
			XWPFTableCell xwpfTableCell = xwpfTableCells.get(i);
			String text = xwpfTableCell.getText();
			result.add(text);
		}
		return result;
	}
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		ResolveWord resolveWord = new ResolveWord();
		resolveWord.resolveDocxTable();
	}
}
