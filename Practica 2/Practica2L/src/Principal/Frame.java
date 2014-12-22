package Principal;

import java.awt.BorderLayout;
import java.awt.EventQueue;
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
import javax.swing.JTextField;

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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(28, 149, 599, 211);
		scrollPane.setLayout(null);
		contentPane.add(scrollPane);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(63, 62, 176, 20);
		contentPane.add(comboBox);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(339, 62, 186, 20);
		contentPane.add(comboBox_1);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(516, 101, 111, 37);
		contentPane.add(btnBuscar);
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
