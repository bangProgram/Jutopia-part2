package com.jbproject.jutopia.rest.controller.web.user;

import com.jbproject.jutopia.rest.dto.payload.SearchCorpPayload;
import groovy.util.logging.Slf4j;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/corp")
public class UserCorpController {

    @GetMapping("/main")
    public String goCorp(
            HttpServletRequest request, Model model
        , SearchCorpPayload searchCorpPayload
    ) {

        model.addAttribute("searchCorpPayload", searchCorpPayload);
        return "/user/corp/mainPage";
    }
}
