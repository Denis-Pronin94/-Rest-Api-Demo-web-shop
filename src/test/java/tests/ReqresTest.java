package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ReqresTest {

    public static final String getSingleUser = "users?page=2";
    public static final String postRegister = "/register";
    public static final String deleteApi = "/users/2";
    public static final String putUpdateApi = "/users/2";

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in/api/";
    }

    @Test
    void  listUser() {

        given()
                .when()
                .get(getSingleUser)
                .then()
                .log().all()
                .statusCode(200)
                .body("page", is(2))
                .body("per_page", is(6))
                .body("total", is(12))
                .body("total_pages", is(2));
    }

    @Test
    void  registerUserTest() {
        String body = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";

        given()
                .body(body)
                .contentType(JSON)
                .when()
                .post(postRegister)
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void  missingRegisterUserTest() {
        String body = "{ \"email\": \"sydney@fife\" }";

        given()
                .body(body)
                .contentType(JSON)
                .when()
                .post(postRegister)
                .then()
                .log().all()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void  deleteUserTest() {

        given()
                .when()
                .delete(deleteApi)
                .then()
                .log().all()
                .statusCode(204);
    }

    @Test
    void  createUserTest() {
        String body = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

        given()
                .body(body)
                .contentType(JSON)
                .when()
                .post(putUpdateApi)
                .then()
                .log().all()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }
}
