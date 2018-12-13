package com.bridgelabz.note.utility;

import com.bridgelabz.note.model.Note;
import com.google.gson.Gson;

public class GsonUtil {
	
	public static Note fromJson(String jsonData){
		 Note note = new Gson().fromJson(jsonData, Note.class);
		 return note;
	}
	
	public static String toJson(Note note) {
		String jsonData = new Gson().toJson(note); 
		 return jsonData;
	}
}
