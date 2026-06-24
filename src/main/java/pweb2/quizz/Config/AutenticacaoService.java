package pweb2.quizz.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pweb2.quizz.Repository.ParticipanteRepository;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private ParticipanteRepository participanteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return participanteRepository.findByNome(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilizador não encontrado: " + username));
    }
}