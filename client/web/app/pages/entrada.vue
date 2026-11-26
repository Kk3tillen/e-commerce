<script setup lang="ts">
useHead({ title: 'Entrada de estoque · Controle de Estoque' })
</script>

<template>
  <PageHeader
    crumb="Estoque / Movimentações" title="Entrada de estoque"
    description="Reposição: a quantidade é somada ao estoque do produto."
  />
  <MovimentacaoForm tipo="entrada" />
</template>
