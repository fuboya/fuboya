package sourcecode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sourcecode.dto.AccessTokenDTO;
import sourcecode.dto.GithubUser;
import sourcecode.provider.GithubProvider;

/*
    负责处理登录github的事件

 */



@Controller
public class AuthorizeController {
    @Autowired//自动注入方法
    private GithubProvider githubProvider;

    @Value("${github.client_id}")//读取配置文件中键为github.client_id的值赋值给下面定义的变量
    private String client_id;
    @Value("${github.client_secret}")
    private String client_secret;
    @Value("${github.redirect_uri}")
    private String redirect_uri;

     //第二部，授权成功后接受返回的参数code并使用post请求获取accesstoken
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code, //返回的code
                           @RequestParam(name = "state") String state //返回的状态state
    ) {

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();//创建新的accesstokendto数据类
        accessTokenDTO.setClient_id(client_id);//clientid
        accessTokenDTO.setClient_secret(client_secret);//clientsecret
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirect_uri);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);//传入参数获取accesstoken
        GithubUser user = githubProvider.getUser(accessToken);//传入accesstoken获取user对象
        System.out.println(user.getId());

        return "index";
    }
}
