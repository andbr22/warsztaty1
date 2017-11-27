import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PopularWords {

	public static void main(String[] args) {
		popular("http://www.onet.pl/", true);
		popular("http://www.gazeta.pl/", false);
		//popular("http://wiadomosci.wp.pl/", false);
		mostPopular();
	}
	
	//Kod wpisuje wszystkie znalezione słowa z tytułów pod url do pliku
	//url - string - miejsce z którego ściągamy dane
	//newStat - boolean - flaga oznaczająca czy robimy zupełnie nową statystykę(w przypadku false rozszerzamy obecnie istniejącą)
	public static void popular(String url, boolean newStat){
		Connection connect = Jsoup.connect(url);
		try {
			//tworzymy plik o ile nie istnieje
			Path path = Paths.get("popular_words.txt");
			if(!Files.exists(path))Files.createFile(path);
			//
			ArrayList<String> titles = new ArrayList<>();
			
		    Document document = connect.get();
		    Elements links = document.select("span.title");
		    for (Element elem : links) {
		        String helper =elem.text();
		        //helper = helper.replaceAll("\\.|,|\"|\'|:|;|-|!|\\?|\\[|\\]|\\(|\\)|\\||–|„|/|[0-9]", "");
		        titles.add(helper);
		        //System.out.println(helper);
		    }
		    
		    if (newStat) Files.write(path, titles);
		    else Files.write(path, titles, StandardOpenOption.APPEND);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	//Kod wyszukujący najbardziej popularne słowa z pliku
	public static void mostPopular(){
		Path pathP = Paths.get("popular_words.txt");
		
		HashMap<String,Integer> mapa = new HashMap<>();
		try {
			for(String wpis: Files.readAllLines(pathP)){
				//pozbywamy się znaków specjalnych i cyfr, oraz dzielimy tekst na słowa
				wpis = wpis.replaceAll("\\.|,|\"|\'|:|;|-|!|\\?|\\[|\\]|\\(|\\)|\\||–|„|/|[0-9]", "");
				String[] wpisArr = wpis.split("\\s");
				for(String word:wpisArr){
					//pozbywamy się nadmiarowych spacji, przerabiamy słowa na małe litery, nie liczymy słów krószych niż 4
					word = word.trim();
					word = word.toLowerCase();
					if(word.length()>3){
						if(mapa.containsKey(word)){
							//jeżeli słowo istnieje to zastępujemy je tym samym słowem o wartości +1
							mapa.replace(word, mapa.get(word), mapa.get(word)+1);
						}else{
							//jeżeli słowo nie istniej w mapie to tworzymy nowe słowo o wartości 1
							mapa.put(word, 1);
						}
					}
				}
			}
			//System.out.println(mapa.toString());
			//System.out.println(mapa.get("jest"));
			
			//*** WYBÓR 10 NAJPOPULARNIEJSZYCH SŁÓW***
			//tworzymy 2 listy jedną z wartościami  i jedną ze słowami
			ArrayList<Integer> valueList = new ArrayList<>();
			ArrayList<String> keyList = new ArrayList<>();
			for(HashMap.Entry<String, Integer> entry:mapa.entrySet()){
				//sprawdzamy wszystkie słowa w mapie i pobieramy ich klucze (słowo) i wartość (ilość wystąpień)
				String key = entry.getKey();
				Integer value = entry.getValue();
				//Ignorujemy słowa które pojawiły się 1 raz, lub są słowami zakazanymi
				if(value>1 && !banned(key)){
						// iterujemy tylko pierwsze 10 słów! bo więcej nam nie potrzeba
						//Uwaga: w normalnych okolicznościach ten for by się wywalił prze '<='
						for(int i = 0; i<=valueList.size() && i<10;i++ ){
							//Jeżeli tablica jest pusta, lub wstawiamy ostatni element w granicy od 0 do 10;
							// w odpowiednie miejsce w siostrzanej liscie wstawiamy słowo o tej wartości
							// po elemencie 10 wrzucamy i nie sortujemy
							//* ten warunek jako pierwszy powoduje, że pętla się nie wysypie
							if(i==valueList.size()){
								valueList.add(value);
								keyList.add(key);
								break;
							}
							//jeżeli element w liście jest mniejszy od nowego elementu to na jego miejsce wstawiamy nową wartość 
							// w odpowiednie miejsce w siostrzanej liscie wstawiamy słowo o tej wartości
							// jeżeli znaleźliśmy takie miejsce to kończymy działanie pętli for (bo już wstawiliśmy element w listę)
							if(valueList.get(i)<value){
								valueList.add(i, value);
								keyList.add(i,key);
								//System.out.print(value);
								//System.out.println(valueList.toString());
								//System.out.print(key);
								//System.out.println(keyList.toString());
								break;
							}
						}
					
				}
			}
			//System.out.println(valueList.toString());
			//System.out.println(keyList.toString());
			
			Path pathOut = Paths.get("most_popular_words.txt");
			if(!Files.exists(pathOut)) Files.createFile(pathOut);
			ArrayList<String> popularWords = new ArrayList<>();
			for(int i = 0; i<keyList.size() && i<10;i++ ){
				popularWords.add(keyList.get(i));
			}
			Files.write(pathOut, popularWords);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static boolean banned(String str){
		//Lista zbanowanych słów
		String[] bannedWords = {"jest","może","przez","będzie","mają"};
		for(String word : bannedWords){
			if(word.equals(str))return true;
		}
		return false;
	}
}
