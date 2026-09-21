package com.samuca.lanchehub.service;

import com.samuca.lanchehub.dto.CategoriaResumoDTO;
import com.samuca.lanchehub.dto.IngredienteResumoDTO;
import com.samuca.lanchehub.dto.ProdutoRequestDTO;
import com.samuca.lanchehub.dto.ProdutoResponseDTO;
import com.samuca.lanchehub.exception.RecursoNaoEncontrado;
import com.samuca.lanchehub.model.Categoria;
import com.samuca.lanchehub.model.Produto;
import com.samuca.lanchehub.repository.CategoriaRepository;
import com.samuca.lanchehub.repository.IngredienteRepository;
import com.samuca.lanchehub.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {
    private final ProdutoRepository repository;
    private final CategoriaRepository categoriaRepository;
    private final IngredienteRepository ingredienteRepository;

    public ProdutoService(ProdutoRepository repository, CategoriaRepository categoriaRepository,IngredienteRepository ingredienteRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
        this.ingredienteRepository = ingredienteRepository;
    }

    private ProdutoResponseDTO toResponse(Produto produto){
        return new ProdutoResponseDTO(
                produto.getIdProduto(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getImage(),
                produto.getDisponivel(),
                new CategoriaResumoDTO(
                        produto.getCategoria().getIdCategoria(),
                        produto.getCategoria().getNome()
                ),
                produto.getIngredientes().stream().map(ingrediente -> new IngredienteResumoDTO(
                        ingrediente.getIdIngrediente(),
                        ingrediente.getNome(),
                        ingrediente.getUnidadeMedida()
                )).toList()
        );
    }

    private Produto toEntity(ProdutoRequestDTO dto){
        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco());
        produto.setImage(dto.image());
        produto.setDisponivel(dto.disponivel());

        Categoria categoria = categoriaRepository.findById(dto.categoriaId()).orElseThrow(() -> new RecursoNaoEncontrado("ID de categorai nao encontrado"));
        produto.setCategoria(categoria);

        // Se vierem IDs, busca no banco. Se não vier (ex: Coca-Cola), define uma lista vazia.
        if (dto.ingredientesIds() != null && !dto.ingredientesIds().isEmpty()) {
            produto.setIngredientes(ingredienteRepository.findAllById(dto.ingredientesIds()));
        }else {
            produto.setIngredientes(new java.util.ArrayList<>());
        }

        return produto;
    }

    private Produto pegarId(Long id){
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontrado("ID do produto nao encontrado"));
    }

    public ProdutoResponseDTO buscarPorId(Long id){
        return toResponse(pegarId(id));
    }

    public List<ProdutoResponseDTO> listar(){
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public ProdutoResponseDTO cadastrar(ProdutoRequestDTO dto){
        Produto produto = toEntity(dto);
        return toResponse(repository.save(produto));
    }

    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {
        Produto produtoExistente = pegarId(id);

        produtoExistente.setNome(dto.nome());
        produtoExistente.setDescricao(dto.descricao());
        produtoExistente.setPreco(dto.preco());
        produtoExistente.setImage(dto.image());
        produtoExistente.setDisponivel(dto.disponivel());

        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new RecursoNaoEncontrado("ID de categoria nao encontrado"));
        produtoExistente.setCategoria(categoria);

        if (dto.ingredientesIds() != null && !dto.ingredientesIds().isEmpty()) {
            produtoExistente.setIngredientes(
                    ingredienteRepository.findAllById(dto.ingredientesIds())
            );
        } else {
            produtoExistente.getIngredientes().clear(); // Limpa se vier vazio/nulo, dependendo da sua regra
        }

        Produto produtoAtualizado = repository.save(produtoExistente);
        return toResponse(produtoAtualizado);
    }

    public void deletar(Long id){
        Produto produto = pegarId(id);
        repository.delete(produto);
    }

    public ProdutoResponseDTO ativar(Long id) {
        Produto produto = pegarId(id);
        produto.setDisponivel(true);
        return toResponse(repository.save(produto));
    }

    public ProdutoResponseDTO desativar(Long id) {
        Produto produto = pegarId(id);
        produto.setDisponivel(false);
        return toResponse(repository.save(produto));
    }

}
