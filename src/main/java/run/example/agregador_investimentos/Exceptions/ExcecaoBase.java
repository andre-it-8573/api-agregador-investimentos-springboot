package run.example.agregador_investimentos.Exceptions;

public abstract class ExcecaoBase extends RuntimeException {

    public ExcecaoBase(String mensagem) { // Para mensagens de erro personalizadas
        super(mensagem);
    }
}