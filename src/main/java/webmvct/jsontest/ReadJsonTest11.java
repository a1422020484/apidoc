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

/**
 * @author yang
 * 1、一个items下只能存在一个引用。
 */
public class ReadJsonTest11 {
	
	private static JSONObject dataJson = null;
	private static HashMap<String, Object> definitionsMap = null;
	private static String definitionsName = null; 
	
	public static void main(String[] args) throws Exception {
		String url = "/products";
		String address = "src/main/resources/data/swagger4.json";
		System.out.println("dataJson 最终数据=========="+ReadJsonTest11.tranJson(address, url));
	}

	protected static JSONObject tranJson(String address,String url) throws Exception{
		
        // 读取原始json文件并进行操作和输出  
        try {  
            BufferedReader br = new BufferedReader(new FileReader(address));// 读取原始json文件  
            String s = null;
            while ((s = br.readLine()) != null) {  
                // System.out.println(s);  
            	// 创建一个包含原始json串的JSONObject
            	dataJson = new JSONObject(s);
                try {  
                    //传入(JSONObject)dataJson，返回所有definitions，是没有经过替换引用的
                    definitionsMap = ReadJsonTest11.getDefinitions(dataJson);
                    //request：dataJson未经修改的源数据。
                    //return ：dataJson中的definitions的引用已经替换了。
                    dataJson = ReadJsonTest11.tranDefinitions(dataJson);
                    //全局变量必须在验证完引用是否存在递归之后，需要置空。防止因为遗留数据的，错误判断。
                    definitionsName = "";
                    //从替换后的dataJson中，获得全部的definitions数据作为一个map。此时definitions的引用已经替换了。
                    definitionsMap = ReadJsonTest11.getDefinitions(dataJson);
                    
                    JSONObject pathsObj = dataJson.getJSONObject("paths");// 找到properties的json对象  
                    String[] pathArray = JSONObject.getNames(pathsObj);
                    for(String path : pathArray){
                    	//修改所有请求地址
//                    	if(url.equals(path)){
                    		dataJson = ReadJsonTest11.getKeyValueObject(pathsObj,path);
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

	private static JSONObject getKeyValueObject(JSONObject valueObjA,String key) throws Exception{
		JSONArray valueObjArray = null;
		JSONObject valueObjB = null;
		if(valueObjA.toString().indexOf("\"$ref\":\"#/definitions/")==-1){
			return dataJson;
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
							if(impName.equals(definitionsName)){
								throw new Exception("存在循环递归，请检查"+definitionsName+"的引用：\"$ref\":\"#/definitions/"+impName+"\"");
							}
							System.out.println("valueObjA==="+valueObjA);
							System.out.println("valueObjB==="+valueObjB);
							if(definitionsMap.get(impName)==null){
								throw new Exception("未找到合适的引用，请检查"+definitionsName+"的引用：\"$ref\":\"#/definitions/"+impName+"\"");
							}else{
								valueObjA.put(key, definitionsMap.get(impName));
							}
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
			JSONObject definitionsResult = definitionsObj.getJSONObject(definitions);
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
	 * @throws Exception 
	 */
	private static JSONObject tranDefinitions(JSONObject dataJson) throws Exception{
		JSONObject definitionsObj = dataJson.getJSONObject("definitions");// 找到properties的json对象
		String[] definitionsArray = JSONObject.getNames(definitionsObj);
		for(String definitions: definitionsArray){
			definitionsName = definitions;
			JSONObject ActiveObj = definitionsObj.getJSONObject(definitions);
			JSONObject propertiesObj = ActiveObj.getJSONObject("properties");
			String[] propertiesNameArray = JSONObject.getNames(propertiesObj);
			for(String propertiesName: propertiesNameArray){
				//会有异常
				dataJson = ReadJsonTest11.getKeyValueObject(propertiesObj,propertiesName);
			}
		}
		return dataJson;
	}
}
