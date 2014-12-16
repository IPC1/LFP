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
import javax.swing.JTextPane;
import java.awt.Panel;


public class Inicio extends JFrame implements ActionListener {

	private PanelImagen contentPane;
	JMenuItem mntmAbrir = new JMenuItem("Abrir");
	JMenuItem mntmDatosDeAutor = new JMenuItem("Datos de Autor");
	JFileChooser fileC= new JFileChooser();
	
	//private JTextField txtHola;
	JComboBox comboBox = new JComboBox();
	JTextPane txtpnPa = new JTextPane();
	JTextPane txtHola = new JTextPane();
	JLabel lblTexto = new JLabel("Texto");
	JButton btnRegresar = new JButton("Regresar");
	
	public Inicio() {
		setForeground(new Color(255, 165, 0));
		setBackground(new Color(0, 128, 0));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 571, 359);
		
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
		separator.setBounds(41, 155, 480, 20);
		contentPane.add(separator);
		
		JLabel lblEtiquetas = new JLabel("Etiquetas");
		lblEtiquetas.setForeground(new Color(255, 255, 0));
		lblEtiquetas.setFont(new Font("SimHei", Font.BOLD, 24));
		lblEtiquetas.setBounds(64, 48, 154, 39);
		contentPane.add(lblEtiquetas);
		comboBox.setBackground(new Color(224, 255, 255));
		
		comboBox.setForeground(new Color(47, 79, 79));
		comboBox.setFont(new Font("SimHei", Font.BOLD, 20));
		comboBox.setBounds(205, 59, 207, 20);
		contentPane.add(comboBox);
		
		
		lblTexto.setForeground(new Color(127, 255, 0));
		lblTexto.setFont(new Font("SimHei", Font.BOLD, 28));
		lblTexto.setBounds(12, 187, 109, 45);
		contentPane.add(lblTexto);
		
		txtHola.setBackground(new Color(47, 79, 79));
		txtHola.setForeground(new Color(255, 255, 0));
		txtHola.setFont(new Font("SimHei", Font.BOLD, 18));
		txtHola.setEditable(false);
		txtHola.setBounds(122, 197, 398, 54);
		contentPane.add(txtHola);
		//txtHola.setColumns(10);
		
		JButton btnNewButton = new JButton("Aceptar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtHola.setVisible(true);
			    lblTexto.setVisible(true);
			    txtpnPa.setVisible(false);
				btnRegresar.setVisible(false);
				String etiqueta= (String) comboBox.getSelectedItem();
				int contador=0;
				while(etiqueta!=Lectura.texto.get(contador)){
					contador++;
				}
				String text= Lectura.texto.get(contador+1);
				txtHola.setText(text);
			}
		});
		btnNewButton.setForeground(new Color(139, 0, 0));
		btnNewButton.setFont(new Font("SimHei", Font.BOLD, 20));
		btnNewButton.setBounds(388, 103, 133, 39);
		contentPane.add(btnNewButton);
		
		btnRegresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtHola.setVisible(true);
			    lblTexto.setVisible(true);
			    txtpnPa.setVisible(false);
				btnRegresar.setVisible(false);
			}
		});
		btnRegresar.setForeground(new Color(128, 0, 0));
		btnRegresar.setFont(new Font("SimHei", Font.PLAIN, 18));
		btnRegresar.setBounds(401, 258, 120, 23);
		btnRegresar.setVisible(false);
		contentPane.add(btnRegresar);
		
		
		txtpnPa.setFont(new Font("SimHei", Font.BOLD, 18));
		txtpnPa.setForeground(new Color(47, 79, 79));
		txtpnPa.setEditable(false);
		txtpnPa.setText("Curso: Lenguajes Formales de Programacion\r\nNombre: "
				+ "Paula Mar\u00EDa V\u00E1squez Cifuentes\r\nCarnet: 201314745\r\nFecha de Entrega: 16 de Diciembre, 2014\r\n");
		txtpnPa.setBackground(new Color(224, 255, 255));
		txtpnPa.setBounds(35, 170, 486, 111);
		contentPane.add(txtpnPa);
		txtpnPa.setVisible(false);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		 if (e.getSource()== mntmAbrir){
			 txtHola.setVisible(true);
			    lblTexto.setVisible(true);
			    txtpnPa.setVisible(false);
				btnRegresar.setVisible(false);
				
				if(Lectura.texto.size()>0){ //Limpia el arraylist.
						Lectura.texto.clear();
						comboBox.removeAllItems();
				}
			
				
			 int nose = fileC.showOpenDialog(mntmAbrir);
			 if (nose== fileC.APPROVE_OPTION){
				 String direccion= fileC.getSelectedFile().getAbsolutePath();
				 Lectura lectura = new Lectura(direccion);
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
		 }else if(e.getSource()==mntmDatosDeAutor){
			 txtHola.setVisible(false);
			 lblTexto.setVisible(false);
			 txtpnPa.setVisible(true);
			 btnRegresar.setVisible(true);
			 
				
		 }
	}
}
