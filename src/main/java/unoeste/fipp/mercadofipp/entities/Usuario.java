package unoeste.fipp.mercadofipp.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private Long id;
    @Column(name = "usr_name")
    private String nome;
    @Column(name = "usr_pass")
    private String senha;
    @Column(name = "usr_level")
    private int level;

    public Usuario() {
    }

    public Usuario(Long id, String nome, String senha, int level) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
