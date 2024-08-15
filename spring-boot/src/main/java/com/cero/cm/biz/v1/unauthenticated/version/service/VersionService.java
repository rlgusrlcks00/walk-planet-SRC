package com.cero.cm.biz.v1.unauthenticated.version.service;

import com.cero.cm.db.entity.Version;
import com.cero.cm.db.repository.version.VersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VersionService {

    private final VersionRepository versionRepository;

    public Version getVersion() {
        return versionRepository.findFirstByOrderByRegDtDesc();
    }

    @Transactional
    public void saveVersion(Double version, Long buildNumber) {
        LocalDateTime now = LocalDateTime.now();
        Version newVersion = new Version();
        newVersion.setVersion(version);
        newVersion.setBuildNumber(buildNumber);
        newVersion.setRegDt(now);
        versionRepository.save(newVersion);
    }
}
