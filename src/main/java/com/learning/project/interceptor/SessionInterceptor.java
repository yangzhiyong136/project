package com.learning.project.interceptor;

import com.learning.project.mapper.UserMapper;
import com.learning.project.model.User;
import com.learning.project.model.UserExample;
import com.learning.project.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Youngz
 * @date 2019/8/13 - 21:12
 */
//Interceptor可以把地址进行拦截
// (Index、Profile、Publish,的Controller中都要获取一个Session放入到Cookie中，重复代码严重。这个层是对他进行统一处理
@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationService notificationService;

    // @Value("${github.redirect.uri}")
    //  private String redirectUri;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //设置 context 级别的属性
        // request.getServletContext().setAttribute("redirectUri", redirectUri);
        //Cookie是会变的，注意！！！
        Cookie[] cookies = request.getCookies();//获取cookie中的token，判断是否登录
        if (cookies != null && cookies.length != 0)
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    UserExample userExample = new UserExample();
                    userExample.createCriteria().andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(userExample);
                    //   User user = userMapper.findByToken(token);//在数据库中寻找
                   /* if (user != null) {//获取用户信息不为空
                        request.getSession().setAttribute("user", user);//把封装好的对象，传到前端
                    }*/
                    if (users.size() != 0) {
                        request.getSession().setAttribute("user", users.get(0));
                        Long unreadCount = notificationService.unreadCount(users.get(0).getId());
                        request.getSession().setAttribute("unreadCount", unreadCount);
                    }
                        break;
                }
            }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
