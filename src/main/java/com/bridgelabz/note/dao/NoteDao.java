package com.bridgelabz.note.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.bridgelabz.note.model.Collab;
import com.bridgelabz.note.model.Note;;

@Component
public interface NoteDao {
	int insertNote(Note note);
	int updateNote(Note note);
	Note getNoteById(int noteId);
	List<Note> listAllNotes(int userId);
	int deleteNoteById(HashMap<String, Integer> map);
	int tempDeleteNote(Note note);
	int archiveNote(Note note);
	int pinNote(Note note);
	int setReminder(Note note);
//	List<Note> getNotifications();
	int addCollab(Collab newCollab);
	List<Collab> getCollabsFromNote(int noteId);
	int unCollab(HashMap<String, Integer> map);
}
