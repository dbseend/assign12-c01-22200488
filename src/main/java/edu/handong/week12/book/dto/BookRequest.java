package edu.handong.week12.book.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record BookRequest(
        @NotBlank(message = "도서명 필수")
        String title,
        @NotBlank(message = "저자명 필수")
        String author,
        @Min(value = 0, message = "가격은 0 이상")
        int price
) {
}
