package com.heroku;

public class Test1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Length!" + args.length);
		
		for (int i = 0; i< args.length; i++)
			System.out.println("arg[" + i + "]=" + args[i] );
		
    	if (args.length != 2)
    		System.out.println("Usage ApplicationManager <password> <email>!");
	
	}

}
