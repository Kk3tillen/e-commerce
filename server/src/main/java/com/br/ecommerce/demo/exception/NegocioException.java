package com.br.ecommerce.demo.exception;

public abstract class NegocioException extends RuntimeException {

	protected NegocioException(String mensagem) {
		super(mensagem);
	}

}
