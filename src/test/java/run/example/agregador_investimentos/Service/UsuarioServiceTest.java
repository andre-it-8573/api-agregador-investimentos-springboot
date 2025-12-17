package run.example.agregador_investimentos.Service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import run.example.agregador_investimentos.Entities.RequestUsuario;
import run.example.agregador_investimentos.Entities.Usuario;
import run.example.agregador_investimentos.Repository.UsuarioRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
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

    @Captor
    private ArgumentCaptor<Usuario> argumentosRequisicaoUsuario;

    @Captor
    private ArgumentCaptor<UUID> argumentosRequisicaoRetornoEmUuid;


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

            // Validacao do repositorio sempre que o metodo .save() é chamado para o molde do captor
            doReturn(usuario).when(usuarioRepository).save(argumentosRequisicaoUsuario.capture());

            var input = new RequestUsuario("usuario",
                                            "email@email.com",
                                            "");

            var output = usuarioService.registrarUsuario(input);

            // Validação se a chamada do metodo .registrarUsuario com a DTO não tem retorno nulo
            assertNotNull(output);

            // Validação se o metodo processou seguindo o padrao de campos do captor
            var usuarioCapturado = argumentosRequisicaoUsuario.getValue();

            assertEquals(input.nomeUsuario(), usuarioCapturado.getNomeUsuario());
            assertEquals(input.emailUsuario(), usuarioCapturado.getEmailUsuario());
            assertEquals(input.senhaUsuario(), usuarioCapturado.getSenhaUsuario());
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

    @Nested
    class buscarUsuarioPeloId {
        @Test
        @DisplayName("Quando bem sucedido com optional presente, deve retornar usuário pelo id")
        void deveRetornarUsuarioPorIdComSucessoQuandoTemOptional() {
            var usuario = new Usuario(
                    UUID.randomUUID(),
                    "usuario_teste",
                    "email@teste.com",
                    "senha_hash",
                    Instant.now(),
                    null,
                    true
            );

            doReturn(Optional.of(usuario)).when(usuarioRepository).findById(argumentosRequisicaoRetornoEmUuid.capture());

            var output = usuarioService.buscarUsuarioPeloId(usuario.getIdUsuario().toString());

            assertTrue(output.isPresent());

            // Garantia de que os dados do usuário informado são os mesmos do captor
            assertEquals(usuario.getIdUsuario(), argumentosRequisicaoRetornoEmUuid.getValue());
        }

        @Test
        @DisplayName("Quando bem sucedido com optional ausente, deve retornar usuário pelo id")
        void deveRetornarUsuarioPorIdComSucessoQuandoNaoTemOptional() {
            var idUsuario = UUID.randomUUID();

            doReturn(Optional.empty()).when(usuarioRepository).findById(argumentosRequisicaoRetornoEmUuid.capture());

            var output = usuarioService.buscarUsuarioPeloId(idUsuario.toString());

            assertTrue(output.isEmpty());

            assertEquals(idUsuario, argumentosRequisicaoRetornoEmUuid.getValue());
        }
    }

    @Nested
    class listarUsuarios{
        @Test
        @DisplayName("Deve retornar todos os usuários com sucesso")
        void deveRetornarTodosOsUsuariosComSucesso(){
            var usuario = new Usuario(
                    UUID.randomUUID(),
                    "usuario_teste",
                    "email@teste.com",
                    "senha_hash",
                    Instant.now(),
                    null,
                    true
            );

            var listaUsuarios = List.of(usuario);

            doReturn(listaUsuarios)
                    .when(usuarioRepository)
                    .findAllByActiveTrue();

            var output = usuarioService.listarUsuarios();

            assertNotNull(output);

            // listarUsuarios() não retorna null
            assertEquals(listaUsuarios.size(), output.size());
        }
    }
}