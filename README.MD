API de contatos para receber mensagens do meu website e enviar e-mails

Tecnologias
----------------------
- Java 21
- Quarkus (REST, CDI)
- Quarkus REST Jackson
- Jakarta Mail com Angus Mail
- Thymeleaf (templates HTML)

Requisitos
----------
- JDK 21
- Maven 3.9+
- Conta Gmail com senha de app (ou credenciais SMTP equivalentes)

Configuração
------------
Variáveis de ambiente mínimas:
- `GMAIL_USER` — usuário SMTP (ex.: seu-email@gmail.com)
- `GMAIL_PASS` — senha de app do Gmail
- `MAIL_FROM` — opcional; remetente. Padrão: `GMAIL_USER`

Porta e CORS:
- HTTP: `8081`
- CORS liberado para `http://localhost:4200`

Execução em Desenvolvimento
---------------------------
```bash
cd backend
mvn quarkus:dev
```

Endpoint
--------
- POST `http://localhost:8081/api/contact`
    - Body JSON:
      ```json
      {
        "name": "Nome",
        "email": "email@dominio.com",
        "phone": "opcional",
        "message": "Sua mensagem"
      }
      ```
    - Resposta 200:
      ```json
      { "status": "ok", "referenceId": "<uuid>" }
      ```

Template de E-mail
------------------
- `src/main/resources/templates/contact-email.html` (Thymeleaf)

Build para Produção
-------------------
```bash
cd backend
mvn clean package
# artefato em target/ com runner Quarkus
```

Licença
-------
MIT. Consulte `LICENSE`.

