package run.example.agregador_investimentos.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import run.example.agregador_investimentos.Entities.Usuario.RolesUsuario;
import run.example.agregador_investimentos.Entities.Usuario.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    // Padrão JPA que abstrai as queries (select pelos ativos e autorização)
    List<Usuario> findAllByActiveTrue();
    UserDetails findByLogin(String email);
 }
