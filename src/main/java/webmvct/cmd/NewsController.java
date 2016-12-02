package webmvct.cmd;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import webmvct.bean.PostId;
import webmvct.bean.Store;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;


@Api(value="/news",description="新闻")  
@Controller
@RequestMapping("/news")  
public class NewsController {
	
    @RequestMapping(value = "/{postid}",method=RequestMethod.GET)  
    @ResponseBody  
    @ApiOperation(value="获取新闻信息",notes="通过新闻id获取新闻信息")  
    public PostId postId(@PathVariable(value="postid") String postid){ 
    	System.out.println("postid"+postid);
    	PostId ps = new PostId();
        	ps.setId("tt"+ postid);
        	ps.setName("测试世界" + postid);
        return ps;  
    }
      
    @ApiOperation(value="获取新闻信息",notes="通过新闻name获取新闻信息")  
    @ResponseBody  
    @RequestMapping(value = "/{postname}",method=RequestMethod.POST)  
    public PostId getPostId(@PathVariable(value="postname") String postname){
    	System.out.println("postname"+postname);
    	PostId ps = new PostId();
        	ps.setId("tt"+ postname);
        	ps.setName("测试世界" + postname);
        return ps;  
    }
    @ApiOperation(value="获取新闻信息",notes="通过新闻name获取新闻所有信息")  
    @ResponseBody  
    @RequestMapping(value = "/{postname}/postId/{postid}",method=RequestMethod.POST)  
    public PostId getPostId(@PathVariable(value="postId") PostId postId){
    	
    	return postId;  
    }
		
}
