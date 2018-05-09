package com.mail.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "app")
@Component
@Getter
@Setter
@ToString
public class MailProperty {

	private static final Logger logger = LoggerFactory.getLogger(MailProperty.class);

	private String saveDirectory;

	private String folderName;

	private String host;

	private String port;

	private String username;

	private String password;

	private String protocol;

}
