package bandeira;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import models.MessageRespRequest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Master {
    public static final String QUEUE_PAG_APROV = "APROVADOS";
    public static final String QUEUE_PAG_REPROV = "REPROVADOS";
    public static final String QUEUE_REQUEST_PAG = "MASTER";


    public static void main(String[] args) throws IOException, TimeoutException {
        Gson gson = new Gson();

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        Connection connection = connectionFactory.newConnection();

        //COMO CONSUMER
        Channel requestChannel = connection.createChannel();
        requestChannel.queueDeclare(QUEUE_REQUEST_PAG, true, false, false, null);
        MessageRespRequest objectRequest = new MessageRespRequest();


        DeliverCallback callback = (consumerTag, delivery) ->{
            String messageRequest = new String(delivery.getBody());
            MessageRespRequest local = gson.fromJson(messageRequest, MessageRespRequest.class);
            objectRequest.setObjectMessage(local);
            System.out.println("Recebi a mensagem: " + local);
        };
        requestChannel.basicConsume(QUEUE_REQUEST_PAG, true, callback, consumerTag ->{});


        //COMO PRODUCER DE APROVADOS E REPROVADOS
        boolean approved = false;
        MessageRespRequest messageResponse = new MessageRespRequest();
        messageResponse.setObjectMessage(objectRequest);
        messageResponse.setAprovado(approved);
        Channel responseChannel = connection.createChannel();
        if (approved) {
            responseChannel.queueDeclare(QUEUE_PAG_APROV, true, false, false, null);
            responseChannel.basicPublish("", QUEUE_PAG_APROV, null, gson.toJson(messageResponse).getBytes());
        }else{
            responseChannel.queueDeclare(QUEUE_PAG_REPROV, true, false, false, null);
            responseChannel.basicPublish("", QUEUE_PAG_REPROV, null, gson.toJson(messageResponse).getBytes());
        }
    }

}
