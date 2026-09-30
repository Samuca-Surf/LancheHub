package com.samuca.lanchehub.controller;

import com.samuca.lanchehub.dto.MesaResponseDTO;
import com.samuca.lanchehub.dto.PedidoQRRequestDTO;
import com.samuca.lanchehub.dto.PedidoResponseDTO;
import com.samuca.lanchehub.service.MesaService;
import com.samuca.lanchehub.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/publico/mesas")
@Tag(name = "Mesa (público)", description = "Rotas acessadas pelo cliente via QR Code")
public class MesaPublicaController {
    private final MesaService service;
    private final PedidoService pedidoService;

    public MesaPublicaController(MesaService service, PedidoService pedidoService) {
        this.service = service;
        this.pedidoService = pedidoService;
    }

    @Operation(summary = "Acessar mesa pelo qrToken")
    @GetMapping("/{qrToken}")
    public MesaResponseDTO acessarMesa(@PathVariable String qrToken) {
        return service.acessarPorQrToken(qrToken);
    }

    @Operation(summary = "Criar pedido pela mesa do QR Code")
    @PostMapping("/{qrToken}/pedidos")
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponseDTO criar(
            @PathVariable String qrToken,
            @Valid @RequestBody PedidoQRRequestDTO dto) {
        return pedidoService.criarPorQrToken(qrToken, dto);
    }
}
