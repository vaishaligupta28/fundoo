package com.bridgelabz.note.jms;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.bridgelabz.note.model.Note;
import com.bridgelabz.note.utility.ElasticSearchUtility;
import com.bridgelabz.note.utility.GsonUtil;

public class JMSReceivingMsgFromQueue {

	@Autowired
	private ActiveMQConnectionFactory jmsConnectionFactory;

	private Connection conn = null;
	private Session session = null;

	private Logger logger = Logger.getLogger(JMSReceivingMsgFromQueue.class);

	public void init() {
		System.out.println("init()");
		try {
			Connection conn = null;
			Session session = null;
			conn = jmsConnectionFactory.createConnection();
			conn.start();
			System.out.println("Connection : " + conn);

			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Queue queue = session.createQueue("elastic-insert-update-queue");

			MessageConsumer consumer = session.createConsumer(queue);
			receiveElasticDataAndPerformIndexing(consumer);
		} catch (JMSException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void receiveElasticDataAndPerformIndexing(MessageConsumer consumer) throws JMSException, InterruptedException {

		System.out.println("receiveElasticDataAndPerformIndexing()");

		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				System.out.println("Listening...");
				TextMessage textMessage = (TextMessage) message;
				try {
					String jsonData = textMessage.getText();
					Note note = GsonUtil.fromJson(jsonData);
					System.out.println(note);

					// Indexing the notes
					ElasticSearchUtility.createIndexAndLoadData(note);
					System.out.println("Loaded the data");
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		System.out.println(consumer.getMessageListener());
	}

	public void destroy() {
		System.out.println("destroy()");
		if (session != null) {
			try {
				session.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
