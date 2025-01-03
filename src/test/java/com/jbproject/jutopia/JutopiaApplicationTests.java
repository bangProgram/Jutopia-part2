package com.jbproject.jutopia;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jbproject.jutopia.config.security.model.Role;
import com.jbproject.jutopia.constant.ServerErrorCode;
import com.jbproject.jutopia.exception.ExceptionProvider;
import com.jbproject.jutopia.model.MenuTestModel;
import com.jbproject.jutopia.model.RoleMenuRTestModel;
import com.jbproject.jutopia.model.RoleTestModel;
import com.jbproject.jutopia.rest.entity.*;
import com.jbproject.jutopia.rest.entity.key.CorpCisStatTKey;
import com.jbproject.jutopia.rest.entity.relation.PostReplyRelation;
import com.jbproject.jutopia.rest.entity.relation.RoleMenuRelation;
import com.jbproject.jutopia.rest.entity.statistics.CorpCis2020Entity;
import com.jbproject.jutopia.rest.dto.model.CorpCisModel;
import com.jbproject.jutopia.rest.dto.model.XmlCorpModel;
import com.jbproject.jutopia.rest.dto.payload.MergeCorpCisStatPayload;
import com.jbproject.jutopia.rest.dto.result.MenuResult;
import com.jbproject.jutopia.rest.dto.result.ReplyResult;
import com.jbproject.jutopia.rest.repository.*;
import com.jbproject.jutopia.rest.repository.statistics.CorpCis2020Repository;
import com.jbproject.jutopia.rest.repository.statistics.CorpCisStat;
import io.swagger.v3.core.util.Json;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional
public class JutopiaApplicationTests {

	@Autowired
	private RoleMenuRepository roleMenuRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private MenuRepository menuRepository;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private PostReplyRepository postReplyRepository;
	@Autowired
	private ReplyRepository replyRepository;
	@Autowired
	private CorpCisRepository corpCisRepository;
	@Autowired
	private CorpCis2020Repository corpCis2020Repository;
	@Autowired
	private CorpCisStat<CorpCis2020Entity> corpCis2020EntityCorpCisStat;



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

	@Test
	void ReplyTest() {
		List<PostReplyRelation> postReplyRelations = postReplyRepository.findByPostId(5L).stream().toList();
		List<ReplyEntity> replyEntities = postReplyRelations.stream().map(PostReplyRelation::getReplyEntity).filter(entity -> entity.getParentId() == null).toList();

		List<ReplyResult> result = new ArrayList<>();

		for(ReplyEntity replyEntity : replyEntities) {
			ReplyResult replyResult = new ReplyResult(replyEntity);
			result.add(replyResult);
		}

		System.out.println("result : "+result);
	}

	@Test
	void ReplyTest1() {
		Long startTime = System.currentTimeMillis();
		List<PostReplyRelation> postReplyRelationList = postReplyRepository.findByPostId(5L).stream().toList();

		List<ReplyResult> allReplyList = postReplyRelationList.stream().map(PostReplyRelation::getReplyEntity).toList().stream().map(ReplyResult::create).toList();

		Map<Long, List<ReplyResult>> replyGroupByParentId = allReplyList
				.stream().filter(replyResult -> replyResult.getParentId() != null)
				.collect(Collectors.groupingBy(ReplyResult::getParentId));

		List<ReplyResult> results = allReplyList.stream().filter(replyResult -> replyResult.getParentId() == null).toList();

		for(ReplyResult reply : results){
			reply.setChildReplyList(setChildList(reply,replyGroupByParentId));
		}
		Long endTime = System.currentTimeMillis();

		System.out.println("Process Time : "+(endTime - startTime));
		System.out.println("results : "+results);
	}

	private  List<ReplyResult> setChildList(ReplyResult parentReply, Map<Long, List<ReplyResult>> replyGroupByParentId){
		List<ReplyResult> curChildList = replyGroupByParentId.get(parentReply.getReplyId());
		if(curChildList == null) return null;

		for(ReplyResult curReply : curChildList){
			List<ReplyResult> grandSonReply = replyGroupByParentId.get(curReply.getReplyId());
			if(grandSonReply != null) {
				curReply.setChildReplyList(setChildList(curReply, replyGroupByParentId));
			}
		}
		return replyGroupByParentId.get(parentReply.getReplyId());
	}

	@Test
	void replyTest2(){
		Long startTime = System.currentTimeMillis();

		PostEntity postEntity = postRepository.findById(5L).orElseThrow();

		List<PostReplyRelation> postReplyRelationList = postEntity.getPostReplyRelation();

		List<ReplyResult> allReplyList = postReplyRelationList.stream().map(PostReplyRelation::getReplyEntity).toList().stream().map(ReplyResult::create).toList();

		List<ReplyResult> resultList = new ArrayList<>();
		Map<Long, ReplyResult> parent = new HashMap<>();

		for (ReplyResult replyResult : allReplyList) {
			parent.put(replyResult.getReplyId(), replyResult);
			if(replyResult.getParentId() == null) resultList.add(replyResult);
			else {
				parent.get(replyResult.getParentId())
						.getChildReplyList()
						.add(replyResult);
			}
		}

		Long endTime = System.currentTimeMillis();

		System.out.println("Process Time : "+(endTime - startTime));
		System.out.println("resultList : " + resultList);
	}

	@Test
	void corpCisTest(){
		List<String> accountIds = new ArrayList<>();
		accountIds.add("ifrs-full_Revenue");
		accountIds.add("dart_OperatingIncomeLoss");
		accountIds.add("ifrs-full_ProfitLoss");
		accountIds.add("ifrs-full_EarningsPerShareAbstract");
		Long start = System.currentTimeMillis();

	}

	@Test
	void mergeCisStat(){
		MergeCorpCisStatPayload payload = new MergeCorpCisStatPayload();
		payload.setBsnsYear("2023");
		List<String> corpCls = new ArrayList<>();
		corpCls.add("Y");
		corpCls.add("K");
		payload.setCorpCls(corpCls);

		List<CorpCisModel> results = corpCisRepository.getCorpCisList(payload);

		CorpCisStatTKey corpCisKey = new CorpCisStatTKey();
		corpCisKey.setAccountId("1");
		corpCisKey.setStockCode("1");
		corpCisKey.setQuarterlyReportCode("1");

		corpCis2020EntityCorpCisStat.findById(corpCisKey);
	}
}
