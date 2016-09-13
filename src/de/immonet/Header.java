package de.immonet;

import java.util.ArrayList;
import java.util.List;



/**
 * This Class holds the data for an IPv4 Header and implements Methods for calculation
 * and printing purposes.
 * Created by jfische1 and lstoecke on 05.09.16.
 */
public class Header {

	private String version;
	private String tos;
	private String totalLength;
	private String identification;
	private String flags;
	private String fragmentOffset;
	private String ttl;
	private String protocol;
	private String headerChecksumString;
	private String sourceIP;
	private String destinationIP;
	private String ihl;


	public String getHeaderChecksumString() {
		return headerChecksumString;
	}


	public void setTos(String tos) {
		this.tos = tos;
	}


	public void setFlags(String flags) {
		this.flags = flags;
	}


	public void setFragmentOffset(String fragmentOffset) {
		this.fragmentOffset = fragmentOffset;
	}


	public void setTtl(String ttl) {
		this.ttl = ttl;
	}


	public void setSourceIP(String sourceIP) {
		this.sourceIP = sourceIP;
	}


	public void setDestinationIP(String destinationIP) {
		this.destinationIP = destinationIP;
	}


	public Header() {
		version = "4";
		identification = "0";
		protocol = "0";
		headerChecksumString = "0000000000000000";
		totalLength = "5";
		ihl = "5";
	}


	//Splits the IP String at the dot and uses the convertStringToBinary method to convert it to binary
	private String ipToBinary(String ip) {
		String binaryIP = "";
		String[] ipChunks = ip.split("\\.");

		for (int i = 0; i < ipChunks.length; i++) {
			binaryIP += convertStringToBinary(ipChunks[i], 8);
		}

		return binaryIP;
	}


	//Takes a given String and converts it into Binary. It uses the addZeros method to add
	//zeros in front of the value, if it does not match the given length
	private String convertStringToBinary(String input, int length) {
		input = Integer.toBinaryString(Integer.valueOf(input));
		String out = "";
		out = addZeros(input, length);
		return out;

	}


	//Adds zeros to a given String, if it does not match the given length
	private String addZeros(String input, int length) {
		String out = "";
		length = length - input.length();
		for (int i = 0; i < length; i++) {
			out += "0";
		}
		return out += input;
	}


	/**
	 * Splits a Binary String on the Space and converts it into decimal values, saves them in an array
	 * and passes it to the setVariablesFromList method.
	 *
	 * @param binary - be careful to leave blankets in the string according to the
	 *               scheme: A space after each value.
	 */
	public void convertBinaryToString(String binary) {

		String[] binaryChunks = binary.split("\\s+");
		List<String> stringChunks = new ArrayList<String>();

		for (int i = 0; i < binaryChunks.length; i++) {
			if (binaryChunks[i].length() > 16) {
				stringChunks.add(convertBinaryIPToString(binaryChunks[i]));
			}
			else {
				stringChunks.add(convertBinaryToDecimal(binaryChunks[i]));
			}
		}
		setVariablesFromList(stringChunks);
	}


	//Splits the binary IP in eight bit values, converts it to decimal and adds a dot after each eight bit chunk
	private String convertBinaryIPToString(String binaryChunk) {
		String[] ipChunk = new String[4];
		ipChunk[0] = binaryChunk.substring(0, 8);
		ipChunk[1] = binaryChunk.substring(8, 16);
		ipChunk[2] = binaryChunk.substring(16, 24);
		ipChunk[3] = binaryChunk.substring(24, 32);
		String currentIPString = "";

		for (int j = 0; j < ipChunk.length; j++) {
			currentIPString += convertBinaryToDecimal(ipChunk[j]);
			if (j < ipChunk.length - 1) {
				currentIPString += ".";
			}
		}
		return currentIPString;
	}


	//Converts a binary String to a decimal String
	private String convertBinaryToDecimal(String binary) {
		int decimal = Integer.parseInt(binary, 2);
		return Integer.toString(decimal);
	}


	//Sets the Variables from the list given as parameter
	private void setVariablesFromList(List<String> stringChunks) {
		version = stringChunks.get(0);
		ihl = stringChunks.get(1);
		tos = stringChunks.get(2);
		totalLength = stringChunks.get(3);
		identification = stringChunks.get(4);
		flags = convertStringToBinary(stringChunks.get(5), 3);
		fragmentOffset = stringChunks.get(6);
		ttl = stringChunks.get(7);
		protocol = stringChunks.get(8);
		headerChecksumString = convertStringToBinary(stringChunks.get(9), 16);
		sourceIP = stringChunks.get(10);
		destinationIP = stringChunks.get(11);
	}


