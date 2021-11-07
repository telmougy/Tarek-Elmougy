package BestBuyAPIs.BestBuyAPIs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

public class TS_Common {
	//check get/version works correctly with expected version
	@Test(enabled = true,priority =1)
	public void TC_GET_version_request() {
		given().
		header("Content-Type", "application/json")
		.get("http://localhost:3030/version").
		then().
		statusCode(200).body("version", equalTo("1.1.0"));
	}
	//check that post request is not supported for /version end point 
	@Test(enabled = true,priority =2)
	void TC_Post_version_not_exist() {

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		when().
		post("http://localhost:3030/version").
		then().
		statusCode(404);

	}
	//check get/healthcheck works correctly with expected version

	@Test(enabled = true,priority =12)
	public void TC_GET_healthCheck_request() {
		given().
		header("Content-Type", "application/json")
		.get("http://localhost:3030/healthcheck").
		then().
		statusCode(200).body("documents.products", equalTo(51957)).
		body("documents.stores", equalTo(1561)).
		body("documents.categories", equalTo(4307));
	}
	//check that post request is not supported for /healthcheck end point 

	@Test(enabled = true,priority =9)
	void TC_Post_healthCheck__not_exist() {

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		when().
		post("http://localhost:3030/healthcheck").
		then().
		statusCode(404);

	}


}

