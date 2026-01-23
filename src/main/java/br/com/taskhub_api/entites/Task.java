package br.com.taskhub_api.entites;

import br.com.taskhub_api.enums.StatusTask;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tb_tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @Column(nullable = false, name = "task_name")
    private String taskName;

    private String description;
    private LocalDate term;

    @Enumerated(EnumType.STRING)
    private StatusTask status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsible_user_id", nullable = false)
    private User responsible;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    public Long getTaskId() {return taskId;}

    public String getTaskName() {return taskName;}
    public void setTaskName(String taskName) {this.taskName = taskName;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public LocalDate getTerm() {return term;}
    public void setTerm(LocalDate term) {this.term = term;}

    public StatusTask getStatus() {return status;}
    public void setStatus(StatusTask status) {this.status = status;}

    public User getResponsible() {return responsible;}
    public void setResponsible(User responsible) {this.responsible = responsible;}

    public Group getGroup() {return group;}
    public void setGroup(Group group) {this.group = group;}

}
