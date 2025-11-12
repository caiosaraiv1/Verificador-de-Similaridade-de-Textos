package projeto_2;

import java.io.*;
import java.util.*;

/**
 * CLASSE DE REPRESENTAÇÃO DO DOCUMENTO
 * * Esta classe representa um único arquivo de texto. Cada objeto 'Documento'
 * será responsável por:
 * * 1. Armazenar o nome do arquivo (ex: "doc1.txt").
 * * 2. Conter sua própria 'HashTable' interna.
 * * 3. Executar todo o pré-processamento do seu texto:
 * - Normalização: Converter para minúsculas.
 * - Limpeza: Remover pontuação e caracteres não alfanuméricos.
 * - Tokenização: Quebrar o texto em palavras (tokens).
 * - Remoção de Stop-words: Filtrar palavras comuns (ex: "de", "para", "um").
 * * 4. Popular sua 'HashTable' interna com o vocabulário e a frequência
 * de cada palavra (ex: <"estrutura", 1>, <"dados", 1>).
 */

 //KAIQUE PAIVA FARÁ A CLASSE DOCUMENTO!!!!
public class Documento {
	
	private String nomeArquivo;
	private TabelaHash tabela;
	private static final int TAMANHO_HASH = 500;
	
	public Documento(String caminhoArquivo) throws IOException {
	    this.nomeArquivo = new File(caminhoArquivo).getName();
	    this.tabela = new TabelaHash(TAMANHO_HASH);
	    processarArquivo(caminhoArquivo);
	}
	
	private String lerArquivo(String caminhoArquivo) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
	        String linha;
	        while ((linha = br.readLine()) != null) {
	            sb.append(linha).append(" ");
	        }
	    }
	    return sb.toString();
	}
	
	private List<String> preprocessar(String texto) {
        texto = texto.toLowerCase();
        texto = texto.replaceAll("[^a-zà-ú0-9\\s]", " ");
        texto = texto.replaceAll("\\s+", " ");
        String[] separado = texto.trim().split(" ");

        List<String> palavra = new ArrayList<>();
        for (String p : separado) {
            if (!StopWords.eStopWord(p) && !p.isEmpty()) {
                palavra.add(p);
            }
        }
        return palavra;
    }
	
	private void processarArquivo(String caminhoArquivo) throws IOException {
	    String conteudo = lerArquivo(caminhoArquivo);
	    List<String> palavras = preprocessar(conteudo);

	    for (String palavra : palavras) {
	        tabela.inserir(palavra, 1);
	    }
	}
	
	public String getNomeArquivo() {
        return nomeArquivo;
    }

    public TabelaHash getTabela() {
        return tabela;
    }

    public void printTabela() {
        System.out.println("=== " + nomeArquivo + " ===");
        tabela.printDicio();
    }

	
	
}
