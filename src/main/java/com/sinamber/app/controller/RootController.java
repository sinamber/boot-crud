package com.sinamber.app.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sinamber.app.persistence.UserMapper;

@Controller
public class RootController extends BaseController {
	//那啥，我就不写Service层了~~
	@Resource
	private UserMapper userMapper;

	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute(DATA_LIST, userMapper.list());
		model.addAttribute("users", userMapper.listAll());
		return "index";
	}
}
