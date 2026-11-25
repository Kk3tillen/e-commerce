<script setup lang="ts">
const props = withDefaults(defineProps<{ id?: string; label?: string; small?: boolean }>(), { label: 'quantidade' })
const valor = defineModel<string>({ required: true })

function passo(delta: number) {
  const n = parseInteiro(valor.value)
  valor.value = String(Math.max(1, (Number.isNaN(n) ? 0 : n) + delta))
}
</script>

<template>
  <div class="qty">
    <button
      type="button" :class="['btn', 'btn--secondary', 'btn--icon', { 'btn--sm': props.small }]"
      :aria-label="`Diminuir ${props.label}`" @click="passo(-1)"
    >−</button>
    <input
      :id="props.id" v-model="valor" :class="['input', 'mono', { 'input--sm': props.small }]"
      inputmode="numeric" :aria-label="props.id ? undefined : props.label"
    >
    <button
      type="button" :class="['btn', 'btn--secondary', 'btn--icon', { 'btn--sm': props.small }]"
      :aria-label="`Aumentar ${props.label}`" @click="passo(1)"
    >+</button>
  </div>
</template>

<style scoped>
/* Formulários e botões */
.qty { display: flex; gap: 6px; }
.qty .input { text-align: center; padding: 0 6px; }
</style>
