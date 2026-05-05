package pweb2.quizz.model;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class Participante implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    
    private String nome;
    
    private String email;

    private boolean admin;

    private List<Corrida> corridasFeitas;

}
