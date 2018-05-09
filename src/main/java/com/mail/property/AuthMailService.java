package com.mail.property;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

@Service
public class AuthMailService extends Authenticator {

    @Autowired
    private MailProperty mailProperty;

    public AuthMailService() {

        super();
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        String username = mailProperty.getUsername();
        String password = mailProperty.getPassword();

        if ((username != null) && (username.length() > 0) && (password != null)
                && (password.length   () > 0)) {

            return new PasswordAuthentication(username, password);
        }
        return null;
    }


}
