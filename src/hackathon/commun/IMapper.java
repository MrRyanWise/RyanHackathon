package hackathon.commun;

import org.mapstruct.Mapper;  
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget; 
import hackathon.data.Compte;
import hackathon.data.Equipe;
import hackathon.data.Evaluer;
import hackathon.data.Hackathon;
import hackathon.data.Participant;
import hackathon.data.Personne;

@Mapper
public interface IMapper {

	Compte update(@MappingTarget Compte target, Compte source);
	
	@Mapping (target = "compte", expression = "java( source.getCompte() )")
	Personne update(@MappingTarget Personne target, Personne source);
	
	Equipe update( @MappingTarget Equipe target, Equipe source );
	
	
	@Mapping(target = "superUser", expression = "java( source.getSuperUser() )")
	Hackathon update(@MappingTarget Hackathon target, Hackathon source);
		
	
	@Mapping (target = "personne", expression = "java( source.getPersonne() )")
	@Mapping(target = "equipe", expression = "java( source.getEquipe() )")
	Participant update(@MappingTarget Participant target, Participant source);	
	
	
	@Mapping (target = "correcteur", expression = "java( source.getCorrecteur() )")
	@Mapping (target = "equipe", expression = "java( source.getEquipe() )")
	Evaluer update(@MappingTarget Evaluer target, Evaluer source);
	
	
	//@Mapping(target = "jury", expression = "java( source.getJury()  )")
	 
	
	//@Mapping(target = "equipe", expression = "java( source.getEquipe() )")
	//Jury update(@MappingTarget Jury target, Jury source);
	 
}