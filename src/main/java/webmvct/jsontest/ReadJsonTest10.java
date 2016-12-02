package webmvct.jsontest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReadJsonTest10 {
	
	private static JSONObject dataJson = null;
	private static HashMap<String, Object> definitionsMap = null;
	
	public static void main(String[] args) {
		String url = "/products";
		String address = "src/main/resources/data/swagger4.json";
		System.out.println("dataJson 最终数据=========="+ReadJsonTest10.tranJson(address, url));
	}

	public static JSONObject tranJson(String address,String url){
		
        // 读取原始json文件并进行操作和输出  
        try {  
            BufferedReader br = new BufferedReader(new FileReader(address));// 读取原始json文件  
            String s = null;
            while ((s = br.readLine()) != null) {  
                // System.out.println(s);  
            	dataJson = new JSONObject(s);
                try {  
                    // 创建一个包含原始json串的json对象
//                  //1、从json串中获得key为type所对应的值
                    System.out.println(dataJson.get("definitions"));
                    //传入jsonmap，返回所有definitionsMap，是没有经过替换引用的
                    definitionsMap = ReadJsonTest10.getDefinitions(dataJson);
                    //request：dataJson未经修改的源数据。
                    //		   definitionsMapF 全部的definitions数据作为一个map。
                    //return ：dataJson中的definitions的引用已经替换了。
                    dataJson = ReadJsonTest10.tranDefinitions(dataJson);
                    //从替换后的dataJson中，获得全部的definitions数据作为一个map。此时definitions的引用已经替换了。
                    definitionsMap = ReadJsonTest10.getDefinitions(dataJson);
                    
                    JSONObject pathsObj = dataJson.getJSONObject("paths");// 找到properties的json对象  
                    String[] pathArray = JSONObject.getNames(pathsObj);
                    for(String path : pathArray){
                    	//修改所有请求地址
//                    	if(url.equals(path)){
                    		dataJson = ReadJsonTest10.getKeyValueObject(pathsObj,path);
//                    	}
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

	private static JSONObject getKeyValueObject(JSONObject valueObjA,String key){
		JSONArray valueObjArray = null;
		JSONObject valueObjB = null;
		if(valueObjA.toString().indexOf("\"$ref\":\"#/definitions/")==-1){
			return valueObjA;
		}else{
			
			if(valueObjA.get(key) instanceof JSONArray){
				valueObjArray = (JSONArray)valueObjA.get(key);
				for(int i= 0;i<valueObjArray.length();i++){
					System.out.println(key);
					System.out.println("valueObjArray.get("+i+")"+valueObjArray.get(i));
//					getKeyValue(valueObjB,keyStr);
				}
			}else if(valueObjA.get(key) instanceof JSONObject){
				valueObjB = (JSONObject)valueObjA.get(key);
				for(String keyStr : valueObjB.keySet()){
					
					String value = valueObjB.get(keyStr).toString();
					System.out.println("key= "+ keyStr + " and value= " + value);
					if("$ref".equals(keyStr)){
							String imp = valueObjB.getString(keyStr);
							System.out.println("$ref==="+imp);
							String impName = imp.substring(14);
							System.out.println("impName===="+impName);
							System.out.println("valueObjA==="+valueObjA);
							System.out.println("valueObjB==="+valueObjB);
//							System.out.println(definitionsMap.get(impName));
							valueObjA.put(key, definitionsMap.get(impName));
							System.out.println("dataJson from tranDefinitions======"+dataJson);
					}
					getKeyValueObject(valueObjB,keyStr);
				}
			}
			
		}
		System.out.println("valueObjA======="+valueObjA);
		return dataJson;
	}
	
	
	/**获得所有definitions,返回一个包含所有definitions信息的map
	 * @author yang
	 * @time 2016年11月30日上午10:03:39
	 * @return_type HashMap<String,Object>
	 * @param dataJson
	 * @return HashMap<String, Object>
	 */
	private static HashMap<String, Object> getDefinitions(JSONObject dataJson) {
		JSONObject definitionsObj = dataJson.getJSONObject("definitions");// 找到definitions的json对象
		String[] definitionsArray = JSONObject.getNames(definitionsObj);

		HashMap<String, Object> definitionsMap = new HashMap<String, Object>();
		for (String definitions : definitionsArray) {
//			System.out.println(definitions);
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
	private static JSONObject tranDefinitions(JSONObject dataJson){
		//获得替换前的Definitions
		HashMap<String, Object> tranDefBefore = ReadJsonTest10.getDefinitions(dataJson);
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
//					System.out.println(itemsObj);
//					System.out.println("itemsObj.keySet()==="+itemsObj.keySet());
					Set<String> itemsSet = itemsObj.keySet();
					Iterator<String> it = itemsSet.iterator();  
					while (it.hasNext()) {  
					  String str = it.next();  
//					  System.out.println("items set ==="+str);  
						if("items".equals(str)){
							JSONObject refF = (JSONObject)itemsObj.get("items");
							String[] schemaArray = JSONObject.getNames(refF);
							if(schemaArray.length>0){
								for(String ref : schemaArray){
	    							if("$ref".equals(ref)){
	    								String imp = refF.getString(ref);
//	    								System.out.println("$ref==="+imp);
	    								String impName = imp.substring(14);
//	    								System.out.println("impName===="+impName);
//	    								System.out.println(definitionsMap.get(impName));
	    								itemsObj.put("items", definitionsMap.get(impName));
//	    								System.out.println("dataJson from tranDefinitions======"+dataJson);
	    							}
	    						}
							}
//						System.out.println(refF);
						}
					}  
				}
			}
		}
		return dataJson;
	}
}
