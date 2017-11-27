import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Lotto {
	public static void main(String[] args) {
		int[] moje = wybierz();
		int[] wylosowane = losuj();
		System.out.println("Twoje liczby");
		System.out.println(Arrays.toString(moje));
		System.out.println("\nWylosowane liczby");
		System.out.println(Arrays.toString(wylosowane));
		porównaj(moje, wylosowane);
	}

	static int[] wybierz() {
		Scanner scan = new Scanner(System.in);
		int[] result = new int[6];
		for (int i = 0; i < 6; i++) {
			int helperInt = 0;
			System.out.println((i + 1) + " liczba (od 1 do 49):");
			while (true) {
				String helperText = scan.nextLine();
				try {
					helperInt = Integer.parseInt(helperText);
					if ((helperInt > 49) || (helperInt < 1)) {
						System.out.println("Liczba poza zakresem!");
					} else if (contains(result, helperInt)) {
						System.out.println("Już podałeś liczbę!");
					} else {
						result[i] = helperInt;
						break;
					}
				} catch (NumberFormatException e) {
					System.out.println("To nie liczba!");
				}
			}
			result[i] = helperInt;
		}
		return result;
	}

	static int[] losuj() {
		int[] result = new int[6];
		Random rand = new Random();
		for (int i=0; i < result.length; i++) {
			while(true){
				int number = 1+rand.nextInt(49);
				if (!contains(result,number)){
					result[i] = number;
					break;
				}
			}
		}
		return result;
	}

	static void porównaj(int[] wybrane, int[] wylosowane) {
		int counter = 0;
		for(int n : wybrane){
			if(contains(wylosowane, n)) counter++;
		}
		System.out.println("\n\nTrafiłeś "+counter+" liczb.");
		if(counter<3){
			System.out.println("Nic nie wygrałeś...");
		}else{
			System.out.println("Coś wygrałeś!");
		}
	}

	static boolean contains(int[] arr, int n) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == n)
				return true;
		}
		return false;
	}

}
