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


public class ReadJsonTest8 {
	public static void main(String[] args) {
		String url = "/history";
		String address = "src/main/resources/data/swagger4.json";
		System.out.println(ReadJsonTest8.tranJson(address, url)); 
	}
	
	private static List<Map> tranJson(String address,String url){
		List<Map> listJson = new ArrayList<Map>();
        
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
                    System.out.println(dataJson);
                    System.out.println(dataJson.get("definitions"));
                    //获得所有definitions附加的数据
                    HashMap<String,Object> definitionsMap = ReadJsonTest8.getDefinitions(dataJson);
                    
                    
                    JSONObject pathsObj = dataJson.getJSONObject("paths");// 找到properties的json对象  
                    String[] pathArray = JSONObject.getNames(pathsObj);
                    for(String path : pathArray){
                    	System.out.println(path);
                    	if(url.equals(path)){
                    		JSONObject urlObjMap = (JSONObject) pathsObj.get(path);
                    		
                    		JSONObject urlF = pathsObj.getJSONObject(path);
                    		String[] methodArray = JSONObject.getNames(urlF);
                    		for(String method : methodArray){
                    			if("get".equals(method)){
                    				JSONObject methodObjMap = (JSONObject) urlF.get(method);
                    				
                    				JSONObject methodF = urlF.getJSONObject(method);
                    				String[] getArray = JSONObject.getNames(methodF);
                    				for(String para : getArray){
                    					if("responses".equals(para)){
                    						JSONObject paraObjMap =  (JSONObject)methodF.get(para);
                    						
//                    						JSONArray parametersArray = methodF.getJSONArray(para);
                    						JSONObject responsesF = methodF.getJSONObject(para);
                            				String[] responsesArray = JSONObject.getNames(responsesF);
                            				System.out.println(responsesArray);
                            				for(String code :responsesArray){
                            					JSONObject codeObjMap =  (JSONObject)responsesF.get(code);
                            					
                            					JSONObject codeF = responsesF.getJSONObject(code);
                                				String[] codeArray = JSONObject.getNames(codeF);
                                				for(String schema : codeArray){
                                					
                                					
                                					if("schema".equals(schema)){
                                						JSONObject schemaObjMap = (JSONObject)codeF.get(schema);
                                    					System.out.println("schemaObjMap=="+schemaObjMap);
                                    					JSONObject schemaF = codeF.getJSONObject(schema);
                                						String[] schemaArray = JSONObject.getNames(schemaF);
                                						if(schemaArray.length>0){
                                							for(String ref : schemaArray){
                                    							if("$ref".equals(ref)){
                                    								String imp = schemaF.getString(ref);
                                    								System.out.println("$ref==="+imp);
                                    								String impName = imp.substring(14);
                                    								System.out.println("impName===="+impName);
                                    								System.out.println(definitionsMap.get(impName));
                                    								schemaObjMap.put("schema", definitionsMap.get(impName));
                                    								
                                    							}else{
                                    								String description = schemaF.getString("description");
                                    								System.out.println(description);
                                    							}
                                    							
                                								codeObjMap.put(code, schemaObjMap);
                                								paraObjMap.put("responses", codeObjMap);
                                								methodObjMap.put("get", paraObjMap);
                                								
                                								urlObjMap.put(path, methodObjMap);
                                    						}
                                						}
                                					}else if("description".equals(schema)){
                                							String description = codeF.getString("description");
                            								System.out.println(description);
                                					}
                                				}
                            				}
                    					}
                    				}
                    			}
                    		}
                    	}
                    }
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
		return listJson;  
	}
	
	public static HashMap<String,Object> getDefinitions(JSONObject dataJson){
		JSONObject definitionsObj = dataJson.getJSONObject("definitions");// 找到properties的json对象  
		String[] definitionsArray = JSONObject.getNames(definitionsObj);
		HashMap<String,Object> definitionsMap = new HashMap<String,Object>();
		for(String definitions : definitionsArray){
			System.out.println(definitions);
			JSONObject definitionsResult = definitionsObj.getJSONObject(definitions);
//    		String[] methodArray = JSONObject.getNames(urlF);
			definitionsMap.put(definitions, definitionsResult);
		}
		return definitionsMap;
	}
}
