package pweb2.quizz.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pweb2.quizz.model.Pergunta;

public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {

    Page<Pergunta> findByCorridaId(Long corridaId, Pageable pageable);
    
    
}
