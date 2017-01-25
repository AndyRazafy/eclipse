package entite;

import java.sql.Date;

public class Employe 
{
	private int id;
	private int poste;
	private String matricule;
	private String cin;
	private String nom;
	private String prenom;
	private Date naissance;
	private Date enregistrement;
	private String adresse;
	
	public Employe(int id, int poste, String matricule, String cin, String nom, String prenom, Date naissance, Date enregistrement,
			String adresse) {
		super();
		this.setId(id);
		this.setPoste(poste);
		this.setMatricule(matricule);
		this.setCin(cin);
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setNaissance(naissance);
		this.setEnregistrement(enregistrement);
		this.setAdresse(adresse);
	}
	
	public Employe(int poste, String matricule, String cin, String nom, String prenom, Date naissance, Date enregistrement,
			String adresse) {
		super();
		this.setPoste(poste);
		this.setMatricule(matricule);
		this.setCin(cin);
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setNaissance(naissance);
		this.setEnregistrement(enregistrement);
		this.setAdresse(adresse);
	}

	public int getId() {
		return id;
	}
	
	public int getPoste(){
		return poste;
	}
	
	public String getMatricule() {
		return matricule;
	}
	
	public String getCin() {
		return cin;
	}
	
	public String getNom() {
		return nom;
	}
	
	public String getPrenom() {
		return prenom;
	}
	
	public Date getNaissance() {
		return naissance;
	}
	
	public Date getEnregistrement() {
		return enregistrement;
	}
	
	public String getAdresse() {
		return adresse;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setPoste(int poste) {
		this.poste = poste;
	}
	
	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}
	
	public void setCin(String cin) {
		this.cin = cin;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public void setNaissance(Date naissance) {
		this.naissance = naissance;
	}
	
	public void setEnregistrement(Date enregistrement) {
		this.enregistrement = enregistrement;
	}
	
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	
	@Override
	public String toString() {
		return "Employe [id=" + id + ", poste=" + poste + ", matricule=" + matricule + ", cin=" + cin + ", nom=" + nom
				+ ", prenom=" + prenom + ", naissance=" + naissance + ", enregistrement=" + enregistrement
				+ ", adresse=" + adresse + "]";
	}
}
