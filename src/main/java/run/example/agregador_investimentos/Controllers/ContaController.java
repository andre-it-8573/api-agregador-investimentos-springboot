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
import run.example.agregador_investimentos.Service.ContaService;
import run.example.agregador_investimentos.Service.UsuarioService;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/v1/usuarios")
public class ContaController {
    private ContaService contaService;
    private UsuarioService usuarioService;

    public ContaController(ContaService contaService,
                           UsuarioService usuarioService){
        this.contaService = contaService;
        this.usuarioService = usuarioService;
    }

    // Criação de conta e endereco de pagamento para determinado usuario
    @PostMapping("/{idUsuario}/contas")
    public ResponseEntity<ResponseConta> registrarConta(@PathVariable("idUsuario") String idUsuario,
                                                        @RequestBody RequestConta requestConta){
        contaService.criarConta(idUsuario, requestConta);
        return ResponseEntity.created(URI.create("/v1/users/" + idUsuario.toString())).build();
    }
}
