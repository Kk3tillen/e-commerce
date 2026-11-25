// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  compatibilityDate: '2025-07-15',
  devtools: { enabled: true },

  // Front-end puro (SPA): toda a lógica de negócio fica no backend.
  ssr: false,

  css: ['~/assets/css/main.css'],

  app: {
    head: {
      title: 'Controle de Estoque',
      htmlAttrs: { lang: 'pt-BR' },
      link: [
        { rel: 'preconnect', href: 'https://fonts.googleapis.com' },
        { rel: 'preconnect', href: 'https://fonts.gstatic.com', crossorigin: '' },
        {
          rel: 'stylesheet',
          href: 'https://fonts.googleapis.com/css2?family=Bricolage+Grotesque:opsz,wght@12..96,800&family=Geist:wght@400;500;600&family=Geist+Mono:wght@400;500&display=swap',
        },
      ],
    },
  },

  runtimeConfig: {
    public: {
      // Endereço da API REST. Pode ser sobrescrito com NUXT_PUBLIC_API_BASE.
      apiBase: 'http://localhost:8080',
    },
  },
})
