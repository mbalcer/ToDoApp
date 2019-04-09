package entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String name;
    private Date date;
    private String description;
    private String color;
    private boolean isCompleted;

    public Task() {
    }

    public Task(Long userId, String name, Date date, String description, String color, boolean isCompleted) {
        this.userId = userId;
        this.name = name;
        this.date = date;
        this.description = description;
        this.color = color;
        this.isCompleted = isCompleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", color='" + color + '\'' +
                ", isCompleted=" + isCompleted +
                '}';
    }
}
