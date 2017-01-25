package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import affichage.ValidationCongeAff;

public class ValidationCongeAffListener implements ActionListener
{
	ValidationCongeAff fenetre;
	
	public ValidationCongeAffListener(ValidationCongeAff fenetre){
		this.fenetre = fenetre;
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		System.out.println(e.getActionCommand());
		if(e.getActionCommand().compareToIgnoreCase("valider") == 0) fenetre.valider();
		else if(e.getActionCommand().compareToIgnoreCase("refuser") == 0) fenetre.refuser();
		if(e.getActionCommand().compareToIgnoreCase("annuler") == 0) fenetre.annuler();
	}
}
