package datastructure;

import model.No;

public class ListaEncadeada<T> {
    private No<T> inicio;
    private No<T> fim;
    private int tamanho;

    public void adicionarNoFim(T valor) {
        No<T> novo = new No<>(valor);
        if (inicio == null) {
            inicio = novo;
            fim = novo;
        } else {
            fim.setProximo(novo);
            fim = novo;
        }
        tamanho++;
    }

    public T removerDoFim() {
        if (inicio == null) {
            return null;
        }
        T valor;
        if (inicio == fim) {
            valor = fim.getValor();
            inicio = null;
            fim = null;
        } else {
            No<T> atual = inicio;
            while (atual.getProximo() != fim) {
                atual = atual.getProximo();
            }
            valor = fim.getValor();
            fim = atual;
            fim.setProximo(null);
        }
        tamanho--;
        return valor;
    }

    public boolean estaVazia() {
        return inicio == null;
    }

    public int getTamanho() {
        return tamanho;
    }
}
