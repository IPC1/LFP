import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

public class Lectura {
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
	
	public void leer(){
		Lexico AL= new Lexico();
		Tokens t= new Tokens();
		try{
			String linea="";
			int fila=0;
			int columna=0;
			AL.setError("");
			
			while((linea=br.readLine())!=null){
			
				fila++;
				columna=0;
				for (int i=0; i<linea.length();i++){
					columna++;
					char c= linea.charAt(i);
					AL.analizador(t.tipoToken(c), c, fila, columna);
				}	
			}
			
		}catch (Exception e){
			JOptionPane.showMessageDialog(null, "Hay error el la lectura del archivo");
		}
		System.out.println(AL.getError());
		if((AL.getError())!=""){
			JOptionPane.showMessageDialog(null, AL.getError());
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