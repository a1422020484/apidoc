package webmvct.cmd;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/json")
public class JsonReadController {
	
	@RequestMapping("/prodects")
	public void getProdects(){
		
	}
}
