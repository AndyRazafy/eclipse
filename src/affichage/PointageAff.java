package affichage;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PointageAff extends JFrame {

	private JPanel contentPane;
	private JTextField identification;
	private JTextField heure;
	private JTextField date;
	private JTextArea etat;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PointageAff frame = new PointageAff();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PointageAff() {
		init();
	}
	
	private void init(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 218, 221);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCinmatricule = new JLabel("CIN/MATRICULE:");
		lblCinmatricule.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCinmatricule.setBounds(10, 11, 94, 14);
		contentPane.add(lblCinmatricule);
		
		identification = new JTextField();
		identification.setBounds(114, 8, 86, 20);
		contentPane.add(identification);
		identification.setColumns(10);
		
		JLabel lblHeure = new JLabel("Heure:");
		lblHeure.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHeure.setBounds(58, 36, 46, 14);
		contentPane.add(lblHeure);
		
		heure = new JTextField();
		heure.setBounds(114, 33, 86, 20);
		contentPane.add(heure);
		heure.setColumns(10);
		
		JLabel lblDate = new JLabel("Date:");
		lblDate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDate.setBounds(58, 61, 46, 14);
		contentPane.add(lblDate);
		
		date = new JTextField();
		date.setBounds(114, 58, 86, 20);
		contentPane.add(date);
		date.setColumns(10);
		
		JButton btnEntree = new JButton("Entree");
		btnEntree.setBounds(10, 103, 91, 37);
		btnEntree.setActionCommand("entree");
		contentPane.add(btnEntree);
		
		JButton btnSortie = new JButton("sortie");
		btnSortie.setBounds(109, 103, 91, 37);
		btnSortie.setActionCommand("sortie");
		contentPane.add(btnSortie);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 148, 190, 42);
		contentPane.add(scrollPane);
		
		etat = new JTextArea();
		scrollPane.setViewportView(etat);
	}
	
	public void entree(){
		try{

		}
		catch(Exception e){
			
		}
	}
	
	public void sortie(){
		try{
			
		}
		catch(Exception e){
			
		}
	}
	
	public void clearAll(){
		this.identification.setText("");
		this.heure.setText("");
		this.date.setText("");
		this.etat.setText("");
	}
}
