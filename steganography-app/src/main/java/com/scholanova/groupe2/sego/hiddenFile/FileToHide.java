package com.scholanova.groupe2.sego.hiddenFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileToHide {
	private long sizeFile;
	private File file;
	private byte octet;
	private int nbBitsRestant;
	private FileInputStream fis;
	private long sizeFileToSend;
	private int nbBitsRestantSize;
	
	public FileToHide (File file) {
		this.file = file;
		sizeFile = file.length();
		nbBitsRestant = 0;
		sizeFileToSend = sizeFile;
		nbBitsRestantSize = 16;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FileToHide (String pathFile) {
		file = new File(pathFile);
		sizeFile = file.length();
		nbBitsRestant = 0;
		sizeFileToSend = sizeFile;
		nbBitsRestantSize = 16;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public byte long2Bits() throws Exception{
		if (sizeFile >0xFFFF){
			throw new Exception ("Le fichier a cache est trop gros, il doit faire moins de 65535 octets");
		}
		if (nbBitsRestantSize == 0){
			return -1;
		}
		System.out.println("sizeFileToSend audebut ="+sizeFileToSend);
		
		byte ret = (byte) (sizeFileToSend & 0x03);
		System.out.println("donnée envoyée=" +ret);
		
		sizeFileToSend = (sizeFileToSend >>> 2);
		nbBitsRestantSize -=2;
		System.out.println("sizeFileToSend final = "+sizeFileToSend);
		System.out.println("");
		return ret;
	}
	
	public byte read2Bits (){
		if (nbBitsRestant == 0){
			byte[] tabByte = new byte[1];
			int ret = 0;
			try {
				ret = fis.read(tabByte);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(ret == -1){
				return -1;
			}
			octet = tabByte[0];
			nbBitsRestant = 8;
		}
		byte ret2 = (byte) (octet & 0x03);
		octet = (byte) (octet >>>2);
		nbBitsRestant -=2;
		
		return ret2;
		
	}

	public long getSizeFile() {
		return sizeFile;
	}
	
	public static long sizeNewFile (byte[]tabByte){
		long longuerNewFile = 0;
		for (int i=7; i>=0; i--){
			byte x = (byte) (tabByte[i] & 0x03);
			longuerNewFile = longuerNewFile << 2;
			longuerNewFile = longuerNewFile | x;			
		}
		return longuerNewFile;
	}
	
	public static byte[] dataNewFile (byte[]tabByte){
		byte[] data = new byte[tabByte.length/4];
		
		for (int j=0; j < data.length; j++){
			data[j] = 0;
			for (int i=3; i>=0; i--){
				byte x = (byte) (tabByte[(j*4)+i] & 0x03);
				data[j] = (byte) (data[j] << 2);
				data[j] = (byte) (data[j] | x);			
			}
		
		}
		
		return data;
	}

	public boolean isEnoughtOctetToHide (int lenght){
		if (lenght < (sizeFile +2)*4)
			return false;
		
		return true;
		
	}
	
}