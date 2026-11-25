<script setup lang="ts">
import { api } from '~/services/api'
import type { VendaResposta } from '~/services/types'

useHead({ title: 'Registrar venda · Controle de Estoque' })

const { registrar, novaVenda } = useAtividade()

interface Linha { id: number; codigo: string; quantidade: string }
let seq = 0
const novaLinha = (): Linha => ({ id: ++seq, codigo: '', quantidade: '1' })

const linhas = ref<Linha[]>([novaLinha()])
const enviando = ref(false)
const comprovante = ref<VendaResposta | null>(null)
const erro = ref<{ titulo: string; detalhe: string } | null>(null)

const adicionar = () => linhas.value.push(novaLinha())
const remover = (id: number) => {
  linhas.value = linhas.value.filter((l) => l.id !== id)
  if (!linhas.value.length) adicionar()
}

async function finalizar() {
  enviando.value = true
  erro.value = null
  const itens = linhas.value.map((l) => ({ codigo: parseInteiro(l.codigo), quantidade: parseInteiro(l.quantidade) }))
  try {
    const r = await api.venda(itens)
    comprovante.value = r
    const id = novaVenda()
    r.itens.forEach((i) =>
      registrar({ tipo: 'venda', codigo: i.codigo, quantidade: i.quantidade, estoque: i.estoque, valor: i.subtotal, venda: id }),
    )
    linhas.value = [novaLinha()]
  } catch (e) {
    comprovante.value = null
    const t = mensagemDeErro(e)
    erro.value = { titulo: `Venda não realizada: ${t.titulo.toLowerCase()}`, detalhe: 'Nenhum estoque foi alterado. Corrija os itens e tente de novo.' }
  } finally {
    enviando.value = false
  }
}
</script>

<template>
  <PageHeader crumb="Estoque / Vendas" title="Registrar venda" description="Informe os itens. O total é calculado ao finalizar." />

  <div class="split">
    <form class="panel split__main" @submit.prevent="finalizar">
      <div class="panel__head">
        <h2>Itens da venda</h2>
        <span class="hint">{{ linhas.length }} {{ linhas.length === 1 ? 'item' : 'itens' }}</span>
      </div>
      <div class="table-wrap">
        <table>
          <thead><tr><th style="width:32px">#</th><th>Código do produto</th><th style="width:180px">Quantidade</th><th style="width:56px" /></tr></thead>
          <tbody>
            <tr v-for="(l, i) in linhas" :key="l.id">
              <td class="mono text-muted">{{ i + 1 }}</td>
              <td><input v-model="l.codigo" class="input input--sm mono" inputmode="numeric" placeholder="ex. 123" :aria-label="`Código do item ${i + 1}`" required></td>
              <td><QtyInput v-model="l.quantidade" small :label="`quantidade do item ${i + 1}`" /></td>
              <td>
                <button type="button" class="btn btn--ghost btn--icon btn--sm" :aria-label="`Remover item ${i + 1}`" @click="remover(l.id)">
                  <AppIcon name="trash" :size="16" />
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="panel__body" style="padding-top:14px;padding-bottom:14px">
        <button type="button" class="btn btn--secondary btn--dashed" @click="adicionar">+ Adicionar item</button>
      </div>
      <div class="panel__foot">
        <span class="lock-note"><AppIcon name="lock" :size="16" />Se algum item for inválido, nenhum estoque é alterado.</span>
        <button class="btn btn--primary" type="submit" :disabled="enviando">{{ enviando ? 'Finalizando…' : 'Finalizar venda' }}</button>
      </div>
    </form>

    <div class="split__side">
      <section class="panel">
        <div class="panel__head">
          <h2>Comprovante</h2>
          <span v-if="comprovante" class="tag tag--pos"><span class="tag__dot" />Concluída</span>
        </div>
        <template v-if="comprovante">
          <table>
            <thead><tr><th>Item</th><th class="num">Subtotal</th><th class="num">Restante</th></tr></thead>
            <tbody>
              <tr v-for="i in comprovante.itens" :key="i.codigo">
                <td>
                  <span class="mono">{{ i.codigo }}</span>
                  <span class="text-muted" style="display:block;font-size:12px">{{ i.quantidade }} × {{ formatBRL(i.subtotal / i.quantidade) }}</span>
                </td>
                <td class="num">{{ formatBRL(i.subtotal) }}</td>
                <td class="num text-muted">{{ i.estoque }} un.</td>
              </tr>
            </tbody>
          </table>
          <div class="receipt-total"><span style="font-weight:600">Total</span><span class="mono">{{ formatBRL(comprovante.total) }}</span></div>
        </template>
        <p v-else class="empty">O comprovante aparece aqui depois de finalizar a venda.</p>
      </section>

      <AlertBox v-if="erro" tipo="erro" :titulo="erro.titulo">
        <span>{{ erro.detalhe }}</span>
      </AlertBox>
    </div>
  </div>
</template>

<style scoped>
/* Específicos de tela */
.receipt-total { display: flex; justify-content: space-between; align-items: baseline; padding: 18px 20px; border-top: 1px solid var(--border); }
.receipt-total .mono { font-size: 30px; font-weight: 500; color: var(--pos); }
.lock-note { display: flex; align-items: center; gap: 8px; font-size: 13px; color: var(--muted); margin-right: auto; }
</style>
