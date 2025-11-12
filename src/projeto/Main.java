package projeto;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption; 

/**
 * ===============================================================
 *  VERIFICADOR DE SIMILARIDADE DE TEXTOS - INSTRUÇÕES DE USO
 * ===============================================================
 *
 * 🧭 LOCAL DE EXECUÇÃO:
 *   → Todos os comandos devem ser executados dentro da pasta:
 *       Verificador-de-Similaridade-de-Textos/src
 *
 *   → Certifique-se de que todos os arquivos .java (Main.java, Documento.java,
 *     TabelaHash.java, ComparadorCosseno.java, StopWords.java, etc.)
 *     estejam dentro da pasta 'projeto'.
 *
 *   → A pasta "docs" (com os arquivos .txt) deve estar dentro de "src/"
 *     ou o caminho fornecido deve apontar corretamente para ela.
 *
 * ===============================================================
 * 🧩 COMPILAÇÃO:
 *   Compile todos os arquivos Java de uma vez:
 *
 *       javac *.java
 *
 *   Isso gera os arquivos .class na mesma pasta.
 *   Observação: A compilação pode exibir um warning, mas ele não afeta a execução do programa.
 *
 * ===============================================================
 * 🚀 EXECUÇÃO (3 MODOS DISPONÍVEIS):
 *
 * 1️o MODO LISTA:
 *     → Lista todos os pares de documentos com similaridade maior
 *       ou igual ao valor informado.
 *
 *       Exemplo:
 *         java projeto/Main docs 0.75 lista
 *
 * ---------------------------------------------------------------
 * 2️o MODO TOPK:
 *     → Exibe os K pares de documentos mais similares.
 *
 *       Exemplo:
 *         java projeto/Main docs 0.8 topK 5
 *
 * ---------------------------------------------------------------
 * 3️o MODO BUSCA:
 *     → Calcula a similaridade entre dois arquivos específicos.
 *
 *       Exemplo:
 *         java projeto/Main docs 0.0 busca arquivo1.txt arquivo4.txt
 *
 * ===============================================================
 * ⚙️ PARÂMETROS:
 *   • <pasta>         → Caminho da pasta que contém os arquivos .txt
 *   • <limiar>        → Valor de similaridade mínimo (entre 0.0 e 1.0)
 *   • <modo>          → "lista", "topK" ou "busca"
 *   • [parâmetros_op] → (somente para "topK" e "busca")
 *
 * ===============================================================
 */

public class Main {
	
	private static int contadorDocumentos = 0;
	private static int contadorComparacoes = 0;
	private static final String NOME_ARQUIVO_LOG = "resultado.txt";

