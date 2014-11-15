package org.phstudy.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by study on 11/15/14.
 */
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "tasks", columnDefinition = "text[]")
    @Convert(converter = org.phstudy.conventer.ListToArrayConveter.class)
    private List<String> tasks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getTasks() {
        return tasks;
    }

    public void setTasks(List<String> tasks) {
        this.tasks = tasks;
    }
}
