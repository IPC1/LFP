package Principal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

public class LecturaArchivo {
	File archivo= null;
	FileReader fr=null;
	BufferedReader br=null;
	
   
	public void abrirArchivo (String direccion){
		try{
			archivo =new File(direccion);
			fr=new FileReader(archivo);
			br= new BufferedReader (fr);
		}catch (Exception e){
			JOptionPane.showMessageDialog( null, "No se pudo abrir el archivo");
		}
	}
	
	public void leerLinea (){
		try{
			String linea="";
			int fila=0;
			int columna=0;
			AnalizadorLexico AL= new AnalizadorLexico();
			while((linea=br.readLine())!=null){
				fila++;
				columna=0;
				for (int i=0; i<linea.length();i++){
					columna++;
					char c= linea.charAt(i);
					AL.analizador(AL.tipoToken(c), c, fila, columna);
				}	
			}
			if(AL.bandera==true){
				AL.Album.clear();
				AL.error+=" Error en la etiqueta de salida xml";
			}	
			System.out.println(AL.error);
			if((AL.error)!=""){
				JOptionPane.showMessageDialog(null, AL.error);
			}
			
		}catch (Exception e){
			JOptionPane.showMessageDialog(null, "Hay error el la lectura del archivo");
		}
		
	}
	
	public void cerrarArchivo (){
		try{
		br.close();
		fr.close();
		}catch (IOException e){
			JOptionPane.showMessageDialog(null, "No se puede cerrar el archivo");
		}
		
	}
}
