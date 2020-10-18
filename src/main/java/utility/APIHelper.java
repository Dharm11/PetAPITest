package utility;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;

public class APIHelper {
	
	static CloseableHttpResponse closeableHttpResponse = null;
	
	
	public static CloseableHttpResponse executeGetAPI(String serviceURL) {
		try {
			CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
			HttpGet get = new HttpGet(Global.strBaseURL+serviceURL);			
			closeableHttpResponse  = closeableHttpClient.execute(get);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	
		return closeableHttpResponse;
		
	}
	
	
	//===============================================================================
	public static CloseableHttpResponse executePostAPI(String serviceURL,String payloadFileName , String strContentType) {
		try {
			CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
			HttpPost post = new HttpPost(Global.strBaseURL+serviceURL);	
			String strRequestPayload = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"\\Input\\"+payloadFileName+".txt"), "UTF-8");		
			post.setEntity(new StringEntity(strRequestPayload));
			
			if(strContentType.contains("json")) {
				post.addHeader("Content-Type", "application/json");
			}
			else if(strContentType.contains("urlencoded")) {
				
				post.addHeader("Content-Type", "application/x-www-form-urlencoded");
			}
	
			closeableHttpResponse  = closeableHttpClient.execute(post);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	
		return closeableHttpResponse;
		
	}
	
	
	//======================================================================================================
	public static long getAvailablePetID(String serviceURL,String payloadFileName,String contentType) throws Exception {
		
		closeableHttpResponse = executePostAPI(serviceURL,payloadFileName,contentType);	
		Assert.assertEquals(200, closeableHttpResponse.getStatusLine().getStatusCode(),"status code is NOT matched");
		System.out.println("Status code -- > " + closeableHttpResponse.getStatusLine().getStatusCode());
		String strResponseBody = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject jsonObj = new JSONObject(strResponseBody);
		long id = (long) jsonObj.get("id");
		System.out.println("pet ID -- " + id);
		
		return id;
		
	}
	
	

	//======================================================================================================
	public static String updatePetStatus(String serviceURL,String payloadFileName,String contentType) throws Exception {
		
		closeableHttpResponse = executePostAPI(serviceURL,payloadFileName,contentType);		
		String strResponseBody = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		Assert.assertEquals(200, closeableHttpResponse.getStatusLine().getStatusCode(),"status code is NOT matched");
		System.out.println("Status code -- > " + closeableHttpResponse.getStatusLine().getStatusCode());
		JSONObject jsonObj = new JSONObject(strResponseBody);
		String message = (String) jsonObj.get("message");
		System.out.println("message -- " + message);
		
		return message;
		
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static CloseableHttpResponse executeDeleteAPI(String serviceURL) {
		try {
			CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
			HttpDelete delete = new HttpDelete(Global.strBaseURL+serviceURL);			
			closeableHttpResponse  = closeableHttpClient.execute(delete);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	
		return closeableHttpResponse;
		
	}
	
	//======================================================================================================
		public static String deletePet(String serviceURL) throws Exception {
			
			closeableHttpResponse = executeDeleteAPI(serviceURL);		
			String strResponseBody = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
			Assert.assertEquals(200, closeableHttpResponse.getStatusLine().getStatusCode(),"status code is NOT matched");
			JSONObject jsonObj = new JSONObject(strResponseBody);
			String message = (String) jsonObj.get("message");
			System.out.println("delete message -- " + message);
			
			return message;
			
		}

}
