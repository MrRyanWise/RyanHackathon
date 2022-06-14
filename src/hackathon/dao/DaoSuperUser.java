package hackathon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.inject.Inject;
import javax.sql.DataSource;

import hackathon.data.Personne;
import hackathon.data.SuperUser;
import jfox.jdbc.UtilJdbc;

public class DaoSuperUser {
	
	//constructeur
	public DaoSuperUser() {
		
	}
	
	//champs 
	@Inject
	private DataSource dataSource ;
	@Inject
	private DaoPersonne daoPersonne ;
	
	//actions
	public int inserer ( SuperUser superUser ) {
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO superUser ( id, cle ) VALUES( ?, ? ) ";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			stmt.setObject( 1, superUser.getId() );
			stmt.setObject( 2, superUser.getCle() );
			
			//Insertion de la personne correspondante 
			daoPersonne.inserer( superUser.getPersonne() );
			
			//Insertion de la personne precedente dans la table des superUser
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			superUser.setId( rs.getObject( 1, Integer.class) );
			return superUser.getId();
	
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
			sql = "DELETE FROM superUser WHERE id = ? ";
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
	
	
	public SuperUser retrouver( int id ) {

		Connection			cn 		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM personne INNER JOIN superUser ON personne.id = superUser.id WHERE personne.idcompte = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject(1, id);
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				return construireSuperUser( rs );
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}

	// cette fonction permet de retrouver le super user grâce a son mot de passe
	public SuperUser retrouver( String mdp  ) {

		Connection			cn 		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM superUser WHERE cle = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject(1, mdp);
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				return construireSuperUser( rs );
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
	
	protected SuperUser construireSuperUser( ResultSet rs ) throws SQLException {
		SuperUser superUser = new SuperUser();
		Personne personne = daoPersonne.construirePersonne( rs ) ;
		superUser.setCle( rs.getObject( "cle", String.class ) );
		superUser.setPersonne(personne);
		return superUser;
	}
	
}
