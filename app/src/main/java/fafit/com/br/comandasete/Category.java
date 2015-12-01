package fafit.com.br.comandasete;

/**
 * Created by layonn on 05/11/15.
 */
public class Category {
    private int id;
    private String nome;
    private String imagem;

    public Category() {
    }

    public Category(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImage() {
        return imagem;
    }
    public void setImage(String imagem) {
        this.imagem = imagem;
    }
}
