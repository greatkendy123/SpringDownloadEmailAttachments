package com.mail.controller;

import java.util.Map;

import com.mail.service.MailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mail.property.MailProperty;

import javax.mail.MessagingException;

@Controller
public class DemoController {

	private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

	@Autowired
	private MailProperty mailProperty;

	@Autowired
	private MailSenderService mailSenderService;

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) throws MessagingException {

			mailSenderService.downloadEmailAttachments(mailProperty.getUsername(),mailProperty.getPassword());

			model.put("Success","indirilme başarılı");

		return "welcome";
	}
}