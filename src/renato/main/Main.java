package renato.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String key;
		System.out.println("Enter Search Word:");
		key=(scanner.next());
		scanner.close();
		HashMap<Integer, String> myLinks = Search.searchFromString(key);
		ArrayList<Thread> threads = new ArrayList<Thread>();

		for (Map.Entry<Integer, String> entry: myLinks.entrySet()) {
			Search mySearch = new Search(entry.getValue());
			Thread thread = new Thread(mySearch);
			thread.start();
			threads.add(thread);
		}

		for (int i = 0; i < threads.size(); i++) {
			  try {
				((Thread) threads.get(i)).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Search.getLibs(5);
	}
}
