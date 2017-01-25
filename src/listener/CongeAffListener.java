package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import affichage.CongeAff;

public class CongeAffListener implements ActionListener
{
	private CongeAff fenetre;
	
	public CongeAffListener(CongeAff fenetre){
		this.fenetre = fenetre;
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		System.out.println(e.getActionCommand());
		if(e.getActionCommand().compareToIgnoreCase("valider") == 0) fenetre.valider();
		else if(e.getActionCommand().compareToIgnoreCase("rechercher") == 0) fenetre.rechercher();
		else if(e.getActionCommand().compareToIgnoreCase("annuler") == 0) fenetre.annuler();
	}
}
