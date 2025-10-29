package projeto_2;

/**
 * CLASSE DE DADOS (POJO)
 * * Esta é uma classe simples, usada apenas para armazenar os dados de UMA
 * única comparação.
 * * Responsabilidades:
 * * 1. Armazenar o nome do Documento A (ex: "doc1.txt").
 * * 2. Armazenar o nome do Documento B (ex: "doc2.txt").
 * * 3. Armazenar o valor 'double' da similaridade calculada entre A e B.
 * * (Esta classe não tem lógica complexa, apenas 'getters' e um construtor).
 */
public class Resultado {

	private String arquivoA;
	private String arquivoB;
	private double similaridade;
	
	public Resultado(String arquivoA, String arquivoB, double similaridade) {
		this.arquivoA = arquivoA;
		this.arquivoB = arquivoB;
		this.similaridade = similaridade;
	}
	
	public String getArquivoA() {
        return arquivoA;
    }

    public String getArquivoB() {
        return arquivoB;
    }

    public double getSimilaridade() {
        return similaridade;
    }
    
    @Override
    public String toString() {
    	return String.format("%s <-> %s = %.2f",
    			this.arquivoA,
    			this.arquivoB,
    			this.similaridade);
    }
}
