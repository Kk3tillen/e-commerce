package com.br.ecommerce.demo.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.ecommerce.demo.entity.Produto;
import com.br.ecommerce.demo.exception.DadoInvalidoException;
import com.br.ecommerce.demo.exception.ProdutoJaExisteException;
import com.br.ecommerce.demo.repository.ProdutoRepository;

@Service
public class LojaService {

	private final ProdutoRepository repository;

	public LojaService(ProdutoRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public synchronized Produto cadastrarProduto(Integer codigo, BigDecimal preco) {
		if (codigo == null) {
			throw new DadoInvalidoException("O código é obrigatório");
		}
		if (preco == null || preco.signum() <= 0) {
			throw new DadoInvalidoException("O preço deve ser maior que zero");
		}
		if (repository.existsById(codigo)) {
			throw new ProdutoJaExisteException(codigo);
		}
		return repository.save(new Produto(codigo, preco));
	}

	@Transactional(readOnly = true)
	public List<Produto> listarProdutos() {
		return repository.findAll(Sort.by("codigo"));
	}

}
