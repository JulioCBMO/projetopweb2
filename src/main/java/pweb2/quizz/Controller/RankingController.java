package pweb2.quizz.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import pweb2.quizz.Repository.ResultadoRepository;

@Controller
public class RankingController {

    @Autowired
    private ResultadoRepository resultadoRepository;

    @GetMapping("/ranking")
    public String exibirRanking(HttpSession session, Model model) {
        String nomeParticipante = (String) session.getAttribute("participante");
        
        model.addAttribute("resultados", resultadoRepository.findAllByOrderByPontuacaoDesc());
        model.addAttribute("nomeParticipante", nomeParticipante);
        
        return "ranking";
    }
}
