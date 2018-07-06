package com.task.domain;

import com.task.model.Author;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Dimon on 02.07.2018.
 */
public class AuthorModel {
    private String firstName;
    private String lastName;
    private int age;
    private List<String> listBooks;

    public AuthorModel() {
    }

    public AuthorModel(Author author) {
        this.firstName = author.getFirstName();
        this.lastName = author.getLastName();
        LocalDate localBirthday = (new java.sql.Date(author.getBirthDate().getTime())).toLocalDate();
        age = Period.between(localBirthday, LocalDate.now()).getYears();
        this.listBooks = author.getBooks().stream().map(book -> book.getTitle()).collect(Collectors.toList());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getListBooks() {
        return listBooks;
    }

    public void setListBooks(List<String> listBooks) {
        this.listBooks = listBooks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorModel)) return false;

        AuthorModel that = (AuthorModel) o;

        if (getAge() != that.getAge()) return false;
        if (!getFirstName().equals(that.getFirstName())) return false;
        if (!getLastName().equals(that.getLastName())) return false;
        return getListBooks() != null ? getListBooks().equals(that.getListBooks()) : that.getListBooks() == null;

    }

    @Override
    public int hashCode() {
        int result = getFirstName().hashCode();
        result = 31 * result + getLastName().hashCode();
        result = 31 * result + getAge();
        result = 31 * result + (getListBooks() != null ? getListBooks().hashCode() : 0);
        return result;
    }
}
