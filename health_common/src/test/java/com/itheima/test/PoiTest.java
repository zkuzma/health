package com.itheima.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @auther 大雄
 * @create 2020-04-05 13:48
 */
public class PoiTest {

//    @Test
    public void readExcel() throws IOException {
        //创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook("E:\\read.xlsx");
        //获取工作表，既可以根据工作表的顺序获取，也可以根据工作表的名称获取
        XSSFSheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            for (Cell cell : row) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                String s = cell.getStringCellValue();
                System.out.println(s);
            }
        }
        //遍历工作表获得行对象
    /*    for (Row row : sheet) {
            //遍历行对象获取单元格对象
            for (Cell cell : row) {
                //获得单元格中的值
                String value = cell.getStringCellValue();
                System.out.println(value);
            }
        }*/
        workbook.close();
    }
//    @Test
    public void testRead2() throws IOException {
        XSSFWorkbook sheets = new XSSFWorkbook("E:\\read.xlsx");
        XSSFSheet sheetAt = sheets.getSheetAt(0);
        //获取最后一行行号
        int lastRowNum = sheetAt.getLastRowNum();
        System.out.println("最后一行的行号:"+lastRowNum);
        for (int i = 1; i <= lastRowNum; i++) {
            XSSFRow row = sheetAt.getRow(i);
            short lastCellNum = row.getLastCellNum();
            System.out.println("最后一列的列号:"+lastCellNum);
            for (short j=0;j<lastCellNum;j++){
                XSSFCell cell = row.getCell(j);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                System.out.println(cell.getStringCellValue());
            }
            System.out.println("******");
        }
        sheets.close();

    }

//    @Test
    public void testWrite() throws IOException {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        XSSFSheet sheet = xssfWorkbook.createSheet("传智播客");

        XSSFRow titleRow = sheet.createRow(0);

        titleRow.createCell(0).setCellValue("编号");
        titleRow.createCell(1).setCellValue("姓名");
        titleRow.createCell(2).setCellValue("年龄");
        //创建数据行
        XSSFRow dataRow = sheet.createRow(1);

        //创建数据列
        dataRow.createCell(0).setCellValue("1");
        dataRow.createCell(1).setCellValue("大雄");
        dataRow.createCell(2).setCellValue("21");

        OutputStream outputStream = new FileOutputStream("E:\\create.xlsx");
        xssfWorkbook.write(outputStream);
        outputStream.flush();
        outputStream.close();

        xssfWorkbook.close();
    }
}
