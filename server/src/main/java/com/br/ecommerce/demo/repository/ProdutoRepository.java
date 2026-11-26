package com.br.ecommerce.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.ecommerce.demo.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
