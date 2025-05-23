package unoeste.fipp.mercadofipp.entities;


import jakarta.persistence.*;

import java.awt.*;
import java.util.Base64;

@Entity
@Table(name = "foto_anuncio")
public class Foto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fot_id")
    private Long id;

    @Column(name = "fot_file")
    private byte[] imagem;

    @Column(name="fot_ext")
    private String extensao;

    @Transient
    private String imagem64;

    @ManyToOne
    @JoinColumn(name = "anu_id")
    private Anuncio anuncio;

    public Foto(Long id,  byte[] imagem, Anuncio anuncio, String extensao) {
        this.id = id;
        this.imagem = imagem;
        this.anuncio = anuncio;
        this.extensao= extensao;
    }

    public String getExtensao() {
        return extensao;
    }

    public void setExtensao(String extensao) {
        this.extensao = extensao;
    }

    public String getImagem64() {
        if (imagem != null && imagem.length > 0 && extensao != null) {
            return "data:" + extensao + ";base64," + Base64.getEncoder().encodeToString(imagem);
        }
        return null;
    }

    public void setImagem64(String imagem64) {
        this.imagem64 = imagem64;
    }

    public Foto(byte[] imagem, Anuncio anuncio) {
        this(0L,imagem,anuncio,"");
    }

    public Foto() {
        this(0L,null,null,"");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }


}
