package com.crc.CSP;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LicenseBuyController {

	@RequestMapping(value="/LicenseBuy", method=RequestMethod.GET)
	public String LicenseBuy()
	{
		return "LicenseBuy";
	}
	
}
