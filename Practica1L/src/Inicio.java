import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.JButton;


public class Inicio extends JFrame implements ActionListener {

	private PanelImagen contentPane;
	JMenuItem mntmAbrir = new JMenuItem("Abrir");
	JMenuItem mntmDatosDeAutor = new JMenuItem("Datos de Autor");
	JFileChooser fileC= new JFileChooser();
	Lectura lectura;
	private JTextField txtHola;
	JComboBox comboBox = new JComboBox();
	
	public Inicio() {
		setForeground(new Color(255, 165, 0));
		setBackground(new Color(0, 128, 0));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 548, 351);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		mnArchivo.setForeground(new Color(139, 0, 0));
		mnArchivo.setFont(new Font("SimHei", Font.BOLD, 16));
		menuBar.add(mnArchivo);
		
		
		mntmAbrir.setForeground(new Color(128, 0, 0));
		mntmAbrir.setFont(new Font("Tahoma", Font.PLAIN, 14));
		mntmAbrir.addActionListener(this);
		mnArchivo.add(mntmAbrir);
				
		JMenu mnProyecto = new JMenu("Proyecto");
		mnProyecto.setForeground(new Color(139, 0, 0));
		mnProyecto.setFont(new Font("SimHei", Font.BOLD, 16));
		menuBar.add(mnProyecto);
		
		
		mntmDatosDeAutor.setForeground(new Color(128, 0, 0));
		mntmDatosDeAutor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		mntmDatosDeAutor.addActionListener(this);
		mnProyecto.add(mntmDatosDeAutor);
		
		contentPane = new PanelImagen ("/Imagenes/Recepcion.jpg");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(255, 0, 0));
		separator.setForeground(new Color(128, 128, 128));
		separator.setBounds(41, 155, 454, 20);
		contentPane.add(separator);
		
		JLabel lblEtiquetas = new JLabel("Etiquetas");
		lblEtiquetas.setForeground(new Color(255, 255, 0));
		lblEtiquetas.setFont(new Font("SimHei", Font.BOLD, 24));
		lblEtiquetas.setBounds(64, 48, 154, 39);
		contentPane.add(lblEtiquetas);
		
		comboBox.setForeground(new Color(0, 255, 0));
		comboBox.setFont(new Font("SimHei", Font.BOLD, 20));
		comboBox.setBounds(205, 59, 207, 20);
		contentPane.add(comboBox);
		
		JLabel lblTexto = new JLabel("Texto");
		lblTexto.setForeground(new Color(127, 255, 0));
		lblTexto.setFont(new Font("SimHei", Font.BOLD, 28));
		lblTexto.setBounds(25, 197, 109, 45);
		contentPane.add(lblTexto);
		
		txtHola = new JTextField();
		txtHola.setBackground(new Color(47, 79, 79));
		txtHola.setForeground(new Color(255, 255, 0));
		txtHola.setFont(new Font("SimHei", Font.BOLD, 20));
		txtHola.setEditable(false);
		txtHola.setBounds(123, 205, 372, 35);
		contentPane.add(txtHola);
		txtHola.setColumns(10);
		
		JButton btnNewButton = new JButton("Aceptar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnNewButton.setBackground(new Color(95, 158, 160));
		btnNewButton.setForeground(new Color(139, 0, 0));
		btnNewButton.setFont(new Font("SimHei", Font.BOLD, 20));
		btnNewButton.setBounds(362, 105, 109, 39);
		contentPane.add(btnNewButton);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		 if (e.getSource()== mntmAbrir){
			 int nose = fileC.showOpenDialog(mntmAbrir);
			 if (nose== fileC.APPROVE_OPTION){
				 String direccion= fileC.getSelectedFile().getAbsolutePath();
				 lectura = new Lectura(direccion);
				 lectura.leerLinea();
			 }else if (nose== fileC.CANCEL_OPTION){
				 JOptionPane.showMessageDialog(null, "Ha decidido cancelar la operacion");
			 }else if (nose== fileC.ERROR_OPTION){
				 JOptionPane.showMessageDialog(null, "Hubo un error al abrir archivo");
			 }
			 
			 for(int i=0; i < Lectura.texto.size(); i+=2){
				 String item=Lectura.texto.get(i);
				 comboBox.addItem(item);
				}
		 }		
	}
}
