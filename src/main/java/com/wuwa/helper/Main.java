package com.wuwa.helper;

import com.wuwa.helper.model.Material;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		Material lfHowlerCore = new Material();
		lfHowlerCore.id = 1;
		lfHowlerCore.name = "LF Howler Core";
		System.out.println(lfHowlerCore.name);
	}

}
