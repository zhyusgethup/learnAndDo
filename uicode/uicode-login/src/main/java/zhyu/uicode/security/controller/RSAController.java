package zhyu.uicode.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import zhyu.common.security.RSAUtil;
import zhyu.common.vo.Result;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/RSA")
public class RSAController {

    @RequestMapping("/getKeys")
    @ResponseBody
    public Result getKeys() {
        RSAUtil.Keys keys1 = null;
        return null;
    }

    @RequestMapping(value ="/getPublicKey", method = RequestMethod.GET)
    @ResponseBody
    public Result getPublicKey(HttpSession session) throws Exception{
        RSAUtil.Keys keys = null;
        Map<String,Object> result = new HashMap<>();
        keys = RSAUtil.generatorKeyPair();
        String pri1 = keys.getPrivateKey();
        String pub1 = keys.getPublicKey();
        session.setAttribute("privateKey",pri1);
        String describle = "privateKey 用于接收信息,publicKey用于发送信息";
        result.put("msg",describle);
        result.put("publicKey",pub1);
        return Result.getSuccessResult(result);
    }

    @RequestMapping(value ="/getKeysInTwoPair", method = RequestMethod.GET)
    @ResponseBody
    public Result getKeysInTwoPair(HttpSession session) throws Exception{
        RSAUtil.Keys keys1 = null;
        RSAUtil.Keys keys2 = null;
        Map<String,Object> result = new HashMap<>();
             keys1 = RSAUtil.generatorKeyPair();
             String pri1 = keys1.getPrivateKey();
             String pub1 = keys1.getPublicKey();
             String pri2 = keys1.getPrivateKey();
             String pub2 = keys1.getPublicKey();
             session.setAttribute("privateKey",pri1);
             session.setAttribute("publicKey",pub2);
             String describle = "privateKey 用于接收信息,publicKey用于发送信息";
             result.put("msg",describle);
             result.put("privateKey",pri2);
             result.put("publicKey",pub1);
        return Result.getSuccessResult(result);
    }

}
