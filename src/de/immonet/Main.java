package de.immonet;

import java.util.Scanner;



public class Main {

	public static void main(String[] args) {

		Header header = new Header();
		Scanner sc = new Scanner(System.in);

		System.out.println("Willkommen beim IPv4-Header-Generator.");
		System.out.println("Wählen Sie eine der folgenden Eingabeoptionen:");
		System.out.println("(1) Eingabe eines IPv4 Headers als Binärcode, Ausgabe erfolgt als Dezimal-String (Flags und Checksum werden immer in Binär ausgegeben)");
		System.out.println("(2) Eingabe der einzelnen Werte eines IPv4 Headers als String, Ausgabe erfolgt als Dezimal-String & als Binärcode (Flags und Checksum werden immer in Binär ausgegeben)");

		String option = sc.nextLine();
		if (option.equals("1")) {
			System.out.println("Bitte geben Sie den Binärcode ein. Achten Sie darauf, dass die einzelnen Felder durch Leerzeichen voneinander getrennt sind:");
			header.convertBinaryToString(sc.nextLine());
			System.out.println(header.printStringHeader());

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

			header.createChecksum();

			System.out.println("Checksum: " + header.getHeaderChecksumString());

			System.out.println(header.printBinaryHeader());
			System.out.println(header.printStringHeader());
		}

		sc.close();

	}
}
