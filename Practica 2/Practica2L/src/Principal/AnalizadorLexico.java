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
	boolean banderadiag=false;
	
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
			if (palabra != null){//esta leyendo el texto entre etiquetas.
				if (bandera==false){//no hay errores de etiquetas.
				if(xmlb==true){//se abrio xml.
					if (albumb==true){//se abrio album
						if(autorb==true){
							Album.get((Album.size())-1).setAutor(palabra);//si es autor, lo guardo.
							
							palabra=null;//borro la palabra
							etiqueta=true;//siempre para indicar que se abre una etiqueta
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
						}else{//si ninguna es true, hay texto entre la ultima etiqueta y antes de cerrar el album.
							if(albumb==true){

							}
							error+="Error sintactico1 en la linea: "+fila+" por caracteres fuera de lugar"+caracter+"\n";
						}
					}else{//album esta cerrado. Hay texto entre esa etiqueta y la etiqueta de cierre de xml.
						error+="Error sintactico2 en la linea: "+fila+" por caracteres fuera de lugar"+caracter+"\n";
					}
				}else{//xml esta cerrado. Hay texto despues del cierre de xml.
					error+="Error sintactico3 en la linea: "+fila+". No se ha abierto la etiqueta XML. Caracteres fuera de lugar: "+caracter+"\n";
				}
				
			}else if(bandera==true){//hay un error desde la primera etiqueta.
				palabra=null;//no guardo el texto, puesto que hay error.
				etiqueta=true;//abro una etiqueta.
			}
				
			}else{//palabra es = null. empieza a guardar una etiqueta.
				etiqueta=true;
			}			
			break;
		case 2:
			if (palabra==null){//palabra nueva
				palabra=""+caracter;
			}else{
			palabra+=caracter;
			}
			break;
		case 3:
			if ((etiqueta==true)&&(bandera==false)){
			  if(banderadiag==false){
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
				
				}else{
					error+="Palabra no permitida en etiqueta de apertura. Linea"+fila;
				}
				etiqueta=false;
				palabra=null;
			   }else if (banderadiag==true){
					banderadiag=false;
					if ((palabra.equalsIgnoreCase("Portada"))&&(portadab==true)){
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
						
					}else{
						error+="Palabra no permitida en etiqueta de cierre. Linea"+fila;
					}
					etiqueta=false;
					palabra=null;
		
				}
			}else if((etiqueta==false)){
				if (palabra==null){
					palabra=""+caracter;
				}else{
				palabra+=caracter;
				}				
			}else if((etiqueta==true)&&(bandera==true)){//la palabra es de una etiqueta y trae error.
				if (banderadiag==true){//la etiqueta es de cierre.
					if(xmlb==true){//la etiqueta xml esta abierta
					if (albumb==true){//la etiqueta album esta abierta
						if(autorb==true){//la etiqueta autor esta abierta, aqui debe ser el error.
						Album.get((Album.size())-1).setAutor(null);
						autorb=false;
						}else if(titulob==true){//la etiqueta titulo esta abierta, aqui debe ser el error.
						Album.get((Album.size())-1).setTitulo(null);
						titulob=false;
						}else if(formatob==true){//la etiqueta formato esta abierta, aqui debe ser el error.
						Album.get((Album.size())-1).setFormato(null);
						formatob=false;
						}else if (portadab==true){//la etiqueta portada esta abierta, aqui debe ser el error.
						Album.get((Album.size())-1).setPortada(null);
						portadab=false;
						}else{//todas estan cerradas. el problema es en album.
						Album.remove((Album.size())-1);
						albumb=false;
						}
						
					}else{//album esta cerrado. el problema es con xml.
						xmlb=false;
						bandera=true;
					}
					}else{//xml esta cerrado. Las lineas no pertenecen a la lectura.
						error+="Error sintactico3 en la linea: "+fila+". No se ha abierto la etiqueta XML. Caracteres fuera de lugar: "+caracter+"\n";
					}
				etiqueta=false;
				bandera=false;
				banderadiag=false;
				palabra=null;
				}else if (banderadiag==false){//la primer etiqueta tiene error.
					if ((palabra.equalsIgnoreCase("XML"))&&(xmlb==false)){//procedimiento normal como si no tuviera error, pero con bandera activa.
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
					}
					bandera=true;
					etiqueta=false;
				}
			}
			break;
		case 4://numero
			if((etiqueta==true)){//hay error porque no hay numeros en etiquetas.
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
		case 5://diagonal
			if (!(etiqueta==true)){//no es palabra de una etiqueta
				if (palabra==null){
					palabra=""+caracter;
				}else{
				palabra+=caracter;
				}
			}else if (etiqueta==true){//palabra de una etiqueta
				banderadiag=true;
			}
			break;
		case 6://simbolos
			if(!(etiqueta==true)){
				if (palabra==null){
					palabra=""+caracter;
				}else{
				palabra+=caracter;
				}
			}else if(etiqueta==true){
				error+="Error lexico en la linea: "+fila+", posicion: "+columna+", simbolo: "+caracter+"\n";
				bandera=true;
			}
			break;
		}
	}
}
