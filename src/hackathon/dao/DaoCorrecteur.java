package hackathon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.inject.Inject;
import javax.sql.DataSource;
import hackathon.data.Correcteur; 
import hackathon.data.Personne; 
import jfox.jdbc.UtilJdbc;

public class DaoCorrecteur {
	
	//constructeur
	public DaoCorrecteur() {
		
	}
	
	//champs 
	@Inject
	private DataSource dataSource ;
	@Inject
	private DaoPersonne daoPersonne ;
	
	//actions
	public int inserer ( Correcteur correcteur ) {
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO correcteur ( id, motDePasse, specialite ) VALUES( ?, ?, ?) ";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			stmt.setObject( 1, correcteur.getId() );
			stmt.setObject( 2, correcteur.getMotDePasse() );
			stmt.setObject( 3, correcteur.getSpecialite() );
			
			//Insertion de la personne correspondante 
			daoPersonne.inserer(correcteur.getPersonne() );
			
			//Insertion de la personne precedente dans la table  correcteur
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			correcteur.setId( rs.getObject( 1, Integer.class) );
			return correcteur.getId();
	
		} catch ( SQLException e ) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	public void supprimer( int id ) {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "DELETE FROM correcteur WHERE id = ? ";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, id );
			
			//suppression de la personne dans la table des superUsers
			stmt.executeUpdate();
			
			////suppression de la personne dans la table des personnes
			daoPersonne.supprimer(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
	
 
	public Correcteur retrouver( int id ) {

		Connection			cn 		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM personne INNER JOIN correcteur ON personne.id = correcteur.id WHERE personne.id = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject(1, id);
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				return construireCorrecteur( rs );
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	// Méthodes auxiliaires
	
	protected Correcteur construireCorrecteur( ResultSet rs ) throws SQLException {
		Correcteur correcteur = new Correcteur();
		Personne personne = daoPersonne.construirePersonne( rs ) ;
	    correcteur.setMotDePasse(rs.getObject( "motDePasse", String.class ));
	    correcteur.setSpecialite(rs.getObject( "specialite", String.class ));
	  	correcteur.setPersonne(personne);
		return correcteur;
	}
	
}
