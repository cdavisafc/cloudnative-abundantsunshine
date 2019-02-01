package com.corneliadavis.cloudnative.newpostsfromconnections;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import com.corneliadavis.cloudnative.config.CloudnativeApplication;

@RestController
public class LoginController {

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public void whoareyou(@RequestParam(value="username", required=false) String username, HttpServletResponse response) {

        if (username == null)
            response.setStatus(400);
        else {
            UUID uuid = UUID.randomUUID();
            String userToken = uuid.toString();

            CloudnativeApplication.validTokens.put(userToken, username);
            response.addCookie(new Cookie("userToken", userToken));
        }
    }

}
