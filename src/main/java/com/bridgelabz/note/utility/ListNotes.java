package com.bridgelabz.note.utility;

import java.util.List;

import com.bridgelabz.note.model.Note;

public class ListNotes {
	
	private static List<Note> noteListToBeInserted;

	public static List<Note> getNoteListToBeInserted() {
		return noteListToBeInserted;
	}

	public static void  setNoteListToBeInserted(List<Note> noteListToBeInserted) {
		ListNotes.noteListToBeInserted = noteListToBeInserted;
	}
}
