package br.uninove.tcc.util;

import java.math.BigDecimal;

import android.graphics.Bitmap;

public class ClassUtil {
	
	public ClassUtil() {
		
	}

	

	/** Metodo que recebe uma imagem e chama os demais metodos para realizar a transformada de lifiting
	 * O parametro de entrada deverá ser mudado na versão para android.
	 * @param imagem
	 * @return
	 */
	public String tratarImagem(Bitmap imagem) {
		
		int w = imagem.getWidth();
		int h = imagem.getHeight();
		double [][] matrizOrigem = new double [w][h];
		
		  int rgba[] = new int[w * h];  
		   // obtem pixels do bufferedImage  
		  imagem.getPixels(rgba, 0, w, 0, 0, w, h);
		  
		  // converte para tons de cinza  
		  rgba = imageToGray(rgba);  
		   
		  int contador = 0;
		for (int j = 0; j < h; j++) {
			for (int i = 0; i < w; i++) {

				matrizOrigem[i][j] = (rgba[contador] >> 16) & 0xff;
				contador++;

			}
		}
		
		rgba = null;
		
		matrizOrigem = obterTransformada(matrizOrigem, w, h);
		
	 return obterMomentosImagem(matrizOrigem, w, h);
	}
	
	
	/** Metodo que chama a função responsável por realizar a transformada.
	 * @param matrizOrigem
	 * @param w
	 * @param h
	 * @return
	 */
	public double [][] obterTransformada (double [][] matrizOrigem , int w, int h) {
		return realizarTransformadaLinhas(matrizOrigem, w, h);
		
	}
	
	public double [][] obterAproximacao (double [][] matrizParam , int w, int h) {
		double [][] matrizOperacao = new double [w][h];
		double [][] matrizRetorno = new double [w/2][h/2];
		
		int linha = w;
		int coluna = h;
		int indexCol = (coluna / 2) - 1;
		
		//for para as linhas
		for (int i = 0; i < linha; i ++) {
			// for para as colunas
			for (int j = 0; j < indexCol ; j ++) {
				
				matrizOperacao [i][coluna / 2 + j] =  matrizParam [i][(2 * j + 1)] - matrizParam [i][ 2 * j];
			
				matrizOperacao [i][j] = matrizParam [i][2 * j] +  (matrizOperacao [i][coluna / 2 + j])/ 2; 
				
				
				if (matrizOperacao [i][coluna / 2 + j] < 0) {
					matrizOperacao [i][coluna / 2 + j] = matrizOperacao [i][coluna / 2 + j] * (-1);
				}
				
				if (matrizOperacao [i][j] < 0) {
					matrizOperacao [i][j] = matrizOperacao [i][j] * (-1);
				}
			
				
			}
		}
		
		
		//for para as linhas
		for (int i = 0; i < linha; i++) {
			// for para as colunas
			for (int j = 0; j < indexCol; j++) {

				matrizParam[i][j] = matrizOperacao[i][j];

			}
		}
		
		indexCol = (linha / 2) - 1;
		
		//for para as colunas
		for (int i = 0; i < coluna; i ++) {
			// for para as linhas
			for (int j = 0; j < indexCol ; j ++) {
				
				matrizOperacao [linha / 2 + j][i] = matrizParam [(2 * j + 1)][i] - matrizParam [2 *j][i];
				
				matrizOperacao [j][i] = matrizParam [2 * j][i] +  (matrizOperacao [linha / 2 + j][i])/ 2; 
				
				
				
				if (matrizOperacao [linha / 2 + j][i] < 0) {
					matrizOperacao [linha / 2 + j][i] = matrizOperacao [linha / 2 + j][i] * (-1);
				}
				
				if (matrizOperacao [j][i] < 0) {
					matrizOperacao [j][i] = matrizOperacao [j][i] * (-1);
				}
				
			}
		}
		
		for (int i = 0; i < (linha / 2); i ++) {
			for (int j = 0; j < (coluna / 2); j ++) {
				matrizRetorno[i][j] = matrizOperacao [j][i];
			}
		}

		return matrizRetorno;
		
	}
	
