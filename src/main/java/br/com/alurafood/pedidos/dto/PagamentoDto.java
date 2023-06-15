package br.com.alurafood.pedidos.dto;

import java.math.BigDecimal;
import java.util.List;

import br.com.alurafood.pedidos.model.ItemDoPedido;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagamentoDto {

        private Long id;
    private BigDecimal valor;
    private String nome;
    private String numero;
    private String expiracao;
    private String codigo;
    private StatusPagamento status;
    private Long formaDePagamentoId;
    private Long pedidoId;
    private List<ItemDoPedido> itens;
    
}
