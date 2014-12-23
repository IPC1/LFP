package Principal;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class Frame extends JFrame implements ActionListener{

	private PanelImagen contentPane;
	ArrayList <String> Autor = new ArrayList <String>();
	ArrayList <String> Titulo = new ArrayList <String>();
	JFileChooser fileC= new JFileChooser();
	
	JMenuItem mntmAbrir = new JMenuItem("Abrir");
	JComboBox<String> comboBox_1 = new JComboBox<String>();
	JComboBox<String> comboBox = new JComboBox<String>();
	JButton btnBuscar = new JButton("Buscar");
	JTextArea textPane = new JTextArea();
	JScrollPane scroll = new JScrollPane (textPane);
	JLabel lblAutorOAlbum = new JLabel("AUTOR O ALBUM");
	JLabel imagen = new JLabel();
	
	public Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 675, 440);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(224, 255, 255));
		menuBar.setForeground(new Color(153, 255, 204));
		setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		mnArchivo.setForeground(new Color(139, 0, 0));
		mnArchivo.setFont(new Font("SimHei", Font.BOLD, 16));
		menuBar.add(mnArchivo);
		
		mntmAbrir.setForeground(new Color(128, 0, 0));
		mntmAbrir.setFont(new Font("Tahoma", Font.PLAIN, 14));
		mntmAbrir.addActionListener(this);
		mnArchivo.add(mntmAbrir);
		
		contentPane = new PanelImagen("/Imagenes/Recepcion.jpg");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		comboBox.setBounds(36, 59, 226, 20);
		comboBox.setBackground(new Color(224, 255, 255));
		comboBox.setForeground(new Color(47, 79, 79));
		comboBox.setFont(new Font("SimHei", Font.BOLD, 20));
		contentPane.add(comboBox);
		
		
		comboBox_1.setBackground(new Color(224, 255, 255));
		comboBox_1.setForeground(new Color(47, 79, 79));
		comboBox_1.setFont(new Font("SimHei", Font.BOLD, 20));
		comboBox_1.setBounds(299, 59, 219, 20);
		contentPane.add(comboBox_1);
		
		
		btnBuscar.addActionListener(this);
		btnBuscar.setForeground(new Color(139, 0, 0));
		btnBuscar.setFont(new Font("SimHei", Font.BOLD, 20));
		btnBuscar.setBounds(516, 101, 111, 37);
		contentPane.add(btnBuscar);
		
		imagen.setHorizontalAlignment(SwingConstants.CENTER);
		imagen.setIcon(new ImageIcon(getClass().getResource("/imagenes/Recepcion.jpg")));
		contentPane.add(imagen);
		textPane.setFont(new Font("Tekton Pro", Font.PLAIN, 18));
		textPane.setEditable(false);
		textPane.setLineWrap(true);
		textPane.setBounds(5,5, 567, 300);
		scroll.setBounds(36, 146, 592, 205);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scroll);
		
		JLabel lblAlbums = new JLabel("ÁLBUMES");
		lblAlbums.setForeground(Color.WHITE);
		lblAlbums.setFont(new Font("Tekton Pro", Font.PLAIN, 18));
		lblAlbums.setBounds(36, 28, 94, 20);
		contentPane.add(lblAlbums);
		
		JLabel lblAutores = new JLabel("AUTORES");
		lblAutores.setForeground(Color.WHITE);
		lblAutores.setFont(new Font("Tekton Pro", Font.PLAIN, 18));
		lblAutores.setBounds(298, 25, 111, 26);
		contentPane.add(lblAutores);
		lblAutorOAlbum.setHorizontalAlignment(SwingConstants.CENTER);
		lblAutorOAlbum.setForeground(new Color(153, 255, 204));
		lblAutorOAlbum.setFont(new Font("Trajan Pro", Font.BOLD, 26));
		lblAutorOAlbum.setBounds(36, 101, 470, 37);
		
		contentPane.add(lblAutorOAlbum);
		
	}

	public void LlenarAutores(){
		String autor="";
		Autor.add("Vacio");
		for (int i=0; i<AnalizadorLexico.Album.size();i++){
			autor= (AnalizadorLexico.Album.get(i).getAutor());
			if(!(Autor.contains(autor))){
			Autor.add(autor);
			}
		}
		
	}
	
	public void LlenarTitulo(){
		String autor="";
		Titulo.add("Vacio");
		for (int i=0; i<AnalizadorLexico.Album.size();i++){
			autor= (AnalizadorLexico.Album.get(i).getTitulo());
			if(!(Titulo.contains(autor))){
			Titulo.add(autor);
			}
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		 if (e.getSource()== mntmAbrir){
			 if (Titulo.size()>0){//limpia el array de albums
				 AnalizadorLexico.Album.clear();
				 comboBox.removeAllItems();
				 comboBox_1.removeAllItems();
				 Titulo.clear();
				 Autor.clear();
			 }
			 
			 int nose =fileC.showOpenDialog(mntmAbrir);
			 if(nose == fileC.APPROVE_OPTION){
				 String direccion= fileC.getSelectedFile().getAbsolutePath();
				 LecturaArchivo l = new LecturaArchivo ();
				 l.abrirArchivo(direccion);
				 l.leerLinea();
				 l.cerrarArchivo();
			 }else if(nose== fileC.CANCEL_OPTION){
				 JOptionPane.showMessageDialog(null, "Ha decidido cancelar la operacion");
			 }else if (nose== fileC.ERROR_OPTION){
				 JOptionPane.showMessageDialog(null, "Hubo un error al abrir archivo");
			 }
			 LlenarAutores();
			 LlenarTitulo();
			 
			 String item="";
			 int i=0;
			 for(i=0; i<Autor.size();i++){
				 item = Autor.get(i);
				 comboBox_1.addItem(item);
			 }
			 for(i=0;i<Titulo.size();i++){
				 item=Titulo.get(i);
				 comboBox.addItem(item);
			 }
			 
		 }else if(e.getSource()==btnBuscar){
			 
			 String titulo, autor;
			 String texto="";
			 titulo= (String) comboBox.getSelectedItem();
			 autor= (String) comboBox_1.getSelectedItem();
			 
			 if((titulo.equalsIgnoreCase("vacio")&&autor.equalsIgnoreCase("vacio"))||
					 (!(titulo.equalsIgnoreCase("vacio"))&&!(autor.equalsIgnoreCase("vacio")))){
				 JOptionPane.showMessageDialog(null, "Debe seleccionar solamente un autor o un titulo.\nY el otro campo debe quedar en vacio");
			 }else if(titulo.equalsIgnoreCase("vacio")){
				 textPane.setText("");
				 texto="";
				 for(int i=0; i<AnalizadorLexico.Album.size();i++){
					 if (AnalizadorLexico.Album.get(i).getAutor().equalsIgnoreCase(autor)){
						 texto+="Album: "+AnalizadorLexico.Album.get(i).getTitulo()+"\n"+
								 "Formato: "+AnalizadorLexico.Album.get(i).getFormato()+"\n"+
								 "Ruta de Portada: C:\\Users\\Pau!\\Documents\\GitHub\\LFP\\Practica 2\\Practica2L\\src\\Imagenes\\"+
								 AnalizadorLexico.Album.get(i).getPortada()+"\n"+"\n";
					 }else{
						 texto=texto;
					 }
					 lblAutorOAlbum.setText(autor.toUpperCase());	 
					 textPane.setText(texto);
				 }
			 }else if(autor.equalsIgnoreCase("vacio")){
				 textPane.setText("");
				 texto="";
				 for(int i=0; i<AnalizadorLexico.Album.size();i++){
					 if(AnalizadorLexico.Album.get(i).getTitulo().equalsIgnoreCase(titulo)){
						 texto+="Autor: "+AnalizadorLexico.Album.get(i).getAutor()+"\n"+
								 "Formato: "+AnalizadorLexico.Album.get(i).getFormato()+"\n"+
								 "Ruta de Portada: C:\\Users\\Pau!\\Documents\\GitHub\\LFP\\Practica 2\\Practica2L\\src\\Imagenes\\"+
								 AnalizadorLexico.Album.get(i).getPortada()+"\n"+"\n";
						 
						 
					 }else{
						 texto=texto;
					 }
				 }
				 lblAutorOAlbum.setText(titulo.toUpperCase());
				 textPane.setText(texto);
			 }
			 
		 }
		
	}
}
