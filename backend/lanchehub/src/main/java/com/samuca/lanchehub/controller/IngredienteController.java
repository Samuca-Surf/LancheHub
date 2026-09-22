package com.samuca.lanchehub.controller;

import com.samuca.lanchehub.dto.IngredienteRequestDTO;
import com.samuca.lanchehub.dto.IngredienteResponseDTO;
import com.samuca.lanchehub.service.IngredienteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredientes")
public class IngredienteController {
    private final IngredienteService service;
    public IngredienteController(IngredienteService service) {
        this.service = service;
    }
    @GetMapping
    public List<IngredienteResponseDTO> listar(){
        return service.listar();
    }
    @GetMapping("/{id}")
    public IngredienteResponseDTO buscarPorId(@Valid @PathVariable Long id){
        return service.buscarPorId(id);
    }
    @PostMapping
    public IngredienteResponseDTO cadastrar(@Valid @RequestBody IngredienteRequestDTO dto){
        return service.criar(dto);
    }
    @PutMapping("/{id}")
    public IngredienteResponseDTO atualizar(@Valid @PathVariable Long id, @Valid @RequestBody IngredienteRequestDTO dto){
        return service.criar(dto);
    }
    @DeleteMapping("/{id}")
    public void deletar(@Valid @PathVariable Long id){
        service.deletar(id);
    }
}
