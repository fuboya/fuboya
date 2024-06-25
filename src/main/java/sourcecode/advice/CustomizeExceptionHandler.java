package sourcecode.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import sourcecode.exception.CustomizeException;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable e, Model model){
        if(e instanceof CustomizeException){
            model.addAttribute("message",e.getMessage());
        }else{
            model.addAttribute("message","服务器错误，请稍后再试");
        }
        return new ModelAndView("error");
    }
}
