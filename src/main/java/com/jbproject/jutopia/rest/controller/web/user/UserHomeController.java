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
            String[] inputs = br.readLine().split(" ");

            int num1 = Integer.parseInt(inputs[0]);
            int num2 = Integer.parseInt(inputs[1]);

            if(num1 == 0 && num2 == 0){
                break;
            }

            if(num1/num2 > 0 && num1%num2 == 0){
                bw.write("multiple\n");
            }else if(num2/num1 > 0 && num2%num1 == 0){
                bw.write("factor\n");
            }else{
                bw.write("neither\n");
            }
        }

        bw.flush();
        bw.close();



        System.out.println("==== end.. ====");

        return "/user/home/mainPage";
    }

    public int charConvert(char ch){
        int rst;
        if(ch > 57){
            rst = ch - 55;
        }else{
            rst = ch - 48;
        }
        return rst;
    }

    public String intConvert(int num){
        if(num > 9){
            return Character.toString(num + 55);
        }else{
            return String.valueOf(num);
        }
    }
}
