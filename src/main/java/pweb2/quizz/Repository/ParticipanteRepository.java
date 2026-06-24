package pweb2.quizz.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pweb2.quizz.model.Participante;
import java.util.Optional;

public interface ParticipanteRepository extends JpaRepository<Participante, Long> {
    
    Optional<Participante> findByNome(String nome);
}