import java.util.ArrayList;


public class Lexico {
static ArrayList <Album> Album = new ArrayList <Album>();
Tokens to= new Tokens ();
private boolean xmlb=false;
private boolean albumb=false;
private boolean autorb=false;
private boolean titulob=false;
private boolean formatob=false;
private boolean portadab=false;
private boolean cancionesb=false;
private boolean pistasb=false;

private String error="";
private boolean etiqueta=false;
private boolean bandera=false;
private boolean banderadiag=false;
private boolean xmle=false;
private boolean albume=false;
private boolean otrose=false;
private boolean palabrae=false;

private String palabra= null;

public void analizador (int tokenActual, char caracter, int fila, int columna){
	
if(xmle==false){
	switch (tokenActual){
//es un menor que	
	case 1:
//CASO1: 
		if ((palabra==null)&&(etiqueta==false)){//va a empezar a leer una etiqueta
			etiqueta=true;
			System.out.println(palabrae);
//CASO2
		}else if((palabra==null)&&(etiqueta==true)){//error lexico en etiqueta
			bandera=true;
			error+="Error lexico en la linea: "+fila+", posicion: "+columna+", simbolo: "+caracter+"\n";
		}
//CASO3
		else if ((palabra!=null)&&(etiqueta==false)){//Termino de leer entre etiquetas, palabra es lo que voy a guardar
			if((xmle==false)){//no hay error en xml
				if (xmlb==true){//xml ya fue abierto
					if(albume==false){//si no hay error en album.
						if (albumb==true){//album ya fue abierto
							if (otrose==false){
								if(autorb==true){//autor ya fue abierto
									Album.get((Album.size())-1).setAutor(palabra);//si es autor, lo guardo.
									palabra=null;//borro la palabra
									etiqueta=true;//siempre para indicar que se abre una etiqueta
								}else if(titulob==true){//titulo ya fue abierto
									Album.get((Album.size())-1).setTitulo(palabra);
									palabra=null;
									etiqueta=true;
								}else if(formatob==true){//formato ya fue abierto
									Album.get((Album.size())-1).setFormato(palabra);
									palabra=null;
									etiqueta=true;
								}else if (portadab==true){//portada ya fue abierto
									Album.get((Album.size())-1).setPortada(palabra);
									palabra=null;
									etiqueta=true;
								}else if (cancionesb==true){// canciones ya fue abierto
									if(pistasb==true){//pistas ya fue abierto
										Album.get((Album.size())-1).AgregarCanciones(palabra);
										System.out.println("guarde cancion");
										palabra=null;
										etiqueta=true;
									}else if (pistasb==false){//esta cerrado pistas y hay texto entre su etiqueta y la de cierre de canciones.
										error+="Error sintactico0 en la linea: "+fila+", entre etiquetas de Canciones por caracteres fuera de lugar: "+palabra+"\n";
										Album.get(Album.size()-1).LimpiarCanciones();// no leo las pistas porque hay error en canciones.
										palabra=null;	
										etiqueta=true;
									}
								}else{//solo album esta abierto. hay texto entre etiquetas no hay que leerlo puesto que hay error.
									Album.remove(Album.size()-1);
									palabra=null;
									error+="Error sintactico1 en la linea: "+fila+", entre etiquetas de Album por caracteres fuera de lugar: "+palabra+"\n";
								}
							}else if(otrose==true){
								palabra=null;
								etiqueta=true;
								otrose=false;
							}
						}else if(albumb==false){//solo xml esta abierto, caracteres entre etiquetas, no leo el archivo porque hay error en xml.
							error+="Error sintactico2 en la linea: "+fila+", entre etiquetas de XML por caracteres fuera de lugar: "+palabra+"\n";
							palabra=null;
							Album.clear();
						}
					}else if(albume==true){
						palabra=null;
					}
				}else if(xmlb==false){//xml no esta abierto, etiqueta no esta abierta, caracteres no permitidos antes de empezar
					error+="Error sintactico3 en la linea: "+fila+". Caracteres fuera de lugar: "+palabra+"\n";
					palabra=null;
				}
			}else if (xmle==true){
				palabra=null;
			}
//CASO:4
		}else if((palabra!=null)&&(etiqueta==true)){//error lexico en etiqueta
			bandera=true;
			error+="Error lexico en la linea: "+fila+", posicion: "+columna+", simbolo: "+caracter+"\n";
		}
		break;
//es una letra		
	case 2:
		if (palabra==null){//palabra nueva
			
			palabra=""+caracter;
		}else{//solo concatena.
			System.out.println(palabra);
		palabra+=caracter;
		}
		System.out.println(palabra);
		break;
//es un mayor que	
	case 3:
//CASO1:
		System.out.println(palabra);
		//termina de leer la primer etiqueta, "sin" errores.
		if ( (etiqueta==true)&&(bandera==false)&&(banderadiag==false)){
			switch(to.tipoToken(palabra)){
			case 1: //palabra es xml
				if (xmlb==false){
					xmlb=true;
					
				}else if(xmlb==true){
					error+="Falta diagonal en etiqueta de cierre de XML. Fila"+fila+"\n";
					 Album.clear();//hay error en xml, no se lee nada.
					 xmlb=false;
					 if(palabrae==true){//la palabra de cierre de album si venia dañada...
							error+="Palabra equivocada en etiqueta de cierre Album. Fila"+(fila-2)+"\n";
							Album.remove(Album.size()-1);
							albumb=false;
							palabrae=false;
					 }
				}
				palabra=null;
				etiqueta=false;
				break;
			case 2://palabra es album
				if (xmlb==true){
					if (albumb==false){
						if(albume==false){//la primera palabra de album viene dañada.
							Album temp = new Album("Desconocido","Desconocido","Desconocido","Desconocido.jpg");
							Album.add(temp);
							albumb=true;
						}else if (albume==true){
							albume=false;
						}
					}else if (albumb==true){
						error+="Falta diagonal en etiqueta de cierre de Album. Fila"+fila+"\n";
						Album.remove(Album.size()-1);//elimino el album que ya cree, por el error
						albumb=false;
						if(palabrae==true){//la palabra de cierre de album no venia dañada, hay una etiqueta de más.
							error+="Etiqueta de más. Fila "+(fila-2)+"\n";
							palabrae=false;
					 }
					}
				}else if(xmlb==false){
					error+="Debe abrir etiqueta XML, antes de etiqueta Album. Fila"+fila+"\n";
				}
				palabra=null;
				etiqueta=false;
				break;
			case 3: // palabra es autor
				
				if (albumb==true){
					if (autorb==false){
						autorb=true;
						if(palabrae==true){//la palabra de cierre de album no venia dañada, 
							//la etiqueta de autor de inicio viene dañada.
							error+="Etiqueta de apertura en autor, palabra no permitida. Fila"+(fila-2)+"\n";
							autorb=false;
							palabrae=false;
						}
					}else if(autorb==true){
						error+="Falta diagonal en etiqueta de cierre de Autor. Fila"+fila+"\n";
						Album.get(Album.size()-1).setAutor("Desconocido");
						autorb=false;
						if(palabrae==true){//la palabra de cierre de album no venia dañada, 
							//hay una etiqueta de mas.
							error+="Etiqueta de más. Fila"+(fila-2)+"\n";
							autorb=false;
							palabrae=false;
						}
					}
				}else if(albumb==false){
					if(albume==false){
						error+="Debe abrir etiqueta Album, antes de etiqueta Autor. Fila"+fila+"\n";
					}
				}
				palabra=null;
				etiqueta=false;
				break;
			case 4: //palabra es titulo
				if (albumb==true){
					if (titulob==false){
						titulob=true;
						if(palabrae==true){//la palabra de cierre de album no venia dañada, 
							//la etiqueta de titulo de inicio viene dañada.
							error+="Etiqueta de apertura en titulo, palabra no permitida. Fila"+(fila-2)+"\n";
							error+="Falta diagonal en etiqueta de cierre de Titulo. Fila"+fila+"\n";
							titulob=false;
							palabrae=false;
						}
					}else if(titulob==true){
						error+="Falta diagonal en etiqueta de cierre de Titulo. Fila"+fila+"\n";
						Album.get(Album.size()-1).setTitulo("Desconocido");
						titulob=false;
					}
				}else if(albumb==false){
					if(albume==false){
						error+="Debe abrir etiqueta Album, antes de etiqueta Autor. Fila"+fila+"\n";
					}
				}
				palabra=null;
				etiqueta=false;
				break;
			case 5://palabra es formato
				if (albumb==true){
					if (formatob==false){
						formatob=true;
						if(palabrae==true){//la palabra de cierre de album no venia dañada, 
							//la etiqueta de formato de inicio viene dañada.
							error+="Etiqueta de apertura en formato, palabra no permitida. Fila"+(fila-2)+"\n";
							error+="Falta diagonal en etiqueta de cierre de Formato. Fila"+fila+"\n";
							formatob=false;
							palabrae=false;
						}
					}else if(formatob==true){
						error+="Falta diagonal en etiqueta de cierre de Formato. Fila"+fila+"\n";
						Album.get(Album.size()-1).setFormato("Desconocido");
						formatob=false;
					}
				}else if(albumb==false){
					if(albume==false){
						error+="Debe abrir etiqueta Album, antes de etiqueta Autor. Fila"+fila+"\n";
					}
				}
				palabra=null;
				etiqueta=false;
				break;
			case 6: //palabra es portada
				if (albumb==true){
					if (portadab==false){
						portadab=true;
						if(palabrae==true){//la palabra de cierre de album no venia dañada, 
							//la etiqueta de portada de inicio viene dañada.
							error+="Etiqueta de apertura en portada, palabra no permitida. Fila"+(fila-2)+"\n";
							error+="Falta diagonal en etiqueta de cierre de Portadad. Fila"+fila+"\n";
							portadab=false;
							palabrae=false;
						}
					}else if(portadab==true){
						error+="Falta diagonal en etiqueta de cierre de Portadad. Fila"+fila+"\n";
						Album.get(Album.size()-1).setPortada("Desconocido.jpg");
						portadab=false;
					}
				}else if(albumb==false){
					if(albume==false){
						error+="Debe abrir etiqueta Album, antes de etiqueta Autor. Fila"+fila+"\n";
					}
				}
				palabra=null;
				etiqueta=false;
				break;
			case 7: //palabra es canciones
				if (albumb==true){
					if (cancionesb==false){
						cancionesb=true;
						if(palabrae==true){//la palabra de cierre de album no venia dañada, 
							//la etiqueta de canciones de inicio viene dañada.
							error+="Etiqueta de apertura en canciones, palabra no permitida. Fila"+(fila-2)+"\n";
							error+="Falta diagonal en etiqueta de cierre de Canciones. Fila"+fila+"\n";
							cancionesb=false;
							palabrae=false;
						}
					}else if(cancionesb==true){
						error+="Falta diagonal en etiqueta de cierre de Canciones. Fila"+fila+"\n";
						Album.get(Album.size()-1).LimpiarCanciones();;
						cancionesb=false;
					}
				}else if(albumb==false){
					if(albume==false){
						error+="Debe abrir etiqueta Album, antes de etiqueta Autor. Fila"+fila+"\n";
					}
				}
				palabra=null;
				etiqueta=false;
				break;
			case 8: //palabra es pistas
				
				if (cancionesb==true){
					if (pistasb==false){
						pistasb=true;
						if(palabrae==true){//la palabra de cierre de album no venia dañada, 
							//la etiqueta de pista de inicio viene dañada.
							System.out.println("pistas true");
							error+="Etiqueta de apertura en pistas, palabra no permitida. Fila"+(fila-2)+"\n";
							error+="Falta diagonal en etiqueta de cierre de Pistas. Fila"+fila+"\n";
							pistasb=false;
							palabrae=false;
						}
					}else if(pistasb==true){System.out.println(palabrae+"pistas");
						error+="Falta diagonal en etiqueta de cierre de Pistas. Fila"+fila+"\n";
						Album.get(Album.size()-1).EliminarCanciones();;
						pistasb=false;
					}
				}else if(cancionesb==false){
					if(palabrae==true){//la palabra de cierre de album no venia dañada, 
						//la etiqueta de canciones de inicio viene dañada.
						
						error+="Etiqueta de apertura en canciones, palabra no permitida. Fila"+(fila-2)+"\n";
						error+="Falta diagonal en etiqueta de cierre de Pistas. Fila"+fila+"\n";
						cancionesb=false;
						palabrae=false;
					}
					error+="Debe abrir etiqueta Canciones, antes de etiqueta Pistas. Fila"+fila+"\n";
				}
				palabra=null;
				etiqueta=false;
				break;
			case 9: //palabra no esta permitida
				//verificar si es etiqueta de cierre sin diagonal y mal escrita.
				if(palabrae==true){//la palabra de cierre de album no venia dañada, 
					//la etiqueta de apertura y de fin viene dañada
					error+="Etiqueta de apertura, palabra no permitida. Fila"+(fila-2)+"\n";
					error+="Falta diagonal en etiqueta de cierre. Fila"+fila+"\n";
					palabrae=false;
				}else if (xmlb==true){
					if (albumb==true){
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
						}else{//todas estan cerradas, se supone error en cierre de album
							palabrae=true;
							System.out.println("Pase palabrae true");
							otrose=true;
						}
						
					}else if(albumb==false){//no esta abierto album, error en cierre xml.
						if(albume==false){
							error+="Palabra equivocada en etiqueta de cierre XML. Fila"+fila+"\n";
						Album.clear();
						xmlb=false;
						}else if(albume==true){//si hay error en album, esta mal escrito el cierre tambien.
							albume=false;
						}
						
					}
				}else if (xmlb==false){//no se ha abierto el documento, etiqueta inicial mal escrita. O hay texto despues de cerrar.
					error+="Se debe abrir con etiqueta de apertura XML. Fila"+fila+"\n";
					xmle=true;
				}
				
				otrose=true;
				palabra=null;
				etiqueta=false;
				break;
			}
//CASO2:		
		//termina de leer la segunda etiqueta, "sin" errores.	
		}else if((etiqueta==true)&&(bandera==false)&&(banderadiag==true)){
			switch(to.tipoToken(palabra)){
			case 1: //palabra es xml
				if (xmlb==true){
					xmlb=false;
					if(palabrae==true){//la palabra de cierre de album si venia dañada...
						error+="Palabra equivocada en etiqueta de cierre Album. Fila"+(fila-2)+"\n";
						Album.remove(Album.size()-1);
						albumb=false;
						palabrae=false;
				 }
				}else if(xmlb==false){//no se ha abierto XML
					error+="Diagonal en etiqueta de apertura XML. Fila"+fila+"\n";
				}
				palabra=null;
				etiqueta=false;
				banderadiag=false;
				break;
			case 2: //palabra es album
				if (albumb==true){
					albumb=false;
					if(palabrae==true){//la palabra de cierre de album no venia dañada, hay una etiqueta de más.
						error+="Etiqueta de más. Fila "+(fila-2)+"\n";
						palabrae=false;
				 }
					
				}else if (albumb==false){
					if (albume==false){
						error+="Diagonal en etiqueta de apertura Album. Fila"+fila+"\n";
						albume=true;
					}else if(albume==true){
						albume=false;
					}
				}
				palabra=null;
				etiqueta=false;
				banderadiag=false;
				break;
			case 3: //palabra es autor
				System.out.println("leí autor");
				if (autorb==true){
					autorb=false;
				}else if (autorb==false){
					if(palabrae==true){//la palabra de cierre de album no venia dañada, 
						//la etiqueta de autor de inicio viene dañada.
						error+="Etiqueta de apertura en autor, palabra no permitida. Fila"+(fila-2)+"\n";
						autorb=false;
						palabrae=false;
					}
					System.out.println("etiqueta apertura autor");
					error+="Diagonal en etiqueta de apertura Autor. Fila"+fila+"\n";
				}
				palabra=null;
				etiqueta=false;
				banderadiag=false;
				break;
			case 4: //palabra es titulo
				if (titulob==true){
					titulob=false;
				}else if (titulob==false){
					error+="Diagonal en etiqueta de apertura Titulo. Fila"+fila+"\n";
					if(palabrae==true){//la palabra de cierre de album no venia dañada, 
						//la etiqueta de titulo de inicio viene dañada.
						error+="Etiqueta de apertura en titulo, palabra no permitida. Fila"+(fila-2)+"\n";
						titulob=false;
						palabrae=false;
					}
				}
				palabra=null;
				etiqueta=false;
				banderadiag=false;
				break;
			case 5://palabra es formato
				if (formatob==true){
					formatob=false;
				}else if (formatob==false){
					error+="Diagonal en etiqueta de apertura Formato. Fila"+fila+"\n";
					if(palabrae==true){//la palabra de cierre de album no venia dañada, 
						//la etiqueta de formato de inicio viene dañada.
						error+="Etiqueta de apertura en formato, palabra no permitida. Fila"+(fila-2)+"\n";
						formatob=false;
						palabrae=false;
					}
				}
				palabra=null;
				etiqueta=false;
				banderadiag=false;
				break;
			case 6: //palabra es portada
				if (portadab==true){
					portadab=false;
				}else if (portadab==false){
					error+="Diagonal en etiqueta de apertura Portada. Fila"+fila+"\n";
					if(palabrae==true){//la palabra de cierre de album no venia dañada, 
						//la etiqueta de portada de inicio viene dañada.
						error+="Etiqueta de apertura en portada, palabra no permitida. Fila"+(fila-2)+"\n";
						portadab=false;
						palabrae=false;
					}
				}
				palabra=null;
				etiqueta=false;
				banderadiag=false;
				break;
			case 7: //palabra es canciones
				if (cancionesb==true){
					cancionesb=false;
				}else if (cancionesb==false){
					error+="Diagonal en etiqueta de apertura Canciones. Fila"+fila+"\n";
					if(palabrae==true){//la palabra de cierre de album no venia dañada, 
						//la etiqueta de canciones de inicio viene dañada.
						error+="Etiqueta de apertura en canciones, palabra no permitida. Fila"+(fila-2)+"\n";
						cancionesb=false;
						palabrae=false;
					}
				}
				palabra=null;
				etiqueta=false;
				banderadiag=false;
				break;
			case 8: //palabra es pistas
				if(cancionesb==false){
					if(palabrae==true){//la palabra de cierre de album no venia dañada, 
						//la etiqueta de canciones de inicio viene dañada.
						error+="Etiqueta de apertura en canciones, palabra no permitida. Fila"+(fila-2)+"\n";
						error+="Falta diagonal en etiqueta de cierre de Pistas. Fila"+fila+"\n";
						cancionesb=false;
						palabrae=false;
					}
					error+="Debe abrir etiqueta Canciones, antes de etiqueta Pistas. Fila"+fila+"\n";
				}else if (cancionesb==true){
					if (pistasb==true){
					pistasb=false;
				}else if (pistasb==false){
					error+="Diagonal en etiqueta de apertura Pistas. Fila"+fila+"\n";
					if(palabrae==true){//la palabra de cierre de album no venia dañada, 
						//la etiqueta de pistas de inicio viene dañada.
						error+="Etiqueta de apertura en pistas, palabra no permitida. Fila"+(fila-2)+"\n";
						pistasb=false;
						palabrae=false;
					}
				}
				}
				
				palabra=null;
				etiqueta=false;
				banderadiag=false;
				break;
			case 9: //palabra no esta permitida
				//verificar si es etiqueta de cierre mal escrita.
				if(palabrae==true){//la palabra de cierre de album no venia dañada, 
					//la etiqueta de apertura viene dañada y la de cierre mal escrita.
					error+="Etiqueta de apertura, palabra no permitida. Fila"+(fila-2)+"\n";
					error+="Etiqueta de cierre mal escrita. Fila"+fila+"\n";
					palabrae=false;
				}else 
				if (xmlb==true){
					if (albumb==true){
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
						}else{//todas estan cerradas, error en cierre de album
							error+="Palabra equivocada en etiqueta de cierre Album. Fila"+fila+"\n";
							Album.remove(Album.size()-1);
							albumb=false;
						}
						
					}else if(albumb==false){//no esta abierto album, error en cierre xml.
						if(albume==false){
							error+="Palabra equivocada en etiqueta de cierre XML. Fila"+fila+"\n";
						Album.clear();
						xmlb=false;
						}else if(albume==true){//si hay error en album, esta mal escrito el cierre tambien.
							albume=false;
						}
					}
				}else if (xmlb==false){//no se ha abierto el documento, etiqueta inicial mal escrita. O hay texto despues de cerrar.
					error+="Se debe abrir con etiqueta de apertura XML. Fila"+fila+"\n";
					xmle=true;
				}
				
				palabra=null;
				etiqueta=false;
				banderadiag=false;
				break;
			}
//CASO3		
		//termina de leer la primer etiqueta, con errores.	
		}else if((etiqueta==true)&&(bandera==true)&&(banderadiag==false)){
			switch(to.tipoToken(palabra)){
			case 1: //palabra es xml
				if (xmlb==false){
					xmle=true;//hay error en la etiqueta de apertura xml.
				}else if(xmlb==true){
					if(palabrae==true){//la palabra de cierre de album si venia dañada...
						error+="Palabra equivocada en etiqueta de cierre Album. Fila"+(fila-2)+"\n";
						Album.remove(Album.size()-1);
						albumb=false;
						palabrae=false;
					}
					error+="Falta diagonal en etiqueta de cierre de XML. Fila"+fila+"\n";
					 Album.clear();//hay error en xml, no se lee nada.
					 xmlb=false;
					 
				}
				palabra=null;
				etiqueta=false;
				bandera =false;
				break;
			case 2://palabra es album
				if (xmlb==true){
					if (albumb==false){//album no esta abierto. Error al principio
						if(albume==true){
							albume=false;
						}else if(albume==false){
							albume=true;
						}
					}else if (albumb==true){//album esta abierto. Error en la etiqueta de cierre
						error+="Falta diagonal en etiqueta de cierre de Album. Fila"+fila+"\n";
						Album.remove(Album.size()-1);//elimino el album que ya cree, por el error
						if(palabrae==true){//la palabra de cierre de album no venia dañada, hay una etiqueta de más.
							error+="Etiqueta de más. Fila "+(fila-2)+"\n";
							palabrae=false;
						}
					}
				}else if(xmlb==false){
					error+="Debe abrir etiqueta XML, antes de etiqueta Album. Fila"+fila+"\n";
				}
				palabra=null;
				etiqueta=false;
				bandera =false;
				break;
			case 3: // palabra es autor
				if (albumb==true){
					if (autorb==false){
						otrose=true;
						if(palabrae==true){//la palabra de cierre de album no venia dañada, 
							//la etiqueta de Autor de inicio viene dañada.
							error+="Etiqueta de apertura en Autor, palabra no permitida. Fila"+(fila-2)+"\n";
							autorb=false;
							palabrae=false;
							otrose=false;
						}
					}else if(autorb==true){
						error+="Falta diagonal en etiqueta de cierre de Autor. Fila"+fila+"\n";
						Album.get(Album.size()-1).setAutor("Desconocido");
						autorb=false;
					}
				}else if(albumb==false){
					error+="Debe abrir etiqueta Album, antes de etiqueta Autor. Fila"+fila+"\n";
				}
				palabra=null;
				etiqueta=false;
				bandera =false;
				break;
			case 4: //palabra es titulo
				if (albumb==true){
					if (titulob==false){
						otrose=true;
						if(palabrae==true){//la palabra de cierre de album no venia dañada, 
							//la etiqueta de titulo de inicio viene dañada.
							error+="Etiqueta de apertura en titulo, palabra no permitida. Fila"+(fila-2)+"\n";
							titulob=false;
							palabrae=false;
							otrose=false;
						}
					}else if(titulob==true){
						error+="Falta diagonal en etiqueta de cierre de Titulo. Fila"+fila+"\n";
						Album.get(Album.size()-1).setTitulo("Desconocido");
						titulob=false;
					}
				}else if(albumb==false){
					error+="Debe abrir etiqueta Album, antes de etiqueta Titulo. Fila"+fila+"\n";
				}
				palabra=null;
				etiqueta=false;
				bandera =false;
				break;
			case 5://palabra es formato
				if (albumb==true){
					if (formatob==false){
						otrose=true;
						if(palabrae==true){//la palabra de cierre de album no venia dañada, 
							//la etiqueta de Formato de inicio viene dañada.
							error+="Etiqueta de apertura en Formato, palabra no permitida. Fila"+(fila-2)+"\n";
							formatob=false;
							palabrae=false;
							otrose=false;
						}
					}else if(formatob==true){
						error+="Falta diagonal en etiqueta de cierre de Formato. Fila"+fila+"\n";
						Album.get(Album.size()-1).setFormato("Desconocido");
						formatob=false;
					}
				}else if(albumb==false){
					error+="Debe abrir etiqueta Album, antes de etiqueta Formato. Fila"+fila+"\n";
				}
				palabra=null;
				etiqueta=false;
				bandera =false;
				break;
			case 6: //palabra es portada
				if (albumb==true){
					if (portadab==false){
						otrose=true;
						if(palabrae==true){//la palabra de cierre de album no venia dañada, 
							//la etiqueta de portada de inicio viene dañada.
							error+="Etiqueta de apertura en Portada, palabra no permitida. Fila"+(fila-2)+"\n";
							portadab=false;
							palabrae=false;
							otrose=false;
						}
					}else if(portadab==true){
						error+="Falta diagonal en etiqueta de cierre de Portadad. Fila"+fila+"\n";
						Album.get(Album.size()-1).setPortada("Desconocido.jpg");
						portadab=false;
					}
				}else if(albumb==false){
					error+="Debe abrir etiqueta Album, antes de etiqueta Portada. Fila"+fila+"\n";
				}
				palabra=null;
				etiqueta=false;
				bandera =false;;
			case 7: //palabra es canciones
				if (albumb==true){
					if (cancionesb==false){
						otrose=true;
						if(palabrae==true){//la palabra de cierre de album no venia dañada, 
							//la etiqueta de Canciones de inicio viene dañada.
							error+="Etiqueta de apertura en Canciones, palabra no permitida. Fila"+(fila-2)+"\n";
							cancionesb=false;
							palabrae=false;
							otrose=false;
						}
					}else if(cancionesb==true){
						error+="Falta diagonal en etiqueta de cierre de Canciones. Fila"+fila+"\n";
						Album.get(Album.size()-1).LimpiarCanciones();;
						cancionesb=false;
					}
				}else if(albumb==false){
					error+="Debe abrir etiqueta Album, antes de etiqueta Canciones. Fila"+fila+"\n";
				}
				palabra=null;
				etiqueta=false;
				bandera =false;
				break;
			case 8: //palabra es pistas
				if (cancionesb==true){
					if (pistasb==false){
						otrose=true;
						if(palabrae==true){//la palabra de cierre de album no venia dañada, 
							//la etiqueta de Pistas de inicio viene dañada.
							error+="Etiqueta de apertura en Pistas, palabra no permitida. Fila"+(fila-2)+"\n";
							pistasb=false;
							palabrae=false;
							otrose=false;
						}
					}else if(pistasb==true){
						error+="Falta diagonal en etiqueta de cierre de Pistas. Fila"+fila+"\n";
						Album.get(Album.size()-1).EliminarCanciones();;
						pistasb=false;
					}
				}else if(cancionesb==false){
					if(palabrae==true){//la palabra de cierre de album no venia dañada, 
						//la etiqueta de canciones de inicio viene dañada.
						error+="Etiqueta de apertura en canciones, palabra no permitida. Fila"+(fila-2)+"\n";
						error+="Falta diagonal en etiqueta de cierre de Pistas. Fila"+fila+"\n";
						cancionesb=false;
						palabrae=false;
					}
					error+="Debe abrir etiqueta Canciones, antes de etiqueta Pistas. Fila"+fila+"\n";
				}
				palabra=null;
				etiqueta=false;
				bandera =false;
				break;
			case 9: //palabra no esta permitida
				//verificar si no es etiqueta de cierre mal escrita y sin diagonal.
				if(palabrae==true){//la palabra de cierre de album no venia dañada, 
					//la etiqueta de apertura viene dañada y la de cierre mal escrita.
					error+="Etiqueta de apertura, palabra no permitida. Fila"+(fila-2)+"\n";
					error+="Etiqueta de cierre mal escrita. Fila"+fila+"\n";
					palabrae=false;
				}else if (xmlb==true){
					if (albumb==true){
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
						}else{//todas estan cerradas, error en cierre de album
							error+="Palabra equivocada en etiqueta de cierre Album. Fila"+fila+"\n";
							Album.remove(Album.size()-1);
							albumb=false;
						}
						
					}else if(albumb==false){//no esta abierto album, error en cierre xml.
						if(albume==false){
							error+="Palabra equivocada en etiqueta de cierre XML. Fila"+fila+"\n";
						Album.clear();
						xmlb=false;
						}else if(albume==true){//si hay error en album, esta mal escrito el cierre tambien.
							albume=false;
						}
					}
				}else if (xmlb==false){//no se ha abierto el documento, etiqueta inicial mal escrita. O hay texto despues de cerrar.
					error+="Se debe abrir con etiqueta de apertura XML. Fila"+fila+"\n";
					xmle=true;
				}
				otrose=true;
				palabra=null;
				etiqueta=false;
				bandera =false;
				break;
			}
//CASO4		
		//termina de leer la segunda etiqueta, con errores.	
		}else if ((etiqueta==true)&&(bandera==true)&&(banderadiag==true)){
			switch(to.tipoToken(palabra)){
			case 1: //palabra es xml
				if(xmlb==true){//la segunda etiqueta tiene error
					Album.clear();
					xmlb=false;
					if(palabrae==true){//la palabra de cierre de album si venia dañada...
						error+="Palabra equivocada en etiqueta de cierre Album. Fila"+(fila-2)+"\n";
						Album.remove(Album.size()-1);
						albumb=false;
						palabrae=false;
				 }
				}else if(xmlb==false){//es la primera con error
					xmle=true;
					error+="Diagonal en etiqueta de apertura XML. Fila"+fila+"\n";
				}
				palabra=null;
				bandera=false;
				banderadiag=false;
				etiqueta=false;
				break;
			case 2: //palabra es album
				if(albumb==true){//la segunda etiqueta tiene error
					Album.remove(Album.size()-1);
					albumb=false;
					if(palabrae==true){//la palabra de cierre de album no venia dañada, hay una etiqueta de más.
						error+="Etiqueta de más. Fila "+(fila-2)+"\n";
						palabrae=false;
					}
				}else if(albumb==false){//es la primera con error
					if(albume==true){
						albume=false;
					}else if(albume=false){
						albume=true;
						error+="Diagonal en etiqueta de apertura Album. Fila"+fila+"\n";
					}
				}
				palabra=null;
				bandera=false;
				banderadiag=false;
				etiqueta=false;
				break;
			case 3: //palabra es autor
				if(autorb==true){//la segunda etiqueta tiene error
					Album.get(Album.size()-1).setAutor("Desconocido");
					autorb=false;
				}else if(autorb==false){//es la primera con error
					otrose=true;
					if(palabrae==true){//la palabra de cierre de album no venia dañada, 
						//la etiqueta de Autor de inicio viene dañada.
						error+="Etiqueta de apertura en Autor, palabra no permitida. Fila"+(fila-2)+"\n";
						autorb=false;
						palabrae=false;
						otrose=false;
					}
					error+="Diagonal en etiqueta de apertura Autor. Fila"+fila+"\n";
				}
				palabra=null;
				bandera=false;
				banderadiag=false;
				etiqueta=false;
				break;
			case 4: //palabra es titulo
				if(titulob==true){//la segunda etiqueta tiene error
					Album.get(Album.size()-1).setTitulo("Desconocido");
					titulob=false;
				}else if(titulob==false){//es la primera con error
					otrose=true;
					if(palabrae==true){//la palabra de cierre de album no venia dañada, 
						//la etiqueta de Titulo de inicio viene dañada.
						error+="Etiqueta de apertura en Titulo, palabra no permitida. Fila"+(fila-2)+"\n";
						titulob=false;
						palabrae=false;
						otrose=false;
					}
					error+="Diagonal en etiqueta de apertura Titulo. Fila"+fila+"\n";
				}
				palabra=null;
				bandera=false;
				banderadiag=false;
				etiqueta=false;
				break;
			case 5://palabra es formato
				if(formatob==true){//la segunda etiqueta tiene error
					Album.get(Album.size()-1).setFormato("Desconocido");
					formatob=false;
				}else if(formatob==false){//es la primera con error
					otrose=true;
					if(palabrae==true){//la palabra de cierre de album no venia dañada, 
						//la etiqueta de Formato de inicio viene dañada.
						error+="Etiqueta de apertura en Formato, palabra no permitida. Fila"+(fila-2)+"\n";
						formatob=false;
						palabrae=false;
						otrose=false;
					}
					error+="Diagonal en etiqueta de apertura Formato. Fila"+fila+"\n";
				}
				palabra=null;
				bandera=false;
				banderadiag=false;
				etiqueta=false;
				break;
			case 6: //palabra es portada
				if(portadab==true){//la segunda etiqueta tiene error
					Album.get(Album.size()-1).setPortada("Desconocido");
					portadab=false;
				}else if(portadab==false){//es la primera con error
					otrose=true;
					if(palabrae==true){//la palabra de cierre de album no venia dañada, 
						//la etiqueta de portada de inicio viene dañada.
						error+="Etiqueta de apertura en portada, palabra no permitida. Fila"+(fila-2)+"\n";
						portadab=false;
						palabrae=false;
						otrose=false;
					}
					error+="Diagonal en etiqueta de apertura Portada. Fila"+fila+"\n";
				}
				palabra=null;
				bandera=false;
				banderadiag=false;
				etiqueta=false;
				break;
			case 7: //palabra es canciones
				if(cancionesb==true){//la segunda etiqueta tiene error
					Album.get(Album.size()-1).LimpiarCanciones();
					cancionesb=false;
				}else if(cancionesb==false){//es la primera con error
					otrose=true;
					if(palabrae==true){//la palabra de cierre de album no venia dañada, 
						//la etiqueta de canciones de inicio viene dañada.
						error+="Etiqueta de apertura en Canciones, palabra no permitida. Fila"+(fila-2)+"\n";
						cancionesb=false;
						palabrae=false;
						otrose=false;
					}
					error+="Diagonal en etiqueta de apertura Canciones. Fila"+fila+"\n";
				}
				palabra=null;
				bandera=false;
				banderadiag=false;
				etiqueta=false;
				break;
			case 8: //palabra es pistas
				if(cancionesb==false){
					if(palabrae==true){//la palabra de cierre de album no venia dañada, 
						//la etiqueta de canciones de inicio viene dañada.
						error+="Etiqueta de apertura en canciones, palabra no permitida. Fila"+(fila-2)+"\n";
						error+="Falta diagonal en etiqueta de cierre de Pistas. Fila"+fila+"\n";
						cancionesb=false;
						palabrae=false;
					}
					error+="Debe abrir etiqueta Canciones, antes de etiqueta Pistas. Fila"+fila+"\n";
				}else if(cancionesb==true){
					if(pistasb==true){//la segunda etiqueta tiene error
					Album.get(Album.size()-1).EliminarCanciones();
					pistasb=false;
				}else if(pistasb==false){//es la primera con error
					otrose=true;
					if(palabrae==true){//la palabra de cierre de album no venia dañada, 
						//la etiqueta de pistas de inicio viene dañada.
						error+="Etiqueta de apertura en Pistas, palabra no permitida. Fila"+(fila-2)+"\n";
						pistasb=false;
						palabrae=false;
						otrose=false;
					}
					error+="Diagonal en etiqueta de apertura Pistas. Fila"+fila+"\n";
				}
				}
				
				palabra=null;
				bandera=false;
				banderadiag=false;
				etiqueta=false;
				break;
			case 9: //palabra no esta permitida
				//verificar si es el cierre mal escrito y sin diagonal.
				if(palabrae==true){//la palabra de cierre de album no venia dañada, 
					//la etiqueta de apertura viene dañada y la de cierre mal escrita.
					error+="Etiqueta de apertura, palabra no permitida. Fila"+(fila-2)+"\n";
					error+="Etiqueta de cierre mal escrita. Fila"+fila+"\n";
					palabrae=false;
				}else if (xmlb==true){
					if (albumb==true){
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
						}else{//todas estan cerradas, error en cierre de album
							error+="Palabra equivocada en etiqueta de cierre Album. Fila"+fila+"\n";
							Album.remove(Album.size()-1);
							albumb=false;
						}
						
					}else if(albumb==false){//no esta abierto album, error en cierre xml.
						if(albume==false){
							error+="Palabra equivocada en etiqueta de cierre XML. Fila"+fila+"\n";
						Album.clear();
						xmlb=false;
						}else if(albume==true){//si hay error en album, esta mal escrito el cierre tambien.
							albume=false;
						}
					}
				}else if (xmlb==false){//no se ha abierto el documento, etiqueta inicial mal escrita. O hay texto despues de cerrar.
					error+="Se debe abrir con etiqueta de apertura XML. Fila"+fila+"\n";
					xmle=true;
				}
				
				palabra=null;
				etiqueta=false;
				break;
			}
//CASO5		
		//el simbolo viene en el texto	
		}else if((etiqueta==false)){
			if (palabra==null){//empieza la palabra
				palabra=""+caracter;
			}else{//solo concatena.
			palabra+=caracter;
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
	case 5: //diagonal
		if ((etiqueta==false)){//no es palabra de una etiqueta
			if (palabra==null){
				palabra=""+caracter;
			}else{
			palabra+=caracter;
			}
		}else if (etiqueta==true){//palabra de una etiqueta
			System.out.println("banderadiag true");
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

public String getError() {
	return error;
}

public void setError(String error) {
	this.error = error;
}

}
