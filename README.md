# 📋 Sistema de Cadastro

Este é um projeto em **Java** utilizando **Maven**, desenvolvido na falcudade para gerenciar usuários de forma simples, aplicando conceitos de **camadas de software (Controller, Service e Model)**.

---

## 🚀 Tecnologias utilizadas
- **Java 17+**
- **Maven**
  
---

## 📂 Estrutura do projeto
```text
sistema-de-cadastro/
 ├── pom.xml
 ├── src/
 │   ├── main/
 │   │   └── java/com/testesoftware/
 │   │        ├── Main.java                  # Classe principal
 │   │        ├── controller/
 │   │        │    └── UsuarioController.java
 │   │        ├── model/
 │   │        │    └── Usuario.java
 │   │        └── service/
 │   │             └── UsuarioService.java
 │   └── test/java/com/testesoftware/
 │        └── AppTest.java
 └── usuarios/                               # Arquivos JSON de simulação
```

## ✅ Funcionalidades
- 📌 **Cadastro de usuários**: permite criar novos registros de usuários.  
- 📋 **Listagem de usuários**: exibe os usuários cadastrados.  
- 🏗 **Arquitetura em camadas**: organização em Controller, Service e Model, seguindo boas práticas.  
- 🧪 **Testes unitários**: implementação de testes para garantir a qualidade do código.  
- 📂 **Persistência em arquivos JSON**: simulação de armazenamento de dados no diretório `usuarios/`.

# Diagrama IDEF0
- Em desenvolvimento 🚧


# Diagrama de classe em UML
<img width="1975" height="1242" alt="Image" src="https://github.com/user-attachments/assets/0f6d069c-f583-4562-a2f0-b62d8466fbc8" />

# Diagrama de caso de uso em UML
<img width="1192" height="471" alt="Image" src="https://github.com/user-attachments/assets/cc60a50c-2d6c-4edd-9083-d5c29fd29ab4" />
