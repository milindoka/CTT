package ctt;

import java.util.ArrayList;
import java.util.Random;

public class Tipoftheday 
{
 ArrayList<String> Tip = new ArrayList<String>();

 Tipoftheday()
 {Tip.add("Work only on Master, Individuals and Classes are updated instantly.");
  Tip.add("Always stick to 3-2 format, for example MAT(MO), ALG(TM), ECO(SK) etc");
  Tip.add("Set Printer for First Time Printing. Setting will be saved thereafter.");
  Tip.add("Split lectures should be entered in the same cell, separeated by comma (,).");
  Tip.add("Always take incremental backup in different files. Use SaveAs from File menu.");
  Tip.add("Use copy paste cell(s) to speedup your work. Delete will clear selected cells.");
  Tip.add("Freeze cells that you don not want to move in clash remove routine.");
 }
 
 String GetTip()
 { 
	 int n=Tip.size();
	 Random r = new Random();
	 int index=r.nextInt(n);
	 return "Tip : "+Tip.get(index);
 }
	
}
