package webmvct.jsontest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

public class ReadJsonTest9 {
	public static void main(String[] args) {
		String url = "/products";
		String address = "src/main/resources/data/swagger4.json";
		System.out.println("listJson 最终数据=========="+ReadJsonTest9.tranJson(address, url));
	}

	private static JSONObject tranJson(String address,String url){
		JSONObject dataJson = null;
        // 读取原始json文件并进行操作和输出  
        try {  
            BufferedReader br = new BufferedReader(new FileReader(address));// 读取原始json文件  
            String s = null;
            while ((s = br.readLine()) != null) {  
                // System.out.println(s);  
            	dataJson = new JSONObject(s);
                try {  
                    // 创建一个包含原始json串的json对象
//                    //1、从json串中获得key为type所对应的值
//                    String name = dataJson.getString("paths");//从json串中获得key为type所对应的值
//                    System.out.println(name);
                    System.out.println(dataJson);
                    System.out.println(dataJson.get("definitions"));
                    //传入jsonmap，返回所有definitionsMap，是没有经过替换引用的
                    HashMap<String,Object> definitionsMapF = ReadJsonTest9.getDefinitions(dataJson);
                    //request：dataJson未经修改的源数据。
                    //		   definitionsMapF 全部的definitions数据作为一个map。
                    //return ：tranDataJsonAfter中的definitions的引用已经替换了。
                    JSONObject tranDataJsonAfter = ReadJsonTest9.tranDefinitions(dataJson,definitionsMapF);
                    //从替换后的tranDataJsonAfter中，获得全部的definitions数据作为一个map。此时definitions的引用已经替换了。
                    HashMap<String,Object> definitionsMap = ReadJsonTest9.getDefinitions(tranDataJsonAfter);
                    
                    JSONObject pathsObj = tranDataJsonAfter.getJSONObject("paths");// 找到properties的json对象  
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
                    				JSONObject responsesObjMap = (JSONObject)methodObjMap.get("responses");
                    				String[] responsesArray = JSONObject.getNames(responsesObjMap);
                    				for(String code : responsesArray){
                    					JSONObject codeMap = (JSONObject)responsesObjMap.get(code);
                    					//可能会有异常
                    					if(codeMap.get("schema") instanceof JSONObject){
                    						JSONObject schemaF = (JSONObject)codeMap.get("schema");
                    						String[] schemaArray = JSONObject.getNames(schemaF);
                    						if(schemaArray.length>0){
                    							for(String ref : schemaArray){
                        							if("$ref".equals(ref)){
                        								String imp = schemaF.getString(ref);
                        								System.out.println("$ref==="+imp);
                        								String impName = imp.substring(14);
                        								System.out.println("impName===="+impName);
                        								System.out.println(definitionsMap.get(impName));
                        								codeMap.put("schema", definitionsMap.get(impName));
                        								System.out.println("dataJson from tranJson last======"+dataJson);
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
        return dataJson; 
	}

	/**获得所有definitions,返回一个包含所有definitions信息的map
	 * @author yang
	 * @time 2016年11月30日上午10:03:39
	 * @return_type HashMap<String,Object>
	 * @param dataJson
	 * @return HashMap<String, Object>
	 */
	public static HashMap<String, Object> getDefinitions(JSONObject dataJson) {
		JSONObject definitionsObj = dataJson.getJSONObject("definitions");// 找到properties的json对象
		String[] definitionsArray = JSONObject.getNames(definitionsObj);
		for(String definitions : definitionsArray){
			JSONObject ActiveObj = definitionsObj.getJSONObject(definitions);
			ActiveObj.getJSONObject("properties");
		}
		HashMap<String, Object> definitionsMap = new HashMap<String, Object>();
		for (String definitions : definitionsArray) {
			System.out.println(definitions);
			JSONObject definitionsResult = definitionsObj.getJSONObject(definitions);
			// String[] methodArray = JSONObject.getNames(urlF);
			definitionsMap.put(definitions, definitionsResult);
		}
		return definitionsMap;
	}
	
	/**
	 * 替换definitions中的引用，返回替换后的map
	 * @author yang
	 * @time 2016年11月30日上午10:03:15
	 * @return_type HashMap<String,Object>
	 * @param dataJson
	 * @return
	 */
	public static JSONObject tranDefinitions(JSONObject dataJson,HashMap<String, Object> definitionsMap){
		//获得替换前的Definitions
		HashMap<String, Object> tranDefBefore = ReadJsonTest9.getDefinitions(dataJson);
		JSONObject definitionsObj = dataJson.getJSONObject("definitions");// 找到properties的json对象
		String[] definitionsArray = JSONObject.getNames(definitionsObj);
		for(String definitions : definitionsArray){
			JSONObject ActiveObj = definitionsObj.getJSONObject(definitions);
			JSONObject propertiesObj = ActiveObj.getJSONObject("properties");
			String[] propertiesNameArray = JSONObject.getNames(propertiesObj);
			for(String propertiesName: propertiesNameArray){
				//会有异常
				if(propertiesObj.getJSONObject(propertiesName) instanceof JSONObject){
					JSONObject itemsObj = propertiesObj.getJSONObject(propertiesName);
					System.out.println(itemsObj);
					System.out.println("itemsObj.keySet()==="+itemsObj.keySet());
					Set<String> itemsSet = itemsObj.keySet();
					Iterator<String> it = itemsSet.iterator();  
					while (it.hasNext()) {  
					  String str = it.next();  
					  System.out.println("items set ==="+str);  
						if("items".equals(str)){
							JSONObject refF = (JSONObject)itemsObj.get("items");
							String[] schemaArray = JSONObject.getNames(refF);
							if(schemaArray.length>0){
								for(String ref : schemaArray){
	    							if("$ref".equals(ref)){
	    								String imp = refF.getString(ref);
	    								System.out.println("$ref==="+imp);
	    								String impName = imp.substring(14);
	    								System.out.println("impName===="+impName);
	    								System.out.println(definitionsMap.get(impName));
	    								itemsObj.put("items", definitionsMap.get(impName));
	    								System.out.println("dataJson from tranDefinitions======"+dataJson);
	    							}
	    						}
							}
						System.out.println(refF);
						}
					}  
				}
			}
		}
		return dataJson;
	}
}
