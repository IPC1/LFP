
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Principal p = new Principal ();
		//p.setVisible(true);
		Lectura l = new Lectura();
		l.abrirArchivo("C:\\Users\\usuario\\USAC\\Lenguajes Formales de Programacion\\entrada3.txt");
		l.leer();
		l.cerrarArchivo();
	}

}
