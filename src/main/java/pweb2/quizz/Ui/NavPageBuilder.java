package pweb2.quizz.Ui;

import org.springframework.data.domain.Page;

public class NavPageBuilder {

    public static NavPage build(Page<?> page) {
        return new NavPage(
            page.getNumber() + 1,    
            page.getTotalElements(),
            page.getTotalPages(),
            page.getSize()
        );
    }
}