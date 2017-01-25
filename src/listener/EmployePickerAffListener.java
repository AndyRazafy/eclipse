package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import affichage.EmployePickerAff;

public class EmployePickerAffListener implements ActionListener
{
	private EmployePickerAff fenetre;
	
	public EmployePickerAffListener(EmployePickerAff fenetre){
		this.fenetre = fenetre;
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		try{
			System.out.println(e.getActionCommand());
			if(e.getActionCommand().compareToIgnoreCase("selectionner") == 0) fenetre.selectionner();
			else if(e.getActionCommand().compareToIgnoreCase("poste") == 0) fenetre.poste();
			else if(e.getActionCommand().compareToIgnoreCase("annuler") == 0) fenetre.annuler();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
