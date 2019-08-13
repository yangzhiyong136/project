package com.learning.project.controller;

import com.learning.project.dto.AccessTokenDTO;
import com.learning.project.dto.GithubUser;
import com.learning.project.model.User;
import com.learning.project.provider.GitHubProvider;
import com.learning.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author Youngz
 * @date 2019/8/3 - 21:58
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GitHubProvider gitHubProvider;

    @Value("${github.client.id}")//读取配置文件中的key,value
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response) {


        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        //github中自己创建的授权用户id，等信息
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = gitHubProvider.getUser(accessToken);
        //业务逻辑，判断都再Controller
        //先登录，获取用户信息
        if (githubUser != null && githubUser.getId() != null) {
            User user = new User();
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            String token = UUID.randomUUID().toString();
            user.setToken(token);//以UUID形式
            user.setAvatarUrl(githubUser.getAvatar_url());
            userService.createOrUpdate(user);
            //将token放入到cookie中
            response.addCookie(new Cookie("token", token));//把封装好的对象，传到前端
            //redirect返回的是路径，所以是要"/"
            return "redirect:/";
        } else {
            //登录失败，重新登录
            return "redirect:/";
        }

    }

}
