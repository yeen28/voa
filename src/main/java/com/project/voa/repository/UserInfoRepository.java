package com.project.voa.repository;

import com.project.voa.domain.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {
    boolean existsByUserEmail(String userEmail);
}
