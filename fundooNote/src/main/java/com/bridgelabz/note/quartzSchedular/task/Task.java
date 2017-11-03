package com.bridgelabz.note.quartzSchedular.task;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

//import com.bridgelabz.note.utility.ReminderNotifications;

@Component
public class Task {

	private Logger logger = Logger.getLogger(Task.class);


	public void executeTask() {
		System.out.println("executeTask()");
		//ReminderNotifications.getNotify();
	}
}
