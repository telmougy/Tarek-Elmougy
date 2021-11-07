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

public class TS_Products {
	@DataProvider(name = "ProductsValidParams")
	Object[][] getProductsValidParams() throws IOException {
		ExcellReader ER = new ExcellReader();
		return ER.getExcelData("ProductsValidParams", 2);
	}


	//GET test Cases
	//check status code
	@Test(enabled = true,priority = 1)
	public void TC_GET_status_code() {
		given().
		header("Content-Type", "application/json")
		.get("http://localhost:3030/products").
		then().
		statusCode(200);
	}
	
	//check response size
	@Test(enabled = true,priority =2)
	public void TC_GET_response_size() {
		given().
		header("Content-Type", "application/json")
		.get("http://localhost:3030/products").
		then().
		statusCode(200)
		.body("data.size()", equalTo(10)).log().all();
	}
	
	//check limit parameter
	@Test(enabled = true,priority =3)
	public void TC_GET_LIMIT_check_status_code_and_lenght() {
		given().
		header("Content-Type", "application/json")
		.get("http://localhost:3030/products/?$limit=25").
		then().
		statusCode(200)
		.body("data.size()", equalTo(25)).log().all();
	}
	
	@Test(enabled = true,priority =4)
	public void TC_GET_LIMIT_bad_request() {
		given().
		header("Content-Type", "application/json")
		.get("http://localhost:3030/products/?$limit=five").
		then().
		statusCode(500)
		.log().all();
	}
	
	//check limit parameter
	@Test(enabled = true,priority =5)
	public void TC_GET_SKIP_status_code_and_lenght() {
		given().
		header("Content-Type", "application/json")
		.get("http://localhost:3030/products/?$skip=51950").
		then().statusCode(200).body("data.size()", equalTo(7)).
		log().all();
	}
	
	@Test(enabled = true,priority =6)
	public void TC_GET_SKIP_bad_request() {
		given().
		header("Content-Type", "application/json")
		.get("http://localhost:3030/products/?$skip=two").
		then().statusCode(500);
	}

	@Test(enabled = true, dataProvider = "ProductsValidParams",priority =7)
	public void TC_GET_request_with_Valid_Param(String paramName,String paramValue) {
		given().
		header("Content-Type", "application/json").param(paramName, paramValue)
		.get("http://localhost:3030/products").
		then().
		statusCode(200)
		.log().all();
	}

	@Test(enabled = true,priority =8)
	public void TC_GET_request_with_Not_exist_id() {
		given().
		header("Content-Type", "application/json")
		.get("http://localhost:3030/products/1111111111111").
		then().
		statusCode(404)
		.log().all();
	}


	//POST test cases

	@Test(enabled = true,priority =9)
	void TC_Post_valid_request() {
		JSONObject request = new JSONObject();

		request.put("name", "TestProduct");
		request.put("type", "testType");
		request.put("price", 1111);
		request.put("shipping", 0);
		request.put("upc", "Testupc");
		request.put("description", "test"); 
		request.put("manufacturer", "testManufacturer");
		request.put("model", "MN2400B4Z");
		request.put("url", "https//:test.com");
		request.put("image", "String");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		post("http://localhost:3030/products").
		then().
		statusCode(201);

		//check that product has posted

		given().get("http://localhost:3030/products/?name=TestProduct").then().
		statusCode(200).log().all();
		//delete posted product
		given().delete("http://localhost:3030/products/?name=TestProduct").then().statusCode(200);
		// check that product has deleted
		given().get("http://localhost:3030/products/?name=TestProduct").then().
		statusCode(200).
		body("data.size()", equalTo(0)).log().all();
	}

	@Test(enabled = true,priority =10)
	void TC_Post_request_with_no_Name() {

		JSONObject request = new JSONObject();

		request.put("type", "testType");
		request.put("price", 1111);
		request.put("shipping", 0);
		request.put("upc", "Testupc");
		request.put("description", "test"); 
		request.put("manufacturer", "testManufacturer");
		request.put("model", "MN2400B4Z");
		request.put("url", "https//:test.com");
		request.put("image", "String");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		post("http://localhost:3030/products").
		then().
		statusCode(400);
	}

