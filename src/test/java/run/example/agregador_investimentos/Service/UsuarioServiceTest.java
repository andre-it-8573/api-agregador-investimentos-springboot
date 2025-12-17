package run.example.agregador_investimentos.Service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import run.example.agregador_investimentos.Entities.RequestUsuario;
import run.example.agregador_investimentos.Entities.Usuario;
import run.example.agregador_investimentos.Repository.UsuarioRepository;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {
    // Organizar classes e inputs (toda dependência precisa ser mockada)
    // Testar os métodos
    // Validar o resultado

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    // Uma classe por metodo, um metodo por teste
    @Nested
    class registrarUsuario {
        @Test
        @DisplayName("Deve criar um usuário com sucesso")
        void deveCriarUsuarioComSucesso(){

            var usuario = new Usuario(
                    UUID.randomUUID(),
                    "usuario_teste",
                    "email@teste.com",
                    "senha_hash",
                    Instant.now(),
                    null,
                    true
            );

            // Validacao do repositorio sempre que o metodo .save() é chamado
            doReturn(usuario).when(usuarioRepository).save(any());

            var input = new RequestUsuario("usuario",
                                            "email@email.com",
                                            "");

            var output = usuarioService.registrarUsuario(input);

            // Validação se a chamada do metodo .registrarUsuario com a DTO não tem retorno nulo
            assertNotNull(output);
        }

        @Test
        @DisplayName("Deve retornar uma exceção quando um erro acontecer")
        void deveRetornarExcecaoQuandoOcorreErro(){

            doThrow(new RuntimeException()).when(usuarioRepository).save(any());

            var input = new RequestUsuario("usuario",
                    "email@email.com",
                    "");
            // Não testar apenas caminhos de sucesso, e sim todos os cenários, especialmente críticos
            assertThrows(RuntimeException.class, ()-> usuarioService.registrarUsuario(input));

        }
    }

}