package pweb2.quizz.model;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Data
public class Pergunta implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String enunciado;

    @ElementCollection
    private List<String> alternativas;

    private Integer respostaCorreta;

    @ManyToOne
    @JoinColumn(name = "corrida_id")
    private Corrida corrida;

}
