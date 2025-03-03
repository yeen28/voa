package com.project.voa.repository;

import com.project.voa.domain.UserInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {
	boolean existsByUserEmail(String userEmail);
	UserInfo findUserInfoByUserEmail(String userEmail);
	Optional<UserInfo> findByUserEmail(String userEmail);
}
