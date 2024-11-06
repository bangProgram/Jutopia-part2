package com.jbproject.jutopia;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jbproject.jutopia.rest.model.XmlCorpModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootTest
@Transactional
class JutopiaApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void test() throws IOException {

		XmlMapper objectMapper = new XmlMapper();

		MockMultipartFile file = new MockMultipartFile(
				"test", // 파일의 파라미터 이름
				"CORPCODE.xml", // 실제 파일 이름
				"application/xml", // 파일의 확장자 타입
				new FileInputStream(new File("C:/Users/Dejay/Downloads/corpCode/CORPCODE.xml")) // 실제 파일
		);

		XmlCorpModel xmlCorpModel = objectMapper.readValue(file.getInputStream(), XmlCorpModel.class);
		System.out.println("xmlCorpModel : "+xmlCorpModel);

		String fileType = file.getContentType();

	}
}
