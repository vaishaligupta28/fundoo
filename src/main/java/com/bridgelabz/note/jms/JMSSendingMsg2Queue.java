package com.bridgelabz.note.jms;

import java.util.List;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bridgelabz.note.model.Note;
import com.bridgelabz.note.utility.GsonUtil;

public class JMSSendingMsg2Queue {

	@Autowired
	ActiveMQConnectionFactory jmsConnectionFactory;

	private Logger logger = Logger.getLogger(JMSSendingMsg2Queue.class);

	public void sendElasticData(Note note) throws JMSException, InterruptedException {

		Connection conn = null;
		Queue queue;
		Session session = null;
		try {
			logger.info("submit()");
			conn = jmsConnectionFactory.createConnection();
			conn.start();
			System.out.println("connection started");
			String jsonData = GsonUtil.toJson(note);
			// first param means session is non transactional
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			if (note.isSentInTrash()) {
				System.out.println("Sending to :  elastic-delete-queue");
				queue = session.createQueue("elastic-delete-queue");
			} else {
				System.out.println("Sending to :  elastic-insert-update-queue");
				queue = session.createQueue("elastic-insert-update-queue");
			}
			/*
			 * textMessage.setStringProperty("toEmail", toEmail);
			 * textMessage.setStringProperty("fromEmail", fromEmail);
			 * textMessage.setStringProperty("subject", subject);
			 * textMessage.setStringProperty("text", text);
			 */
			MessageProducer producer = session.createProducer(queue);
			TextMessage textMessage = session.createTextMessage(jsonData);
			producer.send(textMessage);
			session.close();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
}
