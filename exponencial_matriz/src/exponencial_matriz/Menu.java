package exponencial_matriz;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class Menu {
	static Scanner leia = new Scanner(System.in);

	public static void main(String[] args) {
		double a = 0, b = 0;
		int continuar = 0;
		
		System.out.println();
		System.out.println("               EXPONENCIAL DE MATRIZ 2X2");
		System.out.println("                        DO TIPO");
		System.out.println();
		System.out.println("               |\t0\t1\t|");
		System.out.println("               |\t-b\t-a\t|");
		
		// Entrada de dados
		try {
			System.out.println("\n\nDigite o valor de b: ");
			b = leia.nextDouble();
			System.out.println("\n\nDigite o valor de a: ");
			a = leia.nextDouble();
			
			System.out.println("\nEssa foi a matriz construída: ");
			System.out.println("|\t0\t1\t|");
			System.out.println("|\t" + String.format("%.1f", -b) + "\t" + String.format("%.1f", -a) + "\t|");
			
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
		
		double[][] matriz = {{0, 1},{-b, -a}};
		RealMatrix matriz_RM = MatrixUtils.createRealMatrix(matriz);
		LUDecomposition luDecompMatriz = new LUDecomposition(matriz_RM);
		
		if (luDecompMatriz.getDeterminant() == 0) {
			leia.close();
			System.out.println("\nO determinante da matriz é 0!");
			System.out.println("\nObrigada por usar o programa! :D");
			System.exit(0);
		}
		else {
			if (a * a > 4 * b) {
				double autovalor1 = ((-a + Math.sqrt(a * a - 4 * b)) / 2);
				double autovalor2 = ((-a - Math.sqrt(a * a - 4 * b)) / 2);
				double[] autovetor1 = {1, autovalor1};
				double[] autovetor2 = {1, autovalor2};
				double[][] matrizH = {{1, 1}, {autovetor1[1], autovetor2[1]}};
				RealMatrix matrizH_RM = MatrixUtils.createRealMatrix(matrizH);
				LUDecomposition luDecompMatrizH = new LUDecomposition(matrizH_RM);
				RealMatrix matrizHInversa_RM = luDecompMatrizH.getSolver().getInverse();
				RealMatrix matrizJordan_RM = matrizHInversa_RM.multiply(matriz_RM.multiply(matrizH_RM));
				double[][] matrizJordan = matrizJordan_RM.getData();
				
				System.out.println("|\texp(" + String.format("%.1f",matrizJordan[0][0]) + " * t)\t\t0\t|");
				System.out.println("|\t0\t\texp(" + String.format("%.1f",matrizJordan[1][1]) + " * t)\t|");
			}
			else if (a * a < 4 * b) {
				Complex autovalor1 = new Complex(-a / 2, Math.sqrt(Math.abs(a * a - 4 * b)) / 2);
				Complex autovalor2 = new Complex(-a / 2, -Math.sqrt(Math.abs(a * a - 4 * b)) / 2);
				Complex[] autovetor1 = {new Complex(1,0), autovalor1};
				Complex[] autovetor2 = {new Complex(1,0), autovalor2};
				Complex[][] matrizC = {{new Complex(0,0), new Complex(1,0)}, {new Complex(-b,0), new Complex(-a,0)}};
				Complex[][] matrizH = {{new Complex(1,0), new Complex(1,0)}, {autovetor1[1], autovetor2[1]}};
				Complex[][] matrizHInversa = Calculos.getMatrizInversa(matrizH);
				Complex[][] primeiraMultip = Calculos.multiplicarMatriz(matrizHInversa, matrizC);
				Complex[][] matrizJordan = Calculos.multiplicarMatriz(primeiraMultip, matrizH);
				
				System.out.println("|\texp{[(" + String.format("%.1f", matrizJordan[0][0].getReal()) + ") + (" + String.format("%.1f", matrizJordan[0][0].getImaginary()) + ")i] * t}" + 
						" \t\t0\t\t\t\t|");
				System.out.println("|\t0\t\t\t\t\texp{[(" + String.format("%.1f", matrizJordan[1][1].getReal()) + ") + (" + 
						String.format("%.1f", matrizJordan[1][1].getImaginary()) + ")i] * t}\t|");
			}
			else {
				double autovalor1 = -a/2;
				double[] autovetor1 = {1, autovalor1};
				double[] autovetor2 = {1, 1 + autovalor1};
				double[][] matrizH = {{1, 1}, {autovetor1[1], autovetor2[1]}};
				RealMatrix matrizH_RM = MatrixUtils.createRealMatrix(matrizH);
				LUDecomposition luDecompMatrizH = new LUDecomposition(matrizH_RM);
				RealMatrix matrizHInversa_RM = luDecompMatrizH.getSolver().getInverse();
				RealMatrix matrizJordan_RM = matrizHInversa_RM.multiply(matriz_RM.multiply(matrizH_RM));
				double[][] matrizJordan = matrizJordan_RM.getData();
				
				System.out.println("|\texp(" + String.format("%.1f",matrizJordan[0][0]) + " * t)\t\tt * exp(" + String.format("%.1f",matrizJordan[0][0]) + " * t)\t|");
				System.out.println("|\t0\t\t\texp(" + String.format("%.1f",matrizJordan[1][1]) + " * t)\t\t|");
			}
		}
	}
}