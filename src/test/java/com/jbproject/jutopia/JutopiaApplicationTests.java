package com.jbproject.jutopia;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jbproject.jutopia.config.security.model.Role;
import com.jbproject.jutopia.constant.ServerErrorCode;
import com.jbproject.jutopia.exception.ExceptionProvider;
import com.jbproject.jutopia.model.MenuTestModel;
import com.jbproject.jutopia.model.RoleMenuRTestModel;
import com.jbproject.jutopia.model.RoleTestModel;
import com.jbproject.jutopia.rest.entity.MenuEntity;
import com.jbproject.jutopia.rest.entity.RoleEntity;
import com.jbproject.jutopia.rest.entity.RoleMenuRelation;
import com.jbproject.jutopia.rest.model.XmlCorpModel;
import com.jbproject.jutopia.rest.model.result.MenuResult;
import com.jbproject.jutopia.rest.repository.MenuRepository;
import com.jbproject.jutopia.rest.repository.RoleMenuRepository;
import com.jbproject.jutopia.rest.repository.RoleRepository;
import io.swagger.v3.core.util.Json;
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
import java.util.*;

@SpringBootTest
@Transactional
public class JutopiaApplicationTests {

	@Autowired
	private RoleMenuRepository roleMenuRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private MenuRepository menuRepository;



    @Test
	void contextLoads() {
	}

	@Test
	void roleTest() throws IOException{
		System.out.println("test 돌아가나");
		ObjectMapper objectMapper = new ObjectMapper();

		Optional<RoleEntity> result = roleRepository.findById(Role.ADMIN.name());

		if(result.isPresent()){
			RoleEntity newRole = result.get();
			RoleTestModel roleTestModel = new RoleTestModel(newRole);
			System.out.println("role 있음 : " + Json.pretty(roleTestModel));

			List<RoleMenuRelation> roleMenuList = newRole.getRoleMenuRelations();

			for(RoleMenuRelation roleMenu : roleMenuList){
				RoleMenuRTestModel roleMenuRTestModel = new RoleMenuRTestModel(roleMenu);
				System.out.println("roleMenuList 있나? : " + Json.pretty(roleMenuRTestModel));

				MenuEntity menu = roleMenu.getMenuEntity();
				MenuTestModel menuTestModel = new MenuTestModel(menu);
				System.out.println("menu 있나? : "+Json.pretty(menuTestModel) );
			}

		}else{
			System.out.println("role 없음");
		}
	}


	@Test
	void menuTest() {
		System.out.println("test 돌아가나");
		Optional<MenuEntity> result = menuRepository.findById(1L);

		if(result.isPresent()){
			MenuEntity newMenu = result.get();
			MenuTestModel menuTestModel = new MenuTestModel(newMenu);
			System.out.println("menu 있나? : "+Json.pretty(menuTestModel) );

			List<RoleMenuRelation> roleMenuList = newMenu.getRoleMenuRelations();

			for(RoleMenuRelation roleMenu : roleMenuList){
				RoleMenuRTestModel roleMenuRTestModel = new RoleMenuRTestModel(roleMenu);
				System.out.println("roleMenuList 있나? : " + Json.pretty(roleMenuRTestModel));

				RoleEntity role = roleMenu.getRoleEntity();
				RoleTestModel roleTestModel = new RoleTestModel(role);
				System.out.println("role 있음 : " + Json.pretty(roleTestModel));
			}

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

	@Test
	void menuTest1() {
		// 메뉴 호출
		System.out.println("menuTest Start !!!");
		MenuEntity menu = menuRepository.findById(1L).orElseThrow(
				() -> new ExceptionProvider(ServerErrorCode.POST_404_01)
		);

		MenuResult result = new MenuResult(menu);

		System.out.println("result : "+result);
	}

	@Test
	void roleMenuRelateionTest() {
		System.out.println("roleMenuRelation Test Start !!!");

		List<RoleMenuRelation> roleMenuRelations = roleMenuRepository.findByRoleId("SYSTEM");


	}
}
