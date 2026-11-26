<script setup lang="ts">
useHead({ title: 'Saída de estoque · Controle de Estoque' })
</script>

<template>
  <PageHeader
    crumb="Estoque / Movimentações" title="Saída de estoque"
    description="Baixa por perda, avaria ou ajuste: a quantidade é subtraída do estoque."
  />
  <MovimentacaoForm tipo="saida" />
</template>
