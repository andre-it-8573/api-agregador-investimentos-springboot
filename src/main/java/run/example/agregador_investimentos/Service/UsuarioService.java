package run.example.agregador_investimentos.Service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import run.example.agregador_investimentos.Entities.RequestUsuario;
import run.example.agregador_investimentos.Entities.ResponseUsuario;
import run.example.agregador_investimentos.Entities.Usuario;
import run.example.agregador_investimentos.Repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {
    // Injeção de dependência com @Service
    // Quando a classe é instanciada, o contrutor chama a classe que a implementa a interface repositório
    private UsuarioRepository usuarioRepository;
    private UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarUsuarios(){
        var usuarios = usuarioRepository.findAllByActiveTrue();
        return usuarios;
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
            usuarioRepository.save(novoUsuario);
            return novoUsuario.getIdUsuario();
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
}
