package sourcecode.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sourcecode.mapper.UserMapper;
import sourcecode.model.User;

/*
    主页
 */
@Controller
public class indexController {

        @Autowired
        UserMapper userMapper;

        @GetMapping("/")//定位主界面
        //访问所有cookies，根据cooikes判断是否处于数据库中
        public String index(HttpServletRequest request)
        {
            Cookie[] cookies = request.getCookies();//获取请求cookies
            for (Cookie cookie : cookies)
            {
                if(cookie.getName().equals("token"))//寻找键为token的cookie
                {
                    String token =cookie.getValue();//获取token
                    User user = userMapper.findByToken(token);//根据token查找数据库
                    if(user != null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }

            }


            return "index";
        }


}
