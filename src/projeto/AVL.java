package projeto;

import java.util.ArrayList;
import java.util.List;

public class AVL {

	private No raiz;
	private int contadorRotacoes;
	
	public AVL() {
		this.raiz = null;
		contadorRotacoes = 0;
	}
	
	public AVL(No no) {
		this.raiz = no;
		contadorRotacoes = 0;
	}
	
	public int getContadorRotacoes() {
		return contadorRotacoes;
	}

	private int altura(No n) {
		if (n == null) return 0;
		return n.getAltura();
	}
	
	private int fatorBalanceamento(No n) {
		if (n == null) return 0;
		return altura(n.getEsq()) - altura(n.getDir());
	}
	
	public void inserir(Resultado res) {
		this.raiz = inserirRecursivo(this.raiz , res);
	}
	
	private No inserirRecursivo(No noAtual, Resultado novoRes) {
		if (noAtual == null) {
			No novoNo = new No(novoRes);
			return novoNo;
		} else if (novoRes.getSimilaridade() == noAtual.getSimilaridade()) {
			noAtual.adicionarResultado(novoRes);
			return noAtual;
		} else {
			if (novoRes.getSimilaridade() < noAtual.getSimilaridade()) {
			    noAtual.setEsq(inserirRecursivo(noAtual.getEsq(), novoRes)); 
			} else { 
			    noAtual.setDir(inserirRecursivo(noAtual.getDir(), novoRes)); 
			}
		}
		
		noAtual.setAltura(1 + Math.max(altura(noAtual.getEsq()), altura(noAtual.getDir())));
		
		int fb = fatorBalanceamento(noAtual);
		
		if (fb > 1 && novoRes.getSimilaridade() < noAtual.getEsq().getSimilaridade()) {
			return rotacaoDireita(noAtual);
		}
		
		if (fb < -1 && novoRes.getSimilaridade() > noAtual.getDir().getSimilaridade()) {
			return rotacaoEsquerda(noAtual);
		}
		
		if (fb > 1 && novoRes.getSimilaridade() > noAtual.getEsq().getSimilaridade()) {
			noAtual.setEsq(rotacaoEsquerda(noAtual.getEsq()));
			return rotacaoDireita(noAtual);
		}
		
		if (fb < -1 && novoRes.getSimilaridade() < noAtual.getDir().getSimilaridade()) {
			noAtual.setDir(rotacaoDireita(noAtual.getDir()));
	        return rotacaoEsquerda(noAtual);
		}
		
		return noAtual;
	}
	
	private No rotacaoDireita(No y) {
	    No x = y.getEsq();      
	    No T2 = x.getDir();     

	    x.setDir(y);            
	    y.setEsq(T2);           

	    y.setAltura(1 + Math.max(altura(y.getEsq()), altura(y.getDir())));
	    x.setAltura(1 + Math.max(altura(x.getEsq()), altura(x.getDir())));

	    this.contadorRotacoes += 1;

	    return x;
	}
	
	private No rotacaoEsquerda(No x) { 
	    No y = x.getDir();     
	    No T2 = y.getEsq();     

	    y.setEsq(x);            
	    x.setDir(T2);           

	    x.setAltura(1 + Math.max(altura(x.getEsq()), altura(x.getDir())));
	    y.setAltura(1 + Math.max(altura(y.getEsq()), altura(y.getDir())));

	    this.contadorRotacoes += 1;

	    return y; 
	}
	
	public Resultado getResultadoMenorSimilaridade() {
        if (this.raiz == null) return null;

        No noAtual = this.raiz;
        
        while (noAtual.getEsq() != null) noAtual = noAtual.getEsq();
  
        return noAtual.getResultados().get(0);
    }
	
	public List<Resultado> lista(double similaridadeMinima) {
		List<Resultado> resultadosEncontrados = new ArrayList<>();
		listaRecursivo(this.raiz, similaridadeMinima, resultadosEncontrados);
		return resultadosEncontrados;
	}
	
	private void listaRecursivo(No noAtual, double similaridadeMinima, List<Resultado> lista) {
		if (noAtual == null) return;
		if (noAtual.getSimilaridade() <= similaridadeMinima) {
			listaRecursivo(noAtual.getDir(), similaridadeMinima, lista);
		} else if (noAtual.getSimilaridade() > similaridadeMinima) {
			lista.addAll(noAtual.getResultados());
			listaRecursivo(noAtual.getEsq(), similaridadeMinima, lista);
			listaRecursivo(noAtual.getDir(), similaridadeMinima, lista);
		}
	}
	
	public List<Resultado> topK(int K, double similaridadeMinima) {
		if (K <= 0) return new ArrayList<>();
		List<Resultado> resultadosEncontrados = new ArrayList<>();
		topKRecursivo(this.raiz, K, similaridadeMinima, resultadosEncontrados);
		return resultadosEncontrados;
	}
	
	private void topKRecursivo(No noAtual, int K, double similaridadeMinima, List<Resultado> lista) {
		if (noAtual == null) return;
		if (lista.size() >= K) return;
		
		topKRecursivo(noAtual.getDir(), K, similaridadeMinima, lista);
		
		if (lista.size() >= K) return; // Se a direita já encheu a lista, pare
		
		if (noAtual.getSimilaridade() > similaridadeMinima) {
			List<Resultado> listaAux = noAtual.getResultados();
			for (Resultado res : listaAux) {
				if (lista.size() < K) lista.add(res);
				else break;
			}
			
			if (lista.size() >= K) return;
			
			topKRecursivo(noAtual.getEsq(), K, similaridadeMinima, lista);
		}
	}
}

