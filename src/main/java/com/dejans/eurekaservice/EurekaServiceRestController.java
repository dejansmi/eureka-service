package com.dejans.eurekaservice;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

@RestController
class EurekaServiceRestController {

	protected Logger logger = Logger.getLogger(EurekaServiceRestController.class.getName());

	@RequestMapping("/portset/")
	public String setport(HttpServletRequest req) throws UnsupportedEncodingException {
		HttpUtils hUtils = new HttpUtils(req.getRequestURL().toString(), req.getQueryString().toString());
		String clientName = hUtils.getParamValue("clientName");
		int pid = Integer.parseInt(hUtils.getParamValue("pid"));
		int port = PortManagment.setPortForClient(clientName, pid);
		
		JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
		jsonBuilder.add ("port",port);
		jsonBuilder.add ("clientName", clientName);
		jsonBuilder.add ("error", "");

		String answer = jsonBuilder.build().toString();

		logger.info(" root() invoked");
		return answer;

	}


}