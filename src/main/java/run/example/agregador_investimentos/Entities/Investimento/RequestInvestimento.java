package run.example.agregador_investimentos.Entities.Investimento;

import java.util.UUID;

public record RequestInvestimento(
        String idAcaoInvestimento,
        Integer quantidade
) {
}