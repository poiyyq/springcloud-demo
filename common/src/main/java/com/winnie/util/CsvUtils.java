package com.winnie.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yanyq
 * @date 2021年06月07日
 */
public class CsvUtils {
    /**
     * 从csv文件中获取数据
     *
     * @param filePath
     * @return
     */
    public static List<Map<String, Object>> getData(String filePath) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        if (filePath == null || filePath.equals("")) {
            throw new RuntimeException("filepath can not be null");
        }
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("file does not exists");
        }
        BufferedReader br = new BufferedReader(new FileReader(file));
        // 第一行为表头
        String line;
        int count = 0;
        String[] title = null;
        while ((line = br.readLine()) != null) {
            if (count == 0) {
                title = line.split(",");
            } else {
                String[] columns = line.split(",");
                Map<String,Object> row = new HashMap<>();
                for (int i = 0; i < columns.length; i++) {
                    row.put(title[i].trim(),columns[i].trim());
                }
                list.add(row);
            }
            count++;
        }
        br.close();
        return list;
    }
}
