//package com.winnie.config;
//
//import com.github.kristofa.brave.Brave;
//import com.github.kristofa.brave.EmptySpanCollectorMetricsHandler;
//import com.github.kristofa.brave.Sampler;
//import com.github.kristofa.brave.SpanCollector;
//import com.github.kristofa.brave.http.DefaultSpanNameProvider;
//import com.github.kristofa.brave.http.HttpSpanCollector;
//import com.github.kristofa.brave.okhttp.BraveOkHttpRequestResponseInterceptor;
//import com.github.kristofa.brave.servlet.BraveServletFilter;
//import lombok.Data;
//import okhttp3.OkHttpClient;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Data
//@Configuration
//@ConfigurationProperties(prefix = "zipkin")
//public class ZipkinConfig {
//
//    /**
//     * 服务名称
//     */
//    private String serviceName;
//
//    /**
//     * zipkin地址
//     */
//    private String url;
//
//    /**
//     * 连接时间
//     */
//    private int connectTimeout;
//
//    /**
//     * 读取时间
//     */
//    private int readTimeout;
//
//    /**
//     * 每间隔多少秒执行一次Span信息上传
//     */
//    private int flushInterval;
//
//    /**
//     * 是否启动压缩
//     */
//    private boolean compressionEnabled;
//
//    /**
//     * （一次请求信息或者一次链路调用）信息收集器
//     * @return
//     */
//    @Bean
//    public SpanCollector spanCollector(){
//        HttpSpanCollector.Config config = HttpSpanCollector.Config.builder()
//                .compressionEnabled(compressionEnabled)
//                .flushInterval(flushInterval)
//                .connectTimeout(connectTimeout)
//                .readTimeout(readTimeout)
//                .build();
//        return HttpSpanCollector.create(url,config, new EmptySpanCollectorMetricsHandler());
//    }
//
//    /**
//     * 作为各调用链路，只需要负责将指定格式的数据发送给zipkin
//     * @param spanCollector
//     * @return
//     */
//    @Bean
//    public Brave brave(SpanCollector spanCollector){
//        // 服务名
//        Brave.Builder builder = new Brave.Builder(serviceName);
//        // 设置采集器
//        builder.spanCollector(spanCollector);
//        // 设置采集率
//        builder.traceSampler(Sampler.ALWAYS_SAMPLE);
//        return builder.build();
//    }
//
//    /**
//     * 设置server的（服务端收到请求和服务端完成处理，并将结果发送给客户端）过滤器
//     * @param brave
//     * @return
//     */
//    @Bean
//    public BraveServletFilter braveServletFilter(Brave brave){
//        BraveServletFilter filter = new BraveServletFilter(brave.serverRequestInterceptor(), brave.serverResponseInterceptor(), new DefaultSpanNameProvider());
//        return filter;
//    }
//
//    /**
//     * 设置client的（发起请求和获取到服务端返回信息）拦截器
//     * @param brave
//     * @return
//     */
//    @Bean
//    public OkHttpClient okHttpClient(Brave brave){
//        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(new BraveOkHttpRequestResponseInterceptor(
//                brave.clientRequestInterceptor(), brave.clientResponseInterceptor(), new DefaultSpanNameProvider()
//        )).build();
//        return client;
//    }
//}
