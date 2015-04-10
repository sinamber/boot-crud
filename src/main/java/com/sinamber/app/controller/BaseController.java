package com.sinamber.app.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sinamber.app.model.Constants;
import com.sinamber.app.util.RespMsg;

/**
 * @Description:控制器基类
 * @Author:XingXiao Chen
 * @Date:Sep 24, 2014
 * @Copyright: All Rights Reserved. Copyright(c) 2014
 */
public class BaseController {
	protected static Logger LOG = Logger.getLogger(BaseController.class);

	public static final String PG = "pg";
	public static final String VO = "vo";
	public static final String DATA = "data";
	public static final String MSG = "msg";
	public static final String DATA_LIST = "data_list";
	public static final String JSON_DATA = "json_data";

	public static final String VALIDATE_SUCCESS = "success";
	public static final String VALIDATE_FAIL = "fail";

	public static final String STATUS = "status";
	public static final int DEFAULT_PAGE_SIZE = 10;

	/**
	 * AJAX请求成功
	 * @param msg
	 * @return
	 */
	public String ajaxSuccess() {
		return "{\"status\": " + Constants.STATUS_SUCCESS + "}";
	}

	/**
	 * AJAX请求成功
	 * @param data -返回值对象
	 * @return 
	 */
	public String ajaxSuccess(Object data) {
		RespMsg respMsg = RespMsg.getInstance();
		respMsg.setStatus(Constants.STATUS_SUCCESS);
		respMsg.setMsg("处理成功");
		respMsg.setData(data);
		return JSON.toJSONString(respMsg, SerializerFeature.DisableCircularReferenceDetect);
	}

	/**
	 * 直接返回操作结果
	 * @param respMsg
	 * @return
	 */
	public String ajaxRespMsg(RespMsg respMsg) {
		return JSON.toJSONString(respMsg);
	}

	/**
	 * AJAX请求失败
	 * @return
	 */
	public String ajaxFail() {
		return "{\"status\": " + Constants.STATUS_FAIL + "}";
	}

	/**
	 * AJAX请求失败
	 * @param msg
	 * @return
	 */
	public String ajaxFail(String msg) {
		RespMsg respMsg = RespMsg.getInstance();
		respMsg.setStatus(Constants.STATUS_FAIL);
		respMsg.setMsg(msg);
		return JSON.toJSONString(respMsg);
	}

	/**
	 * AJAX请求失败，并给出指定状态
	 * @param status
	 * @return
	 */
	public String ajaxFail(int status) {
		return "{\"status\": " + status + "}";
	}

	/**
	 * AJAX请求服务器异常
	 * @return
	 */
	public String ajaxError() {
		return "{\"status\": " + Constants.STATUS_ERROR + "}";
	}

	/**
	 * 返回 500 视图
	 * @return
	 */
	public String errView() {
		return "common/error/500";
	}

	/**
	 * 返回 500 视图
	 * @return
	 */
	public String nfView() {
		return "common/error/404";
	}

	/**
	 * 下载Excel文件
	 * @param request
	 * @param response
	 * @param filePath
	 * @param fileName
	 * @throws Exception
	 */
	public void downloadExcel(HttpServletResponse response, String filePath, String fileName) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		try {
			File f = new File(filePath);
			response.setContentType("application/x-excel");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			response.setHeader("Content-Length", String.valueOf(f.length()));
			in = new BufferedInputStream(new FileInputStream(f));
			out = new BufferedOutputStream(response.getOutputStream());
			byte[] data = new byte[1024];
			int len = 0;
			while (-1 != (len = in.read(data, 0, data.length))) {
				out.write(data, 0, len);
			}
		} catch (Exception e) {
			LOG.error("Error Download excel");
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
			FileUtils.deleteQuietly(new File(filePath));
		}

	}

	/**
	 * 获取request对象
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	/**
	 * 处理服务器异常情况
	 * @param request
	 * @param ex
	 * @return
	 * @throws IOException 
	 */
	@ExceptionHandler
	public String exp(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
		String uri = request.getRequestURI();
		LOG.error("spring mvc ExceptionHandler[" + uri + "] >>" + uri, ex);
		request.setAttribute("uri", uri);
		request.setAttribute("ex", ex.fillInStackTrace());
		if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
			response.getWriter().print(ajaxError());
			return null;
		} else {
			return "common/error/500";
		}
	}
}
