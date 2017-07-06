package com.scholanova.groupe2.sego.fileBMP;

public class EnteteFichier {
//	public final static int enteteFichierSize = 14;
//
//	public EnteteFichier(byte[] data) {
//		if (data==null||data.length!=14){
//			throw new Exception ("Probleme dans l'entete fichier");
//		}
//	}
	
	private byte[] signature = new byte[2];
	private int fileSize;
	private byte[] reserve = new byte [4];
	private int offset;
	
	public byte[] getSignature() {
		return signature;
	}
	public void setSignature(byte[] signature) throws Exception {
		this.signature[0] = signature[0];
		this.signature[1] = signature[1];
		if (signature[0]!='B' || signature[1]!='M' ){
			throw new Exception ("Ceci n'est pas un fichier BMP");
		}
	}
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	public byte[] getReserve() {
		return reserve;
	}
	public void setReserve(byte[] reserve) {
		this.reserve = reserve;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	} 
	
	
	

}
