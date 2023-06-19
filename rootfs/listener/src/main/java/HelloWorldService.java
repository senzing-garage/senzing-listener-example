import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.senzing.listener.service.g2.G2Service;
import com.senzing.g2.engine.G2Engine;
import com.senzing.g2.engine.Result;
import com.senzing.g2.engine.G2JNI;
import com.senzing.listener.service.ListenerService;
import com.senzing.listener.service.exception.ServiceExecutionException;
import com.senzing.listener.service.exception.ServiceSetupException;

public class HelloWorldService implements ListenerService {

  G2Service g2Service;

  @Override
  public void init(String config) throws ServiceSetupException {
    // Get the ini file name from configuration.
    String g2IniFile = null;
    try { 
      // Initalize G2.
      g2Service = new G2Service();
      g2Service.init(g2IniFile);

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
    System.out.println("Started listener");
    JSONObject configObject = new JSONObject(config);
    g2IniFile = configObject.optString("iniFile");
    } catch (JSONException e) {
      throw new ServiceSetupException(e);
    }
  }

  @Override
  public void process(String message) throws ServiceExecutionException {
  /*
    try {
      G2Engine g2engine = new G2JNI();
      String moduleName = "G2Engine for parsing";
      boolean verboseLogging = true;
      String senzingConfigJson = System.getenv("SENZING_ENGINE_CONFIGURATION_JSON");
      System.out.println("before init");
      g2engine.init(moduleName, senzingConfigJson, verboseLogging);
      System.out.println("after init");
      
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
    } catch (JSONException e) {
      throw new ServiceExecutionException(e);
    }

    //String message = "{\"DATA_SOURCE\":\"TEST\",\"RECORD_ID\":\"RECORD3\",\"AFFECTED_ENTITIES\":[{\"ENTITY_ID\":1,\"LENS_CODE\":\"DEFAULT\"}]}";*/
  }

  public void destroy() {
  // do nothing
  }
}
