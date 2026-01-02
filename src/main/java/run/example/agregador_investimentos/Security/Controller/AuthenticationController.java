package run.example.agregador_investimentos.Security.Controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @PostMapping("/login")
    public ResponseEntity login (@RequestBody @Valid AuthenticaticaoDTO authenticaticaoDTO){

    }
}
