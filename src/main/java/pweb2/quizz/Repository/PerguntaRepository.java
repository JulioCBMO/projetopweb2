package pweb2.quizz.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pweb2.quizz.model.Pergunta;

public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {

    
    
}
