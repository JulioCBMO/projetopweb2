package pweb2.quizz.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;
import pweb2.quizz.Repository.CorridaRepository;
import pweb2.quizz.Repository.ResultadoRepository;

@Controller
public class LobbyController {
    
    @Autowired
    private CorridaRepository corridaRepository;

    @Autowired
    private ResultadoRepository resultadoRepository;

    @GetMapping("/lobby")
    public String exibirLobby(HttpSession session, Model model) {
    String participante = (String) session.getAttribute("participante");
    if (participante == null) {
        return "redirect:/login";
    }

    List<Long> corridasFeitas = resultadoRepository
        .findByParticipanteNome(participante)
        .stream()
        .map(r -> r.getCorrida().getId())
        .toList();

    model.addAttribute("nomeParticipante", participante);
    model.addAttribute("corridas", corridaRepository.findByAtivaTrue());
    model.addAttribute("corridasFeitas", corridasFeitas);
    return "lobby";
}
}
