package hackathon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import hackathon.data.Hackathon;
import hackathon.data.SuperUser;
import jfox.jdbc.UtilJdbc;

public class DaoHackathon {

	@Inject
	private DataSource dataSource;
	@Inject 
	private DaoSuperUser daoSuperUser;
	

	// Actions
	public int inserer(Hackathon hackathon) {

		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;
  
		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO hackathon (nomHackathon, theme, problematique, description, lieu, nombreParJury, nombreMinEquipe, nombreMaxEquipe, idSuperUser, dateDebut, heureDebut  ) VALUES( ?,?,?,?,?,?,?,?,?,?,? ) ";
			stmt = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			//stmt.setObject(1, hackathon.getIdHackathon());
			stmt.setObject(1, hackathon.getNom());
			stmt.setObject(2, hackathon.getTheme());
			stmt.setObject(3, hackathon.getProblematique());
			stmt.setObject(4, hackathon.getDescription());
			stmt.setObject(5, hackathon.getLieu());
			stmt.setObject(6, hackathon.getNbJury());
			stmt.setObject(7, hackathon.getMinEquipe());
			stmt.setObject(8, hackathon.getMaxEquipe());
//			stmt.setObject(11, hackathon.getHeureDebut());
//			stmt.setObject(12, hackathon.getHeureFin());
			stmt.setObject(9, hackathon.getSuperUser().getId());
			stmt.setObject(10, hackathon.getDateDebut());
			stmt.setObject(11, hackathon.getDateFin());
			
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			hackathon.setIdHackathon(rs.getObject(1, Integer.class));
			return hackathon.getIdHackathon();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(rs, stmt, cn);
		}
	}
	
	
	public void supprimer( int idHackathon ) {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "DELETE FROM hackathon WHERE idHackathon= ? ";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, idHackathon);
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
	
	//la fonction lister tout ne permet que de lister les hackathons pour un seul utilisateur
	public List<Hackathon> listerTout( SuperUser superUser) {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM hackathon WHERE idSuperUser=? ";
			stmt = cn.prepareStatement( sql );
			stmt.setObject(1, superUser.getId());
			rs = stmt.executeQuery();

			List<Hackathon> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add( construireHackathon( rs ) );
			}
			return liste;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	
	public void modifier( Hackathon hackathon) {
		// TODO Auto-generated method stub
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "UPDATE hackathon SET nomHackathon = ?, theme = ?, problematique = ?, description = ?, lieu = ?, nombreParJury = ?, nombreMinEquipe = ? , nombreMaxEquipe = ?, idSuperUser = ?, dateDebut = ?, dateFin = ?   where idHackathon = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject(1, hackathon.getNom());
			stmt.setObject(2, hackathon.getTheme());
			stmt.setObject(3, hackathon.getProblematique());
			stmt.setObject(4, hackathon.getDescription());
			stmt.setObject(5, hackathon.getLieu());
			stmt.setObject(6, hackathon.getNbJury());
			stmt.setObject(7, hackathon.getMinEquipe());
			stmt.setObject(8, hackathon.getMaxEquipe());
			stmt.setObject(9, hackathon.getSuperUser().getId());
			stmt.setObject(10, hackathon.getDateDebut());
			stmt.setObject(11, hackathon.getDateFin());
			stmt.setObject(12, hackathon.getIdHackathon());
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	
	}
	
	//cette fonction retourne entièrement un hackathon 
	public Hackathon retrouver( int idHackathon ) {

		Connection			cn 		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM hackathon WHERE idHackathon = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject(1, idHackathon);
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				return construireHackathon( rs );
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
	
	protected Hackathon construireHackathon( ResultSet rs ) throws SQLException {
		Hackathon hackathon = new Hackathon();
		hackathon.setIdHackathon( rs.getObject( "idHackathon", Integer.class ) );
		hackathon.setNom( rs.getObject( "nomHackathon", String.class ) );
		hackathon.setTheme( rs.getObject( "theme", String.class ) );
		hackathon.setProblematique( rs.getObject( "problematique", String.class ) );
		hackathon.setDescription( rs.getObject( "description", String.class ) );
		hackathon.setLieu( rs.getObject( "lieu", String.class ) );
		hackathon.setNbJury( rs.getObject( "nombreParJury", Integer.class ) );
		hackathon.setMinEquipe( rs.getObject( "nombreMinEquipe", Integer.class ) );
		hackathon.setMaxEquipe( rs.getObject( "nombreMaxEquipe", Integer.class ) );
		hackathon.setDateFin( rs.getObject( "dateFin", LocalDate.class ) );
		hackathon.setDateDebut( rs.getObject( "dateDebut", LocalDate.class ) );
//		hackathon.setHeureDebut( rs.getObject("heureDebut", Date.class) );
//		hackathon.setHeureFin( rs.getObject("heureFin", Date.class) );
		hackathon.setSuperUser( daoSuperUser.retrouver( rs.getObject("idSuperUser", Integer.class) ) );
		return hackathon;
	}
}
