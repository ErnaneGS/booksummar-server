package com.server.booksummar.domain.enums;

public enum BookCategory {

    FICCAO("ficcao"),
    NAO_FICCAO("nao_ficcao"),
    INFANTOJUVENIL("infantojuvenil"),
    POESIA("poesia"),
    RELIGIAO_ESPIRITUALIDADE("religiao_espiritualidade"),
    LIVROS_DIDATICOS("livros_didaticos"),
    HISTORIA_CULTURA("historia_cultura"),
    ARTES_ENTRETENIMENTO("artes_entreterimento");

    private final String category;

    BookCategory(String category) {
        this.category = category;
    }

}
