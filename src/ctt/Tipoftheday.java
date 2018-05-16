package ctt;

import java.util.ArrayList;
import java.util.Random;

public class Tipoftheday 
{
 ArrayList<String> Tip = new ArrayList<String>();

 Tipoftheday()
 {Tip.add("Work only on Master, Individuals and Classes are updated instantly.");
  Tip.add("Always stick to 3-2 format, for example MAT(MO), ALG(TM), ECO(SK) etc");
  Tip.add("Set Printer before you start printing. It should be done only once.");
  Tip.add("Split lectures should be entered in the same cell, separeated by comma.");
	 
 }
 
 String GetTip()
 { 
	 int n=Tip.size();
	 Random r = new Random();
	 int index=r.nextInt();
	 return Tip.get(index);
	 
 }
	
}
