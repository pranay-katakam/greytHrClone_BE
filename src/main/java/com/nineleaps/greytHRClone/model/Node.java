package com.nineleaps.greytHRClone.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@Table(name = "tree")
public class Node {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String name;

    @JoinColumn(name = "parent_id")
    @ManyToOne
    private Node parent;

    @OneToMany(mappedBy = "parent")
    private List<Node> children;


}
