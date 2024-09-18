package com.project.voa.handler;

import com.project.voa.domain.UserInfo;
import com.project.voa.repository.UserInfoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class UserInfoArgumentResolver implements HandlerMethodArgumentResolver {
	private final UserInfoRepository userInfoRepository;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return UserInfo.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			return userInfoRepository.findByUserEmail(authentication.getName())
					.map(userInfo -> UserInfo.builder()
							.id(userInfo.getId())
							.userName(userInfo.getUserName())
							.userEmail(userInfo.getUserEmail())
							.profile(userInfo.getProfile())
							.build())
					.orElseThrow(EntityNotFoundException::new);
		} else {
			return new UserInfo();
		}
	}
}
