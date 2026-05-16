package pweb2.quizz.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pweb2.quizz.model.Participante;

public interface ParticipanteRepository extends JpaRepository<Participante, Long> {
    Optional<Participante> findByNome(String nome);
  

    
}
