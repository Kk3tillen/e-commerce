
const brl = new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' })

export const formatBRL = (valor: number) => brl.format(valor)

export function parseInteiro(texto: string | number): number {
  const s = String(texto).trim()
  return /^-?\d+$/.test(s) ? Number(s) : Number.NaN
}

export function parseDecimal(texto: string | number): number {
  const s = String(texto).trim().replace(',', '.')
  return /^\d+(\.\d{1,2})?$/.test(s) ? Number(s) : Number.NaN
}

export function mensagemDeErro(e: unknown, codigo?: number | string): { titulo: string; detalhe: string } {
  const status = (e as { status?: number; statusCode?: number })?.status
    ?? (e as { statusCode?: number })?.statusCode
  switch (status) {
    case 400:
      return { titulo: 'Dados inválidos', detalhe: 'Confira os campos: códigos e quantidades devem ser números inteiros.' }
    case 404:
      return { titulo: `Produto ${codigo ?? ''} não encontrado`.replace('  ', ' '), detalhe: 'Confira o código ou cadastre o produto.' }
    case 409:
      return { titulo: 'Código já cadastrado', detalhe: `Já existe um produto com o código ${codigo}.` }
    default:
      return { titulo: 'Não foi possível concluir', detalhe: 'Verifique a conexão com a API e tente novamente.' }
  }
}
