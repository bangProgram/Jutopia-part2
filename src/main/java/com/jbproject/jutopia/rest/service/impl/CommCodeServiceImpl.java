package com.jbproject.jutopia.rest.service.impl;

import com.jbproject.jutopia.rest.model.result.CommCodeResult;
import com.jbproject.jutopia.rest.repository.CommCodeRepository;
import com.jbproject.jutopia.rest.service.CommCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommCodeServiceImpl implements CommCodeService {

    private final CommCodeRepository commCodeRepository;
    public List<CommCodeResult> getCommCodeListByGroupCode(String groupCode) {
        return commCodeRepository.getCommCodeListByGroupCode(groupCode);
    }
}
