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
import pweb2.quizz.model.Corrida;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CorridaRepository corridaRepository;

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
}