package com.jbproject.jutopia.rest.controller.web.user;

import com.jbproject.jutopia.config.security.jwt.AccessJwtToken;
import com.jbproject.jutopia.rest.model.payload.PostViewPayload;
import com.jbproject.jutopia.rest.model.payload.PostSearchPayload;
import com.jbproject.jutopia.rest.model.result.PostResult;
import com.jbproject.jutopia.rest.service.UserPostService;
import groovy.util.logging.Slf4j;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/post")
public class UserPostController {

    private final UserPostService userPostService;

    @GetMapping("/main")
    public String goPostMain(
            HttpServletRequest request, Model model
            , PostSearchPayload postSearchPayload
    ) {
        model.addAttribute("postSearchPayload",postSearchPayload);
        return "/user/post/mainPage";
    }

    @GetMapping("/write")
    public String goPostWrite(
            HttpServletRequest request, Model model
            , PostViewPayload postViewPayload
    ){
        System.out.println("1. postDetailPayload : "+ postViewPayload);

        model.addAttribute("postDetailPayload", postViewPayload);
        return "/user/post/writePage";
    }

    @GetMapping("/view/{postId}")
    public String goPostView(
            HttpServletRequest request, Model model
            ,@PathVariable(value = "postId") Long postId
    ){
        PostResult postResult = userPostService.getPostDetail(postId);

        model.addAttribute("postResult",postResult);
        return "/user/post/viewPage";
    }

    @PostMapping("/create/cud")
    public RedirectView postCreateCud(
            PostViewPayload postViewPayload
            , RedirectAttributes redirectAttributes
            , @AuthenticationPrincipal AccessJwtToken.AccessJwtPrincipal principal
    ){

        Long postId = userPostService.savePost(postViewPayload, principal);

        if(postViewPayload.getPostId() != null) {
            redirectAttributes.addFlashAttribute("serverMessage","게시글이 수정 되었습니다.");
        }else{
            redirectAttributes.addFlashAttribute("serverMessage","게시글이 작성 되었습니다.");
        }

        return new RedirectView("/post/view/"+postId);
    }

}
