package run.example.agregador_investimentos.Exceptions;

public class ExcecaoUsuarioNaoEncontrado extends ExcecaoBase {
    public ExcecaoUsuarioNaoEncontrado(String id){
        super("Usuário não encontrado para o id: " + id);
    }
}