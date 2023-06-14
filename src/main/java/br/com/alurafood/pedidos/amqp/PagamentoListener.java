package br.com.alurafood.pedidos.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.SortedMap;

@Component
public class PagamentoListener {

    @RabbitListener(queues = "pagamento.conclido")
    public void recebeMensagem(Message message){
        System.out.println("Recebi a mensagem " + message.toString());
    }
}
