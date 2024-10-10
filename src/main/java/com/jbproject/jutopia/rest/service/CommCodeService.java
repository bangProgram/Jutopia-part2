package com.jbproject.jutopia.rest.service;

import com.jbproject.jutopia.rest.model.result.CommCodeResult;

import java.util.List;

public interface CommCodeService {

    List<CommCodeResult> getCommCodeListByGroupCode(String groupCode);
}
