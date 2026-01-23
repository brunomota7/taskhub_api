package br.com.taskhub_api.entites;

import br.com.taskhub_api.enums.Role;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "tb_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Set<Group> groups = new HashSet<>();

    @ManyToMany(mappedBy = "responsible", fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();

    public UUID getUserId() {return userId;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public Role getRole() {return role;}
    public void setRole(Role role) {this.role = role;}

    public Set<Group> getGroups() {return groups;}
    public void setGroups(Set<Group> groups) {this.groups = groups;}

    public List<Task> getTasks() {return tasks;}
    public void setTasks(List<Task> tasks) {this.tasks = tasks;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return userId != null && userId.equals(user.userId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
