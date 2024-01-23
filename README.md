Executando o Projeto com Docker
Agora que o Docker está instalado em sua máquina, siga os passos abaixo para executar o projeto:

1. JHipster Application:
docker run -d -p 0.0.0.0:8080:8080 -p 0.0.0.0:9000:9000 --expose=8080 --expose=9000 --name grao-direto jhipster/jhipster
Este comando baixará e executará a imagem Docker do JHipster, expondo as portas 8080 (aplicação) e 9000 (JHipster Control Center). Certifique-se de ajustar os detalhes conforme necessário.

2.MySQL:
docker run -d --name graodireto-mysql -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=mvp -e MYSQL_USER=mvp -e MYSQL_PASSWORD=mvp -p 3306:3306 mysql:8.0.30
Este comando baixará e executará a imagem Docker do MySQL, criando um contêiner com uma instância do MySQL configurada. Certifique-se de ajustar as configurações conforme necessário.

3. Configuração do Banco de Dados:
Abra o arquivo application-dev.yml, geralmente localizado em src/main/resources/config/application-dev.yml dentro do seu projeto.

Localize a seção de configuração do banco de dados (datasource). Deve se parecer com algo assim:
datasource:
  type: com.zaxxer.hikari.HikariDataSource
  url: jdbc:mysql://172.17.0.2:3306/mvp?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&createDatabaseIfNotExist=true
  username: root
  password: 1234

Atualize a URL do banco de dados para apontar para o endereço IP do contêiner do Docker onde o MySQL está sendo executado. Substitua localhost pelo endereço IP do Docker.

Salve as alterações no arquivo application-dev.yml.

Após essas alterações, ao iniciar sua aplicação, ela deve se conectar ao banco de dados MySQL dentro do contêiner Docker. Lembre-se de que o contêiner Docker e a aplicação Java precisam estar na mesma rede para que a comunicação ocorra corretamente.
Rodando o Projeto
Antes de iniciar o projeto, certifique-se de ter as seguintes dependências instaladas em sua máquina:

Node.js: Utilizamos o Node para executar um servidor web de desenvolvimento e construir o projeto. Dependendo do seu sistema, você pode instalar o Node a partir da fonte ou como um pacote pré-empacotado.
Após instalar o Node, execute o seguinte comando para instalar as ferramentas de desenvolvimento. Você só precisará executar este comando quando as dependências mudarem no arquivo package.json.

bash code 
 npm install
 Execute os seguintes comandos para criar uma experiência de desenvolvimento tranquila:

No terminal, execute:

bash code
./mvnw
Em outro terminal, execute:

bash code
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
