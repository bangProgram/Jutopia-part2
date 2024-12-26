package com.jbproject.jutopia.rest.controller.web;

import com.jbproject.jutopia.rest.dto.CorpDetailModel;
import com.jbproject.jutopia.rest.dto.payload.SearchCorpPayload;
import com.jbproject.jutopia.rest.dto.payload.SearchPostPayload;
import com.jbproject.jutopia.rest.dto.payload.SearchReplyPayload;
import com.jbproject.jutopia.rest.dto.result.PostResult;
import com.jbproject.jutopia.rest.dto.result.ReplyResult;
import com.jbproject.jutopia.rest.service.UserPostService;
import com.jbproject.jutopia.rest.service.UtilService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UtilController {

    private final UtilService utilService;
    private final UserPostService userPostService;

    @PostMapping("/ajax/stock/search")
    public String searchStock(Model model, SearchPostPayload searchPostPayload){
        String stockName = "%"+ searchPostPayload.getSearchStockName()+"%";

        List<CorpDetailModel> corpDetailList = utilService.getCorpDetailList(stockName);
        model.addAttribute("corpDetailList",corpDetailList);
        return "/user/post/mainPage::#corpDetailSelect";
    }

    @PostMapping("/ajax/post/search")
    public String searchPost(Model model, SearchPostPayload searchPostPayload){
        List<PostResult> postList = userPostService.searchPostList(searchPostPayload);

        model.addAttribute("postList",postList);
        return "/user/post/mainPage::#postListTable";
    }

    @PostMapping("/ajax/reply/search")
    public String searchReply(Model model, SearchReplyPayload searchReplyPayload){
        List<ReplyResult> replyList = userPostService.searchReplyList(searchReplyPayload);

        System.out.println("replyList : "+replyList);
        model.addAttribute("replyList",replyList);
        return "/user/post/viewPage::#replyListTable";
    }

    @PostMapping("/ajax/corp/search")
    public String searchCorp(Model model, SearchCorpPayload searchCorpPayload){

        return "/user/corp/mainPage::#corpListTable";
    }
}
