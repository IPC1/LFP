import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class Lectura {

	File archivo =null;
	FileReader fr=null;
	BufferedReader br=null;
	static ArrayList <String> texto = new ArrayList <String>();
	
	public Lectura(String direccion){
		/*if(texto.get(0)!=null){
		 for(int i=0;i<texto.size();i++){
			 texto.remove(i);
		 }
		}*/
		try{
			archivo =new File(direccion);
			fr= new FileReader(archivo);
			br= new BufferedReader(fr);
		}catch(Exception e){
		System.out.println("No se pudo abrir el archivo");
		}
	}
	public void leerLinea(){
		
		try{
			String linea=" ";
			 while ((linea=br.readLine())!=null){
				 String temp = null;

				 for (int i=0; i<linea.length(); i++){
					 char c = linea.charAt(i); //separar linea en caracteres;
					 if (c=='<'){//si encuentra este simbolo y es nulo:
						
						 if (temp==null){ // temp se queda igual porque no ha leido despues de la primer etiqueta
							 temp="";
						 }else{
							
						texto.add(temp); // si no, quiere decir que ya leyo el texto despues de la primer etiqueta.
						 temp=null;
						 }
						 
						 
					 }else if(c=='>'){//si encuentra este simbolo:
						 if (i== linea.length()-1){ //si i es la ultimo simbolo va leyendo la ultima etiqueta.
							 temp=null; //no es necesario guardarla.
						 }else{
						 texto.add(temp); //si no, esta terminando de leerla primer etiqueta. Hay que guardarla.
						 temp=null;
						 }
					
					 }else{//si no es ninguno de los 2 simbolos:
						 if (linea.charAt(i-1)=='>'){ // si el simbolo anterior es ese: esta empezando el texto.
							 temp=""+c; //hay que empezar a guardar el texto.
						 }else{
						temp+=c;// si no, ya esta leyendo el texto, hay que concatenar.
						 }
					 }
				 

				 }
			}
			 
			 for (int i=0; i<texto.size();i++){
				 System.out.println(texto.get(i));
			 }	 
		}catch(Exception e){
			System.out.println("Hay error en la lectura del archivo");
		}
	}
}
