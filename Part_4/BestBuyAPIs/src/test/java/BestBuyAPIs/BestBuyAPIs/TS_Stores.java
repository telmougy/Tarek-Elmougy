package BestBuyAPIs.BestBuyAPIs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import BestBuyAPIs.data.ExcellReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class TS_Stores {
	
	@DataProvider(name = "ProductsValidParams")
	Object[][] getProductsValidParams() throws IOException {
		ExcellReader ER = new ExcellReader();
		return ER.getExcelData("StoresValidData", 2);
	}

	//GET test Cases
	//check status code
	@Test(enabled = true,priority = 1)
	public void TC_GET_status_code() {
		given().
		header("Content-Type", "application/json")
		.get("http://localhost:3030/stores").
		then().
		statusCode(200);
	}
	
	//check response size
	@Test(enabled = true,priority =2)
	public void TC_GET_response_size() {
		given().
		header("Content-Type", "application/json")
		.get("http://localhost:3030/stores").
		then().
		statusCode(200)
		.body("data.size()", equalTo(10)).log().all();
	}
	
	//check limit parameter
	@Test(enabled = true,priority =3)
	public void TC_GET_LIMIT_check_status_code_and_lenght() {
		given().
		header("Content-Type", "application/json")
		.get("http://localhost:3030/stores/?$limit=25").
		then().
		statusCode(200)
		.body("data.size()", equalTo(25)).log().all();
	}
	
	@Test(enabled = true,priority =4)
	public void TC_GET_LIMIT_bad_request() {
		given().
		header("Content-Type", "application/json")
		.get("http://localhost:3030/stores/?$limit=five").
		then().
		statusCode(500)
		.log().all();
	}
	//check skip parameter

	@Test(enabled = true,priority =5)
	public void TC_GET_SKIP_status_code_and_lenght() {
		given().
		header("Content-Type", "application/json")
		.get("http://localhost:3030/stores/?$skip=1555").
		then().statusCode(200).body("data.size()", equalTo(6)).
		log().all();
	}
	
	@Test(enabled = true,priority =6)
	public void TC_GET_SKIP_bad_request() {
		given().
		header("Content-Type", "application/json")
		.get("http://localhost:3030/stores/?$skip=two").
		then().statusCode(500);
	}
//get request with each attribute
	@Test(enabled = true, dataProvider = "ProductsValidParams",priority =7)
	public void TC_GET_request_with_Valid_Param(String paramName,String paramValue) {
		given().
		header("Content-Type", "application/json").param(paramName, paramValue)
		.get("http://localhost:3030/stores").
		then().
		statusCode(200)
		.log().all();
	}

	@Test(enabled = true,priority =8)
	public void TC_GET_request_with_Not_exist_id() {
		given().
		header("Content-Type", "application/json")
		.get("http://localhost:3030/stores/1111111111111").
		then().
		statusCode(404)
		.log().all();
	}

	//POST test cases

	@Test(enabled = true,priority =9)
	void TC_Post_valid_request() {

		JSONObject request = new JSONObject();

		request.put("name", "testStoreName");
		request.put("type", "testStoreType");
		request.put("address", "testStoreAddress1");
		request.put("address2", "TestStoreAddress2");
		request.put("city", "testStoreCity");
		request.put("state", "testStoreState"); 
		request.put("zip", "testStoreZip");
		request.put("lat", 111);
		request.put("lng", 222);
		request.put("hours", "testStoreHours");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		post("http://localhost:3030/stores").
		then().
		statusCode(201);

		//check that product has posted

		given().get("http://localhost:3030/stores/?name=testStoreName").then().
		statusCode(200).
		body("data.size()", equalTo(1)).log().all();
		//delete posted store
		given().delete("http://localhost:3030/stores/?name=testStoreName").then().statusCode(200);
		// check that store has deleted
		given().get("http://localhost:3030/stores/?name=testStoreName").then().
		statusCode(200).
		body("data.size()", equalTo(0)).log().all();
	}
	
	@Test(enabled = true,priority =10)
	void TC_Post_request_with_no_Name() {

		JSONObject request = new JSONObject();

		request.put("type", "testStoreType");
		request.put("address", "testStoreAddress1");
		request.put("address2", "TestStoreAddress2");
		request.put("city", "testStoreCity");
		request.put("state", "testStoreState"); 
		request.put("zip", "testStoreZip");
		request.put("lat", 111);
		request.put("lng", 222);
		request.put("hours", "testStoreHours");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		post("http://localhost:3030/stores").
		then().
		statusCode(400);
	}
	
	@Test(enabled = true,priority =11)
	void TC_Post_request_with_no_type() {
		JSONObject request = new JSONObject();

		request.put("name", "testStoreName");
		request.put("address", "testStoreAddress1");
		request.put("address2", "TestStoreAddress2");
		request.put("city", "testStoreCity");
		request.put("state", "testStoreState"); 
		request.put("zip", "testStoreZip");
		request.put("lat", 111);
		request.put("lng", 222);
		request.put("hours", "testStoreHours");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		post("http://localhost:3030/stores").
		then().
		statusCode(201);	
		
		//delete posted stores

		given().delete("http://localhost:3030/stores/?name=testStoreName").then().statusCode(200);

	}
	
	@Test(enabled = true,priority =12)
	void TC_Post_request_with_no_address1() {

		JSONObject request = new JSONObject();
		request.put("name", "testStoreName");
		request.put("type", "testStoreType");
		request.put("address2", "TestStoreAddress2");
		request.put("city", "testStoreCity");
		request.put("state", "testStoreState"); 
		request.put("zip", "testStoreZip");
		request.put("lat", 111);
		request.put("lng", 222);
		request.put("hours", "testStoreHours");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		post("http://localhost:3030/stores").
		then().
		statusCode(400);
	}
	
	@Test(enabled = true,priority =13)
	void TC_Post_request_with_no_address2() {

		JSONObject request = new JSONObject();
		request.put("name", "testStoreName");
		request.put("type", "testStoreType");
		request.put("address", "testStoreAddress1");
		request.put("city", "testStoreCity");
		request.put("state", "testStoreState"); 
		request.put("zip", "testStoreZip");
		request.put("lat", 111);
		request.put("lng", 222);
		request.put("hours", "testStoreHours");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		post("http://localhost:3030/stores").
		then().
		statusCode(201);
		
		//delete posted stores

		given().delete("http://localhost:3030/stores/?name=testStoreName").then().statusCode(200);
	}
	
	@Test(enabled = true,priority =14)
	void TC_Post_request_with_no_city() {

		JSONObject request = new JSONObject();
		request.put("name", "testStoreName");
		request.put("type", "testStoreType");
		request.put("address", "testStoreAddress1");
		request.put("address2", "TestStoreAddress2");
		request.put("state", "testStoreState"); 
		request.put("zip", "testStoreZip");
		request.put("lat", 111);
		request.put("lng", 222);
		request.put("hours", "testStoreHours");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		post("http://localhost:3030/stores").
		then().
		statusCode(400);
	}
	
	@Test(enabled = true,priority =15)
	void TC_Post_request_with_no_state() {

		JSONObject request = new JSONObject();
		request.put("name", "testStoreName");
		request.put("type", "testStoreType");
		request.put("address", "testStoreAddress1");
		request.put("address2", "TestStoreAddress2");
		request.put("city", "testStoreCity");
		request.put("zip", "testStoreZip");
		request.put("lat", 111);
		request.put("lng", 222);
		request.put("hours", "testStoreHours");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		post("http://localhost:3030/stores").
		then().
		statusCode(400);
	}
	
	@Test(enabled = true,priority =16)
	void TC_Post_request_with_no_zip() {

		JSONObject request = new JSONObject();
		request.put("name", "testStoreName");
		request.put("type", "testStoreType");
		request.put("address", "testStoreAddress1");
		request.put("address2", "TestStoreAddress2");
		request.put("city", "testStoreCity");
		request.put("state", "testStoreState"); 
		request.put("lat", 111);
		request.put("lng", 222);
		request.put("hours", "testStoreHours");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		post("http://localhost:3030/stores").
		then().
		statusCode(400);
	}
	
	@Test(enabled = true,priority =17)
	void TC_Post_request_with_no_lat() {

		JSONObject request = new JSONObject();
		request.put("name", "testStoreName");
		request.put("type", "testStoreType");
		request.put("address", "testStoreAddress1");
		request.put("address2", "TestStoreAddress2");
		request.put("city", "testStoreCity");
		request.put("state", "testStoreState"); 
		request.put("zip", "testStoreZip");
		request.put("lng", 222);
		request.put("hours", "testStoreHours");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		post("http://localhost:3030/stores").
		then().
		statusCode(201);
		
		//delete posted stores

		given().delete("http://localhost:3030/stores/?name=testStoreName").then().statusCode(200);
	}
	
	@Test(enabled = true,priority =18)
	void TC_Post_request_with_no_lng() {

		JSONObject request = new JSONObject();
		request.put("name", "testStoreName");
		request.put("type", "testStoreType");
		request.put("address", "testStoreAddress1");
		request.put("address2", "TestStoreAddress2");
		request.put("city", "testStoreCity");
		request.put("state", "testStoreState"); 
		request.put("zip", "testStoreZip");
		request.put("lat", 111);
		request.put("hours", "testStoreHours");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		post("http://localhost:3030/stores").
		then().
		statusCode(201);
		
		//delete posted stores

		given().delete("http://localhost:3030/stores/?name=testStoreName").then().statusCode(200);
	}
	
	@Test(enabled = true,priority =19)
	void TC_Post_request_with_no_hours() {

		JSONObject request = new JSONObject();
		request.put("name", "testStoreName");
		request.put("type", "testStoreType");
		request.put("address", "testStoreAddress1");
		request.put("address2", "TestStoreAddress2");
		request.put("city", "testStoreCity");
		request.put("state", "testStoreState"); 
		request.put("zip", "testStoreZip");
		request.put("lat", 111);
		request.put("lng", 222);

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		post("http://localhost:3030/stores").
		then().
		statusCode(201);
		//delete posted stores
		given().delete("http://localhost:3030/stores/?name=testStoreName").then().statusCode(200);

	}
	@Test(enabled = true,priority =20)
	void TC_Post_request_with_no_body() {
		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		when().
		post("http://localhost:3030/stores").
		then().
		statusCode(400);
	}
	//delete
	@Test(enabled = true,priority =21)
	void TC_Delete_valid_request() {
		JSONObject request = new JSONObject();


		request.put("name", "testStoreName");
		request.put("type", "testStoreType");
		request.put("address", "testStoreAddress1");
		request.put("address2", "TestStoreAddress2");
		request.put("city", "testStoreCity");
		request.put("state", "testStoreState"); 
		request.put("zip", "testStoreZip");
		request.put("lat", 111);
		request.put("lng", 222);
		request.put("hours", "testStoreHours");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		post("http://localhost:3030/stores").
		then().
		statusCode(201);

		//delete posted product
		given().delete("http://localhost:3030/stores/?name=testStoreName").then().statusCode(200);

	}
	@Test(enabled = true,priority =22)
	void TC_Delete_request_with_not_exist_id() {
		given().
		delete("http://localhost:3030/stores/1111111111").
		then().statusCode(404).log().all();

	}
	
	//patch
	@Test(enabled = true,priority =23)

	void TC_Patch_valid_request() {
		//create new store and return its ID 

		String storeId = beforePatchRequest();
		

		JSONObject request = new JSONObject();

		request.put("name", "patched testStoreName");
		request.put("type", "patched testStoreType");
		request.put("address", "patched testStoreAddress1");
		request.put("address2", "patched TestStoreAddress2");
		request.put("city", "patched testStoreCity");
		request.put("state", "patched testStoreState"); 
		request.put("zip", "patched testStoreZip");
		request.put("lat", 555);
		request.put("lng", 666);
		request.put("hours", "patched testStoreHours");
		
		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		patch("http://localhost:3030/stores/"+storeId).
		then().
		statusCode(200).log().all();
		afterPatchRequest(storeId);

	}
	
	@Test(enabled = true,priority =24)
	void TC_Patch_request_with_Name() {
		//create new store and return its ID 

		String storeId = beforePatchRequest();	

		JSONObject request = new JSONObject();

		request.put("name", "patched testStoreName");
		
		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		patch("http://localhost:3030/stores/"+storeId).
		then().
		statusCode(200).log().all();
		afterPatchRequest(storeId);

	}
	@Test(enabled = true,priority =25)
	void TC_Patch_request_with_Type() {
		//create new store and return its ID 

		String storeId = beforePatchRequest();
		
		JSONObject request = new JSONObject();

		request.put("type", "patched testStoreType");
	
		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		patch("http://localhost:3030/stores/"+storeId).
		then().
		statusCode(200).log().all();
		afterPatchRequest(storeId);

	}
	@Test(enabled = true,priority =26)
	void TC_Patch_request_with_Address() {
		//create new store and return its ID 

		String storeId = beforePatchRequest();

		JSONObject request = new JSONObject();

		request.put("address", "patched testStoreAddress1");
		
		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		patch("http://localhost:3030/stores/"+storeId).
		then().
		statusCode(200).log().all();
		afterPatchRequest(storeId);

	}
	@Test(enabled = true,priority =27)
	void TC_Patch_request_with_Address2() {
		//create new store and return its ID 

		String storeId = beforePatchRequest();
		
		JSONObject request = new JSONObject();

		request.put("address2", "patched TestStoreAddress2");
		
		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		patch("http://localhost:3030/stores/"+storeId).
		then().
		statusCode(200).log().all();
		afterPatchRequest(storeId);

	}
	@Test(enabled = true,priority =28)
	void TC_Patch_request_with_City() {
		//create new store and return its ID 

		String storeId = beforePatchRequest();
		
		JSONObject request = new JSONObject();

		request.put("city", "patched testStoreCity");
	
		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		patch("http://localhost:3030/stores/"+storeId).
		then().
		statusCode(200).log().all();
		afterPatchRequest(storeId);

	}
	@Test(enabled = true,priority =29)
	void TC_Patch_request_with_State() {
		//create new store and return its ID 

		String storeId = beforePatchRequest();
		
		JSONObject request = new JSONObject();

		request.put("state", "patched testStoreState"); 
		
		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		patch("http://localhost:3030/stores/"+storeId).
		then().
		statusCode(200).log().all();
		afterPatchRequest(storeId);

	}

	@Test(enabled = true,priority =30)
	void TC_Patch_request_with_Zip() {
		//create new store and return its ID 

		String storeId = beforePatchRequest();
		
		JSONObject request = new JSONObject();

		request.put("zip", "patched testStoreZip");
		
		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		patch("http://localhost:3030/stores/"+storeId).
		then().
		statusCode(200).log().all();
		afterPatchRequest(storeId);

	}
	@Test(enabled = true,priority =31)
	void TC_Patch_request_with_lat() {
		//create new store and return its ID 

		String storeId = beforePatchRequest();
		
		JSONObject request = new JSONObject();

		request.put("lat", 555);
		
		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		patch("http://localhost:3030/stores/"+storeId).
		then().
		statusCode(200).log().all();
		afterPatchRequest(storeId);

	}
	@Test(enabled = true,priority =32)
	void TC_Patch_request_with_lng() {
		//create new store and return its ID 

		String storeId = beforePatchRequest();
		
		JSONObject request = new JSONObject();

		request.put("lng", 666);
			
		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		patch("http://localhost:3030/stores/"+storeId).
		then().
		statusCode(200).log().all();
		afterPatchRequest(storeId);

	}
	@Test(enabled = true,priority =33)
	void TC_Patch_request_with_hours() {
		//create new store and return its ID 

		String storeId = beforePatchRequest();
		
		JSONObject request = new JSONObject();

		request.put("hours", "patched testStoreHours");		
		
		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		patch("http://localhost:3030/stores/"+storeId).
		then().
		statusCode(200).log().all();
		afterPatchRequest(storeId);

	}
	@Test(enabled = true,priority =34)
	void TC_Patch_request_with_not_exist_ID() {

		given().
		patch("http://localhost:3030/stores/1111111").
		then().
		statusCode(404);

	}
	
	//create new store and return its ID 

public String beforePatchRequest ()
{
	JSONObject request = new JSONObject();

	request.put("name", "testStoreName");
	request.put("type", "testStoreType");
	request.put("address", "testStoreAddress1");
	request.put("address2", "TestStoreAddress2");
	request.put("city", "testStoreCity");
	request.put("state", "testStoreState"); 
	request.put("zip", "testStoreZip");
	request.put("lat", 111);
	request.put("lng", 222);
	request.put("hours", "testStoreHours");


	Response response = given().
	header("Content-Type", "application/json").
	contentType(ContentType.JSON).
	accept(ContentType.JSON).
	body(request.toJSONString()).
	when().
	post("http://localhost:3030/stores");
	
	return response.jsonPath().getString("id");
}

public void afterPatchRequest (String storeId)
{
	given().
	delete("http://localhost:3030/stores/"+storeId).
	then().statusCode(200);

}
}

