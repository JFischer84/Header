package de.immonet;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by jfische1 and lstoecke on 05.09.16.
 */
public class Header {

	private String version;
	private String tos;
	private String totalLength = "5";
	private String identification;
	private String flags;
	private String fragmentOffset;
	private String ttl;
	private String protocol;
	private String headerChecksumString = "0";
	private String headerChecksumBinary;
	private String sourceIP;
	private String destinationIP;
	private String ihl = "5";


	public void setTos(String tos) {
		this.tos = tos;
	}


	public void setTotalLength(String totalLength) {
		this.totalLength = totalLength;
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


	public void setHeaderChecksumString(String headerChecksumString) {
		this.headerChecksumString = headerChecksumString;
	}


	public void setHeaderChecksumBinary(String headerChecksumBinary) {
		this.headerChecksumBinary = headerChecksumBinary;
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
	}


	private String ipToBinary(String ip) {
		String binaryIP = "";
		String[] ipChunks = ip.split("\\.");
		for (int i = 0; i < ipChunks.length; i++) {
			if (Integer.toBinaryString(Integer.valueOf(ipChunks[i])).length() == 8) {
				binaryIP += Integer.toBinaryString(Integer.valueOf(ipChunks[i]));
			}
			else {
				binaryIP += addZeros(Integer.toBinaryString(Integer.valueOf(ipChunks[i])), 8);
			}
		}

		return binaryIP;
	}

	public String addZeros(String input, int length) {
		String out = "";
		length = length - input.length();
		for (int i = 0; i < length; i++) {
			out += "0";
		}
		return out += input;

	}

	public void convertBinaryToString(String binary) {

		String[] binaryChunks = binary.split("\\s+");
		List<String> stringChunks = new ArrayList<String>();

		for (int i = 0; i < binaryChunks.length; i++) {
			if (binaryChunks[i].length() > 16) {
				stringChunks.add(convertIPBinary(binaryChunks[i]));
			}
			else {
				stringChunks.add(convertBinary(binaryChunks[i]));
			}
		}

		setValuesFromList(stringChunks);

	}


	private String convertIPBinary(String binaryChunk) {
		String[] ipChunk = new String[4];
		ipChunk[0] = binaryChunk.substring(0, 8);
		ipChunk[1] = binaryChunk.substring(8, 16);
		ipChunk[2] = binaryChunk.substring(16, 24);
		ipChunk[3] = binaryChunk.substring(24, 32);
		String currentIPString = "";

		for (int j = 0; j < ipChunk.length; j++) {
			currentIPString += convertBinary(ipChunk[j]);
			if (j < ipChunk.length - 1) {
				currentIPString += ".";
			}
		}
		return currentIPString;
	}


	private String convertBinary(String binary) {
		int decimal = Integer.parseInt(binary, 2);
		return Integer.toString(decimal);
	}


	private void setValuesFromList(List<String> stringChunks) {
		version = stringChunks.get(0);
		ihl = stringChunks.get(1);
		tos = stringChunks.get(2);
		totalLength = stringChunks.get(3);
		identification = stringChunks.get(4);
		flags = addZeros(Integer.toBinaryString(Integer.valueOf(stringChunks.get(5))), 3);
		fragmentOffset = stringChunks.get(6);
		ttl = stringChunks.get(7);
		protocol = stringChunks.get(8);
		headerChecksumString = stringChunks.get(9);
		sourceIP = stringChunks.get(10);
		destinationIP = stringChunks.get(11);
	}


	public String printBinary() {
		String binary = "";

		binary = addZeros(Integer.toBinaryString(Integer.valueOf(version)), 4);
		binary += " " + addZeros(Integer.toBinaryString(Integer.valueOf(ihl)), 4);
		binary += " " + addZeros(Integer.toBinaryString(Integer.valueOf(tos)), 8);
		binary += " " + addZeros(Integer.toBinaryString(Integer.valueOf(totalLength)), 16);
		binary += " " + addZeros(Integer.toBinaryString(Integer.valueOf(identification)), 16);
		binary += " " + flags;
		binary += " " + addZeros(Integer.toBinaryString(Integer.valueOf(fragmentOffset)), 13);
		binary += " " + addZeros(Integer.toBinaryString(Integer.valueOf(ttl)), 8);
		binary += " " + addZeros(Integer.toBinaryString(Integer.valueOf(protocol)), 8);
		binary += " " + addZeros(headerChecksumString, 16);
		binary += " " + ipToBinary(sourceIP);
		binary += " " + ipToBinary(destinationIP);

		return binary;

	}


	public String printHeader() {

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
}
