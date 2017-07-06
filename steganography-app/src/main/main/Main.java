package main;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import fileBMP.FileBMP;
import hiddenFile.FileToHide;

public class Main {

	public static void main(String[] args) throws Exception{
		File file = new File ("D:\\AgilityFactory\\test\\imageBMP.bmp");
		FileBMP fileBMP = new FileBMP();
		fileBMP.readFileBMP(file);		
		
		
		File file2 = new File ("D:\\AgilityFactory\\test\\imageCache.png");
		FileToHide fileToHide = new FileToHide(file2);
		
		if (!fileToHide.isEnoughtOctetToHide(fileBMP.getImageSize())){
			System.out.println("le fichier BMP est trop petit pour cacher le fichier choisi");
			System.exit(-1);
		}
		
		byte bitsToHide;
		System.out.println(fileToHide.getSizeFile());
//		on cache longueur de FileToHide
		while ((bitsToHide=fileToHide.long2Bits()) != -1){
			fileBMP.hidBits(bitsToHide);	
		}
		
//		on cache les données du fichier dans fichierBMP
		while ((bitsToHide=fileToHide.read2Bits()) != -1){
			fileBMP.hidBits(bitsToHide);
		}
	
//		on sauvegarde le new fichier créer
		File file3 = new File ("D:\\AgilityFactory\\test\\newImageBMP.bmp");
		fileBMP.saveNewBMPFile(file3);
		
		
//		on recupere le fichier cache
		byte[] data = fileBMP.readNewFileBMP(file3);
		if (data !=null){
			File file4 = new File ("D:\\AgilityFactory\\test\\newImageCache.png");
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(file4);
				BufferedOutputStream bos = new BufferedOutputStream (fos);
				bos.write(data);
				bos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
	}

}
