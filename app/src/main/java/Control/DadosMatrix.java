package Control;

import Model.Matrix;

public class DadosMatrix {


    public Matrix[][] GeraDados(){
        int tam = 5;
        Matrix[][] graphs = new Matrix[tam][tam];
        //          X  Y
        graphs[0][0].destino = "A";
        graphs[0][0].origem = "A";
        graphs[0][0].duracao = 0;
        graphs[0][0].distancia = 0;
        graphs[0][0].marcador = 'F';

        graphs[0][1].destino = "B";
        graphs[0][1].origem = "A";
        graphs[0][1].duracao = 0;
        graphs[0][1].distancia = 12;
        graphs[0][1].marcador = 'V';

        graphs[0][2].destino = "C";
        graphs[0][2].origem = "A";
        graphs[0][2].duracao = 0;
        graphs[0][2].distancia = 23;
        graphs[0][2].marcador = 'V';

        graphs[0][3].destino = "D";
        graphs[0][3].origem = "A";
        graphs[0][3].duracao = 0;
        graphs[0][3].distancia = 33;
        graphs[0][3].marcador = 'V';

        graphs[0][4].destino = "E";
        graphs[0][4].origem = "A";
        graphs[0][4].duracao = 0;
        graphs[0][4].distancia = 41;
        graphs[0][4].marcador = 'V';

        graphs[1][0].destino = "A";
        graphs[1][0].origem = "B";
        graphs[1][0].duracao = 0;
        graphs[1][0].distancia = 22;
        graphs[1][0].marcador = 'V';

        graphs[1][1].destino = "B";
        graphs[1][1].origem = "B";
        graphs[1][1].duracao = 0;
        graphs[1][1].distancia = 0;
        graphs[1][1].marcador = 'F';

        graphs[1][2].destino = "C";
        graphs[1][2].origem = "B";
        graphs[1][2].duracao = 0;
        graphs[1][2].distancia = 32;
        graphs[1][2].marcador = 'V';

        graphs[1][3].destino = "D";
        graphs[1][3].origem = "B";
        graphs[1][3].duracao = 0;
        graphs[1][3].distancia = 25;
        graphs[1][3].marcador = 'V';

        graphs[1][4].destino = "E";
        graphs[1][4].origem = "B";
        graphs[1][4].duracao = 0;
        graphs[1][4].distancia = 67;
        graphs[1][4].marcador = 'V';

        graphs[2][0].destino = "A";
        graphs[2][0].origem = "C";
        graphs[2][0].duracao = 0;
        graphs[2][0].distancia = 63;
        graphs[2][0].marcador = 'V';

        graphs[2][1].destino = "B";
        graphs[2][1].origem = "C";
        graphs[2][1].duracao = 0;
        graphs[2][1].distancia = 14;
        graphs[2][1].marcador = 'V';

        graphs[2][2].destino = "C";
        graphs[2][2].origem = "C";
        graphs[2][2].duracao = 0;
        graphs[2][2].distancia = 0;
        graphs[2][2].marcador = 'F';

        graphs[2][3].destino = "D";
        graphs[2][3].origem = "C";
        graphs[2][3].duracao = 0;
        graphs[2][3].distancia = 27;
        graphs[2][3].marcador = 'V';

        graphs[2][4].destino = "E";
        graphs[2][4].origem = "C";
        graphs[2][4].duracao = 0;
        graphs[2][4].distancia = 17;
        graphs[2][4].marcador = 'V';

        graphs[3][0].destino = "A";
        graphs[3][0].origem = "D";
        graphs[3][0].duracao = 0;
        graphs[3][0].distancia = 25;
        graphs[3][0].marcador = 'V';

        graphs[3][1].destino = "B";
        graphs[3][1].origem = "D";
        graphs[3][1].duracao = 0;
        graphs[3][1].distancia = 42;
        graphs[3][1].marcador = 'V';

        graphs[3][2].destino = "C";
        graphs[3][2].origem = "D";
        graphs[3][2].duracao = 0;
        graphs[3][2].distancia = 36;
        graphs[3][2].marcador = 'V';

        graphs[3][3].destino = "D";
        graphs[3][3].origem = "D";
        graphs[3][3].duracao = 0;
        graphs[3][3].distancia = 0;
        graphs[3][3].marcador = 'F';

        graphs[3][4].destino = "E";
        graphs[3][4].origem = "D";
        graphs[3][4].duracao = 0;
        graphs[3][4].distancia = 15;
        graphs[3][4].marcador = 'V';

        graphs[4][0].destino = "A";
        graphs[4][0].origem = "E";
        graphs[4][0].duracao = 0;
        graphs[4][0].distancia = 22;
        graphs[4][0].marcador = 'V';

        graphs[4][1].destino = "B";
        graphs[4][1].origem = "E";
        graphs[4][1].duracao = 0;
        graphs[4][1].distancia = 12;
        graphs[4][1].marcador = 'V';

        graphs[4][2].destino = "C";
        graphs[4][2].origem = "E";
        graphs[4][2].duracao = 0;
        graphs[4][2].distancia = 17;
        graphs[4][2].marcador = 'V';

        graphs[4][3].destino = "D";
        graphs[4][3].origem = "E";
        graphs[4][3].duracao = 0;
        graphs[4][3].distancia = 23;
        graphs[4][3].marcador = 'V';

        graphs[4][4].destino = "E";
        graphs[4][4].origem = "E";
        graphs[4][4].duracao = 0;
        graphs[4][4].distancia = 0;
        graphs[4][4].marcador = 'F';

        return graphs;
    }
}
