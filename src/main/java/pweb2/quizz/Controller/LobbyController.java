package pweb2.quizz.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;
import pweb2.quizz.Repository.CorridaRepository;

@Controller
public class LobbyController {
    
    @Autowired
    private CorridaRepository corridaRepository;

    @GetMapping("/lobby")
    public String exibirLobby(HttpSession session, Model model) {
        String participante = (String) session.getAttribute("participante");
        if (participante == null) {
            return "redirect:/login";
        }
        model.addAttribute("nomeParticipante", participante);
        model.addAttribute("corridas", corridaRepository.findByAtivaTrue());
        return "lobby";
    }
}