    public static void main(String[] args) throws IOException {

    	String caminho = args[0];
    	double similaridade = Double.parseDouble(args[1]);
    	if (similaridade < 0 || similaridade > 1) {
    	    throw new IllegalArgumentException("O valor de similaridade deve estar entre 0 e 1.");
    	}
    	Map<String, Documento> tabelaArquivos = Main.getTabelaArquivos(caminho);
    	AVL arvore = Main.popularAVL(tabelaArquivos);
    	List<Resultado> lista = new ArrayList<Resultado>();
    	
    	if (args.length == 3 && args[2].equalsIgnoreCase("lista")) {
    	    lista = arvore.lista(similaridade);
    	    Main.printarSaida(similaridade, lista, arvore);
    	    Main.gerarLog(NOME_ARQUIVO_LOG, similaridade, lista, arvore); 

    	} else if (args.length == 4 && args[2].equalsIgnoreCase("topK")) {
    	    int K = Integer.parseInt(args[3]);
    	    lista = arvore.topK(K, similaridade);
    	    Main.printarSaida(similaridade, lista, arvore);
    	    Main.gerarLog(NOME_ARQUIVO_LOG, similaridade, lista, arvore);

    	} else if (args.length == 5 && args[2].equalsIgnoreCase("busca")) {
    	    double resultadoBusca = Main.busca(args[3], args[4], tabelaArquivos);
    	    Main.printarSaida(args[3], args[4], resultadoBusca);
    	    Main.gerarLog(NOME_ARQUIVO_LOG, args[3], args[4], resultadoBusca);

    	} else {
    	    System.out.println("❌ Comando inválido! Use:");
    	    System.out.println("   java projeto/Main docs <similaridade> lista");
    	    System.out.println("   java projeto/Main docs <similaridade> topK <K>");
    	    System.out.println("   java projeto/Main docs <similaridade> busca <doc1> <doc2>");
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
                Documento doc = new Documento(f.getPath());
                tabelaArquivos.put(f.getName(), doc);				
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
    
    private static String formatarSaidaLista(double similaridade, List<Resultado> lista, AVL arvore) {
        StringBuilder sb = new StringBuilder();
        String separadorLinha = System.lineSeparator(); 

        sb.append("=== VERIFICADOR DE SIMILARIDADE DE TEXTOS ===").append(separadorLinha);
    	sb.append("Total de documentos processados: ").append(contadorDocumentos).append(separadorLinha);
    	sb.append("Total de pares comparados: ").append(contadorComparacoes).append(separadorLinha);
    	sb.append("Função hash utilizada: Double Hashing").append(separadorLinha);
    	sb.append("Métrica de similaridade: Cosseno").append(separadorLinha);
    	sb.append("Pares com similaridade >= ").append(similaridade).append(":").append(separadorLinha);
    	sb.append("---------------------------------").append(separadorLinha);
    	
        for (Resultado r : lista) {
    		sb.append(r.getArquivoA()).append(" <-> ").append(r.getArquivoB()).append(" = ").append(r.getSimilaridade()).append(separadorLinha);
    	}
    	
        sb.append("Pares com menor similaridade:").append(separadorLinha);
    	sb.append("---------------------------------").append(separadorLinha);
    	
        Resultado menor = arvore.getResultadoMenorSimilaridade();
    	if (menor != null) {
            sb.append(menor.getArquivoA()).append(" <-> ").append(menor.getArquivoB()).append(" = ").append(menor.getSimilaridade()).append(separadorLinha);
        } else {
            sb.append("N/A (nenhum par comparado)").append(separadorLinha);
        }
        
        return sb.toString();
    }

    private static String formatarSaidaBusca(String arquivoA, String arquivoB, double similaridade) {
        StringBuilder sb = new StringBuilder();
        String separadorLinha = System.lineSeparator();

        sb.append("=== VERIFICADOR DE SIMILARIDADE DE TEXTOS ===").append(separadorLinha);
    	sb.append("Comparada: ").append(arquivoA).append(" <-> ").append(arquivoB).append(separadorLinha);
    	sb.append("Similaridade calculada: ").append(similaridade).append(separadorLinha);
    	sb.append("Métrica de similaridade: Cosseno").append(separadorLinha);
        
        return sb.toString();
    }
    
    public static void printarSaida(double similaridade, List<Resultado> lista, AVL arvore) {
    	String saida = formatarSaidaLista(similaridade, lista, arvore);
        System.out.println(saida);
    }
    
    public static void printarSaida(String arquivoA, String arquivoB, double similaridade) {
    	String saida = formatarSaidaBusca(arquivoA, arquivoB, similaridade);
        System.out.println(saida);
    }

    public static void gerarLog(String nomeArquivoLog, double similaridade, List<Resultado> lista, AVL arvore) throws IOException {
        String conteudoLog = formatarSaidaLista(similaridade, lista, arvore);
        Path caminhoLog = Path.of(nomeArquivoLog);
        
        Files.writeString(caminhoLog, conteudoLog, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING); 
        
        System.out.println("Log gerado em: " + caminhoLog.toAbsolutePath());
    }

    public static void gerarLog(String nomeArquivoLog, String arquivoA, String arquivoB, double similaridade) throws IOException {
        String conteudoLog = formatarSaidaBusca(arquivoA, arquivoB, similaridade);
        Path caminhoLog = Path.of(nomeArquivoLog);
        
        Files.writeString(caminhoLog, conteudoLog, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        
        System.out.println("Log gerado em: " + caminhoLog.toAbsolutePath());
    }
}
