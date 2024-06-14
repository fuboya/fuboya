package sourcecode.provider;

import com.alibaba.fastjson2.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;

import sourcecode.dto.AccessTokenDTO;
import sourcecode.dto.GithubUser;

import java.io.IOException;

//向github接口发送请求的类
@Component//将当前类初始化到spring上下文,使用时可以自动注入
public class GithubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO)//获取accesstoken并返回
    {
        MediaType mediaType = MediaType.get("application/json");//定义请求类型json
        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));//自动将accesstokendto对象转成json字符串构建请求体

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")//github接口
                .post(body)//post
                .build();

        try {

            Response response = client.newCall(request).execute();//执行
            String string = response.body().string();//获取返回信息中的字符串

            String accessToken = string.split("&")[0].split("=")[1];
            //根据&拆分字符串,因为返回是access_token=........&token_type=......获取他的第一个
            //根据=划分字符串，因为第一次划分完成后是access_token=........，取他的后半部分

            return accessToken;

        } catch (IOException e) {
            return null;
        }
    }
    public GithubUser getUser(String accessToken)//获取user信息并返回
    {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")//github接口
                .header("Authorization","token "+accessToken)
                .build();

        try {

            Response response = client.newCall(request).execute();
            String string = response.body().string();//获取返回信息中的字符串
            GithubUser githubUser = JSON.parseObject(string,GithubUser.class);//将获取的字符串自动转化成java类对象
            return githubUser;

        } catch (IOException e) {
            return null;
        }
    }
}
