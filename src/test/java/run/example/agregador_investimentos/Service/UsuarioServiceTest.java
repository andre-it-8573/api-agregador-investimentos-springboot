package run.example.agregador_investimentos.Service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import run.example.agregador_investimentos.Entities.Usuario.RequestUsuario;
import run.example.agregador_investimentos.Entities.Usuario.Usuario;
import run.example.agregador_investimentos.Exceptions.ExcecaoUsuarioNaoEncontrado;
import run.example.agregador_investimentos.Repository.UsuarioRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        void deveCriarUsuarioComSucesso() {

            var usuario = new Usuario();
            usuario.setIdUsuario(UUID.randomUUID());
            usuario.setNomeUsuario("usuario_teste");
            usuario.setEmailUsuario("email@teste.com");
            usuario.setSenhaUsuario("senha_hash");
            usuario.setCriacao_entidade(Instant.now());
            usuario.setActive(true);

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
        void deveRetornarExcecaoQuandoOcorreErro() {

            doThrow(new RuntimeException()).when(usuarioRepository).save(any());

            var input = new RequestUsuario("usuario",
                    "email@email.com",
                    "");
            // Não testar apenas caminhos de sucesso, e sim todos os cenários, especialmente críticos
            assertThrows(RuntimeException.class, () -> usuarioService.registrarUsuario(input));

        }
    }

    @Nested
    class buscarUsuarioPeloId {
        @Test
        @DisplayName("Quando bem sucedido com optional presente, deve retornar usuário pelo id")
        void deveRetornarUsuarioPorIdComSucessoQuandoTemOptional() {
            var usuario = new Usuario();
            usuario.setIdUsuario(UUID.randomUUID());
            usuario.setNomeUsuario("usuario_teste");
            usuario.setEmailUsuario("email@teste.com");
            usuario.setSenhaUsuario("senha_hash");
            usuario.setCriacao_entidade(Instant.now());
            usuario.setActive(true);
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
    class listarUsuarios {
        @Test
        @DisplayName("Deve retornar todos os usuários com sucesso")
        void deveRetornarTodosOsUsuariosComSucesso() {
            var usuario = new Usuario();
            usuario.setIdUsuario(UUID.randomUUID());
            usuario.setNomeUsuario("usuario_teste");
            usuario.setEmailUsuario("email@teste.com");
            usuario.setSenhaUsuario("senha_hash");
            usuario.setCriacao_entidade(Instant.now());
            usuario.setActive(true);

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

    @Nested
    class deletarUsuario {
        @Test
        @DisplayName("Deve deletar usuario com sucesso quando o mesmo existe")
        void deveDeletarUsuarioComSucesso() {
            var idUsuario = UUID.randomUUID();
            var usuario = new Usuario();
            usuario.setIdUsuario(UUID.randomUUID());
            usuario.setNomeUsuario("usuario_teste");
            usuario.setEmailUsuario("email@teste.com");
            usuario.setSenhaUsuario("senha_hash");
            usuario.setCriacao_entidade(Instant.now());
            usuario.setActive(true);

            doReturn(Optional.of(usuario))
                    .when(usuarioRepository).findById(idUsuario);
            usuarioService.deletarUsuario(idUsuario.toString());
            verify(usuarioRepository, times(1)).findById(idUsuario);
            assertFalse(usuario.getActive(), "O campo active deve ser false após a deleção lógica");

            // Verifica se o metodo foi chamado
            verify(usuarioRepository).findById(idUsuario);
            assertFalse(usuario.getActive());
        }

        @Test
        @DisplayName("Não deve deletar usuario com sucesso quando o mesmo não existe")
        void naoDeveDeletarUsuarioQuandoNaoExiste() {
            var idUsuario = UUID.randomUUID().toString();

            doReturn(Optional.empty())
                    .when(usuarioRepository).findById(any(UUID.class));

            assertThrows(ExcecaoUsuarioNaoEncontrado.class, () -> {
                usuarioService.deletarUsuario(idUsuario);
            });

            verify(usuarioRepository, times(1)).findById(UUID.fromString(idUsuario));
        }
    }

    @Nested
    class atualizarUsuario {
        @Test
        @DisplayName("Deve atualizar usuario com sucesso se o usuário está presente, bem como seu nome e senha")
        void deveAtualizarUsuarioExistenteQuandoNomeESenhaEstaoPreenchidos(){
            var idUsuario = UUID.randomUUID();
            var usuario = new Usuario();
            usuario.setIdUsuario(UUID.randomUUID());
            usuario.setNomeUsuario("usuario_teste");
            usuario.setEmailUsuario("email@teste.com");
            usuario.setSenhaUsuario("senha_hash");
            usuario.setCriacao_entidade(Instant.now());
            usuario.setActive(true);
            var atualizacaoUsuarioDto = new RequestUsuario(
                    "Usuario para teste",
                    "testando@gmail.com",
                    "senha-segura"
            );

            doReturn(Optional.of(usuario))
                    .when(usuarioRepository).findById(idUsuario);

            doReturn(usuario)
                    .when(usuarioRepository).save(any(Usuario.class));

            usuarioService.atualizarUsuario(atualizacaoUsuarioDto, idUsuario.toString());

            verify(usuarioRepository, times(1)).findById(idUsuario);

            verify(usuarioRepository, times(1)).save(argumentosRequisicaoUsuario.capture());

            var usuarioCapturado = argumentosRequisicaoUsuario.getValue();

            assertEquals(atualizacaoUsuarioDto.nomeUsuario(), usuarioCapturado.getNomeUsuario());
            assertEquals(atualizacaoUsuarioDto.senhaUsuario(), usuarioCapturado.getSenhaUsuario());
            assertEquals(atualizacaoUsuarioDto.emailUsuario(), usuarioCapturado.getEmailUsuario());
        }

        @Test
        @DisplayName("Não deve atualizar se o usuário não existe")
        void naoDeveAtualizarUsuarioSeNaoExiste(){
            var idUsuario = UUID.randomUUID().toString();
            var atualizacaoUsuarioDto = new RequestUsuario(
                    "Nome Teste",
                    "teste@gmail.com",
                    "senha123"
            );

            doReturn(Optional.empty())
                    .when(usuarioRepository).findById(UUID.fromString(idUsuario));

            assertThrows(ExcecaoUsuarioNaoEncontrado.class, () -> {
                usuarioService.atualizarUsuario(atualizacaoUsuarioDto, idUsuario);
            });

            verify(usuarioRepository, never()).save(any(Usuario.class));
            verify(usuarioRepository, times(1)).findById(UUID.fromString(idUsuario));
        }
    }
}
