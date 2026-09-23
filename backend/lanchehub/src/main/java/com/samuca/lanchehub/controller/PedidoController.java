package com.samuca.lanchehub.controller;

import com.samuca.lanchehub.dto.PedidoRequestDTO;
import com.samuca.lanchehub.dto.PedidoResponseDTO;
import com.samuca.lanchehub.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Rotas para gerenciamento de pedidos")
public class PedidoController {
    private final PedidoService service;
    public PedidoController(PedidoService service) {
        this.service = service;
    }
    @GetMapping
    public List<PedidoResponseDTO> listar(){
        return service.listar();
    }
    @GetMapping("/{id}")
    public List<PedidoResponseDTO> buscarPorId(@Valid @PathVariable Long id){
        return service.listar();
    }
    @PostMapping
    public PedidoResponseDTO criar(@Valid @RequestBody PedidoRequestDTO dto){
        return service.criar(dto);
    }
    @PutMapping("/{id}")
    public PedidoResponseDTO atualizar(@Valid @PathVariable Long id, @Valid @RequestBody PedidoRequestDTO dto){
        return service.atualizar(id, dto);
    }
    @DeleteMapping("/{id}")
    public void deletar(@Valid @PathVariable Long id){
        service.deletar(id);
    }

    @Operation(summary = "alterar status de pedido para entregue")
    @PatchMapping("/{id}/entregar")
    public PedidoResponseDTO entregar(@PathVariable Long id) {
        return service.entregar(id);
    }

    @Operation(summary = "Pagar conta da mesa com o id da mesa")
    @PatchMapping("/mesa/{mesaId}/pagar")
    public ResponseEntity<Void> pagarContaMesa(
            @PathVariable Long mesaId)
    {
        service.pagarContaMesa(mesaId);
        return ResponseEntity.noContent().build();
    }
}
