<script setup lang="ts">
import { api } from '~/services/api'

/** Formulário compartilhado pelas telas de Entrada e Saída. */
const props = defineProps<{ tipo: 'entrada' | 'saida' }>()

const route = useRoute()
const { registrar } = useAtividade()

const ehEntrada = computed(() => props.tipo === 'entrada')
const codigo = ref(String(route.query.codigo ?? ''))
const quantidade = ref('1')
const atual = ref<number | null>(null)
const enviando = ref(false)
const sucesso = ref<{ codigo: number; estoque: number } | null>(null)
const erro = ref<{ titulo: string; detalhe: string } | null>(null)

const qtd = computed(() => parseInteiro(quantidade.value))
const depois = computed(() => {
  if (atual.value == null || Number.isNaN(qtd.value)) return null
  return ehEntrada.value ? atual.value + qtd.value : atual.value - qtd.value
})
const barra = computed(() => {
  if (atual.value == null || depois.value == null) return null
  const total = Math.max(atual.value, depois.value, 1)
  const base = Math.min(atual.value, depois.value)
  return { base: (base / total) * 100, delta: (Math.abs(qtd.value) / total) * 100 }
})

/** Lê o estoque atual só para mostrar a prévia (GET /estoque). */
async function carregarAtual() {
  atual.value = null
  const c = parseInteiro(codigo.value)
  if (Number.isNaN(c)) return
  try {
    atual.value = (await api.consultarEstoque(c)).estoque
  } catch {
    atual.value = null
  }
}

async function confirmar() {
  enviando.value = true
  erro.value = null
  sucesso.value = null
  const c = parseInteiro(codigo.value)
  try {
    const r = ehEntrada.value
      ? await api.entradaEstoque(c, qtd.value)
      : await api.saidaEstoque(c, qtd.value)
    registrar({ tipo: props.tipo, codigo: r.codigo, quantidade: qtd.value, estoque: r.estoque })
    sucesso.value = r
    atual.value = r.estoque
    quantidade.value = '1'
  } catch (e) {
    erro.value = mensagemDeErro(e, codigo.value)
  } finally {
    enviando.value = false
  }
}

onMounted(carregarAtual)
</script>

<template>
  <div class="split">
    <form class="panel split__main" @submit.prevent="confirmar">
      <div class="panel__head">
        <h2>Dados da movimentação</h2>
        <span :class="['tag', ehEntrada ? 'tag--pos' : 'tag--neg']">{{ ehEntrada ? 'Entrada' : 'Saída' }}</span>
      </div>
      <div class="panel__body form-grid">
        <div>
          <label for="codigo" class="field-label">Código do produto</label>
          <input id="codigo" v-model="codigo" class="input mono" inputmode="numeric" placeholder="ex. 123" required @change="carregarAtual">
          <span class="field-help">{{ atual == null ? 'Informe o código para ver o estoque atual.' : `Estoque atual: ${atual} un.` }}</span>
        </div>
        <div>
          <label for="quantidade" class="field-label">Quantidade</label>
          <QtyInput id="quantidade" v-model="quantidade" />
          <span class="field-help">Somente números inteiros.</span>
        </div>
      </div>
      <div class="panel__foot">
        <NuxtLink to="/" class="btn btn--secondary">Cancelar</NuxtLink>
        <button :class="['btn', ehEntrada ? 'btn--primary' : 'btn--danger']" type="submit" :disabled="enviando">
          {{ enviando ? 'Salvando…' : ehEntrada ? 'Confirmar entrada' : 'Confirmar saída' }}
        </button>
      </div>
    </form>

    <div class="split__side">
      <section class="panel">
        <div class="panel__head"><h2>Resumo<template v-if="codigo"> · produto <span class="mono">{{ codigo }}</span></template></h2></div>
        <div class="panel__body" style="padding-top:4px">
          <div class="kv"><span class="kv__label">Estoque atual</span><span class="mono">{{ atual ?? '—' }} un.</span></div>
          <div class="kv">
            <span class="kv__label">{{ ehEntrada ? 'Entrada' : 'Saída' }}</span>
            <span :class="['mono', ehEntrada ? 'text-pos' : 'text-neg']">{{ Number.isNaN(qtd) ? '—' : (ehEntrada ? '+' : '−') + qtd }} un.</span>
          </div>
          <div class="kv kv--total"><span class="kv__label">Estoque após</span><span class="mono">{{ depois ?? '—' }} un.</span></div>
          <div v-if="barra" class="bar" aria-hidden="true">
            <div class="bar__base" :style="{ width: barra.base + '%' }" />
            <div :class="ehEntrada ? 'bar__pos' : 'bar__neg'" :style="{ width: barra.delta + '%' }" />
          </div>
        </div>
      </section>

      <AlertBox v-if="sucesso" tipo="sucesso" :titulo="`Estoque do produto ${sucesso.codigo} atualizado`">
        <span>Novo estoque: <strong class="mono">{{ sucesso.estoque }} un.</strong></span>
      </AlertBox>
      <AlertBox v-if="erro" tipo="erro" :titulo="erro.titulo">
        <span>{{ erro.detalhe }}</span>
      </AlertBox>
    </div>
  </div>
</template>

<style scoped>
/* Resumos (chave → valor) */
.bar { display: flex; height: 8px; border-radius: 999px; overflow: hidden; background: var(--surface-2); margin-top: 8px; }
.bar__base { background: var(--bar); }
.bar__pos { background: var(--lime); }
.bar__neg { background: var(--neg); opacity: .35; }
</style>
