package alen.test.webcrawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//iterates through pages on a website and shows links
public class WebCrawler {
	
	//this is a simple hashset so we know what we visited
	static Set<String> allPages;
	
	static String website;
		
	//website structure
	static URLNode webMap;
	
	//this is just for convenience - so the user knows what is happening
	static int linksRead = 0;
	
	public static void main(String[] args) throws IOException {
		System.out.println("Start");
		
		allPages = new HashSet<String>();
		
		website = args[0];
		int maxLevel = Integer.valueOf(args[1]);
		String filePath = null;
		
		if(args.length == 3)
			filePath = args[2];
		
		crawlURL(website, maxLevel, filePath);
		
		System.out.println("END");
	}
	
	private static void crawlURL(String url, int maxlevel, String filePath) throws MalformedURLException, IOException{

		URLNode node = new URLNode(url);
		readLinks(node, maxlevel);
		
		System.out.println("Total number of links opened: " + linksRead);
		
		node.printToConsole();
		if(filePath != null)
			node.printToFile(filePath);
	}

	private static void readLinks(URLNode node, int maxLevel) throws MalformedURLException, IOException {
		URL link = new URL(node.getUrl());
        BufferedReader in = new BufferedReader(new InputStreamReader(link.openStream()));

        StringBuffer html = new StringBuffer();
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            html.append(inputLine);
        in.close();
        
        Pattern linkPattern = Pattern.compile("href=\"(.*?)\"", Pattern.CASE_INSENSITIVE|Pattern.DOTALL);        
        Matcher pageMatcher = linkPattern.matcher(html);        
        while(pageMatcher.find()){                
        	String found = pageMatcher.group(1);
        	
        	//skip bookmarks
        	if(found.startsWith("#"))
        		continue;
        	
        	URLNode newNode = new URLNode(found);
        	node.addChild(newNode);
        	
        	if(newNode.level < maxLevel && isPageAReadCandidate(newNode.getUrl())){
        		linksRead++;
        		if(linksRead%10 == 0)
        			System.out.println("Number of links opened " + linksRead);
        		
        		readLinks(newNode, maxLevel);
        	}        	        	
        }                
	}
	
	//try to filter out what not to read - avoid any extensions, pictures, PHP, non domain pages or pages that are already read
	//tested only on the assignment page - this is far from a finished filter
	private static boolean isPageAReadCandidate(String url){
		
		
		if(allPages.contains(url))
			return false;
		
		if(url.startsWith(website) == false)
			return false;
		
		if(url.endsWith(".php"))
			return false;
		
		if(url.endsWith(".png"))
			return false;
		
		if(url.startsWith("#"))
			return false;
		
		allPages.add(url);
		return true;
	}
	

}
