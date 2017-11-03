package com.bridgelabz.note.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.bridgelabz.note.model.Collab;
import com.bridgelabz.note.model.Note;
import com.bridgelabz.note.model.User;
import com.bridgelabz.note.utility.MyBatisUtil;

@Repository
public class NoteDaoImpl implements NoteDao {

	public int insertNote(Note note) {
		System.out.println("NoteDaoImpl - insertNote()");
		int affectedRowId;
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			NoteDao noteDao = sqlSession.getMapper(NoteDao.class);
			affectedRowId = noteDao.insertNote(note);
			System.out.println("affectedRowId  " + affectedRowId);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
		return affectedRowId;
	}

	public int updateNote(Note note) {
		System.out.println("NoteDaoImpl - updateNote()");
		int affectedRows;
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			NoteDao noteDao = sqlSession.getMapper(NoteDao.class);
			System.out.println("--------");
			System.out.println(note);
			affectedRows = noteDao.updateNote(note);
			System.out.println(affectedRows);
			System.out.println("--------");
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
		return affectedRows;
	}

	public Note getNoteById(int noteId) {
		System.out.println("NoteDaoImpl - getNoteById()");
		Note note;
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			NoteDao noteDao = sqlSession.getMapper(NoteDao.class);
			note = noteDao.getNoteById(noteId);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
		return note;
	}

	public List<Note> listAllNotes(int userId) {
		System.out.println("NoteDaoImpl - listAllNotes()");
		System.out.println("userId" + userId);
		List<Note> noteList;
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			NoteDao noteDao = sqlSession.getMapper(NoteDao.class);
			noteList = noteDao.listAllNotes(userId);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
		return noteList;
	}

	public int deleteNoteById(HashMap<String, Integer> map) {
		System.out.println("NoteDaoImpl - deleteNoteById()");
		int affectedRows;
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			NoteDao noteDao = sqlSession.getMapper(NoteDao.class);
			affectedRows = noteDao.deleteNoteById(map);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
		return affectedRows;
	}

	public int tempDeleteNote(Note note) {
		System.out.println("NoteDaoImpl - tempDeleteNote()");
		int affectedRows;
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			NoteDao noteDao = sqlSession.getMapper(NoteDao.class);
			affectedRows = noteDao.tempDeleteNote(note);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
		return affectedRows;
	}


	public int archiveNote(Note note) {
		System.out.println("NoteDaoImpl - archiveNote()");
		int affectedRows;
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			NoteDao noteDao = sqlSession.getMapper(NoteDao.class);
			affectedRows = noteDao.archiveNote(note);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
		return affectedRows;
	}


	public int pinNote(Note note) {
		System.out.println("NoteDaoImpl - pinNote()");
		int affectedRows;
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			NoteDao noteDao = sqlSession.getMapper(NoteDao.class);
			affectedRows = noteDao.pinNote(note);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
		return affectedRows;
	}

	public int setReminder(Note note) {
		System.out.println("NoteDaoImpl - setReminder()");
		int affectedRows;
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			NoteDao noteDao = sqlSession.getMapper(NoteDao.class);
			affectedRows = noteDao.setReminder(note);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
		return affectedRows;
	}
	/*
	 * @Override public List<Note> getNotifications() { // TODO Auto-generated
	 * method stub SqlSession sqlSession =
	 * MyBatisUtil.getSqlSessionFactory().openSession(); try { NoteDao noteDao =
	 * sqlSession.getMapper(NoteDao.class); List<Note> noteList =
	 * noteDao.getNotifications(); } finally { sqlSession.close(); } return null; }
	 */

	/*
	 * 
	 * User user: a user who is now logged in, shared_to_mailId: toemailId person
	 * Note note: note to be shared
	 * 
	 */
	@Override
	public int addCollab(Collab newCollab) {
		System.out.println("NoteDaoImpl - addCollab");
		int affectedRows;
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			NoteDao noteDao = sqlSession.getMapper(NoteDao.class);
			affectedRows = noteDao.addCollab(newCollab);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
		return affectedRows;
	}

	@Override
	public List<Collab> getCollabsFromNote(int noteId) {
		System.out.println("NoteDaoImpl - getCollabsFromNote");
		List<Collab> listCollabs;
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		System.out.println("--------");
		try {
			NoteDao noteDao = sqlSession.getMapper(NoteDao.class);
			listCollabs = noteDao.getCollabsFromNote(noteId);
		} finally {
			sqlSession.close();
		}
		return listCollabs;
	}

	@Override
	public int unCollab(HashMap<String, Integer> map) {
		System.out.println("NoteDaoImpl - unCollab");
		int affectedRows;
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			NoteDao noteDao = sqlSession.getMapper(NoteDao.class);
			affectedRows = noteDao.unCollab(map);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
		return affectedRows;
	}
}
