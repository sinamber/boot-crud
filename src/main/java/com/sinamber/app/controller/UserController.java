package com.sinamber.app.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinamber.app.model.User;
import com.sinamber.app.persistence.UserMapper;

/**
 * User CRUD Example
 * @Description:
 * @Author:Sine Chen
 * @Date:Apr 14, 2015 10:24:37 AM
 * @Copyright: All Rights Reserved. Copyright(c) 2015
 */
@Controller
public class UserController extends BaseController {

	@Resource
	private UserMapper userMapper;

	@RequestMapping("/user/list")
	public String index(Model model) {
		model.addAttribute(DATA_LIST, userMapper.list());
		return "user/list";
	}

	@RequestMapping(value = "/user/add", method = RequestMethod.GET)
	public String add() {
		return "user/add";
	}

	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(Model model, User user) {
		try {
			int suc = userMapper.add(user);
			if (suc == 1) {
				return ajaxSuccess();
			} else {
				return ajaxFail();
			}
		} catch (Exception e) {
			LOG.error("[/user/add ERROR]user=" + user, e);
			return ajaxError();
		}
	}

	@RequestMapping(value = "/user/update/{id}", method = RequestMethod.GET)
	public String update(Model model, @PathVariable("id") long id) {
		try {
			model.addAttribute(VO, userMapper.get(id));
		} catch (Exception e) {
			LOG.error("[/user/update/{id} ERROR] id=" + id, e);
		}
		return "user/update";
	}

	@RequestMapping(value = "/user/put", method = RequestMethod.POST)
	@ResponseBody
	public String update(Model model, User user) {
		try {
			int suc = userMapper.update(user);
			if (suc == 1) {
				return ajaxSuccess();
			} else {
				return ajaxFail();
			}
		} catch (Exception e) {
			LOG.error("[/user/update ERROR]user=" + user, e);
			return ajaxError();
		}
	}

	@RequestMapping(value = "/user/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public String delete(Model model, @PathVariable("id") long id) {
		try {
			userMapper.delete(id);
		} catch (Exception e) {
			LOG.error("[/user/delete/{id} ERROR] id=" + id, e);
			return ajaxError();
		}
		return ajaxSuccess();
	}

	@RequestMapping(value = "/user/check_name", method = RequestMethod.POST)
	@ResponseBody
	public String check(Model model, String name, String oldValue) {
		if ("sinamber".equals(name)) {
			return VALIDATE_FAIL;
		}
		return VALIDATE_SUCCESS;
	}
}