	@Test(enabled = true,priority =11)
	void TC_Post_request_with_no_type() {

		JSONObject request = new JSONObject();
		request.put("name", "TestProduct");
		request.put("price", 1111);
		request.put("shipping", 0);
		request.put("upc", "Testupc");
		request.put("description", "test"); 
		request.put("manufacturer", "testManufacturer");
		request.put("model", "MN2400B4Z");
		request.put("url", "https//:test.com");
		request.put("image", "String");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		post("http://localhost:3030/products").
		then().
		statusCode(400);
	}

	@Test(enabled = true,priority =12)
	void TC_Post_request_with_no_upc() {

		JSONObject request = new JSONObject();
		request.put("name", "TestProduct");
		request.put("type", "testType");
		request.put("price", 1111);
		request.put("shipping", 0);
		request.put("description", "test"); 
		request.put("manufacturer", "testManufacturer");
		request.put("model", "MN2400B4Z");
		request.put("url", "https//:test.com");
		request.put("image", "String");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		post("http://localhost:3030/products").
		then().
		statusCode(400);
	}

	@Test(enabled = true,priority =13)
	void TC_Post_request_with_no_description() {

		JSONObject request = new JSONObject();
		request.put("name", "TestProduct");
		request.put("type", "testType");
		request.put("price", 1111);
		request.put("shipping", 0);
		request.put("upc", "Testupc");
		request.put("manufacturer", "testManufacturer");
		request.put("model", "MN2400B4Z");
		request.put("url", "https//:test.com");
		request.put("image", "String");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		post("http://localhost:3030/products").
		then().
		statusCode(400);
	}

	@Test(enabled = true,priority =14)
	void TC_Post_request_with_no_model() {

		JSONObject request = new JSONObject();
		request.put("name", "TestProduct");
		request.put("type", "testType");
		request.put("price", 1111);
		request.put("shipping", 0);
		request.put("upc", "Testupc");
		request.put("description", "test"); 
		request.put("manufacturer", "testManufacturer");
		request.put("url", "https//:test.com");
		request.put("image", "String");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		post("http://localhost:3030/products").
		then().
		statusCode(400);
	}
	
	@Test(enabled = true,priority =15)
	void TC_Post_request_with_no_Price() {
		JSONObject request = new JSONObject();

		request.put("name", "TestProduct");
		request.put("type", "testType");
		request.put("shipping", 0);
		request.put("upc", "Testupc");
		request.put("description", "test"); 
		request.put("manufacturer", "testManufacturer");
		request.put("model", "MN2400B4Z");
		request.put("url", "https//:test.com");
		request.put("image", "String");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		post("http://localhost:3030/products").
		then().
		statusCode(201);


		//delete posted product
		given().delete("http://localhost:3030/products/?name=TestProduct").then().statusCode(200);

	}
	
	@Test(enabled = true,priority =16)
	void TC_Post_request_with_no_shipping() {
		JSONObject request = new JSONObject();

		request.put("name", "TestProduct");
		request.put("type", "testType");
		request.put("price", 1111);
		request.put("upc", "Testupc");
		request.put("description", "test"); 
		request.put("manufacturer", "testManufacturer");
		request.put("model", "MN2400B4Z");
		request.put("url", "https//:test.com");
		request.put("image", "String");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		post("http://localhost:3030/products").
		then().
		statusCode(201);

		//delete posted product
		given().delete("http://localhost:3030/products/?name=TestProduct").then().statusCode(200);
	}
	
	@Test(enabled = true,priority =17)
	void TC_Post_request_with_no_Manufacturer() {
		JSONObject request = new JSONObject();


		request.put("name", "TestProduct");
		request.put("type", "testType");
		request.put("price", 1111);
		request.put("shipping", 0);
		request.put("upc", "Testupc");
		request.put("description", "test"); 
		request.put("model", "MN2400B4Z");
		request.put("url", "https//:test.com");
		request.put("image", "String");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		post("http://localhost:3030/products").
		then().
		statusCode(201);


		//delete posted product
		given().delete("http://localhost:3030/products/?name=TestProduct").then().statusCode(200);
	}
	
