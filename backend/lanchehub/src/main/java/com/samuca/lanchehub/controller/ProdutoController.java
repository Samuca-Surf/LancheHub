package com.samuca.lanchehub.controller;

import com.samuca.lanchehub.dto.ProdutoRequestDTO;
import com.samuca.lanchehub.dto.ProdutoResponseDTO;
import com.samuca.lanchehub.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@Tag(name = "Produtos", description = "Rotas para gerenciamento de produtos")
public class ProdutoController {
    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProdutoResponseDTO> listar(){
        return service.listar();
    }
    @GetMapping("/{id}")
    public ProdutoResponseDTO buscarPorId(@Valid @PathVariable Long id){
        return service.buscarPorId(id);
    }
    @PostMapping
    public ProdutoResponseDTO cadastrar(@Valid @RequestBody ProdutoRequestDTO dto){
        return service.cadastrar(dto);
    }
    @PutMapping("/{id}")
    public ProdutoResponseDTO atualizar(@Valid @PathVariable Long id,@Valid @RequestBody ProdutoRequestDTO dto){
        return service.atualizar(id, dto);
    }
    @DeleteMapping("/{id}")
    public void deletar(@Valid @PathVariable Long id){
        service.deletar(id);
    }

    @Operation(summary = "ativar produto por id")
    @PatchMapping("/{id}/ativar")
    public ProdutoResponseDTO ativar(@PathVariable Long id) {
        return service.ativar(id);
    }

    @Operation(summary = "desativar produto por id")
    @PatchMapping("/{id}/desativar")
    public ProdutoResponseDTO desativar(@PathVariable Long id) {
        return service.desativar(id);
    }
}
