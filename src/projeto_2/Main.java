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
    }
    
}
