package com.task.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Dimon on 25.06.2018.
 */
@Entity
@Table(name = "reward")
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    private Integer year;

    @NotNull
    private String title;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_author")
    private Author author;

    public Reward(@NotNull Integer year, @NotNull String title, Author author) {
        this.year = year;
        this.title = title;
        this.author = author;
    }

    public Reward() {
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reward)) return false;

        Reward reward = (Reward) o;

        if (!getId().equals(reward.getId())) return false;
        if (!getYear().equals(reward.getYear())) return false;
        if (!getTitle().equals(reward.getTitle())) return false;
        return getAuthor().equals(reward.getAuthor());

    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getYear().hashCode();
        result = 31 * result + getTitle().hashCode();
        result = 31 * result + getAuthor().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Reward{" +
                "id=" + id +
                ", year=" + year +
                ", title='" + title + '\'' +
                ", author=" + author +
                '}';
    }
}
