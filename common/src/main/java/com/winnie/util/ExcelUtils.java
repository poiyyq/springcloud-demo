package com.winnie.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbookFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {

    public static void exportDatas(List<Map<String,Object>> dataList, List<String> titleList, String outFilePath, String outFileName) throws IOException {
        File file = new File(outFilePath + outFileName);
        if(!file.exists()){
            file.createNewFile();
        }
        HSSFWorkbook wb = HSSFWorkbookFactory.createWorkbook();
//        Workbook wb = HSSFWorkbookFactory.create(file);
        Sheet sheet = wb.createSheet();
        // 设置表头
        Row titleRow = sheet.createRow(0);
        for (int i = 0; i < titleList.size(); i++) {
            Cell cell = titleRow.createCell(i);
            String titleName = titleList.get(i);
            cell.setCellValue(titleName);
        }
        // 设置内容
        for (int i = 0; i < dataList.size(); i++) {
            Row row = sheet.createRow(i + 1);
            Map<String, Object> map = dataList.get(i);
            for (int j = 0; j < titleList.size(); j++) {
                String titleName = titleList.get(j);
                Object dataValue = map.get(titleName);
                Cell cell = row.createCell(j);
                if(dataValue!=null){
                    if(dataValue instanceof String){
                        cell.setCellValue(String.valueOf(dataValue));
                    } else if(dataValue instanceof Integer){
                        cell.setCellValue(Integer.parseInt(String.valueOf(dataValue)));
                    }
                }
            }
        }

        OutputStream outputStream = new FileOutputStream(file);
        wb.write(outputStream);
        wb.close();
    }

    /**
     * 读取xlsx文件，需要有表头
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static List<Map<String, Object>> getData(String filePath) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        Workbook wb = HSSFWorkbookFactory.create(new File(filePath));
        Sheet sheetAt = wb.getSheetAt(0);
        int physicalNumberOfRows = sheetAt.getPhysicalNumberOfRows();

        // 获取列宽
        int size = sheetAt.getRow(0).getPhysicalNumberOfCells();
        // 获取title
        List<String> keyTitle = getTitle(sheetAt);
        // 组装数据对象
        for (int i = 1; i < physicalNumberOfRows; i++) {
            Row row = sheetAt.getRow(i);
            Map<String, Object> map = new HashMap<>();
            for (int j = 0; j < size; j++) {
                Cell cell = row.getCell(j);
                if (cell != null) {
                    String key = keyTitle.get(j);
                    map.put(key, cell.getStringCellValue());
                }
            }
            list.add(map);
        }
        wb.close();
        return list;
    }

    /**
     * 获取title集合
     *
     * @param sheetAt
     * @return
     */
    private static List<String> getTitle(Sheet sheetAt) {
        Row row = sheetAt.getRow(0);
        int physicalNumberOfCells = row.getPhysicalNumberOfCells();
        List<String> keyTitle = new ArrayList<>();
        for (int i = 0; i < physicalNumberOfCells; i++) {
            keyTitle.add(row.getCell(i).getStringCellValue());
        }
        return keyTitle;
    }


}
