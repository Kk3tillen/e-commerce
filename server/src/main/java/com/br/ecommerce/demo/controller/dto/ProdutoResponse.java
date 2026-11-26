package com.br.ecommerce.demo.controller.dto;

import java.math.BigDecimal;

import com.br.ecommerce.demo.entity.Produto;

public record ProdutoResponse(Integer codigo, BigDecimal preco, int estoque) {

	public static ProdutoResponse de(Produto produto) {
		return new ProdutoResponse(produto.getCodigo(), produto.getPreco(), produto.getEstoque());
	}

}
