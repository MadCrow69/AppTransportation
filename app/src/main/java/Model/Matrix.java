package Model;


public class Matrix {

    public char marcador;
    public int distancia;
    public int duracao;
    public String origem;
    public String destino;
    public String url;

    public String toString(){
        return "Marcador: " + marcador + "Distancia: " + distancia + "Duracao: " + duracao + "Saida: " + origem + "Chegada: " + destino;
    }

}
