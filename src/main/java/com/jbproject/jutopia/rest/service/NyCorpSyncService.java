package com.jbproject.jutopia.rest.service;

import org.springframework.web.multipart.MultipartFile;

public interface NyCorpSyncService {

    int uploadAndParseCompanyFacts(MultipartFile zipFile) throws Exception;
}
