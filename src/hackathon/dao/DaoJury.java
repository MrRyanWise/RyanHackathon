package hackathon.dao;

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.inject.Inject;
import javax.sql.DataSource;
import hackathon.data.Hackathon;
import hackathon.data.Jury; 
import jfox.jdbc.UtilJdbc;

public class DaoJury {
	
	//constructeur
	public DaoJury() {
		
	}
	
	//champs 
	@Inject
	private DataSource dataSource ;
	@Inject
	private DaoHackathon daoHackathon;
	
	//actions
	public int inserer ( Jury jury) {
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO superUser ( idjury, nomJury , idHackathon ) VALUES( ?, ?, ? ) ";
			
			 
			
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			stmt.setObject( 1, jury.getIdJury() );
			stmt.setObject( 2, jury.getNomJury() );
			stmt.setObject( 3, jury.getIdHackathon());
			
			//Insertion de la personne correspondante 
			daoHackathon.inserer(jury.getHackathon());
			
			//Insertion de la personne precedente dans la table des superUser
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			jury.setIdJury( rs.getObject( 1, Integer.class) );
			return jury.getIdJury();
	
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
			sql = "DELETE FROM jury WHERE idjury = ? ";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, id );
			
			//suppression de la personne dans la table des superUsers
			stmt.executeUpdate();
			
			////suppression de la personne dans la table des personnes
			daoHackathon.supprimer(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
	
	
	public Jury retrouver( int id ) {

		Connection			cn 		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM hackathon INNER JOIN jury ON hackathon.idHackathon = jury.idHackathon  WHERE hackathon.idHackathon = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject(1, id);
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				return construireJury( rs );
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
	public Jury retrouver( String mdp  ) {

		Connection			cn 		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM jury WHERE idjury = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject(1, mdp);
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				return construireJury( rs );
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
	
	protected Jury construireJury( ResultSet rs ) throws SQLException {
		Jury jury = new Jury();
		Hackathon hackathon = daoHackathon.construireHackathon(rs);
		
		jury.setIdJury( rs.getObject( "idjury", Integer.class ));
		jury.setNomJury(rs.getObject( "nomJury", String.class ));
		jury.setHackathon(hackathon); 
		return jury;
	}
	
}
