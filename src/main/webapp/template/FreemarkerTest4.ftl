<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN" dir="ltr">  
<head>  
 <title>test!</title>  
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>  
</head>  
<body>  
<table>
	<#if OperationMap2?exists>
                <#list OperationMap2  as OperationMapSecond> 
                   <tr>
                           <td>${OperationMapSecond.PHONE}</td>
                           <td>${OperationMapSecond.ADDRESS}</td>
                           <td>${OperationMapSecond.USERNAME}</td>
                           <td>${OperationMapSecond.USERID}</td>
                   </tr>
        		</#list>
   </#if>
	<#if OperationMap?exists>
                <#list OperationMap?keys as key> 
                   <tr>
                           <td>${key}</td>
                           <#if OperationMap[key]?exists>
	                           <#list OperationMap[key] as key,OperationMapSecond> 
				                   <tr>
					                   		
					                   		<#if "${key}"="logic">  
								                 <td>${key}</td>
					                   		   	 <td>${OperationMapSecond}</td>
								             </#if>
					                   		 <#if "${key}"="responses">  
								                 <td>${key}</td>
					                   		   	 <td>${OperationMapSecond}</td>  
								             </#if>
				                   		   <#if "${key}"="parameters">  
							                 <td >  
							                    <#list OperationMapSecond as OperationMapT> 
								                   <tr>
								                           <td>${OperationMapT.DIS_NAME}</td>
								                   </tr>
								        		</#list>
							                </td>  
							             	</#if>
				                   </tr>
				        		</#list>
			        	  </#if>
                   </tr>
        		</#list>
   </#if>
   
</table>
</body>     
</html>  