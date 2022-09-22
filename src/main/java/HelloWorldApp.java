import com.senzing.listener.communication.ConsumerType;
import com.senzing.listener.communication.MessageConsumer;
import com.senzing.listener.communication.MessageConsumerFactory;
import com.senzing.listener.service.ListenerService;
import com.senzing.listener.service.exception.ServiceExecutionException;
import com.senzing.listener.service.exception.ServiceSetupException;

public class HelloWorldApp {
  public static void main(String[] args) {
    // The required configuration, mq name and the host RabbitMQ runs on.
    // Note: the ini file path needs to be adjusted to match the Senzing G2 installation.
    String config = "{\"mqQueue\":\"hwqueue\",\"mqHost\":\"localhost\",\"mqUser\":\"user\",\"mqPassword\":\"bitnami\",\"iniFile\":\"/home/osboxes/senzing/etc/G2Modulebak.ini\"}";

    try {
      // Create the service and initialize it.
      ListenerService service = new HelloWorldService();
      // In this simple the initalization is not needed but is included for demonstration purposes.
      service.init(config);

      // Generate the queue consumer.
      MessageConsumer consumer = MessageConsumerFactory.generateMessageConsumer(ConsumerType.RABBIT_MQ, config);

      // Pass the service to the consumer, which will do the processing.
      consumer.consume(service);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
