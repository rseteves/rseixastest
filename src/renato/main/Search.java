package renato.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author rseixas
 *
 */
public class Search implements Runnable {

	String link;
	private static final HashMap <String, Integer> libs = new HashMap<String, Integer>();

	public Search(String link) {
		this.link = link;
	}

	// Google Custom API Key created in my google account
	private final static String token = "AIzaSyCUXYhrJZerFvBacg3yVYKrABtMSh7Tijg";


	/**Use Google API make a search result and store in a Hash Map
	 * @param key: key to search
	 * @return HashMap with 5 first links from Google Search
	 */
	public static HashMap<Integer, String> searchFromString(String key) {
		URL url;
		HashMap<Integer, String> output = null;
		try {
			// Make the URL Call and get resultus with Http connection
			url = new URL("https://www.googleapis.com/customsearch/v1?key=" + token
					+ "&cx=013036536707430787589:_pqjad5hr1a&q=" + key + "&alt=json");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			// Interation variable
			output = new HashMap<Integer, String>();
			String linkTemp;
			int count = 0;
			// Interate to search link tag and prepare the return HashMap
			while ((linkTemp = br.readLine()) != null) {

				if (linkTemp.contains("\"link\": \"")) {
					String link = linkTemp.substring(linkTemp.indexOf("\"link\": \"") + ("\"link\": \"").length(),
							linkTemp.indexOf("\","));
					output.put(count, link);
					count++;
				}
			}
			conn.disconnect();
		} catch (IOException e) {
			// Some problem to call google or passed the 100 calls per day
			System.out.print("Bad Request.");
			e.printStackTrace();
		}
		return output;
	}

	/** Return all <Script> tag from a URL.
	 * This method is incomplete, need to filter the JS lib inside the <Script> tag.
	 * So I decided just to store the <Script> tag
	 * @param link: URL to get the HTML
	 */
	public static void getPage(String link) {
		Document doc;
		try {
			doc = Jsoup.connect(link).get();
			//Just return one element - Head
			Elements headSet = (Elements) doc.getElementsByTag("head");
			Element head = headSet.first();
			Elements headContent = head.getElementsByTag("script");
			for (Element el : headContent) {
				String src = el.attr("src");
				if (!src.isEmpty()) {
					if (libs.containsKey(src)) {
						int qtt = libs.get(src) +1;
						libs.put(src, qtt);
					}else {
						libs.put(src, 1);
					}
				}
			}

		} catch (IOException e) {
			// Some problem to call google or passed the 100 calls per day
			System.out.print("Bad Request.");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		getPage(this.link);

	}

	/**order the map and print the top used libs
	 * @param records: how many top libs want to print
	 */
	public static void getLibs(int records){

		ArrayList<Entry<String, Integer>> list = new ArrayList<>(libs.entrySet());
		list.sort(Entry.comparingByValue());
		Collections.reverse(list);

		for (int i=0;i<list.size() && i<records;i++) {
			Entry<String, Integer> entry = list.get(i);
			System.out.println("Key : " + entry.getKey()+ " Value : " + entry.getValue());
		}
	}


}
