package entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, length = 20)
    private String nome;

    @ManyToMany(mappedBy = "roles")
    private List<Usuario> usuarios;
}
