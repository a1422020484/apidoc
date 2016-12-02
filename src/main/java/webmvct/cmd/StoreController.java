package webmvct.cmd;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import webmvct.bean.Store;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Api(value="/store",description="商店")  
@Controller  
@RequestMapping("/store")  
public class StoreController {  
      
      
    @RequestMapping(value = "/{storeid}",method=RequestMethod.GET)  
    @ResponseBody  
    @ApiOperation(value="获取商店信息",notes="通过商店id获取商店信息")  
    public Store getStore(String storeid){  
        return new Store();  
    }  
      
    @ApiOperation(value="获取商店信息",notes="通过商店name获取商店信息")  
    @ResponseBody  
    @RequestMapping(value = "/{storename}",method=RequestMethod.POST)  
    public Store getStore2(@PathVariable(value="storeid") String storeid){  
        return new Store();  
    }
    
    
}