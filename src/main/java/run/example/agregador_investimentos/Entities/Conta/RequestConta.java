package run.example.agregador_investimentos.Entities.Conta;

// DTO para conta e endereco de pagamento
public record RequestConta(
    String descricao,
    String rua,
    Integer numero
) {
}
