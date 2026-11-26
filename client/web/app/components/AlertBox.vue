<script setup lang="ts">
defineProps<{ tipo: 'erro' | 'sucesso'; titulo: string }>()
</script>

<template>
  <div :class="['alert', `alert--${tipo}`]" :role="tipo === 'erro' ? 'alert' : 'status'">
    <AppIcon class="alert__icon" :name="tipo === 'erro' ? 'alert' : 'check'" :size="20" />
    <div class="alert__body">
      <strong>{{ titulo }}</strong>
      <slot />
    </div>
  </div>
</template>

<style scoped>
/* Alertas */
.alert { display: flex; gap: 12px; align-items: flex-start; padding: 16px 18px; border-radius: 14px; border: 1px solid; }
.alert--erro { background: var(--err-bg); border-color: var(--err-border); }
.alert--erro .alert__icon { color: var(--err); }
.alert--sucesso { background: var(--ok-bg); border-color: var(--ok-border); }
.alert--sucesso .alert__icon { color: var(--pos); }
.alert__icon { flex: none; margin-top: 1px; }
.alert__body { display: flex; flex-direction: column; gap: 3px; font-size: 13px; color: var(--muted); }
.alert__body strong { font-size: 14px; color: var(--text); }
</style>
