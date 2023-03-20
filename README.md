# saml-idp-auth
Projeto que disponibiliza um Provedor de Identidade (IDP)

## Solução técnica

Para prover uma solução genérica para diversos parceiros, foi criada a tabela no banco de dados.

Nessa tabela estão armazenados os entityIds dos Service Providers (Consumidores).

## Dados do serviço

### Serviços

keytool -genkeypair -alias idp.saml2.kookeeper.com.br -keypass kookeeper.com.br -keystore saml-keystore.jks -keyalg RSA
keytool -export -keystore saml-keystore.jks -alias idp.saml2.kookeeper.com.br -file idp.saml2.kookeeper.com.br.cer