package com.samuca.lanchehub.controller;

import com.samuca.lanchehub.dto.MesaRequestDTO;
import com.samuca.lanchehub.dto.MesaResponseDTO;
import com.samuca.lanchehub.model.StatusMesa;
import com.samuca.lanchehub.service.MesaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mesas")
@Tag(name = "Mesas", description = "Rotas para gerenciamento de Mesas")
public class MesaController {
    private final MesaService service;

    public MesaController(MesaService service) {
        this.service = service;
    }

    @Operation(summary = "Acessar mesa pelo qrToken")
    @GetMapping("/mesa/{qrToken}")
    public MesaResponseDTO acessarMesa(
            @PathVariable String qrToken
    ) {
        return service.acessarPorQrToken(qrToken);
    }

    @Operation(summary = "Alterar status da mesa")
    @PatchMapping("/{id}/status")
    public MesaResponseDTO alterarStatus(
            @PathVariable Long id,
            @RequestParam StatusMesa status
    ) {
        return service.alterarStatus(id, status);
    }

    @Operation(summary = "criar uma nova mesa")
    @PostMapping
    public MesaResponseDTO criar(@Valid @RequestBody MesaRequestDTO dto){
        return service.criar(dto);
    }
    @Operation(summary = "atualizar uma nova mesa")
    @PutMapping("/{id}")
    public MesaResponseDTO atualizar(@Valid @PathVariable Long id, @Valid @RequestBody MesaRequestDTO dto){
        return service.atualizar(id, dto);
    }
    @Operation(summary = "deletar uma mesa")
    @DeleteMapping("/{id}")
    public void deletar(@Valid @PathVariable Long id){
        service.deletar(id);
    }


}
