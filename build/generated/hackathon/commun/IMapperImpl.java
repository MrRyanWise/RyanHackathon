package hackathon.commun;

import hackathon.data.Compte;
import hackathon.data.Equipe;
import hackathon.data.Evaluer;
import hackathon.data.Hackathon;
import hackathon.data.Participant;
import hackathon.data.Personne;
import javafx.collections.ObservableList;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-06-05T12:23:02+0200",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.50.v20210914-1429, environment: Java 17.0.2 (Eclipse Adoptium)"
)
public class IMapperImpl implements IMapper {

    @Override
    public Compte update(Compte target, Compte source) {
        if ( source == null ) {
            return null;
        }

        target.setEmail( source.getEmail() );
        target.setId( source.getId() );
        target.setMotDePasse( source.getMotDePasse() );
        target.setPseudo( source.getPseudo() );
        if ( target.getRoles() != null ) {
            target.getRoles().clear();
            ObservableList<String> observableList = source.getRoles();
            if ( observableList != null ) {
                target.getRoles().addAll( observableList );
            }
        }

        return target;
    }

    @Override
    public Personne update(Personne target, Personne source) {
        if ( source == null ) {
            return null;
        }

        target.setCourriel( source.getCourriel() );
        target.setId( source.getId() );
        target.setMail( source.getMail() );
        target.setNom( source.getNom() );
        target.setPrenom( source.getPrenom() );

        target.setCompte( source.getCompte() );

        return target;
    }

    @Override
    public Equipe update(Equipe target, Equipe source) {
        if ( source == null ) {
            return null;
        }

        target.setIdEquipe( source.getIdEquipe() );
        target.setPseudo( source.getPseudo() );
        target.setNombreMembre( source.getNombreMembre() );
        target.setLienTravaux( source.getLienTravaux() );

        return target;
    }

    @Override
    public Hackathon update(Hackathon target, Hackathon source) {
        if ( source == null ) {
            return null;
        }

        target.setIdHackathon( source.getIdHackathon() );
        target.setNom( source.getNom() );
        target.setTheme( source.getTheme() );
        target.setProblematique( source.getProblematique() );
        target.setDescription( source.getDescription() );
        target.setLieu( source.getLieu() );
        target.setNbJury( source.getNbJury() );
        target.setMinEquipe( source.getMinEquipe() );
        target.setMaxEquipe( source.getMaxEquipe() );
        target.setDateDebut( source.getDateDebut() );
        target.setDateFin( source.getDateFin() );
        target.setHeureDebut( source.getHeureDebut() );
        target.setHeureFin( source.getHeureFin() );

        target.setSuperUser( source.getSuperUser() );

        return target;
    }

    @Override
    public Participant update(Participant target, Participant source) {
        if ( source == null ) {
            return null;
        }

        target.setSpecialite( source.getSpecialite() );

        target.setPersonne( source.getPersonne() );
        target.setEquipe( source.getEquipe() );

        return target;
    }

    @Override
    public Evaluer update(Evaluer target, Evaluer source) {
        if ( source == null ) {
            return null;
        }

        target.setIdCorrecteur( source.getIdCorrecteur() );
        target.setIdEquipe( source.getIdEquipe() );
        target.setNote( source.getNote() );

        target.setCorrecteur( source.getCorrecteur() );
        target.setEquipe( source.getEquipe() );

        return target;
    }
}
