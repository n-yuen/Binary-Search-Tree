import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.filechooser.*;


public class Main {
	public static void main(String[] args){

		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt Files", "txt");
		chooser.setFileFilter(filter);
		chooser.setDialogTitle("Open document containing all integers");
		int allNumChooser = chooser.showOpenDialog(null);
		Treeee<Integer> allNums = new Treeee<Integer>();
		int nextInt;

		Scanner fileScanner;


		if (allNumChooser == JFileChooser.APPROVE_OPTION){
			File allNumbers = chooser.getSelectedFile();
			try {
				fileScanner = new Scanner(allNumbers);
				while (fileScanner.hasNextInt()){
					nextInt = fileScanner.nextInt();
					allNums.add(nextInt);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			chooser.setDialogTitle("Open document containing integers to remove");
			int removerChooser = chooser.showOpenDialog(null);
			if (removerChooser == JFileChooser.APPROVE_OPTION){
				File removeNumbers = chooser.getSelectedFile();
				//System.err.println(allNums.inOrderTraversal());
				//System.err.println("PREORDER " + allNums.preOrderTraversal());
				try {
					fileScanner = new Scanner(removeNumbers);
					while (fileScanner.hasNextInt()){
						nextInt = fileScanner.nextInt();
						allNums.remove(nextInt);
						//System.err.println(nextInt);
						//System.err.println(allNums.inOrderTraversal());
						//System.err.println("PREORDER " + allNums.preOrderTraversal());
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				//System.out.println(allNums.inOrderTraversal());
				//System.err.println("PREORDER " + allNums.preOrderTraversal());

				System.out.println("Name the .txt document containing the new list of integers:");
				Scanner scan = new Scanner(System.in);
				String docname = scan.nextLine();
				scan.close();
				try {
					File output = new File(docname+".txt");
					PrintWriter writer = new PrintWriter(new FileOutputStream(output, false));
					writer.println("Pre-Order:");
					writer.println(allNums.preOrderTraversal());
					writer.println("In-Order:");
					writer.println(allNums.inOrderTraversal());
					writer.println("Post-Order:");
					writer.println(allNums.postOrderTraversal());
					writer.close();
					if (Desktop.isDesktopSupported()) {
					    try {
					        Desktop.getDesktop().open(output);
					    } catch (IOException ex) {
					    	ex.printStackTrace();
					    }
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				

			}
		}
	}
}
