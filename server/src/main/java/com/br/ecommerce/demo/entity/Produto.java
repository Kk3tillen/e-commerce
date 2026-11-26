package com.br.ecommerce.demo.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Produto {

	@Id
	private Integer codigo;

	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal preco;

	@Column(nullable = false)
	private int estoque;

	protected Produto() {
	}

	public Produto(int codigo, BigDecimal preco) {
		this.codigo = codigo;
		this.preco = preco;
		this.estoque = 0;
	}

	public int getCodigo() {
		return codigo;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public int getEstoque() {
		return estoque;
	}

	public void adicionarEstoque(int quantidade) {
		this.estoque += quantidade;
	}

	public void removerEstoque(int quantidade) {
		this.estoque -= quantidade;
	}

}
