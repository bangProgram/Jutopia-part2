package com.jbproject.jutopia.rest.controller.web.user;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserHomeController {

    @GetMapping({
            "/"
            ,"/home/main"
    })
    public String goMain(HttpServletRequest request,Model model) {

        return "/user/home/mainPage";
    }

    @GetMapping("/test")
    public String test() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int input = Integer.parseInt(br.readLine());
        int cnt = 2 * input - 1;

        StringBuilder sb = new StringBuilder("    ");
        for(int i=1; i<=cnt; i++){
            if(i>=input){
                System.out.println(sb);
                if(i<cnt) sb.delete(sb.length()-2, sb.length());
            }else{
                System.out.println(sb);
                sb.append(" ");
                sb.append("**");
            }
        }

        return "/user/home/mainPage";
    }
}
