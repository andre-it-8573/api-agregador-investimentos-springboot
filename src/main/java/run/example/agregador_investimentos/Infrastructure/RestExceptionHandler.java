package run.example.agregador_investimentos.Infrastructure;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import run.example.agregador_investimentos.Exceptions.ExcecaoAcaoInvestimentoNaoEncontrada;
import run.example.agregador_investimentos.Exceptions.ExcecaoContaNaoEncontrada;
import run.example.agregador_investimentos.Exceptions.ExcecaoUsuarioNaoEncontrado;


// Tratamento global de exceções da aplicação e gerais do Java
// (Service chama exceção de domínio e controller advice o converte em HTTP)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    // Exceções de domínio
    @ExceptionHandler(ExcecaoUsuarioNaoEncontrado.class)
    public ResponseEntity<MensagemErroRest> handleUsuarioNaoEncontrado(
            ExcecaoUsuarioNaoEncontrado ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MensagemErroRest(
                        HttpStatus.NOT_FOUND,
                        ex.getMessage()
                ));
    }
    @ExceptionHandler(ExcecaoContaNaoEncontrada.class)
    public ResponseEntity<MensagemErroRest> handleContaNaoEncontrada(
            ExcecaoContaNaoEncontrada ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MensagemErroRest(
                        HttpStatus.NOT_FOUND,
                        ex.getMessage()
                ));
    }
    @ExceptionHandler(ExcecaoAcaoInvestimentoNaoEncontrada.class)
    public ResponseEntity<MensagemErroRest> handleAcaoNaoEncontrada(
            ExcecaoAcaoInvestimentoNaoEncontrada ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MensagemErroRest(
                        HttpStatus.NOT_FOUND,
                        ex.getMessage()
                ));
    }

    // Violação de integridade de dados (FK, unique, etc.)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<MensagemErroRest> handleDataIntegrityViolation(
            DataIntegrityViolationException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new MensagemErroRest(
                        HttpStatus.CONFLICT,
                        "Violação de integridade dos dados"
                ));
    }

    // Para erros não mapeados
    @ExceptionHandler(Exception.class)
    public ResponseEntity<MensagemErroRest> excecaoGenerica(Exception e){
        MensagemErroRest mensagemErroRest = new MensagemErroRest(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro interno no servidor.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensagemErroRest);
    }
}
