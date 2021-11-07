package BestBuyAPIs.BestBuyAPIs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class TS_Services {


	//GET test Cases
	//check status code
	@Test(enabled = true,priority = 1)
	public void TC_GET_status_code() {
		given().
		header("Content-Type", "application/json")
		.get("http://localhost:3030/services").
		then().
		statusCode(200);
	}
	
	//check response size
	@Test(enabled = true,priority =2)
	public void TC_GET_response_size() {
		given().
		header("Content-Type", "application/json")
		.get("http://localhost:3030/services").
		then().
		statusCode(200)
		.body("data.size()", equalTo(10)).log().all();
	}
	
	//check limit parameter
	@Test(enabled = true,priority =3)
	public void TC_GET_LIMIT_check_status_code_and_lenght() {
		given().
		header("Content-Type", "application/json")
		.get("http://localhost:3030/services/?$limit=20").
		then().
		statusCode(200)
		.body("data.size()", equalTo(20)).log().all();
	}
	
	@Test(enabled = true,priority =4)
	public void TC_GET_LIMIT_bad_request() {
		given().
		header("Content-Type", "application/json")
		.get("http://localhost:3030/services/?$limit=five").
		then().
		statusCode(500)
		.log().all();
	}
	//check skip parameter

	@Test(enabled = true,priority =5)
	public void TC_GET_SKIP_status_code_and_lenght() {
		given().
		header("Content-Type", "application/json")
		.get("http://localhost:3030/services/?$skip=15").
		then().statusCode(200).body("data.size()", equalTo(6)).
		log().all();
	}
	
	@Test(enabled = true,priority =6)
	public void TC_GET_SKIP_bad_request() {
		given().
		header("Content-Type", "application/json")
		.get("http://localhost:3030/services/?$skip=two").
		then().statusCode(500);
	}
//get request with each attribute
	@Test(enabled = true,priority =7)
	public void TC_GET_request_with_Valid_Param() {
		given().
		header("Content-Type", "application/json").param("name", "testServiceName")
		.get("http://localhost:3030/services").
		then().
		statusCode(200)
		.log().all();
	}

	@Test(enabled = true,priority =8)
	public void TC_GET_request_with_Not_exist_id() {
		given().
		header("Content-Type", "application/json")
		.get("http://localhost:3030/services/1111111111111").
		then().
		statusCode(404)
		.log().all();
	}


	//POST test cases

	@Test(enabled = true,priority =9)
	void TC_Post_valid_request() {
		JSONObject request = new JSONObject();

		request.put("name", "testServiceName");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		post("http://localhost:3030/services").
		then().
		statusCode(201);

		//check that service has posted

		given().get("http://localhost:3030/services/?name=testServiceName").then().
		statusCode(200).
		body("data.size()", equalTo(1)).log().all();
		//delete posted service
		given().delete("http://localhost:3030/services/?name=testServiceName").then().statusCode(200);
		// check that service has deleted
		given().get("http://localhost:3030/services/?name=testServiceName").then().
		statusCode(200).
		body("data.size()", equalTo(0)).log().all();
	}
	
	
	@Test(enabled = true,priority =10)
	void TC_Post_request_with_no_body() {
		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		when().
		post("http://localhost:3030/services").
		then().
		statusCode(400);
	}
	
	//delete
	@Test(enabled = true,priority =11)
	void TC_Delete_valid_request() {
		JSONObject request = new JSONObject();

		request.put("name", "testServiceName");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		post("http://localhost:3030/services").
		then().
		statusCode(201);

		//delete posted service
		given().delete("http://localhost:3030/services/?name=testServiceName").then().statusCode(200);
	}
	
	@Test(enabled = true,priority =12)
	void TC_Delete_request_with_not_exist_id() {
		given().
		delete("http://localhost:3030/services/1111111111").
		then().statusCode(404).log().all();

	}
	
	//patch
	@Test(enabled = true,priority =13)

	void TC_Patch_valid_request() {
		//create new service and return its ID 
		String serviceId = beforePatchRequest();
		
		JSONObject request = new JSONObject();

		request.put("name", "patched testServiceName");
		
		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		patch("http://localhost:3030/services/"+serviceId).
		then().
		statusCode(200).log().all();
		afterPatchRequest(serviceId);

	}
	
	@Test(enabled = true,priority =14)
	void TC_Patch_request_with_not_exist_ID() {

		given().
		patch("http://localhost:3030/services/1111111").
		then().
		statusCode(404);

	}
	
	
public String beforePatchRequest ()
{
	JSONObject request = new JSONObject();

	request.put("name", "tesServiceName");



	Response response = given().
	header("Content-Type", "application/json").
	contentType(ContentType.JSON).
	accept(ContentType.JSON).
	body(request.toJSONString()).
	when().
	post("http://localhost:3030/services");
	
	return response.jsonPath().getString("id");
}

public void afterPatchRequest (String serviceId)
{
	given().
	delete("http://localhost:3030/services/"+serviceId).
	then().statusCode(200);

}
}
