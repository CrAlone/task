package com.cr.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

import java.awt.image.IndexColorModel;

public class ExcelTool {
    public static HSSFWorkbook createHSSFWorkbook(String sheetName,String[] title,String[][] data){
        //第一步，创建一个workbook对象，对应的是一个excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        //第二步，使用workbook创建一个sheet页
        HSSFSheet sheet = workbook.createSheet(sheetName);
        //第三步，使用sheet对象 创建表头 第0行 操作excel 序号都要从0开始
        HSSFRow row = sheet.createRow(0);
            //3.1可以设置表格样式
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            //设置居中
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            //设置边框
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.DOUBLE);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            //背景色
            cellStyle.setFillBackgroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
            cellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            //字体
            HSSFFont font = workbook.createFont();
            font.setFontName("宋体");
            //将字体添加到style中
            cellStyle.setFont(font);
        //第四步，写标题
        for (int i = 1;i<title.length;i++){
            //第五步，使用row创建单元格对象cell
            HSSFCell cell = row.createCell(i);
            //添加样式
            cell.setCellStyle(cellStyle);
            //第六步，设置单元格内容
            cell.setCellValue(title[i]);
        }
        //第七步，填充数据类容
        for (int i =0;i<data.length;i++){
            row = sheet.createRow(i+1);//从标题下一行开始
            //设置行高，列宽为自适应（应用于整个工作表）
            sheet.autoSizeColumn((short)i);
            for (int j = 0; j<data[i].length;j++){
                //写内容到单元格
                HSSFCell c = row.createCell(j);
                c.setCellValue(data[i][j]);
            }
        }
        return workbook;
    }
}
