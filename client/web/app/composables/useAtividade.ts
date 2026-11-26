
export type TipoAtividade = 'cadastro' | 'entrada' | 'saida' | 'venda'

export interface Atividade {
  id: number
  tipo: TipoAtividade
  codigo: number
  quantidade: number
  estoque: number
  valor?: number
  venda?: number 
}

export interface Consulta {
  codigo: number
  estoque: number
}

let seq = 0

export function useAtividade() {
  const lista = useState<Atividade[]>('atividade', () => [])
  const consultas = useState<Consulta[]>('consultas', () => [])

  function registrar(a: Omit<Atividade, 'id'>) {
    lista.value.unshift({ ...a, id: ++seq })
  }

  function registrarConsulta(c: Consulta) {
    consultas.value = [c, ...consultas.value.filter((x) => x.codigo !== c.codigo)].slice(0, 8)
  }

  return { lista, consultas, registrar, registrarConsulta, novaVenda: () => ++seq }
}