	/**
	 * Returns the IPv4 Header as a binary String with spaces between each value.
	 *
	 * @return - the Header as binary String
	 */
	public String printBinaryHeader() {
		String binary = "";

		binary = convertStringToBinary(version, 4);
		binary += " " + convertStringToBinary(ihl, 4);
		binary += " " + convertStringToBinary(tos, 8);
		binary += " " + convertStringToBinary(totalLength, 16);
		binary += " " + convertStringToBinary(identification, 16);
		binary += " " + flags;
		binary += " " + convertStringToBinary(fragmentOffset, 13);
		binary += " " + convertStringToBinary(ttl, 8);
		binary += " " + convertStringToBinary(protocol, 8);
		binary += " " + headerChecksumString;
		binary += " " + ipToBinary(sourceIP);
		binary += " " + ipToBinary(destinationIP);

		return binary;

	}


	/**
	 * Returns the IPv4 Header as a String.
	 *
	 * @return - the Header as a String
	 */
	public String printStringHeader() {

		String header = "";

		header = version;
		header += "-" + ihl;
		header += "-" + tos;
		header += "-" + totalLength;
		header += "-" + identification;
		header += "-" + flags;
		header += "-" + fragmentOffset;
		header += "-" + ttl;
		header += "-" + protocol;
		header += "-" + headerChecksumString;
		header += "-" + sourceIP;
		header += "-" + destinationIP;

		return header;
	}


	/**
	 * Creates the Checksum for a given IPv4 Header.
	 */
	public void createChecksum() {

		String binary = fillBinaryString();
		String[] chunk = createSubstrings(binary);
		int sum = calculateSumOffAllChunks(chunk);
		String binarySumString = Integer.toBinaryString(sum);
		long checkSum = calculateChecksum(binarySumString);

		binarySumString = Long.toBinaryString(checkSum);
		binarySumString = addZeros(binarySumString, 16);
		binarySumString = flippString(binarySumString);
		headerChecksumString = binarySumString;
	}


	//Calculates the Checksum by adding the first four bits of the sum to the rest
	private long calculateChecksum(String binarySumString) {
		String[] sumChunk = new String[2];
		sumChunk[0] = binarySumString.substring(0, 4);
		sumChunk[1] = binarySumString.substring(4, binarySumString.length());

		return Long.valueOf(sumChunk[0], 2) + Long.valueOf(sumChunk[1], 2);
	}


	//Calculates the sum of all sixteen bit chunks from the header
	private int calculateSumOffAllChunks(String[] chunk) {
		int sum = 0;
		for (int i = 0; i < chunk.length; i++) {
			sum += Integer.valueOf(chunk[i], 2);
		}
		return sum;
	}


	//Creates a String array with all ten sixteen bit parts from the header
	private String[] createSubstrings(String binary) {
		String[] chunk = new String[10];

		chunk[0] = binary.substring(0, 16);
		chunk[1] = binary.substring(16, 32);
		chunk[2] = binary.substring(32, 48);
		chunk[3] = binary.substring(48, 64);
		chunk[4] = binary.substring(64, 80);
		chunk[5] = binary.substring(80, 96);
		chunk[6] = binary.substring(96, 112);
		chunk[7] = binary.substring(112, 128);
		chunk[8] = binary.substring(128, 144);
		chunk[9] = binary.substring(144, 160);

		return chunk;
	}


	//Returns the header as one big binary String
	private String fillBinaryString() {
		String binary = "";

		binary = convertStringToBinary(version, 4);
		binary += convertStringToBinary(ihl, 4);
		binary += convertStringToBinary(tos, 8);
		binary += convertStringToBinary(totalLength, 16);
		binary += convertStringToBinary(identification, 16);
		binary += flags;
		binary += convertStringToBinary(fragmentOffset, 13);
		binary += convertStringToBinary(ttl, 8);
		binary += convertStringToBinary(protocol, 8);
		binary += ipToBinary(sourceIP);
		binary += ipToBinary(destinationIP);
		binary += headerChecksumString;

		return binary;
	}


	//Flipps the zeros and ones in a binary String
	private String flippString(String toFlip) {
		String flippedString = "";

		for (int j = 0; j < toFlip.length(); j++) {
			if (toFlip.charAt(j) == '0') {
				flippedString += "1";
			}
			else {
				flippedString += "0";
			}
		}
		return flippedString;
	}
}
