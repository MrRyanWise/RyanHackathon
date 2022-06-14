package hackathon.dao;

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.inject.Inject;
import javax.sql.DataSource;

import hackathon.data.Hackathon;
import hackathon.data.Personne; 
import jfox.jdbc.UtilJdbc;

public class DaoIntervenir {
	
	//constructeur
	public DaoIntervenir() {
		
	}
	
	
	//champs 
	@Inject
	private DataSource dataSource ;
	@Inject
	private DaoHackathon daoHackathon ;
	
	
	//actions
	public void inserer ( Hackathon hack,  Personne pers) {
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO intervenir ( id, idHackathon ) VALUES( ?, ? ) ";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			
			stmt.setObject( 1, pers.getId());
			stmt.setObject( 2, hack.getIdHackathon() );
			
			//Insertion de la personne precedente dans la table des superUser
			stmt.executeUpdate();
	
		} catch ( SQLException e ) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	public void supprimer(Hackathon hack,  Personne pers ) {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "DELETE FROM intervenir WHERE id = ? AND idHackathon = ? ";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, pers.getId() );
			stmt.setObject( 2, hack.getIdHackathon() );
			
			//suppression de la personne dans la table des superUsers
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
	
	
	public Hackathon retrouverHackathon( int id ) {

		Connection			cn 		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT idHackathon FROM intervenir WHERE id = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject(1, id);
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				//on retourne le premier hackathon trouvé
				return daoHackathon.retrouver(rs.getObject("idHackathon", Integer.class));
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}	
	
}