	/** Metodo que realizada a transformada nas linhas
	 * @param matrizParam
	 * @param w
	 * @param h
	 * @return
	 */
	public double [][] realizarTransformadaLinhas (double [][] matrizParam, int w, int h) {
		double [][] matrizOperacao = new double [w][h];
		
		int linha = w;
		int coluna = h;
		int indexCol = (coluna / 2) - 1;
		
		//for para as linhas
		for (int i = 0; i < linha; i ++) {
			// for para as colunas
			for (int j = 0; j < indexCol ; j ++) {
				
				matrizOperacao [i][coluna / 2 + j] =  matrizParam [i][(2 * j + 1)] - matrizParam [i][ 2 * j];
			
				matrizOperacao [i][j] = matrizParam [i][2 * j] +  (matrizOperacao [i][coluna / 2 + j])/ 2; 
				
				if (matrizOperacao [i][coluna / 2 + j] < 0) {
					matrizOperacao [i][coluna / 2 + j] = matrizOperacao [i][coluna / 2 + j] * (-1);
				}
				
				if (matrizOperacao [i][j] < 0) {
					matrizOperacao [i][j] = matrizOperacao [i][j] * (-1);
				}
				
			}
		}
		
		
		//for para as linhas
		for (int i = 0; i < linha; i++) {
			// for para as colunas
			for (int j = 0; j < indexCol; j++) {

				matrizParam[i][j] = matrizOperacao[i][j];

			}
		}
		
		indexCol = (linha / 2) - 1;
		
		//for para as colunas
		for (int i = 0; i < coluna; i ++) {
			// for para as linhas
			for (int j = 0; j < indexCol ; j ++) {
				
				matrizOperacao [linha / 2 + j][i] = matrizParam [(2 * j + 1)][i] - matrizParam [2 *j][i];
				
				matrizOperacao [j][i] = matrizParam [2 * j][i] +  (matrizOperacao [linha / 2 + j][i])/ 2; 
				
				if (matrizOperacao [linha / 2 + j][i] < 0) {
					matrizOperacao [linha / 2 + j][i] = matrizOperacao [linha / 2 + j][i] * (-1);
				}
				
				if (matrizOperacao [j][i] < 0) {
					matrizOperacao [j][i] = matrizOperacao [j][i] * (-1);
				}
				
			}
		}
		
		//for para as linhas
		for (int i = 0; i < linha; i++) {
			// for para as colunas
			for (int j = 0; j < indexCol; j++) {

				matrizParam[i][j] = matrizOperacao[i][j];

			}
		}
		
		int indexLinhas = linha / 2;
		
		for (int i = 0; i < (linha / 2); i ++) {
			for (int j = 0; j < (coluna / 2); j ++) {
				matrizParam[i][j] = 0;
			}
		}
		
		//for para as colunas
		for (int i = 0; i < coluna; i ++) {
			// for para as linhas
			for (int j = 0; j < indexLinhas ; j ++) {
				
				matrizOperacao [2 *j][i] = matrizParam [j][i] -  (matrizParam [indexLinhas + j][i])/ 2; 

				matrizOperacao [(2 * j) + 1 ][i] = matrizOperacao [2 *j][i] + (matrizParam [indexLinhas + j][i]);
				
				if (matrizOperacao [2 *j][i] < 0) {
					matrizOperacao [2 *j][i] = matrizOperacao [2 *j][i] * (-1);
				}
				
				if (matrizOperacao [(2 * j) + 1 ][i] < 0) {
					matrizOperacao [(2 * j) + 1 ][i] = matrizOperacao [(2 * j) + 1 ][i] * (-1);
				}
				
			}
		}
		
		
		//for para as linhas
		for (int i = 0; i < linha; i++) {
			// for para as colunas
			for (int j = 0; j < indexCol; j++) {

				matrizParam[i][j] = matrizOperacao[i][j];

			}
		}
		
		indexCol = (coluna / 2) - 1;
		
		//for para as linhas
		for (int i = 0; i < linha; i ++) {
			// for para as colunas
			for (int j = 0; j < indexCol ; j ++) {
				
				matrizOperacao [i][2 * j] = matrizParam [i][j] -  (matrizParam [i][indexCol + j])/ 2; 

				matrizOperacao [i][(2 * j) + 1] = matrizOperacao [i][2 * j] + (matrizParam [i][indexCol + j]);
				
				if (matrizOperacao [i][2 * j] < 0) {
					matrizOperacao [i][2 * j] = matrizOperacao [i][2 * j] * (-1);
				}
				
				if (matrizOperacao [i][(2 * j) + 1] < 0) {
					matrizOperacao [i][(2 * j) + 1] = matrizOperacao [i][(2 * j) + 1] * (-1);
				}
				
			}
		
		}
		
		//for para as linhas
		for (int i = 0; i < linha; i++) {
			// for para as colunas
			for (int j = 0; j < indexCol; j++) {

				matrizParam[i][j] = matrizOperacao[i][j];

			}
		}
		
		return matrizOperacao;
		
	}
	
