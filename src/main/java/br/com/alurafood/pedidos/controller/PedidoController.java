package br.com.alurafood.pedidos.controller;

import br.com.alurafood.pedidos.dto.PedidoDto;
import br.com.alurafood.pedidos.dto.StatusDto;
import br.com.alurafood.pedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

        @Autowired
        private PedidoService service;

        @GetMapping
        public List<PedidoDto> listarTodos() {
            return this.service.obterTodos();
        }

        @GetMapping("/{id}")
        public ResponseEntity<PedidoDto> listarPorId(@PathVariable @NotNull final Long id) {
            final PedidoDto dto = this.service.obterPorId(id);

            return  ResponseEntity.ok(dto);
        }

        @PostMapping
        public ResponseEntity<PedidoDto> realizaPedido(@RequestBody @Valid final PedidoDto dto, final UriComponentsBuilder uriBuilder) {
            final PedidoDto pedidoRealizado = this.service.criarPedido(dto);

            final URI endereco = uriBuilder.path("/pedidos/{id}").buildAndExpand(pedidoRealizado.getId()).toUri();

            return ResponseEntity.created(endereco).body(pedidoRealizado);

        }

        @PutMapping("/{id}/status")
        public ResponseEntity<PedidoDto> atualizaStatus(@PathVariable final Long id, @RequestBody final StatusDto status){
           final PedidoDto dto = this.service.atualizaStatus(id, status);

            return ResponseEntity.ok(dto);
        }


        @PutMapping("/{id}/pago")
        public ResponseEntity<Void> aprovaPagamento(@PathVariable @NotNull final Long id) {
            this.service.aprovaPagamentoPedido(id);

            return ResponseEntity.ok().build();
        }

        @GetMapping("/porta")
        public String retornaPorta(@Value("${local.server.port}") final String porta){
            return String.format("Requisição respondida na pela instancia executando na porta %s", porta);
        }
}
