package pweb2.quizz.model;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
@Data
public class Corrida implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String descricao;

    private Integer tempoSegundos;

    private Boolean ativa;

    @OneToMany(mappedBy = "corrida", cascade = CascadeType.ALL)
    private List<Pergunta> perguntas;
}
