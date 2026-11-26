package com.br.ecommerce.demo.controller.dto;

import java.math.BigDecimal;

public record ProdutoRequest(Integer codigo, BigDecimal preco) {
}
