//PARAS ARORA
//pa6633
//Steganography <- Assignment 3

import java.util.*;
import java.io.FileWriter;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.util.Map;
import java.io.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.TreeMap;

public class Steganography {

	public static void main(String[] args) throws Exception {

		long pixel, bytes;
		String arg1, arg2;
		BufferedImage photo = null;
		boolean booE, booD;
		int length, broad;
		String skrt;
		arg1 = args[1];
		booE = false;
		arg2 = args[2];
		booD = false;

		if(args[0].equalsIgnoreCase("-d"))
			booD = true;
		else if(args[0].equalsIgnoreCase("-e"))
			booE = true;

		//try it
		try {
            photo = ImageIO.read(new File(arg1));
        } 
        // catch it
        catch (FileNotFoundException e) {
			System.out.println("File error.");
		}
        // catch it
        catch (IOException p) {
            System.out.println("Error: Image can't be found.");
            return;
		}
		//getting all the dimensions
		broad = photo.getWidth();
		//get how big it is horizontally ^
		length = photo.getHeight();
		//get how big it is vertically ^
		skrt = arg1.substring(arg1.indexOf('.') + 1);
		//type of image ^
		pixel = broad * length;
		//multiply both of em ^
		bytes = 3 * pixel;
		//bytes per pixel ^

		System.out.println("Name of the file: " + arg1);
		//printing out the file name ^
		System.out.println("Amount of Pixels: " + pixel);
		//printing out the Amount of Pixels in the picture ^
		System.out.println("Height of the image: " + length);
		//printing out the Height of the image ^
		System.out.println("Width of the image: " + broad);
		//printing out the Width of the image ^

		if(booD) {
			int move = 7;
			int forNow;
			FileOutputStream out = null;
			int pixell;
			File ffile = new File(arg2);
			int nInt = 0;
			int ro = 0;
			if(ffile.exists()) {
				ffile.delete();
				ffile = new File(arg2);
			}
			out = new FileOutputStream(ffile, true);
			while(ro < length) {
				int col = 0;
				while(col < broad) {
					pixell = photo.getRGB(col,ro);
					int kl = 2;
					while(kl > -1) {
						if(move == -1) {
							nInt = nInt & 0b000011111111;
							if(nInt == 0) {
								out.close();
								return;
							}
							out.write(nInt);
							move = 7;
							nInt = 0;
						}
						if(kl == 1) {
							forNow = pixell & 0b000000000000000000000000000100000000;
							forNow = forNow >> 8;
							forNow = forNow & 1;
							nInt = nInt | (forNow << move);
						}
						else if(kl == 2) {
							forNow = pixell & 0b000000000000000000010000000000000000;
							forNow = forNow >> 16;
							forNow = forNow & 1;
							nInt = nInt | (forNow << move);
						}
						else {
							forNow = pixell & 0b000000000000000000000000000000000001;
							nInt = nInt | (forNow << move);
						}
						move--;
						kl--;
					}
					col++;
				}
				ro++;
			}
		}
		else if(booE) {
			StringBuffer bus;
			long lone;
			FileInputStream inF;
			String str;
			File rod;
			BufferedImage buf;
			File fil = new File(arg2);
			if(fil.exists())
				inF = new FileInputStream(fil);
			else {
				System.out.println("No message.");
				return;
			}
			lone = fil.length() * 8; 
			bus = new StringBuffer(arg1);
			bus.insert(bus.length() - 4, "-steg");
			str = bus.toString();
			ImageIO.write(photo, skrt, new File(str));
			rod = new File(str);
			buf = ImageIO.read(rod); 
			if(lone + 8 > bytes) {
				int a, b;
				int rgb = 2;
				a = 0;
				int toRead = 8;
				int neg = -1;
				if(bytes < 6) {
					System.out.println("Pic too small.");
					return;
				}
				b = 0;
				int clone = buf.getRGB(a,b);
				long goNo = bytes - 8;
				int move = -1;
				int stop;
				for(; goNo % 8 != 0; )
					goNo--;
				for(; goNo > 0; ) {
					toRead = 8;
					neg = inF.read();
					for(; toRead > 0; ) {
						int now;
						if(rgb == -1) {
							buf.setRGB(a,b,clone);
							rgb = 2;
							a++;
                			if(a >= broad) {
                				a = 0;
                				b++;
                			}
                			clone = buf.getRGB(a,b);
						}
						if(move == -1)
							move = 7;
						now = (neg >>> move) & 1;
						if(now == 1) {
							if(rgb == 0)
								clone = clone | (0b000000000000000000000000000000000001);							
							else if(rgb == 1)
								clone = clone | (0b1100100);
							else if(rgb == 2)
								clone = clone | (0b000000000000000000010000000000000000);							
						}
						else {
							if(rgb == 0)
								clone = clone & (0b000011111111111111111111111111111110);
							else if(rgb == 1)
								clone = clone & (0b000011111111111111111111111011111111);							
							else if(rgb == 2)
								clone = clone & (0b000011111111111111101111111111111111);
						}
						toRead--;
						move--;
						rgb--;
						goNo--;
					}
				}
				if (rgb == 0) {
                    clone = clone & (0b000011111111111111111111111111111110);
                    stop = 3;
                } 
				else if (rgb == 1) {
                    clone = clone & (0b000011111111111111111111111011111110);
                    stop = 2;
                }                 
                else
                	stop = 3;
                int qw = 0;
                buf.setRGB(a,b,clone);
                a++;
                if(a >= broad) {
                	a = 0;
                	b++;
                }
                while(qw < stop) {
                	clone = buf.getRGB(a,b) & (0b000011111111111111101111111011111110);
                	buf.setRGB(a,b,clone);
                	a++;
                	if(a >= broad) {
                		a = 0;
                		b++;
                	}
                	qw++;
                }
                System.out.println("Text too big for the pic.");
			}
			else {
				//lot of ints
				int a, b;
				int rgb = 2;
				a = 0;
				int toRead = 8;
				int neg = -1;
				b = 0;
				int clone = buf.getRGB(a,b);
				int move = -1;
				int stop;
				for(; inF.available() > 0; ) {
					toRead = 8;
					neg = inF.read();
					for(; toRead > 0; ) {
						int now;
						if(rgb == -1) {
							buf.setRGB(a,b,clone);
							rgb = 2;
							a++;
							if(a >= broad) {
								a = 0;
								b++;
							}
							clone = buf.getRGB(a,b);
						}
						if(move == -1) 
							move = 7;
						now = (neg >>> move) & 1;
						if(now == 1) {
							if(rgb == 0)
								clone = clone | (0b000000000000000000000000000000000001);
							else if(rgb == 1)
								clone = clone | (0b1100100);
							else if(rgb == 2)
								clone = clone | (0b000000000000000000010000000000000000);
						}
						else {
							if(rgb == 0)
								clone = clone & (0b000011111111111111111111111111111110);							
							else if(rgb == 1)
								clone = clone & (0b000011111111111111111111111011111111);
							else if(rgb == 2)
								clone = clone & (0b000011111111111111101111111111111111);							
						}
						toRead--;
						move--;
						rgb--;
					} 
				}
				if (rgb == 0) {
                    clone = clone & (0b000011111111111111111111111111111110);
                    stop = 3;
                } 
				else if (rgb == 1) {
                    clone = clone & (0b000011111111111111111111111011111110);
                    stop = 2;
                }                
                else
                	stop = 3;
                int qw = 0;
                buf.setRGB(a,b,clone);
                a++;
                if(a >= broad) {
                	a = 0;
                	b++;
                }
                while(qw < stop) {
                	clone = buf.getRGB(a,b) & (0b000011111111111111101111111011111110);
                	buf.setRGB(a,b,clone);
                	a++;
                	if(a >= broad) {
                		a = 0;
                		b++;
                	}
                	qw++;
                }				
			}
			ImageIO.write(buf,skrt,rod);
			inF.close();
		}
	}
}