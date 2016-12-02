package webmvct.jsontest;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class OperationGet {
	
	private Configuration cfg;            //模版配置对象 
	private static Map<String,String> responsesObjMap = new HashMap<String,String>();
	private static Map<String,String> logicObjMap = new HashMap<String,String>();
	private static OperationGet operationGet = new OperationGet();
	private static ReadJson readJson = new ReadJson();
	
	public Map<String,Object> OperationGetMain(String url,String method) throws Exception {
		
//		url = "/products";
		method ="get";
		Map<String,Object> map = new HashMap<String,Object>();
		String address = "src/main/resources/data/swagger4.json";
		System.out.println("dataJson 最终数据=========="+readJson.tranJson(address, url));
		
        List<Map> parametersList = new ArrayList<Map>();
        //在模版上执行插值操作，并输出到制定的输出流中 \
		JSONObject dataJson = readJson.tranJson(address, url);
		
		JSONObject pathsObj = dataJson.getJSONObject("paths");// 找到properties的json对象  
        String[] pathArray = JSONObject.getNames(pathsObj);
        for(String path:pathArray){
        	if(url.equals(path)){
        		JSONObject methodObj = pathsObj.getJSONObject(path);
        		JSONObject operationObj = methodObj.getJSONObject(method);// 找到properties的json对象  
                String[] operationArray = JSONObject.getNames(operationObj);
                for(String operation : operationArray){
                	if("parameters".equals(operation)){
                		JSONArray parametersObj = operationObj.getJSONArray(operation);
                		parametersList = operationGet.getParameters(parametersObj);
                		if(parametersList!=null){
                			map.put("parameters", parametersList);
                		}
                	}else if("logic".equals(operation)){
                		JSONObject logicObj = operationObj.getJSONObject(operation);
                		logicObjMap = operationGet.getLogic(logicObj);
                		if(logicObjMap!=null){
                			map.put("logic", logicObjMap);
                		}
                	}else if("responses".equals(operation)){
                		JSONObject logicObj = operationObj.getJSONObject(operation);
                		responsesObjMap = operationGet.getResponses(logicObj);
                		if(responsesObjMap!=null){
                			map.put("responses", responsesObjMap);
                		}
                	}else if("templete".equals(operation)){
                		String templeteObj = operationObj.getString(operation);
                		if(templeteObj!=null&&templeteObj.length()>0){
                			map.put("responses", templeteObj);
                		}
                	}
                }
        	}
        }
        	return map;
	}
	
	public List<Map> getParameters(JSONArray parametersArray){
		List<Map> parametersList = new ArrayList<Map>();
		for(int i=0;i<parametersArray.length();i++){
			JSONObject paraObject = parametersArray.getJSONObject(i);
            String[] paraNameArray = JSONObject.getNames(paraObject);
            HashMap<String,Object> map = new HashMap<String,Object>();
            for(String paramName : paraNameArray){
            	String paraValue = paraObject.get(paramName).toString();
            	map.put(paramName, paraValue);
            }
            parametersList.add(i, map);
        }
		System.out.println("listJson+++++"+parametersList);
		return parametersList;
	}
	
	public Map<String,String> getResponses(JSONObject operationObj){
		String[] codeArray = JSONObject.getNames(operationObj);
		for(String code : codeArray){
			JSONObject responsesObj = operationObj.getJSONObject(code);
//			JSONObject ResProObj = responsesObj.getJSONObject("schema").getJSONObject("items").getJSONObject("properties");
			JSONObject ResProObj = responsesObj.getJSONObject("schema").getJSONObject("properties");
			System.out.println(code + "+++++ResProObj==="+ResProObj);
			
			responsesObjMap.put(code, ResProObj.toString());
		}
		System.out.println("operationObjMap===="+responsesObjMap);
		return responsesObjMap;
	}
	
	
	public Map<String,String> getLogic(JSONObject operationObj){
		for(String keyStr : operationObj.keySet()){
			System.out.println("Logic ======== key= "+ keyStr + " and value= " + operationObj.get(keyStr).toString());
			logicObjMap.put(keyStr, operationObj.get(keyStr).toString());
		}
		return logicObjMap;
	}
	
}
