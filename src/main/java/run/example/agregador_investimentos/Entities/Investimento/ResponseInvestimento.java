package run.example.agregador_investimentos.Entities.Investimento;

import java.util.UUID;

public record ResponseInvestimento(
        UUID idConta,
        String idAcaoInvestimento,
        Integer quantidade
) {
    public static ResponseInvestimento fromEntity(Investimento investimento) {
        return new ResponseInvestimento(
        // Extrai os valores individuais da PK Composta
                investimento.getId().getIdConta(),
                investimento.getId().getIdAcaoInvestimento(),
                investimento.getQuantidade()
        );
    }
}