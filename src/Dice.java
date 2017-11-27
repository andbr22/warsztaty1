import java.util.Random;

public class Dice {

	public static void main(String[] args) {
		rzut("10D10+10");

	}
	
	static int rzut(String opis){
		//xDy+z
		
		//wyznaczamy x
		int dIndex = opis.indexOf('D');
		int x = 0;
		if(dIndex == -1){
			System.out.println("Niepoprawne wejście");
			return 0;
		}
		if(dIndex==0) x=1;
		else{
			try{
				String helperS = opis.substring(0, dIndex);
				//System.out.println(helperS);
				x = Integer.parseInt(helperS);
				//System.out.println(x);
			}catch(NumberFormatException e){
				System.out.println("Niepoprawne wejście x");
				return 0;
			}
		}
		
		//Znajdujemy z
		int pIndex = opis.length();
		int z = 0;
		int ptype = 0; // '+' ,czy '-'
		if(opis.contains("+")){
			ptype = 1;
			pIndex = opis.indexOf('+');
		}else if(opis.contains("-")){
			ptype = -1;
			pIndex = opis.indexOf('-');
		}
		if(pIndex<opis.length()){
			try{
				String helperS = opis.substring(pIndex, opis.length());
				z = ptype*Integer.parseInt(helperS);
			}catch(NumberFormatException e){
				System.out.println("Niepoprawne wejście z");
				return 0;
			}
		}
		
		
		//Znajdujemy y (posiłkujemy się pIndex i dIndex)
		int y = 0;
		try{
			String helperS = opis.substring(dIndex+1,pIndex);
			y = Integer.parseInt(helperS);
		}catch(NumberFormatException e){
			System.out.println("Niepoprawne wejście y");
			return 0;
		}
		//tylko zidentyfikowane typy kości do gry
		int[] arr = {3,4,6,8,10,12,20,100};
		if(!contains(arr,y)){
			System.out.println("Niepoprawny rodzaj kości");
			return 0;
		}
		
		//System.out.println(x);
		//System.out.println(y);
		//System.out.println(z);
		
		//rzucamy kośćmi
		Random rand = new Random();
		int result = z;
		for (int i=0; i<x;i++){
			result += 1+rand.nextInt(y);
		}
		
		System.out.println(result);
		return result;
	}
	
	static boolean contains(int[] arr, int n) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == n)
				return true;
		}
		return false;
	}
}
