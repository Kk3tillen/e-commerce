<script setup lang="ts">
const tema = useTheme()
useHead({ htmlAttrs: { 'data-theme': computed(() => tema.value) } })
</script>

<template>
  <NuxtLayout>
    <NuxtPage />
  </NuxtLayout>
</template>
