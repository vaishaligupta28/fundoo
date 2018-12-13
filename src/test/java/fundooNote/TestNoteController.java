package fundooNote;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.bridgelabz.note.model.Note;
import com.bridgelabz.note.model.User;
import com.bridgelabz.note.utility.ElasticSearchUtility;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class TestNoteController {

	private static Logger logger = Logger.getLogger(TestNoteController.class);
	private static Note note1, note2, note3, note4, note5, note6, note7, note8, note9, note10, note11, note12;
	private static User user1, user2, user3, user4, user5;
	private String token = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJicmlkZ2VsYWJ6Iiwic3ViIjoiSXRzIGEgZ29vZCBkYXkiLCJuYW1lIjoiTWFoZWVzaCBDaGV0cnJpIiwiaWQiOjEsImlhdCI6MTQ2Njc5NjgyMiwiZXhwIjoxNTEwMjA4NDAzfQ.ojKahPEK9s1HnMgRW5RYveTJbHF-dSmu9uUBlgwGGok";

	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/fundooNote";
		logger.info("setup()");

		user1 = new User();
		user1.setUserId(1);

		user2 = new User();
		user2.setUserId(2);

		user3 = new User();
		user3.setUserId(3);

		user4 = new User();
		user4.setUserId(4);

		user5 = new User();
		user5.setUserId(5);

		note1 = new Note();
		note1.setNoteId(1);
		note1.setTitle("Mahesh first note");
		note1.setDesc("Hello i am user id 1, i am writing my first note");
		note1.setCreatedOn(new Date().toString());
		note1.setUser(user1);

		note2 = new Note();
		note2.setNoteId(2);
		note2.setTitle("Mahesh Second note");
		note2.setDesc("Hello i am user id 1, i am writing my second note");
		note2.setCreatedOn(new Date().toString());
		note2.setUser(user1);

		note3 = new Note();
		note3.setNoteId(3);
		note3.setTitle("Rajesh first note");
		note3.setDesc("Hello i am user id 2, i writing my first note");
		note3.setCreatedOn(new Date().toString());
		note3.setUser(user2);

		note4 = new Note();
		note4.setNoteId(4);
		note4.setTitle("Rajesh second note");
		note4.setDesc("Hello i am user id 2, i writing my first note but a different.");
		note4.setCreatedOn(new Date().toString());
		note4.setUser(user2);

		note5 = new Note();
		note5.setNoteId(5);
		note5.setTitle("Seema first note");
		note5.setDesc("Hello i am user id 3, i writing my first note but a different one.");
		note5.setUser(user3);

		// note created to test sent in trash
		note6 = new Note();
		note6.setNoteId(6);
		note6.setTitle("Seema second note");
		note6.setDesc("Hello i am user id 3, i writing my second note but a different one.");
		note6.setUser(user3);

		// note created to test archive
		note7 = new Note();
		note7.setNoteId(7);
		note7.setTitle("Seema second note");
		note7.setDesc("Hello i am user id 3, i writing my third note but a varied one.");
		note7.setUser(user3);

		// note created to test pinned
		note8 = new Note();
		note8.setNoteId(8);
		note8.setTitle("arjun first note");
		note8.setDesc("Hello i am user id 4, i writing my first note but a varied slate.");
		note8.setUser(user4);

		// note created to test reminder
		note9 = new Note();
		note9.setNoteId(9);
		note9.setTitle("arjun first note");
		note9.setDesc("Hello i am user id 4, i writing my first note but a different memory");
		note9.setUser(user4);

		// note created to test reminder
		note10 = new Note();
		note10.setNoteId(10);
		note10.setTitle("Vaishali first note");
		note10.setDesc("Hello i am user id 5, i writing my first note but a different memory");
		note10.setUser(user5);

		// note created to test reminder
		note11 = new Note();
		note11.setNoteId(11);
		note11.setTitle("Vaishali second note");
		note11.setDesc("Hello i am user id 5, i writing my second note but a different memory");
		note11.setUser(user5);

		// note created to test reminder
		note12 = new Note();
		note12.setNoteId(12);
		note12.setTitle("Vaishali third note");
		note12.setDesc("Hello i am user id 5, i writing my third note but a different memory");
		note12.setUser(user5);
	}

	@Test
	// @Ignore
	public void insertNoteWithoutAnyError() {
		// String token =
		// "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJicmlkZ2VsYWJ6Iiwic3ViIjoiSXRzIGEgZ29vZCBkYXkiLCJuYW1lIjoiVmFpc2hhbGkgR3VwdGEiLCJpZCI6NCwiaWF0IjoxNDY2Nzk2ODIyLCJleHAiOjE1MDc3MzY1MzF9.UhsKQd0AN1br5QR3_W9OTjFgfTLKtyWYVO91TSb558E";
		List<Note> noteList = new ArrayList<Note>();
		noteList.add(note1);
		noteList.add(note2);
		noteList.add(note3);
		noteList.add(note4);
		noteList.add(note5);
		noteList.add(note6);
		noteList.add(note7);
		noteList.add(note8);
		noteList.add(note9);
		noteList.add(note10);
		noteList.add(note11);
		noteList.add(note12);

		logger.info("insertNotWithoutAnyError()");
		for (Note note : noteList) {
			Response resp = given().header("token", token).contentType(ContentType.JSON).body(note).when()
					.get("auth/insertNote");
			System.out.println(resp.asString());
		}
	}

	@Test
	@Ignore
	public void insertNoteWithTokenNotSetError() {
		String token = "";
		logger.info("insertNoteWithTokenNotSetError()");
		Response resp = given().header("token", token).contentType(ContentType.JSON).body(note2).when()
				.get("auth/insertNote");
		System.out.println(resp.asString());
	}

	@Test
	@Ignore
	public void updateNoteWithoutAnyError() {
		logger.info("updateNoteWithoutAnyError()");
		given().header("token", token).contentType(ContentType.JSON).body(note1).when().get("auth/updateNote").then()
				.statusCode(200);
	}

	@Test
	@Ignore
	public void getNodeByIdWithoutAnyError() {
		logger.info("getNodeByIdWithoutAnyError()");
		given().header("token", token).contentType(ContentType.JSON).body(1).when().get("auth/getNoteById").then()
				.statusCode(200);
	}

	@Test
	@Ignore
	public void getListAllNotesWithoutAnyError() {
		logger.info("getListAllNotesWithoutAnyError()");
		Response response = given().header("token", token).contentType(ContentType.JSON).body(user1.getUserId()).when()
				.get("auth/listAllNotes");
		System.out.println(response.asString());
		List<Note> noteList = response.then().extract().path("noteList");
		System.out.println(noteList);
	}

	@Test
	@Ignore
	public void deleteNoteWithoutAnyError() {
		logger.info("deleteNoteWithoutAnyError()");
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("userId", 1);
		map.put("noteId", 5);
		given().header("token", token).contentType(ContentType.JSON).params(map).when().get("auth/deleteNoteById")
				.then().statusCode(200);
	}

	@Test
	@Ignore
	public void tempDeleteAndSendToTrashTest() {
		logger.info("tempDeleteAndSendToTrashTest()");
		given().header("token", token).contentType(ContentType.JSON).body(note9).when().get("auth/tempDeleteNote")
				.then().statusCode(200);
	}

	@Test
	@Ignore
	public void archiveNoteTest() {
		logger.info("archiveNoteTest()");
		given().header("token", token).contentType(ContentType.JSON).body(note7).when().get("auth/archiveNote").then()
				.statusCode(200);
	}


	@Test
	@Ignore
	public void pinNoteTest() {
		logger.info("pinNoteTest()");
		given().header("token", token).contentType(ContentType.JSON).body(note8).when().get("auth/pinNote").then()
				.statusCode(200);
	}

	@Test
	@Ignore
	public void setReminderTest() {
		logger.info("setReminderTest()");
		String setTime = "2017-10-12 13:30:00";
		Date reminder = null;
		try {
			reminder = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(setTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		note9.setReminderTime(reminder);
		given().header("token", token).contentType(ContentType.JSON).body(note9).when().get("auth/setReminder").then()
				.statusCode(200);
	}

	@Test
	@Ignore
	public void searchTest() {
		logger.info("searchTest");
		Response response = given().contentType(ContentType.JSON).pathParam("text", "sec").pathParam("userId", 1).when()
				.get("/search/{text}/{userId}");
		System.out.println("\n\n*************After searching*************\n\n");
		List<Note> noteList = response.then().extract().path("notes");

		System.out.println(noteList.getClass());
		for (int i = 0; i < noteList.size(); i++) {
			System.out.println(noteList.get(i) + "\n");
		}
	}

	@Test
	@Ignore
	public void testCollaborate() {
		logger.info("testCollaborate()");
		List<String> shared_to_mailIds = new ArrayList<String>();
		// shared_to_mailIds.add("arju12@gmail.com");
		shared_to_mailIds.add("vaishaligupta028@gmail.com");
		// shared_to_mailIds.add("b130031cs@nitsikkim.ac.in");
		// shared_to_mailIds.add("g28vaishali@gmail.com");
		shared_to_mailIds.add("Maheshsh12@gmail.com");
		Response response = given().contentType(ContentType.JSON).pathParam("noteId", note5.getNoteId())
				.pathParam("shared_from_id", user5.getUserId()).body(shared_to_mailIds).when()
				.get("/collaborate/{noteId}/{shared_from_id}");
	}

	@Test
	@Ignore
	public void testUnCollab() {
		logger.info("restoreFromTrashTest");
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("userId", 5);
		map.put("noteId", 5);
		given().header("token", token).contentType(ContentType.JSON).body(map).when().get("auth/uncollab").then()
				.statusCode(200);

	}
}
