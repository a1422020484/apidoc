package webmvct.cmd;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Configuration;
import webmvct.jsontest.MyBatisJson;
import webmvct.jsontest.OperationGet;

/**
 * Servlet implementation class HelloJsonServlet
 */
// @WebServlet("/HelloJsonServlet")
public class HelloJsonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HelloJsonServlet() {
		super();
		System.out.println("HelloJsonServlet");
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		OperationGet operationGet = new OperationGet();
		MyBatisJson myBatisJson = new MyBatisJson();
		
		Configuration cfg = new Configuration(); 
        //设置FreeMarker的模版文件夹位置 
        cfg.setDirectoryForTemplateLoading(new File("src/main/webapp/template")); 
		
		String method = "get";
		String url = request.getServletPath();
		System.out.println(url);
		if("/".equals(url)){
			request.getRequestDispatcher("/template/default.ftl").forward(request, response);
			return;
		}else{
		Map<String, String> logicMap = null;
		List<Map> userInfo = null;
		String mybaticPathName = "";
		try {
			Map<String, Object> map = operationGet.OperationGetMain(url, method);
			if (map == null) {
				throw new Exception("json数据未正确处理");
			} else {
				map = operationGet.OperationGetMain(url, method);
			}
			request.setAttribute("OperationMap", map);
			
			if (map.get("logic") == null) {
				throw new Exception("不包含对应logic块");
			} else {
				logicMap = (Map<String, String>) map.get("logic");
				mybaticPathName = (String) logicMap.get("name");
			}
			if ("".equals(mybaticPathName) || mybaticPathName.isEmpty()) {
				throw new Exception("logic不包含mybatis方法");
			} else {
//				userInfo = myBatisJson.getUser(mybaticPathName);
			}
			if(map.get("templete")==null||"".equals(map.get("templete"))){
				throw new Exception("未找到合适的模板templete");
			}else{
				String templete = map.get("templete").toString();
				request.setAttribute("OperationMap2", userInfo);
				request.getRequestDispatcher("/template/"+templete+".ftl").forward(request, response);
			}
			
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
