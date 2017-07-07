package com.scholanova.groupe2.sego.main;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import com.scholanova.groupe2.sego.fileBMP.FileBMP;
import com.scholanova.groupe2.sego.hiddenFile.FileToHide;

public class Main {

	public static void main(String[] args) throws Exception{
		Scanner sc = new Scanner(System.in);
		System.out.println("entrer le chemin ainsi que le nom de l'image BMP que vous souhaitez utiliser avec son extension:");
		String imageBMP = sc.nextLine();
		System.out.println("entrer le chemin ainsi que le nom du fichier que vous souhaitez cacher avec son extension:");
		String hiddenFile = sc.nextLine();
		System.out.println("entrer le chemin ainsi que le nom de l'image BMP créée avec son extension:");
		String newImageBMP = sc.nextLine();
		System.out.println("entrer le chemin ainsi que le nom du fichier que vous souhaitez récupérer avec son extension:");
		String newHiddenFile = sc.nextLine();
		sc.close();
	
		
		File file1 = new File (imageBMP);
		FileBMP fileBMP = new FileBMP();
		fileBMP.readFileBMP(file1);		
		
		
		File file2 = new File (hiddenFile);
		FileToHide fileToHide = new FileToHide(file2);
		
		if (!fileToHide.isEnoughtOctetToHide(fileBMP.getImageSize())){
			System.out.println("le fichier BMP est trop petit pour cacher le fichier choisi");
			System.exit(-1);
		}
		
		byte bitsToHide;

//		on cache longueur de FileToHide
		while ((bitsToHide=fileToHide.long2Bits()) != -1){
			fileBMP.hidBits(bitsToHide);	
		}
		
//		on cache les données du fichier dans fichierBMP
		while ((bitsToHide=fileToHide.read2Bits()) != -1){
			fileBMP.hidBits(bitsToHide);
		}
	
//		on sauvegarde le new fichier créer
		File file3 = new File (newImageBMP);
		fileBMP.saveNewBMPFile(file3);
		
		
//		on recupere le fichier cache
		byte[] data = fileBMP.readNewFileBMP(file3);
		if (data !=null){
			File file4 = new File (newHiddenFile);
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
