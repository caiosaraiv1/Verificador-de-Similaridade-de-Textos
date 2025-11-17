# **Verificador de Similaridade de Textos**

Este projeto implementa um sistema completo para análise de similaridade entre documentos de texto, utilizando **pré-processamento linguístico**, **Tabela Hash com Double Hashing**, **vetorização de frequências**, **Similaridade do Cosseno** e **Árvore AVL** para organização ordenada dos resultados. O programa permite comparar pares de arquivos, listar os mais similares e realizar buscas específicas entre documentos de uma pasta.

---

## Funcionalidades

* **Listagem por limiar:** mostra todos os pares de documentos com similaridade acima de um valor definido.
* **Top-K:** exibe os *K* pares mais similares entre todos os documentos analisados.
* **Busca direta:** calcula a similaridade entre dois arquivos específicos.
* **Geração automática de relatório (`resultado.txt`).**
* **Pré-processamento completo dos textos:** normalização, remoção de pontuação, stopwords e contagem de frequência.
* **Implementação manual das principais estruturas:**

  * Tabela Hash com Double Hashing
  * Árvore AVL com rotações
  * Vetorização e Similaridade do Cosseno

---

## Estrutura do Projeto

```
src/
 └── projeto/
      ├── Main.java
      ├── Documento.java
      ├── TabelaHash.java
      ├── NoHash.java
      ├── ComparadorCosseno.java
      ├── Resultado.java
      ├── AVL.java
      ├── No.java
      └── StopWords.java
```

---

## Como Compilar

Dentro da pasta `src/projeto`:

```bash
javac *.java
```

---

## Como Executar

Os arquivos `.txt` devem estar em uma pasta, por exemplo `"docs"`.

### **1. Modo LISTA**

Lista todos os pares com similaridade maior ou igual ao limiar:

```bash
java projeto/Main docs 0.75 lista
```

---

### **2. Modo TOP-K**

Retorna os *K* documentos mais similares:

```bash
java projeto/Main docs 0.8 topK 5
```

---

### **3. Modo BUSCA**

Compara diretamente dois arquivos:

```bash
java projeto/Main docs 0.0 busca arquivo1.txt arquivo2.txt
```

---

## Como Funciona

### **Documento**

Faz o pré-processamento textual: leitura do arquivo, normalização, remoção de pontuação e stopwords, e armazenamento das palavras na Tabela Hash com suas frequências.

### **Tabela Hash (Double Hashing)**

Armazena cada palavra e sua frequência usando duas funções de dispersão e sondagem por double hashing na inserção e na busca.

### **Cálculo da Similaridade**

A classe `ComparadorCosseno` cria vetores de frequência alinhados e calcula a Similaridade do Cosseno, ideal para comparar documentos representados como vetores numéricos.

### **Árvore AVL**

Armazena os pares de documentos e suas similaridades de forma ordenada, garantindo buscas e inserções eficientes.

---

## Saída e Relatórios

Após a execução, o programa cria automaticamente:

```
resultado.txt
```

contendo:

* documentos processados
* total de comparações
* similaridades encontradas
* pares acima do limiar
* menor e maior similaridade

---

## Destaques Técnicos

* Implementação manual de Tabela Hash e Árvore AVL
* Similaridade do Cosseno aproveitando frequência real das palavras
* Pré-processamento robusto reduz falsos positivos
* Estrutura eficiente e extensível
* Correção do double hashing garantindo consistência entre inserção e busca

---

## Melhorias Futuras

* Redimensionamento dinâmico da Tabela Hash
* Novas métricas de similaridade (TF-IDF, Dice, Jaccard vetorial)
* Comparação paralela
* Visualização gráfica das relações entre documentos
* Interface GUI ou API

---

## Autores

* **Isabela Hissa Pinto**
* **Caio Ariel**
* **Kaique Paiva**

---
