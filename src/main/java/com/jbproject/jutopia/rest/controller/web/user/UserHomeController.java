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
import java.util.*;

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

        String[] aInput = br.readLine().split(" ");
        int a1 = Integer.parseInt(aInput[0]);
        int a0 = Integer.parseInt(aInput[1]);
        int c = Integer.parseInt(br.readLine());
        int n0 = Integer.parseInt(br.readLine());
        int n = n0;

        long start = System.currentTimeMillis();

        if( n0 <=(-a0/(a1-c)) ){
            System.out.println(1);
        }else{
            System.out.println(0);
        }

        long end = System.currentTimeMillis();


        System.out.println("Process Time : "+(end-start));
        System.out.println("==== end.. ====");

        return "/user/home/mainPage";
    }

    public long loop1(long n, long rst){
        while(n > 0){
            rst = loop2t(n,rst);
            n--;
        }

        return rst;
    }

    public long loop2(long n, long rst){
        if(n < 1) return rst;

        rst += n--;
        return loop2(n,rst);
    }

    // 개선된 반복문 코드 (안전)
    public long loop2t(long n, long rst) {
        while (n > 0) {
            rst += n;
            n--;
        }
        return rst;
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
