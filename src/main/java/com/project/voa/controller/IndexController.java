package com.project.voa.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class IndexController {
	@GetMapping("/")
	public RedirectView index() {
		return new RedirectView("/main");
	}

	@GetMapping("/main")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ModelAndView main() {
		return new ModelAndView("main");
	}
}