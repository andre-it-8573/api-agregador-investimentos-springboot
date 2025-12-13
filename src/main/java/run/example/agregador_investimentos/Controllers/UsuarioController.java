package run.example.agregador_investimentos.Controllers;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import run.example.agregador_investimentos.Entities.RequestUsuario;
import run.example.agregador_investimentos.Entities.ResponseUsuario;
import run.example.agregador_investimentos.Entities.Usuario;
import run.example.agregador_investimentos.Service.UsuarioService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UsuarioController {

    // Mesmo princípio da classe de regra de negócio
    private final UsuarioService usuarioService;
    private UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios(){
        var usuarios = usuarioService.listarUsuarios();
        // HTTP 200 (OK)
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<ResponseUsuario> buscarUsuarioPeloId(@PathVariable("idUsuario") String idUsuario){
        var usuario = usuarioService.buscarUsuarioPeloId(idUsuario);
        if (usuario.isPresent()){
            // HTTP 200 (OK) com o DTO
            return ResponseEntity.ok(usuario.get());
        } else {
            // HTTP 404 (NOT FOUND)
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody RequestUsuario requestUsuario){
        var idUsuario = usuarioService.registrarUsuario(requestUsuario);
        //  HTTP 201 (Created) e URI da criação junto do Id
        return ResponseEntity.created(URI.create("/v1/users " + idUsuario.toString())).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable("id") String idUsuario){
        usuarioService.deletarUsuario(idUsuario);
        // HTTP 204 (No Content)
        return ResponseEntity.noContent().build();
    }
}
