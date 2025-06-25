package com.jbproject.jutopia.rest.controller.web.user;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/home")
public class UserHomeController {

    @GetMapping({
            ""
            ,"/main"
    })
    public String goMain(HttpServletRequest request,Model model) {

        return "/user/home/mainPage";
    }

    @GetMapping("/bootstrap")
    public String bootstrapIndex() {

        return "/bootstrap/index";
    }

    @GetMapping("/test")
    public String test() throws IOException, NullPointerException {
        System.out.println("==== start!! ====");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));


        while(true){
            int num = Integer.parseInt(br.readLine());
            if(num < 0) break;

            int chk = 0;
            String rst = "";
            for(int i=1; i<num; i++){
                if((num%i) == 0){
                    chk += i;
                    if(i == 1){
                        rst += num + " = "+i+"";
                    }else{
                        rst += " + "+i;
                    }
                }
            }
            if(num == chk){
                bw.write(rst+"\n");
            }else{
                bw.write(num+" is NOT perfect.\n");
            }
        }

        bw.flush();
        bw.close();


        System.out.println("==== end.. ====");

        return "/user/home/mainPage";
    }

    public void backup(String[] input){
        int num = Integer.parseInt(input[0]);
        int cnt = Integer.parseInt(input[1]);
        int chk = 0;

        for(int i=1; i<=num; i++){
            if((num%i) == 0){
                chk++;
                if(cnt == chk){
                    System.out.println(i);
                    break;
                }
            }
        }
        System.out.println(0);
    }
}
