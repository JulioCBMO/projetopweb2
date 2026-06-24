package pweb2.quizz.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import pweb2.quizz.Repository.ResultadoRepository;
import pweb2.quizz.Ui.NavPageBuilder;
import pweb2.quizz.model.Resultado;

@Controller
public class RankingController {

    @Autowired
    private ResultadoRepository resultadoRepository;

    @GetMapping("/ranking")
    public String exibirRanking( 
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "5") int size,
        HttpSession session,
        Model model) {
       
        String nomeParticipante = (String) session.getAttribute("participante");
        
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Resultado> paginaResultados = resultadoRepository.findAllByOrderByPontuacaoDesc(pageable);
    
        model.addAttribute("resultados", paginaResultados);
        model.addAttribute("nomeParticipante", nomeParticipante);
        model.addAttribute("navPage", NavPageBuilder.build(paginaResultados));
        
        return "ranking";
    }
}
