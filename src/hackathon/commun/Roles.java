package hackathon.commun;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Roles {

	// Champs statiques

	public static final String ADMINISTRATEUR = "ADMINISTRATEUR";
	public static final String UTILISATEUR = "UTILISATEUR";
	public static final String CORRECTEUR = "CORRECTEUR";
	public static final String ADMINBIS = "ADMINBIS";

	private static final List<String> roles = Collections.unmodifiableList(Arrays.asList(ADMINISTRATEUR, UTILISATEUR, CORRECTEUR, ADMINBIS));

	private static final String[] libelles = new String[] { "Administrateur", "Correcteur" , "Utilisateur", "AdminBis" };

	// Constructeur privé qui empêche l'instanciation de la classe

	private Roles() {
	}

	// Actions

	public static List<String> getRoles() {
		return roles;
	}

	public static String getLibelle(String role) {
		int index = roles.indexOf(role);
		if (index == -1) {
			throw new IllegalArgumentException();
		}
		return libelles[index];
	}
}