Rodando o Projeto
Antes de iniciar o projeto, certifique-se de ter as seguintes dependências instaladas em sua máquina:

Node.js: Utilizamos o Node para executar um servidor web de desenvolvimento e construir o projeto. Dependendo do seu sistema, você pode instalar o Node a partir da fonte ou como um pacote pré-empacotado.
Após instalar o Node, execute o seguinte comando para instalar as ferramentas de desenvolvimento. Você só precisará executar este comando quando as dependências mudarem no arquivo package.json.

bash
 code
npm install
Execute os seguintes comandos para criar uma experiência de desenvolvimento tranquila:

No terminal, execute:

bash
 code
./mvnw
Em outro terminal, execute:

bash
Copy code
npm run start
Agora, seu ambiente de desenvolvimento está pronto e o projeto está em execução. Certifique-se de ajustar as instruções conforme necessário para se adequar ao seu projeto específico.

Aplicação gerada com JHipster 8.1.0.

Estrutura do Projeto:

Utiliza Node.js para desenvolvimento.
package.json gerado para melhor experiência.
Configurações para ferramentas como git, prettier, eslint, husky, etc.
Estrutura Java padrão em /src/*.
Arquivos de configuração, como .yo-rc.json e .yo-resolve.
Configurações de entidade em .jhipster/*.json.
Wrapper npmw para usar npm instalado localmente.
Configurações Docker em /src/main/docker.

Desenvolvimento:

Dependências necessárias: Node.js.
Execute npm install para instalar ferramentas de desenvolvimento.
Use npm scripts e Angular CLI com Webpack.
Comandos para desenvolvimento: ./mvnw e npm start.
PWA (Progressive Web App) desativado por padrão.

Gerenciamento de Dependências:

Use npm para gerenciar dependências de CSS e JavaScript.
Atualize com npm update e npm install.
Comandos úteis: npm run.

Construção para Produção:

Empacotar como JAR: ./mvnw -Pprod clean verify.
Empacotar como WAR: ./mvnw -Pprod,war clean verify.

Outros Aspectos:

JHipster Control Center disponível.
Testes com Spring Boot e cliente (Jest).
Análise de código usando Sonar.
Docker para facilitar o desenvolvimento (opcional).
Integração Contínua configurável (opcional).