	/**Metodo que realiza a transformada nas colunas
	 * @param matrizParam
	 * @param rows
	 * @param coluns
	 * @return
	 */
	public double [][] realizarTransformadaColunas (double [][] matrizParam, int rows, int coluns) {
		
		double [][] matrizOperacao = new double [rows][coluns];
		
		int linha = rows;
		int coluna = coluns;
		int indexCol = (linha / 2) - 1;
		
		//for para as colunas
		for (int i = 0; i < coluna; i ++) {
			// for para as linhas
			for (int j = 0; j < indexCol ; j ++) {
				
				matrizOperacao [linha / 2 + j][i] = matrizParam [(2 * j + 1)][i] - matrizParam [2 *j][i];
				
				matrizOperacao [j][i] = matrizParam [2 * j][i] +  (matrizOperacao [linha / 2 + j][i])/ 2; 
				
				if (matrizOperacao [linha / 2 + j][i] < 0) {
					matrizOperacao [linha / 2 + j][i] = matrizOperacao [linha / 2 + j][i] * (-1);
				}
				
				if (matrizOperacao [j][i] < 0) {
					matrizOperacao [j][i] = matrizOperacao [j][i] * (-1);
				}
				
			}
		}
		
		return matrizOperacao;
		
	}
	
	/**Metodo que realiza a transformada inversa nas linhas
	 * @param matrizParam
	 * @param w
	 * @param h
	 * @return
	 */
	public double [][] realizarInversaLinhas (double [][] matrizParam, int w, int h) {
		double [][] matrizOperacao = new double [w][h];
		
		int linha = w;
		int coluna = h;
		int indexCol = (coluna / 2) - 1;
		
		//for para as linhas
		for (int i = 0; i < linha; i ++) {
			// for para as colunas
			for (int j = 0; j < indexCol ; j ++) {
				
				matrizOperacao [i][2 * j] = matrizParam [i][j] -  (matrizParam [i][indexCol + j])/ 2; 

				matrizOperacao [i][(2 * j) + 1] = matrizOperacao [i][2 * j] + (matrizParam [i][indexCol + j]);
				
				if (matrizOperacao [i][2 * j] < 0) {
					matrizOperacao [i][2 * j] = matrizOperacao [i][2 * j] * (-1);
				}
				
				if (matrizOperacao [i][(2 * j) + 1] < 0) {
					matrizOperacao [i][(2 * j) + 1] = matrizOperacao [i][(2 * j) + 1] * (-1);
				}
				
			}
		
		}
		
		return matrizOperacao;
		
	}
	
