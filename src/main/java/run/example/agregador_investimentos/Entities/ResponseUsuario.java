package run.example.agregador_investimentos.Entities;

import java.util.UUID;

public record ResponseUsuario(
        UUID idUsuario,
        String nomeUsuario,
        String emailUsuario
) {
    // Metodo para instanciar DTO de resposta ao instanciar a classe
    public static ResponseUsuario fromEntity(Usuario usuario){
        return new ResponseUsuario(
        usuario.getIdUsuario(),
        usuario.getNomeUsuario(),
        usuario.getEmailUsuario()
        );
    }
}

