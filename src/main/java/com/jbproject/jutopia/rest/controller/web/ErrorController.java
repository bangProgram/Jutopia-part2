package com.jbproject.jutopia.rest.controller.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbproject.jutopia.exception.model.ExceptionModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/error")
public class ErrorController {

    @GetMapping({
            "/main",
            "/auth"
    })
    public String goError(HttpServletRequest request, HttpServletResponse response, Model model) throws JsonProcessingException {

        String body = request.getAttribute("errorBody").toString();
        ExceptionModel exceptionModel = new ObjectMapper().readValue(body,ExceptionModel.class);

        System.out.println("request getStatusCode : "+exceptionModel.getStatusCode());
        System.out.println("request getErrorCode : "+exceptionModel.getErrorCode());
        System.out.println("request getErrorMsg : "+exceptionModel.getErrorMsg());

        model.addAttribute("statusCode",exceptionModel.getStatusCode());
        model.addAttribute("errorCode",exceptionModel.getErrorCode());
        model.addAttribute("message",exceptionModel.getErrorMsg());
        return "/error/auth";
    }
}