	/** Metodo que realiza a transformada Inversa nas colunas
	 * @param matrizParam
	 * @param rows
	 * @param coluns
	 * @return
	 */
	public double [][] realizarInversaColunas (double [][] matrizParam, int rows, int coluns) {
		
		double [][] matrizOperacao = new double [rows][coluns];
		
		int linha = rows;
		int coluna = coluns;
		int indexLinhas = linha / 2;
		
		for (int i = 0; i < (rows / 2); i ++) {
			for (int j = 0; j < (coluns / 2); j ++) {
				matrizParam[i][j] = 0;
			}
		}
		
		//for para as colunas
		for (int i = 0; i < coluna; i ++) {
			// for para as linhas
			for (int j = 0; j < indexLinhas ; j ++) {
				
				matrizOperacao [2 *j][i] = matrizParam [j][i] -  (matrizParam [indexLinhas + j][i])/ 2; 

				matrizOperacao [(2 * j) + 1 ][i] = matrizOperacao [2 *j][i] + (matrizParam [indexLinhas + j][i]);
				
				
				if (matrizOperacao [2 *j][i] < 0) {
					matrizOperacao [2 *j][i] = matrizOperacao [2 *j][i] * (-1);
				}
				
				if (matrizOperacao [(2 * j) + 1 ][i] < 0) {
					matrizOperacao [(2 * j) + 1 ][i] = matrizOperacao [(2 * j) + 1 ][i] * (-1);
				}
				
			}
		}
		
		return realizarInversaLinhas(matrizOperacao, rows, coluns);
		
	}
	

	
	/** Metodo responsável por obter os momentos da imagem
	 * @param matrizParam
	 * @param w
	 * @param h
	 * @return
	 */
	public String obterMomentosImagem(double[][] matrizParam, int w, int h) {
		double[][] matrizOperacao = matrizParam;
		String retorno = "";

		double m00 = 0;
		double m10 = 0;
		double m01 = 0;

		int V = h; // Tamanho do eixo vertical
		int H = w; // Tamanho do eixo Horizontal

		for (int y = 1; y < V; y++) {
			for (int x = 1; x < H; x++) {
				m00 = m00 + matrizOperacao[x][y];
				m10 = (m10 + (x * matrizOperacao[x][y]));
				m01 = (m01 + (y * matrizOperacao[x][y]));

			}
		}

		double xm = (m10 / m00);
		double ym = (m01 / m00);

		double m11 = (0);
		double m20 = (0);
		double m02 = (0);
		double m12 = (0);
		double m21 = (0);
		double m30 = (0);
		double m03 = (0);
		
		for (int y = 1; y < V; y++) {
			for (int x = 1; x < H; x++) {
				
				m20 = (m20 + (Math.pow(x-xm, 2) * matrizOperacao[x][y] ));
				m02 = (m02 + (Math.pow(y-ym, 2) * matrizOperacao[x][y] ));
				m11 = (m11 + ( ((x-xm) * (y-ym)) * matrizOperacao[x][y] ));
				m30 = (m30 + ( Math.pow(x-xm, 3) * matrizOperacao[x][y] ));
				m03 = (m03 + ( Math.pow(y-ym, 3) * matrizOperacao[x][y] ));
				m12 = (m12 + ( (x-xm) * Math.pow(y-ym, 2) * matrizOperacao[x][y] ));
				m21 = (m21 + ( Math.pow(x-xm, 2) * (y-ym) * matrizOperacao[x][y] ));
				
			}
		}
		
		double m00G = Math.pow(m00, 2);
		double N20 = m20/m00G;
		double N02 = m02 /m00G;
		double N11 = m11 /m00G;
		
		m00G= Math.pow(m00, (5/2));
		double N30 = (m30 / m00G);
		double N03 = (m03 / m00G);
		double N12 = (m12 / m00G);
		double N21 = (m21 / m00G);
		
		double S1=(N30 + N12);
		double S2=(N21 + N03);
		double S3=(N30 - 3*N12);
		double S4=(N20 - N02);
		double S5=(3*N21 - N03);
		double S6 =( Math.pow(S1, 2) - (3*(Math.pow(S2,2))) );
		double S7 = ( (3*(Math.pow(S1, 2))) - Math.pow(S2,2) );
		double S8 = (S3*S1);
		double S9 = (S5*S2);
		double S10= (S5*S1);
		double S11= (S3*S2);
		
		double[]I= new double[7];
		
		I[0]= (N20 + N02);
		I[1]= Math.pow( S4,2 ) + ( Math.pow(2*N11,2) );
		I[2]=  Math.pow( S3,2 ) +  Math.pow( S5,2 );
		I[3]=  Math.pow( S1,2 ) +  Math.pow( S2,2 );
		I[4]= ( S8 * S6) + (S9*S7);
		I[5]= ( S4*( Math.pow(S1,2)- Math.pow(S2,2)) ) + (4*N11*S1*S2);
		I[6]= ( S10*S6 )+ (S11*S7);
		
		for (int i = 0; i < 7; i ++) {
			if (i != 6) {
				retorno += I[i] + "/";
			} else {
				retorno += I[i] + "";
			}
		}
		
		return retorno;
		
	}

	/**Converte o valor dos pixels para cinza
	 * @param image
	 * @return
	 */
	public static int[] imageToGray(int image[]) {

		for (int i = 0; i < image.length; i++) {
			// extrai a informação RGBA do valor inteiro
			int alpha = (image[i] >> 24) & 0xff;
			int red = (image[i] >> 16) & 0xff;
			int green = (image[i] >> 8) & 0xff;
			int blue = (image[i]) & 0xff;

			// média dos valores RGB para tranformar para
			// escala de cinza
			int mean = (red + green + blue) / 3;

			// converte a informação de rgb para inteiro
			int gray = ((0 << 24) & 0xFF000000) | ((mean << 16) & 0x00FF0000)
					| ((mean << 8) & 0x0000FF00) | (mean & 0x000000FF);

			// seta o pixel modificado na imagem
			image[i] = gray;
		}

		return image;
	}
	
	/** Converte o uma string para um vetor de caractéristicas
	 * @param paramBanco
	 * @return
	 */
	public Double[] obterVetor (String paramBanco ) {
		String valores[] = paramBanco.split("/");
		Double [] retorno = new Double [7] ;
		for (int i = 0 ; i < 7; i ++) {
			retorno[i] = new Double(valores[i]);
		}
		return retorno;
		
	}

	/** Metodo responsável por comparar as caracteristicas de duas imagens
     * @param x caracteristicas da imagem primeira imagem.
     * @param y caracteristicas da imagem já cadastrada na base de dados
     * @return
     */
	public boolean compararImagens(Double[] x, Double[] y) {
		
		Double somatorio = new Double(0);
		BigDecimal parametroDistancia = new BigDecimal(0.003);
		Double result = new Double(0);
		for (int i = 0; i < 7; i++) {
			somatorio += Math.pow(x[i] - y[i], 2);
		}
		result = Math.sqrt(somatorio);
		BigDecimal valorDistancia = new BigDecimal(result).setScale(10, BigDecimal.ROUND_DOWN);

		boolean aux = new Long(valorDistancia.compareTo(parametroDistancia)).longValue() < 1l;
		
		return aux;
              
    }
	
}
