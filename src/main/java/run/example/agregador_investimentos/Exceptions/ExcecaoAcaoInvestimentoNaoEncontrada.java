package run.example.agregador_investimentos.Exceptions;

public class ExcecaoAcaoInvestimentoNaoEncontrada extends ExcecaoBase {
    public ExcecaoAcaoInvestimentoNaoEncontrada(String id){
        super("Ação não encontrada para a identificação fornecida");
    }
}
