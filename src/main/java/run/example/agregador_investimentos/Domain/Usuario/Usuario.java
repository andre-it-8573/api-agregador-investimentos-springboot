package run.example.agregador_investimentos.Domain.Usuario;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import run.example.agregador_investimentos.Domain.Conta.Conta;
import run.example.agregador_investimentos.Security.Enum.RolesUsuario;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// Metodos para comparacao comparando sem ser por referência (pelos dados em si) e fazendo uso de hash (mesmo valor, mesmo resultado)
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails { // Para autenticação
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "cd_usuario", columnDefinition = "BINARY(16)")
    private UUID idUsuario;

    @Column(name = "nm_usuario")
    private String nomeUsuario;

    @Column(name = "ds_email_usuario")
    private String emailUsuario;

    @Column(name = "ds_senha")
    private String senhaUsuario;

    @Enumerated(EnumType.STRING)
    private RolesUsuario role;

    // Registros automáticos de criação e atualização
    @CreationTimestamp
    @Column(name = "dt_criacao_usuario")
    private Instant criacao_entidade;

    @UpdateTimestamp
    @Column(name = "dt_atualizacao_usuario")
    private Instant atualizacao_entidade;

    // Para soft delete
    @Column(name = "fl_status")
    private Boolean active;

    @OneToMany(mappedBy = "usuario")
    private List<Conta> contas;

    // DTOs -> Entity
    public Usuario(RequestUsuario requestUsuario){
        this.nomeUsuario = requestUsuario.nomeUsuario();
        this.emailUsuario = requestUsuario.emailUsuario();
        this.senhaUsuario = requestUsuario.senhaUsuario();

        this.criacao_entidade = Instant.now();
        this.atualizacao_entidade = null;
        this.active = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // Para autorização (permissão com roles)
        if (this.role == RolesUsuario.ADMIN){return List.of(
                new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));}
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public @Nullable String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return emailUsuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