	@Test(enabled = true,priority =18)
	void TC_Post_request_with_no_URL() {
		JSONObject request = new JSONObject();

		request.put("name", "TestProduct");
		request.put("type", "testType");
		request.put("price", 1111);
		request.put("shipping", 0);
		request.put("upc", "Testupc");
		request.put("description", "test"); 
		request.put("manufacturer", "testManufacturer");
		request.put("model", "MN2400B4Z");
		request.put("image", "String");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		post("http://localhost:3030/products").
		then().
		statusCode(201);

		//delete posted product
		given().delete("http://localhost:3030/products/?name=TestProduct").then().statusCode(200);
	}
	
	@Test(enabled = true,priority =19)
	void TC_Post_request_with_no_Image() {
		JSONObject request = new JSONObject();

		request.put("name", "TestProduct");
		request.put("type", "testType");
		request.put("price", 1111);
		request.put("shipping", 0);
		request.put("upc", "Testupc");
		request.put("description", "test"); 
		request.put("manufacturer", "testManufacturer");
		request.put("model", "MN2400B4Z");
		request.put("url", "https//:test.com");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		post("http://localhost:3030/products").
		then().
		statusCode(201);

		//delete posted product
		given().delete("http://localhost:3030/products/?name=TestProduct").then().statusCode(200);
	}

	@Test(enabled = true,priority =20)
	void TC_Post_request_with_no_body() {
		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		when().
		post("http://localhost:3030/products").
		then().
		statusCode(400);
	}
	
	//delete request test cases 
	@Test(enabled = true,priority =21)
	void TC_Delete_valid_request() {
		JSONObject request = new JSONObject();

		request.put("name", "TestProduct");
		request.put("type", "testType");
		request.put("price", 1111);
		request.put("shipping", 0);
		request.put("upc", "Testupc");
		request.put("description", "test"); 
		request.put("manufacturer", "testManufacturer");
		request.put("model", "MN2400B4Z");
		request.put("url", "https//:test.com");
		request.put("image", "String");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		post("http://localhost:3030/products").
		then().
		statusCode(201);

		//delete posted product
		given().delete("http://localhost:3030/products/?name=TestProduct").then().statusCode(200);
	}
	
	@Test(enabled = true,priority =22)
	void TC_Delete_request_with_not_exist_id() {
		given().
		delete("http://localhost:3030/products/1111111111").
		then().statusCode(404).log().all();
	}

	//patch request test cases 
	@Test(enabled = true,priority =23)
	void TC_Patch_valid_request() {
		//create new product and return its ID 
		String productID = beforePatchRequest();

		JSONObject request = new JSONObject();

		request.put("name", "PatchedTestProduct");
		request.put("type", "PatchedtestType");
		request.put("price", 0000);
		request.put("shipping", 2);
		request.put("upc", "Patched Testupc");
		request.put("description", "Patched test"); 
		request.put("manufacturer", "Patched Apple");
		request.put("model", "Patched MN2400B4Z");
		request.put("url", "Patched https//:apple.com");
		request.put("image", "Patched String");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		patch("http://localhost:3030/products/"+productID).
		then().
		statusCode(200).log().all();
		afterPatchRequest(productID);
	}

	@Test(enabled = true,priority =24)
	void TC_Patch_request_with_Name() {
		//create new product and return its ID 

		String productID = beforePatchRequest();	

		JSONObject request = new JSONObject();

		request.put("name", "PatchedTestProduct");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		patch("http://localhost:3030/products/"+productID).
		then().
		statusCode(200).log().all();
		afterPatchRequest(productID);
	}
	
	@Test(enabled = true,priority =25)
	void TC_Patch_request_with_Type() {
		//create new product and return its ID 

		String productID = beforePatchRequest();

		JSONObject request = new JSONObject();

		request.put("type", "PatchedtestType");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		patch("http://localhost:3030/products/"+productID).
		then().
		statusCode(200).log().all();
		afterPatchRequest(productID);
	}
	
