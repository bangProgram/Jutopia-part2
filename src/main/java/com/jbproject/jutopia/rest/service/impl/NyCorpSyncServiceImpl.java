package com.jbproject.jutopia.rest.service.impl;

import com.jbproject.jutopia.config.EdgarClient;
import com.jbproject.jutopia.rest.dto.model.NyCorpCisModel;
import com.jbproject.jutopia.rest.entity.NyCorpCisEntity;
import com.jbproject.jutopia.rest.repository.NyCorpCisRepository;
import com.jbproject.jutopia.rest.service.NyCorpSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
@RequiredArgsConstructor
@Transactional
public class NyCorpSyncServiceImpl implements NyCorpSyncService {

    private final EdgarClient edgarClient;
    private final NyCorpCisRepository nyCorpCisRepository;
    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir") + "/companyfacts";

    public int uploadAndParseCompanyFacts(MultipartFile zipFile) throws Exception{

        // 1. 임시 디렉토리에 저장
        File tempZip = new File(TEMP_DIR, "companyfacts.zip");
        zipFile.transferTo(tempZip);

        // 2. 압축 해제
        File extractDir = new File(TEMP_DIR, "unzipped");
        if (!extractDir.exists()) extractDir.mkdirs();
        unzip(tempZip, extractDir);

        // 3. 모든 JSON 파일 처리
        File[] files = extractDir.listFiles((dir, name) -> name.endsWith(".json"));
        int totalSaved = 0;
        int totalCorp = 0;

        if (files == null || files.length == 0) return totalSaved;

        for (File json : files) {
            List<NyCorpCisModel> models = edgarClient.parseCompanyFacts(json.toPath());
            List<NyCorpCisEntity> entities = models.stream().map(NyCorpCisModel::create).toList();
            nyCorpCisRepository.saveAll(entities);
            totalSaved += entities.size();
            totalCorp++;
        }

        return totalCorp;
    }

    // ZIP 압축 해제
    private void unzip(File zipFile, File destDir) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                File newFile = new File(destDir, entry.getName());
                try (FileOutputStream fos = new FileOutputStream(newFile)) {
                    zis.transferTo(fos);
                }
            }
        }
    }
}
