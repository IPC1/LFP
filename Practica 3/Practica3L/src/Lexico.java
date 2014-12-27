import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Lexico {
	
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
	boolean inicio =false;
	
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
						}else if (cancionesb==true){
							if(pistasb==true){
								Album.get((Album.size())).AgregarCanciones(palabra);
								palabra=null;
								etiqueta=true;
							}else if (pistasb==false){//esta cerrado pistas y hay texto entre su etiqueta y la de cierre de canciones.
								error+="Error sintactico0 en la linea: "+fila+" por caracteres fuera de lugar"+palabra+"\n";
								palabra=null;	
								etiqueta=true;
							}
						}else{//si ninguna es true, hay texto entre la ultima etiqueta y antes de cerrar el album.
							if(albumb==true){

							}
							error+="Error sintactico1 en la linea: "+fila+" por caracteres fuera de lugar"+palabra+"\n";
						}
					}else{//album esta cerrado. Hay texto entre esa etiqueta y la etiqueta de cierre de xml.
						error+="Error sintactico2 en la linea: "+fila+" por caracteres fuera de lugar"+palabra+"\n";
					}
				}else{//xml esta cerrado. Hay texto despues del cierre de xml, o antes de la apertura.
					error+="Error sintactico3 en la linea: "+fila+". Caracteres fuera de lugar: "+palabra+"\n";
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
			  if(banderadiag==false){//se supone que no hay error y es etiqueta de apertura
				  if(xmlb==true){
					  if (albumb==true){
						  if((palabra.equalsIgnoreCase("Autor"))&&(autorb==true)){//falta diagonal de cierre autor
							  error+="Falta diagonal en etiqueta de cierre de Autor. Fila"+fila+"\n";
							  Album.get((Album.size())-1).setAutor(null);
							  autorb=false;
						  }else if ((palabra.equalsIgnoreCase("Titulo"))&&(titulob==true)){
							  error+="Falta diagonal en etiqueta de cierre de Titulo. Fila"+fila+"\n";
							  Album.get((Album.size())-1).setTitulo(null);
							  titulob=false;
						  }else if((palabra.equalsIgnoreCase("Formato"))&&(formatob==true)){
							  error+="Falta diagonal en etiqueta de cierre de Formato. Fila"+fila+"\n";
							  Album.get((Album.size())-1).setFormato(null);
							  formatob=false;
						  }else if ((palabra.equalsIgnoreCase("Portada"))&&(portadab==true)){
							  error+="Falta diagonal en etiqueta de cierre de Portada. Fila"+fila+"\n";
							  Album.get((Album.size())-1).setPortada(null);
							  portadab=false;
						  }else if ((cancionesb=true)){
							  if((palabra.equalsIgnoreCase("Pistas"))&&(pistasb==true)){
								  error+="Falta diagonal en etiqueta de cierre de Pistas. Fila"+fila+"\n";
								  Album.get((Album.size())-1).EliminarCanciones();
								  pistasb=false;
							  }else if((palabra.equalsIgnoreCase("Canciones"))){
								  error+="Falta diagonal en etiqueta de cierre de Canciones. Fila"+fila+"\n";
								  Album.get((Album.size())-1).LimpiarCanciones();
								  cancionesb=false;
							  }
						  }else if ((palabra.equalsIgnoreCase("Album"))){
							  error+="Falta diagonal en etiqueta de cierre de Album. Fila"+fila+"\n";
							  Album.remove((Album.size())-1);
							  albumb=false;
						  }
					  }else if (palabra.equalsIgnoreCase("XML")){
						  error+="Falta diagonal en etiqueta de cierre de XML. Fila"+fila+"\n";
						  Album.clear();
						  bandera=true;
					  }
				  }
				  
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
				}else if ((palabra.equalsIgnoreCase("Canciones"))&&(cancionesb==false)){
					cancionesb=true;
				}else if((palabra.equalsIgnoreCase("Pistas"))&&(pistasb==false)){
					pistasb=true;
				}else{
					error+="Palabra no permitida en etiqueta de apertura. Linea"+fila+"\n";
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
					}else if ((palabra.equalsIgnoreCase("Canciones"))&&(cancionesb==true)){
						cancionesb=false;
					}else if((palabra.equalsIgnoreCase("Pistas"))&&(pistasb==true)){
						pistasb=false;
					}else if (palabra.equalsIgnoreCase("Portada")&&palabra.equalsIgnoreCase("Formato")&&
							palabra.equalsIgnoreCase("Titulo")&&palabra.equalsIgnoreCase("Album")&&
							palabra.equalsIgnoreCase("XML")&&palabra.equalsIgnoreCase("Canciones")&&
							palabra.equalsIgnoreCase("Autor")&&palabra.equalsIgnoreCase("Pistas")){//Si no es palabra permitida en etiquetas
						error+="Palabra no permitida en etiqueta de cierre. Linea"+fila+"\n";
					}else if ((palabra.equalsIgnoreCase("Portada"))&&(portadab==false)){//Empieza a ver si solo hay etiqueta de cierre.
						error+="Falta etiqueta de apertura de Portada. Linea"+fila+"\n";
						bandera=true;
					}else if ((palabra.equalsIgnoreCase("Formato"))&&(formatob==false)){
						error+="Falta etiqueta de apertura de Formato. Linea"+fila+"\n";
						bandera=true;
					}else if ((palabra.equalsIgnoreCase("Titulo"))&&(titulob==false)){
						error+="Falta etiqueta de apertura de Titulo. Linea"+fila+"\n";
						bandera=true;
					}else if((palabra.equalsIgnoreCase("Autor"))&&(autorb==false)){
						error+="Falta etiqueta de apertura de Autor. Linea"+fila+"\n";
						bandera=true;
					}else if ((palabra.equalsIgnoreCase("Album"))&&(albumb==false)){
						error+="Falta etiqueta de apertura de Album. Linea"+fila+"\n";
						bandera=true;
					}else if ((palabra.equalsIgnoreCase("XML"))&&(xmlb==false)){
						error+="Falta etiqueta de apertura de XML. Linea"+fila+"\n";
						bandera=true;
					}else if ((palabra.equalsIgnoreCase("Canciones"))&&(cancionesb==false)){
						error+="Falta etiqueta de apertura de Canciones. Linea"+fila+"\n";
						bandera=true;
					}else if((palabra.equalsIgnoreCase("Pistas"))&&(pistasb==false)){
						error+="Falta etiqueta de apertura de Pistas. Linea"+fila+"\n";
						bandera=true;
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
				JOptionPane.showMessageDialog(null, "bandera,diag true etiqueta true");
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
							}else if(cancionesb==true){//la etiqueta de canciones esta abierta
								if(pistasb==true){//la etiqueta de pistas tambien esta abierta, aqui es el error
									Album.get(Album.size()-1).EliminarCanciones();
								}else if(pistasb==false){//pistas esta cerrado, el error es en canciones.
									Album.get((Album.size()-1)).LimpiarCanciones();//elimino todas las canciones
								}
							}else{//todas estan cerradas. el problema es en album.
								Album.remove((Album.size())-1);
								albumb=false;
							}
						}else if(albumb==false){//album esta cerrado. el problema es con etiqueta final xml.
							xmlb=false;
							bandera=true;
						}
					}else if(xmlb==false){//xml esta cerrado. Las lineas no pertenecen a la lectura. O el problema es con etiqueta inicial xml.
						error+="Error sintactico3 en la linea: "+fila+". No se ha abierto la etiqueta XML. Caracteres fuera de lugar: "+caracter+"\n";
						bandera=true;
						palabra=null;
						etiqueta=false;
						inicio =true;
					}
					etiqueta=false;
					bandera=false;
					banderadiag=false;
					palabra=null;
					
				}else if (banderadiag==false){//se supone que la primer etiqueta tiene error.
					if (xmlb==false){//no esta abierta xml
						inicio=true;		
					}else if(xmlb==true){//falta la diagonal de la etiqueta de cierre.
						if(albumb==true){
							if(autorb==true){
								error+="Falta cerrar etiqueta de autor, de album. Fila:"+fila+"\n";
							}if(titulob==true){
								error+="Falta cerrar etiqueta de titulo, de album. Fila:"+fila+"\n";
							}if (formatob==true){
								error+="Falta cerrar etiqueta de formato, de album. Fila:"+fila+"\n";
							}if(portadab==true){
								error+="Falta cerrar etiqueta de portada, de album. Fila:"+fila+"\n";
							}if(cancionesb==true){
								if (pistasb==true){
									error+="Falta cerrar etiqueta de pistas, de canciones, de album. Fila:"+fila+"\n";
								}else if(pistasb==false){
									error+="Falta cerrar etiqueta de canciones, de album. Fila:"+fila+"\n";
								}
							}
						}else if (albumb==false){
							error+="Falta la diagonal de etiqueta de cierre XML. Fila:"+fila+"\n";
						}
					}
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
			if (etiqueta==false){//no es palabra de una etiqueta
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