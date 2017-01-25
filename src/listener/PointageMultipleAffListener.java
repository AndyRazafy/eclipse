package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import affichage.PointageMultipleAff;

public class PointageMultipleAffListener implements ActionListener
{
	private PointageMultipleAff fenetre;
	
	public PointageMultipleAffListener(PointageMultipleAff fenetre){
		this.fenetre = fenetre;
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		try{
			System.out.println(e.getActionCommand());
			if(e.getActionCommand().compareToIgnoreCase("poste") == 0) fenetre.poste();
			else if(e.getActionCommand().compareToIgnoreCase("entete") == 0) fenetre.entete();
			else if(e.getActionCommand().compareToIgnoreCase("pointer") == 0) fenetre.pointer();
			//else if(e.getActionCommand().compareToIgnoreCase("annuler") == 0) fenetre.annuler();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}