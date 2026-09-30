package com.samuca.lanchehub.service;

import com.samuca.lanchehub.dto.*;
import com.samuca.lanchehub.exception.ProdutoIndisponivelException;
import com.samuca.lanchehub.exception.RecursoNaoEncontrado;
import com.samuca.lanchehub.exception.RegraNegocioException;
import com.samuca.lanchehub.model.*;
import com.samuca.lanchehub.repository.MesaRepository;
import com.samuca.lanchehub.repository.PedidoRepository;
import com.samuca.lanchehub.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
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

    // ===== CRIAR (atendente) =====
    @Transactional
    public PedidoResponseDTO criar(PedidoRequestDTO dto) {
        Mesa mesa = mesaRepository.findById(dto.mesaId())
                .orElseThrow(() -> new RecursoNaoEncontrado("Mesa não encontrada"));
        return toResponse(criarPedido(mesa, dto.itens()));
    }

    // ===== CRIAR (cliente via QR) =====
    @Transactional
    public PedidoResponseDTO criarPorQrToken(String qrToken, PedidoQRRequestDTO dto) {
        Mesa mesa = mesaService.buscarMesaPorToken(qrToken);
        return toResponse(criarPedido(mesa, dto.itens()));
    }

    private Pedido criarPedido(Mesa mesa, List<ItemPedidoRequestDTO> itensDto) {
        if (mesa.getStatusMesa() == StatusMesa.INATIVA){
            throw new RegraNegocioException("Mesa indisponivel");
        }
        Pedido pedido = new Pedido();
        pedido.setMesa(mesa);
        pedido.setDataHora(LocalDateTime.now());
        pedido.setStatusPedido(StatusPedido.PENDENTE);
        pedido.setStatusPagamento(StatusPagamento.PENDENTE);
        pedido.setItens(new ArrayList<>());

        preencherItens(pedido, itensDto);

        mesaService.ocuparMesa(mesa);
        return pedidoRepository.save(pedido);
    }

    private void preencherItens(Pedido pedido, List<ItemPedidoRequestDTO> itensDto) {
        pedido.getItens().clear();
        BigDecimal total = BigDecimal.ZERO;

        for (ItemPedidoRequestDTO itemDTO : itensDto) {
            if (itemDTO.quantidade() == null || itemDTO.quantidade() <= 0) {
                throw new IllegalArgumentException("A quantidade deve ser maior que zero");
            }

            Produto produto = produtoRepository.findById(itemDTO.produtoId())
                    .orElseThrow(() -> new RecursoNaoEncontrado("Produto não encontrado"));

            if (!Boolean.TRUE.equals(produto.getDisponivel())) {
                throw new ProdutoIndisponivelException("Produto indisponível: " + produto.getNome());
            }

            ItemPedido item = new ItemPedido();
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setQuantidade(itemDTO.quantidade());
            item.setPrecoUnitario(produto.getPreco());
            pedido.getItens().add(item);

            total = total.add(produto.getPreco().multiply(BigDecimal.valueOf(itemDTO.quantidade())));
        }
        pedido.setValorTotal(total);
    }

    // ===== ATUALIZAR =====
    @Transactional
    public PedidoResponseDTO atualizar(Long id, PedidoRequestDTO dto) {
        Pedido pedido = pegarId(id);

        if (pedido.getStatusPedido() != StatusPedido.PENDENTE) {
            throw new RegraNegocioException("Só é possível alterar pedidos pendentes");
        }

        Mesa mesa = mesaRepository.findById(dto.mesaId())
                .orElseThrow(() -> new RecursoNaoEncontrado("Mesa não encontrada"));
        pedido.setMesa(mesa);
        preencherItens(pedido, dto.itens());

        return toResponse(pedidoRepository.save(pedido));
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
    // DELETAR
    // =========================

    public void deletar(Long id) {
        Pedido pedido = pegarId(id);
        pedidoRepository.delete(pedido);
    }

    public List<PedidoResponseDTO> listarParaCozinha(){
        return pedidoRepository.findByStatusPedidoInOrderByDataHoraAsc(
                List.of(StatusPedido.PENDENTE, StatusPedido.EM_PREPARACAO)
        ).stream().map(this::toResponse).toList();
    }


    //@Transactional
    public PedidoResponseDTO iniciarPreparacao(Long id){
        return mudarStatus(
                id,
                StatusPedido.EM_PREPARACAO,
                List.of(StatusPedido.PENDENTE),
                "Só pedidos pendentes podem iniciar a preparação"
        );
    }

    //@Transactional
    public PedidoResponseDTO marcarComoPronto(Long id){
        return mudarStatus(id, StatusPedido.PRONTO,
            List.of(StatusPedido.EM_PREPARACAO), "Só pedidos EM_PREPARACAO podem ser marcados como prontos"
        );
    }

    //@Transactional
    public PedidoResponseDTO cancelar(Long id){
        return mudarStatus(id, StatusPedido.CANCELADO,
                List.of(StatusPedido.PENDENTE, StatusPedido.EM_PREPARACAO), "Só pedidos PENDENTES ou EM_PREPARACAO podem ser cancelados"
        );
    }

    // valida a transição e salva; reaproveitado por todos os métodos acima
    private PedidoResponseDTO mudarStatus(Long id, StatusPedido novo, List<StatusPedido> permitidos, String mensagemErro){
        Pedido pedido = pegarId(id);
        if (!permitidos.contains(pedido.getStatusPedido())){
            throw new RegraNegocioException(mensagemErro);
        }
        pedido.setStatusPedido(novo);
        return toResponse(pedidoRepository.save(pedido));
    }

    @Transactional
    public PedidoResponseDTO entregar(Long id) {
        Pedido pedido = pegarId(id);
        if (pedido.getStatusPedido() != StatusPedido.PRONTO) {
            throw new RegraNegocioException("Só pedidos PRONTOS podem ser entregues");
        }
        pedido.setStatusPedido(StatusPedido.ENTREGUE);
        return toResponse(pedidoRepository.save(pedido));
    }

    @Transactional
    public void pagarContaMesa(Long mesaId) {
        Mesa mesa = mesaRepository.findById(mesaId)
                .orElseThrow(() -> new RecursoNaoEncontrado("Mesa não encontrada"));

        List<Pedido> pendentes = pedidoRepository.findByMesa(mesa).stream()
                .filter(p -> p.getStatusPagamento() == StatusPagamento.PENDENTE)
                .filter(p -> p.getStatusPedido() != StatusPedido.CANCELADO)
                .toList();

        pendentes.forEach(p -> p.setStatusPagamento(StatusPagamento.PAGO));
        pedidoRepository.saveAll(pendentes);
        mesaService.liberarMesa(mesa);
    }
}