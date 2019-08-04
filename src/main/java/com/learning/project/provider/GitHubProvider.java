package com.learning.project.provider;

import com.alibaba.fastjson.JSON;
import com.learning.project.dto.AccessTokenDTO;
import com.learning.project.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Youngz
 * @date 2019/8/3 - 22:11
 */
@Component//获取变量的注解
public class GitHubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType
                = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
          String string = response.body().string();
          String token = string.split("&")[0].split("=")[1];

           return token;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

 public GithubUser getUser(String accessToken){
     OkHttpClient client = new OkHttpClient();
     Request request = new Request.Builder()
             .url("https://api.github.com/user?access_token="+accessToken)
             .build();
     try {
         Response response = client.newCall(request).execute();
         String string = response.body().string();
         GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
     //string json对象，自动解析成java对象
         return githubUser;
     } catch (IOException e) {
     }
     return null;
 }

}
