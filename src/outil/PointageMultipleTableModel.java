package outil;

import javax.swing.table.AbstractTableModel;

public class PointageMultipleTableModel extends AbstractTableModel 
{
	private static final long serialVersionUID = 1L;
	private String[] headers;
	private Object[][] list;
	private boolean[] columnEditables;

	public PointageMultipleTableModel(String[] headers, Object[][] list, boolean[] columnEditables){
		this.setHeaders(headers);
		this.setData(list);
		this.setColumnEditables(columnEditables);
	}
	
	public void setData(Object[][] data){
		this.list = data;
	}
	
	public void setHeaders(String[] h){
		this.headers = h;
	}
	
	public void setColumnEditables(boolean[] columnEditables){
		this.columnEditables = columnEditables;
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
				list[rowIndex][columnIndex] = (String) obj;
				break;

			case 8: 
				list[rowIndex][columnIndex] = (String) obj;
				break;

			case 9: 
				list[rowIndex][columnIndex] = (String) obj;
				break;
			
			case 10: 
				list[rowIndex][columnIndex] = (String) obj;
				break;
				
			case 11: 
				list[rowIndex][columnIndex] = (String) obj;
				break;
				
			case 12: 
				list[rowIndex][columnIndex] = (String) obj;
				break;
				
			case 13: 
				list[rowIndex][columnIndex] = (String) obj;
				break;
				
			case 14: 
				list[rowIndex][columnIndex] = (String) obj;
				break;
				
			case 15: 
				list[rowIndex][columnIndex] = (String) obj;
				break;
				
			case 16: 
				list[rowIndex][columnIndex] = (String) obj;
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
				return String.class;

			case 8:
				return String.class;

			case 9:
				return String.class;
				
			case 10:
				return String.class;
			
			case 11:
				return String.class;
				
			case 12:
				return String.class;
				
			case 13:
				return String.class;
				
			case 14:
				return String.class;
				
			case 15:
				return String.class;
				
			case 16:
				return String.class;
	
			default:
				throw new IllegalArgumentException();
		}
	}

	@Override	
	public boolean isCellEditable(int row, int column) {
		return columnEditables[column];
	}
}
