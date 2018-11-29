package zhyu.common.exceptionResolver;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import zhyu.common.exception.CommonServiceException;
import zhyu.common.vo.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommonExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse response, Object o, Exception e) {
        String message = null;
        //判断是否为系统自定义异常。
        System.out.println("hello CER");
        System.out.println(e);
        if(e instanceof CommonServiceException) {
            ModelAndView mv = new ModelAndView();
//            response.setStatus(HttpStatus.OK.value()); //设置状态码
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE); //设置ContentType
//            response.setCharacterEncoding("UTF-8"); //避免乱码
//            response.setHeader("Cache-Control", "no-cache, must-revalidate");
//            try {
//                Result r = Result.getFailResult(e.getMessage());
//                String json = JSON.toJSONString(r);
//                response.getWriter().write(json);
//            } catch (IOException ex) {
//            }
            FastJsonJsonView view = new FastJsonJsonView();
            mv.addObject(Result.getFailResult(e.getMessage()));
            mv.setView(view);
            return mv;
        }
        return new ModelAndView();
    }
}
