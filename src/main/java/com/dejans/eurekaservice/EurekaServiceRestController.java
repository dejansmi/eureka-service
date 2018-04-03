package com.dejans.eurekaservice;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class EurekaServiceRestController {

	protected Logger logger = Logger.getLogger(EurekaServiceRestController.class.getName());

	@RequestMapping("/portset/")
	public String setport(HttpServletRequest req) throws UnsupportedEncodingException {
		HttpUtils hUtils = new HttpUtils(req.getRequestURL().toString(), req.getQueryString().toString());
		String paramName = hUtils.getParamName();
		String clientName = hUtils.getParamValue(paramName);
		paramName = hUtils.getParamName();
		String XZ = hUtils.getParamValue(paramName);
		logger.info(" root() invoked");
		return "Set port tekst " + clientName + " " + XZ;

	}


}