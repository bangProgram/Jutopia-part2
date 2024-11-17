package com.jbproject.jutopia.rest.controller.web;

import com.jbproject.jutopia.rest.model.CorpDetailModel;
import com.jbproject.jutopia.rest.model.payload.PostSearchPayload;
import com.jbproject.jutopia.rest.model.payload.ReplySearchPayload;
import com.jbproject.jutopia.rest.model.result.PostResult;
import com.jbproject.jutopia.rest.model.result.ReplyResult;
import com.jbproject.jutopia.rest.service.UserPostService;
import com.jbproject.jutopia.rest.service.UtilService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UtilController {

    private final UtilService utilService;
    private final UserPostService userPostService;

    @PostMapping("/ajax/stock/search")
    public String searchStock(Model model, PostSearchPayload postSearchPayload){
        String stockName = "%"+postSearchPayload.getSearchStockName()+"%";

        List<CorpDetailModel> corpDetailList = utilService.getCorpDetailList(stockName);
        model.addAttribute("corpDetailList",corpDetailList);
        return "/user/post/mainPage::#corpDetailSelect";
    }

    @PostMapping("/ajax/post/search")
    public String searchPost(Model model, PostSearchPayload postSearchPayload){
        List<PostResult> postList = userPostService.searchPostList(postSearchPayload);

        model.addAttribute("postList",postList);
        return "/user/post/mainPage::#postListTable";
    }

    @PostMapping("/ajax/reply/search")
    public String searchReply(Model model, ReplySearchPayload replySearchPayload){
        List<ReplyResult> replyList = userPostService.searchReplyList(replySearchPayload);

        model.addAttribute("replyList",replyList);
        return "/user/post/viewPage::#replyListTable";
    }
}
