package hackathon.dao;

//import java.sql.Connection; 
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//import javax.inject.Inject;
//import javax.sql.DataSource;
//
//import hackathon.data.Equipe;
//import hackathon.data.Participant;
//import hackathon.data.Personne; 
//import jfox.jdbc.UtilJdbc;

public class DaoComposer {
	
	//constructeur
	public DaoComposer() {
		
	}
	/*
	//champs 
	@Inject
	private DataSource dataSource ;
	@Inject
	private DaoPersonne daoPersonne ;
	@Inject
	private DaoEquipe daoEquipe  ;
	
	//actions
	public int inserer ( Participant participant ) {
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO superUser ( id, idEquipe,specialite ) VALUES( ?, ?, ? ) ";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			
			stmt.setObject( 1, participant.getIdPersonne());
			stmt.setObject( 2, participant.getIdEquipe());
			stmt.setObject( 3, participant.getSpecialite());
			
			//Insertion de la personne correspondante 
			daoPersonne.inserer( participant.getPersonne() );
			
			//Insertion de la personne precedente dans la table des superUser
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			participant.setId( rs.getObject( 1, Integer.class) );
			return participant.getIdPersonne();
	
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
			sql = "SELECT * FROM personne INNER JOIN participant ON personne.id = participant.id WHERE personne.idcompte = ?";
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

	// cette fonction permet de retrouver le super user grâce a son mot de passe
	public Participant retrouver( String mdp  ) {

		Connection			cn 		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM participant WHERE cle = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject(1, mdp);
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
		Personne personne = daoPersonne.construirePersonne( rs ) ;	
		Equipe equipe = daoEquipe.construireEquipe( rs ) ;
		
		participant.setSpecialite(rs.getObject("specialite", String.class ));
		participant.setPersonne(personne);
		return participant;
	}*/
	
}
