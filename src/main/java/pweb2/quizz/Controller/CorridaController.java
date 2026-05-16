package pweb2.quizz.Controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import pweb2.quizz.Repository.CorridaRepository;
import pweb2.quizz.Repository.ParticipanteRepository;
import pweb2.quizz.Repository.ResultadoRepository;
import pweb2.quizz.model.Corrida;
import pweb2.quizz.model.Participante;
import pweb2.quizz.model.Resultado;

@Controller
public class CorridaController {

    @Autowired
    private CorridaRepository corridaRepository;

    @Autowired
    private ResultadoRepository resultadoRepository;

    @Autowired
    private ParticipanteRepository participanteRepository;
    
    @PostMapping("corrida/iniciar")
    public String iniciarCorrrida(@RequestParam Long corridaId, HttpSession session, RedirectAttributes flash) {
        Corrida corrida = corridaRepository.findById(corridaId).orElseThrow(() -> new RuntimeException("Corrida não encontrada"));
        
        if (resultadoRepository.existsByParticipanteNomeAndCorridaId("nomeParticipante", corridaId)) {
        flash.addFlashAttribute("mensagem", "Você já participou desta corrida!");
        return "redirect:/lobby";
    }

        session.setAttribute("corrida", corrida);
        session.setAttribute("indicePergunta", 0);
        session.setAttribute("tempoInicio", LocalDateTime.now());
        session.setAttribute("pontuacao", 0);
        
        return "redirect:/corrida/pergunta";
    }

    @GetMapping("/corrida/pergunta")
    public String exibirPergunta(HttpSession session, Model model, 
                              RedirectAttributes flash) {
    Corrida corrida = (Corrida) session.getAttribute("corrida");
    if (corrida == null) {
        return "redirect:/lobby";
    }

    LocalDateTime inicio = (LocalDateTime) session.getAttribute("tempoInicio");
    long segundosPassados = ChronoUnit.SECONDS.between(inicio, LocalDateTime.now());

    if (segundosPassados >= corrida.getTempoSegundos()) {
        flash.addFlashAttribute("mensagem", "Tempo esgotado!");
        return "redirect:/corrida/resultado";
    }

    Integer indice = (Integer) session.getAttribute("indicePergunta");
    if (indice >= corrida.getPerguntas().size()) {
        return "redirect:/corrida/resultado";
    }

    model.addAttribute("pergunta", corrida.getPerguntas().get(indice));
    model.addAttribute("numeroPergunta", indice + 1);
    model.addAttribute("totalPerguntas", corrida.getPerguntas().size());
    model.addAttribute("tempoRestante", corrida.getTempoSegundos() - segundosPassados);

    return "pergunta";
}
   
   @PostMapping("/corrida/responder")
    public String responderPergunta(@RequestParam Integer resposta, 
                                 HttpSession session,
                                 RedirectAttributes flash) {
    Corrida corrida = (Corrida) session.getAttribute("corrida");
    if (corrida == null) {
        return "redirect:/lobby";
    }

    LocalDateTime inicio = (LocalDateTime) session.getAttribute("tempoInicio");
    long segundosPassados = ChronoUnit.SECONDS.between(inicio, LocalDateTime.now());
    if (segundosPassados >= corrida.getTempoSegundos()) {
        flash.addFlashAttribute("mensagem", "Tempo esgotado!");
        return "redirect:/corrida/resultado";
    }

    Integer indice = (Integer) session.getAttribute("indicePergunta");
    Integer respostaCorreta = corrida.getPerguntas().get(indice).getRespostaCorreta();

    if (respostaCorreta.equals(resposta)) {
        Integer pontuacao = (Integer) session.getAttribute("pontuacao");
        session.setAttribute("pontuacao", pontuacao + 1);
        flash.addFlashAttribute("mensagem", "✅ Resposta correta! +1 pontos");
    } else {
        flash.addFlashAttribute("mensagem", "❌ Resposta errada!");
    }

    session.setAttribute("indicePergunta", indice + 1);
    return "redirect:/corrida/pergunta";
}

@GetMapping("/corrida/resultado")
public String exibirResultado(HttpSession session, Model model) {
    String nomeParticipante = (String) session.getAttribute("participante");
    Corrida corrida = (Corrida) session.getAttribute("corrida");
    Integer pontuacao = (Integer) session.getAttribute("pontuacao");

    Participante p = participanteRepository.findByNome(nomeParticipante)
        .orElseGet(() -> {
            Participante novo = new Participante();
            novo.setNome(nomeParticipante);
            return participanteRepository.save(novo);
        });

    Resultado resultado = new Resultado();
    resultado.setParticipante(p);
    resultado.setCorrida(corrida);
    resultado.setPontuacao(BigDecimal.valueOf(pontuacao));
    resultado.setDataHora(LocalDateTime.now());
    resultadoRepository.save(resultado);

    model.addAttribute("pontuacao", pontuacao);
    model.addAttribute("corrida", corrida);
    model.addAttribute("participante", nomeParticipante);

    session.removeAttribute("corrida");
    session.removeAttribute("indicePergunta");
    session.removeAttribute("tempoInicio");
    session.removeAttribute("pontuacao");

    return "resultado";
}
}

