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

import jfox.jdbc.UtilJdbc;
import hackathon.data.Compte;
import hackathon.data.Hackathon;
import hackathon.data.Personne;


public class DaoPersonne {

	
	// Champs

	@Inject
	private DataSource		dataSource;
	@Inject
	private DaoCompte daoCompte;

	
	// Actions

	public int inserer( Personne personne )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			
			//insertion du compte
			//daoCompte.inserer( personne.getCompte() );
			
			// Insère le personne
			sql = "INSERT INTO personne ( nom, prenom, idcompte ) VALUES ( ?, ?, ? )";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS  );
			//stmt.setObject(	1, personne.getId() );
			stmt.setObject(	1, personne.getNom() );
			stmt.setObject(	2, personne.getPrenom() );
			stmt.setObject(	3, personne.getCompte().getId() );
			stmt.executeUpdate();
			
			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			personne.setId( rs.getObject( 1, Integer.class ) );
	
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
		
		// Retourne l'identifiant
		return personne.getId();
	}

	
	public void modifier( Personne personne )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Modifie le personne
			sql = "UPDATE personne SET id = ?, nom = ?, prenom = ? WHERE id =  ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, personne.getId() );
			stmt.setObject( 2, personne.getNom() );
			stmt.setObject( 3, personne.getPrenom() );
			stmt.setObject( 4, personne.getId() );
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}

	}

	
	public void supprimer( int id )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Supprime le personne
			sql = "DELETE FROM personne WHERE id = ? ";
			stmt = cn.prepareStatement(sql);
			stmt.setObject( 1, id );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}

	
	public Personne retrouver( int id )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM personne WHERE id = ?";
            stmt = cn.prepareStatement(sql);
            stmt.setObject( 1, id);
            rs = stmt.executeQuery();

            if ( rs.next() ) {
                return construirePersonne( rs );
            } else {
            	return null;
            }
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}

	
	public List<Personne> listerTout()   {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM personne ORDER BY nom, prenom";
			stmt = cn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			List<Personne> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add( construirePersonne( rs ) );
			}
			return liste;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}

   	
	// Méthodes auxiliaires
	
    protected Personne construirePersonne( ResultSet rs ) throws SQLException {

		Personne personne = new Personne();
		personne.setId(rs.getObject( "id", Integer.class ));
		personne.setNom(rs.getObject( "nom", String.class ));
		personne.setPrenom(rs.getObject( "prenom", String.class ));
		personne.setCompte(  daoCompte.retrouver( rs.getObject( "idcompte", Integer.class ) )  );
		
		return personne;
	}
    
    public List<Personne> listerToutPourHackathon(Hackathon hackathon)   {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM personne ORDER BY nom, prenom ";
			stmt = cn.prepareStatement(sql);
			//stmt.setObject( 1, hackathon.getIdHackathon());
			rs = stmt.executeQuery();
			
			List<Personne> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add( construirePersonne( rs ) );
			}
			return liste;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
    
  //Méthode qui ne nous servent à rien mais sont conservés pour des raisons d'intégrités 
    public int compterPourCategorie( int idCategorie ) {
    	
		Connection			cn		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;

		try {
			cn = dataSource.getConnection();
            String sql = "SELECT COUNT(*) FROM personne WHERE idcategorie = ?";
            stmt = cn.prepareStatement( sql );
            stmt.setObject( 1, idCategorie );
            rs = stmt.executeQuery();

            rs.next();
            return rs.getInt( 1 );

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
    }
    
    public List<Personne> listerPourCompte(Compte compte)   {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM personne WHERE idcompte=? ORDER BY nom, prenom";
			stmt = cn.prepareStatement(sql);
			stmt.setObject(1, compte.getId());
			rs = stmt.executeQuery();
			
			List<Personne> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add( construirePersonne( rs ) );
			}
			return liste;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}


	public List<Personne> listerAdmin() {
		List<Compte> comptes = new ArrayList<>();
		List<Personne> admins = new ArrayList<>();
		
		comptes  = daoCompte.listerCompteAdmin();
		
		for(Compte c: comptes) {
			admins.addAll( listerPourCompte(c) );
		}
		return admins;
	}	
}
