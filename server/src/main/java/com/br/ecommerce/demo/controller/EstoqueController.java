package com.br.ecommerce.demo.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.ecommerce.demo.entity.Produto;
import com.br.ecommerce.demo.service.EstoqueService;

@RestController
@RequestMapping("/loja/produto")
public class EstoqueController {

    private final EstoqueService service;

    public EstoqueController(EstoqueService service) {
        this.service = service;
    }

    @GetMapping("/{codigo}/estoque")
    public Map<String, Object> consultar(@PathVariable int codigo) {
        return resposta(service.consultarEstoque(codigo));
    }

    private Map<String, Object> resposta(Produto p) {
        Map<String, Object> r = new LinkedHashMap<>();
        r.put("codigo", p.getCodigo());
        r.put("estoque", p.getEstoque());
        return r;
    }
}
