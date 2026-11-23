package edu.handong.week12.book.exception;

public record ApiError(
        String code,
        String message
) {
}
