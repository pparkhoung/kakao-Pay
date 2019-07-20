package com.kakaopay.main;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	URLShortener u = new URLShortener(8, "kakao.pay/");
	/**
	 * Simply selects the home view to render by returning its name.
	 */

	  
	@RequestMapping(value = "/insertUrl", method = RequestMethod.GET)
	public String insertUrl(Locale locale, Model model) {
				
		return "insertUrl";
	}
	
	@RequestMapping(value = "/checkUrl", method = RequestMethod.POST)
	public String checkUrl(HttpServletRequest req ,Model model) throws Exception {
		req.setCharacterEncoding("UTF-8");
		String url = req.getParameter("url").trim();	
		
		if(url != null) {
			url = u.cleanXSS(url);
		}
		String checkUrl = u.checkUrl(url);
		if(!checkUrl.equals("")) {
			return  "redirect:" + checkUrl;
		}
		

		System.out.println("URL:" + url + "\tshortUrl: "+ u.shortenURL(url) + "\toriginalUrl: "+ u.originalUrl(u.shortenURL(url)));
		
		model.addAttribute("shortUrl", u.shortenURL(url) );
		model.addAttribute("originalUrl", u.originalUrl(u.shortenURL(url)) );

		return "insertUrl";
	}
	

	
}
