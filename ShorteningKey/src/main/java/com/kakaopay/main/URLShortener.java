package com.kakaopay.main;


import java.util.HashMap;
import java.util.Random;

/*
 * URL Shortener
 */
public class URLShortener {
	// storage for generated keys
	private HashMap<String, String> keyMap; // key-url map
	private HashMap<String, String> valueMap;// url-key map to quickly check
												// whether an url is
	// already entered in our system
	private String domain; // Use this attribute to generate urls for a custom
							// domain name defaults to http://fkt.in
	private char arrChars[]; // This array is used for character to number
							// mapping
	private Random myRand; // Random object used to generate random integers
	private int keyLength; // the key length in URL defaults to 8

	// Default Constructor
	URLShortener() {
		keyMap = new HashMap<String, String>();
		valueMap = new HashMap<String, String>();
		myRand = new Random();
		keyLength = 8;
		arrChars = new char[62];
		for (int i = 0; i < 62; i++) {
			int j = 0;
			if (i < 10) {
				j = i + 48;
			} else if (i > 9 && i <= 35) {
				j = i + 55;
			} else {
				j = i + 61;
			}
			arrChars[i] = (char) j;
		}
		domain = "kakao.pay/";
	}

	// Constructor which enables you to define tiny URL key length and base URL
	// name
	URLShortener(int length, String newDomain) {
		this();
		this.keyLength = length;
		if (!newDomain.isEmpty()) {
			newDomain = sanitizeURL(newDomain);
			domain = newDomain;
		}
	}

	// shortenURL
	// the public method which can be called to shorten a given URL
	public String shortenURL(String originalUrl) {
		String shortUrl = "";
		if (validateURL(originalUrl)) {
			originalUrl = sanitizeURL(originalUrl);
			if (valueMap.containsKey(originalUrl)) {
				shortUrl = domain + "/" + valueMap.get(originalUrl);
			} else {
				shortUrl = domain + "/" + getKey(originalUrl);
			}
		}
		return shortUrl;
	}

	// expandURL
	// public method which returns back the original URL given the shortened url
	public String originalUrl(String shortUrl) {
		String originalUrl = "";
		String key = shortUrl.substring(domain.length() + 1);
		originalUrl = keyMap.get(key);
		return originalUrl;
	}

	// Validate URL
	// not implemented, but should be implemented to check whether the given URL
	// is valid or not
	boolean validateURL(String url) {
		return true;
	}

	// sanitizeURL
	String sanitizeURL(String url) {
	//	if (url.substring(0, 7).equals("http://"))
	//		url = url.substring(7);

	//	if (url.substring(0, 8).equals("https://"))
	//		url = url.substring(8);

		if (url.charAt(url.length() - 1) == '/')
			url = url.substring(0, url.length() - 1);
		return url;
	}
	
	public String cleanXSS(String url) { 
		url = url.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		url = url.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
		url = url.replaceAll("'", "& #39;");
		url = url.replaceAll("eval\\((.*)\\)", "");
		url = url.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']","\"\"");
		url = url.replaceAll("script", "");
		return url;
	}

	public String checkUrl(String url) {
		String value = "";
		url = sanitizeURL(url);
		if("kakao.pay/".equals(url.substring(0,10))) {
			if (keyMap.containsKey(url.substring(10, url.length()))) {
				value = keyMap.get(url.substring(10, url.length()));
			} 
		}
		
		return value;
	}
	
	/*
	 * Get Key method
	 */
	private String getKey(String originalUrl) {
		String key;
		key = generateKey();
		keyMap.put(key, originalUrl);
		valueMap.put(originalUrl, key);
		return key;
	}

	// Key 생성
	private String generateKey() {
		String key = "";
		boolean flag = true;
		while (flag) {
			key = "";
			for (int i = 0; i <= keyLength; i++) {
				key += arrChars[myRand.nextInt(62)];
			}
			if (!keyMap.containsKey(key)) {
				flag = false;
			}
		}
		return key;
	}
	
}