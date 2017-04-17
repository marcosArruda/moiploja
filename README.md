# Moiploja
**Moiploja é uma simples aplicação e-commerce construída com SpringBoot e Moip para os fluxos de pagamentos.**

Como Rodar?

git clone https://github.com/marcosArruda/moiploja.git

cd moiploja

moiploja> mvn clean install

moiploja> cd moiploja-site

moiploja-site> mvn spring-boot:run

Agora você já pode acessar o site Moiploja(a loja propriamente) no url https://localhost:8443/


**A aplicação estará constantemente funcionando em https://marcosarruda.info:8443/**

A arquitetura de código segue o classico padrão mvc do Spring framework. Utilizei Websocket para a atualização assíncrona
do status do pagamento em tempo real;

O Deploy foi realizado em uma VPS que possuo. Utilizei o script deploy.sh que criei para realizar esse procedimento.


TODO:
- Subir a app na nuvem - **Done**;
- Terminar o cc_hash - **Done**;
- Terminar a call para o moip - **Done**;
- terminar a cupom - **Done**;
- terminar o websocket - **Done**;
- Melhoria nos testes unitários;


