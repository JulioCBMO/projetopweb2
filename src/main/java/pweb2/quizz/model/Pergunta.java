package pweb2.quizz.model;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class Pergunta implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;

    private String enunciado;

    private List<String> alternativas;

    private Integer respostaCorreta;

    private Corrida corrida;

}
