<script setup lang="ts">
import { api } from '~/services/api'
import type { Produto } from '~/services/types'

useHead({ title: 'Cadastrar produto · Controle de Estoque' })

const { registrar } = useAtividade()

const codigo = ref('')
const preco = ref('')
const enviando = ref(false)
const criado = ref<Produto | null>(null)
const erro = ref<{ titulo: string; detalhe: string } | null>(null)

const precoPrevia = computed(() => {
  const v = parseDecimal(preco.value)
  return Number.isNaN(v) ? '—' : formatBRL(v)
})

async function cadastrar() {
  enviando.value = true
  erro.value = null
  criado.value = null
  try {
    const p = await api.cadastrarProduto({ codigo: parseInteiro(codigo.value), preco: parseDecimal(preco.value) })
    criado.value = p
    registrar({ tipo: 'cadastro', codigo: p.codigo, quantidade: 0, estoque: p.estoque, valor: p.preco })
    codigo.value = ''
    preco.value = ''
  } catch (e) {
    erro.value = mensagemDeErro(e, codigo.value)
  } finally {
    enviando.value = false
  }
}
</script>

<template>
  <PageHeader crumb="Estoque / Produtos" title="Cadastrar produto" description="O produto é criado com estoque igual a zero." />

  <div class="split">
    <form class="panel split__main" @submit.prevent="cadastrar">
      <div class="panel__head"><h2>Dados do produto</h2><span class="hint">Todos os campos são obrigatórios</span></div>
      <div class="panel__body form-grid">
        <div>
          <label for="codigo" class="field-label">Código</label>
          <input id="codigo" v-model="codigo" class="input mono" inputmode="numeric" placeholder="ex. 123" required>
          <span class="field-help">Número inteiro, único por produto.</span>
        </div>
        <div>
          <label for="preco" class="field-label">Preço unitário</label>
          <div class="input-prefix">
            <span>R$</span>
            <input id="preco" v-model="preco" class="input mono" inputmode="decimal" placeholder="0,00" required>
          </div>
        </div>
        <div>
          <label for="estoque" class="field-label">Estoque inicial</label>
          <input id="estoque" class="input mono" value="0" disabled>
          <span class="field-help">Definido automaticamente.</span>
        </div>
      </div>
      <div class="panel__foot">
        <NuxtLink to="/" class="btn btn--secondary">Cancelar</NuxtLink>
        <button class="btn btn--primary" type="submit" :disabled="enviando">{{ enviando ? 'Cadastrando…' : 'Cadastrar produto' }}</button>
      </div>
    </form>

    <div class="split__side">
      <section class="panel">
        <div class="panel__head"><h2>Resumo</h2></div>
        <div class="panel__body" style="padding-top:4px;padding-bottom:12px">
          <div class="kv"><span class="kv__label">Código</span><span class="mono">{{ codigo || '—' }}</span></div>
          <div class="kv"><span class="kv__label">Preço</span><span class="mono">{{ precoPrevia }}</span></div>
          <div class="kv"><span class="kv__label">Estoque</span><span class="mono">0 un.</span></div>
        </div>
      </section>

      <AlertBox v-if="criado" tipo="sucesso" :titulo="`Produto ${criado.codigo} cadastrado`">
        <NuxtLink :to="{ path: '/entrada', query: { codigo: criado.codigo } }">Registrar primeira entrada →</NuxtLink>
      </AlertBox>
      <AlertBox v-if="erro" tipo="erro" :titulo="erro.titulo">
        <span>{{ erro.detalhe }}</span>
      </AlertBox>
    </div>
  </div>
</template>
