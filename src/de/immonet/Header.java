package de.immonet;




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

	public String convertBinaryToString(String binary) {
		return "";
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
