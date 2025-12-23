package run.example.agregador_investimentos.Service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import run.example.agregador_investimentos.Entities.Conta.Conta;
import run.example.agregador_investimentos.Entities.Conta.RequestConta;
import run.example.agregador_investimentos.Entities.EnderecoCobranca.EnderecoCobranca;
import run.example.agregador_investimentos.Entities.Usuario.RequestUsuario;
import run.example.agregador_investimentos.Entities.Usuario.ResponseUsuario;
import run.example.agregador_investimentos.Entities.Usuario.Usuario;
import run.example.agregador_investimentos.Repository.ContaRepository;
import run.example.agregador_investimentos.Repository.EnderecoCobrancaRepository;
import run.example.agregador_investimentos.Repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    // Injeção de dependência com @Service
    // Quando a classe é instanciada, o contrutor chama a classe que a implementa a interface repositório
    private UsuarioRepository usuarioRepository;
    private EnderecoCobrancaRepository enderecoCobrancaRepository;
    private ContaRepository contaRepository;

    private UsuarioService(UsuarioRepository usuarioRepository,
                           EnderecoCobrancaRepository enderecoCobrancaRepository,
                           ContaRepository contaRepository){

        this.usuarioRepository = usuarioRepository;
        this.enderecoCobrancaRepository = enderecoCobrancaRepository;
        this.contaRepository = contaRepository;

    }

    public List<ResponseUsuario> listarUsuarios(){
        List<Usuario> usuarios = usuarioRepository.findAllByActiveTrue();
        // Entity -> DTO usando Stream API, que pega a lista de entidades e as mapeia para a lista de DTOs de resposta
        return usuarios.stream()
                .map(ResponseUsuario::fromEntity)
                .collect(Collectors.toList());
    }

    // Com tipo Optional<T>, retorna DTO de resposta se tiver e Optional.empty() caso não possua
    public Optional<ResponseUsuario> buscarUsuarioPeloId(String idUsuario){
        var usuario = usuarioRepository.findById(UUID.fromString(idUsuario));
        return usuario.map(ResponseUsuario::fromEntity);
    }

    // Retornar somente Id, em vez da DTO. Tampouco, a classe inteira
    public UUID registrarUsuario(RequestUsuario requestUsuario){
        // DTO -> Entity
        Usuario novoUsuario = new Usuario(requestUsuario);
        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);
        return usuarioSalvo.getIdUsuario();
    }

    public void atualizarUsuario(RequestUsuario requestUsuario, String idUsuario){
        Optional<Usuario> optionalUsuario= usuarioRepository.findById(UUID.fromString(idUsuario));
        if (optionalUsuario.isPresent()){
            Usuario usuario = optionalUsuario.get();
            if (usuario.getNomeUsuario() != null &&
                usuario.getEmailUsuario() != null &&
                usuario.getSenhaUsuario() != null){
                usuario.setNomeUsuario(requestUsuario.nomeUsuario());
                usuario.setEmailUsuario(requestUsuario.emailUsuario());
                usuario.setSenhaUsuario(requestUsuario.senhaUsuario());
            }
            usuarioRepository.save(usuario);
        } else {
            throw new EntityNotFoundException();
        }
    }

    public void deletarUsuario(String idUsuario){
        Optional<Usuario> optionalUsuario= usuarioRepository.findById(UUID.fromString(idUsuario));
        if (optionalUsuario.isPresent()){
            Usuario usuario = optionalUsuario.get();
            usuario.setActive(false);
        } else {
            throw new EntityNotFoundException("Usuário não encontrado");
        }
    }


    public void criarConta(String idUsuario, RequestConta requestConta){
        var usuario = usuarioRepository.findById(UUID.fromString((idUsuario)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var conta = new Conta(
                UUID.randomUUID(),
                usuario,
                null,
                requestConta.descricao(),
                new ArrayList<>()
        );
        var contaCriada = contaRepository.save(conta);

        var enderecoPagamento = new EnderecoCobranca(
                contaCriada.getIdConta(),
                conta,
                requestConta.rua(),
                requestConta.numero()
        );
        enderecoCobrancaRepository.save(enderecoPagamento);
    }
}
