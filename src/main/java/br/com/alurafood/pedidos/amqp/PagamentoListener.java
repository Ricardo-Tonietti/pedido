package br.com.alurafood.pedidos.amqp;

import br.com.alurafood.pedidos.dto.PagamentoDto;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PagamentoListener {

    @RabbitListener(queues = "pagamentos.detalhe-pedido")
    public void recebeMensagem(final PagamentoDto pagamento){

        final String mensagem = """
                Nome: %s
                Dados do pagamento: %s
                Número do pedido: %s
                Valor R$: %s
                Status: %s
                """.formatted(
                        pagamento.getNome(),
                pagamento.getId(),
                pagamento.getPedidoId(),
                pagamento.getValor(),
                pagamento.getStatus());
        System.out.println("Recebi a mensagem" + mensagem);
    }
    
}
