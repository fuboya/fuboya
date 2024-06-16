package sourcecode.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sourcecode.dto.AccessTokenDTO;
import sourcecode.dto.GithubUser;
import sourcecode.mapper.UserMapper;
import sourcecode.model.User;
import sourcecode.provider.GithubProvider;


import java.util.UUID;

/*
    负责处理登录github的事件

 */



@Controller

public class AuthorizeController {
    @Autowired//自动注入方法
    private GithubProvider githubProvider;
    @Autowired
    private UserMapper userMapper;//注入UserMapper


    @Value("${github.client_id}")//读取配置文件中键为github.client_id的值赋值给下面定义的变量
    private String client_id;
    @Value("${github.client_secret}")
    private String client_secret;
    @Value("${github.redirect_uri}")
    private String redirect_uri;


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code, //返回的code
                           @RequestParam(name = "state") String state, //返回的状态state
                           HttpServletResponse response
    ) {
        //第二部，授权成功后接受返回的参数code并使用post请求获取accesstoken
            //创建新的accesstokendto数据类
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(client_id);//clientid
        accessTokenDTO.setClient_secret(client_secret);//clientsecret
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);//传入参数获取accesstoken

        //第三步，使用已经获得的accesstoken获取用户信息
        GithubUser githubuser = githubProvider.getUser(accessToken);//传入accesstoken获取githubuser对象

        //第四步，将获取的用户信息重组传入数据库和session
        if(githubuser!=null) {
            //登录成功,编写cookie,创建user对象传入数据库

            String token =UUID.randomUUID().toString();//生成新token
            response.addCookie(new Cookie("token",token));

            User user =new User();
            user.setName(githubuser.getName());
            user.setToken(token);
            user.setAccount_id(String.valueOf(githubuser.getId()));
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            user.setBio(githubuser.getBio());
            userMapper.insert(user);


            return "redirect:/";//重定向

        }else{
            //登陆失败
            return "redirect:/";

        }


    }
}
