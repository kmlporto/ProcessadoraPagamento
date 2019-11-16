package bandeira;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import models.MessageRespRequest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Banco {

    public static final String QUEUE_MASTER = "MASTER";
    public static final String QUEUE_VISA = "VISA";
    public static final String MASTER = "MASTER";
    public static final String VISA = "VISA";
    public static void main(String[] args) throws IOException, TimeoutException {
        Gson gson = new Gson();

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        Connection connection = connectionFactory.newConnection();

        //COMO PRODUCER
        String bandeira = "VISA";
        MessageRespRequest objectRequest = new MessageRespRequest("00001", "2321", "6522.2121.2143", 1000f, 2121);
        Channel requestChannel = connection.createChannel();
        if (bandeira.equals(VISA)){
            requestChannel.queueDeclare(QUEUE_VISA, true, false, false, null);
            requestChannel.basicPublish("", QUEUE_VISA, null, gson.toJson(objectRequest).getBytes());
        }else{
            requestChannel.queueDeclare(QUEUE_MASTER, true, false, false, null);
            requestChannel.basicPublish("", QUEUE_MASTER, null, gson.toJson(objectRequest).getBytes());
        }

    }
}
