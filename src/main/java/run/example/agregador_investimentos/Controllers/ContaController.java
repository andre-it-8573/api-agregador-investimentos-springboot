package run.example.agregador_investimentos.Controllers;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import run.example.agregador_investimentos.Entities.Conta.Conta;
import run.example.agregador_investimentos.Entities.Conta.RequestConta;
import run.example.agregador_investimentos.Entities.Conta.ResponseConta;
import run.example.agregador_investimentos.Entities.EnderecoCobranca.EnderecoCobranca;
import run.example.agregador_investimentos.Entities.Investimento.RequestInvestimento;
import run.example.agregador_investimentos.Entities.Investimento.ResponseInvestimento;
import run.example.agregador_investimentos.Service.ContaService;
import run.example.agregador_investimentos.Service.UsuarioService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/contas")
public class ContaController {
    private ContaService contaService;
    private UsuarioService usuarioService;

    public ContaController(ContaService contaService,
                           UsuarioService usuarioService){
        this.contaService = contaService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{idUsuario}/contas")
    public ResponseEntity<List<ResponseConta>> listarContasPorUsuario(@PathVariable("idUsuario") String idUsuario){
        var contas = contaService.listarContasPorUsuario(idUsuario);
        return ResponseEntity.ok(contas);
    }

    // Criação de conta e endereco de pagamento para determinado usuario
    @PostMapping("/{idUsuario}/contas")
    public ResponseEntity<ResponseConta> registrarConta(@PathVariable("idUsuario") String idUsuario,
                                                        @RequestBody RequestConta requestConta){
        contaService.criarConta(idUsuario, requestConta);
        return ResponseEntity.created(URI.create("/v1/users/" + idUsuario.toString())).build();
    }

    // Associação de ações a uma conta
    @PostMapping("/{idConta}/acoes")
    public ResponseEntity<Void> associarAcoesPraConta(@PathVariable("idConta") String idConta,
                                                                      @RequestBody RequestInvestimento requestInvestimento){
        contaService.associarAcoesPraConta(idConta, requestInvestimento);

        return ResponseEntity.ok().build();
    }

}
