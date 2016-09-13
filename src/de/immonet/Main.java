package de.immonet;

import java.util.Scanner;



public class Main {

	public static void main(String[] args) {

		Header header = new Header();
		Scanner sc = new Scanner(System.in);

		System.out.println("Willkommen beim IPv4-Header-Generator.");
		System.out.println("Wählen Sie eine der folgenden Eingabeoptionen:");
		System.out.println("(1) Eingabe eines IPv4 Headers als Binärcode (Ausgabe erfolgt als String)");
		System.out.println("(2) Eingabe der einzelnen Werte eines IPv4 Headers als String (Ausgabe erfolgt als String & als Binärcode)");

		String option = sc.nextLine();
		if (option.equals("1")) {
			System.out.println("Bitte geben Sie den Binärcode ein. Achten Sie darauf, dass die einzelnen Felder durch Leerzeichen voneinander getrennt sind:");
			header.convertBinaryToString(sc.nextLine());
			System.out.println(header.printString());

		}
		if (option.equals("2")) {
			System.out.println("Bitte geben Sie den TOS-Wert ein: ");
			header.setTos(sc.nextLine());
			System.out.println("Bitte geben Sie die Werte für die Flags ein (z.B. 010 oder 110): ");
			header.setFlags(sc.nextLine());
			System.out.println("Bitte geben Sie die Werte für das Fragment-Offset ein: ");
			header.setFragmentOffset(sc.nextLine());
			System.out.println("Bitte geben Sie die Werte für die TTL ein: ");
			header.setTtl(sc.nextLine());
			System.out.println("Bitte geben Sie die Werte für die Quell-IP ein: ");
			header.setSourceIP(sc.nextLine());
			System.out.println("Bitte geben Sie die Werte für die Ziel-IP ein: ");
			header.setDestinationIP(sc.nextLine());

			System.out.println(header.printBinary());
			System.out.println(header.printString());
		}



		sc.close();

	}
}
