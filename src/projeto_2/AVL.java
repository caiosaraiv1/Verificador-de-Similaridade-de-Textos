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

	No raiz;
	int contadorRotacoes;
	
	public AVL() {
		this.raiz = null;
		contadorRotacoes = 0;
	}
	
	public AVL(No no) {
		this.raiz = no;
		contadorRotacoes = 0;
	}
	
}
