package br.com.baozistore.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.baozistore.model.Cliente;
import br.com.baozistore.model.Pedido;
import br.com.baozistore.model.Produto;
import br.com.baozistore.repository.ClienteRepository;
import br.com.baozistore.repository.PedidoRepository;
import br.com.baozistore.repository.ProdutoRepository;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoController(PedidoRepository pedidoRepository, ClienteRepository clienteRepository,
            ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    // POST /pedidos - registra um pedido de um cliente para um produto
    @PostMapping
    public ResponseEntity<Object> criar(@RequestBody PedidoRequest dados) {
        ResponseEntity<Object> erro = validar(dados);
        if (erro != null) {
            return erro;
        }
        Pedido pedido = new Pedido();
        preencher(pedido, dados);
        Pedido salvo = pedidoRepository.save(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // GET /pedidos - lista todos os pedidos
    @GetMapping
    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    // GET /pedidos/{id} - consulta um pedido pelo id
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return pedidoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /pedidos/{id} - atualiza um pedido (opcional no enunciado)
    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody PedidoRequest dados) {
        Optional<Pedido> existente = pedidoRepository.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ResponseEntity<Object> erro = validar(dados);
        if (erro != null) {
            return erro;
        }
        Pedido pedido = existente.get();
        preencher(pedido, dados);
        return ResponseEntity.ok(pedidoRepository.save(pedido));
    }

    // DELETE /pedidos/{id} - apaga um pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        if (!pedidoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pedidoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Confere se o cliente e o produto existem e se a quantidade e valida.
    // Retorna null quando esta tudo certo.
    private ResponseEntity<Object> validar(PedidoRequest dados) {
        if (dados.quantidade() == null || dados.quantidade() <= 0) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Quantidade deve ser maior que zero"));
        }
        if (dados.clienteId() == null || !clienteRepository.existsById(dados.clienteId())) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Cliente nao encontrado"));
        }
        if (dados.produtoId() == null || !produtoRepository.existsById(dados.produtoId())) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Produto nao encontrado"));
        }
        return null;
    }

    private void preencher(Pedido pedido, PedidoRequest dados) {
        Cliente cliente = clienteRepository.findById(dados.clienteId()).orElseThrow();
        Produto produto = produtoRepository.findById(dados.produtoId()).orElseThrow();
        pedido.setCliente(cliente);
        pedido.setProduto(produto);
        pedido.setQuantidade(dados.quantidade());
    }
}
