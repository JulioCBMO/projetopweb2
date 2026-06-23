package pweb2.quizz.Ui;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NavPage {
    private int currentPage;
    private long totalElements;
    private int totalPages;
    private int pageSize;
}