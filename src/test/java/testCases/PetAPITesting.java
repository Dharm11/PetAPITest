package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import utility.APIHelper;
import utility.Global;

public class PetAPITesting {

 // Note : I have not used the extent report /sernity report in API testing as i have used the same in 
	//automation of Web FE automation (exercise -2)
	
  static long petID;
  static String petMsgID;
  static String petDeleteID;
  

@Test
  public void verifyAPI() {
	try {
	    petID = APIHelper.getAvailablePetID(Global.postavailablePetURL, "JSONPayload","json");
	    Assert.assertTrue(!String.valueOf(petID).isEmpty(), "pet is NOT available to the store"); //used hard assert
	    System.out.println("added new available pet to the store");
	    
	    petMsgID = APIHelper.updatePetStatus(Global.postavailablePetURL+"/"+petID, "UpdatePetStatus","urlencoded");
	    Assert.assertTrue(!petMsgID.isEmpty(), "pet status is NOT updated to sold"); //used hard assert
	    System.out.println("pet status is updated to sold");
	    
	    petDeleteID = APIHelper.deletePet(Global.postavailablePetURL+"/"+petID);
	    Assert.assertTrue(!petDeleteID.isEmpty(), "pet ID is NOT deleted"); //used hard assert
	    System.out.println("pet ID is deleted");
	    
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	

  }
}
