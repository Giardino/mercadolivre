MercadoLivre App


Aplicativo Android para teste técnico que permite pesquisar e visualizar detalhes de produtos do MercadoLivre. O projeto foi desenvolvido utilizando as melhores práticas de desenvolvimento, como Clean Architecture e Jetpack Compose.

:rocket: Funcionalidades
Pesquisa de Produtos: Permite que os usuários pesquisem produtos por nome.
Detalhes do Produto: Exibe informações detalhadas sobre os produtos, incluindo descrição, preço e imagens.

:computer: Tecnologias Utilizadas
Kotlin: Linguagem principal do projeto.
Hilt: Injeção de dependências.
Retrofit: Cliente HTTP para chamadas de API.
Jetpack: Framework que ajuda desenvolvedores a seguir as práticas recomendadas
Jetpack Compose: Framework para construção de UI nativo do Android.
JUnit e Mockito: Testes unitários.
:wrench: Configuração e Instalação
Pré-requisitos
Android Studio
Java Development Kit (JDK) 11
Passos para Configuração
Clone o repositório:

bash
Copiar código
git clone https://github.com/seu-usuario/seu-repositorio.git
Abra o projeto no Android Studio.

Sincronize o Gradle para baixar todas as dependências.

Conecte seu dispositivo ou emulador Android e execute o projeto.

:triangular_flag_on_post: Estrutura do Projeto
O projeto segue a Clean Architecture com separação de responsabilidades em camadas:

Data Layer: Implementa os repositórios e lida com a persistência de dados (local e remoto).
Domain Layer: Contém as entidades e casos de uso (Use Cases) do aplicativo.
Presentation Layer: Contém os ViewModels e a lógica de apresentação.
UI Layer: Utiliza o Jetpack Compose para construir as telas do aplicativo.
perl
Copiar código
├── data
│   ├── repository
│   ├── remote
│   └── local
├── domain
│   ├── model
│   └── usecase
├── presentation
│   ├── viewmodel
│   ├── state
│   └── ui
└── di

:page_with_curl: Licença
Este projeto está licenciado sob a MIT License.


