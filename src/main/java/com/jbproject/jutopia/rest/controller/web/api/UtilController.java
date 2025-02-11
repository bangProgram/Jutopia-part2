package com.jbproject.jutopia.rest.controller.web.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbproject.jutopia.rest.dto.model.CorpDetailModel;
import com.jbproject.jutopia.rest.dto.model.NyStockModel;
import com.jbproject.jutopia.rest.dto.payload.SearchCorpPayload;
import com.jbproject.jutopia.rest.dto.payload.SearchNyCorpPayload;
import com.jbproject.jutopia.rest.dto.payload.SearchPostPayload;
import com.jbproject.jutopia.rest.dto.payload.SearchReplyPayload;
import com.jbproject.jutopia.rest.dto.result.PostResult;
import com.jbproject.jutopia.rest.dto.result.ReplyResult;
import com.jbproject.jutopia.rest.service.UserInvestService;
import com.jbproject.jutopia.rest.service.UserPostService;
import com.jbproject.jutopia.rest.service.UtilService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UtilController {

    private final UtilService utilService;
    private final UserPostService userPostService;
    private final UserInvestService userInvestService;

    private final ObjectMapper objectMapper = new ObjectMapper();

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
    @PostMapping("/ajax/nyCorp/search")
    @ResponseBody
    public String searchNyCorp(Model model, SearchNyCorpPayload searchNyCorpPayload){
        try{
            List<NyStockModel> nyCorpList = userInvestService.searchNyCorpList(searchNyCorpPayload);
            return objectMapper.writeValueAsString(nyCorpList);
        }catch (Exception e){
            return "기업 검색에 실패했습니다..";
        }
    }
}
