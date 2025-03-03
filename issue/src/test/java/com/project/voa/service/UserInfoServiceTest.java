package com.project.voa.service;

import com.project.voa.dto.VersionModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserInfoServiceTest {
    @Autowired VersionService versionService;

    @Test
    public void createVersionTest() {
        String version = "test";
        versionService.createVersion(version);

        List<VersionModel> results = versionService.getVersions();

        assertEquals(1, results.size());
        assertEquals(version, results.get(0).getName());
    }

    @Test
    public void deleteVersion() {
        String version = "test";
        versionService.createVersion(version);

        List<VersionModel> results = versionService.getVersions();
        long id = Long.parseLong(results.get(0).getId());

        versionService.deleteVersion(id);

        assertEquals(0, versionService.getVersions().size());
    }
}