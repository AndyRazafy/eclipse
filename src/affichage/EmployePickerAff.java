package affichage;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import database.connection.DatabaseConnection;
import database.manager.DatabaseManager;
import entite.Employe;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;

import outil.EmployePickerTableModel;
import outil.PointageMultipleTableModel;
import listener.EmployePickerAffListener;

public class EmployePickerAff extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JComboBox<String> posteComboBox = new JComboBox<String>(getPosteListe());
	private String[][] tableData;
	private EmployePickerTableModel tableModel;
	private JFrame obj;

	public EmployePickerAff(JFrame obj)throws Exception{
		this.obj = (JFrame) obj;
		setTableData();
		setTableModel();
		init();
	}
	
	public void init(){
		setTitle("Employe Picker");
		setBounds(400, 100, 450, 340);
		this.setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		posteComboBox.setBounds(62, 11, 100, 22);
		posteComboBox.setActionCommand("poste");
		contentPane.add(posteComboBox);
		
		JLabel lblPoste = new JLabel("Poste:");
		lblPoste.setBounds(10, 15, 50, 14);
		contentPane.add(lblPoste);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 40, 422, 222);
		contentPane.add(scrollPane);
		
		table = new JTable(tableModel);
		scrollPane.setViewportView(table);
		
		JButton btnSelectionner = new JButton("Selectionner");
		btnSelectionner.setBounds(224, 273, 107, 23);
		btnSelectionner.setActionCommand("selectionner");
		contentPane.add(btnSelectionner);
		
		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.setBounds(341, 273, 91, 23);
		btnAnnuler.setActionCommand("annuler");
		contentPane.add(btnAnnuler);
		
		posteComboBox.addActionListener(new EmployePickerAffListener(this));
		btnSelectionner.addActionListener(new EmployePickerAffListener(this));
		btnAnnuler.addActionListener(new EmployePickerAffListener(this));
	}
	
	public String[][] getData(){
		return this.tableData;
	}
	
	public Vector<String> getPosteListe()throws SQLException, Exception{
		Connection conn = null;
		try{
			conn = DatabaseConnection.getPostgreConnection("rh", "postgres", "itu");
			return DatabaseManager.getAllPoste(conn);
		}
		catch(SQLException sqle){
			throw sqle;
		}
		catch(Exception e){
			throw e;
		}
		finally{
			if(conn != null) conn.close();
		}
	}
	
	public void setTableData()throws SQLException, Exception{
		Connection conn = null;
		try{
			conn = DatabaseConnection.getPostgreConnection("rh", "postgres", "itu");
			this.tableData = dataToStringArray(DatabaseManager.getAllEmploye(posteComboBox.getSelectedItem().toString(), conn));
		}
		catch(SQLException sqle){
			throw sqle;
		}
		catch(Exception e){
			throw e;
		}
		finally{
			if(conn != null) conn.close();
		}
	}
	
	public void setTableModel(){
		tableModel = new EmployePickerTableModel(this.tableData); 
	}
	
	public void selectionner()throws Exception{
		if(this.obj instanceof CongeAff){
			CongeAff fenetre = null;
			try{
				fenetre= (CongeAff) obj;
				fenetre.setEmpTextField((String) tableData[table.getSelectedRow()][0]);
				this.setVisible(false);
			}
			catch(Exception e){
				fenetre.getCommentTextArea().setText("Veuiller selectionner.");
			}
		}
	}
	
	public void annuler(){
		this.setVisible(false);
	}
	
	public void poste()throws Exception{
		setTableData();
		tableModel.setData(tableData);
		actualiser();
	}
	
	public void actualiser(){
		table.revalidate();
		table.repaint();
	}
	
	private String[][] dataToStringArray(ArrayList<Employe> empListe)throws Exception{ // String[][] for tableData
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		String[][] tableData = new String[empListe.size()][17];
		int i = 0;
		for(Employe emp : empListe){
			tableData[i][0] = emp.getMatricule();
			tableData[i][1] = emp.getCin();
			tableData[i][2] = emp.getNom();
			tableData[i][3] = emp.getPrenom();
			tableData[i][4] = format.format(emp.getNaissance());
			i++;
		}
		return tableData;
	}
}
