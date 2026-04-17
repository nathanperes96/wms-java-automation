# 📦 Sistema de Automação de WMS (Gestão de Paletes)

## 📌 Sobre o Projeto
Este projeto foi desenvolvido como requisito final para a formação no programa **DIO Campus Expert (Turma 15)**. 

Trata-se de um sistema inteligente para gerenciamento de armazém (WMS) focado no endereçamento e rastreio de paletes. O diferencial do projeto é a **automação logística**: o sistema gera a carga de paletes automaticamente e utiliza lógica de programação para varrer os endereços físicos do galpão, alocando os itens em vagas vazias e liberando-os durante a expedição.

## ⚙️ Regras de Negócio e Arquitetura
O sistema simula um estoque real com 200 posições de porta-paletes, formatadas sob a matriz: `[RUA] - [LADO/ANDAR] - [VAGA]`.
Exemplo de endereço gerado: `04-2B-09`.

**Funcionalidades (CRUD):**
* **Entrada Automatizada:** O sistema recebe a quantidade de paletes do caminhão e gera automaticamente os lotes, definindo o turno atual e buscando vagas ociosas usando a classe `Random` vinculada a um `ArrayList` de controle espacial.
* **Consulta:** Relatório de todos os itens ativos no estoque.
* **Expedição (Picking):** O sistema busca o tipo de produto requisitado pelo cliente, retira do estoque e devolve o endereço para a lista de posições ociosas.

## 💻 Tecnologias Utilizadas
* **Java (Orientação a Objetos):** Criação da classe `Palete` como modelo de domínio.
* **Collections Framework:** Uso intensivo de `ArrayList` e `Iterator` para manipulação segura de dados em tempo de execução sem estourar a memória.
* **LocalDateTime:** Para geração automatizada de numeração de Lotes únicos.

## 👨‍💻 Autor
Desenvolvido por **Nathan**, graduado em Análise e Desenvolvimento de Sistemas e estudante de Engenharia de Software.
