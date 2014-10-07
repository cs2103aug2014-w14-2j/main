package application;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import java.util.Date;
import java.util.List;

public class Time
{
   public static void main(String[] args)
   {
      List<Date> dates = new PrettyTimeParser().parse("watch movie right now!");
      System.out.println(dates.toString());
      // e.g. Prints: "[Sun Dec 12 13:45:12 CET 2013]"
   }
   
}