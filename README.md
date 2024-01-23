**graoDiretoMvp**

Esta aplicação foi gerada usando o JHipster 8.1.0.

**Estrutura do Projeto**

O Node é necessário para a geração e é recomendado para o desenvolvimento. O `package.json` é sempre gerado para uma melhor experiência de desenvolvimento com prettier, ganchos de commit, scripts e assim por diante.

Na raiz do projeto, o JHipster gera arquivos de configuração para ferramentas como git, prettier, eslint, husky e outros que são bem conhecidos e você pode encontrar referências na web.

A estrutura em `/src/*` segue a estrutura padrão do Java.

- `.yo-rc.json`: Arquivo de configuração do Yeoman. A configuração do JHipster é armazenada neste arquivo na chave `generator-jhipster`. Você pode encontrar configurações específicas para blueprints como `generator-jhipster-*`.
- `.yo-resolve` (opcional): Resolvedor de conflitos do Yeoman. Permite usar uma ação específica quando conflitos são encontrados, ignorando prompts para arquivos que correspondem a um padrão.
- `.jhipster/*.json`: Arquivos de configuração de entidades do JHipster.

`npmw`: Invólucro para usar o npm instalado localmente. O JHipster instala o Node e o npm localmente usando a ferramenta de construção por padrão. Este invólucro garante que o npm esteja instalado localmente e o utiliza, evitando algumas diferenças que versões diferentes podem causar.

`/src/main/docker`: Configurações do Docker para a aplicação e serviços dos quais a aplicação depende.

**Desenvolvimento**

Antes de construir este projeto, você deve instalar e configurar as seguintes dependências em sua máquina:

- Node.js: Usamos o Node para executar um servidor web de desenvolvimento e construir o projeto. Dependendo do seu sistema, você pode instalar o Node de origem ou como um pacote pré-empacotado.
  
Depois de instalar o Node, você deve conseguir executar o seguinte comando para instalar as ferramentas de desenvolvimento. Você só precisará executar este comando quando as dependências mudarem no `package.json`.

```bash
npm install
```

Usamos scripts npm e Angular CLI com Webpack como nosso sistema de construção.

Execute os seguintes comandos em dois terminais separados para criar uma experiência de desenvolvimento agradável, onde o navegador atualiza automaticamente quando os arquivos mudam no seu disco rígido.

```bash
./mvnw
npm start
```

O Npm também é usado para gerenciar as dependências de CSS e JavaScript usadas nesta aplicação. Você pode atualizar as dependências especificando uma versão mais recente no `package.json`. Você também pode executar `npm update` e `npm install` para gerenciar dependências. Adicione a flag de ajuda em qualquer comando para ver como você pode usá-lo. Por exemplo, `npm help update`.

O comando `npm run` listará todos os scripts disponíveis para execução neste projeto.

**Suporte a PWA**

O JHipster possui suporte a PWA (Progressive Web App), mas está desativado por padrão. Um dos principais componentes de uma PWA é um service worker.

O código de inicialização do service worker está desativado por padrão. Para ativá-lo, descomente o seguinte código em `src/main/webapp/app/app.config.ts`:

```typescript
ServiceWorkerModule.register('ngsw-worker.js', { enabled: false }),
```

**Gerenciamento de Dependências**

Por exemplo, para adicionar a biblioteca Leaflet como uma dependência de tempo de execução da sua aplicação, você executaria o seguinte comando:

```bash
npm install --save --save-exact leaflet
```

Para se beneficiar das definições de tipo TypeScript do repositório DefinitelyTyped no desenvolvimento, você executaria o seguinte comando:

```bash
npm install --save-dev --save-exact @types/leaflet
```

Então, você importaria os arquivos JS e CSS especificados nas instruções de instalação da biblioteca para que o Webpack saiba sobre eles. Edite o arquivo `src/main/webapp/app/app.config.ts`:

```typescript
import 'leaflet/dist/leaflet.js';
```

Edite o arquivo `src/main/webapp/content/scss/vendor.scss`:

```scss
@import 'leaflet/dist/leaflet.css';
```

Observação: Ainda existem algumas outras coisas a serem feitas para o Leaflet que não detalharemos aqui.

