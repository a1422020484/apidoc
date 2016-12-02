package webmvct.cmd;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import webmvct.bean.User;

@Api(value="/user",description="用户信息")  
@Controller  
@RequestMapping("/user")  
public class UserController {
	
	
	/**通过一个用户id请求数据
	 * @author yang
	 * @time 2016年11月28日下午4:53:32
	 * @return_type User
	 * @param userid
	 * @return
	 */
	@RequestMapping(value = "/{userid}",method=RequestMethod.GET)  
    @ResponseBody  
    @ApiOperation(value="用户信息",notes="通过用户id获得用户信息")  
    public User postId(@PathVariable(value="userid") String userid){ 
    	System.out.println("userid:"+userid);
    	User ps = new User();
        ps.setAddress("河南");
        ps.setPhone("phone-110");
        ps.setUsername("yang-XP");
        return ps;  
    }
	
	/**
	 * 传一个实体表单内容
	 * @author yang
	 * @time 2016年11月28日下午4:51:25
	 * @return_type User
	 * @param user
	 * @return user
	 * @throws IOException 
	 */
	@RequestMapping(value = "/userAll",method=RequestMethod.GET)  
	@ResponseBody  
	@ApiOperation(value="用户信息",notes="保存用户所有信息")  
	public User postId(@ModelAttribute("user") User user) throws IOException{
		System.out.println(user);
		
		return user;
	}
	@RequestMapping(value = "/userList/{userid}",method=RequestMethod.POST)  
	@ResponseBody  
	@ApiOperation(value="用户信息",notes="保存用户列表")  
	public List<Map> userList(@PathVariable(value="userid") String userid) throws IOException{ 
		System.out.println(userid);
		String resource = "./mybatis/mybatis-single.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);
		SqlSession session = sqlMapper.openSession();
//		User u1 = (User) session.selectOne("webmvct.mapper.UserMapper.getUser", 24);
		List<Map> list = (List<Map>) session.selectList("webmvct.mapper.UserMapper.getUser",userid);
		// 获取映射接口
//		UserMapper userMapper = session.getMapper(UserMapper.class);
		// 调用接口中的方法
//		List<User> list = userMapper.getUser();
		// 提交事务
//		System.out.println(u1.getUsername());
//		for(User u : list){
//			System.out.println("address" + u.getAddress());
//		}
		System.out.println(list);
//		session.commit();
//		Connection conn =  null;
//		transactionFactory.newTransaction(conn, false);
		return list;
	}
	
	
	
	
	
	
	public static void main(String[] args) throws IOException {
		String resource = "./mybatis/mybatis-single.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);
		SqlSession session = sqlMapper.openSession();
//		User u1 = (User) session.selectOne("webmvct.mapper.UserMapper.getUser", 24);
		List<Map> list = (List<Map>) session.selectList("webmvct.mapper.UserMapper.getUser","24640");
		// 获取映射接口
//		UserMapper userMapper = session.getMapper(UserMapper.class);
		// 调用接口中的方法
//		List<User> list = userMapper.getUser();
		// 提交事务
//		System.out.println(u1.getUsername());
//		for(User u : list){
//			System.out.println("address" + u.getAddress());
//		}
		System.out.println(list);
		session.commit();
//		Connection conn =  null;
//		transactionFactory.newTransaction(conn, false);
	}
}
