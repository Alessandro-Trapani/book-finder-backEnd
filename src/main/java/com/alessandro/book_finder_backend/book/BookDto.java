package com.alessandro.book_finder_backend.book;

import java.util.Objects;

public class BookDto {

    String email;
    String id;

    public BookDto(String email, String id) {
        this.email = email;
        this.id = id;
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "email='" + email + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BookDto bookDto = (BookDto) o;
        return Objects.equals(email, bookDto.email) && Objects.equals(id, bookDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, id);
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
