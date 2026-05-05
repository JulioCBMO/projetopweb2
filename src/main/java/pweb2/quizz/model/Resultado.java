package pweb2.quizz.model;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Resultado implements Serializable {
    private static final long serialVersionUID = 1L; 

    private Long id;

    private Participante participante;

    private Corrida corrida;

    private BigDecimal pontuacao;

    private LocalDateTime dataHora;
    
}
