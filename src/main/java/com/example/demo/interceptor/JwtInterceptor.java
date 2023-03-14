package com.example.demo.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.example.demo.common.Code;
import com.example.demo.entity.User;
import com.example.demo.exception.ServiceException;
import com.example.demo.service.IUserService;
import com.example.demo.utils.JwtUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

public class JwtInterceptor implements HandlerInterceptor {
    @Resource
    private IUserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        /*
        HandlerMethod handlerMethod=(HandlerMethod)handler;
        Method method=handlerMethod.getMethod();
        * */
        if(token == null){
            throw new ServiceException(Code.CODE_401.getCode(),"无token");
        }

        Map<String, Claim> userInfo = JwtUtil.verifyToken(token);
        if(userInfo == null){
            throw new ServiceException(Code.CODE_401.getCode(),"token不合法");
        }

        Integer id = userInfo.get("id").asInt();
        /*
        String username = userInfo.get("username").asString();
        String password = userInfo.get("password").asString();
        * */
        // 根据token中的userid查询数据库
        User user = userService.getById(id);
        if(user == null){
            throw new ServiceException(Code.CODE_401.getCode(),"用户不存在，请重新登陆");
        }

        return true;
    }
}
