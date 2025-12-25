package run.example.agregador_investimentos.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.example.agregador_investimentos.Entities.AcaoInvestimento.RequestAcaoInvestimento;
import run.example.agregador_investimentos.Entities.AcaoInvestimento.ResponseAcaoInvestimento;
import run.example.agregador_investimentos.Entities.Usuario.RequestUsuario;
import run.example.agregador_investimentos.Entities.Usuario.ResponseUsuario;
import run.example.agregador_investimentos.Service.AcaoInvestimentoService;

import java.net.URI;

@RestController
@RequestMapping("/v1/acoes")
public class AcaoInvestimentoController {
    private AcaoInvestimentoService acaoInvestimentoService;

    public AcaoInvestimentoController(AcaoInvestimentoService acaoInvestimentoService) {
        this.acaoInvestimentoService = acaoInvestimentoService;
    }

    // Também atualiza e não gera duplicação de ação
    @PostMapping
    public ResponseEntity<ResponseAcaoInvestimento> registrarAcao(@RequestBody RequestAcaoInvestimento requestAcaoInvestimento){
        acaoInvestimentoService.registrarAcao(requestAcaoInvestimento);
        //  HTTP 201 (Created) e URI da criação junto do Id
        return ResponseEntity.ok().build();
    }
}
