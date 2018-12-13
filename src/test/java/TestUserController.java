import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.bridgelabz.note.model.User;
import org.apache.log4j.Logger;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class TestUserController {

	private static User user1, user2, user3, user4, user5;
	private static Logger logger = Logger.getLogger(TestUserController.class);

	@BeforeClass
	public static void setUp() {

		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/fundooNote";
		logger.info("setup()");

		// this user created for testing for successful entry into database
		user1 = new User();
		user1.setFullName("Rajesh Chetrri");
		user1.setEmail("b130031cs@nitsikkim.ac.in");
		user1.setMobileNumber("9876521484");
		user1.setPassword("Rajesh##123");

		// this user created for testing the validation part
		user2 = new User();
		user2.setFullName("Seema Biswa");
		user2.setEmail("vaishaligupta028@gmail.com");
		user2.setMobileNumber("9735147824");
		user2.setPassword("Seema##12pwd");

		// this user created for testing the user already exist part
		user3 = new User();
		user3.setFullName("Maheesh Chetrri");
		user3.setEmail("Maheshsh12@gmail.com");
		user3.setMobileNumber("9635786758");
		user3.setPassword("Mahemesh##123");

		// this user created for testing the internal server part
		user4 = new User();
		user4.setFullName("Arjun Shirodi");
		user4.setEmail("arju12@gmail.com");
		user4.setMobileNumber("9576521844");
		user4.setPassword("Arju##312");
		
		user5 = new User();
		user5.setFullName("Vaishali Gupta");
		user5.setEmail("g28vaishali@gmail.com");
		user5.setMobileNumber("9576174774");
		user5.setPassword("Vaish##123");
	}

	@Test
	@Ignore
	public void insertUserWithoutAnyError() {
		logger.info("insertUserWithoutAnyError()");
		given().contentType(ContentType.JSON).body(user5).when().post("register").then().statusCode(200);
		
		//System.out.println(resp.asString());
		//assertThat(resp.getStatusCode(), );
	}

	@Test
	@Ignore
	public void insertUserWithValidationError() {
		logger.info("insertUserWithValidationError()");
		Response resp = given().contentType(ContentType.JSON).body(user2).when().post("register");
		
		
		 System.out.println("-------------"+resp.asString()+"------");
	}

	@Test
	@Ignore
	public void insertUserWithUserAlreadyExistError() {
		logger.info("insertUserWithUserAlreadyExistError()");
		Response resp = given().contentType(ContentType.JSON).body(user3).when().post("register");
		
		System.out.println(resp.asString());
	/*	// assertThat(2, is(equalTo(2)));
		System.out.println(resp.asString());
		System.out.println("lkhjhswdhgfjkdhf");
		resp.then().body("status", Matchers.comparesEqualTo(-1)).body("msg", Matchers.anything(""));
		System.out.println(resp.statusCode());*/
	}

	@Test
	@Ignore
	public void insertUserWithInternalServerError() {
		logger.info("insertUserWithInternalServerError()");
		given().body(user4).when().post("register").then().statusCode(500);
	}


	@Test
	@Ignore
	public void setDisplayPicTest() {}
}
