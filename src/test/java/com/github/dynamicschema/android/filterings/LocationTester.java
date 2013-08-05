/**
 * 
 */
package com.github.dynamicschema.android.filterings;

/**
 * @author esp
 *
 */
public class LocationTester {

	/**
	 * 
	 */
	public LocationTester() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 * "1","1","1020","1","50.874011","4.37854","Belgique"
			"2","2","1020","2","50.874011","4.37854","Belgique"
			"3","3","1348","3","50.669103","4.611584","Belgique"
			"4","4","1348","4","50.669103","4.611584","Belgique"
			"5","5","3500","5","50.934849","5.333122","Belgique"
			"6","6","3500","6","50.934849","5.333122","Belgique"
			"7","7","1050","7","50.835324","4.366695","Belgique"
			"8","8","1050","8","50.835324","4.366695","Belgique"
			"9","9","1030","9","50.854834","4.370743","Belgique"
			"10","10","1030","10","50.854834","4.370743","Belgique"
			"11","11","75000","11","48.80896","2.366695","France"
			"12","12","75000","12","48.80896","2.366695","France"
			"13","13","69000","13","45.73896","4.370743","France"
			"14","14","69000","14","45.72896","4.370743","France"
			"15","15","100","15","41.73896","12.370743","Italie"
			"16","16","100","16","41.920673","11.370743","Italie"
		 */
		
		double latA, longitA;
		double latB, longitB;
		
		//A
		latA = 48.862682;
		longitA = 2.351017;
		
		//B
		latB = 48.84896;
		longitB = 2.366695;
		
		double distance = Math.abs(latA - latB) + Math.abs(longitA - longitB);
		System.out.println(distance);
		System.out.println(""+ distance +  " <= " + "0.053724 : " + (distance <= 0.053724 ));
	}

}
