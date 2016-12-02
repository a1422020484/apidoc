package webmvct.jsontest;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisJson {
	
	public List<Map> getUser(String mybaticPathName) throws IOException{
		String resource = "./mybatis/mybatis-single.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);
		SqlSession session = sqlMapper.openSession();
//		User u1 = (User) session.selectOne("webmvct.mapper.UserMapper.getUser", 24);
		List<Map> list = (List<Map>) session.selectList(mybaticPathName,"24");
		System.out.println(list);
		return list;
	}

}
