package com.samuca.lanchehub.controller;

import com.samuca.lanchehub.dto.CategoriaRequestDTO;
import com.samuca.lanchehub.dto.CategoriaResponseDTO;
import com.samuca.lanchehub.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public List<CategoriaResponseDTO> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public CategoriaResponseDTO buscarPorId(@Valid @PathVariable Long id){
        return service.buscarPorId(id);
    }

    @PostMapping
    public CategoriaResponseDTO cadastrar(@Valid @RequestBody CategoriaRequestDTO dto){
        return service.criar(dto);
    }

    @PutMapping("/{id}")
    public CategoriaResponseDTO atualizar(@Valid @PathVariable Long id, @Valid @RequestBody CategoriaRequestDTO dto){
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@Valid @PathVariable Long id){
        service.deletar(id);
    }
}
