package pweb2.quizz.Exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 1. Trata entidade não encontrada (JPA)
    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFound(EntityNotFoundException ex, RedirectAttributes flash) {
        flash.addFlashAttribute("erro", "Registro não encontrado: " + ex.getMessage());
        return "redirect:/admin/corridas"; // redireciona para lista de corridas
    }

    // 2. Trata IllegalArgumentException (lançada pelo nosso código)
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException ex, RedirectAttributes flash,
                                       HttpServletRequest request) {
        flash.addFlashAttribute("erro", ex.getMessage());
        // Tenta redirecionar para a página anterior (referer)
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.isEmpty()) {
            return "redirect:" + referer;
        }
        return "redirect:/"; // fallback
    }

    // 3. Trata violação de integridade (ex: chave duplicada, FK)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrity(DataIntegrityViolationException ex, RedirectAttributes flash) {
        flash.addFlashAttribute("erro", "Erro de integridade de dados: " + ex.getMostSpecificCause().getMessage());
        return "redirect:/admin/corridas";
    }

    // 4. Trata exceção genérica (fallback)
    @ExceptionHandler(Exception.class)
    public ModelAndView handleGenericException(Exception ex, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("erro");
        mav.addObject("mensagem", "Ocorreu um erro inesperado. Tente novamente mais tarde.");
        mav.addObject("detalhes", ex.getMessage());
        mav.addObject("url", request.getRequestURL());
        return mav;
    }
    
    @ExceptionHandler(org.springframework.dao.EmptyResultDataAccessException.class)
    public String handleEmptyResult(EmptyResultDataAccessException ex, RedirectAttributes flash) {
        flash.addFlashAttribute("erro", "Registro já foi removido ou não existe.");
        return "redirect:/admin/corridas";
}
}