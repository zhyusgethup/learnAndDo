package zhyu.uicode.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zhyu.common.exception.CommonServiceException;
import zhyu.common.vo.Result;

@Controller
@RequestMapping("/test")
public class TestController {
    @RequestMapping("/testE1")
    @ResponseBody
    public Result test1() throws Exception{
        System.out.println("hello e1");
        throw new CommonServiceException("test in testE1");
    }

    @RequestMapping("/testE2")
    public String test2() throws Exception{
        System.out.println("hello e2");
        throw new CommonServiceException("test in testE2");
    }
}
