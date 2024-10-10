package com.jbproject.jutopia.rest.repository.custom;

import com.jbproject.jutopia.rest.model.result.CommCodeResult;

import java.util.List;

public interface CommCodeCustom {

    List<CommCodeResult> getCommCodeListByGroupCode(String groupCode);
}
