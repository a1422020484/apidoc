<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN" dir="ltr">  
<head>  
 <title>products</title>  
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>  
</head>  
<body>  
<table>
	<#if OperationMap2?exists>
                <#list OperationMap2  as OperationMapSecond> 
                   <tr>		<#--属性值区分大小写  map-->
                           <td style="color:red">PHONE:${OperationMapSecond.PHONE}</td>
                           <td>ADDRESS:${OperationMapSecond.ADDRESS}</td>
                           <td>USERNAME:${OperationMapSecond.USERNAME}</td>
                           <td>USERID:${OperationMapSecond.USERID}</td>
                   </tr>
        		</#list>
   </#if>
	<#if OperationMap?exists>
				<#--list<map>-->
                <#list OperationMap?keys as key> 
                   <tr>
                   		<td>${key}
                           <#if key="responses">
	                           <#list OperationMap[key] as key,OperationMapSecond> 
				                   <tr>
					                   <td>${key}</td>
					                   	<td>${OperationMapSecond}</td>		
				                   </tr>
				        		</#list>
			        	  </#if>
			        	  <#--map {key:value}-->
                           <#if key="templete">
				                   <tr>
					                   <td>${key}</td>
					                   	<td>${OperationMap.templete}</td>		
				                   </tr>
			        	  </#if>
			        	  <#if key="logic">
	                           <#list OperationMap[key] as key,OperationMapSecond> 
				                   <tr>
					                   <td>${key}</td>
					                   	<td>${OperationMapSecond}</td>		
				                   </tr>
				        		</#list>
			        	  </#if>
			        	  <#if key="parameters">
	                           <#list OperationMap[key] as OperationMapSecond> 
				                   <tr>
					                   	<td>name : ${OperationMapSecond.name}</td>		
				                   </tr>
				        		</#list>
			        	  </#if>
			        	 </td>
                   </tr>
        		</#list>
   </#if>
   
</table>
</body>     
</html>  