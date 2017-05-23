package PartOfProgram;

import java.util.Date;


public class Test {
	public static void main(String[] args) {
		long time = System.nanoTime();
		Date timeD = new Date(time);
		System.out.println(timeD.toString());
	}

}
