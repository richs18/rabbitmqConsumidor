import com.rabbitmq.client.*;
import java.io.IOException;
import java.sql.SQLException;


public class consumidor {

    private final static String QUEUE_NAME = "MAIN_QUEUE";

    private  static java.sql.Connection cc;
    public static void main(String[] argv) throws Exception {
        conexion cone = new conexion();
        cc = cone.conectarbd();




        //conexion rabbit
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        try {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");



                        insertDatos in = new insertDatos(cc);
                        in.insertarDdatos(message);





                    System.out.println(String.format("Received  «%s»", message));
                }
            };

            channel.basicConsume(QUEUE_NAME, true, consumer);

           Thread.sleep(20000);
        } finally {
            channel.close();
            connection.close();
        }
    }
}
