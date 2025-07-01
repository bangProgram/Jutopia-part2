package com.jbproject.jutopia.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jbproject.jutopia.config.TickerCikCache;
import com.jbproject.jutopia.rest.dto.model.XmlCorpModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class CommonUtils {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public static Double convertToString(String value){
        String result = value.replaceAll(",","");

        if(result.isEmpty() || result.equals("-")){
            return 0D;
        }else{
            return Double.valueOf(result);
        }
    }

    public static Double convertStringToDouble(String value){
        String result = value.replaceAll(",","");

        if(result.isEmpty() || result.equals("-")){
            return 0D;
        }else{
            return Double.valueOf(result);
        }
    }

}
