# Front-end — Controle de Produtos e Estoque

Front-end em **Nuxt 4** (modo SPA) para o sistema de controle de produtos e estoque.
Ele **não contém regra de negócio**: só exibe telas e chama a API REST.

## Rodando

```bash
npm install
npm run dev        # http://localhost:3000
```

## Telas

| Caminho      | Tela               | Endpoint consumido                          |
|--------------|--------------------|---------------------------------------------|
| `/`          | Visão geral        | (apenas histórico local da sessão)          |
| `/produtos`  | Produtos cadastrados | `GET /loja/produto` (fora da especificação) |
| `/cadastrar` | Cadastrar produto  | `POST /loja/produto`                        |
| `/consultar` | Consultar estoque  | `GET /loja/produto/{codigo}/estoque`        |
| `/entrada`   | Entrada de estoque | `PUT /loja/produto/{codigo}/entrada`        |
| `/saida`     | Saída de estoque   | `PUT /loja/produto/{codigo}/saida`          |
| `/venda`     | Registrar venda    | `POST /loja/venda`                          |

O botão de sol/lua na barra alterna entre **modo claro** e **modo escuro**.

## Ligando a API

Os endpoints ainda **não estão ligados**: cada função em `app/services/api.ts`
lança um erro, e as telas mostram a mensagem "Não foi possível concluir".

Para ligar um endpoint, abra `app/services/api.ts`, encontre a função
correspondente e **troque o `throw` pela linha `$fetch` comentada**:

```ts
async consultarEstoque(codigo: number): Promise<EstoqueResposta> {
  return $fetch<EstoqueResposta>(`/loja/produto/${codigo}/estoque`, { baseURL: base() })
},
```

O endereço da API fica em `nuxt.config.ts` (`runtimeConfig.public.apiBase`,
padrão `http://localhost:8080`) e pode ser trocado sem mexer no código:

```bash
NUXT_PUBLIC_API_BASE=http://localhost:8081 npm run dev
```

> Lembre de habilitar **CORS** no backend para a origem `http://localhost:3000`.

## Estrutura

```
app/
├─ assets/css/main.css      # tokens de cor (claro/escuro) e estilos
├─ components/               # AppNav, PageHeader, AlertBox, QtyInput, MovimentacaoForm, AppIcon
├─ composables/              # useTheme (tema), useAtividade (histórico da sessão)
├─ layouts/default.vue       # barra de navegação + conteúdo
├─ pages/                    # uma página por operação
├─ services/
│  ├─ api.ts                 # ← único ponto de contato com a API
│  └─ types.ts               # tipos iguais aos JSON da especificação
└─ utils/format.ts           # moeda, conversão de números, mensagens de erro
```
