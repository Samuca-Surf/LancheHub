package com.samuca.lanchehub.controller;

import com.samuca.lanchehub.dto.PedidoResponseDTO;
import com.samuca.lanchehub.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cozinha")
@Tag(name = "Cozinha", description = "Rotas usadas pela cozinha para acompanhar e atualizar pedidos")
public class CozinhaController {
    private final PedidoService service;

    public CozinhaController(PedidoService service) {
        this.service = service;
    }

    @Operation(summary = "Listar pedidos da cozinha (PENDENTE e EM_PREPARACAO, do mais antigo ao mais novo)")
    @GetMapping("/pedidos")
    public List<PedidoResponseDTO> listar(){
        return service.listarParaCozinha();
    }

    @Operation(summary = "Iniciar preparação (PENDENTE -> EM_PREPARACAO)")
    @PatchMapping("/pedidos/{id}/preparar")
    public PedidoResponseDTO preparar(@PathVariable Long id){
        return service.iniciarPreparacao(id);
    }

    @Operation(summary = "Marcar como pronto (EM_PREPARACAO -> PRONTO)")
    @PatchMapping("/pedidos/{id}/pronto")
    public PedidoResponseDTO pronto(@PathVariable Long id){
        return service.marcarComoPronto(id);
    }

    @Operation(summary = "Cancelar pedido (PENDENTE ou EM_PREPARACAO -> CANCELADO)")
    @PatchMapping("/pedidos/{id}/cancelar")
    public PedidoResponseDTO cancelar(@PathVariable Long id){
        return service.cancelar(id);
    }
}
