<script setup lang="ts">
defineProps<{ crumb: string; title: string; description?: string }>()
</script>

<template>
  <header class="page-header">
    <div>
      <span class="page-header__crumb">{{ crumb }}</span>
      <h1>{{ title }}</h1>
      <p v-if="description">{{ description }}</p>
    </div>
    <slot />
  </header>
</template>

<style scoped>
.page-header { display: flex; flex-wrap: wrap; justify-content: space-between; align-items: flex-end; gap: 16px; }
.page-header__crumb { font-size: 13px; color: var(--muted); }
.page-header h1 { margin: 4px 0 0; font-size: 28px; font-weight: 600; letter-spacing: -.02em; }
.page-header p { margin: 6px 0 0; font-size: 14px; color: var(--muted); }
</style>
