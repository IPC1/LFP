
public class Tokens {
	private int tokenActual=0;
	private int tokenNumero=4;
	private int tokenLetra=2;
	private int tokenError=6;
	private int tokenMayor=3;
	private int tokenMenor=1;
	private int tokenDiagonal=5;
	
	private int tokenXML=1;
	private int tokenAlbum=2;
	private int tokenAutor=3;
	private int tokenTitulo=4;
	private int tokenFormato=5;
	private int tokenPortada=6;
	private int tokenCanciones=7;
	private int tokenPistas=8;
	private int tokenPError=9;
	private int tokenPalabra=0;
	
	public int tipoToken (char caracter){
		int ascii = (int)caracter;
		if((ascii>=97)&&(ascii<=122)||(ascii>=65)&&(ascii<=90)){
			tokenActual=tokenLetra;
		}else if ((ascii>=48)&&(ascii<=57)){
			tokenActual=tokenNumero;
		}else if ((ascii==60)){
			tokenActual=tokenMenor;
		}else if ((ascii==62)){
			tokenActual=tokenMayor;
		}else if((ascii==47)){
			tokenActual=tokenDiagonal;
		}else{
			tokenActual=tokenError;
		}
		return tokenActual;
	}
	
	public int tipoToken (String palabra){
		if(palabra.equalsIgnoreCase("XML")){
			tokenPalabra=tokenXML;
		}else if(palabra.equalsIgnoreCase("Album")){
			tokenPalabra=tokenAlbum;
		}else if(palabra.equalsIgnoreCase("Autor")){
			tokenPalabra=tokenAutor;
		}else if(palabra.equalsIgnoreCase("Titulo")){
			tokenPalabra=tokenTitulo;
		}else if (palabra.equalsIgnoreCase("Formato")){
			tokenPalabra=tokenFormato;
		}else if(palabra.equalsIgnoreCase("Portada")){
			tokenPalabra=tokenPortada;
		}else if(palabra.equalsIgnoreCase("Canciones")){
			tokenPalabra=tokenCanciones;
		}else if(palabra.equalsIgnoreCase("Pistas")){
			tokenPalabra=tokenPistas;
		}else{
			tokenPalabra=tokenPError;
		}
		return tokenPalabra;
	}
}
