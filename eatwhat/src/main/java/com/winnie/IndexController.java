package com.winnie;

import java.io.*;
import java.util.Properties;

public class IndexController {
    public static void main(String[] args) throws ClassNotFoundException, IOException {
//        Properties prop = new Properties();
//        InputStream resourceAsStream = Class.forName("com.winnie.IndexController").getClass().getClassLoader().getResourceAsStream("/soup.json");
//        prop.load(resourceAsStream);
        String soupFilePath = "soup.json";
        File soupFile = new File(soupFilePath);
        FileReader fr = new FileReader(soupFile);
        BufferedReader br = new BufferedReader(fr);
        StringBuilder sb = new StringBuilder();
        String line;
        while((line=br.readLine())!=null){
            sb.append(line);
        }
        br.close();
        String content = sb.toString();


    }
}
