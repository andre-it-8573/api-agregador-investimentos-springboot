package run.example.agregador_investimentos.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import run.example.agregador_investimentos.Entities.Investimento.Investimento;
import run.example.agregador_investimentos.Entities.Investimento.InvestimentoId;
import run.example.agregador_investimentos.Entities.Usuario.Usuario;

import java.util.List;
import java.util.UUID;

@Repository
public interface InvestimentoRepository extends JpaRepository<Investimento, InvestimentoId> {
    List<Investimento> findAll();
}
