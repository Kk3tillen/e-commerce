package com.br.ecommerce.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.ecommerce.demo.controller.dto.ProdutoRequest;
import com.br.ecommerce.demo.controller.dto.ProdutoResponse;
import com.br.ecommerce.demo.service.LojaService;

@RestController
@RequestMapping("/loja")
public class LojaController {

	private final LojaService service;

	public LojaController(LojaService service) {
		this.service = service;
	}

	@PostMapping("/produto")
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoResponse cadastrar(@RequestBody ProdutoRequest request) {
		return ProdutoResponse.de(service.cadastrarProduto(request.codigo(), request.preco()));
	}

	@GetMapping("/produto")
	public List<ProdutoResponse> listar() {
		return service.listarProdutos().stream().map(ProdutoResponse::de).toList();
	}

}
