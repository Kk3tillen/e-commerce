package com.br.ecommerce.demo.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.br.ecommerce.demo.exception.DadoInvalidoException;
import com.br.ecommerce.demo.exception.ProdutoJaExisteException;
import com.br.ecommerce.demo.exception.ProdutoNaoEncontradoException;

@RestControllerAdvice
public class TratadorDeErros {

	@ExceptionHandler(DadoInvalidoException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String> dadoInvalido(DadoInvalidoException e) {
		return erro(e.getMessage());
	}

	@ExceptionHandler(ProdutoNaoEncontradoException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String, String> naoEncontrado(ProdutoNaoEncontradoException e) {
		return erro(e.getMessage());
	}

	@ExceptionHandler(ProdutoJaExisteException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public Map<String, String> jaExiste(ProdutoJaExisteException e) {
		return erro(e.getMessage());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String> corpoInvalido(HttpMessageNotReadableException e) {
		return erro("Corpo da requisição inválido");
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String> parametroInvalido(MethodArgumentTypeMismatchException e) {
		return erro("Parâmetro inválido: " + e.getName());
	}

	private Map<String, String> erro(String mensagem) {
		return Map.of("erro", mensagem);
	}

}
