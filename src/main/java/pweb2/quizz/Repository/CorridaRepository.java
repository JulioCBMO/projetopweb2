package pweb2.quizz.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pweb2.quizz.model.Corrida;

public interface CorridaRepository extends JpaRepository<Corrida, Long> {
    List<Corrida> findByAtivaTrue();
    
}