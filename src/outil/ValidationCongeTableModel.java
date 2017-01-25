package outil;

import javax.swing.table.AbstractTableModel;

public class ValidationCongeTableModel extends AbstractTableModel
{
	private static final long serialVersionUID = 1L;
	private String[] headers = {"Matricule","Nom/Prenom","Date debut","Date Fin", "Heure debut", "Heure fin", "Duree", "Selection"};
	private Object[][] list;
	private boolean[] columnEditables = {false,false,false,false,false,false,false,true};
	
	public ValidationCongeTableModel(Object[][] list){
		this.setData(list);
	}
	
	public void setData(Object[][] data){
		this.list = data;
	}
	
	public void setHeaders(String[] h){
		this.headers = h;
	}

	@Override
	public int getColumnCount() {
		return headers.length;
	}
	
	@Override
	public String getColumnName(int columnIndex) {
		return headers[columnIndex];
	}

	@Override
	public int getRowCount() {
		return list.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return list[rowIndex][columnIndex];
	}

	@Override
	public void setValueAt(Object obj, int rowIndex, int columnIndex) {
		switch (columnIndex){
			case 0: 
				list[rowIndex][columnIndex] = (String) obj;
				break;

			case 1: 
				list[rowIndex][columnIndex] = (String) obj;
				break;

			case 2: 
				list[rowIndex][columnIndex] = (String) obj;
				break;

			case 3: 
				list[rowIndex][columnIndex] = (String) obj;
				break;

			case 4: 
				list[rowIndex][columnIndex] = (String) obj;
				break;
				
			case 5: 
				list[rowIndex][columnIndex] = (String) obj;
				break;
				
			case 6: 
				list[rowIndex][columnIndex] = (String) obj;
				break;
				
			case 7: 
				list[rowIndex][columnIndex] = (boolean) obj;
				break;

			default:
				throw new IllegalArgumentException();
		}
	}


	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
			case 0:
				return String.class;

			case 1:
				return String.class;

			case 2:
				return String.class;
	
			case 3:
				return String.class;
	
			case 4:
				return String.class;
			
			case 5:
				return String.class;
				
			case 6:
				return String.class;
				
			case 7:
				return Boolean.class;
	
			default:
				throw new IllegalArgumentException();
		}
	}

	@Override	
	public boolean isCellEditable(int row, int column) {
		return columnEditables[column];
	}
}
