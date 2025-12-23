package run.example.agregador_investimentos.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.example.agregador_investimentos.Entities.AcaoInvestimento.AcaoInvestimento;
import run.example.agregador_investimentos.Entities.Conta.Conta;
import run.example.agregador_investimentos.Entities.Usuario.Usuario;

import java.util.List;
import java.util.UUID;

@Repository
public interface AcaoInvestimentoRepository extends JpaRepository<AcaoInvestimento, String> {
    List<AcaoInvestimento> findAll();
}
