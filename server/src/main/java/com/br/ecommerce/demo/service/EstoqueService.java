package com.br.ecommerce.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.ecommerce.demo.entity.Produto;
import com.br.ecommerce.demo.exception.ProdutoNaoEncontradoException;
import com.br.ecommerce.demo.repository.ProdutoRepository;

@Service
public class EstoqueService {

    private final ProdutoRepository repository;

    public EstoqueService(ProdutoRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Produto consultarEstoque(int codigo) {
        return buscar(codigo);
    }

    private Produto buscar(int codigo) {
        return repository.findById(codigo)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(codigo));
    }
}
