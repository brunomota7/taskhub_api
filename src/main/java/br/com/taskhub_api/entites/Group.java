package br.com.taskhub_api.entites;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tb_groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long groupId;

    @Column(nullable = false, name = "group_name")
    private String groupName;

    private String description;

    @ManyToMany
    @JoinTable(
            name = "tb_group_users",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    public long getGroupId() {return groupId;}

    public String getGroupName() {return groupName;}
    public void setGroupName(String groupName) {this.groupName = groupName;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public Set<User> getUsers() {return users;}
    public void setUsers(Set<User> users) {this.users  = users;}

    public List<Task> getTasks() {return tasks;}
    public void setTasks(List<Task> tasks) {this.tasks = tasks;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;
        Group group = (Group) o;
        return groupId != null && groupId.equals(group.groupId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
