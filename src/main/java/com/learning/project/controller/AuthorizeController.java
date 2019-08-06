package com.learning.project.controller;

import com.learning.project.dto.AccessTokenDTO;
import com.learning.project.dto.GithubUser;
import com.learning.project.mapper.UserMapper;
import com.learning.project.model.User;
import com.learning.project.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author Youngz
 * @date 2019/8/3 - 21:58
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GitHubProvider gitHubProvider;

    @Autowired
    private UserMapper userMapper;

    @Value("${github.client.id}")//读取配置文件中的key,value
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request) {

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
        if (githubUser != null) {
            User user = new User();
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setToken(UUID.randomUUID().toString());//以UUID形式
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate(0));
            userMapper.insert(user);//插入不了

            //登录成功，写cookie，和session
            request.getSession().setAttribute("user", githubUser);
            //redirect返回的是路径，所以是要"/"
            return "redirect:/";
        } else {
            //登录失败，重新登录
            return "redirect:/";
        }

    }

}
