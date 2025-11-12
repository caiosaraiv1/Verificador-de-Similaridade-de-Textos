package projeto_2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main {
	
	private static int contadorDocumentos = 0;
	private static int contadorComparacoes = 0;

    public static void main(String[] args) throws IOException {

    	String caminho = args[0];
    	double similaridade = Double.parseDouble(args[1]);
    	Map<String, Documento> tabelaArquivos = Main.getTabelaArquivos(caminho);
    	AVL arvore = Main.popularAVL(tabelaArquivos);
    	List<Resultado> lista = new ArrayList<Resultado>();
    	
    	if (args.length == 3) {
    		lista = arvore.lista(similaridade);
    		Main.printarSaida(similaridade, lista, arvore);
    	} else if (args.length == 4) {
    		int K = Integer.parseInt(args[3]);
    		lista = arvore.topK(K, similaridade);
    		Main.printarSaida(similaridade, lista, arvore);
    	} else if (args.length == 5) {
    		double resultadoBusca = Main.busca(args[3], args[4], tabelaArquivos);
    		Main.printarSaida(args[3], args[4], resultadoBusca);
    	}
        
        

    }
    
    public static double busca(String arquivoA, String arquivoB, Map<String, Documento> tabelaArquivos) {
    	if (arquivoA.equals(arquivoB)) return 1.0;
    	
    	Documento docA = tabelaArquivos.get(arquivoA);
    	Documento docB = tabelaArquivos.get(arquivoB);
    	
    	if (docA == null || docB == null) throw new IllegalArgumentException("Arquivos invalidos.");
    	
    	TabelaHash tabA = docA.getTabela();
    	TabelaHash tabB = docB.getTabela();
    	
    	ComparadorCosseno comparador = new ComparadorCosseno(tabA, tabB);
    	
    	return comparador.similaridade();
    }
    
    public static Map<String, Documento> getTabelaArquivos(String caminhoPasta) throws IOException {
    	File pasta = new File(caminhoPasta);
        if (!pasta.isDirectory()) throw new IllegalStateException("O caminho especificado não é um diretório válido: " + pasta.getPath());
        
        File[] arquivosPasta = pasta.listFiles();
        if (arquivosPasta == null) throw new IOException("Não foi possível listar os arquivos da pasta: " + pasta.getPath());
        
        Map<String, Documento> tabelaArquivos = new HashMap<String, Documento>();
        for (File f: arquivosPasta) {
        	if (f.isFile()) {
				tabelaArquivos.put(f.getName(), new Documento(f.getPath()));
				contadorDocumentos++;
        	}
        }
        return tabelaArquivos;
    }
    
    public static AVL popularAVL(Map<String, Documento> tabelaArquivos) {
    	List<Documento> lista = new ArrayList<>(tabelaArquivos.values());
    	AVL arvore = new AVL();

    	for (int i = 0; i < lista.size(); i++) {
    		for (int j = i + 1; j < lista.size(); j++) {
    			
    			Documento docA = lista.get(i);
    			Documento docB = lista.get(j);
    			
    			TabelaHash tabA = docA.getTabela();
    			TabelaHash tabB = docB.getTabela();
    			
    			ComparadorCosseno comparador = new ComparadorCosseno(tabA, tabB);
    			double similaridade = comparador.similaridade();
    			
    			Resultado res = new Resultado(docA.getNomeArquivo(), docB.getNomeArquivo(), similaridade);
    			arvore.inserir(res);
    			contadorComparacoes++;
    		}
    	}
    	
    	return arvore;
    }
    
    public static void printarSaida(double similaridade, List<Resultado> lista, AVL arvore) {
    	System.out.println("=== VERIFICADOR DE SIMILARIDADE DE TEXTOS ===");
    	System.out.println("Total de documentos processados: " + contadorDocumentos);
    	System.out.println("Total de pares comparados: " + contadorComparacoes);
    	System.out.println("Função hash utilizada:");
    	System.out.println("Métrica de similaridade: Cosseno");
    	System.out.println("Pares com similaridade >= " + similaridade + ":");
    	System.out.println("---------------------------------");
    	for (Resultado r : lista) {
    		System.out.println(r.getArquivoA() + " <-> " + r.getArquivoB() + " = " + r.getSimilaridade());
    	}
    	System.out.println("Pares com menor similaridade:");
    	System.out.println("---------------------------------");
    	Resultado menor = arvore.getResultadoMenorSimilaridade();
    	System.out.println(menor.getArquivoA() + " <-> " + menor.getArquivoB() + " = " + menor.getSimilaridade());
    }
    
    public static void printarSaida(String arquivoA, String arquivoB, double similaridade) {
    	System.out.println("=== VERIFICADOR DE SIMILARIDADE DE TEXTOS ===");
    	System.out.println("Comparada: " + arquivoA + " <-> " + arquivoB);
    	System.out.println("Similaridade calculada: " + similaridade);
    	System.out.println("Métrica de similaridade: Cosseno");
    }
}
