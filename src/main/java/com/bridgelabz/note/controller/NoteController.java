package com.bridgelabz.note.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bridgelabz.note.json.NoteResponse;
import com.bridgelabz.note.json.Response;
import com.bridgelabz.note.json.SearchErrorResponse;
import com.bridgelabz.note.model.Note;
import com.bridgelabz.note.model.User;
import com.bridgelabz.note.services.NoteService;

@Controller
public class NoteController {

	@Autowired
	private NoteService noteService;

	@RequestMapping(value = "/auth/insertNote", consumes = "application/json")
	public @ResponseBody ResponseEntity<Response> insertNote(@RequestBody Note note, BindingResult bindRes) {
		Response resp;
		System.out.println("NoteController - insertNote()");
		noteService.insertNote(note);
		System.out.println("Note Inserted");
		resp = new NoteResponse();
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/auth/updateNote", consumes = "application/json")
	public @ResponseBody ResponseEntity<Response> updateNote(@RequestBody Note note) {
		System.out.println("NoteController - updateNote()");
		if (noteService.updateNote(note) > 0) {
			return new ResponseEntity<Response>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Response>(HttpStatus.CONFLICT);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/auth/getNoteById", consumes = "application/json")
	public ResponseEntity<Response> getNoteById(@RequestBody int noteId) {
		System.out.println("NoteController - getNoteById()");
		Note note = noteService.getNoteById(noteId);
		if (note != null) {
			System.out.println("Note is:  \n" + note.toString());
			return new ResponseEntity<Response>(HttpStatus.OK);
		} else {
			System.out.println("No such note exists");
			return new ResponseEntity<Response>(HttpStatus.CONFLICT);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/auth/listAllNotes", consumes = "application/json")
	public ResponseEntity<Response> listAllNotes(@RequestBody int userId) {
		System.out.println("NoteController - listAllNotes()");
		List<Note> noteList = noteService.listAllNotes(userId);
		System.out.println(noteList);
		System.out.println("NoteId    Title                Desc                            CreatedOn");
		for (Note note : noteList) {
			System.out.println(note.getNoteId() + "      " + note.getTitle() + "    " + note.getDesc() + "   "
					+ note.getCreatedOn());
		}
		SearchErrorResponse resp = new SearchErrorResponse();
		resp.setNotes(noteList);
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/auth/deleteNoteById", consumes = "application/json")
	public ResponseEntity<Response> deleteNoteById(@RequestParam HashMap<String, Integer> map) {
		System.out.println("NoteController - deleteNoteById()");
		int affectedRows = noteService.deleteNoteById(map);
		if (affectedRows > 0) {
			System.out.println("Note deleted");
			return new ResponseEntity<Response>(HttpStatus.OK);
		} else {
			System.out.println("Note not deleted");
			return new ResponseEntity<Response>(HttpStatus.CONFLICT);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/auth/tempDeleteNote", consumes = "application/json")
	public ResponseEntity<Response> tempDeleteNote(@RequestBody Note note) {
		System.out.println("Note Controller - tempDeleteNote()");
		int affectedRows = noteService.tempDeleteNote(note);
		System.out.println(affectedRows);
		if (affectedRows > 0) {
			System.out.println("Note Send To Trash");
			return new ResponseEntity<Response>(HttpStatus.OK);
		} else {
			System.out.println("Note not send to trash");
			return new ResponseEntity<Response>(HttpStatus.CONFLICT);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/auth/archiveNote", consumes = "application/json")
	public ResponseEntity<Response> archiveNote(@RequestBody Note note) {
		System.out.println("Note Controller - archiveNote");
		int affectedRows = noteService.archiveNote(note);
		System.out.println(affectedRows);
		if (affectedRows > 0) {
			System.out.println("Note Archived");
			return new ResponseEntity<Response>(HttpStatus.OK);
		} else {
			System.out.println("Note not archived");
			return new ResponseEntity<Response>(HttpStatus.CONFLICT);
		}
	}


	@ResponseBody
	@RequestMapping(value = "/auth/pinNote", consumes = "application/json")
	public ResponseEntity<Response> pinNote(@RequestBody Note note) {
		System.out.println("Note Controller - pinNote()");
		int affectedRows = noteService.pinNote(note);
		System.out.println(affectedRows);
		if (affectedRows > 0) {
			System.out.println("Note Pinned");
			return new ResponseEntity<Response>(HttpStatus.OK);
		} else {
			System.out.println("Note not pin");
			return new ResponseEntity<Response>(HttpStatus.CONFLICT);
		}
	}


	@ResponseBody
	@RequestMapping(value = "/auth/setReminder", consumes = "application/json")
	public ResponseEntity<Response> setReminder(@RequestBody Note note) {
		System.out.println("Note Controller - setReminderForNote()");
		int affectedRows = noteService.setReminder(note);
		System.out.println(affectedRows);
		if (affectedRows > 0) {
			System.out.println("Reminder set");
			return new ResponseEntity<Response>(HttpStatus.OK);
		} else {
			System.out.println("Reminder not set");
			return new ResponseEntity<Response>(HttpStatus.CONFLICT);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/search/{text}/{userId}", consumes = "application/json")
	public ResponseEntity<Response> search(@PathVariable String text, @PathVariable String userId) {
		List<Note> notes = noteService.searchNotes(text, userId);
		SearchErrorResponse response = new SearchErrorResponse();
		response.setNotes(notes);
		response.setMsg("Notes searched by title");
		response.setStatus(200);
		return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
	}

	@ResponseBody
	@RequestMapping(value = "/collaborate/{noteId}/{shared_from_id}", consumes = "application/json")
	public ResponseEntity<Response> addCollab(@PathVariable int noteId, @PathVariable int shared_from_id,
			@RequestBody List<String> shared_to_mailIds) {
		Response response = new Response();
		System.out.println(shared_to_mailIds + "---");
		List<String> messagesError = noteService.addCollab(noteId, shared_from_id, shared_to_mailIds);
		if (messagesError.isEmpty()) {
			response.setMsg("Success");
			response.setStatus(200);
			System.out.println("Successfully added");
		} else {
			response.setMsg("Error");
			response.setStatus(404);
			System.out.println(messagesError.toString());
		}
		return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
	}

	@ResponseBody
	@RequestMapping(value = "/auth/uncollab", consumes = "application/json")
	public ResponseEntity<Response> unCollabFromNote(HashMap<String, Integer> map) {
		Response response = new Response();
		int affectedRows = noteService.unCollab(map);
		if (affectedRows > 0) {
			response.setMsg("Success");
			response.setStatus(200);
			return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
		} else {
			response.setMsg("Error");
			response.setStatus(404);
			return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
		}
	}
}
