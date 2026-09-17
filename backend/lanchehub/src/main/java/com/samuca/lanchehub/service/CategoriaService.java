package com.samuca.lanchehub.service;

import com.samuca.lanchehub.exception.RecursoNaoEncontrado;
import com.samuca.lanchehub.model.Categoria;
import com.samuca.lanchehub.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {
    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    private Categoria BuscarPorIdCategoria(Long id){
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontrado("ID de categoria nao foi encontrado"));
    }

    public Categoria criar(Categoria categoria){

    }
}
