package hackathon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import jfox.jdbc.UtilJdbc;
import hackathon.data.Compte;


public class DaoRole {

	
	// Champs

	@Inject
	private DataSource		dataSource;

	
	// Actions

	public void insererPourCompte( Compte compte )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO role (idcompte, role) VALUES (?,?)";
			stmt = cn.prepareStatement( sql );
			for( String role : compte.getRoles() ) {
				stmt.setObject( 1, compte.getId() );
				stmt.setObject( 2, role );
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}

	
	public void supprimerPourCompte( int idCompte ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Supprime les roles
			sql = "DELETE FROM role  WHERE idcompte = ? ";
			stmt = cn.prepareStatement(sql);
			stmt.setObject( 1, idCompte );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}


	public List<String> listerPourCompte( Compte compte ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM role WHERE idcompte = ? ORDER BY role";
			stmt = cn.prepareStatement(sql);
			stmt.setObject( 1, compte.getId() );
			rs = stmt.executeQuery();

			List<String> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add( rs.getObject("role", String.class) );
			}
			return liste;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	public List<Integer> listerPourAdmin( ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT idcompte FROM role WHERE role <> 'UTILISATEUR'  ORDER BY idcompte";
			stmt = cn.prepareStatement(sql);
			rs = stmt.executeQuery();

			List<Integer> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add( rs.getObject("idcompte", Integer.class) );
			}
			return liste;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}

}
