package run.example.agregador_investimentos.Service;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import run.example.agregador_investimentos.Entities.Conta.Conta;
import run.example.agregador_investimentos.Entities.Conta.RequestConta;
import run.example.agregador_investimentos.Entities.Conta.ResponseConta;
import run.example.agregador_investimentos.Entities.EnderecoCobranca.EnderecoCobranca;
import run.example.agregador_investimentos.Repository.ContaRepository;
import run.example.agregador_investimentos.Repository.EnderecoCobrancaRepository;
import run.example.agregador_investimentos.Repository.UsuarioRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ContaService {
    private final UsuarioRepository usuarioRepository;
    private final EnderecoCobrancaRepository enderecoCobrancaRepository;
    private final ContaRepository contaRepository;

    public ContaService(UsuarioRepository usuarioRepository,
                        EnderecoCobrancaRepository enderecoCobrancaRepository,
                        ContaRepository contaRepository){

        this.usuarioRepository = usuarioRepository;
        this.enderecoCobrancaRepository = enderecoCobrancaRepository;
        this.contaRepository = contaRepository;
    }

    public List<ResponseConta> listarContasPorUsuario(String idUsuario){
        var usuario = usuarioRepository.findById(UUID.fromString(idUsuario))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        //Retorna uma lista de DTOs
        return usuario.getContas()
                .stream()
                .map(ac ->
                        new ResponseConta(ac.getIdConta(), ac.getDescricao()))
                .toList();
    }

    @Transactional
    public void criarConta(String idUsuario, RequestConta requestConta){
        var usuario = usuarioRepository.findById(UUID.fromString((idUsuario)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var conta = new Conta();
        conta.setUsuario(usuario);
        conta.setDescricao(requestConta.descricao());

        var enderecoCobranca = new EnderecoCobranca();
        enderecoCobranca.setRua(requestConta.rua());
        enderecoCobranca.setNumero_casa(requestConta.numero());

        enderecoCobranca.setConta(conta);
        conta.setEnderecoCobranca(enderecoCobranca);

        contaRepository.save(conta);

        enderecoCobrancaRepository.save(enderecoCobranca);
    }
}
