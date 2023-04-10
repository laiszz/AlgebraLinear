package troca_base;

import java.util.InputMismatchException;
import java.util.Scanner;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class Menu{
	static Scanner leia = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println();
		System.out.println("               TROCA DE BASES 2X2");
		System.out.println();
		
		double[] vetor1BaseOriginal = {0, 0};
		double[] vetor2BaseOriginal = {0, 0};
		
		try {
			System.out.println("Base Original");
			System.out.println("Digite a 1ª coordenada do 1° vetor: ");
			vetor1BaseOriginal[0] = leia.nextDouble();
			System.out.println("Digite a 2ª coordenada do 1° vetor: ");
			vetor1BaseOriginal[1] = leia.nextDouble();
			System.out.println("Digite a 1ª coordenada do 2° vetor: ");
			vetor2BaseOriginal[0] = leia.nextDouble();
			System.out.println("Digite a 2ª coordenada do 2° vetor: ");
			vetor2BaseOriginal[1] = leia.nextDouble();
		} catch(InputMismatchException e) {
			System.out.println("\nValor inválido!");
			leia.close();
			System.exit(0);
		}
		
		double[][] baseOriginal = {vetor1BaseOriginal, vetor2BaseOriginal};
		RealMatrix baseOriginalRM = MatrixUtils.createRealMatrix(baseOriginal);
		
		System.out.println("\nA base original digitada foi: ");
		System.out.println("|\t" + String.format("%.1f", baseOriginal[0][0]) + "\t\t" + String.format("%.1f", baseOriginal[0][1]) + "\t|");
		System.out.println("|\t" + String.format("%.1f", baseOriginal[1][0]) + "\t\t" + String.format("%.1f", baseOriginal[1][1]) + "\t|");
		
		System.out.println();
		System.out.println("----------------------------------------");
		System.out.println();
		
		LUDecomposition decompBaseOriginal = new LUDecomposition(baseOriginalRM);
		double determinanteBaseOriginal = decompBaseOriginal.getDeterminant();
		
		if (determinanteBaseOriginal == 0) {
			System.out.println("A base digitada não é linearmente independente!");
			leia.close();
			System.exit(0);
		}
		else {
			double[] vetor1BaseNova = {0, 0};
			double[] vetor2BaseNova = {0, 0};
			
			try {
				System.out.println("Base Nova");
				System.out.println("Digite a 1ª coordenada do 1° vetor: ");
				vetor1BaseNova[0] = leia.nextDouble();
				System.out.println("Digite a 2ª coordenada do 1° vetor: ");
				vetor1BaseNova[1] = leia.nextDouble();
				System.out.println("Digite a 1ª coordenada do 2° vetor: ");
				vetor2BaseNova[0] = leia.nextDouble();
				System.out.println("Digite a 2ª coordenada do 2° vetor: ");
				vetor2BaseNova[1] = leia.nextDouble();
			} catch(InputMismatchException e) {
				System.out.println("\nValor inválido!");
				leia.close();
				System.exit(0);
			}
			
			double[][] baseNova = {vetor1BaseNova, vetor2BaseNova};
			RealMatrix baseNovaRM = MatrixUtils.createRealMatrix(baseNova);
			
			System.out.println("\nA base nova digitada foi: ");
			System.out.println("|\t" + String.format("%.1f", baseNova[0][0]) + "\t\t" + String.format("%.1f", baseNova[0][1]) + "\t|");
			System.out.println("|\t" + String.format("%.1f", baseNova[1][0]) + "\t\t" + String.format("%.1f", baseNova[1][1]) + "\t|");
			
			System.out.println();
			System.out.println("----------------------------------------");
			System.out.println();
			
			LUDecomposition decompBaseNova = new LUDecomposition(baseNovaRM);
			double determinanteBaseNova = decompBaseNova.getDeterminant();
			
			if (determinanteBaseNova == 0) {
				System.out.println("A base digitada não é linearmente independente!");
				leia.close();
				System.exit(0);
			}
			else {
				RealMatrix transformacaoLinearRM = decompBaseNova.getSolver().solve(baseOriginalRM);
				double[][] transformacaoLinear = transformacaoLinearRM.getData();
				LUDecomposition decompTransformacaoLinear = new LUDecomposition(transformacaoLinearRM);
				
				double[] vetorCoordenadaOriginal = {0, 0};
				
				try {
					System.out.println("Coordenadas na base original");
					System.out.println("Digite a 1ª coordenada: ");
					vetorCoordenadaOriginal[0] = leia.nextDouble();
					System.out.println("Digite a 2ª coordenada: ");
					vetorCoordenadaOriginal[1] = leia.nextDouble();
				} catch(InputMismatchException e) {
					System.out.println("\nValor inválido!");
					leia.close();
					System.exit(0);
				}
				
				RealVector vetorCoordenadaOriginalRV = MatrixUtils.createRealVector(vetorCoordenadaOriginal);
				
				System.out.println("\nAs coordenadas originais são: " + vetorCoordenadaOriginalRV);
				
				System.out.println();
				System.out.println("----------------------------------------");
				System.out.println();
				
				RealVector vetorCoordenadaNovaRV = decompTransformacaoLinear.getSolver().solve(vetorCoordenadaOriginalRV);
				
				System.out.println("\nA matriz de transformação linear é: ");
				System.out.println("|\t" + String.format("%.1f", transformacaoLinear[0][0]) + "\t\t" + String.format("%.1f", transformacaoLinear[0][1]) + "\t|");
				System.out.println("|\t" + String.format("%.1f", transformacaoLinear[1][0]) + "\t\t" + String.format("%.1f", transformacaoLinear[1][1]) + "\t|");
				
				System.out.println();
				System.out.println("----------------------------------------");
				System.out.println();
				
				System.out.println("\nAs coordenadas novas são: " + vetorCoordenadaNovaRV);
			}
		}
	}
}
