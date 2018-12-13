package com.bridgelabz.note.json;

import java.util.List;

import com.bridgelabz.note.model.Note;

public class SearchErrorResponse extends Response{

	List<Note> notes;
	String msg;

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
}
