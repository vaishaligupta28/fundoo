package com.bridgelabz.note.quartzSchedular.job;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.bridgelabz.note.quartzSchedular.task.Task;


public class Job extends QuartzJobBean {

	private Task task;
	private Logger logger = Logger.getLogger(Job.class);

	//private JMSSendingMsg2Queue jMSSendingMsg2Queue;

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("executeInternal()");
		task.executeTask();
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
}
