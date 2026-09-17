package com.samuca.lanchehub.repository;

import com.samuca.lanchehub.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
