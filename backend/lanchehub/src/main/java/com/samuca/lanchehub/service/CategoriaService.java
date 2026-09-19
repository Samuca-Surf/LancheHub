package com.samuca.lanchehub.service;

import com.samuca.lanchehub.dto.CategoriaRequestDTO;
import com.samuca.lanchehub.dto.CategoriaResponseDTO;
import com.samuca.lanchehub.exception.RecursoNaoEncontrado;
import com.samuca.lanchehub.model.Categoria;
import com.samuca.lanchehub.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {
    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    private CategoriaResponseDTO toResponse(Categoria categoria){
        return new CategoriaResponseDTO(
                categoria.getIdCategoria(),
                categoria.getNome(),
                categoria.getDescricao()
        );
    }

    private Categoria toEntity(CategoriaRequestDTO dto){
        Categoria categoria = new Categoria();

        categoria.setNome(dto.nome());
        categoria.setDescricao(dto.descricao());

        return categoria;
    }

    private Categoria pegarId(Long id){
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontrado("ID de categoria nao foi encontrado"));
    }

    public CategoriaResponseDTO buscarPorId(Long id){
        return toResponse(pegarId(id));
    }

    public List<CategoriaResponseDTO> listar(){
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public CategoriaResponseDTO criar(CategoriaRequestDTO dto){
        Categoria categoria = toEntity(dto);
        return toResponse(repository.save(categoria));
    }

    public CategoriaResponseDTO atualizar(Long idCategoria, CategoriaRequestDTO dto){
        Categoria categoria = pegarId(idCategoria);
        categoria.setNome(dto.nome());
        categoria.setDescricao(dto.descricao());
        return toResponse(repository.save(categoria));
    }

    public void deletar(Long id){
        Categoria categoria = pegarId(id);
        repository.delete(categoria);
    }

}
