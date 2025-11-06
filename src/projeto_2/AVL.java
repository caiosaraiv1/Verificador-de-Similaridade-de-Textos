package projeto_2;

/**
 * IMPLEMENTAÇÃO DA ÁRVORE AVL MANUAL
 * * Esta classe é a nossa implementação manual da Árvore AVL auto-balanceada.
 * * Responsabilidades:
 * * 1. Armazenar os objetos 'Resultado' de todas as comparações.
 * * 2. Usar a similaridade (um 'double') como chave de ordenação e
 * balanceamento.
 * * 3. Lidar com "empates": Se múltiplos pares tiverem a MESMA similaridade,
 * o nó da árvore deve armazenar uma LISTA de objetos 'Resultado'.
 * * 4. Implementar as operações de balanceamento (Rotação Simples e Dupla).
 * * 5. CONTAR o número total de rotações (simples e duplas) que
 * foram executadas durante as inserções. (Isso é crucial
 * para o relatório de análise experimental).
 * * 6. Fornecer métodos de consulta (ex: "buscar todos acima de X",
 * "buscar os K maiores").
 */
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
	
	/*
	 * TODO: método de busca por lista -> exibe todos os pares com similaridade acima do limiar;
	 * TODO: método de busca topK -> exibe apenas os K pares mais semelhantes (K informado);
	 * TODO: método de busca -> compara dois arquivos específicos (o limiar é ignorado neste modo);
	 */
}
