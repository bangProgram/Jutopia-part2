package com.jbproject.jutopia.rest.common;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jbproject.jutopia.rest.dto.model.XmlCorpModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

@Component
public class CommonUtils {


    public static Double convertToString(String value){
        String result = value.replaceAll(",","").replaceAll("-","");

        if(result.isEmpty()){
            return 0D;
        }else{
            return Double.valueOf(result);
        }
    }

}