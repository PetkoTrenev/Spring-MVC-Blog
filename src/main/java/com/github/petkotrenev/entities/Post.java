package com.github.petkotrenev.entities;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 300)
    private String title;

    @Lob @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDate creationDate;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User creator;

    @OneToOne(optional = true, fetch = FetchType.LAZY)
    private User lastLockedBy;

    @Column(nullable = true)
    private Instant lockTimestamp;


    public Post() {
    }

    public User getLastLockedBy() {
        return lastLockedBy;
    }

    public void setLastLockedBy(User lastLockedBy) {
        this.lastLockedBy = lastLockedBy;
    }

    public Instant getLockTimestamp() {
        return lockTimestamp;
    }

    public void setLockTimestamp(Instant lockTimestamp) {
        this.lockTimestamp = lockTimestamp;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
