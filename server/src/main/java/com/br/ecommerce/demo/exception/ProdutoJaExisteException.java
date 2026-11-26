package com.br.ecommerce.demo.exception;

public class ProdutoJaExisteException extends NegocioException {

	public ProdutoJaExisteException(Integer codigo) {
		super("Já existe produto com o código " + codigo);
	}

}
