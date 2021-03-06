import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JList;

import java.awt.Color;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;



public class Principal extends JFrame implements ActionListener{

	private ArrayList <String> Autor = new ArrayList <String>();
	private ArrayList <String> Titulo = new ArrayList <String> ();
	private ArrayList <String> canciones = new ArrayList <String>();
	private ArrayList <Album> temp = new ArrayList <Album>();
	
	private PanelImagen contentPane;
	private JComboBox<String> cBAutor;
	private JComboBox<String> cBTitulo;
	private JButton btnBuscar;
	private JButton btnAnterior;
	private JButton btnSiguiente;
	
	private JLabel lblMusica;
	private JTextField txtFormato;
	private JList list;
	private DefaultListModel listModel = new DefaultListModel();
	private JTextField txtAutor;
	private JTextField txtTitulo;
	private JButton btnPause;
	private JButton btnPlay;
	private JLabel lblImagen;
	
	private JMenuItem mntmAbrir;
	private JMenu mnInformacion;
	private JFileChooser fileC= new JFileChooser();
	private JTextField txtAlbumes;
	
	String seleccion="";
	//Musica titoga = new Musica();   
	String url;
	File file;
	boolean rep= false;
	
	public Principal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 672, 568);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(102, 51, 0));
		setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		mnArchivo.setForeground(new Color(255, 255, 255));
		mnArchivo.setFont(new Font("Tekton Pro", Font.PLAIN, 20));
		menuBar.add(mnArchivo);
		
		mntmAbrir = new JMenuItem("Abrir");
		mntmAbrir.setForeground(new Color(102, 0, 0));
		mntmAbrir.setFont(new Font("Tekton Pro", Font.PLAIN, 20));
		mntmAbrir.addActionListener(this);
		mnArchivo.add(mntmAbrir);
		
		mnInformacion= new JMenu("Informacion");
		mnInformacion.setForeground(new Color(255, 255, 255));
		mnInformacion.setFont(new Font("Tekton Pro", Font.PLAIN, 20));
		menuBar.add(mnInformacion);
		
		JMenuItem mntmCreador = new JMenuItem("Creador");
		mntmCreador.setForeground(new Color(102, 0, 0));
		mntmCreador.setFont(new Font("Tekton Pro", Font.PLAIN, 20));
		mnInformacion.add(mntmCreador);
		contentPane = new PanelImagen("/Imagenes/fondo2.jpg");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		cBTitulo = new JComboBox<String>();
		cBTitulo.setForeground(new Color(102, 0, 0));
		cBTitulo.setFont(new Font("Tekton Pro", Font.PLAIN, 20));
		cBTitulo.setBounds(47, 96, 264, 22);
		contentPane.add(cBTitulo);
		
		cBAutor = new JComboBox<String>();
		cBAutor.setForeground(new Color(102, 0, 0));
		cBAutor.setFont(new Font("Tekton Pro", Font.PLAIN, 20));
		cBAutor.setBounds(351, 96, 264, 22);
		contentPane.add(cBAutor);
		
		JLabel lblLibreriaDeMusica = new JLabel("Libreria de Musica");
		lblLibreriaDeMusica.setForeground(new Color(153, 51, 51));
		lblLibreriaDeMusica.setHorizontalAlignment(SwingConstants.CENTER);
		lblLibreriaDeMusica.setFont(new Font("Tekton Pro", Font.BOLD, 40));
		lblLibreriaDeMusica.setBounds(47, 13, 568, 31);
		contentPane.add(lblLibreriaDeMusica);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(this);
		btnBuscar.setBackground(new Color(255, 255, 255));
		btnBuscar.setForeground(new Color(153, 0, 102));
		btnBuscar.setFont(new Font("Tekton Pro", Font.PLAIN, 20));
		btnBuscar.setBounds(290, 131, 97, 25);
		contentPane.add(btnBuscar);
		
		JLabel lblAlbum = new JLabel("Album:");
		lblAlbum.setFont(new Font("Tekton Pro", Font.PLAIN, 20));
		lblAlbum.setBounds(47, 69, 85, 25);
		contentPane.add(lblAlbum);
		
		JLabel lblAutor = new JLabel("Autor:");
		lblAutor.setFont(new Font("Tekton Pro", Font.PLAIN, 20));
		lblAutor.setBounds(350, 69, 71, 25);
		contentPane.add(lblAutor);
		
		lblImagen= new JLabel ();
		lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
		lblImagen.setBounds(438, 212, 150, 123);
		contentPane.add(lblImagen);
		
		JLabel lblAutor_1 = new JLabel("Autor");
		lblAutor_1.setFont(new Font("Tekton Pro", Font.PLAIN, 20));
		lblAutor_1.setBounds(76, 241, 56, 16);
		contentPane.add(lblAutor_1);
		
		txtAutor = new JTextField();
		txtAutor.setEditable(false);
		txtAutor.setForeground(new Color(102, 0, 0));
		txtAutor.setFont(new Font("Tekton Pro Cond", Font.PLAIN, 20));
		txtAutor.setText("Autor Desconocido");
		txtAutor.setBounds(134, 238, 287, 22);
		contentPane.add(txtAutor);
		txtAutor.setColumns(10);

		JLabel lblTitulo = new JLabel("Titulo");
		lblTitulo.setFont(new Font("Tekton Pro", Font.PLAIN, 20));
		lblTitulo.setBounds(76, 212, 56, 16);
		contentPane.add(lblTitulo);
		
		txtTitulo = new JTextField();
		txtTitulo.setEditable(false);
		txtTitulo.setForeground(new Color(102, 0, 0));
		txtTitulo.setFont(new Font("Tekton Pro Cond", Font.PLAIN, 20));
		txtTitulo.setText("Titulo Desconocido");
		txtTitulo.setBounds(132, 209, 287, 22);
		contentPane.add(txtTitulo);
		txtTitulo.setColumns(10);

		JLabel lblFormato= new JLabel("Formato");
		lblFormato.setFont(new Font("Tekton Pro", Font.PLAIN, 20));
		lblFormato.setBounds(76, 276, 77, 16);
		contentPane.add(lblFormato);
		
		txtFormato = new JTextField();
		txtFormato.setEditable(false);
		txtFormato.setForeground(new Color(102, 0, 0));
		txtFormato.setFont(new Font("Tekton Pro Cond", Font.PLAIN, 20));
		txtFormato.setText("Formato Des.");
		txtFormato.setBounds(174, 273, 97, 22);
		contentPane.add(txtFormato);
		txtFormato.setColumns(10);

		JLabel lblCanciones= new JLabel("Canciones");
		lblCanciones.setFont(new Font("Tekton Pro", Font.PLAIN, 20));
		lblCanciones.setBounds(76, 306, 97, 16);
		contentPane.add(lblCanciones);
		list = new JList(listModel);
		list.setForeground(new Color(102, 0, 0));
		list.setFont(new Font("Tekton Pro Cond", Font.PLAIN, 20));
		list.setBounds(176, 305, 245, 76);
		//contentPane.add(list);
		
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(176, 305, 245, 76);
		contentPane.add(scrollPane);
		//scrollPane.getViewport().setView(list);
		
		JButton btnSiguiente = new JButton("");
		btnSiguiente.addActionListener(this);
		btnSiguiente.setBackground(new Color(255, 255, 255));
		btnSiguiente.setBounds(350, 425, 62, 59);
		btnSiguiente.setIcon(new ImageIcon(getClass().getResource("/Imagenes/siguiente.png")));
		contentPane.add(btnSiguiente);
		
		JButton btnAnterior = new JButton("");
		btnAnterior.addActionListener(this);
		btnAnterior.setForeground(Color.WHITE);
		btnAnterior.setBackground(new Color(255, 255, 255));
		btnAnterior.setIcon(new ImageIcon(getClass().getResource("/Imagenes/anterior.png")));
		btnAnterior.setBounds(249, 425, 62, 59);
		contentPane.add(btnAnterior);
		
		JButton btnPlay = new JButton("");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rep==false&&file==null){
					if(rep==false){
						seleccion=(String) listModel.getElementAt(list.getSelectedIndex());
				System.out.println(seleccion);
				url = getClass().getResource("/Imagenes/"+seleccion).toString();
				System.out.println("esta es la url: "+url);
				System.out.print(url);
				try{
					//titoga.AbrirFichero("C:\\Users\\Pau!\\Documents\\GitHub\\LFP\\Practica 3\\Practica3L\\src\\Imagenes\\"+seleccion);
					System.out.println("Encontre el archivo");
				}catch(Exception e2){
					
				}
				try {
					//titoga.Play();
					System.out.println("Estoy reproduciendo");
					rep=true;
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				rep=true;
					}else{
						try {
							//titoga.Continuar();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
				}
				
			}
		});
		btnPlay.setForeground(new Color(189, 183, 107));
		btnPlay.setBackground(new Color(255, 255, 255));
		btnPlay.setIcon(new ImageIcon(getClass().getResource("/Imagenes/play.png")));
		btnPlay.setBounds(488, 348, 36, 31);
		contentPane.add(btnPlay);
		
		JButton btnStop = new JButton("");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//titoga.Stop();
					rep=false;
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnStop.setBounds(528, 348, 36, 33);
		contentPane.add(btnStop);
		btnStop.setIcon(new ImageIcon(getClass().getResource("/Imagenes/stop.png")));
		
		JButton btnPause = new JButton("");
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//titoga.Pausa();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnPause.setForeground(new Color(189, 183, 107));
		btnPause.setBackground(new Color(255, 255, 255));
		btnPause.setIcon(new ImageIcon(getClass().getResource("/Imagenes/pause.png")));
		btnPause.setBounds(448, 348, 36, 31);
		contentPane.add(btnPause);
		
		lblMusica = new JLabel("");
		lblMusica.setBounds(47, 189, 567, 223);
		lblMusica.setIcon(new ImageIcon(Principal.class.getResource("/Imagenes/musica5.jpg")));
		contentPane.add(lblMusica);
		
		JLabel lblAlbumes = new JLabel("Albumes");
		lblAlbumes.setFont(new Font("Tekton Pro", Font.PLAIN, 18));
		lblAlbumes.setBounds(47, 160, 71, 16);
		contentPane.add(lblAlbumes);
		
		txtAlbumes = new JTextField();
		txtAlbumes.setEditable(false);
		txtAlbumes.setForeground(new Color(153, 0, 102));
		txtAlbumes.setFont(new Font("Tekton Pro", Font.PLAIN, 16));
		txtAlbumes.setBounds(116, 158, 32, 22);
		contentPane.add(txtAlbumes);
		txtAlbumes.setColumns(10);
	}

	public void LlenarAutores(){
		String autor="";
		Autor.add("Vacio");
		for (int i=0; i<Lexico.Album.size();i++){
			autor= (Lexico.Album.get(i).getAutor());
			if(!(Autor.contains(autor))){
			Autor.add(autor);
			}
		}
		
	}
	
	public void LlenarTitulo(){
		String autor="";
		Titulo.add("Vacio");
		for (int i=0; i<Lexico.Album.size();i++){
			autor= (Lexico.Album.get(i).getTitulo());
			if(!(Titulo.contains(autor))){
			Titulo.add(autor);
			}
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int i=0;
		String titulo, autor;
		 if (e.getSource()== mntmAbrir){
			 if (Titulo.size()>0){//limpia el array de albums
				 Lexico.Album.clear();
				 cBAutor.removeAllItems();
			     cBTitulo.removeAllItems();
				 Titulo.clear();
				 Autor.clear();
				 
			 }
			 
			 int nose =fileC.showOpenDialog(mntmAbrir);
			
			 if(nose == fileC.APPROVE_OPTION){
				 String direccion= fileC.getSelectedFile().getAbsolutePath();
				 Lectura l = new Lectura ();
				 l.abrirArchivo(direccion);
				 l.leer();
				 l.cerrarArchivo();
					
			 }else if(nose== fileC.CANCEL_OPTION){
				 JOptionPane.showMessageDialog(null, "Ha decidido cancelar la operacion");
			 }else if (nose== fileC.ERROR_OPTION){
				 JOptionPane.showMessageDialog(null, "Hubo un error al abrir archivo");
			 }
			 LlenarAutores();
			 LlenarTitulo();
			 String item="";
			 
			 for(i=0; i<Autor.size();i++){
				 item = Autor.get(i);
				 cBAutor.addItem(item);
			 }
			 for(i=0;i<Titulo.size();i++){
				 item=Titulo.get(i);
				 cBTitulo.addItem(item);
			 }
		 }else if(e.getSource()==btnBuscar){
			
			 String texto="";
			 Album tem;
			 
			 titulo= (String) cBTitulo.getSelectedItem();
			 autor= (String) cBAutor.getSelectedItem();
			 if((titulo.equalsIgnoreCase("vacio")&&autor.equalsIgnoreCase("vacio"))||
					 (!(titulo.equalsIgnoreCase("vacio"))&&!(autor.equalsIgnoreCase("vacio")))){
				 JOptionPane.showMessageDialog(null, "Debe seleccionar solamente un autor o un titulo.\nY el otro campo debe quedar en vacio");
			 }else if(titulo.equalsIgnoreCase("vacio")){
				 temp.clear();
				for(i=0; i<Lexico.Album.size();i++){
					if (Lexico.Album.get(i).getAutor().equalsIgnoreCase(autor)){
						System.out.println(Lexico.Album.size());
						tem=Lexico.Album.get(i);
						temp.add(tem);
						System.out.println("Agregue album "+i);
						System.out.println(Lexico.Album.get(i).ObtenerCanciones().size());
					}
				}
				txtAlbumes.setText(""+temp.size());
				txtTitulo.setText(temp.get(0).getTitulo());
				txtAutor.setText(temp.get(0).getAutor());
				txtFormato.setText(temp.get(0).getFormato());
				lblImagen.setIcon(new ImageIcon(Principal.class.getResource("/Imagenes/"+temp.get(0).getPortada())));
				 System.out.println(canciones.size());
				 System.out.println(temp.get(0).ObtenerCanciones()+"tama�o de temp");
				canciones.clear();
				 System.out.println(canciones.size());
				 ArrayList  <String> tempo= new ArrayList <String>();
				tempo = temp.get(0).ObtenerCanciones();
				System.out.println(tempo.size()+"Tama�o de tempo");
				canciones=tempo;
				 System.out.println(canciones.size()+"tama�o de canciones");
				 System.out.println(temp.get(0).ObtenerCanciones()+"tama�o de temp");
				 listModel.removeAllElements();
				for(i=0; i<canciones.size();i++){
					listModel.addElement(canciones.get(i));
					 System.out.println("Agregue canciones "+i);
				}
				
			 }else if(autor.equalsIgnoreCase("vacio")){
				 temp.clear();
				 for(i=0;i<Lexico.Album.size();i++){
					 if(Lexico.Album.get(i).getTitulo().equalsIgnoreCase(titulo)){
						 tem=Lexico.Album.get(i);
						 temp.add(tem);
						 System.out.println("Agregue album "+i);
						 System.out.println(Lexico.Album.get(i).ObtenerCanciones().size());
					 }
				 }
				 txtAlbumes.setText(""+temp.size());
				 txtTitulo.setText(temp.get(0).getTitulo());
				 txtAutor.setText(temp.get(0).getAutor());
				 txtFormato.setText(temp.get(0).getFormato());
				 lblImagen.setIcon(new ImageIcon(Principal.class.getResource("/Imagenes/"+temp.get(0).getPortada())));
				 System.out.println(canciones.size());
				 canciones.clear();
				 System.out.println(canciones.size());
				 canciones = temp.get(0).ObtenerCanciones();
				 System.out.println(canciones.size());
				 listModel.removeAllElements();
				 for(i=0; i<canciones.size();i++){
					 listModel.addElement(canciones.get(i));
					 System.out.println("Agregue cancion "+i);
				 }
			 }
			 
			 
		 }else if(e.getSource()==btnSiguiente){
			 canciones.clear();
			 listModel.removeAllElements();
			 titulo= txtTitulo.getText();
			 autor=txtAutor.getText();
			 int cont=0;
			 for(i=0;i<temp.size();i++){
				 if(temp.get(i).getAutor().equalsIgnoreCase(autor)&&
						 temp.get(i).getTitulo().equalsIgnoreCase(titulo)){
					 break;
				 }else{
					 cont++;
				 }
			 }
			 if(cont<temp.size()-1){
				 txtAlbumes.setText(""+temp.size());
			 txtTitulo.setText(temp.get(cont+1).getTitulo());
			 txtAutor.setText(temp.get(cont+1).getAutor());
			 txtFormato.setText(temp.get(cont+1).getFormato());
			 lblImagen.setIcon(new ImageIcon(Principal.class.getResource("/Imagenes/"+temp.get(cont+1).getPortada())));
			 canciones = temp.get(cont+1).ObtenerCanciones();
			 System.out.println("Obtuve canciones");
			 listModel.removeAllElements();
			 for(i=0; i<canciones.size();i++){
				 listModel.addElement(canciones.get(i));
			 }
			 }else if(cont==temp.size()-1){
				 txtAlbumes.setText(""+temp.size());
				 txtTitulo.setText(temp.get(cont).getTitulo());
				 txtAutor.setText(temp.get(cont).getAutor());
				 txtFormato.setText(temp.get(cont+1).getFormato());
				 lblImagen.setIcon(new ImageIcon(Principal.class.getResource("/Imagenes/"+temp.get(cont).getPortada())));
				 canciones = temp.get(cont).ObtenerCanciones();
				 System.out.println("Obtuve canciones");
				 listModel.removeAllElements();
				 for(i=0; i<canciones.size();i++){
					 listModel.addElement(canciones.get(i));
				 }
			 }
			 
			 
		 }else if(e.getSource()==btnAnterior){
			 canciones.clear();
			 
			 titulo= txtTitulo.getText();
			 autor=txtAutor.getText();
			 int cont=0;
			 for(i=0;i<temp.size();i++){
				 if(temp.get(i).getAutor().equalsIgnoreCase(autor)&&
						 temp.get(i).getTitulo().equalsIgnoreCase(titulo)){
					 break;
				 }else{
					 cont++;
				 }
			 }
			 if(cont>0){
				  txtAlbumes.setText(""+temp.size());
			 txtTitulo.setText(temp.get(cont-1).getTitulo());
			 txtAutor.setText(temp.get(cont-1).getAutor());
			 txtFormato.setText(temp.get(cont-1).getFormato());
			 lblImagen.setIcon(new ImageIcon(Principal.class.getResource("/Imagenes/"+temp.get(cont-1).getPortada())));
			 canciones = temp.get(cont-1).ObtenerCanciones();
			 listModel.removeAllElements();
			 for(i=0; i<canciones.size();i++){
				 listModel.addElement(canciones.get(i));
			 }
			 }else if(cont==0){
				  txtAlbumes.setText(""+temp.size());
					 txtTitulo.setText(temp.get(temp.size()-1).getTitulo());
					 txtAutor.setText(temp.get(temp.size()-1).getAutor());
					 txtFormato.setText(temp.get(temp.size()-1).getFormato());
					 lblImagen.setIcon(new ImageIcon(Principal.class.getResource("/Imagenes/"+temp.get(temp.size()-1).getPortada())));
					 canciones = temp.get(temp.size()-1).ObtenerCanciones();
					 listModel.removeAllElements();
					 for(i=0; i<canciones.size();i++){
						 listModel.addElement(canciones.get(i));
					 }
			 }
			
		 }
	}
}
