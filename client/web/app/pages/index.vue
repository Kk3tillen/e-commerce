<script setup lang="ts">
import type { TipoAtividade } from '~/composables/useAtividade'

useHead({ title: 'Visão geral · Controle de Estoque' })

const { lista } = useAtividade()
const busca = ref('')

function consultar() {
  if (busca.value.trim()) navigateTo({ path: '/consultar', query: { codigo: busca.value.trim() } })
}

const soma = (tipo: TipoAtividade, campo: 'quantidade' | 'valor') =>
  lista.value.filter((a) => a.tipo === tipo).reduce((s, a) => s + (a[campo] ?? 0), 0)
const conta = (tipo: TipoAtividade) => lista.value.filter((a) => a.tipo === tipo).length

const nVendas = computed(() => new Set(lista.value.filter((a) => a.tipo === 'venda').map((a) => a.venda)).size)

const rotulo: Record<TipoAtividade, string> = { cadastro: 'Cadastro', entrada: 'Entrada', saida: 'Saída', venda: 'Venda' }
const tagClasse: Record<TipoAtividade, string> = {
  cadastro: 'tag--outline', entrada: 'tag--pos', saida: 'tag--neg', venda: 'tag--neutral',
}
const plural = (n: number, um: string, varios: string) => `${n} ${n === 1 ? um : varios}`
const comSinal = (n: number, s: string) => (n === 0 ? '0' : `${s}${n}`)
const sinal = (tipo: TipoAtividade, q: number) => (tipo === 'entrada' ? `+${q}` : tipo === 'cadastro' ? '0' : `−${q}`)
</script>

<template>
  <PageHeader crumb="Estoque" title="Visão geral">
    <form style="display:flex;gap:8px;flex:0 1 440px" @submit.prevent="consultar">
      <div class="input-prefix" style="flex:1">
        <AppIcon name="search" />
        <input v-model="busca" class="input" inputmode="numeric" placeholder="Consultar estoque por código" aria-label="Código do produto">
      </div>
      <button class="btn btn--primary" type="submit">Consultar</button>
    </form>
  </PageHeader>

  <section class="grid-auto">
    <div class="kpi">
      <span class="kpi__label">Faturamento na sessão</span>
      <span class="kpi__value">{{ formatBRL(soma('venda', 'valor')) }}</span>
      <span class="kpi__sub">{{ plural(nVendas, 'venda', 'vendas') }} · {{ plural(soma('venda', 'quantidade'), 'unidade', 'unidades') }}</span>
    </div>
    <div class="kpi">
      <span class="kpi__label">Entradas</span>
      <span class="kpi__value text-pos">{{ comSinal(soma('entrada', 'quantidade'), '+') }} un.</span>
      <span class="kpi__sub">{{ plural(conta('entrada'), 'movimentação', 'movimentações') }}</span>
    </div>
    <div class="kpi">
      <span class="kpi__label">Saídas (baixas)</span>
      <span class="kpi__value text-neg">{{ comSinal(soma('saida', 'quantidade'), '−') }} un.</span>
      <span class="kpi__sub">{{ plural(conta('saida'), 'movimentação', 'movimentações') }}</span>
    </div>
    <div class="kpi">
      <span class="kpi__label">Produtos cadastrados</span>
      <span class="kpi__value">{{ conta('cadastro') }}</span>
      <span class="kpi__sub">nesta sessão</span>
    </div>
  </section>

  <section class="grid-auto">
    <NuxtLink to="/cadastrar" class="quick"><span class="quick__icon"><AppIcon name="plus" /></span>Cadastrar produto</NuxtLink>
    <NuxtLink to="/entrada" class="quick"><span class="quick__icon quick__icon--pos"><AppIcon name="in" /></span>Entrada de estoque</NuxtLink>
    <NuxtLink to="/saida" class="quick"><span class="quick__icon quick__icon--neg"><AppIcon name="out" /></span>Saída de estoque</NuxtLink>
    <NuxtLink to="/venda" class="quick quick--primary"><span class="quick__icon"><AppIcon name="cart" /></span>Registrar venda</NuxtLink>
  </section>

  <section class="panel">
    <div class="panel__head">
      <h2>Movimentações recentes</h2>
      <span class="hint">Mais recentes primeiro</span>
    </div>
    <div v-if="lista.length" class="table-wrap">
      <table>
        <thead>
          <tr><th>Tipo</th><th>Produto</th><th class="num">Quantidade</th><th class="num">Estoque após</th><th class="num">Valor</th></tr>
        </thead>
        <tbody>
          <tr v-for="a in lista" :key="a.id">
            <td><span :class="['tag', tagClasse[a.tipo]]">{{ rotulo[a.tipo] }}</span></td>
            <td class="mono">{{ a.codigo }}</td>
            <td :class="['num', { 'text-pos': a.tipo === 'entrada', 'text-neg': a.tipo === 'saida' || a.tipo === 'venda' }]">{{ sinal(a.tipo, a.quantidade) }}</td>
            <td class="num">{{ a.estoque }}</td>
            <td class="num" :class="{ 'text-muted': a.valor == null }">{{ a.valor == null ? '—' : formatBRL(a.valor) }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <p v-else class="empty">Nenhuma movimentação ainda. Comece cadastrando um produto ou registrando uma venda.</p>
  </section>
</template>

<style scoped>
/* Indicadores */
.quick {
  display: flex; align-items: center; gap: 12px; padding: 14px 16px;
  background: var(--surface); border: 1px solid var(--border); border-radius: 14px;
  color: var(--text); text-decoration: none; font-size: 14px; font-weight: 500;
}
.quick:hover { border-color: var(--bar); background: var(--surface-3); }
.quick__icon {
  width: 34px; height: 34px; border-radius: 8px;
  display: grid; place-items: center; background: var(--surface-2); flex: none;
}
.quick__icon--pos { background: var(--pos-bg); color: var(--pos); }
.quick__icon--neg { background: var(--neg-bg); color: var(--neg); }
.quick--primary { background: var(--lime); border-color: var(--lime); color: var(--on-lime); font-weight: 600; }
.quick--primary:hover { background: var(--lime-hover); border-color: var(--lime-hover); }
.quick--primary .quick__icon { background: var(--on-lime); color: var(--lime); }
</style>
