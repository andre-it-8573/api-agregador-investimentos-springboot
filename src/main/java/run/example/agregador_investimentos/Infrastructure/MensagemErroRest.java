package run.example.agregador_investimentos.Infrastructure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class MensagemErroRest {
    private HttpStatus status;
    private String mensagem;
}
