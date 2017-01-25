package affichage;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entite.Conge;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import listener.CongeAffListener;
import metier.DemanderConge;

public class CongeAff extends JFrame {

	private JPanel contentPane;
	private JTextField empTextField;
	private JTextField dDebutTextField;
	private JTextField hDebutTextField;
	private JTextField dFinTextField;
	private JTextField hFinTextField;
	private JTextArea commentTextArea;
	private EmployePickerAff employePicker;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CongeAff frame = new CongeAff();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CongeAff(){
		init();
		try{
			employePicker = new EmployePickerAff(this);
		}
		catch(Exception e){
			this.commentTextArea.setText(e.getMessage());
		}
	}
	
	public void init(){
		setTitle("Conge");
		this.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 224, 263);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEmploye = new JLabel("Employe:");
		lblEmploye.setHorizontalAlignment(SwingConstants.LEFT);
		lblEmploye.setBounds(10, 8, 60, 14);
		contentPane.add(lblEmploye);
		
		empTextField = new JTextField();
		empTextField.setBounds(10, 25, 140, 20);
		contentPane.add(empTextField);
		empTextField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Date debut:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(10, 59, 73, 14);
		contentPane.add(lblNewLabel);
		
		dDebutTextField = new JTextField();
		dDebutTextField.setBounds(10, 73, 110, 20);
		contentPane.add(dDebutTextField);
		dDebutTextField.setColumns(10);
		
		JLabel lblHeureDebut = new JLabel("Heure debut:");
		lblHeureDebut.setBounds(130, 59, 73, 14);
		contentPane.add(lblHeureDebut);
		
		hDebutTextField = new JTextField();
		hDebutTextField.setBounds(130, 73, 73, 20);
		contentPane.add(hDebutTextField);
		hDebutTextField.setColumns(10);
		
		JLabel lblDateFin = new JLabel("Date fin:");
		lblDateFin.setHorizontalAlignment(SwingConstants.LEFT);
		lblDateFin.setBounds(10, 104, 46, 14);
		contentPane.add(lblDateFin);
		
		dFinTextField = new JTextField();
		dFinTextField.setBounds(10, 117, 110, 20);
		contentPane.add(dFinTextField);
		dFinTextField.setColumns(10);
		
		JLabel lblHeureFin = new JLabel("Heure fin:");
		lblHeureFin.setBounds(130, 104, 69, 14);
		contentPane.add(lblHeureFin);
		
		hFinTextField = new JTextField();
		hFinTextField.setBounds(130, 117, 73, 20);
		contentPane.add(hFinTextField);
		hFinTextField.setColumns(10);
		
		JButton rechercherBtn = new JButton("...");
		rechercherBtn.setBounds(160, 24, 25, 23);
		rechercherBtn.setActionCommand("rechercher");
		contentPane.add(rechercherBtn);
		
		JButton annulerBtn = new JButton("Annuler");
		annulerBtn.setBounds(112, 160, 91, 23);
		annulerBtn.setActionCommand("annuler");
		contentPane.add(annulerBtn);
		
		JButton validerBtn = new JButton("Valider");
		validerBtn.setBounds(10, 160, 90, 23);
		validerBtn.setActionCommand("valider");
		contentPane.add(validerBtn);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 189, 193, 37);
		contentPane.add(scrollPane);
		
		commentTextArea = new JTextArea();
		commentTextArea.setEditable(false);
		commentTextArea.setLineWrap(true);
		scrollPane.setViewportView(commentTextArea);
		
		rechercherBtn.addActionListener(new CongeAffListener(this));
		validerBtn.addActionListener(new CongeAffListener(this));
		annulerBtn.addActionListener(new CongeAffListener(this));
	}
	
	public EmployePickerAff getEmployePicker(){
		return employePicker;
	}
	
	public JTextArea getCommentTextArea(){
		return this.commentTextArea;
	}
	
	public void setEmpTextField(String str){
		this.empTextField.setText(str);
	}
	
	public void setCommentTextArea(String str){
		this.commentTextArea.setText(str);
	}
	
	public void valider(){
		clearComment();
		this.commentTextArea.setText("Insertion...");
		try{
			DemanderConge.prendreConge(this.empTextField.getText(), this.dDebutTextField.getText(), this.dFinTextField.getText(), this.hDebutTextField.getText(), this.hFinTextField.getText());
			this.commentTextArea.setText(this.commentTextArea.getText() + "\nTerminer");
		}
		catch(Exception e){
			this.commentTextArea.setText(e.getMessage());
		}
	}
	
	public void annuler(){
		this.empTextField.setText("");
		this.dDebutTextField.setText("");
		this.dFinTextField.setText("");
		this.hDebutTextField.setText("");
		this.hFinTextField.setText("");
		clearComment();
	}
	
	public void rechercher(){
		this.employePicker.setVisible(true);
	}
	
	public void clearComment(){
		this.commentTextArea.setText("");
	}
}
