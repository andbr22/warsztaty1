import java.util.Scanner;

public class Guess2 {

	public static void main(String[] args) {
		guessing();
	}

	static void guessing() {
		System.out.println("Wybierz liczbę od 1-1000 a ja ją zgadnę");
		int min = 0, max = 1001, counter = 0;
		Scanner scan = new Scanner(System.in);
		String helpText = "";
		while (counter<10) {
			System.out.println(min+" "+max);
			int number = (max - min)/2+min;
			System.out.println("Czy ta liczba to: " + number);
			System.out.println("(t)rafione");
			System.out.println("(m)niej");
			System.out.println("(w)ięcej");
			helpText = scan.nextLine();
			if (helpText.equalsIgnoreCase("t")) {
				System.out.println("To świetnie");
				break;
			} else if (helpText.equalsIgnoreCase("m")) {
				max = number;
				counter++;
			} else if (helpText.equalsIgnoreCase("w")) {
				min = number;
				counter++;
			} else {
				System.out.println("To nie jest poprawna opcja");
			}
			
			if (counter==10){
				System.out.println("Zapomniałeś swojej liczby \n");
			}
		}

	}

}
