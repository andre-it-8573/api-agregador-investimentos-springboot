package run.example.agregador_investimentos.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import run.example.agregador_investimentos.Entities.Usuario;

@RestController
@RequestMapping("/v1/users")
public class UsuarioController {

    @GetMapping("/{idUsuario}")
    public ResponseEntity<Usuario> buscarUsuarioPeloId(@PathVariable("idUsuario") int idUsuario){
        return null;
    }

    @PostMapping
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody String dadosUsuario){
        //
        return null;
    }
}
