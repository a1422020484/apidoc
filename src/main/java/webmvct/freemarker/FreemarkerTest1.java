package webmvct.freemarker;

import freemarker.template.Template; 

import java.io.OutputStreamWriter; 
import java.io.StringReader; 
import java.util.HashMap; 
import java.util.Map; 

public class FreemarkerTest1 {
	 public static void main(String[] args) throws Exception{ 
         //创建一个模版对象 
         Template t = new Template(null, new StringReader("用户名：${user};URL：    ${url};姓名： 　${name}"), null); 
         //创建插值的Map 
         Map<String,String> map = new HashMap<String,String>(); 
         map.put("user", "lavasoft"); 
         map.put("url", "http://www.baidu.com/"); 
         map.put("name", "百度"); 
         //执行插值，并输出到指定的输出流中 
         t.process(map, new OutputStreamWriter(System.out)); 
 } 
}
