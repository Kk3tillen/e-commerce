<script setup lang="ts">
const links = [
  { to: '/', label: 'Início' },
  { to: '/produtos', label: 'Produtos' },
  { to: '/cadastrar', label: 'Cadastrar' },
  { to: '/consultar', label: 'Consultar' },
  { to: '/entrada', label: 'Entrada' },
  { to: '/saida', label: 'Saída' },
  { to: '/venda', label: 'Venda' },
]

const tema = useTheme()
const alternar = () => { tema.value = tema.value === 'claro' ? 'escuro' : 'claro' }
</script>

<template>
  <header class="nav">
    <NuxtLink to="/" class="brand">
      <span class="brand__mark"><AppIcon name="box" /></span>
      <span class="brand__name">estoque<span class="brand__dot">.</span></span>
    </NuxtLink>

    <nav class="nav__pill" aria-label="Navegação principal">
      <NuxtLink
        v-for="l in links" :key="l.to" :to="l.to"
        class="nav__link" exact-active-class="is-active"
      >
        {{ l.label }}
      </NuxtLink>
    </nav>

    <div class="nav__end">
      <button
        type="button" class="theme-toggle"
        :aria-label="tema === 'claro' ? 'Ativar modo escuro' : 'Ativar modo claro'"
        @click="alternar"
      >
        <AppIcon :name="tema === 'claro' ? 'moon' : 'sun'" />
      </button>
      <span class="version">v1 · rc-1.1</span>
    </div>
  </header>
</template>

<style scoped>
/* Navegação */
.nav {
  max-width: 1280px;
  margin: 0 auto;
  padding: 24px 32px;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
}
.brand { display: flex; align-items: center; gap: 10px; color: var(--text); text-decoration: none; }
.brand__mark {
  width: 36px; height: 36px; border-radius: 12px;
  background: var(--lime); color: var(--on-lime);
  display: grid; place-items: center;
}
.brand__name { font-family: var(--display); font-weight: 800; font-size: 22px; letter-spacing: -.03em; }
.brand__dot { color: var(--pos); }
.nav__pill {
  display: flex; flex-wrap: wrap; gap: 2px; padding: 5px;
  background: var(--surface);
  border: 1px solid var(--border);
  border-radius: 999px;
}
.nav__link {
  height: 44px; padding: 0 18px; border-radius: 999px;
  display: inline-flex; align-items: center;
  color: var(--muted); text-decoration: none;
  font-size: 14px; font-weight: 500; white-space: nowrap;
}
.nav__link:hover { color: var(--text); background: var(--surface-2); }
.nav__link.is-active { background: var(--lime); color: var(--on-lime); font-weight: 600; }
.nav__end { display: flex; align-items: center; gap: 8px; }
.theme-toggle {
  width: 40px; height: 40px; border-radius: 999px;
  border: 1px solid var(--border); background: transparent; color: var(--muted);
  display: grid; place-items: center; cursor: pointer;
}
.theme-toggle:hover { color: var(--text); background: var(--surface-2); }
.version {
  font-family: var(--mono); font-size: 12px; color: var(--muted);
  border: 1px solid var(--border); border-radius: 999px; padding: 8px 14px;
}
</style>
