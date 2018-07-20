package zhyu.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import zhyu.common.exception.CommonServiceException;

import javax.servlet.http.HttpSession;
import java.security.PublicKey;

public class BaseController {
    @Autowired
    HttpSession session;
    protected String encode(String src) throws CommonServiceException{
        String re = null;
        try {
            String key = session.getAttribute("privateKey").toString();
            re = RSAUtil.encryptionByPublicKey(src, key);
        }catch(Exception e) {
            throw new CommonServiceException("加密异常");
        }
        return  re;
    }

    protected String decode(String src) throws CommonServiceException{
        String re = null;
        try {
            String key = session.getAttribute("publicKey").toString();
            re = RSAUtil.decryptionByPrivateKey(src, key);
        }catch(Exception e) {
            throw new CommonServiceException("解密异常");
        }
        return  re;
    }
}
