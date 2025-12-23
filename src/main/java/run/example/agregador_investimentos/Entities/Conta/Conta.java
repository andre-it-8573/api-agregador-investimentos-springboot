package run.example.agregador_investimentos.Entities.Conta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import run.example.agregador_investimentos.Entities.EnderecoCobranca.EnderecoCobranca;
import run.example.agregador_investimentos.Entities.Investimento.Investimento;
import run.example.agregador_investimentos.Entities.Usuario.Usuario;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_conta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "cd_tabela")
    private UUID idConta;

    @ManyToOne
    @JoinColumn(name = "usuario_id") // FK
    private Usuario usuario;

    // O que é feito em um é automaticamente refletido no outro
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "conta")
    @PrimaryKeyJoinColumn
    private EnderecoCobranca enderecoCobranca;

    @Column(name = "ds_descricao_conta")
    private String descricao;

    // Listagem de investimentos da conta
    @OneToMany(mappedBy = "conta")
    private List<Investimento> investimentosConta;
}
