package com.bridgelabz.note.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.bridgelabz.note.dao.NoteDao;
import com.bridgelabz.note.dao.UserDaoImpl;
import com.bridgelabz.note.jms.JMSSendingMsg2Queue;
import com.bridgelabz.note.model.Collab;
import com.bridgelabz.note.model.Note;
import com.bridgelabz.note.utility.ElasticSearchUtility;

@Service
public class NoteService {

	@Autowired
	NoteDao noteDao;

	@Autowired
	private UserDaoImpl daoImpl;

	@Autowired
	private JMSSendingMsg2Queue jMSSendingMsg2Queue;

	public void insertNote(Note note) {
		System.out.println("Note service- insertNote()");
		int affectedRowId = noteDao.insertNote(note);
		// After inserting notes add asynchronously note to JMS Queue
		try {

			System.out.println(note.getNoteId());
			System.out.println(jMSSendingMsg2Queue);
			jMSSendingMsg2Queue.sendElasticData(note);
		} catch (JMSException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int updateNote(Note note) {
		System.out.println("Note service- updateNote()");
		int affectedRows = noteDao.updateNote(note);

		// After inserting notes add asynchronously the list of notes to JMS Queue
		if (affectedRows > 0) {
			try {
				jMSSendingMsg2Queue.sendElasticData(note);
			} catch (JMSException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Note not updated");
		}
		return affectedRows;
	}

	public Note getNoteById(int noteId) {
		System.out.println("Note service- getNoteById()");
		return noteDao.getNoteById(noteId);
	}

	public List<Note> listAllNotes(int userId) {
		System.out.println("Note service- listAllNotes()");
		return noteDao.listAllNotes(userId);
	}

	public int deleteNoteById(HashMap<String, Integer> map) {
		System.out.println("Note service- deleteNoteById()");
		int affectedRows = noteDao.deleteNoteById(map);
		return affectedRows;
	}

	public int tempDeleteNote(Note note) {
		System.out.println("Note Service - deleteNoteAndMoveToTrash()");
		int affectedRows = noteDao.tempDeleteNote(note);
		if (affectedRows > 0) {
			try {
				jMSSendingMsg2Queue.sendElasticData(note);
			} catch (JMSException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return affectedRows;
	}

	public int archiveNote(Note note) {
		System.out.println("Note Service - archiveNote()");
		return noteDao.archiveNote(note);
	}

	public int pinNote(Note note) {
		System.out.println("Note Service - pinNote()");
		return noteDao.pinNote(note);
	}

	public int setReminder(Note note) {
		System.out.println("Note Service - setReminder()");
		return noteDao.setReminder(note);
	}

	public List<Note> searchNotes(String text, String userId) {
		// IndexElasticNotes.createIndexAndLoadData(noteList);
		// System.out.println("Loaded the data...");
		List<Note> notes = ElasticSearchUtility.searchNotesFromElastic(text, userId);
		return notes;
	}

	public List<String> addCollab(int noteId, int shared_from_id, List<String> shared_to_mailIds) {
		int affectedRows;
		Collab newCollab;
		System.out.println("collab()");
		Collab collab = null;
		List<Integer> listSharedToId = new ArrayList<Integer>();
		List<Collab> listCollabs = noteDao.getCollabsFromNote(noteId);
		List<String> messagesError = new ArrayList<String>();
		for (String mailId : shared_to_mailIds) {
			newCollab = new Collab();
			newCollab.setNoteId(noteId);
			newCollab.setShared_from_id(shared_from_id);
			int shared_to_id = daoImpl.getUserByEmailID(mailId).getUserId();
			if (listCollabs.isEmpty()) {
				System.out.println("Empty");
				newCollab.setShared_to_id(shared_to_id);
				listCollabs = new ArrayList<Collab>();
				affectedRows = noteDao.addCollab(newCollab);
				listCollabs.add(newCollab);
				continue;
			}
			int ownerId = listCollabs.get(0).getShared_from_id();
			if (shared_to_id == ownerId) {
				messagesError.add("Cannot add " + mailId + "!!Already a member");
				continue;
			}
			for (Collab iterCollab : listCollabs) {
				listSharedToId.add(iterCollab.getShared_to_id());
			}

			if (listSharedToId.contains(shared_to_id)) {
				messagesError.add("Cannot add " + mailId + "!!Already a member");
			} else {
				newCollab.setShared_to_id(shared_to_id);
				affectedRows = noteDao.addCollab(newCollab);
				listCollabs.add(newCollab);
			}
		}
		return messagesError;
	}

	public int unCollab(HashMap<String, Integer> map) {
		System.out.println("Note Service - unCollab()");
		return noteDao.unCollab(map);
	}
}
