package pweb2.quizz.model;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class Corrida implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private long id;

    private String titulo;

    private String descricao;

    private Integer tempoSegundos;

    private Boolean ativa;

    private List<Pergunta> perguntas;
}