Para mais instruções sobre como desenvolver com o JHipster, dê uma olhada em [Usando o JHipster no desenvolvimento](https://www.jhipster.tech/development/).

**Usando Angular CLI**

Você também pode usar o Angular CLI para gerar algum código cliente personalizado.

Por exemplo, o seguinte comando:

```bash
ng generate component my-component
```

gerará alguns arquivos:

- Cria `src/main/webapp/app/my-component/my-component.component.html`
- Cria `src/main/webapp/app/my-component/my-component.component.ts`
- Atualiza `src/main/webapp/app/app.config.ts`

**Construção para Produção**

**Empacotando como JAR**

Para construir o jar final e otimizar a aplicação `graoDiretoMvp` para produção, execute:

```bash
./mvnw -Pprod clean verify
```

Isso concatenará e minificará os arquivos CSS e JavaScript do cliente. Ele também modificará o `index.html` para referenciar esses novos arquivos. Para garantir que tudo funcionou, execute:

```bash
java -jar target/*.jar
```

Em seguida, acesse [http://localhost:8080](http://localhost:8080) no seu navegador.

Consulte [Usando o JHipster em produção](https://www.jhipster.tech/production/) para mais detalhes.

**Empacotando como WAR**

Para empacotar sua aplic

ação como um WAR para implantá-la em um servidor de aplicativos, execute:

```bash
./mvnw -Pprod,war clean verify
```

**Centro de Controle JHipster**

O Centro de Controle JHipster pode ajudá-lo a gerenciar e controlar sua(s) aplicação(ões). Você pode iniciar um servidor de centro de controle local (acessível em [http://localhost:7419](http://localhost:7419)) com:

```bash
docker compose -f src/main/docker/jhipster-control-center.yml up
```

**Testes**

**Testes do Spring Boot**

Para iniciar os testes de sua aplicação, execute:

```bash
./mvnw verify
```

**Testes do Cliente**

Os testes de unidade são executados pelo Jest. Eles estão localizados em `src/test/javascript/` e podem ser executados com:

```bash
npm test
```

**Outros**

**Qualidade do Código usando Sonar**

O Sonar é usado para analisar a qualidade do código. Você pode iniciar um servidor Sonar local (acessível em [http://localhost:9001](http://localhost:9001)) com:

```bash
docker compose -f src/main/docker/sonar.yml up -d
```

Em seguida, execute uma análise do Sonar com o `sonar-scanner` ou usando o plugin Maven.

```bash
./mvnw -Pprod clean verify sonar:sonar -Dsonar.login=admin -Dsonar.password=admin
```

Se precisar executar novamente a fase do Sonar, certifique-se de especificar pelo menos a fase de inicialização, pois as propriedades do Sonar são carregadas do arquivo `sonar-project.properties`.

```bash
./mvnw initialize sonar:sonar -Dsonar.login=admin -Dsonar.password=admin
```

Além disso, em vez de passar `sonar.password` e `sonar.login` como argumentos da CLI, esses parâmetros podem ser configurados a partir do `sonar-project.properties`.

```properties
sonar.login=admin
sonar.password=admin
```

Para mais informações, consulte a página de [Qualidade do Código](https://www.jhipster.tech/code-quality/).

**Usando Docker para Simplificar o Desenvolvimento (opcional)**

Você pode usar o Docker para melhorar sua experiência de desenvolvimento com o JHipster. Várias configurações do docker-compose estão disponíveis na pasta `src/main/docker` para iniciar serviços de terceiros necessários.

Por exemplo, para iniciar um banco de dados MySQL em um contêiner Docker, execute:

```bash
docker compose -f src/main/docker/mysql.yml up -d
```

Para pará-lo e remover o contêiner, execute:

```bash
docker compose -f src/main/docker/mysql.yml down
```

Você também pode dockerizar completamente sua aplicação e todos os serviços de que ela depende. Para fazer isso, primeiro construa uma imagem Docker do seu aplicativo executando:

```bash
npm run java:docker
```

Ou construa uma imagem Docker arm64 ao usar um processador arm64 como MacOS com a família de processadores M1 em execução:

```bash
npm run java:docker:arm64
```

Em seguida, execute:

```bash
docker compose -f src/main/docker/app.yml up -d
```

Para obter mais informações, consulte [Usando Docker e Docker-Compose](https://www.jhipster.tech/docker/).

**Integração Contínua (opcional)**

Para configurar CI para o seu projeto, execute o sub-gerador ci-cd (jhipster ci-cd). Isso permitirá que você gere arquivos de configuração para um número de sistemas de Integração Contínua. Consulte a página [Configuração de Integração Contínua](https://www.jhipster.tech/setting-up-ci/) para mais informações.
