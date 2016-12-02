package webmvct.jsontest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import webmvct.bean.User;


public class ReadJsonTest7 {
	public static void main(String[] args) throws IOException {
		String url = "/products";
		String address = "src/main/resources/data/swagger7.json";
		
		System.out.println(ReadJsonTest7.tranJson(address, url)); 
	}
	
	private static List<Map> tranJson(String address,String url) throws IOException{
		List<Map> listJson = new ArrayList<Map>();
		BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/data/swagger_new.json"));// 输出新的json文件  
        // 读取原始json文件并进行操作和输出  
        try {  
            BufferedReader br = new BufferedReader(new FileReader(address));// 读取原始json文件  
            String s = null;
            while ((s = br.readLine()) != null) {  
                // System.out.println(s);  
                try {  
                    JSONObject dataJson = new JSONObject(s);// 创建一个包含原始json串的json对象
//                    //1、从json串中获得key为type所对应的值
//                    String name = dataJson.getString("paths");//从json串中获得key为type所对应的值
//                    System.out.println(name);
                    JSONObject definitionsObj = dataJson.getJSONObject("definitions");// 找到properties的json对象  
                    String[] definitionsArray = JSONObject.getNames(definitionsObj);
                    for(String definitions : definitionsArray){
                    	int i=0;
                    	System.out.println(definitions);
                    	JSONObject definitionsValue = definitionsObj.getJSONObject(definitions);
                    	HashMap<String,Object> definitionsMap = new HashMap<String,Object>();
                    	definitionsMap.put(definitions, definitionsValue);
                    	listJson.add(i,definitionsMap);
                    	i++;
//                    	System.out.println(urlF);
//                		String[] methodArray = JSONObject.getNames(urlF);
                    }
                    System.out.println("dataJson========="+dataJson.toString());
                    String jsonData = dataJson.toString();
                    for(int i=0;i<listJson.size();i++){
                    	String oldChar = "\"$ref\":\"#/definitions/"+definitionsArray[i]+"\"";
                    	String newChar = ReadJsonTest7.getDefinitions(definitionsArray,listJson,definitionsArray[i]);
                    	System.out.println("oldChar==="+oldChar);
                    	System.out.println("newChar==="+newChar);
                    	jsonData.replace(oldChar, newChar)  ;
                    }
                    
                    bw.write(jsonData);
                } catch (JSONException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
            } 
            bw.flush();
            
            br.close();  
            bw.close();
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }
		return listJson;  
	}
	
	
	public static String getDefinitions(String[] definitionsArray,List<Map> jsondata,String definitions){
		String definitionsValue ="";
		for(int i=0;i<jsondata.size();i++){
			Map<String,Object> map = jsondata.get(i);
			System.out.println(map);
			String keyset = map.keySet().toString();
			System.out.println(map.keySet().toString());
			definitions = "["+definitions+"]";
			if(keyset == definitions){
				definitionsValue = map.get(definitions).toString();
			}else{
				continue;
			}
		}
		return definitionsValue;
	}
}
