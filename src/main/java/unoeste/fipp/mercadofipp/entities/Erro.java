package unoeste.fipp.mercadofipp.entities;

public class Erro {
    private String mensagem;

    public Erro(String mensagem) {
        this.mensagem = mensagem;
    }

    public Erro() {
        this("");
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
