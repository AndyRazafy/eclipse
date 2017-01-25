package affichage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import database.connection.DatabaseConnection;
import database.manager.DatabaseManager;
import entite.EmploiDuTemps;
import entite.Employe;
import entite.Pointage;
import listener.PointageMultipleAffListener;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Vector;
import javax.swing.ScrollPaneConstants;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import outil.PointageMultipleTableModel;
import metier.Pointer;

public class PointageMultipleAff extends JFrame 
{
	private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
	private static SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss");
	private static final long serialVersionUID = 1L;
	private JTextField dateRefTextField = new JTextField(this.sdf2.format(Date.valueOf(sdf1.format(new java.util.Date()))));
	private JTable table;
	private JComboBox<String> posteComboBox = new JComboBox<String>(getPosteListe());
	private String[][] tableData;
	private PointageMultipleTableModel tableModel;// = new PointageMultipleTableModel(getTableHeaders(), getTableData());
	
	public static void main(String[] args)throws Exception{
		new PointageMultipleAff().setVisible(true);
	}
	
	public PointageMultipleAff()throws Exception{
		setTableData();
		setTableModel();
		init();
	}

	public void init()throws Exception{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1080, 381);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		sdf3.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		JLabel lblPoste = new JLabel("Poste:");
		lblPoste.setBounds(10, 11, 38, 14);
		contentPane.add(lblPoste);
		
		posteComboBox.setBounds(58, 8, 115, 20);
		posteComboBox.setActionCommand("poste");
		contentPane.add(posteComboBox);
		
		JLabel lblSemaine = new JLabel("Semaine du:");
		lblSemaine.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSemaine.setBounds(183, 11, 70, 14);
		contentPane.add(lblSemaine);
		
