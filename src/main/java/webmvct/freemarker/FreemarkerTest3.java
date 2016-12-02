package webmvct.freemarker;

import java.io.File;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import freemarker.template.Configuration;
import freemarker.template.Template; 

public class FreemarkerTest3 {
	private Configuration cfg;            //模版配置对象 

    public void init() throws Exception { 
            //初始化FreeMarker配置 
            //创建一个Configuration实例 
            cfg = new Configuration(); 
            //设置FreeMarker的模版文件夹位置 
            cfg.setDirectoryForTemplateLoading(new File("src/main/resources/template")); 
    } 

    public void process() throws Exception { 
//            //构造填充数据的Map 
//            Map<String,String> map = new HashMap<String,String>(); 
//            map.put("user", "lavasoft"); 
//            map.put("url", "http://www.baidu.com/"); 
//            map.put("name", "百度"); 
//            //创建模版对象 
//            Template t = cfg.getTemplate("FreemarkerTest2.ftl"); 
//            //在模版上执行插值操作，并输出到制定的输出流中 
//            t.process(map, new OutputStreamWriter(System.out)); 
    		JSONObject map = new JSONObject();
    		map.put("user", "lavasoft"); 
            map.put("url", "http://www.baidu.com/"); 
            map.put("name", "百度"); 
            Template t = cfg.getTemplate("FreemarkerTest2.ftl"); 
//          //在模版上执行插值操作，并输出到制定的输出流中 
            t.process(map, new OutputStreamWriter(System.out)); 
    } 

    public static void main(String[] args) throws Exception { 
    		FreemarkerTest3 hf = new FreemarkerTest3(); 
            hf.init(); 
            hf.process(); 
    } 
}
