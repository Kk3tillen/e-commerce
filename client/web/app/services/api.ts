import type { EstoqueResposta, ItemVenda, Produto, VendaResposta } from './types'

const base = () => useRuntimeConfig().public.apiBase as string

const naoImplementado = (endpoint: string): never => {
  throw new Error(`Endpoint ainda não implementado: ${endpoint}`)
}

export const api = {
  async cadastrarProduto(body: { codigo: number; preco: number }): Promise<Produto> {
    return naoImplementado('')
  },

  async listarProdutos(): Promise<Produto[]> {
    return naoImplementado('')
  },

  async consultarEstoque(codigo: number): Promise<EstoqueResposta> {
    return naoImplementado('')
  },

  async entradaEstoque(codigo: number, quantidade: number): Promise<EstoqueResposta> {
    return naoImplementado('')
  },

  async saidaEstoque(codigo: number, quantidade: number): Promise<EstoqueResposta> {
    return naoImplementado('')
  },

  async venda(itens: ItemVenda[]): Promise<VendaResposta> {
    return naoImplementado('')
  },
}

void base
