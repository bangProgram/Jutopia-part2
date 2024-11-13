package com.jbproject.jutopia.rest.controller.web;

import com.jbproject.jutopia.rest.model.CorpDetailModel;
import com.jbproject.jutopia.rest.model.payload.PostSearchPayload;
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

    @PostMapping("/ajax/stock/search")
    public String ajaxHome(Model model, PostSearchPayload postSearchPayload){
        String stockName = "%"+postSearchPayload.getSearchStockName()+"%";

        List<CorpDetailModel> corpDetailList = utilService.getCorpDetailList(stockName);
        model.addAttribute("corpDetailList",corpDetailList);
        return "/user/post/mainPage::#corpDetailSelect";
    }
}
