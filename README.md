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
| Modelo & Estrutura de Dados | Lucas | `model/Player`, `model/Node`, `tree/BinarySearchTree`, reuso da Pilha/ListaEncadeada do RA1 |
| I/O & Integração | Roberto | `io/LerCSV` (inserção em ordem reversa via Pilha), `Main` |
| Interface Gráfica | Luc Bruno | `ui/TreePanel`, `ui/TreeLayout`, `ui/RankingAppFrame` |

## Estrutura de pastas

```
pjbl02-ranking-jogadores/
├── docs/                         documentação e PDF do enunciado
├── projeto/
│   ├── players.csv               base de dados dos jogadores
│   └── src/
│       ├── Main.java
│       ├── model/                Player, Node, No<T>
│       ├── datastructure/        Pilha, ListaEncadeada (RA1)
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

Na janela que abre, clique em **Carregar CSV...** e selecione o arquivo `projeto/players.csv`.

## Funcionalidades

- Carregamento de jogadores a partir de um CSV (inserção em ordem reversa de leitura)
- Inserção manual de novos jogadores (nickname + ranking)
- Busca de jogador por nickname (busca em profundidade recursiva)
- Remoção de jogador por nickname (recursiva, tratando os 3 casos de filhos)
- Visualização hierárquica da árvore em interface gráfica (Swing)
- Impressão em ordem (in-order traversal) no console

## Regras do trabalho

- ABB implementada do zero, sem estruturas prontas do Java
- Chave de ordenação: `ranking` (int)
- Busca e remoção pelo `nickname` (String), via DFS recursiva
- Todos os métodos (inserir, buscar, remover, findMin) são **recursivos**
- Código sem comentários
- Reuso da `Pilha` e da `ListaEncadeada` implementadas no RA1
