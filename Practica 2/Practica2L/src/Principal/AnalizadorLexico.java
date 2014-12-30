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
	boolean cancionesb=false;
	boolean pistasb=false;
	
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
	boolean lectura =false;
	
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
						}else if(cancionesb==true){
							if(pistasb==true){//es una pista.
								Album.get(Album.size()-1).AgregarCanciones(palabra);
								palabra=null;
								etiqueta=true;
							}else if(pistasb==false){//pistas esta cerrado, texto entre etiquetas
								error+="Error sintactico0 en la linea: "+fila+" por caracteres fuera de lugar "+caracter+"\n";
							}
						}else{//si ninguna es true, hay texto entre la ultima etiqueta y antes de cerrar el album.
						
							error+="Error sintactico1 en la linea: "+fila+" por caracteres fuera de lugar"+caracter+"\n";
						}
					}else if (albumb==false){//album esta cerrado. Hay texto entre esa etiqueta y la etiqueta de cierre de xml.
						error+="Error sintactico2 en la linea: "+fila+" por caracteres fuera de lugar"+caracter+"\n";
					}
				}else if(xmlb==false){//xml esta cerrado. Hay texto despues del cierre de xml.O al iniciar el documento.
					error+="Error sintactico3 en la linea: "+fila+". No se ha abierto la etiqueta XML. Caracteres fuera de lugar: "+caracter+"\n";
					
				}
				
			}else if(bandera==true){//hay un error desde la primera etiqueta.
				palabra=null;//no guardo el texto, puesto que hay error.
				etiqueta=true;//abro una etiqueta.
			}
				
			}else{//palabra es null. empieza a guardar una etiqueta.
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
//CASO 1: ETIQUETA "SIN" ERROR
			if ((etiqueta==true)&&(bandera==false)){
				
				if(banderadiag==false){
					if ((palabra.equalsIgnoreCase("XML"))&&(xmlb==false)){//se abre xml
						xmlb=true;
					}else if((palabra.equalsIgnoreCase("XML"))&&(xmlb==true)){//ya se abrio xml, falta diagonal
						Album.clear();//elimino lo leído.
						xmlb=false;
						error+="Falta diagonal en etiqueta de cierre de XML. Fila"+fila+"\n";
					}
					else if ((palabra.equalsIgnoreCase("Album"))&&(albumb==false)&&(xmlb==true)){
						Album temp = new Album("Desconocido","Desconocido","Desconocido","Desconocido.jpg");
						Album.add(temp);
						albumb=true;
					}else if ((palabra.equalsIgnoreCase("Album"))&&(albumb==false)&&(xmlb==false)){
						error+="Debe abrir xml antes de Album. Fila"+fila+"\n";
					}else if((palabra.equalsIgnoreCase("Album"))&&(albumb==true)){//falta diagonal
						Album.remove((Album.size())-1);
						albumb=false;
						error+="Falta diagonal en etiqueta de cierre de Album. Fila"+fila+"\n";
					}
					else if ((palabra.equalsIgnoreCase("Autor"))&&(autorb==false)&&(albumb==true)){
						autorb=true;
					}else if ((palabra.equalsIgnoreCase("Autor"))&&(autorb==false)&&(albumb==false)){
						error+="Debe abrir Album antes de Autor. Fila"+fila+"\n";
					}else if((palabra.equalsIgnoreCase("Autor"))&&(autorb==true)){//falta diagonal
						Album.get((Album.size())-1).setAutor("Desconocido");
						autorb=false;
						error+="Falta diagonal en etiqueta de cierre de Autor. Fila"+fila+"\n";
					}
					else if ((palabra.equalsIgnoreCase("Titulo"))&&(titulob==false)&&(albumb==true)){
						titulob=true;
					}else if((palabra.equalsIgnoreCase("Titulo"))&&(titulob==false)&&(albumb==false)){
						error+="Debe abrir Album antes de Titulo. Fila"+fila+"\n";
					}else if((palabra.equalsIgnoreCase("Titulo"))&&(titulob==true)){
						Album.get((Album.size())-1).setTitulo("Desconocido");
						titulob=false;
						error+="Falta diagonal en etiqueta de cierre de Titulo. Fila"+fila+"\n";
					}
					else if ((palabra.equalsIgnoreCase("Formato"))&&(formatob==false)&&(albumb==true)){
						formatob=true;
					}else if ((palabra.equalsIgnoreCase("Formato"))&&(formatob==false)&&(albumb==false)){
						error+="Debe abrir Album antes de Formato. Fila"+fila+"\n";
					}else if((palabra.equalsIgnoreCase("Formato"))&&(formatob==true)){//falta diagonal
						Album.get((Album.size())-1).setFormato("Desconocido");
						formatob=false;
						error+="Falta diagonal en etiqueta de cierre de Formato. Fila"+fila+"\n";
					}
					else if ((palabra.equalsIgnoreCase("Portada"))&&(portadab==false)&&(albumb==true)){
						portadab=true;
					}else if ((palabra.equalsIgnoreCase("Portada"))&&(portadab==false)&&(albumb==false)){
						error+="Debe abrir Album antes de Portada. Fila"+fila+"\n";
					}else if((palabra.equalsIgnoreCase("Portada"))&&(portadab==true)){//falta diagonal
						Album.get(Album.size()-1).setPortada("Desconocido.jpg");
						portadab=false;
						error+="Falta diagonal en etiqueta de cierre de Portada. Fila"+fila+"\n";
					}
					else if((palabra.equalsIgnoreCase("Canciones"))&&(cancionesb==false)&&(albumb==true)){//no se ha abierto canciones
						cancionesb=true;
					}else if((palabra.equalsIgnoreCase("Canciones"))&&(cancionesb==false)&&(albumb==false)){
						error+="Debe abrir Album antes de Canciones. Fila"+fila+"\n";
					}
					else if((palabra.equalsIgnoreCase("Canciones"))&&(cancionesb==true)){//falta diagonal 
						Album.get(Album.size()-1).LimpiarCanciones();
					}
					else if((palabra.equalsIgnoreCase("Pistas"))&&(pistasb==false)&&(cancionesb==true)){
						pistasb=true;
					}else if((palabra.equalsIgnoreCase("Pistas"))&&(pistasb==false)&&(cancionesb==false)){
						error+="Debe abrir canciones, antes de pistas. Fila"+fila+"\n";
					}else if((palabra.equalsIgnoreCase("Pistas"))&&(pistasb==true)){//falta diagonal
						Album.get(Album.size()-1).EliminarCanciones();
						pistasb=false;
						error+="Falta diagonal en etiqueta de cierre de Pistas. Fila"+fila+"\n";
					}
					else{// no es una palabra permitida.
						if(xmlb==true){//esta abierto xml
							if (albumb==true){//esta abierto album
								if(autorb==true){//esta abierto autor
									error+="Palabra equivocada en etiqueta de cierre Autor. Fila"+fila+"\n";
									Album.get((Album.size())-1).setAutor("Desconocido");
									autorb=false;
								}else if(titulob==true){
									error+="Palabra equivocada en etiqueta de cierre Titulo. Fila"+fila+"\n";
									Album.get((Album.size())-1).setTitulo("Desconocido");
									titulob=false;
								}else if(formatob==true){
									error+="Palabra equivocada en etiqueta de cierre Formato. Fila"+fila+"\n";
									Album.get((Album.size())-1).setFormato("Desconocido");
									formatob=false;
								}else if(portadab==true){
									error+="Palabra equivocada en etiqueta de cierre Portada. Fila"+fila+"\n";
									Album.get((Album.size())-1).setPortada("Desconocido");
									portadab=false;
								}else if (cancionesb==true){
									if(pistasb==true){
										error+="Palabra equivocada en etiqueta de cierre Pistas. Fila"+fila+"\n";
										Album.get((Album.size())-1).EliminarCanciones();
										pistasb=false;
									}else{
										error+="Palabra equivocada en etiqueta de cierre Canciones. Fila"+fila+"\n";
										Album.get((Album.size())-1).LimpiarCanciones();
										cancionesb=false;
									}
								}else {//Todas estan cerradas.
									error+="Palabra equivocada en etiqueta de cierre Album. Fila"+fila+"\n";
									Album.remove((Album.size())-1);
									albumb=false;
								}
							}else{//esta cerrado album. problema con el cierre de xml
								error+="Palabra equivocada en etiqueta de cierre XML. Fila"+fila+"\n";
							}
						}else{//error en la etiqueta de apertura 
							error+="Palabra no permitida en etiqueta de apertura. Linea"+fila+"\n";
						}
					}
			   }else if (banderadiag==true){
					banderadiag=false;
					if ((palabra.equalsIgnoreCase("Pistas"))&&(pistasb==true)){
						pistasb=false;
					}else if ((palabra.equalsIgnoreCase("Pistas"))&&(pistasb==false)){//viene diagonal en etiqueta de apertura
						error+="No se ha abierto la etiqueta de pistas. Linea"+fila+"\n";
					}
					else if ((palabra.equalsIgnoreCase("Canciones"))&&(cancionesb==true)){
						cancionesb=false;
					}else if ((palabra.equalsIgnoreCase("Canciones"))&&(cancionesb==false)){
						error+="No se ha abierto la etiqueta de Canciones. Linea"+fila+"\n";
					}
					else if ((palabra.equalsIgnoreCase("Titulo"))&&(titulob==true)){
						titulob=false;
					}else if ((palabra.equalsIgnoreCase("Titulo"))&&(titulob==false)){
						error+="No se ha abierto la etiqueta de Titulo. Linea"+fila+"\n";
					}
					else if((palabra.equalsIgnoreCase("Autor"))&&(autorb==true)){
						autorb=false;
					}else if((palabra.equalsIgnoreCase("Autor"))&&(autorb==false)){
						error+="No se ha abierto la etiqueta de Autor. Linea"+fila+"\n";
					}
					else if((palabra.equalsIgnoreCase("Formato"))&&(formatob==true)){
						formatob=false;
					}else if((palabra.equalsIgnoreCase("Formato"))&&(formatob==false)){
						error+="No se ha abierto la etiqueta de Formato. Linea"+fila+"\n";
					}
					else if((palabra.equalsIgnoreCase("Portada"))&&(portadab==true)){
						autorb=false;
					}else if((palabra.equalsIgnoreCase("Portada"))&&(portadab==false)){
						error+="No se ha abierto la etiqueta de Portada. Linea"+fila+"\n";
					}
					else if ((palabra.equalsIgnoreCase("Album"))&&(albumb==true)){
						albumb=false;
					}else if ((palabra.equalsIgnoreCase("Album"))&&(albumb==false)){
						error+="No se ha abierto la etiqueta de Album. Linea"+fila+"\n";
					}
					else if ((palabra.equalsIgnoreCase("XML"))&&(xmlb==true)){
						xmlb=false;
					}else if ((palabra.equalsIgnoreCase("XML"))&&(xmlb==false)){
						error+="No se ha abierto la etiqueta de XML. Linea"+fila+"\n";
					}
					else{//no es ninguna de la palabras.
						if(xmlb==true){//esta abierto xml
							if (albumb==true){//esta abierto album
								if(autorb==true){//esta abierto autor
									error+="Palabra equivocada en etiqueta de cierre Autor. Fila"+fila+"\n";
									Album.get((Album.size())-1).setAutor("Desconocido");
									autorb=false;
								}else if(titulob==true){
									error+="Palabra equivocada en etiqueta de cierre Titulo. Fila"+fila+"\n";
									Album.get((Album.size())-1).setTitulo("Desconocido");
									titulob=false;
								}else if(formatob==true){
									error+="Palabra equivocada en etiqueta de cierre Formato. Fila"+fila+"\n";
									Album.get((Album.size())-1).setFormato("Desconocido");
									formatob=false;
								}else if(portadab==true){
									error+="Palabra equivocada en etiqueta de cierre Portada. Fila"+fila+"\n";
									Album.get((Album.size())-1).setPortada("Desconocido");
									portadab=false;
								}else if (cancionesb==true){
									if(pistasb==true){
										error+="Palabra equivocada en etiqueta de cierre Pistas. Fila"+fila+"\n";
										Album.get((Album.size())-1).EliminarCanciones();
										pistasb=false;
									}else{
										error+="Palabra equivocada en etiqueta de cierre Canciones. Fila"+fila+"\n";
										Album.get((Album.size())-1).LimpiarCanciones();
										cancionesb=false;
									}
								}else {//Todas estan cerradas.
									error+="Palabra equivocada en etiqueta de cierre Album. Fila"+fila+"\n";
									Album.remove((Album.size())-1);
									albumb=false;
								}
							}else{//esta cerrado album. problema con el cierre de xml
								error+="Palabra equivocada en etiqueta de cierre XML. Fila"+fila+"\n";
								Album.clear();
							}
						}else{//error en la etiqueta de apertura 
							error+="Palabra no permitida en etiqueta de apertura. Linea"+fila+"\n";
							Album.clear();
						}
					}
				}
				etiqueta=false;
				palabra=null;
//CASO2: NO ETIQUETA			
			}else if((etiqueta==false)){
				if (palabra==null){
					palabra=""+caracter;
				}else{
				palabra+=caracter;
				}
//CASO3: ETIQUETA CON ERROR				
			}else if((etiqueta==true)&&(bandera==true)){//la palabra es de una etiqueta y trae error.
				
				if (banderadiag==true){//la etiqueta es de cierre.
					banderadiag=false;
					if ((palabra.equalsIgnoreCase("Pistas"))&&(pistasb==true)){//pistas tiene un error.
						Album.get(Album.size()-1).EliminarCanciones();
						pistasb=false;
						bandera= false;
					}else if ((palabra.equalsIgnoreCase("Canciones"))&&(cancionesb==true)){
						Album.get(Album.size()-1).LimpiarCanciones();
						cancionesb=false;
						bandera= false;
					}else if ((palabra.equalsIgnoreCase("Titulo"))&&(titulob==true)){
						Album.get(Album.size()-1).setTitulo("Desconocido");
						titulob=false;
						bandera= false;
					}else if((palabra.equalsIgnoreCase("Autor"))&&(autorb==true)){
						Album.get(Album.size()-1).setAutor("Desconocido");
						autorb=false;
						bandera= false;
					}else if((palabra.equalsIgnoreCase("Formato"))&&(formatob==true)){
						Album.get(Album.size()-1).setFormato("Desconocido");
						formatob=false;
						bandera= false;
					}else if((palabra.equalsIgnoreCase("Portada"))&&(portadab==true)){
						Album.get(Album.size()-1).setPortada("Desconocido.jpg");
						autorb=false;
						bandera= false;
					}else if ((palabra.equalsIgnoreCase("Album"))&&(albumb==true)){
						Album.remove(Album.size()-1);
						albumb=false;
						bandera= false;
					}else if ((palabra.equalsIgnoreCase("XML"))&&(xmlb==true)){
						Album.clear();
						bandera= false;
						xmlb=false;
					}else{//no es ninguna de la palabras, ya abiertas.
						if(xmlb==true){//esta abierto xml
							if (albumb==true){//esta abierto album
								if(autorb==true){//esta abierto autor
									error+="Palabra equivocada en etiqueta de cierre Autor. Fila"+fila+"\n";
									Album.get((Album.size())-1).setAutor("Desconocido");
									autorb=false;
									bandera= false;
								}else if(titulob==true){
									error+="Palabra equivocada en etiqueta de cierre Titulo. Fila"+fila+"\n";
									Album.get((Album.size())-1).setTitulo("Desconocido");
									titulob=false;
									bandera= false;
								}else if(formatob==true){
									error+="Palabra equivocada en etiqueta de cierre Formato. Fila"+fila+"\n";
									Album.get((Album.size())-1).setFormato("Desconocido");
									formatob=false;
									bandera= false;
								}else if(portadab==true){
									error+="Palabra equivocada en etiqueta de cierre Portada. Fila"+fila+"\n";
									Album.get((Album.size())-1).setPortada("Desconocido");
									portadab=false;
									bandera= false;
								}else if (cancionesb==true){
									if(pistasb==true){
										error+="Palabra equivocada en etiqueta de cierre Pistas. Fila"+fila+"\n";
										Album.get((Album.size())-1).EliminarCanciones();
										pistasb=false;
										bandera= false;
									}else{
										error+="Palabra equivocada en etiqueta de cierre Canciones. Fila"+fila+"\n";
										Album.get((Album.size())-1).LimpiarCanciones();
										cancionesb=false;
										bandera= false;
									}
								}else {//Todas estan cerradas.
									error+="Palabra equivocada en etiqueta de cierre Album. Fila"+fila+"\n";
									Album.remove((Album.size())-1);
									albumb=false;
									bandera= false;
								}
							}else{//esta cerrado album. problema con el cierre de xml
								error+="Palabra equivocada en etiqueta de cierre XML. Fila"+fila+"\n";
								Album.clear();
								bandera= false;
								xmlb=false;
							}
						}else{//XML no esta abierto. Error en la etiqueta de apertura 
							error+="Palabra no permitida en etiqueta de apertura. Linea"+fila+"\n";
							Album.clear();
						}
					}
				etiqueta=false;
				banderadiag=false;
				palabra=null;
		
				}else if (banderadiag==false){
					//Si la primer etiqueta tiene error: no se habilita.
					//Si la etiqueta de cierre no tiene diagonal y tiene error: elimina lo leido
					if ((palabra.equalsIgnoreCase("XML"))&&(xmlb==false)){//No se abre xml.
						xmlb=false;
					}else if((palabra.equalsIgnoreCase("XML"))&&(xmlb==true)){//ya se abrio xml, falta diagonal
						Album.clear();//elimino lo leído.
						xmlb=false;
						error+="Falta diagonal en etiqueta de cierre de XML. Fila"+fila+"\n";
					}
					else if ((palabra.equalsIgnoreCase("Album"))&&(albumb==false)&&(xmlb==true)){
						Album temp = new Album("Desconocido","Desconocido","Desconocido","Desconocido.jpg");
						Album.add(temp);
						albumb=false;
					}else if ((palabra.equalsIgnoreCase("Album"))&&(albumb==false)&&(xmlb==false)){
						error+="Debe abrir xml antes de Album. Fila"+fila+"\n";
					}else if((palabra.equalsIgnoreCase("Album"))&&(albumb==true)){//falta diagonal
						Album.remove((Album.size())-1);
						albumb=false;
						error+="Falta diagonal en etiqueta de cierre de Album. Fila"+fila+"\n";
					}
					else if ((palabra.equalsIgnoreCase("Autor"))&&(autorb==false)&&(albumb==true)){
						autorb=false;
					}else if ((palabra.equalsIgnoreCase("Autor"))&&(autorb==false)&&(albumb==false)){
						error+="Debe abrir Album antes de Autor. Fila"+fila+"\n";
					}else if((palabra.equalsIgnoreCase("Autor"))&&(autorb==true)){//falta diagonal
						Album.get((Album.size())-1).setAutor("Desconocido");
						autorb=false;
						error+="Falta diagonal en etiqueta de cierre de Autor. Fila"+fila+"\n";
					}
					else if ((palabra.equalsIgnoreCase("Titulo"))&&(titulob==false)&&(albumb==true)){
						titulob=false;
					}else if((palabra.equalsIgnoreCase("Titulo"))&&(titulob==false)&&(albumb==false)){
						error+="Debe abrir Album antes de Titulo. Fila"+fila+"\n";
					}else if((palabra.equalsIgnoreCase("Titulo"))&&(titulob==true)){
						Album.get((Album.size())-1).setTitulo("Desconocido");
						titulob=false;
						error+="Falta diagonal en etiqueta de cierre de Titulo. Fila"+fila+"\n";
					}
					else if ((palabra.equalsIgnoreCase("Formato"))&&(formatob==false)&&(albumb==true)){
						formatob=false;
					}else if ((palabra.equalsIgnoreCase("Formato"))&&(formatob==false)&&(albumb==false)){
						error+="Debe abrir Album antes de Formato. Fila"+fila+"\n";
					}else if((palabra.equalsIgnoreCase("Formato"))&&(formatob==true)){//falta diagonal
						Album.get((Album.size())-1).setFormato("Desconocido");
						formatob=false;
						error+="Falta diagonal en etiqueta de cierre de Formato. Fila"+fila+"\n";
					}
					else if ((palabra.equalsIgnoreCase("Portada"))&&(portadab==false)&&(albumb==true)){
						portadab=false;
					}else if ((palabra.equalsIgnoreCase("Portada"))&&(portadab==false)&&(albumb==false)){
						error+="Debe abrir Album antes de Portada. Fila"+fila+"\n";
					}else if((palabra.equalsIgnoreCase("Portada"))&&(portadab==true)){//falta diagonal
						Album.get(Album.size()-1).setPortada("Desconocido.jpg");
						portadab=false;
						error+="Falta diagonal en etiqueta de cierre de Portada. Fila"+fila+"\n";
					}
					else if((palabra.equalsIgnoreCase("Canciones"))&&(cancionesb==false)&&(albumb==true)){//no se ha abierto canciones
						cancionesb=false;
					}else if((palabra.equalsIgnoreCase("Canciones"))&&(cancionesb==false)&&(albumb==false)){
						error+="Debe abrir Album antes de Canciones. Fila"+fila+"\n";
					}
					else if((palabra.equalsIgnoreCase("Canciones"))&&(cancionesb==true)){//falta diagonal 
						Album.get(Album.size()-1).LimpiarCanciones();
					}
					else if((palabra.equalsIgnoreCase("Pistas"))&&(pistasb==false)&&(cancionesb==true)){
						pistasb=false;
					}else if((palabra.equalsIgnoreCase("Pistas"))&&(pistasb==false)&&(cancionesb==false)){
						error+="Debe abrir canciones, antes de pistas. Fila"+fila+"\n";
					}else if((palabra.equalsIgnoreCase("Pistas"))&&(pistasb==true)){//falta diagonal
						Album.get(Album.size()-1).EliminarCanciones();
						pistasb=false;
						error+="Falta diagonal en etiqueta de cierre de Pistas. Fila"+fila+"\n";
					}
					else{// no es una palabra permitida.
						if(xmlb==true){//esta abierto xml
							if (albumb==true){//esta abierto album
								if(autorb==true){//esta abierto autor
									error+="Palabra equivocada en etiqueta de cierre Autor. Fila"+fila+"\n";
									Album.get((Album.size())-1).setAutor("Desconocido");
									autorb=false;
								}else if(titulob==true){
									error+="Palabra equivocada en etiqueta de cierre Titulo. Fila"+fila+"\n";
									Album.get((Album.size())-1).setTitulo("Desconocido");
									titulob=false;
								}else if(formatob==true){
									error+="Palabra equivocada en etiqueta de cierre Formato. Fila"+fila+"\n";
									Album.get((Album.size())-1).setFormato("Desconocido");
									formatob=false;
								}else if(portadab==true){
									error+="Palabra equivocada en etiqueta de cierre Portada. Fila"+fila+"\n";
									Album.get((Album.size())-1).setPortada("Desconocido");
									portadab=false;
								}else if (cancionesb==true){
									if(pistasb==true){
										error+="Palabra equivocada en etiqueta de cierre Pistas. Fila"+fila+"\n";
										Album.get((Album.size())-1).EliminarCanciones();
										pistasb=false;
									}else{
										error+="Palabra equivocada en etiqueta de cierre Canciones. Fila"+fila+"\n";
										Album.get((Album.size())-1).LimpiarCanciones();
										cancionesb=false;
									}
								}else {//Todas estan cerradas.
									error+="Palabra equivocada en etiqueta de cierre Album. Fila"+fila+"\n";
									Album.remove((Album.size())-1);
									albumb=false;
								}
							}else{//esta cerrado album. problema con el cierre de xml
								error+="Palabra equivocada en etiqueta de cierre XML. Fila"+fila+"\n";
							}
						}else{//error en la etiqueta de apertura 
							error+="Palabra no permitida en etiqueta de apertura. Linea"+fila+"\n";
						}
					}
				}
				etiqueta=false;
				palabra=null;
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
			if((etiqueta==false)){
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
