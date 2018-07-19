package zhyu.uicode.api.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
@Controller()
public class ApiController {
	ApplicationContext ctx = null;
	@RequestMapping("/testApi")
	public String testApi(String code) {
	    if(code.equals("getPublicKey")) {
	        return "/RSA/getPublicKey.do";
        }
		return "testApi";
	}
}