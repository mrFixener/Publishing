package com.task.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.task.domain.Sex;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Dimon on 25.06.2018.
 */
@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Sex sex;

    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @ManyToMany(mappedBy = "authors")
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Book> books = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private List<Reward> rewards = new ArrayList<>();

    @Column(name = "birthdate")
    private Date birthDate;

    public Author() {
    }

    public Author(String firstName, String lastName, Sex sex, Date birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.birthDate = birthDate;
    }

    public void setBooks(List<Book> books) {
        if (books != null)
            this.books = books;
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    public void setRewards(List<Reward> rewards) {
        if (rewards != null)
            this.rewards = rewards;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName != null)
            this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName != null)
            this.lastName = lastName;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        if (sex != null)
            this.sex = sex;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        if (birthDate != null)
            this.birthDate = birthDate;
    }

    public Long getId() {
        return id;
    }

    public List<Book> getBooks() {
        return books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;

        Author author = (Author) o;

        if (!getId().equals(author.getId())) return false;
        if (!getFirstName().equals(author.getFirstName())) return false;
        if (!getLastName().equals(author.getLastName())) return false;
        if (getSex() != author.getSex()) return false;
        return getBirthDate().equals(author.getBirthDate());

    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getFirstName().hashCode();
        result = 31 * result + getLastName().hashCode();
        result = 31 * result + getSex().hashCode();
        result = 31 * result + getBirthDate().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", sex=" + sex +
                ", birthDate=" + birthDate +
                '}';
    }
}
