package pweb2.quizz.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import pweb2.quizz.Repository.CorridaRepository;
import pweb2.quizz.Repository.PerguntaRepository;
import pweb2.quizz.model.Corrida;
import pweb2.quizz.model.Pergunta;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CorridaRepository corridaRepository;

    @Autowired
    private PerguntaRepository perguntaRepository;

    @GetMapping("/login")
    public String loginAdmin(HttpSession session) {
        session.setAttribute("participante", "admin_master");
        return "redirect:/admin/corridas";
    }

    @GetMapping("/corridas")
    public String listarCorridas(Model model) {
        model.addAttribute("corridas", corridaRepository.findAll());
        return "admin/lista-corridas";
    }

    @GetMapping("/corridas/nova")
    public String formNovaCorrida(Model model) {
        model.addAttribute("corrida", new Corrida());
        return "admin/form-corrida";
    }

    @GetMapping("/corridas/{id}/editar")
    public String formEditarCorrida(@PathVariable Long id, Model model) {
        Corrida corrida = corridaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Corrida inválida:" + id));
        model.addAttribute("corrida", corrida);
        return "admin/form-corrida";
    }

    @PostMapping("/corridas/salvar")
    public String salvarCorrida(Corrida corrida, RedirectAttributes flash) {
        if (corrida.getAtiva() == null) {
            corrida.setAtiva(false);
        }
        corridaRepository.save(corrida);
        flash.addFlashAttribute("mensagem", "Corrida salva com sucesso!");
        return "redirect:/admin/corridas";
    }

    @GetMapping("/corridas/{id}/excluir")
    public String excluirCorrida(@PathVariable Long id, RedirectAttributes flash) {
        corridaRepository.deleteById(id);
        flash.addFlashAttribute("mensagem", "Corrida excluída com sucesso!");
        return "redirect:/admin/corridas";
    }

    @GetMapping("/corridas/{corridaId}/perguntas")
    public String listarPerguntas(@PathVariable Long corridaId, Model model) {
        Corrida corrida = corridaRepository.findById(corridaId)
            .orElseThrow(() -> new IllegalArgumentException("Corrida inválida:" + corridaId));
        model.addAttribute("corrida", corrida);
        model.addAttribute("perguntas", corrida.getPerguntas());
        return "admin/lista-perguntas";
    }

    @GetMapping("/corridas/{corridaId}/perguntas/nova")
    public String formNovaPergunta(@PathVariable Long corridaId, Model model) {
        Corrida corrida = corridaRepository.findById(corridaId)
            .orElseThrow(() -> new IllegalArgumentException("Corrida inválida:" + corridaId));
        Pergunta pergunta = new Pergunta();
        pergunta.setCorrida(corrida);
        model.addAttribute("pergunta", pergunta);
        model.addAttribute("corridaId", corridaId);
        return "admin/form-pergunta";
    }

    @PostMapping("/corridas/{corridaId}/perguntas/salvar")
    public String salvarPergunta(@PathVariable Long corridaId, Pergunta pergunta, RedirectAttributes flash) {
        Corrida corrida = corridaRepository.findById(corridaId)
            .orElseThrow(() -> new IllegalArgumentException("Corrida inválida:" + corridaId));
        pergunta.setCorrida(corrida);
        perguntaRepository.save(pergunta);
        flash.addFlashAttribute("mensagem", "Pergunta salva com sucesso!");
        return "redirect:/admin/corridas/" + corridaId + "/perguntas";
    }

    @GetMapping("/corridas/{corridaId}/perguntas/{id}/excluir")
    public String excluirPergunta(@PathVariable Long corridaId, @PathVariable Long id, RedirectAttributes flash) {
        perguntaRepository.deleteById(id);
        flash.addFlashAttribute("mensagem", "Pergunta excluída com sucesso!");
        return "redirect:/admin/corridas/" + corridaId + "/perguntas";
    }
}