package test.git;

public class Test_NTYJ {

	public void bonjour() {
		System.out.println("Bonjour Yann");
	}

	private String[] adresses = { "14 Rue Mozart, Paris", "77 Rue Picasso, Toulouse", "53 Rue des fleurs, Limoges", };

	public String getAdresse(int i) {

		if (0 <= i && i < adresses.length) {
			return adresses[i];
		} else {
			return null;
		}
	}
}