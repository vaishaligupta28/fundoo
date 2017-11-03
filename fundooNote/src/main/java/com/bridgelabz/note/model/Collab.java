package com.bridgelabz.note.model;

public class Collab {

	private int collabId;
	private int shared_to_id;
	private int noteId;
	private int shared_from_id;

	public int getCollabId() {
		return collabId;
	}

	public void setCollabId(int collabId) {
		this.collabId = collabId;
	}

	public int getShared_to_id() {
		return shared_to_id;
	}

	public void setShared_to_id(int shared_to_id) {
		this.shared_to_id = shared_to_id;
	}

	public int getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	public int getShared_from_id() {
		return shared_from_id;
	}

	public void setShared_from_id(int shared_from_id) {
		this.shared_from_id = shared_from_id;
	}

	@Override
	public String toString() {
		return "Collab [collabId=" + collabId + ", shared_to_id=" + shared_to_id + ", noteId=" + noteId
				+ ", shared_from_id=" + shared_from_id + "]";
	}
}