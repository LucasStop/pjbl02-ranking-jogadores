# PBL 02 — Ranking de Jogadores

Sistema de Ranking de Jogadores utilizando Árvore Binária de Busca (ABB) não balanceada, implementada do zero, com interface gráfica em Java Swing.

Disciplina: **Resolução de Problemas Estruturados em Computação** — Profª Marina de Lara (PUCPR — BSI).

## Integrantes

- Lucas Stopinski ([@LucasStop](https://github.com/LucasStop))
- Roberto Zhou ([@RobertoZhou](https://github.com/RobertoZhou))
- Luc Bruno ([@Luc-Bruno](https://github.com/Luc-Bruno))

## Divisão do trabalho

| Camada | Responsável | Conteúdo |
|--------|-------------|----------|
| Modelo & Estrutura de Dados | Lucas | `model/Player`, `model/Node`, `tree/BinarySearchTree`, estruturas auxiliares `Pilha` e `ListaEncadeada` reutilizadas do RA1 |
| I/O & Integração | Roberto | `io/LerCSV` (leitura sequencial do arquivo), `Main` |
| Interface Gráfica | Luc Bruno | `ui/TreeLayout`, `ui/TreePanel`, `ui/RankingAppFrame` |

## Estrutura de pastas

```
pjbl02-ranking-jogadores/
├── docs/                         enunciado do PBL, CSV e exemplo de TreeVisualizer
├── projeto/
│   ├── players.csv               base de dados dos jogadores
│   └── src/
│       ├── Main.java             ponto de entrada
│       ├── model/                Player, Node (ABB), No<T>
│       ├── datastructure/        Pilha, ListaEncadeada (reuso do RA1)
│       ├── tree/                 BinarySearchTree
│       ├── io/                   LerCSV
│       └── ui/                   TreePanel, TreeLayout, RankingAppFrame
└── README.md
```

## Como compilar e executar

Requer Java 17 ou superior.

```bash
cd projeto
javac -d out $(find src -name "*.java")
java -cp out Main
```

Na janela que abre, clique em **Carregar CSV...** e selecione `projeto/players.csv`.

## Funcionalidades

- Carregamento de jogadores a partir de um arquivo CSV, com inserção sequencial na ordem em que aparecem no arquivo
- Inserção manual de novos jogadores (nickname + ranking), com validação de duplicidade tanto de nickname quanto de ranking
- Busca de jogador pelo nickname, destacando visualmente o nó encontrado
- Remoção de jogador pelo nickname, com confirmação antes de executar
- Visualização hierárquica da árvore em interface gráfica Swing, com rolagem e antialiasing
- Impressão em ordem crescente de ranking (in-order traversal) no console
- Limpeza completa da árvore pelo painel lateral

## Decisões de projeto

- **Chave da árvore é o ranking**, mas a busca e a remoção são feitas pelo nickname, o que obriga a usar uma busca em profundidade (DFS) recursiva que percorre todos os nós.
- **Inserção sequencial** dos jogadores na ordem em que aparecem no CSV. Cada linha lida pelo `LerCSV` é inserida diretamente na árvore, fazendo do primeiro registro a raiz da ABB. As estruturas `Pilha` e `ListaEncadeada` reutilizadas do RA1 ficam disponíveis no pacote `datastructure` para qualquer extensão futura que precise de coleções auxiliares.
- **Ranking é chave única**: a `BinarySearchTree.insert` ignora rankings já presentes para preservar a invariante da ABB, e a UI consulta `containsRanking` antes de aceitar uma nova inserção, exibindo aviso se o ranking digitado já existir.
- **Recursividade em tudo**: `insert`, `containsRanking`, `search`, `remove`, `findMin`, `inOrder`, `size` e `height` são todos recursivos, conforme exigido pelo enunciado.
- **`Node` encapsulado** com getters e setters, permitindo substituir o jogador dentro do nó quando a remoção promove um sucessor in-order, sem quebrar encapsulamento.
- **UI desacoplada** da implementação da árvore: o `TreePanel` só depende de `Node` e pode ser reutilizado por qualquer renderização hierárquica.

## Plano de testes manuais

### Carregamento do CSV
1. Abrir a aplicação e clicar em **Carregar CSV...**.
2. Escolher `projeto/players.csv`.
3. A árvore deve aparecer com **KDA_Kitsune** como raiz (ranking 50, primeiro registro do CSV).
4. Clicar em **Imprimir em ordem** e conferir no console que os 100 jogadores aparecem ordenados de 1 a 100.

### Busca
- Buscar `KDA_Kitsune` (raiz, ranking 50), `GhostPing` (ranking 1), `RadiantRookie` (ranking 100) — todos devem ser encontrados e destacados.
- Buscar um nickname inexistente — deve mostrar mensagem de não encontrado.

### Remoção (três casos)
- **Folha**: remover um nó folha visível na árvore (por exemplo `RadiantRookie`, ranking 100). Desaparece, restante intacto.
- **Nó com um filho**: identificar um nó com apenas um lado e remover. O único filho é promovido à posição do nó removido.
- **Nó com dois filhos**: remover `KDA_Kitsune` (raiz, com filhos esquerdo e direito). O sucessor in-order assume a posição e a ordem in-order continua crescente.
- Remover um nickname inexistente — mensagem de erro, árvore inalterada.

### Inserção manual
- Inserir novo jogador com ranking válido e inédito — aparece na posição correta na árvore.
- Tentar inserir com ranking não inteiro — pop-up de erro.
- Tentar inserir nickname duplicado — pop-up de aviso.
- Tentar inserir ranking duplicado — pop-up de aviso, sem alterar a árvore.

## Regras do trabalho atendidas

- [x] ABB implementada do zero, sem estruturas prontas do Java
- [x] Chave de ordenação: `ranking` (inteiro)
- [x] Busca e remoção pelo `nickname`, via DFS recursiva
- [x] `insert`, `search`, `remove` e `findMin` recursivos
- [x] Código sem nenhum comentário
- [x] `Pilha` e `ListaEncadeada` do RA1 disponíveis no projeto como estruturas auxiliares
- [x] Validação de chave única na inserção (nickname e ranking)
- [x] Visualização gráfica hierárquica da árvore
- [x] Princípios de orientação a objetos (pacotes por responsabilidade, encapsulamento, composição)
