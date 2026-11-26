export interface Produto {
  codigo: number
  preco: number
  estoque: number
}

export interface EstoqueResposta {
  codigo: number
  estoque: number
}

export interface ItemVenda {
  codigo: number
  quantidade: number
}

export interface ItemVendaResposta extends ItemVenda {
  subtotal: number
  estoque: number
}

export interface VendaResposta {
  total: number
  itens: ItemVendaResposta[]
}

export class ApiError extends Error {
  constructor(public status: number, message: string) {
    super(message)
  }
}
