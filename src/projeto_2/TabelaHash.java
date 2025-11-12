package projeto_2;

/**
 * IMPLEMENTAÇÃO DA TABELA HASH MANUAL
 * * Esta classe é a nossa implementação manual da Tabela Hash.
 * Ela NÃO PODE usar o HashMap pronto do Java.
 * * Responsabilidades:
 * * 1. Armazenar pares (chave, valor). No contexto do 'Documento', será (Palavra, Frequência).
 * * 2. Implementar DUAS funções de dispersão (hash) distintas, conforme
 * exigido pelo projeto. OK
 * * 3. Implementar uma estratégia de tratamento de colisões (ex: Encadeamento
 * Separado ou Endereçamento Aberto). OK
 * * 4. Fornecer os métodos essenciais:
 * - put(chave, valor): Para inserir ou atualizar um par. OK
 * - get(chave): Para recuperar um valor. OK
 * * 5. Manter métricas para o relatório (ex: número de colisões). OK ??
 */

public class TabelaHash {
    private int m; //quantidade de palavras
    private NoHash[] dicio; //Vetor de frequências
    private int qtdColisoes;

    public TabelaHash(int m){
        this.m = m;
        this.dicio = new NoHash[m];
        this.qtdColisoes = 0;
    }

    private int dispersao1(String palavra){
        int aux = 0;
        for (int i = 0; i < palavra.length(); i++){
            aux = (31 * aux + palavra.charAt(i))%this.m;
        } //Atualiza aux fazendo (31·aux + ASCII(i)) pegando o resto da div por m.
        return aux;
    }

    private int dispersão2(String palavra, int erro){
        return ((palavra.hashCode()+erro) & 0x7fffffff) % this.m;
    } //Converte o hash da string para positivo e ajusta ao tamanho m com o resto da div.

    public void inserir(String palavra, int frequencia){
        int index = dispersão2(palavra, 0);
        NoHash aux = new NoHash(palavra, frequencia);
        int qtdColisoesLocal = 0;

        while(this.dicio[index] != null && !this.dicio[index].getPalavra().equals(palavra)){
            index = dispersão2(palavra, index+1);
            qtdColisoesLocal++;
        }

        if (this.dicio[index] == null || this.dicio[index].getPalavra().equals(palavra)){
            this.qtdColisoes += qtdColisoesLocal;
            this.dicio[index] = aux;
        }
        else System.out.println("Erro na inserção.");
    }

    public int busca(String palavra) {
        int index = dispersão2(palavra, 0);

        while(this.dicio[index] != null && !this.dicio[index].getPalavra().equals(palavra)){
            index = dispersão2(palavra, index+1);
        }
        
        if (this.dicio[index] != null && this.dicio[index].getPalavra().equals(palavra)) return this.dicio[index].getFrequencia();
        return -1;
    }

    public int getColisoes(){
        return this.qtdColisoes;
    }

    public int getM(){
        return this.m;
    }

    public NoHash at(int i) {
        return this.dicio[i];
    }

    public void printDicio(){
        for(int i = 0; i < this.m; i++){
            if(this.dicio[i] != null) {
                System.out.println(this.dicio[i].getPalavra()+ ": " + this.dicio[i].getFrequencia());
            }
        }
    }
}
