package com.winnie.util;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ExcelUtilsTest {

    @Test
    public void readExcelTest2() throws IOException {
        String key = "bill_main_id";
        String value = "value";
        String fileCounttPath = "/Users/admin/Downloads/工作簿4.xlsx";
        String fileCountt2Path = "/Users/admin/Downloads/工作簿9.xlsx";
        String fileCountt3Path = "/Users/admin/Downloads/工作簿10.xlsx";
        List<Map<String, Object>> data1 = ExcelUtils.getData(fileCounttPath);
        List<Map<String, Object>> data2 = ExcelUtils.getData(fileCountt2Path);
        List<Map<String, Object>> data3 = ExcelUtils.getData(fileCountt3Path);
        for (int i = 0; i < data3.size(); i++) {
            Map<String, Object> data3Map = data3.get(i);
            String payType = String.valueOf(data3Map.get("支出类型"));
            data1.forEach(data->{
                String targetPayType = String.valueOf(data.get("支出类型"));
                if(payType.equals(targetPayType)){
                    String currKey = "费用类型";
                    String feeType = String.valueOf(data.get(currKey));
                    data3Map.put(currKey,feeType);
                }
            });
            data2.forEach(data->{
                String targetPayType = String.valueOf(data.get("支出类型"));
                if(payType.equals(targetPayType)){
                    String currKey = "报表类型";
                    String biType = String.valueOf(data.get(currKey));
                    data3Map.put(currKey,biType);
                }
            });
        }
        List<String> titleList = Arrays.asList("支出类型", "管报科目", "费用类型", "报表类型");
        ExcelUtils.exportDatas(data3, titleList, "/Users/admin/Downloads/","outFile2.xlsx");


        System.out.println();
    }

    @Test
    public void readExcelTest() throws IOException {
        String key = "bill_main_id";
        String value = "value";
        String filePath = "/Users/admin/Downloads/datas.xls";
        String fileCounttPath = "/Users/admin/Downloads/countt.xlsx";
        String fileCountt2Path = "/Users/admin/Downloads/countt2.xlsx";
        String fileCountt3Path = "/Users/admin/Downloads/countt3.xlsx";
        List<Map<String, Object>> datas = ExcelUtils.getData(filePath);
        List<Map<String, Object>> dataCountt = ExcelUtils.getData(fileCounttPath);
        List<Map<String, Object>> dataCountt2 = ExcelUtils.getData(fileCountt2Path);
        List<Map<String, Object>> dataCountt3 = ExcelUtils.getData(fileCountt3Path);
        datas.forEach(data->{
            String billMainId = String.valueOf(data.get("主表id"));
            dataCountt.forEach(obj -> {
                String srcBillMainId = String.valueOf(obj.get(key));
                if(billMainId.equals(srcBillMainId)){
                    data.put("供应商",obj.get(value));
                }
            });
            dataCountt2.forEach(obj ->{
                String srcBillMainId = String.valueOf(obj.get(key));
                if(billMainId.equals(srcBillMainId)){
                    data.put("供应商",obj.get(value));
                }
            });
            dataCountt3.forEach(obj -> {
                String srcBillMainId = String.valueOf(obj.get(key));
                if(billMainId.equals(srcBillMainId)){
                    data.put("签约对方全称",obj.get(value));
                }
            });
        });

        List<String> titleList = Arrays.asList("支出类型", "", "说明", "创建日期", "申请人", "申请人部门", "合计金额", "合计金额币种", "单据状态", "付款状态", "供应商", "签约对方全称");
        ExcelUtils.exportDatas(datas, titleList, "/Users/admin/Downloads/","outFile.xlsx");


        System.out.println();
    }

    @Test
    public void readWechatPayExcel() throws IOException {
        String filePath = "/Users/admin/Downloads/微信支付账单(20210501-20210531)/微信支付账单(20210501-20210531).csv";
        List<Map<String, Object>> datas = ExcelUtils.getData(filePath);

//        ExcelUtils.exportDatas(datas, titleList, "/Users/admin/Downloads/","outFile.xlsx");


        System.out.println();
    }

}
