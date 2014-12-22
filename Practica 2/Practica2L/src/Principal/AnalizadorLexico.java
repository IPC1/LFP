package Principal;

import java.util.ArrayList;

public class AnalizadorLexico {
	static ArrayList <Album> Album = new ArrayList <Album>();
	
	boolean xmlb=false;
	boolean albumb=false;
	boolean autorb=false;
	boolean titulob=false;
	boolean formatob=false;
	boolean portadab=false;
	
    int tokenActual=0;
    int tokenNumero=4;
    int tokenLetra=2;
    int tokenError=6;
    int tokenMayor=3;
    int tokenMenor=1;
    int tokenDiagonal=5;
	String palabra= null;
	static String error="";
	boolean etiqueta=false;
	boolean bandera=false;
	
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
	
	public void analizador (int tokenActual, char caracter, int fila, int columna){
		
		switch (tokenActual){
		
		case 1:
			if (palabra != null){
				if(xmlb==true){
					if (albumb==true){
						if(autorb==true){
							Album.get((Album.size())-1).setAutor(palabra);
							palabra=null;
							etiqueta=true;
						}else if(titulob==true){
							Album.get((Album.size())-1).setTitulo(palabra);
							palabra=null;
							etiqueta=true;
						}else if(formatob==true){
							Album.get((Album.size())-1).setFormato(palabra);
							palabra=null;
							etiqueta=true;
						}else if (portadab==true){
							Album.get((Album.size())-1).setPortada(palabra);
							palabra=null;
							etiqueta=true;
						}else{
							error+="Error sintactico1 en la linea: "+fila+" por caracteres fuera de lugar"+caracter+"\n";
						}
					}else{
						error+="Error sintactico2 en la linea: "+fila+" por caracteres fuera de lugar"+caracter+"\n";
					}
				}else{
					error+="Error sintactico3 en la linea: "+fila+" por caracteres fuera de lugar"+caracter+"\n";
				}
			}else{
				etiqueta=true;
			
			}			
			break;
		case 2:
			if (palabra==null){
				palabra=""+caracter;
			}else{
			palabra+=caracter;
			}
			break;
		case 3:
			if ((etiqueta==true)&&(bandera==false)){
				if ((palabra.equalsIgnoreCase("XML"))&&(xmlb==false)){
					xmlb=true;
				}else if ((palabra.equalsIgnoreCase("Album"))&&(albumb==false)){
					Album temp = new Album(null,null,null,null);
					Album.add(temp);
					albumb=true;
				}else if ((palabra.equalsIgnoreCase("Autor"))&&(autorb==false)){
					autorb=true;
				}else if ((palabra.equalsIgnoreCase("Titulo"))&&(titulob==false)){
					titulob=true;
				}else if ((palabra.equalsIgnoreCase("Formato"))&&(formatob==false)){
					formatob=true;
				}else if ((palabra.equalsIgnoreCase("Portada"))&&(portadab==false)){
					portadab=true;
				}else if ((palabra.equalsIgnoreCase("Portada"))&&(portadab==true)){
					portadab=false;
				}else if ((palabra.equalsIgnoreCase("Formato"))&&(formatob==true)){
					formatob=false;
				}else if ((palabra.equalsIgnoreCase("Titulo"))&&(titulob==true)){
					titulob=false;
				}else if((palabra.equalsIgnoreCase("Autor"))&&(autorb==true)){
					autorb=false;
				}else if ((palabra.equalsIgnoreCase("Album"))&&(albumb==true)){
					albumb=false;
				}else if ((palabra.equalsIgnoreCase("XML"))&&(xmlb==true)){
					xmlb=false;
					
				}
				etiqueta=false;
				palabra=null;
				
			}else if((etiqueta==false)){
				if (palabra==null){
					palabra=""+caracter;
				}else{
				palabra+=caracter;
				}				
			}else if((etiqueta==true)&&(bandera==true)){
				if(xmlb){
					if (albumb==true){
						if(autorb==true){
						Album.get((Album.size())-1).setAutor(null);
						}else if(titulob==true){
						Album.get((Album.size())-1).setTitulo(null);
						}else if(formatob==true){
							Album.get((Album.size())-1).setFormato(null);
						}else if (portadab==true){
							Album.get((Album.size())-1).setPortada(null);
						}else{
							Album.remove((Album.size())-1);
						}
					}else{
						xmlb=true;
					}
				}
				etiqueta=false;
				bandera=false;
			}
			break;
		case 4:
			if((etiqueta==true)){
				error+="Error lexico en la linea: "+fila+", posicion: "+columna+", simbolo: "+caracter+"\n";
				bandera =true;
			}else{
				if (palabra==null){
					palabra=""+caracter;
				}else{
				palabra+=caracter;
				}
			}
			break;
		case 5:
			if (!(etiqueta==true)){
				if (palabra==null){
					palabra=""+caracter;
				}else{
				palabra+=caracter;
				}
			}
			break;
		case 6:
			if(!(etiqueta==true)){
				if (palabra==null){
					palabra=""+caracter;
				}else{
				palabra+=caracter;
				}
			}
			break;
		}
	}
}
