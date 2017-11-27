import java.util.Random;
import java.util.Scanner;

public class GuessNumbers {

	public static void main(String[] args) {
		guess();

	}

	static void guess(){
		System.out.println("Hello!\nGuess a Number from 1 to 100:");
		Random rand = new Random();
		int numberToGuess = 1+rand.nextInt(100);
		Scanner scan = new Scanner(System.in);
		while(true){
			String text = scan.nextLine();
			try{
				int numbIn = Integer.parseInt(text);
				if(numbIn>numberToGuess){
					System.out.println("Too much");
				}else if(numbIn<numberToGuess) {
					System.out.println("Too little");
				}else{
					System.out.println("You guessed correctly");
					break;
				}
			}catch(NumberFormatException e){
				System.out.println("You know it is not a number \nGuess again:");
			}
			
			
		}
	}
}
