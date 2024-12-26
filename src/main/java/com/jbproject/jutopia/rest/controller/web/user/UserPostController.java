package com.jbproject.jutopia.rest.controller.web.user;

import com.jbproject.jutopia.config.security.jwt.AccessJwtToken;
import com.jbproject.jutopia.constant.CommonConstatns;
import com.jbproject.jutopia.rest.dto.payload.ViewPostPayload;
import com.jbproject.jutopia.rest.dto.payload.SearchPostPayload;
import com.jbproject.jutopia.rest.dto.payload.ReplyPayload;
import com.jbproject.jutopia.rest.dto.result.CommCodeResult;
import com.jbproject.jutopia.rest.dto.result.PostResult;
import com.jbproject.jutopia.rest.service.CommCodeService;
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
    private final CommCodeService commCodeService;

    @GetMapping("/main")
    public String goPostMain(
            HttpServletRequest request, Model model
            , SearchPostPayload searchPostPayload
    ) {
        List<CommCodeResult> postTypeList = commCodeService.getCommCodeListByGroupCode(CommonConstatns.POST_TYPE);

        model.addAttribute("postTypeList", postTypeList);
        model.addAttribute("postSearchPayload", searchPostPayload);
        return "/user/post/mainPage";
    }

    @GetMapping("/write")
    public String goPostWrite(
            HttpServletRequest request, Model model
            , ViewPostPayload viewPostPayload
    ){
        System.out.println("1. postDetailPayload : "+ viewPostPayload);

        model.addAttribute("postDetailPayload", viewPostPayload);
        return "/user/post/writePage";
    }

    @GetMapping("/view/{postId}")
    public String goPostView(
            HttpServletRequest request, Model model
            , @PathVariable(value = "postId") Long postId
            , ReplyPayload replyPayload
    ){
        PostResult postResult = userPostService.getPostDetail(postId);

        model.addAttribute("replyPayload",replyPayload);
        model.addAttribute("postResult",postResult);
        return "/user/post/viewPage";
    }

    @PostMapping("/create/cud")
    public RedirectView postCreateCud(
            ViewPostPayload viewPostPayload
            , RedirectAttributes redirectAttributes
            , @AuthenticationPrincipal AccessJwtToken.AccessJwtPrincipal principal
    ){

        Long postId = userPostService.savePost(viewPostPayload, principal);

        if(viewPostPayload.getPostId() != null) {
            redirectAttributes.addFlashAttribute("serverMessage","게시글이 수정 되었습니다.");
        }else{
            redirectAttributes.addFlashAttribute("serverMessage","게시글이 작성 되었습니다.");
        }

        return new RedirectView("/post/view/"+postId);
    }


    @PostMapping("/reply/cud")
    public RedirectView postReplyCud(
            ReplyPayload replyPayload
            , RedirectAttributes redirectAttributes
            , @AuthenticationPrincipal AccessJwtToken.AccessJwtPrincipal principal
    ){

        Long postId = replyPayload.getPostId();
        userPostService.savePostReply(replyPayload, principal);

        if(replyPayload.getReplyId() != null) {
            redirectAttributes.addFlashAttribute("serverMessage","댓글이 수정 되었습니다.");
        }else{
            redirectAttributes.addFlashAttribute("serverMessage","댓글이 작성 되었습니다.");
        }

        return new RedirectView("/post/view/"+postId);
    }

}
