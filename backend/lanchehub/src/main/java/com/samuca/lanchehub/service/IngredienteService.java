package com.samuca.lanchehub.service;

import com.samuca.lanchehub.dto.IngredienteRequestDTO;
import com.samuca.lanchehub.dto.IngredienteResponseDTO;
import com.samuca.lanchehub.exception.RecursoNaoEncontrado;
import com.samuca.lanchehub.model.Ingrediente;
import com.samuca.lanchehub.repository.IngredienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredienteService {
    private final IngredienteRepository repository;
    public IngredienteService(IngredienteRepository repository) {
        this.repository = repository;
    }

    private IngredienteResponseDTO toResponse(Ingrediente ingrediente){
        return new IngredienteResponseDTO(
                ingrediente.getIdIngrediente(),
                ingrediente.getNome(),
                ingrediente.getPreco(),
                ingrediente.getDisponivel(),
                ingrediente.getUnidadeMedida()
        );
    }
    private Ingrediente toEntity(IngredienteRequestDTO dto){
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNome(dto.nome());
        ingrediente.setPreco(dto.preco());
        ingrediente.setDisponivel(dto.disponivel());
        ingrediente.setUnidadeMedida(dto.unidadeMedida());
        return ingrediente;
    }

    private Ingrediente pegarId(Long id){
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontrado("ID do ingrediente nao encontrado"));
    }

    public List<IngredienteResponseDTO> listar(){
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public IngredienteResponseDTO buscarPorId(Long id){
        return toResponse(pegarId(id));
    }

    public IngredienteResponseDTO criar(IngredienteRequestDTO dto){
        Ingrediente ingrediente = toEntity(dto);
        return toResponse(repository.save(ingrediente));
    }

    public IngredienteResponseDTO atualizar(Long id, IngredienteRequestDTO dto){
        Ingrediente ingrediente = pegarId(id);
        ingrediente.setNome(dto.nome());
        ingrediente.setPreco(dto.preco());
        ingrediente.setDisponivel(dto.disponivel());
        ingrediente.setUnidadeMedida(dto.unidadeMedida());
        return toResponse(repository.save(ingrediente));
    }

    public void deletar(Long id){
        Ingrediente ingrediente = pegarId(id);
        repository.delete(ingrediente);
    }

}
