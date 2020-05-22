package com.rest.webservices.restfulwebservices.pojo;

import java.util.Date;
import java.util.Objects;

public class Todo {

    private long id;
    private String description;
    private Date targetDate;
    private boolean done;

    public Todo(long id, String description, Date targetDate, boolean done) {
        this.id = id;
        this.description = description;
        this.targetDate = targetDate;
        this.done = done;
    }
    public Todo(){};

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return id == todo.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
