package com.luv2tech.service;

import com.luv2tech.response.NotificationEmail;

public interface MailService {

    public void sendMail(NotificationEmail notificationEmail);
}
