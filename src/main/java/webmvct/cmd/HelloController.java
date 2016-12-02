package webmvct.cmd;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/store")  
public class HelloController {
	
	@RequestMapping(value={"/hello"})
	 public String hello(){
	  System.out.println("hello");
	  return "hello";
	 }
	
	
	
}
