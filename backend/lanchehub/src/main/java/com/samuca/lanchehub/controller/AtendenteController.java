package com.samuca.lanchehub.controller;

import com.samuca.lanchehub.dto.AtendenteMesaResponseDTO;
import com.samuca.lanchehub.dto.PedidoAtendenteRequestDTO;
import com.samuca.lanchehub.dto.PedidoResponseDTO;
import com.samuca.lanchehub.model.StatusPedido;
import com.samuca.lanchehub.service.MesaService;
import com.samuca.lanchehub.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atendente")
@Tag(name = "Atendente", description = "Rotas usadas pelo garçom/atendente")
public class AtendenteController {
    private final PedidoService pedidoService;
    private final MesaService mesaService;

    public AtendenteController(PedidoService pedidoService, MesaService mesaService) {
        this.pedidoService = pedidoService;
        this.mesaService = mesaService;
    }

    @Operation(summary = "Listar mesas com status (sem qrToken)")
    @GetMapping("/mesas")
    public List<AtendenteMesaResponseDTO> listarMesas() {
        return mesaService.listarParaAtendente();
    }

    @Operation(summary = "Listar pedidos. Sem filtro: pedidos ativos (não pagos e não cancelados). Com ?status=: filtra por status")
    @GetMapping("/pedidos")
    public List<PedidoResponseDTO> listarPedidos(@RequestParam(required = false) StatusPedido status) {
        return pedidoService.listarParaAtendente(status);
    }

    @Operation(summary = "Criar pedido para qualquer mesa (mesaId vem da URL)")
    @PostMapping("/mesas/{mesaId}/pedidos")
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponseDTO criarPedido(
            @PathVariable Long mesaId,
            @Valid @RequestBody PedidoAtendenteRequestDTO dto) {
        return pedidoService.criarParaMesa(mesaId, dto);
    }

    @Operation(summary = "Confirmar entrega (PRONTO -> ENTREGUE)")
    @PatchMapping("/pedidos/{id}/entregar")
    public PedidoResponseDTO entregar(@PathVariable Long id) {
        return pedidoService.entregar(id);
    }
}
