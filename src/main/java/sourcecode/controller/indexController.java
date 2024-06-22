package sourcecode.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sourcecode.dto.PaginationDTO;
import sourcecode.service.QuestionService;
import sourcecode.dto.QuestionDTO;
import sourcecode.mapper.UserMapper;
import sourcecode.model.User;

import java.util.List;

/*
    主页
 */
@Controller
public class indexController {

        @Autowired
        private UserMapper userMapper;
        @Autowired
        private QuestionService questionService;

        @GetMapping("/")//定位主界面
        //访问所有cookies，根据cooikes判断是否处于数据库中
        public String index(HttpServletRequest request,
                            Model model,
                            @RequestParam(name="page",defaultValue = "1") Integer page,
                            @RequestParam(name="size",defaultValue = "6") Integer size) {

            Cookie[] cookies = request.getCookies();//获取请求cookies
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("token"))//寻找键为token的cookie
                    {
                        String token = cookie.getValue();//获取token
                        User user = userMapper.findByToken(token);//根据token查找数据库
                        if (user != null) {
                            request.getSession().setAttribute("user", user);
                        }
                        break;
                    }

                }
            }

            PaginationDTO pagination = questionService.list(page,size);
            model.addAttribute("pagination",pagination);
            return "index";
        }

}
