package pweb2.quizz.model;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
@Data
public class Participante implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    
    private String email;

    private boolean admin;

    @ManyToMany
    @JoinTable(name = "participante_corrida")
    private List<Corrida> corridasFeitas;

}
