package pweb2.quizz.Repository;

import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import pweb2.quizz.model.Resultado;

public interface ResultadoRepository extends JpaRepository<Resultado, Long> {

  List<Resultado> findAllByOrderByPontuacaoDesc();

  boolean existsByParticipanteNomeAndCorridaId(String nome, Long corridaId);

  List<Resultado> findByParticipanteNome(String nome);
    

    
}
