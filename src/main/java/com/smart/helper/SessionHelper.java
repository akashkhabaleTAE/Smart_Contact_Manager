package com.smart.helper;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class SessionHelper
{
    public void removeMessage(){
        final HttpSession session = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes())
                .getRequest()
                .getSession();
        session.removeAttribute("message");
    }
}
