<script setup lang="ts">
import { api } from '~/services/api'
import type { Produto } from '~/services/types'

useHead({ title: 'Produtos · Controle de Estoque' })

const produtos = ref<Produto[]>([])
const carregando = ref(true)
const erro = ref<{ titulo: string; detalhe: string } | null>(null)
const busca = ref('')

async function carregar() {
  carregando.value = true
  erro.value = null
  try {
    produtos.value = await api.listarProdutos()
  } catch (e) {
    erro.value = mensagemDeErro(e)
  } finally {
    carregando.value = false
  }
}

const filtrados = computed(() => {
  const q = busca.value.trim()
  return q ? produtos.value.filter((p) => String(p.codigo).includes(q)) : produtos.value
})

const totalUnidades = computed(() => produtos.value.reduce((s, p) => s + p.estoque, 0))
const valorEmEstoque = computed(() => produtos.value.reduce((s, p) => s + p.preco * Math.max(p.estoque, 0), 0))
const semEstoque = computed(() => produtos.value.filter((p) => p.estoque <= 0).length)

onMounted(carregar)
</script>

<template>
  <PageHeader crumb="Estoque / Produtos" title="Produtos cadastrados" description="Todos os produtos com preço e quantidade em estoque.">
    <NuxtLink to="/cadastrar" class="btn btn--primary"><AppIcon name="plus" :size="16" />Cadastrar produto</NuxtLink>
  </PageHeader>

  <section class="grid-auto">
    <div class="kpi">
      <span class="kpi__label">Produtos</span>
      <span class="kpi__value">{{ produtos.length }}</span>
      <span class="kpi__sub">cadastrados</span>
    </div>
    <div class="kpi">
      <span class="kpi__label">Unidades em estoque</span>
      <span class="kpi__value">{{ totalUnidades }}</span>
      <span class="kpi__sub">somando todos os produtos</span>
    </div>
    <div class="kpi">
      <span class="kpi__label">Valor em estoque</span>
      <span class="kpi__value">{{ formatBRL(valorEmEstoque) }}</span>
      <span class="kpi__sub">preço × quantidade</span>
    </div>
    <div class="kpi">
      <span class="kpi__label">Sem estoque</span>
      <span :class="['kpi__value', { 'text-neg': semEstoque > 0 }]">{{ semEstoque }}</span>
      <span class="kpi__sub">{{ semEstoque === 1 ? 'produto' : 'produtos' }}</span>
    </div>
  </section>

  <AlertBox v-if="erro" tipo="erro" :titulo="erro.titulo">
    <span>{{ erro.detalhe }} <a href="#" @click.prevent="carregar">Tentar de novo</a></span>
  </AlertBox>

  <section class="panel">
    <div class="panel__head">
      <h2>Lista de produtos</h2>
      <div class="input-prefix" style="flex:0 1 260px">
        <AppIcon name="search" />
        <input v-model="busca" class="input input--sm" inputmode="numeric" placeholder="Filtrar por código" aria-label="Filtrar por código">
      </div>
    </div>

    <p v-if="carregando" class="empty">Carregando produtos…</p>
    <div v-else-if="filtrados.length" class="table-wrap">
      <table>
        <thead>
          <tr><th>Código</th><th>Situação</th><th class="num">Preço</th><th class="num">Estoque</th><th class="num">Ações</th></tr>
        </thead>
        <tbody>
          <tr v-for="p in filtrados" :key="p.codigo">
            <td class="mono">{{ p.codigo }}</td>
            <td>
              <span :class="['tag', p.estoque > 0 ? 'tag--pos' : 'tag--neg']">
                <span class="tag__dot" />{{ p.estoque > 0 ? 'Em estoque' : 'Sem estoque' }}
              </span>
            </td>
            <td class="num">{{ formatBRL(p.preco) }}</td>
            <td class="num">{{ p.estoque }}</td>
            <td style="text-align:right;white-space:nowrap">
              <NuxtLink :to="{ path: '/entrada', query: { codigo: p.codigo } }">Entrada</NuxtLink>
              ·
              <NuxtLink :to="{ path: '/saida', query: { codigo: p.codigo } }">Saída</NuxtLink>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <p v-else-if="produtos.length" class="empty">Nenhum produto com o código “{{ busca }}”.</p>
    <p v-else class="empty">Nenhum produto cadastrado ainda. <NuxtLink to="/cadastrar">Cadastrar o primeiro</NuxtLink></p>
  </section>
</template>
