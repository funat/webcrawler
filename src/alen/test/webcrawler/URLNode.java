package alen.test.webcrawler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple tree like structure to keep the URL map
 * 
 * @author alen
 *
 */
public class URLNode {
	String url;
	URLNode parent;
	Map<String, URLNode> children;

	// this is just help for output indentation
	int level = 0;

	public URLNode(String url) {
		this.url = url;
		children = new HashMap<String, URLNode>();
		this.level = 1;
	}

	public void addChild(URLNode node) {
		if (children.containsKey(node.getUrl()) == false) {
			node.parent = this;
			node.level = node.parent.level + 1;
			children.put(node.getUrl(), node);
		}
	}

	public String getUrl() {
		return url;
	}

	// simple method to show the contents in console
	public void printToConsole() {
		String prefix = "";
		for (int i = 1; i < level; i++)
			prefix += "-";

		System.out.println(prefix + " " + url);

		for (URLNode node : children.values()) {
			node.printToConsole();
		}
	}

	// show this node and all the children in a text file
	public void printToFile(String path) {
		try (FileWriter fw = new FileWriter(path, true); BufferedWriter bw = new BufferedWriter(fw); PrintWriter out = new PrintWriter(bw)) {
			String prefix = "";
			for (int i = 1; i < level; i++)
				prefix += "-";

			out.println(prefix + " " + url);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		for (URLNode node : children.values()) {
			node.printToFile(path);
		}
	}
}
