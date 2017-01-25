package affichage;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import database.connection.DatabaseConnection;
import database.manager.DatabaseManager;
import entite.Conge;
import entite.Employe;
import outil.EmployePickerTableModel;
import outil.ValidationCongeTableModel;
import javax.swing.JTextArea;

import listener.ValidationCongeAffListener;

public class ValidationCongeAff extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private ArrayList<Conge> tableData;
	private ValidationCongeTableModel tableModel;
	private JTextArea commentTextArea;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ValidationCongeAff frame = new ValidationCongeAff();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ValidationCongeAff()throws Exception{
		setTableData();
		setTableModel();
		init();
	}
	
	public void init(){
		setTitle("Validation de conge");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		setBounds(100, 100, 685, 366);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 30, 657, 208);
		contentPane.add(scrollPane);
		
		table = new JTable(this.tableModel);
		scrollPane.setViewportView(table);
		
		JButton btnRefuser = new JButton("Refuser");
		btnRefuser.setBounds(475, 249, 91, 23);
		btnRefuser.setActionCommand("refuser");
		contentPane.add(btnRefuser);
		
		JButton btnValider = new JButton("Valider");
		btnValider.setBounds(374, 249, 91, 23);
		btnValider.setActionCommand("valider");
		contentPane.add(btnValider);
		
		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.setBounds(576, 249, 91, 23);
		btnAnnuler.setActionCommand("annuler");
		contentPane.add(btnAnnuler);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 280, 657, 46);
		contentPane.add(scrollPane_1);
		
		commentTextArea = new JTextArea();
		scrollPane_1.setViewportView(commentTextArea);
		
		btnValider.addActionListener(new ValidationCongeAffListener(this));
		btnRefuser.addActionListener(new ValidationCongeAffListener(this));
		btnAnnuler.addActionListener(new ValidationCongeAffListener(this));
	}
	
	public void setTableData()throws SQLException, Exception{
		Connection conn = null;
		try{
			conn = DatabaseConnection.getPostgreConnection("rh", "postgres", "itu");
			this.tableData = DatabaseManager.getCongeAValider(conn);
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
	
	public void setTableModel()throws Exception{
		tableModel = new ValidationCongeTableModel(dataToObjectArray(this.tableData)); 
	}
	
	public void valider(){
		clearComment();
		this.commentTextArea.setText("Validation....");
		Connection conn = null;
		try{
			conn = DatabaseConnection.getPostgreConnection("rh", "postgres", "itu");
			int trueCount = 0, insertCount = 0, length = table.getRowCount();
			for(int i = 0;i < length;i++){
				if((boolean) table.getModel().getValueAt(i, 7)){
					trueCount++;
					try{
						DatabaseManager.insertIntoTable(tableData.get(i), "validation_conge", conn);
					}
					catch(Exception e){
						continue;
					}
					insertCount++;
				}
			}
			this.commentTextArea.setText("\nValidé: " + insertCount + "/" + trueCount + " selectionnes\nTerminer");
			setTableData();
			setTableModel();
			actualiser();
		}
		catch(SQLException sqle){
			this.commentTextArea.setText(sqle.getMessage());
		}
		catch(Exception e){
			this.commentTextArea.setText(e.getMessage());
		}
		finally{
			try{
				if(conn != null) conn.close();
			}
			catch(SQLException sqle){
				this.commentTextArea.setText(sqle.getMessage());
			}
		}
	}
	
	public void refuser(){
		clearComment();
		this.commentTextArea.setText("Rejet....");
		Connection conn = null;
		try{
			conn = DatabaseConnection.getPostgreConnection("rh", "postgres", "itu");
			int trueCount = 0, insertCount = 0, length = table.getRowCount();
			for(int i = 0;i < length;i++){
				if((boolean) table.getModel().getValueAt(i, 7)){
					trueCount++;
					try{
						DatabaseManager.insertIntoTable(tableData.get(i), "refus_conge", conn);
					}
					catch(Exception e){
						continue;
					}
					insertCount++;
				}
			}
			this.commentTextArea.setText("\nRejet: " + insertCount + "/" + trueCount + " selectionnes\nTerminer");
			setTableData();
			setTableModel();
			actualiser();
		}
		catch(SQLException sqle){
			this.commentTextArea.setText(sqle.getMessage());
		}
		catch(Exception e){
			this.commentTextArea.setText(e.getMessage());
		}
		finally{
			try{
				if(conn != null) conn.close();
			}
			catch(SQLException sqle){
				this.commentTextArea.setText(sqle.getMessage());
			}
		}
	}
	
	public void annuler(){
		int length = table.getRowCount();
		for(int i = 0;i < length;i++){
			if((boolean) table.getModel().getValueAt(i, 7)){
				table.setValueAt(false, i, 7);
			}
		}
		actualiser();
	}
	
	public void actualiser(){
		table.setModel(tableModel);
		this.table.revalidate();
		this.table.repaint();
	}
	
	public void clearComment(){
		this.commentTextArea.setText("");
	}
	
	private Object[][] dataToObjectArray(ArrayList<Conge> congeListe)throws Exception{ // String[][] for tableData
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatHeure = new SimpleDateFormat("HH:mm");
		Object[][] tableData = new Object[congeListe.size()][8];
		int i = 0;
		Connection conn = null;
		try{
			conn = DatabaseConnection.getPostgreConnection("rh", "postgres", "itu");
			for(Conge c : congeListe){
				Employe emp = null;
				try{
					emp = DatabaseManager.getEmployeById(c.getEmpId(), conn);
					tableData[i][0] = (String) emp.getMatricule();
					tableData[i][1] = (String) emp.getNom() + "/" + emp.getPrenom();
					tableData[i][2] = (String) format.format(c.getDDebut());
					tableData[i][3] = (String) format.format(c.getDFin());
					tableData[i][4] = (String) formatHeure.format(c.getHDebut());
					tableData[i][5] = (String) formatHeure.format(c.getHFin());
					tableData[i][6] = (String) c.getDureeToString();
					tableData[i][7] = (Boolean) false;
					i++;
				}
				catch(SQLException sqle){
					throw sqle;
				}
				catch(Exception e){
					throw e;
				}
			}
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
		return tableData;
	}
}
