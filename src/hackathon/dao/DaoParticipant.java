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
import hackathon.data.Participant;
import jfox.jdbc.UtilJdbc;

public class DaoParticipant {
	
	//constructeur
	public DaoParticipant() {
		
	}
	
	//champs 
	@Inject
	private DataSource dataSource ;
	@Inject
	private DaoPersonne daoPersonne ;
	@Inject
	private DaoEquipe daoEquipe  ;
	
	//actions
	public List<Participant> listerTout() {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM participant";
			stmt = cn.prepareStatement( sql ); 
			rs = stmt.executeQuery();

			List<Participant> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add( construireParticipant( rs ) );
			}
			return liste;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	public List<Participant> listerTout(int id) {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM participant WHERE idEquipe=?";
			stmt = cn.prepareStatement( sql ); 
			stmt.setObject(1, id);
			rs = stmt.executeQuery();

			List<Participant> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add( construireParticipant( rs ) );
			}
			return liste;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	public int inserer ( Participant participant ) {
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			
			//Tous les participants ont un compte commun
			participant.getPersonne().getCompte().setId(0);
			
			//Insertion de la personne correspondante 
			daoPersonne.inserer( participant.getPersonne() );
			
			cn = dataSource.getConnection();
			sql = "INSERT INTO participant ( id, idEquipe,specialite ) VALUES( ?, ?, ? ) ";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			
			stmt.setObject( 1, participant.getPersonne().getId() );
			stmt.setObject( 2, participant.getEquipe().getIdEquipe() );
			stmt.setObject( 3, participant.getSpecialite());
			
			//Insertion de la personne precedente dans la table des superUser
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			
			//participant.setId( rs.getObject( 1, Integer.class) );
			participant.getPersonne().setId( rs.getObject(1, Integer.class) );
			return participant.getPersonne().getId();
	
		} catch ( SQLException e ) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}

	public void modifier ( Participant participant ) {
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			
			cn = dataSource.getConnection();
			sql = "UPDATE participant SET idEquipe = ? ,specialite = ? WHERE id = ?";
			stmt = cn.prepareStatement( sql );
			
			stmt.setObject( 1, participant.getEquipe().getIdEquipe() );
			stmt.setObject( 2, participant.getSpecialite());
			stmt.setObject( 3, participant.getPersonne().getId() );			
			stmt.executeUpdate();
			
			//Insertion de la personne correspondante 
			daoPersonne.modifier( participant.getPersonne() );

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
			sql = "DELETE FROM participant WHERE id = ? ";
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
	
	
	public Participant retrouver( int id ) {

		Connection			cn 		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM participant WHERE  id=?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject(1, id);
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				return construireParticipant( rs );
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
	
	protected Participant construireParticipant( ResultSet rs ) throws SQLException {
		Participant participant = new Participant();
		
		participant.setEquipe(daoEquipe.retrouver(rs.getObject("idEquipe", Integer.class)));
		participant.setPersonne(daoPersonne.retrouver(rs.getObject( "id", Integer.class ) ));
		participant.setSpecialite(rs.getObject( "specialite", String.class ));
 
		return participant;
	}

	public void supprimerPourEquipe(Integer idEquipe) {
		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			//On recupere tous les membres de l'equipe 
			sql = "SELECT * FROM participant WHERE idEquipe = ? ";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, idEquipe );
			
			rs = stmt.executeQuery();
			
//			On supprime chacun de ces occurences
			while (rs.next()) {
				supprimer(rs.getObject("id", Integer.class));
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
	
	public int compterPourEquipe(Equipe equipe) {
		Connection			cn		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;

		try {
			cn = dataSource.getConnection();
            String sql = "SELECT COUNT(*) FROM participant WHERE idEquipe = ?";
            stmt = cn.prepareStatement( sql );
            stmt.setObject( 1, equipe.getIdEquipe() );
            rs = stmt.executeQuery();

            rs.next();
            return rs.getInt( 1 );

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
}
