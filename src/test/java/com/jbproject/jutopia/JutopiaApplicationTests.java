package com.jbproject.jutopia;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jbproject.jutopia.rest.entity.RoleEntity;
import com.jbproject.jutopia.rest.model.XmlCorpModel;
import com.jbproject.jutopia.rest.repository.RoleMenuRepository;
import com.jbproject.jutopia.rest.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

@SpringBootTest
@Transactional
public class JutopiaApplicationTests {

	@Autowired
	private RoleMenuRepository roleMenuRepository;
	@Autowired
	private RoleRepository roleRepository;



    @Test
	void contextLoads() {
	}

	@Test
	void test1() {
		System.out.println("test 돌아가나");
		Optional<RoleEntity> result = roleRepository.findById("SYSTEM");

		if(result.isPresent()){
			RoleEntity newRole = result.get();
			System.out.println("role 있음" + newRole);
		}else{
			System.out.println("role 없음");
		}
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
