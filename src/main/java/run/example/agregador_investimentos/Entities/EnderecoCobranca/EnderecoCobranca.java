package run.example.agregador_investimentos.Entities.EnderecoCobranca;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.engine.internal.Cascade;
import run.example.agregador_investimentos.Entities.Conta.Conta;

import java.util.UUID;

@Entity
@Table(name = "tb_endereco_cobranca")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoCobranca {

    @Id
    @Column(name = "cd_tabela")
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    // Com @PrimaKeyJoinColumn, garante que que o id do endereco de cobranca definido é o mesmo
    // que o da conta no relacionamento 1:1 (PK de conta e o mesmo do endereco de cobranca)
    @MapsId
    @JoinColumn(name = "cd_tabela")
    private Conta conta;

    @Column(name = "ds_rua")
    private String rua;

    @Column(name = "cd_numero_casa")
    private Integer numero_casa;
}