		dateRefTextField.setBounds(259, 8, 86, 20);
		dateRefTextField.setActionCommand("entete");
		contentPane.add(dateRefTextField);
		dateRefTextField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 36, 1052, 274);
		contentPane.add(scrollPane);
		
		table = new JTable(tableModel);
		table.getTableHeader().setFont((new Font("Tahoma", Font.PLAIN, 10)));
		scrollPane.setViewportView(table);
		contentPane.add(scrollPane);
		
		JButton btnPointer = new JButton("Pointer");
		btnPointer.setBounds(947, 320, 115, 23);
		btnPointer.setActionCommand("pointer");
		contentPane.add(btnPointer);
		
		JCheckBox chckbxAvecRetard = new JCheckBox("avec retard");
		chckbxAvecRetard.setBounds(377, 7, 109, 23);
		contentPane.add(chckbxAvecRetard);
		
		JButton btnValider = new JButton("Valider");
		btnValider.setBounds(971, 7, 91, 23);
		btnValider.setActionCommand("valider");
		contentPane.add(btnValider);

		//this.dateRefTextField.addActionListener(new PointageMultipleAffListener(this));
		posteComboBox.addActionListener(new PointageMultipleAffListener(this));
		dateRefTextField.addActionListener(new PointageMultipleAffListener(this));
		btnPointer.addActionListener(new PointageMultipleAffListener(this));
	}
	
	public String getPoste(){
		return (String) this.posteComboBox.getSelectedItem().toString();
	}
	
	public String[][] getTableData(){
		return this.tableData;
	}

	public Vector<String> getPosteListe()throws SQLException, Exception{
		Connection conn = null;
		try{
			conn = DatabaseConnection.getPostgreConnection("rh", "postgres", "andy");
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
	
	public void setTableModel()throws Exception{
		boolean[] columnEditables = new boolean[] {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true};
		tableModel = new PointageMultipleTableModel(getTableHeaders(), getTableData(), columnEditables); 
	}
	
	public void setTableData()throws SQLException, Exception{
		Connection conn = null;
		try{
			conn = DatabaseConnection.getPostgreConnection("rh", "postgres", "andy");
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
	
	private String[] getTableHeaders()throws Exception{
		// header: identification / NomPrenom / lundi / retard / mardi / retard / mercredi / retard / jeudi / retard / vendredi / retard / samedi / retard / dimanche / retard / total retard 
		String d = sdf1.format(sdf2.parse(this.dateRefTextField.getText()));
		Date date = Date.valueOf(d);
		String[] names = {"identification", "Nom/Prenom"};
		String[] days = getDaysOfWeek(date, Calendar.getInstance().getFirstDayOfWeek());
		String retardLbl = "retard";
		String[] headers = new String[names.length + (days.length * 2) + 1];
		int i = 0, j = 0, limit = names.length;
		while(i < limit){
			headers[i] = names[i];
			i++;
		}
		i = limit;
		limit = ((days.length) * 2) + 2; 
		while(i < limit){
			if(i % 2 == 0){
				headers[i] = days[j];
				j++;
			}
			else headers[i] = retardLbl;
			i++;
		}
		i = limit;
		headers[i] = "retard total";
		return headers;
	}
	
	public void pointer()throws SQLException, Exception{
		Connection conn = null;
		try{
			conn = DatabaseConnection.getPostgreConnection("rh", "postgres", "andy");
			int limit = table.getRowCount(), i = 0, j = 0;
			long retardTotal = 0L;
			for(i = 0;i < limit;i++){
				for(j = 2;j < 16;j+=2){
					String heure = (String) table.getModel().getValueAt(i, j);
					String date = (String) table.getColumnModel().getColumn(j).getHeaderValue();
					String empId = (String) table.getModel().getValueAt(i, 0);
					//Pointer.pointerEntree(empId, heure, date);
					Pointage p = new Pointage(empId, heure, date);
					if(Pointer.enRetard(p, "e", conn)){
						EmploiDuTemps edt = DatabaseManager.getEmploiDuTemps(p, Pointer.jourToString(Pointer.getJourSemaine(p.getDate())), conn);
						System.out.println(p.getHeure().getTime());
						System.out.println(edt.getHDebut().getTime());
						Time diff = Pointer.calculDifference(edt.getHDebut(), p.getHeure());
						System.out.println(diff.getTime());
						retardTotal = retardTotal + diff.getTime();
					}
				}
				j = 0;
			}
			System.out.println(retardTotal);
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

	public void poste()throws Exception{
		setTableData();
		tableModel.setData(getTableData());
		table.revalidate();
		table.repaint();
	}
	
	public void entete()throws Exception{
		actualiserHeaders(getTableHeaders());
		table.getTableHeader().revalidate();
		table.getTableHeader().repaint();
	}
	
	public void annuler()throws Exception{
		
	}
	
	private void actualiserHeaders(String[] headers)throws Exception{
		table.getColumnModel().getColumn(2).setHeaderValue(headers[2]);
		table.getColumnModel().getColumn(4).setHeaderValue(headers[4]);
		table.getColumnModel().getColumn(6).setHeaderValue(headers[6]);
		table.getColumnModel().getColumn(8).setHeaderValue(headers[8]);
		table.getColumnModel().getColumn(10).setHeaderValue(headers[10]);
		table.getColumnModel().getColumn(12).setHeaderValue(headers[12]);
		table.getColumnModel().getColumn(14).setHeaderValue(headers[14]);
	}
	
	private String[][] dataToStringArray(ArrayList<Employe> empListe)throws Exception{ // String[][] for tableData
		String[][] tableData = new String[empListe.size()][17];
		int i = 0;
		for(Employe emp : empListe){
			tableData[i][0] = emp.getMatricule();
			tableData[i][1] = emp.getNom() + "/" + emp.getPrenom();
			tableData[i][2] = "";
			tableData[i][3] = "";
			tableData[i][4] = "";
			tableData[i][5] = "";
			tableData[i][6] = "";
			tableData[i][7] = "";
			tableData[i][8] = "";
			tableData[i][9] = "";
			tableData[i][10] = "";
			tableData[i][11] = "";
			tableData[i][12] = "";
			tableData[i][13] = "";
			tableData[i][14] = "";
			tableData[i][15] = "";
			tableData[i][16] = "";
			i++;
		}
		return tableData;
	}
	
	public static Time calculSomme(Time reel, Time ref)throws Exception{
		return Time.valueOf(sdf3.format(new Time(reel.getTime() + ref.getTime())));
	}

	private String[] getDaysOfWeek(Date refDate, int firstDayOfWeek)throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(refDate);
		calendar.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);
		String[] daysOfWeek = new String[7];
		for(int i = 0;i < 7;i++){
			daysOfWeek[i] = format.format(calendar.getTime());
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		return daysOfWeek;
	}
}
