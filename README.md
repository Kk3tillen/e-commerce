<div align="center">

# 🛒 E-commerce — Controle de Produtos e Estoque

**Sistema web para cadastrar produtos, movimentar estoque e registrar vendas.**

Trabalho acadêmico da **UFRN**, desenvolvido em equipe com **GitFlow**, issues e pull requests.

![Java](https://img.shields.io/badge/Java-25-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.2-6DB33F?logo=springboot&logoColor=white)
![H2](https://img.shields.io/badge/Banco-H2-1E6BB8)
![Nuxt](https://img.shields.io/badge/Nuxt-4-00DC82?logo=nuxt&logoColor=white)
![Vue](https://img.shields.io/badge/Vue-3-4FC08D?logo=vuedotjs&logoColor=white)
![TypeScript](https://img.shields.io/badge/TypeScript-3178C6?logo=typescript&logoColor=white)

</div>

---

## 📖 Sobre o trabalho

O objetivo é construir uma pequena loja com **controle de estoque**, separando bem as responsabilidades:

- o **back-end** (Spring Boot) concentra **toda a regra de negócio** e expõe uma API REST;
- o **front-end** (Nuxt) é só a interface: exibe as telas e chama a API, **sem nenhuma regra de negócio**.

Cada produto tem um **código** (único), um **preço** e uma **quantidade em estoque**, que começa em zero no cadastro.

---

## ✨ Funcionalidades

| | Funcionalidade | O que faz |
|---|---|---|
| ➕ | **Cadastrar produto** | Cria um produto com código e preço; o estoque começa em 0. Recusa código repetido. |
| 🔎 | **Consultar estoque** | Mostra quanto há em estoque de um produto. |
| 📥 | **Entrada de estoque** | Soma uma quantidade inteira ao estoque. |
| 📤 | **Saída de estoque** | Retira uma quantidade inteira do estoque. |
| 🧾 | **Realizar venda** | Vende vários itens de uma vez, calculando o subtotal de cada um e o total. |
| 📋 | **Listar produtos** | Mostra todos os produtos cadastrados (extra, fora da especificação). |
| 🌗 | **Modo claro/escuro** | Botão de sol/lua na barra de navegação. |

---

## 🧰 Tecnologias

**Back-end** (`server/`)

- ☕ Java 25
- 🌱 Spring Boot 4.2 (Web MVC + Data JPA)
- 🗄️ Banco H2 em arquivo (`./data/loja`) com console web
- 🧩 Lombok
- 🐘 Gradle (com Gradle Wrapper)

**Front-end** (`client/web/`)

- 💚 Nuxt 4 em modo SPA (`ssr: false`)
- 🖖 Vue 3 + TypeScript
- 🎨 CSS próprio com *design tokens* e tema claro/escuro

---

## 🚀 Como rodar

### Pré-requisitos

- **Java 25** (JDK) → confira com `java -version`
- **Node.js 20+** e **npm** → confira com `node -v` e `npm -v`

> O Gradle não precisa ser instalado: o projeto já traz o Gradle Wrapper (`./gradlew`).

### 1️⃣ Back-end

```bash
cd server
./gradlew bootRun
```

Fica disponível em **http://localhost:8080**. Na primeira execução o Gradle baixa as dependências, então pode demorar um pouco.

🗄️ O console do banco H2 fica em **http://localhost:8080/h2-console**
(JDBC URL: `jdbc:h2:file:./data/loja` · usuário: `sa` · senha em branco).

```bash
./gradlew test    # roda os testes
./gradlew build   # gera o .jar em build/libs/
```

### 2️⃣ Front-end

Em **outro terminal**:

```bash
cd client/web
npm install    # só na primeira vez ou quando mudar o package.json
npm run dev
```

Acesse **http://localhost:3000**. As alterações no código aparecem automaticamente no navegador.

```bash
npm run build     # gera a versão de produção
npm run preview   # roda a versão de produção localmente
```


```bash
NUXT_PUBLIC_API_BASE=http://localhost:8081 npm run dev
```

---

## 👥 Equipe de Desenvolvimento

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/danluan">
        <img src="https://avatars.githubusercontent.com/u/65050199?v=4" width="100px;" alt="Foto de Daniel Luan no GitHub"/>
        <br>
        <sub>
          <b>Daniel Luan</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Kk3tillen">
        <img src="https://avatars.githubusercontent.com/Kk3tillen" width="100px;" alt="Foto de Kézia Ketillen no GitHub"/>
        <br>
        <sub>
          <b>Kézia Ketillen</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/HannahSantos">
        <img src="https://avatars.githubusercontent.com/u/111528201?v=4" width="100px;" alt="Foto de Marcus Aurelius no GitHub"/>
        <br>
        <sub>
          <b>Hannah Santos</b>
        </sub>
      </a>
    </td>
  </tr>
</table>

