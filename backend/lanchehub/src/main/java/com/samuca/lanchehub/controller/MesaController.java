package com.samuca.lanchehub.controller;

import com.samuca.lanchehub.dto.MesaRequestDTO;
import com.samuca.lanchehub.dto.MesaResponseDTO;
import com.samuca.lanchehub.model.StatusMesa;
import com.samuca.lanchehub.service.MesaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mesas")
public class MesaController {
    private final MesaService service;

    public MesaController(MesaService service) {
        this.service = service;
    }

    @GetMapping("/mesa/{qrToken}")
    public MesaResponseDTO acessarMesa(
            @PathVariable String qrToken
    ) {
        return service.acessarPorQrToken(qrToken);
    }

    @PatchMapping("/{id}/status")
    public MesaResponseDTO alterarStatus(
            @PathVariable Long id,
            @RequestParam StatusMesa status
    ) {
        return service.alterarStatus(id, status);
    }

    @PostMapping
    public MesaResponseDTO criar(@Valid @RequestBody MesaRequestDTO dto){
        return service.criar(dto);
    }
    @PutMapping("/{id}")
    public MesaResponseDTO criar(@Valid @PathVariable Long id, @Valid @RequestBody MesaRequestDTO dto){
        return service.atualizar(id, dto);
    }
    @DeleteMapping("/{id}")
    public void deletar(@Valid @PathVariable Long id){
        service.deletar(id);
    }


}
