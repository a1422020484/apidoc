package webmvct.jsontest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ReadJsonTest5 {
	
	public static void main(String[] args) {
		List<Map> listJson = new ArrayList<Map>();
		String url = "/products";
		String address = "src/main/resources/data/swagger5.json";
        // 读取原始json文件并进行操作和输出  
        try {  
            BufferedReader br = new BufferedReader(new FileReader(address));// 读取原始json文件  
            String s = null;
            while ((s = br.readLine()) != null) {  
                // System.out.println(s);  
                try {  
                    JSONObject dataJson = new JSONObject(s);// 创建一个包含原始json串的json对象
                    
                    //获得第一个级别
                    String[] firstKeyArray = JSONObject.getNames(dataJson);
                    for(String firstKey : firstKeyArray){
                    	System.out.println("firstKey==="+firstKey);
                    	JSONObject secondObj = dataJson.getJSONObject(firstKey);
                    	String[] secondKeyArray = JSONObject.getNames(secondObj);
                    	for(String secondKey : secondKeyArray){
                    		System.out.println("secondKey==="+secondKey);
                        	JSONObject threeObj = dataJson.getJSONObject(secondKey);
                        	String[] threeKeyArray = JSONObject.getNames(threeObj);
                        	for(String threeKey : threeKeyArray){
                        		System.out.println("threeKey==="+threeKey);
                            	JSONObject fourObj = dataJson.getJSONObject(threeKey);
                            	String[] fourKeyArray = JSONObject.getNames(fourObj);
                            	for(String fourKey : fourKeyArray){
                            		System.out.println("fourKey==="+fourKey);
                                	JSONObject fiveObj = dataJson.getJSONObject(fourKey);
                                	String[] foveKeyArray = JSONObject.getNames(fiveObj);
                                	for(String sexKey : foveKeyArray){
                                		System.out.println("sexKey==="+sexKey);
                                    	JSONObject sexObj = dataJson.getJSONObject(sexKey);
                                    	String[] sexKeyArray = JSONObject.getNames(sexObj);
                                    	for(String sevenKey : sexKeyArray){
                                    		System.out.println("sevenKey==="+sevenKey);
                                    	}
                                	}
                            	}
                        	}
                    	}
                    }
                    
                    
//                    //1、从json串中获得key为type所对应的值
//                    String name = dataJson.getString("paths");//从json串中获得key为type所对应的值
//                    System.out.println(name);
//                    JSONObject pathsObj = dataJson.getJSONObject("paths");// 找到properties的json对象  
//                    String[] pathArray = JSONObject.getNames(pathsObj);
//                    for(String path : pathArray){
//                    	System.out.println(path);
//                    	if(url.equals(path)){
//                    		JSONObject urlF = pathsObj.getJSONObject(path);
//                    		String[] methodArray = JSONObject.getNames(urlF);
//                    		for(String method : methodArray){
//                    			if("get".equals(method)){
//                    				JSONObject methodF = urlF.getJSONObject(method);
//                    				String[] getArray = JSONObject.getNames(methodF);
//                    				for(String para : getArray){
//                    					if("parameters".equals(para)){
//                    						JSONArray parametersArray = methodF.getJSONArray(para);
//                    						for(int i=0;i<parametersArray.length();i++){
//                    							JSONObject paraObject = parametersArray.getJSONObject(i);
//                    				            String[] paraNameArray = JSONObject.getNames(paraObject);
//                    				            HashMap<String,Object> map = new HashMap<String,Object>();
//                    				            for(String paramName : paraNameArray){
//                    				            	String paraValue = paraObject.get(paramName).toString();
//                    				            	map.put(paramName, paraValue);
//                    				            }
//                    				            System.out.println(map);
//                    				            listJson.add(i, map);
//                    				        }
//                    					}
//                    				}
//                    			}
//                    		}
//                    	}
//                    }
                } catch (JSONException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
            }  
            br.close();  
  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }
	}
}
