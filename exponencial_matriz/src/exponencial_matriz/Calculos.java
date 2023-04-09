package exponencial_matriz;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;

public class Calculos {
	public static Complex[] getAutovalores(RealMatrix matriz) {
		EigenDecomposition decomp = new EigenDecomposition(matriz);
		
		double autovalor1Re = decomp.getRealEigenvalue(0);
		double autovalor1Im = decomp.getImagEigenvalue(0);
		double autovalor2Re = decomp.getRealEigenvalue(1);
		double autovalor2Im = decomp.getImagEigenvalue(1);
		Complex autovalor1 = new Complex(autovalor1Re, autovalor1Im);
		Complex autovalor2 = new Complex(autovalor2Re, autovalor2Im);
		
		Complex[] autovalores = {autovalor1, autovalor2};
		return autovalores;
	}
	
	public static Complex[][] getAutovetores(Complex[] listaAutovalores){
		Complex[][] matrizH = {{new Complex(1,0), new Complex(1,0)}, {listaAutovalores[0], listaAutovalores[1]}};
		return matrizH;
	}
	
	public static Complex getDeterminante(Complex[][] matriz) {
		Complex determinanteA = matriz[0][0].multiply(matriz[1][1]);
		Complex determinanteB = matriz[0][1].multiply(matriz[1][0]);
		Complex determinante = determinanteA.subtract(determinanteB);
		return determinante;
	}
	
	public static Complex[][] getMatrizInversa(Complex[][] matriz){
		Complex determinante = getDeterminante(matriz);
		Complex[][] matrizInversa = {{new Complex(0,0), new Complex(0,0)}, {new Complex(0,0), new Complex(0,0)}};
		
		if (determinante != new Complex(0, 0)) {
			Complex el00 = matriz[1][1].divide(determinante);
			Complex el01 = matriz[0][1].negate().divide(determinante);
			Complex el10 = matriz[1][0].negate().divide(determinante);
			Complex el11 = matriz[0][0].divide(determinante);
			
			matrizInversa[0][0] = el00;
			matrizInversa[0][1] = el01;
			matrizInversa[1][0] = el10;
			matrizInversa[1][1] = el11;
			
			return matrizInversa;
		}
		else {
			return null;
		}
	}
	
	public static Complex[][] multiplicarMatriz(Complex[][] matriz1, Complex[][] matriz2){
		Complex[][] matrizResultado = {{new Complex(0,0), new Complex(0,0)}, {new Complex(0,0), new Complex(0,0)}};
		
		Complex el00Parte1 = matriz2[0][0].multiply(matriz1[0][0]);
		Complex el00Parte2 = matriz2[1][0].multiply(matriz1[0][1]);
		Complex el00 = el00Parte1.add(el00Parte2);
		
		Complex el01Parte1 = matriz2[0][1].multiply(matriz1[0][0]);
		Complex el01Parte2 = matriz2[1][1].multiply(matriz1[0][1]);
		Complex el01 = el01Parte1.add(el01Parte2);
		
		Complex el10Parte1 = matriz2[0][0].multiply(matriz1[1][0]);
		Complex el10Parte2 = matriz2[1][0].multiply(matriz1[1][1]);
		Complex el10 = el10Parte1.add(el10Parte2);
		
		Complex el11Parte1 = matriz2[0][1].multiply(matriz1[1][0]);
		Complex el11Parte2 = matriz2[1][1].multiply(matriz1[1][1]);
		Complex el11 = el11Parte1.add(el11Parte2);
		
		matrizResultado[0][0] = el00;
		matrizResultado[0][1] = el01;
		matrizResultado[1][0] = el10;
		matrizResultado[1][1] = el11;
		
		return matrizResultado;
	}
	
	public static Complex[][] gerarMatrizExponencial(Complex[][] matrizDiagonal){
		Complex[][] matrizExponencial = {{new Complex(0,0), new Complex(0,0)}, {new Complex(0,0), new Complex(0,0)}};
		
		matrizExponencial[0][0] = matrizDiagonal[0][0].exp();
		matrizExponencial[1][1] = matrizDiagonal[1][1].exp();
		
		return matrizExponencial;
	}
	
	public static void imprimirMatriz(Complex[][] matriz) {
		System.out.println("|\t" + String.format("%.1f", matriz[0][0].getReal()) + " + (" + String.format("%.1f", matriz[0][0].getImaginary()) + ")i" + 
				" \t\t" + String.format("%.1f", matriz[0][1].getReal()) + " + (" + 
				String.format("%.1f", matriz[0][1].getImaginary()) + ")i" + " \t|");
		System.out.println("|\t" + String.format("%.1f", matriz[1][0].getReal()) + " + (" + String.format("%.1f", matriz[1][0].getImaginary()) + ")i" + 
				" \t\t" + String.format("%.1f", matriz[1][1].getReal()) + " + (" + 
				String.format("%.1f", matriz[1][1].getImaginary()) + ")i" + " \t|");
	}
}