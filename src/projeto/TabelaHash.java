package projeto;

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

    private int dispersao2(String palavra, int erro) {
        return ((palavra.hashCode() + erro) & 0x7fffffff) % this.m;
    } 

    public void inserir(String palavra, int frequencia) {
        int h1 = dispersao1(palavra);
        int h2 = dispersao2(palavra, 0);

        int index = h1;
        int tentativas = 0;

        while (dicio[index] != null && !dicio[index].getPalavra().equals(palavra)) {
            index = (h1 + tentativas * h2) % m;
            tentativas++;
            qtdColisoes++;

            if (tentativas > m) {
                System.err.println("TabelaHash cheia! Não foi possível inserir: " + palavra);
                return;
            }
        }

        // Se a palavra já existir, soma a frequência
        if (dicio[index] != null && dicio[index].getPalavra().equals(palavra)) {
            dicio[index].setFrequencia(dicio[index].getFrequencia() + frequencia);
        } else {
            dicio[index] = new NoHash(palavra, frequencia);
        }
    }

    public int busca(String palavra) {
    int h1 = dispersao1(palavra);
    int h2 = dispersao2(palavra, 0);

    int index = h1;
    int tentativas = 0;

    while (this.dicio[index] != null &&
           !this.dicio[index].getPalavra().equals(palavra) &&
           tentativas <= m) {

        index = (h1 + tentativas * h2) % m;
        tentativas++;
    }

    if (this.dicio[index] != null &&
        this.dicio[index].getPalavra().equals(palavra)) {
        return this.dicio[index].getFrequencia();
    }

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
