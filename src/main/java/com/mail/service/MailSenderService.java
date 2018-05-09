package com.mail.service;


import com.mail.property.MailProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;

@Service
public class MailSenderService {

    private static final Logger logger = LoggerFactory.getLogger(MailSenderService.class);

    @Autowired
    private MailProperty mailProperty;

    public void downloadEmailAttachments(String userName, String password) throws MessagingException {

        Session session = Session.getDefaultInstance(mailProperties(mailProperty.getHost(),mailProperty.getPort()));

            Store store = session.getStore(mailProperty.getProtocol());
            store.connect(userName, password);
            Folder folderInbox = store.getFolder(mailProperty.getFolderName());
            folderInbox.open(Folder.READ_ONLY);
            getFolderMessages(folderInbox);

            folderInbox.close(false);
            store.close();
    }

    private void getAttachments(Message message) throws Exception {
        String attachFiles = "";
        Object content = message.getContentType();

        if (((String) content).contains("multipart")) {
            Multipart multiPart = (Multipart) message.getContent();
            for (int partCount = 0; partCount < multiPart.getCount(); partCount++) {
                MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                    String fileName = part.getFileName();
                    attachFiles += fileName + ", ";
                    part.saveFile(mailProperty.getSaveDirectory() + File.separator + fileName);
                }
            }
        }
    }

    private void getFolderMessages(Folder folderInboxMessage) {
        try {
            List<Message> countMessage = Arrays.stream(folderInboxMessage.getMessages()).collect(Collectors.toList());

            for (int i = 0; i < countMessage.size(); i++) {
                Message message = countMessage.get(i);
                getAttachments(message);
            }
        } catch (NoSuchProviderException ex) {
            logger.info("No provider for pop3",ex.getMessage());
            ex.printStackTrace();
        } catch (MessagingException ex) {
            logger.info("Could not connect to the message store",ex);
            ex.printStackTrace();
        }  catch (Exception e) {
            e.printStackTrace();
            logger.info("General Exception",e);
        }

    }

    public Properties mailProperties(String host,String port){
        Properties properties = new Properties();
        properties.put("mail.pop3.host",host);
        properties.put("mail.pop3.port", port);
        properties.setProperty("mail.pop3.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.pop3.socketFactory.fallback", "false");
        properties.setProperty("mail.pop3.socketFactory.port",String.valueOf(port));
        return properties;
    }
}