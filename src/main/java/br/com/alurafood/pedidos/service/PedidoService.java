package br.com.alurafood.pedidos.service;

import br.com.alurafood.pedidos.dto.PedidoDto;
import br.com.alurafood.pedidos.dto.StatusDto;
import br.com.alurafood.pedidos.model.Pedido;
import br.com.alurafood.pedidos.model.Status;
import br.com.alurafood.pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private final ModelMapper modelMapper;


    public List<PedidoDto> obterTodos() {
        return this.repository.findAll().stream()
                .map(p -> this.modelMapper.map(p, PedidoDto.class))
                .collect(Collectors.toList());
    }

    public PedidoDto obterPorId(final Long id) {
        final Pedido pedido = this.repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return this.modelMapper.map(pedido, PedidoDto.class);
    }

    public PedidoDto criarPedido(final PedidoDto dto) {
        final Pedido pedido = this.modelMapper.map(dto, Pedido.class);

        pedido.setDataHora(LocalDateTime.now());
        pedido.setStatus(Status.REALIZADO);
        pedido.getItens().forEach(item -> item.setPedido(pedido));
        final Pedido salvo = this.repository.save(pedido);

        return this.modelMapper.map(pedido, PedidoDto.class);
    }

    public PedidoDto atualizaStatus(final Long id, final StatusDto dto) {

        final Pedido pedido = this.repository.porIdComItens(id);

        if (null == pedido) {
            throw new EntityNotFoundException();
        }

        pedido.setStatus(dto.getStatus());
        this.repository.atualizaStatus(dto.getStatus(), pedido);
        return this.modelMapper.map(pedido, PedidoDto.class);
    }

    public void aprovaPagamentoPedido(final Long id) {

        final Pedido pedido = this.repository.porIdComItens(id);

        if (null == pedido) {
            throw new EntityNotFoundException();
        }

        pedido.setStatus(Status.PAGO);
        this.repository.atualizaStatus(Status.PAGO, pedido);
    }
}
