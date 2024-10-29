package com.jbproject.jutopia.rest.controller.web.user;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/home")
public class UserHomeController {

    @GetMapping("/main")
    public String goMain(HttpServletRequest request,Model model) {

        return "/user/home/mainPage";
    }

    @GetMapping("/test")
    public String test() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        String[] nm = input.split(" ");

        int[] numList = new int[Integer.parseInt(nm[0])];
        for(int i=0; i< numList.length; i++){
            numList[i] = i+1;
        }


        for(int i=0; i<Integer.parseInt(nm[1]); i++){
            int temp = 0;
            String input2 = br.readLine();
            String[] holNum = input2.split(" ");

            temp = numList[Integer.parseInt(holNum[0])-1];
            numList[Integer.parseInt(holNum[0])-1] = numList[Integer.parseInt(holNum[1])-1];
            numList[Integer.parseInt(holNum[1])-1] = temp;
        }

        for(int i=0; i<numList.length; i++){
            if(i==0) {
                System.out.print(numList[i]);
            }else{
                System.out.print(" "+numList[i]);
            }
        }

        return "/user/home/mainPage";
    }
}
