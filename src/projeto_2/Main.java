package projeto_2;

/**
 * CLASSE PRINCIPAL (ORQUESTRADORA) Esta classe é o ponto de entrada do
 * programa. Suas responsabilidades são: * 1. Ler e validar os argumentos da
 * linha de comando: - <diretorio_documentos>
 * - <limiar> (grau de similaridade) - <modo> (lista, topk, busca) -
 * [argumentos_opcionais] (como o 'K' do topk ou os nomes dos arquivos da busca)
 * * 2. Orquestrar todo o fluxo de processamento: - Chamar a leitura dos
 * arquivos do diretório. - Para cada arquivo, disparar a criação do objeto
 * 'Documento' (que fará o pré-processamento). - Iniciar o loop de comparação
 * entre todos os pares de documentos. - Inserir cada 'Resultado' na 'AVLTree'.
 * * 3. Gerar a saída: - Com base no <modo>, consultar a 'AVLTree' para obter os
 * resultados corretos. - Exibir a saída formatada no terminal. - Gravar a mesma
 * saída no arquivo "resultado.txt".
 */
public class Main {

    public static void main(String[] args) {

        //Testando classe Hash ------------------------------
        // cria a tabela com tamanho 10
        TabelaHash tabela = new TabelaHash(10);

        // inserindo palavras
        tabela.inserir("gato", 3);
        tabela.inserir("cachorro", 5);
        tabela.inserir("peixe", 2);
        tabela.inserir("gato", 7); // teste de atualização da mesma palavra

        try {
            System.out.println("Frequência de 'gato': " + tabela.busca("gato"));
            System.out.println("Frequência de 'cachorro': " + tabela.busca("cachorro"));
            System.out.println("Frequência de 'peixe': " + tabela.busca("peixe"));

            // Testando palavra que não existe (deve lançar exceção)
            System.out.println("Frequência de 'coelho': " + tabela.busca("coelho"));

        } catch (Exception e) {
            System.out.println("Exceção capturada: " + e.getMessage());
        }
        System.out.println("\n");
        tabela.printDicio();
        System.out.println("Quantidade de colisões: " + tabela.getColisoes());
        //Fim do teste - apagar dps ------------------

        // ========================= TESTE DO COMPARADOR (COSSSENO) =========================
        System.out.println("\n=== Teste ComparadorCosseno ===");

// Doc A
        TabelaHash docA = new TabelaHash(17);
        docA.inserir("dados", 3);
        docA.inserir("ciência", 2);
        docA.inserir("computação", 1);

// Doc B
        TabelaHash docB = new TabelaHash(17);
        docB.inserir("dados", 1);
        docB.inserir("ciência", 1);
        docB.inserir("computação", 4);

        ComparadorCosseno comp1 = new ComparadorCosseno(docA, docB);
        double sim1 = comp1.similaridade();
        System.out.println("Similaridade (docA x docB): " + sim1);

// --- Caso idêntico (espera ~1.0)
        TabelaHash docC = new TabelaHash(17);
        docC.inserir("dados", 3);
        docC.inserir("ciência", 2);
        docC.inserir("computação", 1);

        ComparadorCosseno comp2 = new ComparadorCosseno(docA, docC);
        double sim2 = comp2.similaridade();
        System.out.println("Similaridade (docA x docC - idênticos): " + sim2);

// --- Caso disjunto (espera ~0.0)
        TabelaHash docD = new TabelaHash(17);
        docD.inserir("estruturas", 5);
        docD.inserir("algoritmos", 2);

        ComparadorCosseno comp3 = new ComparadorCosseno(docA, docD);
        double sim3 = comp3.similaridade();
        System.out.println("Similaridade (docA x docD - disjuntos): " + sim3);

    }

}
