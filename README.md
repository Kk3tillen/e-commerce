# E-commerce

Projeto de e-commerce dividido em duas partes:

| Pasta         | O que é    | Tecnologia                                  | Endereço local          |
| ------------- | ---------- | ------------------------------------------- | ----------------------- |
| `server/`     | Back-end   | Java + Spring Boot 4 (Gradle), banco H2     | http://localhost:8080   |
| `client/web/` | Front-end  | Next.js 16 + React 19 + Tailwind CSS 4      | http://localhost:3000   |

## Pré-requisitos

- **Java 25** (JDK). Verifique com `java -version`.
- **Node.js 20 ou mais recente** e **npm**. Verifique com `node -v` e `npm -v`.

O Gradle não precisa ser instalado: o projeto já traz o Gradle Wrapper (`./gradlew`).

## Rodando o back-end (server)

```bash
cd server
./gradlew bootRun
```

Na primeira execução o Gradle baixa as dependências, então pode demorar um pouco.

Ele fica disponível em http://localhost:8080. 

Outros comandos úteis:

```bash
./gradlew test    # roda os testes
./gradlew build   # gera o .jar em build/libs/
```

## Rodando o front-end (client)

Em **outro terminal**:

```bash
cd client/web
npm install    # só na primeira vez ou quando mudar o package.json
npm run dev
```

Acesse http://localhost:3000. As alterações no código aparecem automaticamente no navegador.

Outros comandos úteis:

```bash
npm run build   # gera a versão de produção
npm run start   # roda a versão de produção (depois do build)
npm run lint    # verifica o código com o ESLint
```

## Rodando tudo junto

Abra dois terminais:

1. Terminal 1: `cd server && ./gradlew bootRun`
2. Terminal 2: `cd client/web && npm run dev`

Front-end em http://localhost:3000 e back-end em http://localhost:8080.
