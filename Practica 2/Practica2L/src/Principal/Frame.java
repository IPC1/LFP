package Principal;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JTextPane;

public class Frame extends JFrame implements ActionListener{

	private PanelImagen contentPane;
	static ArrayList <String> Autor = new ArrayList <String>();
	LecturaArchivo l = new LecturaArchivo ();
	
	public Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 675, 440);
		contentPane = new PanelImagen("/Imagenes/Recepcion.jpg");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(89, 62, 176, 20);
		contentPane.add(comboBox);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(339, 62, 186, 20);
		contentPane.add(comboBox_1);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(516, 101, 111, 37);
		contentPane.add(btnBuscar);
		
		JPanel textPane = new JPanel();
	//textPane.setEditable(false);
		//textPane.setLineWrap(true);
		textPane.setBounds(36, 146, 567, 205);
		JScrollPane scroll = new JScrollPane (textPane);
		scroll.setBounds(36, 146, 592, 205);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scroll);
		textPane.setLayout(null);
		
		JLabel lblTitulo = new JLabel("Titulo");
		lblTitulo.setFont(new Font("Tekton Pro", Font.PLAIN, 18));
		lblTitulo.setBounds(86, 6, 44, 19);
		textPane.add(lblTitulo);
		
		JLabel lblAutor = new JLabel("Autor");
		lblAutor.setFont(new Font("Tekton Pro", Font.PLAIN, 18));
		lblAutor.setBounds(86, 38, 45, 19);
		textPane.add(lblAutor);
		
		JTextField textField_1 = new JTextField();
		textField_1.setBounds(166, 36, 116, 22);
		textPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblFormato = new JLabel("Formato");
		lblFormato.setFont(new Font("Tekton Pro", Font.PLAIN, 18));
		lblFormato.setBounds(74, 70, 65, 19);
		textPane.add(lblFormato);
		
		JTextField textField_2 = new JTextField();
		textField_2.setBounds(166, 71, 116, 22);
		textPane.add(textField_2);
		textField_2.setColumns(10);
		
		JTextField textField = new JTextField();
		textField.setBounds(166, 4, 316, 22);
		textPane.add(textField);
		textField.setColumns(10);
		
		JTextPane textPaneI = new JTextPane();
		textPaneI.setBounds(307, 38, 165, 109);
		textPane.add(textPaneI);
		
	}

	public void LlenarAutores(){
		String autor="";
		for (int i=0; i<AnalizadorLexico.Album.size();i++){
			autor= (AnalizadorLexico.Album.get(i).getAutor());
			if(!(Autor.contains(autor))){
			Autor.add(autor);
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
