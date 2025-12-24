package run.example.agregador_investimentos.Service;

import org.springframework.stereotype.Service;
import run.example.agregador_investimentos.Entities.AcaoInvestimento.AcaoInvestimento;
import run.example.agregador_investimentos.Entities.AcaoInvestimento.RequestAcaoInvestimento;
import run.example.agregador_investimentos.Repository.AcaoInvestimentoRepository;
import run.example.agregador_investimentos.Repository.ContaRepository;
import run.example.agregador_investimentos.Repository.EnderecoCobrancaRepository;
import run.example.agregador_investimentos.Repository.UsuarioRepository;

@Service
public class AcaoInvestimentoService {
    private AcaoInvestimentoRepository acaoInvestimentoRepository;

    public AcaoInvestimentoService(AcaoInvestimentoRepository acaoInvestimentoRepository){

        this.acaoInvestimentoRepository = acaoInvestimentoRepository;
    }

    public void registrarAcao(RequestAcaoInvestimento requestAcaoInvestimento) {
        var acao = new AcaoInvestimento(
                requestAcaoInvestimento.acaoId(),
                requestAcaoInvestimento.descricao()
        );
        acaoInvestimentoRepository.save(acao);
    }
}
