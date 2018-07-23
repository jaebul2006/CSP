package com.crc.CSP;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProductInfoController {

	@RequestMapping(value="/ProductInfo", method=RequestMethod.GET)
	public String ProductInfo()
	{
		return "ProductInfo";
	}
	
}
