package exponencial_matriz;

import java.util.InputMismatchException;
import java.util.Scanner;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class Menu {
	static Scanner leia = new Scanner(System.in);

	public static void main(String[] args) {
		double w = 0;
		int continuar = 0;
		
		System.out.println();
		System.out.println("               EXPONENCIAL DE MATRIZ 2X2");
		System.out.println("                        DO TIPO");
		System.out.println();
		System.out.println("               |\t0\t1\t|");
		System.out.println("               |\t-w^2\t0\t|");
		
		// Entrada de dados
		try {
			System.out.println("\n\nDigite o valor de W: ");
			w = leia.nextDouble();
			
			System.out.println("\nEssa foi a matriz construída: ");
			System.out.println("|\t0\t1\t|");
			System.out.println("|\t" + String.format("%.1f", -(w*w)) + "\t0\t|");
			
			do {
				System.out.println("\nDeseja continuar? (Digite 1 para SIM, e 0 para NÃO)");
				continuar = leia.nextInt();
			} while (continuar < 0 || continuar > 1);
			
			if (continuar == 0) {
				leia.close();
				System.out.println("\nObrigada por usar o programa! :D");
				System.exit(0);
			}
		} catch(InputMismatchException e){
			leia.nextLine();
			System.out.println("\nDigite valores válidos!\n\n");
		}
		
		// Criando a matriz A
		double[][] matrizDados = {{0, 1},{-(w*w), 0}};
		RealMatrix matriz = MatrixUtils.createRealMatrix(matrizDados);
		Complex[][] matrizOriginal = {{new Complex(0,0), new Complex(1,0)}, {new Complex(-(w*w),0), new Complex(0,0)}};
		
		// Pegando os autovalores da matriz A
		Complex[] listaAutovalores = Calculos.getAutovalores(matriz);
		System.out.println("\nAutovalores: ");
		System.out.println("Autovalor 1: " + String.format("%.1f", listaAutovalores[0].getReal()) + " + (" + 
				String.format("%.1f", listaAutovalores[0].getImaginary()) + ")i");
		System.out.println("Autovalor 1: " + String.format("%.1f", listaAutovalores[0].getReal()) + " + (" + 
				String.format("%.1f", listaAutovalores[1].getImaginary()) + ")i");
		
		// Pegando os autovetores da matriz A e formando a matriz H
		Complex[][] matrizH = Calculos.getAutovetores(listaAutovalores);
		System.out.println("\nMatriz de Autovetores: ");
		Calculos.imprimirMatriz(matrizH);
		
		// Invertendo a matriz H
		Complex[][] matrizHInversa = Calculos.getMatrizInversa(matrizH);
		if (matrizHInversa != null) {
			System.out.println("\nMatriz de Autovetores Inversa: ");
			Calculos.imprimirMatriz(matrizHInversa);
			
			// Criando a matriz de Jordan J = H^-1 x A x H
			Complex[][] primeiraMultiplicacaoJordan = Calculos.multiplicarMatriz(matrizHInversa, matrizOriginal);
			Complex[][] matrizJordan = Calculos.multiplicarMatriz(primeiraMultiplicacaoJordan, matrizH);
			System.out.println("\nMatriz de Jordan: ");
			Calculos.imprimirMatriz(matrizJordan);
			
			// Gerando a matriz Exponencial exp(At) = H x exp(Jt) x H^-1
			Complex[][] matrizExponencial = Calculos.gerarMatrizExponencial(matrizJordan);
			System.out.println("\nExponencial da matriz escolhida: ");
			Calculos.imprimirMatriz(matrizExponencial);
			
			leia.close();
			System.out.println("\nObrigada por usar o programa! :D");
			System.exit(0);
		}
		else {
			System.out.println("A matriz não possui inversa!");
			leia.close();
			System.out.println("\nObrigada por usar o programa! :D");
			System.exit(0);
		}
	}
}