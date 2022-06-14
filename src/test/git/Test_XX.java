package test.git;

public class Test_XX {
	
	
	public void bonjour() {
		System.out.println( "Bonjoour" );
	}
	
	
	private String[] adresses = {
			"14 Rue Mozart, Paris",
			"77 Rue Picasso, Toulouse", 
			"53 Rue des fleurs, Limoges",
	};

	
	public String getAdresse( int i ) {

		if ( 0 <= i && i < adresses.length ) {
			return adresses[i];
		} else {
			return null;
		}
	}
	
}
