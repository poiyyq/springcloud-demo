import com.winnie.util.CsvUtils;
import com.winnie.util.ObjectUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * 统计每月花销
 */
public class CountDayliPay {

    // 餐饮
    private final static String foods = "午餐,晚餐,宵夜,速冻,乐易购,食品,寿司,砂锅粥,正新鸡排,嫩牛五方,生煎先生,牛杂,天猫超市零食,永旺,薯片,椰子鸡,朴朴,超市,锅盔,煲仔饭,七个粉,煌上煌,戈饭,番茄酱,广州珅珅府餐饮管理有限责任公司,两颗鸡蛋,汉堡王,米仓食堂（正佳广场）,顺客来,湘岳城市蒸菜,粮油,叮咚买菜,福州朴朴电子商务有限公司,芙蓉兴盛便利超市,友宝,天猫小店启明超市,饿了么,广州市天河区车陂购易乐商店,东北大饼,华洋百货商店,芙蓉兴盛便利超市,苏江南广州员村店,余秋锐,俏碗里广州猎德店,温记,佛山市顺德区众缘融合餐饮店," +
            "佛山市虾鱼炳餐饮服务有限公司,金榜老街坊姜撞奶,渔乡米坊(凤山店),新惠便利店,黄记干货,一碌木寿司（东圃店）,快线便利店,北京每日优鲜电子商务有限公司";
    // 利息
    private final static String lixi = "爷爷利息,姐姐还款,APPLE,时来运转";
    // 交通
    private final static String traffic = "单车,打车,广州骑安,滴滴出行,羊城通,智行,哈啰出行,金米尼旗舰店,高德打车入驻商户,顺丰速运,有鹏出行";
    // 酒水
    private final static String water = "茶理宜世,啤酒,贡茶,下午茶,茶百道,咖啡,果茶,汽水,挞柠,果粒橙,luckin coffee,新作の茶";
    // 水果
    private final static String fruit = "水果,优鲜果车陂南店";
    // 邮费
    private final static String post = "邮费,快递,顺丰快递 车陂南";
    // 娱乐
    private final static String happy = "ktv,娱乐,奥冠体育,广州UA花城汇电影城,莲花山旅游区票务部（自助售票）";
    // 日用品
    private final static String dayUser = "球拍,炒锅,搓澡器,纸巾,宽带,路由器,手机充值,风扇,送礼,理发,小易到家,名创优品广州正佳广场2店,广州正佳网现代服务有限公司,镜头";
    // 穿搭
    private final static String wear = "迪卡侬DECATHLON";
    // 亲情卡
    private final static String relation = "宝宝";
    // 保险
    private final static String insurance = "众安在线财产保险股份有限公司,中国人民人寿保险股份有限公司";
    // 其他
    private final static String other = "张galary,发给Q-终生敬爱的妈妈,致远创想,发出群红包,其他,充电宝,信用借还服务商";
    private final static String allCategory = "餐饮,利息,交通,酒水,娱乐,日用品,邮费,穿搭,亲情卡,保险,其他";

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // 支付宝
        String alipayFilePath = "/Users/admin/Downloads/alipay_record_20210604_173221.csv";
        Map<String, Map<String, Double>> aliPayData = getAliPayData(alipayFilePath);
        // 微信
        String wechatFilePath = "/Users/admin/Downloads/微信支付账单(20210501-20210531)/微信支付账单(20210501-20210531).csv";
        Map<String, Map<String, Double>> wechatData = getWechatData(wechatFilePath);
        // 数据融合
        Map<String, Map<String, Double>> dataMap = unionData(aliPayData,wechatData);
        // 数据汇总
        dataSummary(dataMap);
        // 倒序
        dataMap = dataReverse(dataMap);

//         填充数据
//        Map<String, Map<String,Double>> dataMap = fillData(filePath);
        // 数据统计，计算总金额
        calMoney(dataMap);
        // 输出
        dataOutput(dataMap);
    }

    /**
     * 倒序
     * @param dataMap
     */
    private static Map<String, Map<String, Double>> dataReverse(Map<String, Map<String, Double>> dataMap) {
        Map<String, Map<String, Double>> result = new LinkedHashMap<>(dataMap.size());
        ListIterator<Map.Entry<String, Map<String, Double>>> listIterator = new ArrayList<>(dataMap.entrySet()).listIterator(dataMap.size());
        while(listIterator.hasPrevious()){
            Map.Entry<String, Map<String, Double>> entry = listIterator.previous();
            String date = entry.getKey();
            Map<String, Double> value = entry.getValue();
            result.put(date,value);
        }
        return result;
    }

    /**
     * 数据汇总
     * @param dataMap
     */
    private static void dataSummary(Map<String, Map<String, Double>> dataMap) throws IOException, ClassNotFoundException {
        Set<String> keys = dataMap.keySet();
        for (String date : keys) {
            Map<String, Double> map = dataMap.get(date);
            Set<String> categories = map.keySet();
            // categories 需要做深拷贝，否则后续修改map会影响到当前categories（指针问题）
            Set<String> newCategories = new HashSet<>(categories.size());
            for (String category : categories) {
                newCategories.add(category);
            }
            for (String category : newCategories) {
                Double currMoney = map.get(category);
                if(foods.contains(category)){
                    category = "餐饮";
                } else if(lixi.contains(category)){
                    category = "利息";
                } else if(traffic.contains(category)){
                    category = "交通";
                } else if(water.contains(category)){
                    category = "酒水";
                } else if(fruit.contains(category)){
                    category = "水果";
                } else if(dayUser.contains(category)){
                    category = "日用品";
                } else if(happy.contains(category)){
                    category = "娱乐";
                } else if(post.contains(category)){
                    category = "邮费";
                } else if(wear.contains(category)){
                    category = "穿搭";
                } else if(relation.contains(category)){
                    category = "亲情卡";
                } else if(insurance.contains(category)){
                    category = "保险";
                } else if(other.contains(category)){
                    category = "其他";
                }
                Double money = 0.0;
                if(map.containsKey(category)){
                    money = map.get(category);
                }
                money+=currMoney;
                map.put(category,money);
            }
        }
    }

    /**
     * 数据融合
     * @param aliPayData
     * @param wechatData
     * @return
     */
    private static Map<String, Map<String, Double>> unionData(Map<String, Map<String, Double>> aliPayData, Map<String, Map<String, Double>> wechatData) {
        Set<String> alipayKeySet = aliPayData.keySet();
        Map<String, Map<String, Double>> result = new LinkedHashMap<>();
        for (String date : alipayKeySet) {
            // 填充返回数据
            Map<String, Double> map = new HashMap<>();
            result.put(date,map);

            // 深度拷贝
            Map<String, Double> alipayMap = aliPayData.get(date);
            Set<String> nameKeys = alipayMap.keySet();
            for (String nameKey : nameKeys) {
                Double money = alipayMap.get(nameKey);
                map.put(nameKey,money);
            }

            // 加上微信数据
            Map<String, Double> wechatMap = wechatData.get(date);
            if(wechatMap!=null && wechatMap.size()>0){
                Set<String> wnameKeys = wechatMap.keySet();
                for (String nameKey : wnameKeys) {
                    Double money = 0.0;
                    if(map.containsKey(nameKey)){
                        money = map.get(nameKey);
                    }
                    money += wechatMap.get(nameKey);
                    map.put(nameKey,money);
                }
            }
        }
        return result;
    }

    /**
     * 数据输出
     * @param dataMap
     */
    private static void dataOutput(Map<String, Map<String, Double>> dataMap) {
        /**
         * 2021-04-29 日计 234
         * 餐饮 149.2
         * 水果 24.9
         * 日用品 44.9
         * 交通 5
         * 红包 10
         */
        Set<String> dates = dataMap.keySet();
        for (String date : dates) {
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            sb.append(date);
            Map<String, Double> map = dataMap.get(date);
            Double totalMoney = map.get("totalMoney");
            sb.append(" 总计 ")
                    .append(totalMoney)
                    .append("\n");
            Set<String> catogarys = map.keySet();
            for (String catogary : catogarys) {
                if(catogary.equals("totalMoney")){
                    continue;
                }
                if(allCategory.contains(catogary)){
                    sb2.append(catogary)
                            .append(" ")
                            .append(map.get(catogary))
                            .append("\n");
                } else {
                    sb.append(catogary)
                            .append(" ")
                            .append(map.get(catogary))
                            .append("\n");
                }
            }
            System.out.println(sb.toString() + "----------\n" + sb2.toString());
        }
    }

    /**
     * 计算总金额
     * @param dataMap
     */
    private static void calMoney(Map<String, Map<String, Double>> dataMap) {
        Set<String> dates = dataMap.keySet();
        for (String date : dates) {
            Map<String, Double> map = dataMap.get(date);
            Set<String> valueMap = map.keySet();
            Double totalMoney = 0.0;
            for (String category : valueMap) {
                if(allCategory.contains(category)){
                    Double value = map.get(category);
                    totalMoney+=value;
                }
            }
            map.put("totalMoney", totalMoney);
        }
    }

    /**
     * 填充数据
     * @param filePath
     */
    private static Map<String,Map<String,Double>> fillData(String filePath) throws IOException {
        Map<String,Map<String,Double>> dataMap = new LinkedHashMap<>();
        File file = new File(filePath);
        if(!file.exists()){
            System.exit(-1);
        }
        BufferedReader br = new BufferedReader(new FileReader(file));
        String key = null;
        String word ;
        while ((word=br.readLine()) != null){
            if(word.equals("") || word.length()==0){
                continue;
            }
            if(word.startsWith("2021-")){
                key = word;
            } else {
                Map<String,Double> map;
                if(dataMap.containsKey(key)){
                    map = dataMap.get(key);
                } else {
                    map = new HashMap<>();
                    dataMap.put(key,map);
                }
                String[] words = word.split(" ");
                String calculation2 = words[1];
                String[] calculations2 = calculation2.split("\\+");
                Double total2=0.0;
                for (int i = 0; i < calculations2.length; i++) {
                    Double money = Double.parseDouble(calculations2[i]);
                    total2 += money;
                }
                map.put(words[0], total2);
                String catagory = words[0];
                if(foods.contains(catagory)){
                    catagory = "餐饮";
                } else if(lixi.contains(catagory)){
                    catagory = "利息";
                } else if(traffic.contains(catagory)){
                    catagory = "交通";
                } else if(water.contains(catagory)){
                    catagory = "酒水";
                } else if(fruit.contains(catagory)){
                    catagory = "水果";
                } else if(dayUser.contains(catagory)){
                    catagory = "日用品";
                } else if(happy.contains(catagory)){
                    catagory = "娱乐";
                } else if(post.contains(catagory)){
                    catagory = "邮费";
                } else if(wear.contains(catagory)){
                    catagory = "穿搭";
                } else if(other.contains(catagory)){
                    catagory = "其他";
                }
                String calculation = words[1];
                String[] calculations = calculation.split("\\+");
                Double total=0.0;
                if(map.containsKey(catagory)){
                    total = map.get(catagory);
                }
                for (int i = 0; i < calculations.length; i++) {
                    Double money = Double.parseDouble(calculations[i]);
                    total += money;
                }
                map.put(catagory, total);
            }
        }
        br.close();

        return dataMap;
    }

    /**
     * 获取微信账单
     * @param filePath
     * @return
     * @throws IOException
     */
    private static Map<String,Map<String,Double>> getWechatData(String filePath) throws IOException {
        List<Map<String, Object>> list = CsvUtils.getData(filePath);
        // 微信账单
        Map<String,Map<String,Double>> wechatMap = new LinkedHashMap<>();
        for (Map<String, Object> data : list) {
            String time = String.valueOf(data.get("交易时间"));
            DateTime dateTime = DateTime.parse(time, DateTimeFormat.forPattern("yyyy/M/d HH:mm"));
            String dateStr = dateTime.toString("yyyy-MM-dd");

            // 获取日期对象
            Map<String, Double> map;
            if(wechatMap.containsKey(dateStr)){
                map = wechatMap.get(dateStr);
            } else {
                map = new HashMap<>();
                wechatMap.put(dateStr,map);
            }
            // 设置名称+金额
            String name = String.valueOf(data.get("交易对方"));
            Double money = 0.0;
            if(map.containsKey(name)){
                money = map.get(name);
            }
            money += Double.parseDouble(String.valueOf(data.get("金额(元)")));
            map.put(name,money);
        }
        return wechatMap;
    }

    /**
     * 获取支付宝账单
     * @return
     * @throws IOException
     */
    private static Map<String,Map<String,Double>> getAliPayData(String filePath) throws IOException {
        List<Map<String, Object>> list = CsvUtils.getData(filePath);
        // 支付宝账单
        Map<String,Map<String,Double>> alipayMap = new LinkedHashMap<>();
        for (Map<String, Object> data : list) {
            String time = String.valueOf(data.get("交易时间"));
            DateTime dateTime = DateTime.parse(time, DateTimeFormat.forPattern("yyyy/M/d HH:mm"));
            String dateStr = dateTime.toString("yyyy-MM-dd");

            // 获取日期对象
            Map<String, Double> map;
            if(alipayMap.containsKey(dateStr)){
                map = alipayMap.get(dateStr);
            } else {
                map = new HashMap<>();
                alipayMap.put(dateStr,map);
            }
            // 设置名称+金额
            String name = String.valueOf(data.get("交易对方"));
            Double money = 0.0;
            if(map.containsKey(name)){
                money = map.get(name);
            }
            money += Double.parseDouble(String.valueOf(data.get("金额")));
            map.put(name,money);
        }
        return alipayMap;
    }
}
