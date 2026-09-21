package com.samuca.lanchehub.service;

import com.samuca.lanchehub.dto.ItemPedidoRequestDTO;
import com.samuca.lanchehub.dto.ItemPedidoResponseDTO;
import com.samuca.lanchehub.dto.PedidoRequestDTO;
import com.samuca.lanchehub.dto.PedidoResponseDTO;
import com.samuca.lanchehub.exception.ProdutoIndisponivelException;
import com.samuca.lanchehub.exception.RecursoNaoEncontrado;
import com.samuca.lanchehub.model.*;
import com.samuca.lanchehub.repository.MesaRepository;
import com.samuca.lanchehub.repository.PedidoRepository;
import com.samuca.lanchehub.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final MesaRepository mesaRepository;
    private final MesaService mesaService;


    public PedidoService(
            PedidoRepository pedidoRepository,
            ProdutoRepository produtoRepository,
            MesaRepository mesaRepository,
            MesaService mesaService
    ) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.mesaRepository = mesaRepository;
        this.mesaService = mesaService;
    }

    private Pedido pegarId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontrado("Pedido não encontrado")
                );
    }

    public PedidoResponseDTO buscarPorId(Long id){
        return toResponse(pegarId(id));
    }

    public List<PedidoResponseDTO> listar(){
        return pedidoRepository.findAll().stream().map(this::toResponse).toList();
    }

    // =========================
    // CRIAR
    // =========================

    public PedidoResponseDTO criar(PedidoRequestDTO dto) {

        var mesa = mesaRepository.findById(dto.mesaId())
                .orElseThrow(() ->
                        new RecursoNaoEncontrado("Mesa não encontrada")
                );

        Pedido pedido = new Pedido();

        pedido.setMesa(mesa);
        pedido.setDataHora(LocalDateTime.now());
        pedido.setStatusPedido(StatusPedido.PENDENTE);
        pedido.setItens(new ArrayList<>());

        BigDecimal valorTotal = BigDecimal.ZERO;

        for (ItemPedidoRequestDTO itemDTO : dto.itens()) {

            if (itemDTO.quantidade() == null || itemDTO.quantidade() <= 0) {
                throw new IllegalArgumentException(
                        "A quantidade deve ser maior que zero"
                );
            }

            Produto produto = produtoRepository.findById(itemDTO.produtoId())
                    .orElseThrow(() ->
                            new RecursoNaoEncontrado("Produto não encontrado")
                    );

            if (!Boolean.TRUE.equals(produto.getDisponivel())) {
                throw new ProdutoIndisponivelException(
                        "Produto indisponível: " + produto.getNome()
                );
            }

            ItemPedido item = new ItemPedido();

            item.setProduto(produto);
            item.setQuantidade(itemDTO.quantidade());
            item.setPrecoUnitario(produto.getPreco());
            item.setPedido(pedido);

            pedido.getItens().add(item);

            BigDecimal subtotal = produto.getPreco()
                    .multiply(BigDecimal.valueOf(itemDTO.quantidade()));

            valorTotal = valorTotal.add(subtotal);
        }

        pedido.setValorTotal(valorTotal);

        mesaService.ocuparMesa(mesa);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        return toResponse(pedidoSalvo);
    }

    // =========================
    // TO RESPONSE
    // =========================

    private PedidoResponseDTO toResponse(Pedido pedido) {
        List<ItemPedidoResponseDTO> itens = pedido.getItens()
                .stream()
                .map(this::itemToResponse)
                .toList();
        return new PedidoResponseDTO(
                pedido.getIdPedido(),
                pedido.getDataHora(),
                pedido.getStatusPedido(),
                pedido.getStatusPagamento(),
                pedido.getMesa().getIdMesa(),
                pedido.getValorTotal(),
                itens
        );
    }

    private ItemPedidoResponseDTO itemToResponse(ItemPedido item) {

        BigDecimal subtotal = item.getPrecoUnitario()
                .multiply(BigDecimal.valueOf(item.getQuantidade()));

        return new ItemPedidoResponseDTO(
                item.getIdItemPedido(),
                item.getProduto().getIdProduto(),
                item.getProduto().getNome(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
                subtotal
        );
    }

    // =========================
    // TO ENTITY
    // =========================

    private Pedido toEntity(PedidoRequestDTO dto) {
        var mesa = mesaRepository.findById(dto.mesaId())
                .orElseThrow(() ->
                        new RecursoNaoEncontrado("Mesa não encontrada")
                );
        Pedido pedido = new Pedido();
        pedido.setMesa(mesa);
        pedido.setDataHora(LocalDateTime.now());
        pedido.setStatusPedido(StatusPedido.PENDENTE);
        pedido.setStatusPagamento(StatusPagamento.PENDENTE);
        pedido.setItens(new ArrayList<>());

        BigDecimal valorTotal = BigDecimal.ZERO;

        for (ItemPedidoRequestDTO itemDTO : dto.itens()) {

            if (itemDTO.quantidade() == null || itemDTO.quantidade() <= 0) {
                throw new IllegalArgumentException(
                        "A quantidade deve ser maior que zero"
                );
            }

            Produto produto = produtoRepository.findById(itemDTO.produtoId())
                    .orElseThrow(() ->
                            new RecursoNaoEncontrado("Produto não encontrado")
                    );

            if (!Boolean.TRUE.equals(produto.getDisponivel())) {
                throw new ProdutoIndisponivelException(
                        "Produto indisponível: " + produto.getNome()
                );
            }

            ItemPedido item = new ItemPedido();

            item.setPedido(pedido);
            item.setProduto(produto);
            item.setQuantidade(itemDTO.quantidade());
            item.setPrecoUnitario(produto.getPreco());

            pedido.getItens().add(item);

            BigDecimal subtotal = produto.getPreco()
                    .multiply(BigDecimal.valueOf(itemDTO.quantidade()));

            valorTotal = valorTotal.add(subtotal);
        }

        pedido.setValorTotal(valorTotal);

        return pedido;
    }

    // =========================
    // ATUALIZAR
    // =========================

    public PedidoResponseDTO atualizar(Long id, PedidoRequestDTO dto) {

        Pedido pedido = pegarId(id);

        var mesa = mesaRepository.findById(dto.mesaId())
                .orElseThrow(() ->
                        new RecursoNaoEncontrado("Mesa não encontrada")
                );

        pedido.setMesa(mesa);

        // Remove os itens antigos
        pedido.getItens().clear();

        BigDecimal valorTotal = BigDecimal.ZERO;

        for (ItemPedidoRequestDTO itemDTO : dto.itens()) {

            if (itemDTO.quantidade() == null || itemDTO.quantidade() <= 0) {
                throw new IllegalArgumentException(
                        "A quantidade deve ser maior que zero"
                );
            }

            Produto produto = produtoRepository.findById(itemDTO.produtoId())
                    .orElseThrow(() ->
                            new RecursoNaoEncontrado("Produto não encontrado")
                    );

            if (!Boolean.TRUE.equals(produto.getDisponivel())) {
                throw new IllegalStateException(
                        "Produto indisponível: " + produto.getNome()
                );
            }

            ItemPedido item = new ItemPedido();

            item.setPedido(pedido);
            item.setProduto(produto);
            item.setQuantidade(itemDTO.quantidade());
            item.setPrecoUnitario(produto.getPreco());

            pedido.getItens().add(item);

            BigDecimal subtotal = produto.getPreco()
                    .multiply(BigDecimal.valueOf(itemDTO.quantidade()));

            valorTotal = valorTotal.add(subtotal);
        }

        pedido.setValorTotal(valorTotal);

        Pedido pedidoAtualizado = pedidoRepository.save(pedido);

        return toResponse(pedidoAtualizado);
    }

    // =========================
    // DELETAR
    // =========================

    public void deletar(Long id) {
        Pedido pedido = pegarId(id);
        pedidoRepository.delete(pedido);
    }

    public PedidoResponseDTO entregar(Long id) {
        Pedido pedido = pegarId(id);
        pedido.setStatusPedido(StatusPedido.ENTREGUE);
        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        return toResponse(pedidoSalvo);
    }

    public void pagarContaMesa(Long mesaId) {
        Mesa mesa = mesaRepository.findById(mesaId).orElseThrow(() -> new RecursoNaoEncontrado("Mesa não encontrada"));
        List<Pedido> pedidos = pedidoRepository.findByMesa(mesa);

        for (Pedido pedido : pedidos) {
            pedido.setStatusPagamento(StatusPagamento.PAGO);
        }

        pedidoRepository.saveAll(pedidos);
        mesaService.liberarMesa(mesa);
    }
}