	@Test(enabled = true,priority =26)
	void TC_Patch_request_with_Price() {
		//create new product and return its ID 

		String productID = beforePatchRequest();

		JSONObject request = new JSONObject();

		request.put("price", 0000);

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		patch("http://localhost:3030/products/"+productID).
		then().
		statusCode(200).log().all();
		afterPatchRequest(productID);
	}
	
	@Test(enabled = true,priority =27)
	void TC_Patch_request_with_Shipping() {
		//create new product and return its ID 

		String productID = beforePatchRequest();

		JSONObject request = new JSONObject();

		request.put("shipping", 2);

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		patch("http://localhost:3030/products/"+productID).
		then().
		statusCode(200).log().all();
		afterPatchRequest(productID);
	}
	
	@Test(enabled = true,priority =28)
	void TC_Patch_request_with_UPC() {
		//create new product and return its ID 

		String productID = beforePatchRequest();

		JSONObject request = new JSONObject();

		request.put("upc", "Patched Testupc");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		patch("http://localhost:3030/products/"+productID).
		then().
		statusCode(200).log().all();
		afterPatchRequest(productID);
	}
	
	@Test(enabled = true,priority =29)
	void TC_Patch_request_with_Description() {
		//create new product and return its ID 

		String productID = beforePatchRequest();

		JSONObject request = new JSONObject();

		request.put("description", "Patched test"); 

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		patch("http://localhost:3030/products/"+productID).
		then().
		statusCode(200).log().all();
		afterPatchRequest(productID);

	}
	@Test(enabled = true,priority =30)
	void TC_Patch_request_with_Manufacturer() {
		//create new product and return its ID 

		String productID = beforePatchRequest();

		JSONObject request = new JSONObject();

		request.put("manufacturer", "Patched Apple");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		patch("http://localhost:3030/products/"+productID).
		then().
		statusCode(200).log().all();
		afterPatchRequest(productID);
	}
	
	@Test(enabled = true,priority =31)
	void TC_Patch_request_with_Model() {
		//create new product and return its ID 

		String productID = beforePatchRequest();

		JSONObject request = new JSONObject();

		request.put("model", "Patched MN2400B4Z");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		patch("http://localhost:3030/products/"+productID).
		then().
		statusCode(200).log().all();
		afterPatchRequest(productID);
	}
	
	@Test(enabled = true,priority =32)
	void TC_Patch_request_with_URL() {
		//create new product and return its ID 

		String productID = beforePatchRequest();

		JSONObject request = new JSONObject();

		request.put("url", "Patched https//:apple.com");		

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		patch("http://localhost:3030/products/"+productID).
		then().
		statusCode(200).log().all();
		afterPatchRequest(productID);
	}
	
	@Test(enabled = true,priority =33)
	void TC_Patch_request_with_Image() {
		//create new product and return its ID 

		String productID = beforePatchRequest();

		JSONObject request = new JSONObject();

		request.put("image", "Patched String");

		given().
		header("Content-Type", "application/json").
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(request.toJSONString()).
		when().
		patch("http://localhost:3030/products/"+productID).
		then().
		statusCode(200).log().all();
		afterPatchRequest(productID);
	}
	
	@Test(enabled = true,priority =34)
	void TC_Patch_request_with_not_exist_ID() {

		given().
		patch("http://localhost:3030/products/1111111").
		then().
		statusCode(404);
	}


	public String beforePatchRequest ()
	{
		JSONObject request = new JSONObject();

		request.put("name", "TestProduct");
		request.put("type", "testType");
		request.put("price", 1111);
		request.put("shipping", 0);
		request.put("upc", "Testupc");
		request.put("description", "test"); 
		request.put("manufacturer", "testManufacturer");
		request.put("model", "MN2400B4Z");
		request.put("url", "https//:test.com");
		request.put("image", "String");


		Response response = given().
				header("Content-Type", "application/json").
				contentType(ContentType.JSON).
				accept(ContentType.JSON).
				body(request.toJSONString()).
				when().
				post("http://localhost:3030/products");

		return response.jsonPath().getString("id");
	}

	public void afterPatchRequest (String ProductId)
	{
		given().
		delete("http://localhost:3030/products/"+ProductId).
		then().statusCode(200).log().all();

	}
}
