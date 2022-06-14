package hackathon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.inject.Inject;
import javax.sql.DataSource;

import hackathon.data.Admin;
import hackathon.data.Personne; 
import jfox.jdbc.UtilJdbc;

public class DaoAdmin {
	
	//constructeur
	public DaoAdmin() {
		
	}
	
	//champs 
	@Inject
	private DataSource dataSource ;
	@Inject
	private DaoPersonne daoPersonne ;
	
	//actions
	public int inserer ( Admin admin ) {
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO admin ( id, type, motDePasse ) VALUES( ?, ?, ?) ";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			stmt.setObject( 1, admin.getId() );
			stmt.setObject( 2, admin.getType() );
			stmt.setObject( 3, admin.getMotDePasse() );
			
			//Insertion de la personne correspondante 
			daoPersonne.inserer(admin.getPersonne() );
			
			//Insertion de la personne precedente dans la table admin
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			admin.setId( rs.getObject( 1, Integer.class) );
			return admin.getId();
	
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
			sql = "DELETE FROM admin WHERE id = ? ";
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
	
	//retourne un admin à partir de son id dans la base
	public Admin retrouver( int id ) {

		Connection			cn 		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM personne INNER JOIN admin ON personne.id = admin.id WHERE personne.id = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject(1, id);
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				return construireAdmin( rs );
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	//retourne un admin à partir de son id de con compte
	public Admin retrouverPourCompte( int idcompte ) {

		Connection			cn 		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM personne INNER JOIN admin ON personne.id = admin.id WHERE personne.idcompte = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject(1, idcompte);
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				return construireAdmin( rs );
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
	
	protected Admin construireAdmin( ResultSet rs ) throws SQLException {
		Admin admin = new Admin();
		Personne personne = daoPersonne.construirePersonne( rs ) ;
		admin.setType(rs.getObject( "type", String.class ));
	    admin.setMotDePasse(rs.getObject( "motDePasse", String.class ));
	  	admin.setPersonne(personne);
		return admin;
	}
	
}
