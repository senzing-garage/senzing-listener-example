import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.senzing.g2.engine.G2Engine;
import com.senzing.g2.engine.Result;
import com.senzing.g2.engine.G2JNI;
import com.senzing.listener.service.ListenerService;
import com.senzing.listener.service.exception.ServiceExecutionException;
import com.senzing.listener.service.exception.ServiceSetupException;

class parsingTest{
	public static void main(String args[]){
		System.out.println("Application has started.  Press ^c to stop.");
	    
		G2Engine g2engine = new G2JNI();
		String moduleName = "G2Engine for parsing";
	      boolean verboseLogging = true;
	      String senzingConfigJson = System.getenv("SENZING_ENGINE_CONFIGURATION_JSON");
	      System.out.println("before init");
	      g2engine.init(moduleName, senzingConfigJson, verboseLogging);
	      System.out.println("after init");
	      
	      
	      
	      String message = "{\"DATA_SOURCE\":\"TEST\",\"RECORD_ID\":\"RECORD3\",\"AFFECTED_ENTITIES\":[{\"ENTITY_ID\":1,\"LENS_CODE\":\"DEFAULT\"}]}";
	      
	      // Parse the Json string.
	      System.out.println(message);
	      JSONObject json = new JSONObject(message);
	      // Get the entity IDs out of the message.
	      JSONArray entities = json.getJSONArray("AFFECTED_ENTITIES");
	      System.out.println(entities);
	      
		if (entities != null) {
		for (int i = 0; i < entities.length(); i++) {
		  JSONObject entity = entities.getJSONObject(i);
		  if (entity != null) {
		    Long entityID = entity.getLong("ENTITY_ID");
		    System.out.println(entityID);

		    System.out.println("staring buffer init");
	    	    StringBuffer response = new StringBuffer();
	    	    System.out.println("getEntity test2");
	    	    int returncode = g2engine.getEntityByEntityID(entityID, response);
	    	    System.out.println(returncode);
	    	    System.out.println(response);
		  }
		}
	}
	}
	}
