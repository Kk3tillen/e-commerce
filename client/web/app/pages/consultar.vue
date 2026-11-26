<script setup lang="ts">
import { api } from '~/services/api'
import type { EstoqueResposta } from '~/services/types'

useHead({ title: 'Consultar estoque · Controle de Estoque' })

const route = useRoute()
const { consultas, registrarConsulta } = useAtividade()

const codigo = ref(String(route.query.codigo ?? ''))
const carregando = ref(false)
const resultado = ref<EstoqueResposta | null>(null)
const erro = ref<{ titulo: string; detalhe: string } | null>(null)

async function consultar(valor = codigo.value) {
  codigo.value = valor
  carregando.value = true
  erro.value = null
  try {
    resultado.value = await api.consultarEstoque(parseInteiro(valor))
    registrarConsulta(resultado.value)
  } catch (e) {
    resultado.value = null
    erro.value = mensagemDeErro(e, valor)
  } finally {
    carregando.value = false
  }
}

onMounted(() => { if (codigo.value) consultar() })
</script>

<template>
  <PageHeader crumb="Estoque / Produtos" title="Consultar estoque" description="Informe o código para ver a quantidade disponível." />

  <form class="panel panel__body" style="display:flex;flex-wrap:wrap;gap:12px;align-items:flex-end" @submit.prevent="consultar()">
    <div style="flex:1 1 280px">
      <label for="codigo" class="field-label">Código do produto</label>
      <input id="codigo" v-model="codigo" class="input mono" inputmode="numeric" placeholder="ex. 123" required>
    </div>
    <button class="btn btn--primary" type="submit" :disabled="carregando">
      <AppIcon name="search" :size="16" />{{ carregando ? 'Consultando…' : 'Consultar' }}
    </button>
  </form>

  <AlertBox v-if="erro" tipo="erro" :titulo="erro.titulo">
    <span>{{ erro.detalhe }} <NuxtLink v-if="erro.titulo.includes('não encontrado')" to="/cadastrar">Cadastrar produto</NuxtLink></span>
  </AlertBox>

  <section v-if="resultado" class="panel">
    <div class="panel__head">
      <h2>Produto <span class="mono">{{ resultado.codigo }}</span></h2>
      <span :class="['tag', resultado.estoque > 0 ? 'tag--pos' : 'tag--neg']">
        <span class="tag__dot" />{{ resultado.estoque > 0 ? 'Em estoque' : 'Sem estoque' }}
      </span>
    </div>
    <div class="panel__body result">
      <div>
        <span class="hint">Quantidade em estoque</span>
        <div class="stock-big"><span class="mono">{{ resultado.estoque }}</span><span class="text-muted">unidades</span></div>
      </div>
      <div class="actions">
        <NuxtLink :to="{ path: '/entrada', query: { codigo: resultado.codigo } }" class="btn btn--secondary">Registrar entrada</NuxtLink>
        <NuxtLink :to="{ path: '/saida', query: { codigo: resultado.codigo } }" class="btn btn--secondary">Registrar saída</NuxtLink>
        <NuxtLink to="/venda" class="btn btn--secondary">Vender</NuxtLink>
      </div>
    </div>
  </section>

  <section class="panel">
    <div class="panel__head"><h2>Consultas recentes</h2><span class="hint">Nesta sessão</span></div>
    <div v-if="consultas.length" class="table-wrap">
      <table>
        <thead><tr><th>Código</th><th>Situação</th><th class="num">Estoque</th><th class="num">Ação</th></tr></thead>
        <tbody>
          <tr v-for="c in consultas" :key="c.codigo">
            <td class="mono">{{ c.codigo }}</td>
            <td><span :class="['tag', c.estoque > 0 ? 'tag--pos' : 'tag--neg']">{{ c.estoque > 0 ? 'Em estoque' : 'Sem estoque' }}</span></td>
            <td class="num">{{ c.estoque }}</td>
            <td style="text-align:right"><a href="#" @click.prevent="consultar(String(c.codigo))">Consultar de novo</a></td>
          </tr>
        </tbody>
      </table>
    </div>
    <p v-else class="empty">As consultas feitas aparecem aqui.</p>
  </section>
</template>

<style scoped>
/* Específicos de tela */
.result { display: flex; flex-wrap: wrap; gap: 24px; align-items: flex-end; justify-content: space-between; }
.stock-big { display: flex; align-items: baseline; gap: 10px; }
.stock-big .mono { font-size: 64px; font-weight: 500; line-height: 1; letter-spacing: -.03em; }
.actions { display: flex; flex-wrap: wrap; gap: 8px; }
</style>
