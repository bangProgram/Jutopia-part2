package com.jbproject.jutopia.rest.controller.web.user;

import com.jbproject.jutopia.rest.model.payload.PostDetailPayload;
import com.jbproject.jutopia.rest.model.payload.PostSearchPayload;
import com.jbproject.jutopia.rest.service.UserPostService;
import groovy.util.logging.Slf4j;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/post")
public class UserPostController {

    private final UserPostService userPostService;

    @GetMapping("/main")
    public String goBoard(
            HttpServletRequest request, Model model
            , PostSearchPayload postSearchPayload
    ) {

        model.addAttribute("postSearchPayload",postSearchPayload);
        return "/user/post/mainPage";
    }

    @GetMapping("/detail")
    public String goPostDetail(
            HttpServletRequest request, Model model
            , PostDetailPayload postDetailPayload
    ){
        model.addAttribute("postDetailPayload",postDetailPayload);
        return "/user/post/detailPage";
    }
}
