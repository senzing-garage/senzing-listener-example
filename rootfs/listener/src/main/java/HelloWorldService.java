import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.senzing.listener.service.g2.G2Service;
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
      JSONObject configObject = new JSONObject(config);
      g2IniFile = configObject.optString("iniFile");
    } catch (JSONException e) {
      throw new ServiceSetupException(e);
    }
    // Initalize G2.
    g2Service = new G2Service();
    g2Service.init(g2IniFile);

    System.out.println("Application has started.  Press ^c to stop.");
  }

  @Override
  public void process(String message) throws ServiceExecutionException {
    try {
      // Parse the Json string.
      JSONObject json = new JSONObject(message);
      // Get the entity IDs out of the message.
      JSONArray entities = json.getJSONArray("AFFECTED_ENTITIES");
      if (entities != null) {
        for (int i = 0; i < entities.length(); i++) {
          JSONObject entity = entities.getJSONObject(i);
          if (entity != null) {
            Long entityID = entity.getLong("ENTITY_ID");
            String entityData = g2Service.getEntity(entityID, false, false);
            System.out.println("G2 entity:");
            G2EntitySearchData parser = new G2EntitySearchData();
            parser.ParseJson(entityData);
            System.out.println(entityData);
          }
        }
      }
    } catch (JSONException e) {
      throw new ServiceExecutionException(e);
    }

  }
  
  public void destroy() {
  // do nothing
}
}
