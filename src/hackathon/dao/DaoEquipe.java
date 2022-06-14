package hackathon.dao;

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import hackathon.data.Equipe;
import jfox.jdbc.UtilJdbc;

public class DaoEquipe {
	
	//constructeur
	public DaoEquipe() {
	}
	
	//champs 
	@Inject
	private DataSource dataSource ;
		
	//actions
	public List<Equipe> listerTout() {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM Equipe ORDER BY pseudo";
			stmt = cn.prepareStatement( sql ); 
			rs = stmt.executeQuery();

			List<Equipe> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add( construireEquipe( rs ) );
			}
			return liste;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
		
	public int inserer ( Equipe equipe) {
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO equipe (pseudo,nombreMembre,lienTravaux) VALUES(?, ?, ?) ";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
		 
			stmt.setObject( 1, equipe.getPseudo());
			stmt.setObject( 2, equipe.getNombreMembre());
			stmt.setObject( 3, equipe.getLienTravaux());  
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			equipe.setIdEquipe(rs.getObject( 1, Integer.class));
			 
			return equipe.getIdEquipe();
	
		} catch ( SQLException e ) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
 
 

 
	 
		
	public void supprimer( int idEquipe ) {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "DELETE FROM equipe WHERE idEquipe = ? ";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, idEquipe ); 
			stmt.executeUpdate(); 
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
	
	
	public Equipe retrouver( int idEquipe ) {

		Connection			cn 		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM equipe WHERE idEquipe = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject(1,idEquipe);
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				return construireEquipe( rs );
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	public void modifier( Equipe equipe)  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Modifie le personne
			sql = "UPDATE equipe SET idEquipe = ?, pseudo = ?, nombreMembre = ?, lienTravaux = ? WHERE idEquipe =  ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, equipe.getIdEquipe() );
			stmt.setObject( 2, equipe.getPseudo() );
			stmt.setObject( 3, equipe.getNombreMembre() );
			stmt.setObject( 4, equipe.getLienTravaux() );
			stmt.setObject( 5, equipe.getIdEquipe() );
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}

		// Modifie les telephones
		//daoTelephone.modifierPourPersonne( personne );
	}
	 
	// Méthodes auxiliaires
	
	protected Equipe construireEquipe( ResultSet rs ) throws SQLException {
		Equipe equipe = new Equipe(); 
		
		equipe.setIdEquipe(rs.getObject( "idEquipe", Integer.class ));
		equipe.setPseudo(rs.getObject( "pseudo", String.class ));
		equipe.setNombreMembre(rs.getObject( "nombreMembre",Integer.class ));
		equipe.setLienTravaux(rs.getObject( "lienTravaux", String.class ));
	  
		return equipe;
	}
	
}
