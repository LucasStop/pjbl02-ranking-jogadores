package datastructure;

public class Pilha<T> {
    private final ListaEncadeada<T> lista;

    public Pilha() {
        this.lista = new ListaEncadeada<>();
    }

    public void empilhar(T valor) {
        lista.adicionarNoFim(valor);
    }

    public T desempilhar() {
        return lista.removerDoFim();
    }

    public boolean estaVazia() {
        return lista.estaVazia();
    }

    public int tamanho() {
        return lista.getTamanho();
    }
}
