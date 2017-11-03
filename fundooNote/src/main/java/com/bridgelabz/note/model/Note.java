package com.bridgelabz.note.model;

import java.util.Date;
public class Note {
	
	private int noteId;
	private User user;// for foreign key references
	private String createdOn;
	private String title;
	private String desc;
	private boolean archived;
	private boolean pinned;
	private boolean sentInTrash;
	private Date reminderTime;
	

	public int getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	public boolean isPinned() {
		return pinned;
	}

	public void setPinned(boolean pinned) {
		this.pinned = pinned;
	}

	public boolean isSentInTrash() {
		return sentInTrash;
	}

	public void setSentInTrash(boolean sentInTrash) {
		this.sentInTrash = sentInTrash;
	}

	public Date getReminderTime() {
		return reminderTime;
	}

	public void setReminderTime(Date reminderTime) {
		this.reminderTime = reminderTime;
	}

	@Override
	public String toString() {
		return "Note [noteId=" + noteId + ", user=" + user + ", createdOn=" + createdOn + ", title=" + title + ", desc="
				+ desc + ", archived=" + archived + ", pinned=" + pinned + ", sentInTrash=" + sentInTrash
				+ ", reminderTime=" + reminderTime + "]";
	}
}
