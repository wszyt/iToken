package com.zyt.itoken.service.sso.controller;

import com.zyt.itoken.common.domain.TbSysUser;
import com.zyt.itoken.common.utils.CookieUtils;
import com.zyt.itoken.common.utils.MapperUtils;
import com.zyt.itoken.service.sso.service.LoginService;
import com.zyt.itoken.service.sso.service.consumer.RedisService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class LoginController {
    @Autowired
    LoginService loginService;

    @Autowired
    RedisService redisService;

    @RequestMapping(value = {"", "login"}, method = RequestMethod.GET)
    public String login(HttpServletRequest request, Model model, @RequestParam(required = false) String url) {
        String token = CookieUtils.getCookieValue (request, "token");

        //token不为空可能已登录
        if (StringUtils.isNotBlank (token)) {
            String loginCode = redisService.get (token);
            if (StringUtils.isNotBlank (loginCode)) {
                String json = redisService.get (loginCode);
                if (StringUtils.isNotBlank (json)) {
                    try {
                        TbSysUser tbSysUser = MapperUtils.json2pojo (json, TbSysUser.class);
                        //已登录
                        if (tbSysUser != null) {
                            if (StringUtils.isNotBlank (url)) {
                                return "redirect:" + url;
                            }
                        }

                        //将登录信息传到登录页
                        model.addAttribute ("admin", tbSysUser);
                    } catch (Exception e) {
                        e.printStackTrace ();
                    }

                }
            }
        }

        if (StringUtils.isNotBlank (url)) {
            model.addAttribute ("url", url);
        }

        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(String loginCode, String password, @RequestParam(required = false) String url,
                        HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {

        //登录失败
        TbSysUser tbSysUser = loginService.login (loginCode, password);
        if(tbSysUser == null) {
            redirectAttributes.addFlashAttribute ("message", "用户名或密码错误，请重新输入");
        }

        //登录成功
        else {
            String token = UUID.randomUUID ().toString ();
            String result = redisService.put (token, loginCode, 60 * 60 * 24);
            //将Token放入缓存
            if ("ok".equals (result) && StringUtils.isNotBlank (result)) {
                CookieUtils.setCookie (request, response, "token", token, 60 * 60 * 24);
                if (StringUtils.isNotBlank (url)) {
                    return "redirect:" + url;
                }
            }

            //熔断处理
            else {
                redirectAttributes.addFlashAttribute ("message", "服务器异常，请稍后再试");
            }
        }

        return "redirect:/login";
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String Logout(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) String url, Model model) {
        CookieUtils.deleteCookie (request, response, "token");

        return login(request, model, url);
    }

}
