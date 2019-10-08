package com.tbsinfo.questionlib.controller;

import com.tbsinfo.questionlib.component.RetData;
import com.tbsinfo.questionlib.model.UserInfo;
import com.tbsinfo.questionlib.service.UserService;
import com.tbsinfo.questionlib.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author jayMamba
 * @date 2019/9/25
 * @time 15:02
 * @desc
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public void toIndex(HttpServletResponse response) throws Exception {
        response.sendRedirect("/classic/math-index.html");
    }

    @PostMapping("/login")
    public RetData login(@RequestBody UserInfo userInfo, HttpSession session) {
        return userService.login(userInfo,session);
    }

    @PostMapping("/addUser")
    public RetData addUser(@RequestBody UserInfo userInfo) {
        return userService.addUser(userInfo);
    }

    @PostMapping("/logout")
    public RetData logout() {
        RequestUtil.getRequest().getSession().removeAttribute("user");
        return new RetData().success("");
    }
    @PostMapping("/getCurrentUser")
    public RetData getCurrentUser() {
        return new RetData().success(RequestUtil.getRequest().getSession().getAttribute("user"));
    }

}
