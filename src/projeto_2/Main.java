package projeto_2;

/**
 * CLASSE PRINCIPAL (ORQUESTRADORA)
 * Esta classe é o ponto de entrada do programa. Suas responsabilidades são:
 * * 1. Ler e validar os argumentos da linha de comando:
 * - <diretorio_documentos>
 * - <limiar> (grau de similaridade)
 * - <modo> (lista, topk, busca)
 * - [argumentos_opcionais] (como o 'K' do topk ou os nomes dos arquivos da busca) 
 * * 2. Orquestrar todo o fluxo de processamento:
 * - Chamar a leitura dos arquivos do diretório.
 * - Para cada arquivo, disparar a criação do objeto 'Documento' (que fará o pré-processamento).
 * - Iniciar o loop de comparação entre todos os pares de documentos.
 * - Inserir cada 'Resultado' na 'AVLTree'.
 * * 3. Gerar a saída:
 * - Com base no <modo>, consultar a 'AVLTree' para obter os resultados corretos.
 * - Exibir a saída formatada no terminal.
 * - Gravar a mesma saída no arquivo "resultado.txt".
 */
public class Main {

}
