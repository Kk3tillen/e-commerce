package com.br.ecommerce.demo.exception;

public class ProdutoNaoEncontradoException extends NegocioException {

	public ProdutoNaoEncontradoException(Integer codigo) {
		super("Produto não encontrado: " + codigo);
	}

}
