package unoeste.fipp.mercadofipp.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "foto_anuncio")
public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="fot_id" )
    private Long id;

    @Column(name = "fot_file")
    private String file;

    @ManyToOne
    @JoinColumn(name = "anu_id")
    private Anuncio anuncio;

    public Foto(Long id, String file, Anuncio anuncio) {
        this.id = id;
        this.file = file;
        this.anuncio = anuncio;
    }

    public Foto() {
        this(0L,"",null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
