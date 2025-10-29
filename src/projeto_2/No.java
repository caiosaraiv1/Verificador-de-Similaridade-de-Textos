package projeto_2;

import java.util.ArrayList;
import java.util.List;

public class No {

	double similaridade;
	List<Resultado> resultados;
	No esq;
	No dir;
	int altura;
	
	public No(Resultado resultado) {
		this.similaridade = resultado.getSimilaridade();
		this.resultados = new ArrayList<>();
		this.resultados.add(resultado);
		this.esq = null;
		this.dir = null;
		this.altura = 0;
	}
	
	@Override
	public String toString() {
		return "No[Similaridade]: " + this.similaridade +
				", Altura: " + this.altura + 
				", Qtd. Resultados: " + (this.resultados != null ? this.resultados.size() : 0) + 
				"]";
	}
}
