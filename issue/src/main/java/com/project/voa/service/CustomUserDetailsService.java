package com.project.voa.service;

import com.project.voa.domain.UserInfo;
import com.project.voa.repository.UserInfoRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	private final UserInfoRepository userRepository;

	public CustomUserDetailsService(UserInfoRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String username) {
		return userRepository.findByUserEmail(username)
				.map(user -> createUser(username, user))
				.orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
	}

	private org.springframework.security.core.userdetails.User createUser(String username, UserInfo user) {
		List<GrantedAuthority> grantedAuthorities = Stream.of("ROLE_ADMIN", "ROLE_USER")
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		return new org.springframework.security.core.userdetails.User(user.getUserEmail(),
				user.getPassword(),
				grantedAuthorities);
	}
}
