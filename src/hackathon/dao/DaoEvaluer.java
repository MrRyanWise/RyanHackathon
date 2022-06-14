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
import hackathon.data.Correcteur;
import hackathon.data.Equipe;
import hackathon.data.Evaluer;
import jfox.jdbc.UtilJdbc;

public class DaoEvaluer {
	
	//constructeur
	public DaoEvaluer() {
	}
	
	//champs 
	@Inject
	private DataSource dataSource;
	@Inject
	private DaoCorrecteur daoCorrecteur;
	@Inject
	private DaoEquipe daoEquipe;
	 
	//actions 
  
	public int inserer_note ( Equipe equipe, Correcteur correcteur , int Note ) {
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			
			
			cn = dataSource.getConnection();
			sql = "INSERT INTO evaluer ( idEquipe , id , note ) VALUES( ? , ? , ? ) ";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
 
			stmt.setObject( 1, equipe.getIdEquipe() ); 
			stmt.setObject( 2, correcteur.getId() ); 
			stmt.setObject( 3, Note ); 
			
			//Insertion de la personne precedente dans la table des superUser
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			 
			return Note;
	
		} catch ( SQLException e ) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	public void modifier_note( Equipe equipe, Correcteur correcteur, int note )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Modifie le personne
			sql = "UPDATE evaluer SET note = ? WHERE id =  ? AND idEquipe = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, note );
			stmt.setObject( 2, correcteur.getId() );
			stmt.setObject( 3, equipe.getIdEquipe() );
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}

	}
	
	public List<Evaluer> listerTout(Correcteur correcteur) {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM evaluer where id=? order by idequipe ";
			stmt = cn.prepareStatement( sql ); 
			stmt.setObject(1, correcteur.getId());
			rs = stmt.executeQuery();

			List<Evaluer> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add( construireEvaluer( rs ) );
			}
			return liste;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
 
 
	public Integer Retrouver_Note_pourJury(Equipe equipe, Correcteur correcteur) {
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;
	
		try {
			
			
			cn = dataSource.getConnection();
			sql = "SELECT note FROM evaluer WHERE  idequipe=? AND id=?"; 
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
	
			stmt.setObject( 1,equipe.getIdEquipe());  
			stmt.setObject( 2,correcteur.getId());  
			
			//Insertion de la personne precedente dans la table des superUser
			rs = stmt.executeQuery();
	
			if ( rs.next() ) {
				return rs.getObject("note", Integer.class);
            } else {
            	return  null;
            }
	
		} catch ( SQLException e ) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
		
	}
	 

	public Evaluer retrouver( int id ) {

		Connection			cn 		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT note FROM evaluer WHERE  idequipe=?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject(1, id);
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				return construireEvaluer( rs );
			} else {
				return null;
			}
		} catch (SQLException e) {
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
			sql = "DELETE FROM evaluer WHERE id = ? ";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, id );
			
			//suppression de la personne dans la table des superUsers
			stmt.executeUpdate();
			
			//suppression de la personne dans la table des personnes
			//daoPersonne.supprimer(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}

	// 1) Méthode pour fournir la moyenne des notes(idEquipe)
	// 2) Lister notes qui donne toutes les notes des equipes avec les correcteurs : format correcteur : note
	
	public List<Evaluer> Classement() {
		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;
		String				sql;
		
		try {
			cn = dataSource.getConnection();
			sql = "select idequipe, avg(note) as Notes\r\n"
					+ "from evaluer\r\n"
					+ "group by idequipe  \r\n"
					+ "order by Notes  desc ; ";
			stmt = cn.prepareStatement( sql );
			rs = stmt.executeQuery();
 
			List<Evaluer> liste = new ArrayList<>();
			
			while (rs.next()) {
				liste.add( construireEvaluer( rs ) );
			}
			return liste;
			
		}catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
 
 
	
	
	// Méthodes auxiliaires
	
	protected Evaluer construireEvaluer( ResultSet rs ) throws SQLException {
		Evaluer evaluer = new Evaluer();
		
		//Correcteur correcteur = daoCorrecteur.construireCorrecteur( rs ) ;
		Equipe equipe = daoEquipe.retrouver(rs.getObject("idequipe", Integer.class));
		Correcteur correcteur = daoCorrecteur.retrouver(rs.getObject("id", Integer.class));
		evaluer.setNote(rs.getObject("note", Integer.class));
		evaluer.setCorrecteur(correcteur);
		evaluer.setEquipe(equipe);
		return evaluer;
	}
	 
}
