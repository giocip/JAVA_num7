package num7;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

/** DEVELOPED ON AMD Ryzen 5 Mobile 3550H 16GB DDR4 DRAM AND eclipse BY WINDOWS 11 */

/**
 * @file   Num.java => directory num7 (package)
 * @author Giovanni Cipriani <giocip7@gmail.com>
 * @date   2025
 * @brief  num7 ARBITRARY-PRECISION GENERAL PURPOSE ARITHMETIC-LOGIC DECIMAL CLASS FOR JAVA (JavaSE-1.8)
 *
 * @see https://github.com/giocip/JAVA_num7
 */

public class Num implements Comparable<Num> {

    /** public String type = "Num"; //'Num' TYPE SPECIFICATION */
    public String n;
    public String n0;
    public String n1;
    public String n2;
    public int L_n0;
    public int L_n1;
    public int d;

    public Num(String n) { //STANDARD CONSTRUCTOR
        this.d = 80;      //DEFAULT 80 PRECISION DECIMAL DIGITS
        String nn = n.replaceAll("_+", ""); //CLEAR '_'
        nn = nn.replaceAll("\\s+", "");    //CLEAR SPACE, TAB ECC.
        nn = nn.toUpperCase();            //ALL UPPERCASE
        if (nn.length() == 0)
            throw new IllegalArgumentException("Num CLASS CONSTRUCTOR => typing error, void NUMERIC String: \"\"" + n); //CHECK FOR VOID NUMBERIC STRING

        if (nn.contains("E")) nn = Num.exp2num(nn); //CHECK FOR 'E' CHARACTER (EXPONENTIAL)

        switch (nn.charAt(0)) {
            case '-':
                //NEGATIVE, REMOVE '-'
                this.n2 = "-";
                nn = nn.substring(1);
                break;
            case '+':
                //POSITIVE, REMOVE '+'
                this.n2 = "";
                nn = nn.substring(1);
                break;
            default: //POSITIVE
                this.n2="";
                break;
        }
        String[] nv = nn.split("\\.");
        boolean nv0_isOnlyDigits = nv[0].matches("\\d+");
        if (nv.length != 2)
            throw new IllegalArgumentException("Num CLASS CONSTRUCTOR => typing error, postfix .0 for integer: " + n); //CHECK FOR ONLY DIGIT NUMBERIC STRING
        boolean nv1_isOnlyDigits = nv[1].matches("\\d+");
        if (!nv0_isOnlyDigits || !nv1_isOnlyDigits)
            throw new IllegalArgumentException("Num CLASS CONSTRUCTOR => typing error: " + n); //CHECK FOR ONLY DIGIT NUMBERIC STRING
        this.n0 = nv[0];
        this.n1 = nv[1];
        this.L_n0 = this.n0.length();
        this.L_n1 = this.n1.length(); //CHECK FIRST TIME, LENGTH
        if (this.L_n0 == 0 || this.L_n1 == 0)
            throw new IllegalArgumentException("Num CLASS CONSTRUCTOR => missing string number: " + n);
        if (this.L_n0 > 1) {
            this.n0 = this.n0.replaceFirst("^0+", ""); //CLEAR LEFT  ZEROS
            if (this.n0.length() == 0)
                this.n0 = "0"; //if ''
        }
        if (this.L_n1 > 1) {
            this.n1 = this.n1.replaceFirst("0+$", ""); //CLEAR RIGHT ZEROS
            if (this.n1.length() == 0)
                this.n1 = "0"; //if ''
        }
        this.L_n0 = this.n0.length();
        this.L_n1 = this.n1.length(); //CHECK FOR NEW LENGTH
        this.n = this.n2 + this.n0 + "." + this.n1; //SET ALL NUMBER CLEANED

        if (this.n0.equals("0") && this.n1.equals("0"))
            if (this.n2.equals("-") || this.n.charAt(0) == '+')
                throw new IllegalArgumentException("Num CLASS CONSTRUCTOR => zero can not be signed: " + n);
        //this.d = d > this.L_n1 ? d : this.L_n1; //PRECISION
        this.d = d > (this.L_n0 + this.L_n1) ? d : (this.L_n0 + this.L_n1); //PRECISION
    }

    public Num(String n, int d) { //CONSTRUCTOR BY SET PRECISION d
        this(n); 				 //CALL Num(String n) CONSTRUCTOR
        this.d = d > (this.L_n0 + this.L_n1) ? d : (this.L_n0 + this.L_n1); //PRECISION
    }

    public Num(int n) { this(n + ".0"); } //CONSTRUCTOR BY int         

    public Num(int n, int d) { this(n + ".0"); this.d = d > (this.L_n0 + this.L_n1) ? d : (this.L_n0 + this.L_n1); } //CONSTRUCTOR BY int         

    public Num(long n) { this(n + ".0"); } //CONSTRUCTOR BY long         

    public Num(long n, int d) { this(n + ".0"); this.d = d > (this.L_n0 + this.L_n1) ? d : (this.L_n0 + this.L_n1); } //CONSTRUCTOR BY long         

    public Num(BigInteger n) { this(n.toString() + ".0"); } //CONSTRUCTOR BY BigInteger         

    public Num(BigInteger n, int d) { this(n.toString() + ".0"); this.d = d > (this.L_n0 + this.L_n1) ? d : (this.L_n0 + this.L_n1); } //CONSTRUCTOR BY BigInteger         

    public Num(Num n) { //COPY CONSTRUCTOR
        this.n = n.n; this.n0 = n.n0; this.n1 = n.n1; this.n2 = n.n2; this.L_n0 = n.L_n0;
        this.L_n1 = n.L_n1; 
        this.d = n.d;
    }

    /** STATIC MEMBERS => STATIC STATIC AND METHODS VARIABLES ***************************************************************** */
    
    /** ''' class VARIABLES LIST ''' */
    public static final String PI = "3.1415926535897932384626433832795";
    public static final String  E = "2.7182818284590452353602874713527";
    
    /** METHODS */

     /** RETURN pi */
    /** Num.print(new Num(Num.pi()).Round(), "\r\n"); //3.14 */
    public static String pi() { return Num.PI; }

     /** RETURN e */
    /**  Num.print(new Num(Num.e()).Round(), "\r\n"); //2.72 */
    public static String e() { return Num.E; }

    /** CONVERT A SCIENTIFIC NOTATION STRING NUMBER TO STRING NUMERIC */
   /**    CODE: String sun = Num.exp2num("1.9891e+30"); Num.print(sun); //1989100000000000000000000000000.0 (SUN WEIGTH KG) */
  /**    +- 0.0 0.1 1.1 1.01 1.11 10.11 11.11 11.011 11.101 110.101 101.101 110.011 101.011 101.001 100.001 111.011 111.001 111.111 */
  public static String exp2num(String s) {
      String S = s;
      s = s.replaceAll("\\s+", ""); //CLEAR SPACE, TAB ECC.
      s = s.replaceAll("_+", ""); //CLEAR '_'
      s = s.toUpperCase(); //ALL UPPERCASE
      String[] be = s.split("E");
      if (be[0].length() == 0 || be[1].length() == 0 || be.length == 1 || be.length > 2)
          throw new IllegalArgumentException("Num.exp2num => scientific notation not valid: " + S);
      String be0 = be[0];
      String be1 = be[1];
      Boolean POSE;
      switch (be1.charAt(0)) { //CHECK EXPONENT SIGN => be1
          case '+':
              POSE = true;
              be1 = be1.substring(1);
              break;
          case '-':
              POSE = false;
              be1 = be1.substring(1);
              break;
          default:
              POSE = null;
      }
      if (!Num.isDigit(be1))
          throw new IllegalArgumentException("Num.exp2num => scientific notation not valid: " + S); //EXPONENT MUST BE ONLY DIGITS
      be1 = Num.lstrip(be1, "0");
      if (be1.equals("")) { //REBUILD RIGHT ENDING ZERO
          if (POSE == null)
              POSE = true;
          if (POSE == false)
              throw new IllegalArgumentException("Num.exp2num => zero can not be signed: " + S); //SIGNED ZERO ERROR
          be1 = "0";
      }
      Boolean POS; //CHECK BASE SIGN
      switch (be0.charAt(0)) {
          case '+':
              POS = true;
              be0 = be0.substring(1);
              break;
          case '-':
              POS = false;
              be0 = be0.substring(1);
              break;
          default:
              POS = null;
      }
      be0 = Num.lstrip(be0, "0"); //CLEAR LEFT ZEROS
      if (be0.charAt(0) == '.')
          be0 = "0" + "." + be0.substring(1); //REBUILD LEFT STARTING ZERO
      String[] bf = be0.split("\\.");
      if (bf.length == 1 || bf.length > 2)
          throw new IllegalArgumentException(
                  "Num.exp2num => scientific notation not valid (postfix .0 for base integer like 7.0e7): " + S);
      String bf0 = bf[0];
      String bf1 = bf[1];
      if (!Num.isDigit(bf0) || !Num.isDigit(bf1))
          throw new IllegalArgumentException("Num.exp2num => scientific notation not valid, typing error: " + S); //BASE MUST BE ONLY DIGITS
      bf1 = Num.rstrip(bf1, "0"); //CLEAR RIGHT ZEROS
      if (bf1.equals(""))
          bf1 = "0"; //REBUILD RIGHT ENDING ZERO
      if (bf0.equals("0") && bf1.equals("0")) { //ZERO BASE
          if (POS == null)
              return "0.0"; //"0.0E0"
          if (POS == true || POS == false)
              throw new IllegalArgumentException("Num.exp2num => zero can not be signed: " + S); //SIGNED ZERO ERROR  
      }
      if (POSE == null)
          POSE = true;
      int EXP = Integer.parseInt((POSE == false ? "-" : "+") + be1);
      if (POS == null)
          POS = true;
      String POSs = (POS == false ? "-" : "");
      int L_bf0 = bf0.length(); //INTEGER BASE LENGTH
      int L_bf1 = bf1.length(); //DECS BASE LENGTH
      String r = "";
      String DOT;
      if (EXP >= 0) {
          String s_CHECK;
          //POSITIVE INTEGER BASE AND EXPONENT >= 0
          if (EXP - L_bf1 <= 0)
              r = bf0 + bf1;
          else
              r = bf0 + bf1 + String.format("%0" + (EXP - L_bf1) + "d", 0); //NEGATIVE INTEGER AND FLOATING POINT BASE
          DOT = (EXP - L_bf1 < 0 ? "." : "");
          s_CHECK = r.substring(0, L_bf0 + EXP) + DOT + r.substring(L_bf0 + EXP);
          s_CHECK = Num.lstrip(s_CHECK, "0");
          if (Num.IN(s_CHECK, "\\.")) { //if '.' in s_CHECK: /\./.test(s_CHECK)
              if (s_CHECK.charAt(0) == '.')
                  return POSs + "0" + s_CHECK; //0.0105e1 => 0.105 #000.10500e0 => 0.105
              return POSs + Num.lstrip(s_CHECK, "0"); //-000.105e2 => -10.5 
          }
          return POSs + (s_CHECK + ".0"); //+000.105e3 => 105.0 .0105000e4 => 105.0
      }
      //NEGATIVE EXPONENT (EXP < 0)
      if (L_bf0 == 1) { //0.1e-1 => 0.01
          r = String.format("%0" + (-EXP) + "d", 0) + bf0 + bf1;
          return POSs + "0." + Num.rstrip(r.substring(1), "0"); //-0.2e-1 => -0.02
      }
      int DOTi = L_bf0 + EXP;
      if (DOTi == 0)
          return Num.rstrip(POSs + "0." + bf0 + bf1, "0"); //-102.01e-3 => -0.10201
      if (DOTi < 0)
          return Num.rstrip(POSs + "0." + String.format("%0" + (-DOTi) + "d", 0) + bf0 + bf1, "0"); //-102.01e-4 => -0.010201
      if (EXP <= 0)
          r = Num.rstrip(POSs + bf0.substring(0, bf0.length() + EXP) + "." + bf0.substring(bf0.length() + EXP) + bf1,
                  "0"); //-102.01e-2 => -1.0201
      return r.charAt(r.length() - 1) != '.' ? Num.rstrip(r, "0") : r + "0"; //3_000.0e-2 => 30.0                      
  }

   /** CONVERT A Num OBJECT TO SCIENTIFIC NOTATION STRING */  
  /**  CODE: Num a = new Num("1_250.75"); Num.print(Num.num2exp(a), "\r\n"); //1.25075e3 */
  public static String num2exp(Num ob) {
      String CHECK, n1;
      int e, L_n1;
      if (ob.n1.equals("0")) { //EXP >= 0
        e = ob.L_n0 - 1;
        CHECK = Num.rstrip(ob.n0.charAt(0) + "." + ob.n0.substring(1), "0");
        if (CHECK.substring(CHECK.length() - 1).equals(".")) CHECK = CHECK + "0"; //100.0 => 1.0e2
        return ob.n2 + CHECK + "e" + e; //150.0 => 1.5e2
      }  
      if (ob.n0.equals("0")) { //EXP < 0
        n1 = Num.lstrip(ob.n1 , "0");
        L_n1 = (n1).length();
        e = ob.L_n1 - L_n1 + 1;
        if (L_n1 == 1) return ob.n2 + n1 + ".0" + "e" + (-e); //0.03 => 3.0e-2
        return ob.n2 + n1.charAt(0) + "." + n1.substring(1) + "e" + (-e); //0.314 => 3.14e-1
      } 
      e = ob.L_n0 - 1;
      return ob.n2 + ob.n0.charAt(0) + "." + ob.n0.substring(1) + ob.n1 + "e" + e;
  }

     /** PRINT FOR DEBUG (VIDEO OUTPUT) */
    /** CODE: Num a = new Num("-3.141592654"); Num.print_debug(a); //-3.141592654 */
    public static void print_debug(Num n) {
        System.out.print("n="); System.out.print(n.n + " ");
        System.out.print("n0="); System.out.print(n.n0 + " ");
        System.out.print("n1="); System.out.print(n.n1 + " ");
        System.out.print("n2="); System.out.print(n.n2 + " ");
        System.out.print("L_n0="); System.out.print(n.L_n0 + " ");
        System.out.print("L_n1="); System.out.print(n.L_n1 + " ");
        System.out.print("d="); System.out.print(n.d + "\r\n");
    }

     /** PRINT FOR FORMATTING EUR CURRENCY */
    /** CODE: Num.printf(new Num("3000.141592654")); //3.000,14 */
    public static PrintStream printf(Num n) { return System.out.printf(Locale.ITALY, "%,.2f", n.Round(2).toFloat()); }

     /** PRINT FOR FORMATTING EUR CURRENCY */
    /** CODE: Num.printf(new Num("3000.141592654"), "\r\n"); //3.000,14 */
    public static PrintStream printf(Num n, String cr) { return System.out.printf(Locale.ITALY, "%,.2f" + cr, n.Round(2).toFloat()); }

     /** PRINT FOR FORMATTING EUR CURRENCY */
    /** CODE: Num.printf("3000.141592654"); //3.000,14 */
    public static PrintStream printf(String n) { return System.out.printf(Locale.ITALY, "%,.2f", new Num(n).Round(2).toFloat()); }

     /** PRINT FOR FORMATTING EUR CURRENCY */
    /** CODE: Num.printf("3000.141592654", "\r\n"); //3.000,14 */
    public static PrintStream printf(String n, String cr) { return System.out.printf(Locale.ITALY, "%,.2f" + cr, new Num(n).Round(2).toFloat()); }

     /** PRINT FOR FORMATTING EUR, US CURRENCY */
    /** CODE: Num.printf(new Num("3000.141592654"), 2, false); //3,000.14 */
    public static PrintStream printf(Num n, int DECs, boolean EUR) { 
      DECs = DECs < 0 ? 0 : DECs;
      String DECs_S = DECs + "";
      if(EUR == true) return System.out.printf(Locale.ITALY, "%,." + DECs_S + "f", n.Round(DECs).toFloat()); 
      return System.out.printf(Locale.US, "%,." + DECs_S + "f", n.Round(DECs).toFloat()); 
    }

     /** PRINT FOR FORMATTING EUR, US CURRENCY */
    /** CODE: Num.printf(new Num("3000.141592654"), 2, false, "\r\n"); //3,000.14 */
    public static PrintStream printf(Num n, int DECs, boolean EUR, String cr) { 
      DECs = DECs < 0 ? 0 : DECs;
      String DECs_S = DECs + "";
      if(EUR == true) return System.out.printf(Locale.ITALY, "%,." + DECs_S + "f" + cr, n.Round(DECs).toFloat()); 
      return System.out.printf(Locale.US, "%,." + DECs_S + "f" + cr, n.Round(DECs).toFloat()); 
    }

     /** PRINT FOR FORMATTING EUR, US CURRENCY */
    /** CODE: Num.printf("3000.141592654", 2, false, "\r\n"); //3,000.14 */
    public static PrintStream printf(String n, int DECs, boolean EUR, String cr) { 
      DECs = DECs < 0 ? 0 : DECs;
      String DECs_S = DECs + "";
      if(EUR == true) return System.out.printf(Locale.ITALY, "%,." + DECs_S + "f" + cr, new Num(n).Round(DECs).toFloat()); 
      return System.out.printf(Locale.US, "%,." + DECs_S + "f" + cr, new Num(n).Round(DECs).toFloat()); 
    }

     /** PRINT FOR FORMATTING EUR, US CURRENCY */
    /** CODE: Num.printf("3000.141592654", 2, false); //3,000.14 */
    public static PrintStream printf(String n, int DECs, boolean EUR) { 
      DECs = DECs < 0 ? 0 : DECs;
      String DECs_S = DECs + "";
      if(EUR == true) return System.out.printf(Locale.ITALY, "%,." + DECs_S + "f", new Num(n).Round(DECs).toFloat()); 
      return System.out.printf(Locale.US, "%,." + DECs_S + "f", new Num(n).Round(DECs).toFloat()); 
    }

     /** PRINT FOR FORMATTING EUR, US CURRENCY */
    /** CODE: Num.printf(new Num("3000.141592654"), false); //3,000.14 */
    public static PrintStream printf(Num n, boolean EUR) { 
      if(EUR == true) return System.out.printf(Locale.ITALY, "%,.2f", n.Round(2).toFloat()); 
      return System.out.printf(Locale.US, "%,.2f", n.Round(2).toFloat()); 
    }

     /** PRINT FOR FORMATTING EUR, US CURRENCY */
    /** CODE: Num.printf("3000.141592654", false); //3,000.14 */
    public static PrintStream printf(String n, boolean EUR) { 
      if(EUR == true) return System.out.printf(Locale.ITALY, "%,.2f", new Num(n).Round(2).toFloat()); 
      return System.out.printf(Locale.US, "%,.2f", new Num(n).Round(2).toFloat()); 
    }

     /** PRINT FOR FORMATTING EUR, US CURRENCY */
    /** CODE: Num.printf(new Num("3000.141592654"), false, "\r\n"); //3,000.14 */
    public static PrintStream printf(Num n, boolean EUR, String cr) { 
      if(EUR == true) return System.out.printf(Locale.ITALY, "%,.2f" + cr, n.Round(2).toFloat()); 
      return System.out.printf(Locale.US, "%,.2f" + cr, n.Round(2).toFloat()); 
    }

     /** PRINT FOR FORMATTING EUR, US CURRENCY */
    /** CODE: Num.printf("3000.141592654", false, "\r\n"); //3,000.14 */
    public static PrintStream printf(String n, boolean EUR, String cr) { 
      if(EUR == true) return System.out.printf(Locale.ITALY, "%,.2f" + cr, new Num(n).Round(2).toFloat()); 
      return System.out.printf(Locale.US, "%,.2f" + cr, new Num(n).Round(2).toFloat()); 
    }

     /** PRINT Object (VIDEO OUTPUT) */
    /** CODE: Num a = new Num("-3.141592654"); Num.print(a.toString()); //-3.141592654 */
    public static void print(Object txt) { System.out.print(txt); }    

     /** PRINT Object, String (VIDEO OUTPUT) */
    /** CODE: Num a = new Num("-3.141592654"); Num.print(a.toString()); //-3.141592654 */
    public static void print(Object txt, String txt0) { System.out.print(txt + txt0); }   

     /** PRINT String (VIDEO OUTPUT) */
    /** CODE: Num a = new Num("-3.141592654"); Num.print(a.toString()); //-3.141592654 */
    public static void print(String txt) { System.out.print(txt); }    

     /** PRINT String, String (VIDEO OUTPUT) */
    /** CODE: Num a = new Num("3.141592654"); Num.print(a.toString(), " => pi"); //3.141592654 => pi */
    public static void print(String txt, String txt0) { System.out.print(txt + txt0); }

     /** PRINT String, String, String (VIDEO OUTPUT) */
    /** CODE: Num a = new Num("3.141592654"); Num.print(a.toString(), " => pi", " greek"); //3.141592654 => pi greek */
    public static void print(String txt, String txt0, String txt1) { System.out.print(txt + txt0 + txt1); }

     /** PRINT Num (VIDEO OUTPUT) */
    /** CODE: Num a = new Num("-3.141592654"); Num.print(a); //-3.141592654 */
    public static void print(Num n) { System.out.print(n.toString()); }

     /** PRINT Num, String (VIDEO OUTPUT) */
    /** CODE: Num a = new Num("-3.141592654"); Num.print(a, "\r\n"); //-3.141592654 */
    public static void print(Num n, String s) { System.out.print(n.toString() + s); }

     /** PRINT byte (VIDEO OUTPUT) */
    /** CODE: byte a = 127; Num.print(a); //127 */
    public static void print(byte n) { System.out.print(n); }

     /** PRINT byte, String (VIDEO OUTPUT) */
    /** CODE: byte a = 127; Num.print(a, "\r\n"); //127 */
    public static void print(byte n, String s) { System.out.print(n + s); }
    
     /** PRINT char (VIDEO OUTPUT) */
    /** CODE: char a = 'A'; Num.print(a); //'A' */
    public static void print(char n) { System.out.print(n); }
    
     /** PRINT char, String (VIDEO OUTPUT) */
    /** CODE: char a = 'A'; Num.print(a, "\r\n"); //'A' */
    public static void print(char n, String s) { System.out.print(n + s); }
    
     /** PRINT int (VIDEO OUTPUT) */
    /** CODE: int a = 3; Num.print(a); //3 */
    public static void print(int n) { System.out.print(n); }
    
     /** PRINT int, String (VIDEO OUTPUT) */
    /** CODE: int a = 3; Num.print(a, "\r\n"); //3 - CARRIAGE RETURN */
    public static void print(int n, String txt) { System.out.print(n + txt); }

     /** PRINT long (VIDEO OUTPUT) */
    /** CODE: long n = 2000000000L; Num.print(n); //2000000000 */
    public static void print(long n) { System.out.print(n); }

     /** PRINT long, String (VIDEO OUTPUT) */
    /** CODE: long n = 2000000000L; Num.print(n, "\r\n"); //2000000000 */
    public static void print(long n, String txt) { System.out.print(n + txt); }

     /** PRINT BigInteger (VIDEO OUTPUT) */
    /** CODE: BigInteger n = new BigInteger("2000000000"); Num.print(n); //2000000000 */
    public static void print(BigInteger n) { System.out.print(n.toString()); }

     /** PRINT BigInteger, String (VIDEO OUTPUT) */
    /** CODE: BigInteger n = new BigInteger("2000000000"); Num.print(n, "\r\n"); //2000000000 */
    public static void print(BigInteger n, String txt) { System.out.print(n.toString() + txt); }

     /** PRINT double (VIDEO OUTPUT) */
    /** CODE: double n = 1.123456; Num.print(n); //1.123456 */
    public static void print(double n) { System.out.print(n); }

     /** PRINT double, String (VIDEO OUTPUT) */
    /** CODE: double n = 1.123456; Num.print(n, "\r\n"); //1.123456 */
    public static void print(double n, String txt) { System.out.print(n + txt); }

     /** PRINT boolean (VIDEO OUTPUT) */
    /** CODE: boolean a = true; Num.print(a); //true */
    public static void print(boolean n) { System.out.print(n); }
    
     /** PRINT boolean, String(VIDEO OUTPUT) */
    /** CODE: boolean a = true; Num.print(a, "\r\n"); //true */
    public static void print(boolean n, String txt) { System.out.print(n + txt); }
    
     /** PRINT ArrayList<Num> (VIDEO OUTPUT) */
    /** ArrayList<Num> a = new ArrayList<>(Arrays.asList(new Num("3.0"), new Num("4.0"), new Num("5.0"))); Num.print(a); //[3.0, 4.0, 5.0] */
    public static void print(ArrayList<Num> NL) { System.out.print(NL); }
    
     /** PRINT ArrayList<Num>, String (VIDEO OUTPUT) */
    /** ArrayList<Num> a = new ArrayList<>(Arrays.asList(new Num("3.0"), new Num("4.0"), new Num("5.0"))); Num.print(a, "\r\n"); //[3.0, 4.0, 5.0] */
    public static void print(ArrayList<Num> NL, String txt) { System.out.print(NL + txt); }
    
     /** PRINT Num Array (VIDEO OUTPUT) */
    /** Num a[] = { new Num("3.0"), new Num("4.0"), new Num("5.0") }; Num.print(a, "\r\n"); //[3.0, 4.0, 5.0] */
    public static void print(Num[] A, String txt) { 
      ArrayList<Num> NL = new ArrayList<>(Arrays.asList(A));
      System.out.print(NL + txt); 
    }
    
    /**   DIVISION BETWEEN SIGNED INTEGER NUMBER 
          IT RUNS THE DIVISION BETWEEN SIGNED INTEGER NUMBERS ONLY AND THE QUOTIENT
            IS A FLOATING POINT STRING OF ARBITRARY PRECISION */
    /**   CODE: Num.print(Num.divi("5", "3", "3")); //1.666 */
    public static String divi(String N, String DIV) { return divi(N, DIV, "32"); }
    
    public static String divi(String N, String DIV, String D) {
        BigInteger n = new BigInteger(N);
        BigInteger div = new BigInteger(DIV);
        BigInteger d = new BigInteger(D);
        if (div.compareTo(new BigInteger("0")) == 0)
            throw new ArithmeticException("Num.divi => DIVISION BY ZERO: " + DIV);
        boolean n_si = n.compareTo(new BigInteger("0")) < 0;
        boolean div_si = div.compareTo(new BigInteger("0")) < 0;
        n = n.abs();      //absolute
        div = div.abs(); //absolute
        d = d.abs();    //absolute
        BigInteger r = n.divide(div); //INTEGER BigInteger DIVISION
        String s = r.toString() + ".";
        BigInteger k = d;
        while (k.compareTo(new BigInteger("0")) > 0) {
            r = n.remainder(div);
            n = r.multiply(new BigInteger("10")); //10x
            r = n.divide(div);
            s = s + r.toString();
            if (r.equals(new BigInteger("0")) && n.equals(new BigInteger("0")))
                break; //CLEAR SPURIOUS ZEROs
            k = k.subtract(new BigInteger("1"));
        }
        if (!(n_si || div_si) || (n_si && div_si))
            return d.compareTo(new BigInteger("0")) > 0 ? s : s + "0"; //POSITIVE
        else
            return d.compareTo(new BigInteger("0")) > 0 ? (s.equals("0.0") ? s : "-" + s)
                    : (("-" + s + "0").equals("-0.0") ? "0.0" : "-" + s + "0"); //NEGATIVE
    }

   /** (+) CALCULATOR ADDITION METHOD */
  /**  CODE: Num.print(Num.add(new Num("2.1"), new Num("3.2")), "\r\n"); //5.3 */
  public static Num add(Num a, Num b) { return a.Add(b); }
  
   /** (+) CALCULATOR ADDITION METHOD */
  /**  CODE: Num.print(Num.add("2.1", "3.2"), "\r\n"); //5.3 */
  public static Num add(String a, String b) { Num A = new Num(a); Num B = new Num(b); return A.Add(B); }

   /** (+) CALCULATOR ADDITION METHOD */
  /**  CODE: Num.print(Num.add(2, 3), "\r\n"); //5.0 */
  public static Num add(int a, int b) { Num A = new Num(a); Num B = new Num(b); return A.Add(B); }

   /** (+) CALCULATOR ADDITION METHOD */
  /**  CODE: Num.print(Num.add(1000000000L, 999999999L), "\r\n"); //1999999999.0 */
  public static Num add(long a, long b) { Num A = new Num(a); Num B = new Num(b); return A.Add(B); }

   /** (+) CALCULATOR ADDITION METHOD */
  /**  CODE: Num.print(Num.add(new BigInteger("100000000000000000000"), new BigInteger("99999999999999999999")), "\r\n"); //199999999999999999999.0 */
  public static Num add(BigInteger a, BigInteger b) { Num A = new Num(a); Num B = new Num(b); return A.Add(B); }

   /**  (-) CALCULATOR SUBTRACTION METHOD */
  /**  CODE: Num.print(Num.sub(new Num("2.1"), new Num("3.2")), "\r\n"); //-1.1 */
  public static Num sub(Num a, Num b) { return a.Sub(b); }

   /**  (-) CALCULATOR SUBTRACTION METHOD */
  /**  CODE: Num.print(Num.sub("2.1", "3.2"), "\r\n"); //-1.1 */
  public static Num sub(String a, String b) { Num A = new Num(a); Num B = new Num(b); return A.Sub(B); }

   /**  (-) CALCULATOR SUBTRACTION METHOD */
  /**  CODE: Num.print(Num.sub(2, 3), "\r\n"); //-1.0 */
  public static Num sub(int a, int b) { Num A = new Num(a); Num B = new Num(b); return A.Sub(B); }

   /**  (-) CALCULATOR SUBTRACTION METHOD */
  /**  CODE: Num.print(Num.sub(1000000000L, 999999999L), "\r\n"); //1.0 */
  public static Num sub(long a, long b) { Num A = new Num(a); Num B = new Num(b); return A.Sub(B); }

   /**  (-) CALCULATOR SUBTRACTION METHOD */
  /**  CODE: Num.print(Num.sub(new BigInteger("100000000000000000000"), new BigInteger("99999999999999999999")), "\r\n"); //1.0 */
  public static Num sub(BigInteger a, BigInteger b) { Num A = new Num(a); Num B = new Num(b); return A.Sub(B); }
  
   /** (*) CALCULATOR MULTIPLICATION METHOD */
  /**  CODE: Num.print(Num.mul(new Num("2.1"), new Num("3.2")), "\r\n"); //6.72 */
  public static Num mul(Num a, Num b) { return a.Mul(b); }

  /**  CODE: Num.print(Num.mul("2.1", "3.2"), "\r\n"); //6.72 */
  public static Num mul(String a, String b) { Num A = new Num(a); Num B = new Num(b); return A.Mul(B); }

  /**  CODE: Num.print(Num.mul(2, 3), "\r\n"); //6.0 */
  public static Num mul(int a, int b) { Num A = new Num(a); Num B = new Num(b); return A.Mul(B); }

  /**  CODE: Num.print(Num.mul(1000000000L, 999999999L), "\r\n"); //999999999000000000.0 */
  public static Num mul(long a, long b) { Num A = new Num(a); Num B = new Num(b); return A.Mul(B); }

  /**  CODE: Num.print(Num.mul(new BigInteger("100000000000000000000"), new BigInteger("99999999999999999999")), "\r\n"); //9999999999999999999900000000000000000000.0 */
  public static Num mul(BigInteger a, BigInteger b) { Num A = new Num(a); Num B = new Num(b); return A.Mul(B); }

   /** (1/n) CALCULATOR NUMBER INVERSE METHOD -DEFAULT PRECISION BY 80 */
  /**  Num.print(Num.inv(new Num("3.0")), "\r\n"); //0.33333333333333333333333333333333333333333333333333333333333333333333333333333333 */
  public static Num inv(Num n) { int precision = 80; Num one = new Num("1.0", precision); return one.Div(n, precision); }

   /** (1/n) CALCULATOR NUMBER INVERSE METHOD -DEFAULT PRECISION BY 80 */
  //   CODE: Num.print(Num.inv("3.0"), "\r\n"); //0.33333333333333333333333333333333333333333333333333333333333333333333333333333333
  public static Num inv(String n) { int precision = 80; Num one = new Num("1.0", precision); return one.Div(new Num(n), precision); }
   
   /** (1/n) CALCULATOR NUMBER INVERSE METHOD -DEFAULT PRECISION BY 80 */
  /**   CODE: Num.print(Num.inv(3), "\r\n"); //0.33333333333333333333333333333333333333333333333333333333333333333333333333333333 */
  public static Num inv(int n) { int precision = 80; Num one = new Num("1.0", precision); return one.Div(new Num(n), precision); }
   
   /** (1/n) CALCULATOR NUMBER INVERSE METHOD -DEFAULT PRECISION BY 80 */
  /**   CODE: Num.print(Num.inv(3000000000L), "\r\n"); //0.00000000033333333333333333333333333333333333333333333333333333333333333333333333 */
  public static Num inv(long n) { int precision = 80; Num one = new Num("1.0", precision); return one.Div(new Num(n), precision); }
  
   /** (1/n) CALCULATOR NUMBER INVERSE METHOD -DEFAULT PRECISION BY 80 */
  /** CODE: Num.print(Num.inv(new BigInteger("3")).Round(6).toEXP(), "\r\n"); //3.33333e-1 */
  public static Num inv(BigInteger n) { int precision = 80; Num one = new Num("1.0", precision); return one.Div(new Num(n), precision); }
  
   /** (1/n) CALCULATOR NUMBER INVERSE METHOD */
  /** CODE: Num.print(Num.inv(new Num("9234567890123456789012345678901234567890.0"), 46).toEXP(), "\r\n"); //1.0828877018376991066195041298565409435617e-40 */
  public static Num inv(Num n, int precision) { Num one = new Num("1.0", precision); return one.Div(n, precision); }

   /** (1/n) CALCULATOR NUMBER INVERSE METHOD */
  /** CODE: Num.print(Num.inv("9234567890123456789012345678901234567890.0", 46).toEXP(), "\r\n"); //1.082887e-40 */
  public static Num inv(String n, int precision) { Num one = new Num("1.0", precision); return one.Div(new Num(n, precision), precision); }
   
   /** (1/n) CALCULATOR NUMBER INVERSE METHOD */
  /**  CODE: Num.print(Num.inv(3, 6).toEXP(), "\r\n"); //3.33333e-1 */
  public static Num inv(int n, int precision) { Num one = new Num("1.0", precision); return one.Div(new Num(n + ".0", precision), precision); }
   
   /** (1/n) CALCULATOR NUMBER INVERSE METHOD */
  /** CODE: Num.print(Num.inv(3L, 6).toEXP(), "\r\n"); //3.33333e-1 */
  public static Num inv(long n, int precision) { Num one = new Num("1.0", precision); return one.Div(new Num(n + ".0", precision), precision); }
   
   /** (1/n) CALCULATOR NUMBER INVERSE METHOD */
  /**  CODE: Num.print(Num.inv(new BigInteger("3"), 6).toEXP(), "\r\n"); //3.33333e-1 */
  public static Num inv(BigInteger n, int precision) { Num one = new Num("1.0", precision); return one.Div(new Num(n + ".0", precision), precision); }

   /** (/) CALCULATOR DIVISION METHOD */
  /**  CODE: Num.print(Num.div(new Num("1_234.001"), new Num("9.14")), "\r\n"); //135.01105032822757111597374179431072210065645514223194748358862144420131291028446389 */
  public static Num div(Num n, Num DIV) { //DEFAULT d = 80
      int d = 80;
      int D = DIV.L_n0 + n.L_n0 + DIV.L_n1 + n.L_n1; //AUTOMATIC FLOATING POINT COUNT
      return n.Div(DIV, d >= D ? d : D);
  }

   /** (/) CALCULATOR DIVISION METHOD */
  /** CODE: Num.print(Num.div("1_234.001", "9.14"), "\r\n"); //135.01105032822757111597374179431072210065645514223194748358862144420131291028446389 */
  public static Num div(String N, String div) { //DEFAULT d = 80
      int d = 80;
      Num DIV = new Num(div);
      Num n = new Num(N);
      int D = DIV.L_n0 + n.L_n0 + DIV.L_n1 + n.L_n1; //AUTOMATIC FLOATING POINT COUNT
      return n.Div(DIV, d >= D ? d : D);
  }
  
   /** (/) CALCULATOR DIVISION METHOD */
  /** CODE: Num.print(Num.div(1234, 914), "\r\n"); //1.35010940919037199124726477024070021881838074398249452954048140043763676148796498 */
  public static Num div(int N, int div) { //DEFAULT d = 80
      int d = 80;
      Num DIV = new Num(div);
      Num n = new Num(N);
      int D = DIV.L_n0 + n.L_n0 + DIV.L_n1 + n.L_n1; //AUTOMATIC FLOATING POINT COUNT
      return n.Div(DIV, d >= D ? d : D);
  }

   /** (/) CALCULATOR DIVISION METHOD */
  /**  CODE: Num.print(Num.div(1234L, 914L), "\r\n"); //1.35010940919037199124726477024070021881838074398249452954048140043763676148796498 */
  public static Num div(long N, long div) { //DEFAULT d = 80
      int d = 80;
      Num DIV = new Num(div);
      Num n = new Num(N);
      int D = DIV.L_n0 + n.L_n0 + DIV.L_n1 + n.L_n1; //AUTOMATIC FLOATING POINT COUNT
      return n.Div(DIV, d >= D ? d : D);
  }

   /** (/) CALCULATOR DIVISION METHOD */
  /**  CODE: Num.print(Num.div(new BigInteger("1234"), new BigInteger("914")), "\r\n"); //1.35010940919037199124726477024070021881838074398249452954048140043763676148796498 */
  public static Num div(BigInteger N, BigInteger div) { //DEFAULT d = 80
    int d = 80;
    Num DIV = new Num(div);
    Num n = new Num(N);
    int D = DIV.L_n0 + n.L_n0 + DIV.L_n1 + n.L_n1; //AUTOMATIC FLOATING POINT COUNT
    return n.Div(DIV, d >= D ? d : D);
  }

   /** (/) CALCULATOR DIVISION METHOD */
  /**  CODE: Num.print(Num.div(new Num("1_234.001"), new Num("9.14"), 100), "\r\n"); //135.0110503282275711159737417943107221006564551422319474835886214442013129102844638949671772428884026258 */
  public static Num div(Num n, Num DIV, int precision) { 
      int d = precision;
      int D = DIV.L_n0 + n.L_n0 + DIV.L_n1 + n.L_n1; //AUTOMATIC FLOATING POINT COUNT
      return n.Div(DIV, d >= D ? d : D);
  }

   /** (/) CALCULATOR DIVISION METHOD */
  /**  CODE: Num.print(Num.div("1_234.001", "9.14", 100), "\r\n"); //135.0110503282275711159737417943107221006564551422319474835886214442013129102844638949671772428884026258 */
  public static Num div(String N, String div, int precision) { 
      int d = precision;
      Num DIV = new Num(div);
      Num n = new Num(N);
      int D = DIV.L_n0 + n.L_n0 + DIV.L_n1 + n.L_n1; //AUTOMATIC FLOATING POINT COUNT
      return n.Div(DIV, d >= D ? d : D);
  }
  
   /** (/) CALCULATOR DIVISION METHOD */
  /**  CODE: Num.print(Num.div(1234, 914, 100), "\r\n"); //1.3501094091903719912472647702407002188183807439824945295404814004376367614879649890590809628008752735 */
  public static Num div(int N, int div, int precision) { 
      int d = precision;
      Num DIV = new Num(div);
      Num n = new Num(N);
      int D = DIV.L_n0 + n.L_n0 + DIV.L_n1 + n.L_n1; //AUTOMATIC FLOATING POINT COUNT
      return n.Div(DIV, d >= D ? d : D);
  }

   /** (/) CALCULATOR DIVISION METHOD */
  /**  CODE:  Num.print(Num.div(1234L, 914L, 100), "\r\n"); //1.3501094091903719912472647702407002188183807439824945295404814004376367614879649890590809628008752735 */
  public static Num div(long N, long div, int precision) {
      int d = precision;
      Num DIV = new Num(div);
      Num n = new Num(N);
      int D = DIV.L_n0 + n.L_n0 + DIV.L_n1 + n.L_n1; //AUTOMATIC FLOATING POINT COUNT
      return n.Div(DIV, d >= D ? d : D);
  }

   /** (/) CALCULATOR DIVISION METHOD */
  /**  CODE: Num.print(Num.div(new BigInteger("1234"), new BigInteger("914"), 100), "\r\n"); //1.3501094091903719912472647702407002188183807439824945295404814004376367614879649890590809628008752735 */
  public static Num div(BigInteger N, BigInteger div, int precision) { 
    int d = precision;
    Num DIV = new Num(div);
    Num n = new Num(N);
    int D = DIV.L_n0 + n.L_n0 + DIV.L_n1 + n.L_n1; //AUTOMATIC FLOATING POINT COUNT
    return n.Div(DIV, d >= D ? d : D);
  }
  
   /** (%) MODULE BINARY OPERATOR (NUM FLOATING POINT DIVISION REMAINDER) */
  /**  CODE: Num.print(Num.mod(new Num("1_234.001"), new Num( "9.14")), "\r\n"); //0.101 */
  public static Num mod(Num n, Num DIV) { return n.Mod(DIV); }
  
   /** (%) MODULE BINARY OPERATOR (NUM FLOATING POINT DIVISION REMAINDER) */
  /**  CODE: Num.print(Num.mod("1_234.001", "9.14"), "\r\n"); //0.101 */
  public static Num mod(String n, String DIV) { return new Num(n).Mod(new Num(DIV)); }

   /** (%) MODULE BINARY OPERATOR (NUM FLOATING POINT DIVISION REMAINDER) */
  /**  CODE: Num.print(Num.mod(11, 7), "\r\n"); //4.0 */
  public static Num mod(int n, int DIV) { return new Num(n).Mod(new Num(DIV)); }

   /** (%) MODULE BINARY OPERATOR (NUM FLOATING POINT DIVISION REMAINDER) */
  /**  CODE: Num.print(Num.mod(11L, 7L), "\r\n"); //4.0 */
  public static Num mod(long n, long DIV) { return new Num(n).Mod(new Num(DIV)); }

   /** (%) MODULE BINARY OPERATOR (NUM FLOATING POINT DIVISION REMAINDER) */
  /**  CODE: Num.print(Num.mod(new BigInteger("11"), new BigInteger("7")), "\r\n"); //4.0 */
  public static Num mod(BigInteger n, BigInteger DIV) { return new Num(n).Mod(new Num(DIV)); }

   /** (/) CALCULATOR DIVISION AND MODULUS METHOD RETURN TWO ELEMENT ARRAY */
  /**  CODE: Num[] QR = Num.divmod(new Num("1_234.001"),new Num( "9.14")); Num.print(QR[0].toString(), "\r\n", QR[1] + "\r\n"); //135.0 0.101 */
  public static Num[] divmod(Num n, Num DIV) { return n.DivMod(DIV); }

   /** (/) CALCULATOR DIVISION AND MODULUS METHOD RETURN TWO ELEMENT ARRAY */
  /**  CODE: Num[] QR = Num.divmod("1_234.001", "9.14"); Num.print(QR[0].toString(), "\r\n", QR[1] + "\r\n"); //135.0 0.101 */
  public static Num[] divmod(String n, String DIV) { return new Num(n).DivMod(new Num(DIV)); }

   /** (/) CALCULATOR DIVISION AND MODULUS METHOD RETURN TWO ELEMENT ARRAY */
  /**  CODE: Num[] QR = Num.divmod(100, 84); Num.print(QR[0].toString(), "\r\n", QR[1] + "\r\n"); //1.0 16.0 */
  public static Num[] divmod(int n, int DIV) { return new Num(n).DivMod(new Num(DIV)); }

   /** (/) CALCULATOR DIVISION AND MODULUS METHOD RETURN TWO ELEMENT ARRAY */
  /**  CODE: Num[] QR = Num.divmod(1000000000L, 999999984L); Num.print(QR[0].toString(), "\r\n", QR[1] + "\r\n"); //1.0 16.0 */
  public static Num[] divmod(long n, long DIV) { return new Num(n).DivMod(new Num(DIV)); }

   /** (/) CALCULATOR DIVISION AND MODULUS METHOD RETURN TWO ELEMENT ARRAY */
  /**  CODE: Num[] QR = Num.divmod(new BigInteger("1000000000000000"), new BigInteger("999999999999984")); Num.print(QR[0].toString(), "\r\n", QR[1] + "\r\n"); //1.0 16.0 */
  public static Num[] divmod(BigInteger n, BigInteger DIV) { return new Num(n).DivMod(new Num(DIV)); }

   /** INCREMENT VARIABLE ADDING METHOD -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num(3); Num.inc(a).Print("\r\n"); //4.0 */
  public static Num inc(Num m) { return m.Inc(1); }

   /** INCREMENT VARIABLE ADDING METHOD -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num(3); Num.inc(a, new Num("2.1")).Print("\r\n"); //5.1 */
  public static Num inc(Num m, Num i) { return m.Inc(i); }

   /** INCREMENT VARIABLE ADDING METHOD -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num(3); Num.inc(a, "2.1").Print("\r\n"); //5.1 */
  public static Num inc(Num m, String i) { return m.Inc(i); }

   /** INCREMENT VARIABLE ADDING METHOD -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num(3); Num.inc(a, 2).Print("\r\n"); //5.0 */
  public static Num inc(Num m, int i) { return m.Inc(i); }

   /** INCREMENT VARIABLE ADDING METHOD -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num(3); Num.inc(a, 2L).Print("\r\n"); //5.0 */
  public static Num inc(Num m, long i) { return m.Inc(i); }

   /** INCREMENT VARIABLE ADDING METHOD -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num(3); Num.inc(a, new BigInteger("2")).Print("\r\n"); //5.0 */
  public static Num inc(Num m, BigInteger i) { return m.Inc(i); }

   /** INCREMENT VARIABLE MULTIPLYING METHOD -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num(3); Num.incmul(a).Print("\r\n"); //6.0 */
  public static Num incmul(Num m) { return m.IncMul(2); }

   /** INCREMENT VARIABLE MULTIPLYING METHOD -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num(3); Num.incmul(a, new Num("2.1")).Print("\r\n"); //6.3 */
  public static Num incmul(Num m, Num i) { return m.IncMul(i); }

   /** INCREMENT VARIABLE MULTIPLYING METHOD -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num(3); Num.incmul(a, "2.1").Print("\r\n"); //6.3 */
  public static Num incmul(Num m, String i) { return m.IncMul(i); }

   /** INCREMENT VARIABLE MULTIPLYING METHOD -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num(3); Num.incmul(a, 2).Print("\r\n"); //6.0 */
  public static Num incmul(Num m, int i) { return m.IncMul(i); }

   /** INCREMENT VARIABLE MULTIPLYING METHOD -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num(3); Num.incmul(a, 2L).Print("\r\n"); //6.0 */
  public static Num incmul(Num m, long i) { return m.IncMul(i); }

   /** INCREMENT VARIABLE MULTIPLYING METHOD -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num(3); Num.incmul(a, new BigInteger("2")).Print("\r\n"); //6.0 */
  public static Num incmul(Num m, BigInteger i) { return m.IncMul(i); }

   /** DECREMENT VARIABLE SUBTRACTING METHOD -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num(3); Num.dec(a).Print("\r\n"); //2.0 */
  public static Num dec(Num m) { return m.Dec(1); }

   /** DECREMENT VARIABLE SUBTRACTING METHOD -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num(3); Num.dec(a, new Num("2.1")).Print("\r\n"); //0.9 */
  public static Num dec(Num m, Num i) { return m.Dec(i); }

   /** DECREMENT VARIABLE SUBTRACTING METHOD -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num(3); Num.dec(a, "2.1").Print("\r\n"); //0.9 */
  public static Num dec(Num m, String i) { return m.Dec(i); }

   /** DECREMENT VARIABLE SUBTRACTING METHOD -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num(3); Num.dec(a, 2).Print("\r\n"); //1.0 */
  public static Num dec(Num m, int i) { return m.Dec(i); }

   /** DECREMENT VARIABLE SUBTRACTING METHOD -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num(3); Num.dec(a, 2L).Print("\r\n"); //1.0 */
  public static Num dec(Num m, long i) { return m.Dec(i); }

   /** DECREMENT VARIABLE SUBTRACTING METHOD -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num(3); Num.dec(a, new BigInteger("2")).Print("\r\n"); //1.0 */
  public static Num dec(Num m, BigInteger i) { return m.Dec(i); }

   /** DECREMENT VARIABLE DIVIDING METHOD -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num(3); Num.decdiv(a).Print("\r\n"); //1.5 */
  public static Num decdiv(Num m) { return m.DecDiv(2); }

   /** DECREMENT VARIABLE SUBTRACTING METHOD -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num("7.77"); Num.decdiv(a, new Num("2.1")).Print("\r\n"); //3.7 */
  public static Num decdiv(Num m, Num i) { return m.DecDiv(i); }

   /** DECREMENT VARIABLE SUBTRACTING METHOD -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num("7.77"); Num.decdiv(a, "2.1").Print("\r\n"); //3.7 */
  public static Num decdiv(Num m, String i) { return m.DecDiv(i); }

   /** DECREMENT VARIABLE SUBTRACTING METHOD -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num("7.77"); Num.decdiv(a, 2).Print("\r\n"); //3.885 */
  public static Num decdiv(Num m, int i) { return m.DecDiv(i); }

   /** DECREMENT VARIABLE SUBTRACTING METHOD -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num("7.77"); Num.decdiv(a, 2L).Print("\r\n"); //3.885 */
  public static Num decdiv(Num m, long i) { return m.DecDiv(i); }

   /** DECREMENT VARIABLE SUBTRACTING METHOD -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num("7.77"); Num.decdiv(a, new BigInteger("2")).Print("\r\n"); //3.885 */
  public static Num decdiv(Num m, BigInteger i) { return m.DecDiv(i); }

   /** minus_unary INVERTED SIGN OF this ARGUMENT  (UNARY MINUS)
  /**  CODE: Num a = new Num("+3.14"); a.Print("\r\n"); Num.minus_unary(a); a.Print("\r\n"); //3.14 -3.14 */
  public static Num minus_unary(Num N) { return N.Invsign(); }
  
   /** invsign INVERTED SIGN OF this ARGUMENT (UNARY MINUS) */
  /**  CODE: Num a = new Num("-3.14"); a.Print("\r\n"); Num.invsign(a); a.Print("\r\n"); //-3.14 3.14 */
  public static Num invsign(Num N) { return N.Invsign(); }
  
   /**  plus, SET PLUS SIGN OF Num */
  /**   CODE: Num a = new Num("-3.14"); a.Print("\r\n"); Num.plus(a); a.Print("\r\n"); //-3.14 3.14 */
  public static Num plus(Num N) { return N.Plus(); }
  
   /**  minus, SET MINUS SIGN OF Num */
  /**   CODE: Num a = new Num("3.14"); a.Print("\r\n"); Num.minus(a); a.Print("\r\n"); //3.14 -3.14 */
  public static Num minus(Num N) { return N.Minus(); }
  
   /** CLEAR LEFT CHARACTER STRING */
  /**  CODE: String s = "00001230"; String sc = Num.lstrip(s); Num.print(sc, "\r\n"); //1230 */
  public static String lstrip(String str) { return lstrip(str, "0"); } //DEFAULT REMOVING LEADING ZEROs
  
   /** CLEAR LEFT CHARACTER STRING */
  /**  CODE: String s = "-1230"; String sc = Num.lstrip(s, "-"); Num.print(sc, "\r\n"); //1230 */
  public static String lstrip(String str, String ch) { return str.replaceAll("^" + Pattern.quote(ch) + "+", ""); }
    
    /**  CLEAR RIGHT CHARACTER STRING */
   /**  CODE: String s = "1230.20030000"; String sc = Num.rstrip(s); Num.print(sc, "\r\n"); //1230.2003 */
   public static String rstrip(String str) { return rstrip(str, "0"); } //DEFAULT REMOVING TRAILING ZEROs
   
    /**  CLEAR RIGHT CHARACTER STRING */
   /**  CODE: String s = "1230.2003+++"; String sc = Num.rstrip(s, "+"); Num.print(sc, "\r\n"); //1230.2003 */
   public static String rstrip(String str, String ch) { return str.replaceAll(Pattern.quote(ch) + "+$", ""); }
  
    /**  is_numstr BOOLEAN CHECKS NUMERIC STRING VALIDATION */
   /**   CODE: String a = "7.14"; Num.print(Num.is_numstr(a), "\r\n"); //true */
   public static boolean is_numstr(String n) { 
       try { new Num(n); return true;
       } catch (Exception e) { return false; }
    }

   /** double2num, FLOAT TO Num CONVERSION */  
  /**  CODE: Num.double2num(3.14).Print("\r\n"); //3.14 (Num) */
  public static Num double2num(double f) { return new Num(f + ""); }
 
   /** float2num, FLOAT TO Num CONVERSION */  
  /**  CODE: Num.float2num(3.14).Print("\r\n"); //3.14 (Num) */
  public static Num float2num(double f) { return new Num(f + ""); }
 
   /** float2num_list, FLOAT TO NUM LIST CONVERSION */
  /**  CODE: double[] D = { -110.0, +0.14, -20.456120, 1200.0654, 0.0, 3.141592654, 2.7182818281234567899 }; ArrayList<Num> A = Num.float2num_list(D); for(Num a : A) Num.print(a, "\r\n"); //3.141592654 2.7182818281234566 */
  public static ArrayList<Num> float2num_list(double[] L) {
    ArrayList<Num> LN = new ArrayList<>();
    for(double d : L) LN.add(new Num(d + "")); 
    return LN;
  }
  
     /**  trunc, FLOATING POINT TRUNCATION */
    /**   CODE: Num a = new Num("27.953"); Num.print(Num.trunc(a), "\r\n"); //27.0 */
    public static Num trunc(Num a) { return a.Trunc(0); }

     /**  trunc, FLOATING POINT TRUNCATION */
    /**   CODE: Num.print(Num.trunc("27.953"), "\r\n"); //27.0 */
    public static Num trunc(String a) { return new Num(a).Trunc(0); }

     /**  trunc, FLOATING POINT TRUNCATION */
    /**   CODE: Num a = new Num("27.953"); Num.print(Num.trunc(a, 1), "\r\n"); //27.9 */
    public static Num trunc(Num a, int d) { return a.Trunc(d); }
    
     /**  trunc, FLOATING POINT TRUNCATION */
    /**   CODE: Num.print(Num.trunc("27.953", -1), "\r\n"); //20.0 */
    public static Num trunc(String a, int d) { return new Num(a).Trunc(d); }
    
     /**  round_floor, FLOOR ROUNDING -RELATIVE ROUND DOWN d=1: 0.12 => 0.1 -0.12 => -0.2 */
    /**   CODE: Num a = new Num("27.953"); Num.print(Num.round_floor(a), "\r\n"); //27.0 */
    public static Num round_floor(Num a) { return a.Round_floor(0); } //-> RELATIVE VALUE (REAL NUMBER R)

     /**  round_floor, FLOOR ROUNDING -RELATIVE ROUND DOWN d=1: 0.12 => 0.1 -0.12 => -0.2 */
    /**  CODE: Num.print(Num.round_floor("-27.953"), "\r\n"); //-28.0 */
    public static Num round_floor(String a) { return new Num(a).Round_floor(0); } //-> RELATIVE VALUE (REAL NUMBER R)

     /**  round_floor, FLOOR ROUNDING -RELATIVE ROUND DOWN d=1: 0.12 => 0.1 -0.12 => -0.2 */
    /**   CODE: Num a = new Num("27.953"); Num.print(Num.round_floor(a, 2), "\r\n"); //27.95 */
    public static Num round_floor(Num a, int d) { return a.Round_floor(d); } //-> RELATIVE VALUE (REAL NUMBER R)

     /**  round_floor, FLOOR ROUNDING -RELATIVE ROUND DOWN d=1: 0.12 => 0.1 -0.12 => -0.2 */
    /**   CODE: Num.print(Num.round_floor("-27.958", 2), "\r\n"); //-27.96 */
    public static Num round_floor(String a, int d) { return new Num(a).Round_floor(d); } //-> RELATIVE VALUE (REAL NUMBER R)

     /**  round, HALF UP ROUNDING - COMMON STANDARD -RELATIVE ROUND_HALF_CEIL d=1: 0.15 => 0.2 -0.15 => -0.1 */
    /**   CODE: Num a = new Num("27.955"); Num.print(Num.round(a), "\r\n"); //27.96 */
    public static Num round(Num a) { return a.Round(2); }

     /**  round, HALF UP ROUNDING - COMMON STANDARD -RELATIVE ROUND_HALF_CEIL d=1: 0.15 => 0.2 -0.15 => -0.1 */
    /**   CODE: Num.print(Num.round("27.955"), "\r\n"); //27.96 */
    public static Num round(String a) { return new Num(a).Round(2); }

     /**  round, HALF UP ROUNDING - COMMON STANDARD -RELATIVE ROUND_HALF_CEIL d=1: 0.15 => 0.2 -0.15 => -0.1 */
    /**   CODE: Num a = new Num("-26.5"); Num.print(Num.round(a, 0), "\r\n"); //-26.0 */
    public static Num round(Num a, int d) { return a.Round(d); }

     /**  round, HALF UP ROUNDING - COMMON STANDARD -RELATIVE ROUND_HALF_CEIL d=1: 0.15 => 0.2 -0.15 => -0.1 */
    /**  CODE: Num.print(Num.round("25.5", 0), "\r\n"); //26.0 */
    public static Num round(String a, int d) { return new Num(a).Round(d); }

     /**  round_ceil, CEIL ROUNDING -RELATIVE ROUND UP d=1: 0.12 => 0.2 -0.12 => -0.1 */
    /**   CODE:Num a = new Num("27.953"); Num.print(Num.round_ceil(a), "\r\n"); //28.0 */
    public static Num round_ceil(Num a) { return a.Round_ceil(0); }

     /**  round_ceil, CEIL ROUNDING -RELATIVE ROUND UP d=1: 0.12 => 0.2 -0.12 => -0.1 */
    /**   CODE: Num.print(Num.round_ceil("27.953"), "\r\n"); //28.0 */
    public static Num round_ceil(String a) { return new Num(a).Round_ceil(0); }

     /**  round_ceil, CEIL ROUNDING -RELATIVE ROUND UP d=1: 0.12 => 0.2 -0.12 => -0.1 */
    /**   CODE: Num.print(Num.round_ceil(new Num("27.953"), 2), "\r\n"); //27.96 */
    public static Num round_ceil(Num a, int d) { return a.Round_ceil(d); }

     /**  round_ceil, CEIL ROUNDING -RELATIVE ROUND UP d=1: 0.12 => 0.2 -0.12 => -0.1 */
    /**   CODE: String a = "27.953"; Num.print(Num.round_ceil(a, 2), "\r\n"); //27.96 */
    public static Num round_ceil(String a, int d) { return new Num(a).Round_ceil(d); }

     /**  round_bank, HALF EVEN ROUNDING */
    /**   CODE: Num.print(Num.round_bank(new Num("27.965")), "\r\n"); //27.96 */
    public static Num round_bank(Num a) { return a.Round_bank(2); }

     /**  round_bank, HALF EVEN ROUNDING */
    /**   CODE: Num.print(Num.round_bank("27.965"), "\r\n"); //27.96 */
    public static Num round_bank(String a) { return new Num(a).Round_bank(2); }

     /**  round_bank, HALF EVEN ROUNDING */
    /**   CODE: Num.print(Num.round_bank(new Num("21.5"), 0), "\r\n"); //22.0 */
    public static Num round_bank(Num a, int d) { return a.Round_bank(d); }

     /**  round_bank, HALF EVEN ROUNDING */
    /**   CODE: Num.print(Num.round_bank("26.5", 0), "\r\n"); //26.0 */
    public static Num round_bank(String a, int d) { return new Num(a).Round_bank(d); }

     /**  CALCULATOR, Array by sum METHOD */
    /**  CODE: Num[] cart = { new Num("0.1"), new Num("0.2"), new Num("0.3"), new Num("0.4") }; Num.print(Num.sum(cart), "\r\n"); //1.0 */
    public static Num sum(Num[] cart) { Num sum = new Num(0); for (Num v : cart) sum = sum.Add(v); return sum; }

     /**  CALCULATOR, ArrayList by sum METHOD */
    /**  CODE: ArrayList<Num> cart = new ArrayList<>(); cart.add(new Num("0.1")); cart.add(new Num("0.2")); cart.add(new Num("0.3")); cart.add(new Num("0.4")); Num.print(Num.sum(cart), "\r\n"); //1.0 */
    public static Num sum(ArrayList<Num> cart) { Num sum = new Num(0); for (Num v : cart) sum = sum.Add(v); return sum; }

     /**  CALCULATOR, String by sum METHOD */
    /**   CODE: String[] cart = { "0.1", "0.2", "0.3", "0.4" }; Num.print(Num.sum(cart), "\r\n"); //1.0 */
    public static Num sum(String[] cart) { Num sum = new Num(0); for (String v : cart) sum = sum.Add(new Num(v)); return sum; }    

     /**  CALCULATOR, int by sum METHOD */
    /**   CODE: int[] cart = { 2147483647, 2147483646, 2147483645, 2147483644 }; Num.print(Num.sum(cart), "\r\n"); //8589934582.0 */
    public static Num sum(int[] cart) { Num sum = new Num(0); for (int v : cart) sum = sum.Add(new Num(v)); return sum; }    
    
     /**  CALCULATOR, long by sum METHOD */
    /**  CODE: long[] cart = { -4294967295L, -4294967296L, -4294967297L, -4294967298L }; Num.print(Num.sum(cart), "\r\n"); //-17179869186.0 */
    public static Num sum(long[] cart) { Num sum = new Num(0); for (long v : cart) sum = sum.Add(new Num(v)); return sum; }    
    
     /**  CALCULATOR, BigInteger by sum METHOD */
    /**  CODE: BigInteger[] cart = { new BigInteger("4294967295"), new BigInteger("4294967296"), new BigInteger("4294967297"), new BigInteger("4294967298") }; Num.print(Num.sum(cart), "\r\n"); //17179869186.0 */
    public static Num sum(BigInteger[] cart) { Num sum = new Num(0); for (BigInteger v : cart) sum = sum.Add(new Num(v)); return sum; }    
    
     /**  CALCULATOR, Array by mean METHOD */
    /**   CODE: Num[] cart = { new Num("0.1"), new Num("0.2"), new Num("0.3"), new Num("0.4") }; Num.print(Num.mean(cart), "\r\n"); //0.25 */
    public static Num mean(Num[] L) { return Num.sum(L).Div(L.length); }

     /**  CALCULATOR, ArrayList by mean METHOD */
    /**   CODE: ArrayList<Num> cart = new ArrayList<>(); cart.add(new Num("0.1")); cart.add(new Num("0.2")); cart.add(new Num("0.3")); cart.add(new Num("0.4")); Num.print(Num.mean(cart), "\r\n"); //0.25 */
    public static Num mean(ArrayList<Num> L) { return Num.sum(L).Div(L.size()); }

     /**  CALCULATOR, String by mean METHOD */
    /**   CODE: String[] cart = { "0.1", "0.2", "0.3", "0.4" }; Num.print(Num.mean(cart), "\r\n"); //0.25 */
    public static Num mean(String[] L) { Num sum = new Num(0); for (String v : L) sum = sum.Add(new Num(v)); return sum.Div(L.length); }
    
     /**  CALCULATOR, int by mean METHOD */
    /**   CODE: int[] cart = { 1, 2, 3, 4 }; Num.print(Num.mean(cart), "\r\n"); //2.5 */
    public static Num mean(int[] L) { Num sum = new Num(0); for (int v : L) sum = sum.Add(new Num(v)); return sum.Div(L.length); }
    
     /**  CALCULATOR, long by mean METHOD */
    /**   CODE: long[] cart = { 1L, 2L, 3L, 4L }; Num.print(Num.mean(cart), "\r\n"); //2.5 */
    public static Num mean(long[] L) { Num sum = new Num(0); for (long v : L) sum = sum.Add(new Num(v)); return sum.Div(L.length); }
    
     /**  CALCULATOR, BigInteger by mean METHOD */
    /**   CODE: BigInteger[] cart = { new BigInteger("4294967295"), new BigInteger("4294967296"), new BigInteger("4294967297"), new BigInteger("4294967298") }; Num.print(Num.mean(cart), "\r\n"); //4294967296.5 */
    public static Num mean(BigInteger[] L) { Num sum = new Num(0); for (BigInteger v : L) sum = sum.Add(new Num(v)); return sum.Div(L.length); }
    
     /**  CALCULATOR, Array by min METHOD */
    /**   CODE: Num[] cart = { new Num("0.1"), new Num("-0.2"), new Num("0.3"), new Num("0.4") }; Num.print(Num.min(cart), "\r\n"); //-0.2 */
    public static Num min(Num[] L) {
        Num m, t;
        m = new Num(L[0]);                    //FIRST ELEMENT
        for (int j = 1; j < L.length; j++) { //DIRECTLY ACCESS EACH ELEMENT
            t = new Num(L[j]);
            if (t.LT(m)) m = t;
        }
        return m;
    }
    
     /** CALCULATOR, ArrayList by min METHOD */
    /**  CODE: ArrayList<Num> cart = new ArrayList<>(); cart.add(new Num("0.4")); cart.add(new Num("0.3")); cart.add(new Num("0.1")); cart.add(new Num("0.4")); Num.print(Num.min(cart), "\r\n"); //0.1 */
    public static Num min(ArrayList<Num> L) {
        Num m, t;
        m = new Num(L.get(0));                //FIRST ELEMENT
        for (int j = 1; j < L.size(); j++) { //DIRECTLY ACCESS EACH ELEMENT
            t = new Num(L.get(j));
            if (t.LT(m)) m = t;
        }
        return m;
    }
    
     /** CALCULATOR, String by min METHOD */
    /**  CODE: String[] cart = { "0.1", "0.2", "-0.3", "0.4" }; Num.print(Num.min(cart), "\r\n"); //-0.3 */
    public static Num min(String[] L) {
        Num m, t;
        m = new Num(L[0]);                    //FIRST ELEMENT
        for (int j = 1; j < L.length; j++) { //DIRECTLY ACCESS EACH ELEMENT
            t = new Num(L[j]);
            if(t.LT(m)) m = t;
        } 
        return m;
    }

     /** CALCULATOR, int by min METHOD */
    /**  CODE: int[] cart = { 1, -2, 3, 4 }; Num.print(Num.min(cart), "\r\n"); //-2.0 */
    public static Num min(int[] L) {
    	Num m, t;
    	m = new Num(L[0]);                    //FIRST ELEMENT
    	for (int j = 1; j < L.length; j++) { //DIRECTLY ACCESS EACH ELEMENT
    		t = new Num(L[j]);
    		if(t.LT(m)) m = t;
    	} 
    	return m;
    }
    
     /** CALCULATOR, long by min METHOD */
    /**  CODE: long[] cart = { 1L, -2L, 3L, 4L }; Num.print(Num.min(cart), "\r\n"); //-2.0 */
    public static Num min(long[] L) {
    	Num m, t;
    	m = new Num(L[0]);                    //FIRST ELEMENT
    	for (int j = 1; j < L.length; j++) { //DIRECTLY ACCESS EACH ELEMENT
    		t = new Num(L[j]);
    		if(t.LT(m)) m = t;
    	} 
    	return m;
    }
    
     /** CALCULATOR, BigInteger by min METHOD */
    /**  CODE: BigInteger[] cart = { new BigInteger("4294967295"), new BigInteger("-4294967296"), new BigInteger("4294967297"), new BigInteger("4294967298") }; Num.print(Num.min(cart), "\r\n"); //-4294967296.0 */
    public static Num min(BigInteger[] L) {
    	Num m, t;
    	m = new Num(L[0]);                    //FIRST ELEMENT
    	for (int j = 1; j < L.length; j++) { //DIRECTLY ACCESS EACH ELEMENT
    		t = new Num(L[j]);
    		if(t.LT(m)) m = t;
    	} 
    	return m;
    }
    
     /** CALCULATOR, Array by max METHOD */
    /**  CODE: Num[] cart = { new Num("0.1"), new Num("-0.2"), new Num("0.3"), new Num("-0.4") }; Num.print(Num.max(cart), "\r\n"); //0.3 */
    public static Num max(Num[] L) {
        Num m, t;
        m = new Num(L[0]); 					  //FIRST ELEMENT
        for (int j = 1; j < L.length; j++) { //DIRECTLY ACCESS EACH ELEMENT
            t = new Num(L[j]);
            if (t.GT(m))
                m = t;
        }
        return m;
    }
    
     /** CALCULATOR, ArrayList by max METHOD */
    /**  CODE: ArrayList<Num> cart = new ArrayList<>(); cart.add(new Num("0.4")); cart.add(new Num("0.3")); cart.add(new Num("0.1")); cart.add(new Num("0.3")); Num.print(Num.max(cart), "\r\n"); //0.4 */
    public static Num max(ArrayList<Num> L) {
        Num m, t;
        m = new Num(L.get(0)); 			      //FIRST ELEMENT
        for (int j = 1; j < L.size(); j++) { //DIRECTLY ACCESS EACH ELEMENT
            t = new Num(L.get(j));
            if (t.GT(m)) m = t;
        }
        return m;
    }
    
     /** CALCULATOR, String by max METHOD */
    /**  CODE: String[] cart = { "0.1", "0.2", "-0.3", "-0.4" }; Num.print(Num.max(cart), "\r\n"); //0.2 */
    public static Num max(String[] L) {
        Num m, t;
        m = new Num(L[0]); 					  //FIRST ELEMENT
        for (int j = 1; j < L.length; j++) { //DIRECTLY ACCESS EACH ELEMENT
            t = new Num(L[j]);
            if (t.GT(m))
                m = t;
        }
        return m;
    }
    
     /** CALCULATOR, int by max METHOD */
    /**  CODE: int[] cart = { 1, 4, 5, 3 }; Num.print(Num.max(cart), "\r\n"); //5.0 */
    public static Num max(int[] L) {
    	Num m, t;
    	m = new Num(L[0]); 					  //FIRST ELEMENT
    	for (int j = 1; j < L.length; j++) { //DIRECTLY ACCESS EACH ELEMENT
    		t = new Num(L[j]);
    		if (t.GT(m))
    			m = t;
    	}
    	return m;
    }
    
     /** CALCULATOR, long by max METHOD */
    /**  CODE: long[] cart = { 1L, 4L, 5L, 3L }; Num.print(Num.max(cart), "\r\n"); //5.0 */
    public static Num max(long[] L) {
    	Num m, t;
    	m = new Num(L[0]); 					  //FIRST ELEMENT
    	for (int j = 1; j < L.length; j++) { //DIRECTLY ACCESS EACH ELEMENT
    		t = new Num(L[j]);
    		if (t.GT(m))
    			m = t;
    	}
    	return m;
    }
    
     /** CALCULATOR, BigInteger by max METHOD */
    /**  CODE: BigInteger[] cart = { new BigInteger("4294967295"), new BigInteger("-4294967296"), new BigInteger("4294967297"), new BigInteger("-4294967298") }; Num.print(Num.max(cart), "\r\n"); //4294967297.0 */
    public static Num max(BigInteger[] L) {
    	Num m, t;
    	m = new Num(L[0]); 					  //FIRST ELEMENT
    	for (int j = 1; j < L.length; j++) { //DIRECTLY ACCESS EACH ELEMENT
    		t = new Num(L[j]);
    		if (t.GT(m))
    			m = t;
    	}
    	return m;
    }
    
     /** CALCULATOR, Num Array by minmax METHOD */
    /**  CODE: Num[] cart = { new Num("19.31999"), new Num("19.32"), new Num("18.37"), new Num("-15.13"), new Num("-15.12") }; Num.print(Num.minmax(cart), "\r\n"); //[-15.13, 19.32] */
    public static Num[] minmax(Num A[]) {
        Num V[] = new Num[2];
        V[0] = Num.min(A);
        V[1] = Num.max(A);
        return V;
    }    
    
     /** CALCULATOR, Num ArrayList by minmax METHOD */
    /**  CODE: ArrayList<Num> cart = new ArrayList<>(); cart.add(new Num("0.4")); cart.add(new Num("0.3")); cart.add(new Num("0.1")); cart.add(new Num("0.3")); Num.print(Num.minmax(cart), "\r\n"); //[0.1, 0.4] */
    public static Num[] minmax(ArrayList<Num> A) {
    	Num V[] = new Num[2];
    	V[0] = Num.min(A);
    	V[1] = Num.max(A);
    	return V;
    }    
    
     /** CALCULATOR, numeric String Array by minmax METHOD */
    /**  CODE: String[] cart = { "0.1", "0.2", "-0.3", "-0.4" }; Num.print(Num.minmax(cart), "\r\n"); //[-0.4, 0.2] */
    public static Num[] minmax(String A[]) {
    	Num V[] = new Num[2];
    	V[0] = Num.min(A);
    	V[1] = Num.max(A);
    	return V;
    }    
    
     /** CALCULATOR, int Array by minmax METHOD */
    /**  CODE: int[] cart = { 2, 4, -1, 3 }; Num.print(Num.minmax(cart), "\r\n"); //[-1.0, 4.0] */
    public static Num[] minmax(int A[]) {
    	Num V[] = new Num[2];
    	V[0] = Num.min(A);
    	V[1] = Num.max(A);
    	return V;
    }    
    
     /** CALCULATOR, long Array by minmax METHOD */
    /**  CODE: long[] cart = { 2L, 4L, -1L, 3L }; Num.print(Num.minmax(cart), "\r\n"); //[-1.0, 4.0] */
    public static Num[] minmax(long A[]) {
    	Num V[] = new Num[2];
    	V[0] = Num.min(A);
    	V[1] = Num.max(A);
    	return V;
    }    
    
     /** CALCULATOR, BigInteger Array by minmax METHOD */
    /**  CODE: BigInteger[] cart = { new BigInteger("4294967295"), new BigInteger("-4294967296"), new BigInteger("4294967297"), new BigInteger("-4294967298") }; Num.print(Num.minmax(cart), "\r\n"); //[-4294967298.0, 4294967297.0] */
    public static Num[] minmax(BigInteger A[]) {
    	Num V[] = new Num[2];
    	V[0] = Num.min(A);
    	V[1] = Num.max(A);
    	return V;
    }    
    
    /** CALCULATOR, Num Array by suminmax METHOD */
   /**  CODE: Num[] cart = { new Num("5.0"), new Num("1.0"), new Num("4.0"), new Num("3.0"), new Num("2.0") }; Num.print(Num.suminmax(cart), "\r\n"); //[15.0, 3.0, 1.0, 5.0] */
   public static Num[] suminmax(Num A[]) {
       Num V[] = new Num[4];
       V[0] = Num.sum(A);
       V[1] = Num.mean(A);
       V[2] = Num.min(A);
       V[3] = Num.max(A);
       return V;
   }    

    /** CALCULATOR, Num ArrayList by suminmax METHOD */
   /**  CODE: ArrayList<Num> cart = new ArrayList<>(); cart.add(new Num("0.4")); cart.add(new Num("0.3")); cart.add(new Num("0.1")); cart.add(new Num("0.3")); Num.print(Num.suminmax(cart), "\r\n"); //[1.1, 0.275, 0.1, 0.4] */
   public static Num[] suminmax(ArrayList<Num> A) {
	   Num V[] = new Num[4];
	   V[0] = Num.sum(A);
	   V[1] = Num.mean(A);
	   V[2] = Num.min(A);
	   V[3] = Num.max(A);
	   return V;
   }    
   
    /** CALCULATOR, numeric String by suminmax METHOD */
   /**  CODE: String[] cart = { "0.1", "0.2", "-0.3", "-0.4" }; Num.print(Num.suminmax(cart), "\r\n"); //[-0.4, -0.1, -0.4, 0.2] */
   public static Num[] suminmax(String[] A) {
	   Num V[] = new Num[4];
	   V[0] = Num.sum(A);
	   V[1] = Num.mean(A);
	   V[2] = Num.min(A);
	   V[3] = Num.max(A);
	   return V;
   }    
   
    /** CALCULATOR, int by suminmax METHOD */
   /**  CODE: int[] cart = { 4, 2, 5, 1, 3 }; Num.print(Num.suminmax(cart), "\r\n"); //[15.0, 3.0, 1.0, 5.0] */
   public static Num[] suminmax(int[] A) {
	   Num V[] = new Num[4];
	   V[0] = Num.sum(A);
	   V[1] = Num.mean(A);
	   V[2] = Num.min(A);
	   V[3] = Num.max(A);
	   return V;
   }    
   
    /** CALCULATOR, long by suminmax METHOD */
   /**  CODE: long[] cart = { 4L, 2L, 5L, 1L, 3L }; Num.print(Num.suminmax(cart), "\r\n"); //[15.0, 3.0, 1.0, 5.0] */
   public static Num[] suminmax(long[] A) {
	   Num V[] = new Num[4];
	   V[0] = Num.sum(A);
	   V[1] = Num.mean(A);
	   V[2] = Num.min(A);
	   V[3] = Num.max(A);
	   return V;
   }    
   
    /** CALCULATOR, BigInteger by suminmax METHOD */
   /**  CODE: BigInteger[] cart = { new BigInteger("4"), new BigInteger("2"), new BigInteger("5"), new BigInteger("1"), new BigInteger("3"), }; Num.print(Num.suminmax(cart), "\r\n"); //[15.0, 3.0, 1.0, 5.0] */
   public static Num[] suminmax(BigInteger[] A) {
	   Num V[] = new Num[4];
	   V[0] = Num.sum(A);
	   V[1] = Num.mean(A);
	   V[2] = Num.min(A);
	   V[3] = Num.max(A);
	   return V;
   }    
   
     /** hypot, PYTHAGOREAN THEOREM -DEFAULT PRECISION TEN */
    /**  CODE: Num a = new Num(3); Num b = new Num(4); Num.print(Num.hypot(a, b), "\r\n"); //5.0 */
    public static Num hypot(Num a, Num b) { Num H = a.Mul(a).Add(b.Mul(b)); return H.Sqrt(); }
    
     /** hypot, PYTHAGOREAN THEOREM -DEFAULT PRECISION TEN */
    /**  CODE: Num.print(Num.hypot("3.0","4.0"), "\r\n"); //5.0 */
    public static Num hypot(String a, String b) { Num A = new Num(a); Num B = new Num(b); return A.Mul(A).Add(B.Mul(B)).Sqrt(); }
    
     /** hypot, PYTHAGOREAN THEOREM -DEFAULT PRECISION TEN */
    /**  CODE:  Num.print(Num.hypot(3, 4), "\r\n"); //5.0 */
    public static Num hypot(int a, int b) { Num A = new Num(a); Num B = new Num(b); return A.Mul(A).Add(B.Mul(B)).Sqrt(); }
    
     /** hypot, PYTHAGOREAN THEOREM -DEFAULT PRECISION TEN */
    /**  CODE:  Num.print(Num.hypot(3L, 4L), "\r\n"); //5.0 */
    public static Num hypot(long a, long b) { Num A = new Num(a); Num B = new Num(b); return A.Mul(A).Add(B.Mul(B)).Sqrt(); }
    
     /** hypot, PYTHAGOREAN THEOREM -DEFAULT PRECISION TEN */
    /**  CODE:  Num.print(Num.hypot(new BigInteger("3"), new BigInteger("4")), "\r\n"); //5.0 */
    public static Num hypot(BigInteger a, BigInteger b) { Num A = new Num(a); Num B = new Num(b); return A.Mul(A).Add(B.Mul(B)).Sqrt(); }
    
     /** hypot, PYTHAGOREAN THEOREM BY PRECISIION */
    /**  CODE: Num a = new Num(3); Num b = new Num(5); Num.print(Num.hypot(a, b, 40), "\r\n"); //5.8309518948453004708741528775455830765213 */
    public static Num hypot(Num a, Num b, int p) { Num H = a.Mul(a).Add(b.Mul(b)); return H.Sqrt(p); }
    
     /** hypot, PYTHAGOREAN THEOREM BY PRECISIION */
    /**  CODE: Num.print(Num.hypot("3.0","5.0", 40), "\r\n"); //5.8309518948453004708741528775455830765213 */
    public static Num hypot(String a, String b, int p) { Num A = new Num(a); Num B = new Num(b); return A.Mul(A).Add(B.Mul(B)).Sqrt(p); }
    
     /** hypot, PYTHAGOREAN THEOREM BY PRECISIION */
    /**  CODE: Num.print(Num.hypot(3, 5, 40), "\r\n"); //5.8309518948453004708741528775455830765213 */
    public static Num hypot(int a, int b, int p) { Num A = new Num(a); Num B = new Num(b); return A.Mul(A).Add(B.Mul(B)).Sqrt(p); }
    
     /** hypot, PYTHAGOREAN THEOREM BY PRECISIION */
    /**  CODE: Num.print(Num.hypot(3L, 5L, 40), "\r\n"); //5.8309518948453004708741528775455830765213 */
    public static Num hypot(long a, long b, int p) { Num A = new Num(a); Num B = new Num(b); return A.Mul(A).Add(B.Mul(B)).Sqrt(p); }
    
     /** hypot, PYTHAGOREAN THEOREM BY PRECISIION */
    /**  CODE: Num.print(Num.hypot(new BigInteger("3"), new BigInteger("5"), 40), "\r\n"); //5.8309518948453004708741528775455830765213 */
    public static Num hypot(BigInteger a, BigInteger b, int p) { Num A = new Num(a); Num B = new Num(b); return A.Mul(A).Add(B.Mul(B)).Sqrt(p); }
    
     /** (**) pow, POWER BINARY OPERATOR */
    /**  CODE: Num.print(Num.pow(new Num("3.14"), new Num("5.0")), "\r\n"); //305.2447761824 */
    public static Num pow(Num b, Num e) { return b.Pow(e); } 

     /** (**) pow, POWER BINARY OPERATOR */
    /**  CODE: Num.print(Num.pow(new Num("3.14"), 5), "\r\n"); //305.2447761824 */
    public static Num pow(Num b, int e) { return b.Pow(e); } 

     /** (**) pow, POWER BINARY OPERATOR */
    /**  CODE: Num.print(Num.pow("3.14", "5.0"), "\r\n"); //305.2447761824 */
    public static Num pow(String b, String e) { return new Num(b).Pow(e); } 
   
     /** (**) pow, POWER BINARY OPERATOR */
    /**  CODE: Num.print(Num.pow("3.14", 5), "\r\n"); //305.2447761824 */
    public static Num pow(String b, int e) { return new Num(b).Pow(e); } 
   
     /** (**) pow, POWER BINARY OPERATOR */
    /**  CODE: Num.print(Num.pow(2, 256), "\r\n"); //115792089237316195423570985008687907853269984665640564039457584007913129639936.0 */
    public static Num pow(int b, int e) { return new Num(b).Pow(e); } 
   
     /** (**) pow, POWER BINARY OPERATOR */
    /**  CODE: Num.print(Num.pow(2L, 256L), "\r\n"); //115792089237316195423570985008687907853269984665640564039457584007913129639936.0 */
    public static Num pow(long b, long e) { return new Num(b).Pow(e); } 
   
     /** (**) pow, POWER BINARY OPERATOR */
    /**  CODE: Num.print(Num.pow(new BigInteger("2"), new BigInteger("256")), "\r\n"); //115792089237316195423570985008687907853269984665640564039457584007913129639936.0 */
    public static Num pow(BigInteger b, BigInteger e) { return new Num(b).Pow(e); } 
   
     /** (<=) le, LESS OR EQUAL LOGIC BINARY OPERATOR */
    /**  CODE: Num.print(Num.le(new Num("32.0"), new Num("00032.0000")), "\r\n"); //true */
    public static boolean le(Num a, Num b) { return a.LE(b); }

     /** (<=) le, LESS OR EQUAL LOGIC BINARY OPERATOR */
    /**  CODE: Num.print(Num.le("32.0", "00032.0000"), "\r\n"); //true */
    public static boolean le(String a, String b) { return new Num(a).LE(new Num(b)); }
   
     /** (<=) le, LESS OR EQUAL LOGIC BINARY OPERATOR */
    /**  CODE: Num.print(Num.le(32, 32), "\r\n"); //true */
    public static boolean le(int a, int b) { return new Num(a).LE(new Num(b)); }
    
     /** (<=) le, LESS OR EQUAL LOGIC BINARY OPERATOR */
    /**  CODE: Num.print(Num.le(32L, 32L), "\r\n"); //true */
    public static boolean le(long a, long b) { return new Num(a).LE(new Num(b)); }
    
     /** (<=) le, LESS OR EQUAL LOGIC BINARY OPERATOR */
    /**  CODE: Num.print(Num.le(new BigInteger("32"), new BigInteger("32")), "\r\n"); //true */
    public static boolean le(BigInteger a, BigInteger b) { return new Num(a).LE(new Num(b)); }
    
     /** (<) lt, LESS LOGIC BINARY OPERATOR */
    /**  CODE: Num.print(Num.lt(new Num("32.0"), new Num("0032.0001")), "\r\n"); //true */
    public static boolean lt(Num a, Num b) { return a.LT(b); }

     /** (<) lt, LESS LOGIC BINARY OPERATOR */
    /**  CODE: Num.print(Num.lt("32.0", "0032.0001"), "\r\n"); //true */
    public static boolean lt(String a, String b) { return new Num(a).LT(new Num(b)); }

     /** (<) lt, LESS LOGIC BINARY OPERATOR */
    /**  CODE: Num.print(Num.lt(32, 33), "\r\n"); //true */
    public static boolean lt(int a, int b) { return new Num(a).LT(new Num(b)); }
    
     /** (<) lt, LESS LOGIC BINARY OPERATOR */
    /**  CODE: Num.print(Num.lt(32L, 33L), "\r\n"); //true */
    public static boolean lt(long a, long b) { return new Num(a).LT(new Num(b)); }
    
     /** (<) lt, LESS LOGIC BINARY OPERATOR */
    /**  CODE: Num.print(Num.lt(new BigInteger("32"), new BigInteger("33")), "\r\n"); //true */
    public static boolean lt(BigInteger a, BigInteger b) { return new Num(a).LT(new Num(b)); }
    
     /** (!=) ne, NOT EQUAL LOGIC BINARY OPERATOR */
    /**  CODE: Num a = new Num("32.0"); Num b = new Num("00032.000"); Num.print(Num.ne(a, b), "\r\n"); //false */
    public static boolean ne(Num a, Num b) { return a.NE(b); }  
      
     /** (!=) ne, NOT EQUAL LOGIC BINARY OPERATOR */
    /**  CODE: Num.print(Num.ne("32.0", "00032.000"), "\r\n"); //false */
    public static boolean ne(String a, String b) { return new Num(a).NE(new Num(b)); }  
      
     /** (!=) ne, NOT EQUAL LOGIC BINARY OPERATOR */
    /**  CODE: Num.print(Num.ne(32, 31+1), "\r\n"); //false */
    public static boolean ne(int a, int b) { return new Num(a).NE(new Num(b)); }  
    
     /** (!=) ne, NOT EQUAL LOGIC BINARY OPERATOR */
    /**  CODE: Num.print(Num.ne(32L, 31+1L), "\r\n"); //false */
    public static boolean ne(long a, long b) { return new Num(a).NE(new Num(b)); }  
    
     /** (!=) ne, NOT EQUAL LOGIC BINARY OPERATOR */
    /**  CODE: Num.print(Num.ne(new BigInteger("32"), new BigInteger("32")), "\r\n"); //false */
    public static boolean ne(BigInteger a, BigInteger b) { return new Num(a).NE(new Num(b)); }  
    
     /** (==) eq, EQUAL LOGIC BINARY OPERATOR */
    /**  CODE: Num.print(Num.eq(new Num("32.0"), new Num("00032.000")), "\r\n"); //true */
    public static boolean eq(Num a, Num b) { return a.EQ(b); }

     /** (==) eq, EQUAL LOGIC BINARY OPERATOR */
    /**  CODE: Num.print(Num.eq(new Num("32.0"), new Num("00032.000")), "\r\n"); //true */
    public static boolean eq(String a, String b) { return new Num(a).EQ(new Num(b)); }
 
     /** (==) eq, EQUAL LOGIC BINARY OPERATOR */
    /**  CODE: Num.print(Num.eq(32, 32), "\r\n"); //true */
    public static boolean eq(int a, int b) { return new Num(a).EQ(new Num(b)); }
    
     /** (==) eq, EQUAL LOGIC BINARY OPERATOR */
    /**  CODE: Num.print(Num.eq(32L, 32L), "\r\n"); //true */
    public static boolean eq(long a, long b) { return new Num(a).EQ(new Num(b)); }
    
     /** (==) eq, EQUAL LOGIC BINARY OPERATOR */
    /**  CODE: Num.print(Num.eq(new BigInteger("32"), new BigInteger("32")), "\r\n"); //true */
    public static boolean eq(BigInteger a, BigInteger b) { return new Num(a).EQ(new Num(b)); }
    
     /** (>) gt, GREATER LOGIC BINARY OPERATOR */
    /**  CODE: Num a = new Num("32.0"); Num b = new Num("31.009"); Num.print(Num.gt(a, b), "\r\n"); //true */
    public static boolean gt(Num a, Num b) { return a.GT(b); }

     /** (>) gt, GREATER LOGIC BINARY OPERATOR */
    /**  CODE: Num.print(Num.gt("32.0", "31.009"), "\r\n"); //true */
    public static boolean gt(String a, String b) { return new Num(a).GT(new Num(b)); }
 
     /** (>) gt, GREATER LOGIC BINARY OPERATOR */
    /**  CODE: Num.print(Num.gt(32, 31), "\r\n"); //true */
    public static boolean gt(int a, int b) { return new Num(a).GT(new Num(b)); }
    
     /** (>) gt, GREATER LOGIC BINARY OPERATOR */
    /**  CODE: Num.print(Num.gt(32L, 31L), "\r\n"); //true */
    public static boolean gt(long a, long b) { return new Num(a).GT(new Num(b)); }
    
     /** (>) gt, GREATER LOGIC BINARY OPERATOR */
    /**  CODE: Num.print(Num.gt(new BigInteger("32"), new BigInteger("31")), "\r\n"); //true */
    public static boolean gt(BigInteger a, BigInteger b) { return new Num(a).GT(new Num(b)); }
    
    /** (>=) ge, GREATER OR EQUAL LOGIC BINARY OPERATOR */
   /**  Num.print(Num.ge(new Num("32.0"), new Num("00032.000")), "\r\n"); //true */
   public static boolean ge(Num a, Num b) { return a.GE(b); }

    /** (>=) ge, GREATER OR EQUAL LOGIC BINARY OPERATOR */
   /**  Num.print(Num.ge("32.0", "00032.000"), "\r\n"); //true */
   public static boolean ge(String a, String b) { return new Num(a).GE(new Num(b)); }

    /** (>=) ge, GREATER OR EQUAL LOGIC BINARY OPERATOR */
   /**  Num.print(Num.ge(32, 32), "\r\n"); //true */
   public static boolean ge(int a, int b) { return new Num(a).GE(new Num(b)); }
   
    /** (>=) ge, GREATER OR EQUAL LOGIC BINARY OPERATOR */
   /**  Num.print(Num.ge(32L, 32L), "\r\n"); //true */
   public static boolean ge(long a, long b) { return new Num(a).GE(new Num(b)); }
   
    /** (>=) ge, GREATER OR EQUAL LOGIC BINARY OPERATOR */
   /**  CODE: Num.print(Num.ge(new BigInteger("32"), new BigInteger("33")), "\r\n"); //false */
   public static boolean ge(BigInteger a, BigInteger b) { return new Num(a).GE(new Num(b)); }

     /** (!) not, LOGIC UNARY OPERATOR */
    /** CODE: Num a = new Num("32.0"); Num.print(Num.not(a), "\r\n"); //false */
    public static boolean not(Num a) { return a.Not(); }

     /** (!) not, LOGIC UNARY OPERATOR */
    /**  CODE: Num.print(Num.not("32.0"), "\r\n"); //false */
    public static boolean not(String a) { return new Num(a).Not(); }

     /** (!) not, LOGIC UNARY OPERATOR */
    /**  CODE: Num.print(Num.not(32), "\r\n"); //false */
    public static boolean not(int a) { return new Num(a).Not(); }

     /** (!) not, LOGIC UNARY OPERATOR */
    /**  CODE: Num.print(Num.not(32L), "\r\n"); //false */
    public static boolean not(long a) { return new Num(a).Not(); }

     /** (!) not, LOGIC UNARY OPERATOR */
    /**  CODE: Num.print(Num.not(new BigInteger("32")), "\r\n"); //false */
    public static boolean not(BigInteger a) { return new Num(a).Not(); }

     /**  is_numeven, BOOLEAN CHECKS FOR Num EVEN (0 2 4 6 8) */
    /**   CODE: Num a = new Num("32.0"); Num.print(Num.is_numeven(a), "\r\n"); //true */
    public static boolean is_numeven(Num n) { return n.Is_numeven(); }
    
     /**  is_numeven, BOOLEAN CHECKS FOR String EVEN (0 2 4 6 8) */
    /**   CODE: Num.print(Num.is_numeven("32.0"), "\r\n"); //true */
    public static boolean is_numeven(String N) { Num n = new Num(N); return n.Is_numeven(); }
    
     /**  is_numeven, BOOLEAN CHECKS FOR int EVEN (0 2 4 6 8) */
    /**   CODE: Num.print(Num.is_numeven(32), "\r\n"); //true */
    public static boolean is_numeven(int N) { Num n = new Num(N); return n.Is_numeven(); }
    
     /**  is_numeven, BOOLEAN CHECKS FOR long EVEN (0 2 4 6 8) */
    /**   CODE: Num.print(Num.is_numeven(32L), "\r\n"); //true */
    public static boolean is_numeven(long N) { Num n = new Num(N); return n.Is_numeven(); }
    
     /**  is_numeven, BOOLEAN CHECKS FOR BigInteger EVEN (0 2 4 6 8) */
    /**   CODE: Num.print(Num.is_numeven(new BigInteger("32")), "\r\n"); //true */
    public static boolean is_numeven(BigInteger N) { Num n = new Num(N); return n.Is_numeven(); }
    
     /** is_numodd, BOOLEAN CHECKS FOR Num ODD (1 3 5 7 9) */ 
    /**  CODE: Num a = new Num("3.0"); Num.print(Num.is_numodd(a), "\r\n"); //true */
    public static boolean  is_numodd(Num n) { return n.Is_numodd(); }

     /** is_numodd, BOOLEAN CHECKS FOR String ODD (1 3 5 7 9) */ 
    /**  CODE: Num.print(Num.is_numodd("3.0"), "\r\n"); //true */
    public static boolean  is_numodd(String N) { Num n = new Num(N); return n.Is_numodd(); }
    
     /** is_numodd, BOOLEAN CHECKS FOR int ODD (1 3 5 7 9) */ 
    /**  CODE: Num.print(Num.is_numodd(3), "\r\n"); //true */
    public static boolean  is_numodd(int N) { Num n = new Num(N); return n.Is_numodd(); }
    
     /** is_numodd, BOOLEAN CHECKS FOR long ODD (1 3 5 7 9) */ 
    /**  CODE: Num.print(Num.is_numodd(3L), "\r\n"); //true */
    public static boolean  is_numodd(long N) { Num n = new Num(N); return n.Is_numodd(); }
    
     /** is_numodd, BOOLEAN CHECKS FOR BigInteger ODD (1 3 5 7 9) */ 
    /**  CODE: Num.print(Num.is_numodd(new BigInteger("33")), "\r\n"); //true */
    public static boolean  is_numodd(BigInteger N) { Num n = new Num(N); return n.Is_numodd(); }
    
     /** is_positive, BOOLEAN CHECKS FOR Num POSITIVE */ 
    /**  CODE: Num a = new Num("60.0"); Num.print(Num.is_positive(a), "\r\n"); //true */
    public static boolean is_positive(Num n) { return !n.Is_negative(); }

     /** is_positive, BOOLEAN CHECKS FOR String POSITIVE */ 
    /**  CODE: Num.print(Num.is_positive("60.0"), "\r\n"); //true */
    public static boolean is_positive(String N) { Num n = new Num(N); return !n.Is_negative(); }
    
     /** is_positive, BOOLEAN CHECKS FOR int POSITIVE */ 
    /**  CODE: Num.print(Num.is_positive(60), "\r\n"); //true */
    public static boolean is_positive(int N) { Num n = new Num(N); return !n.Is_negative(); }
    
     /** is_positive, BOOLEAN CHECKS FOR long POSITIVE */ 
    /**  CODE: Num.print(Num.is_positive(60L), "\r\n"); //true */
    public static boolean is_positive(long N) { Num n = new Num(N); return !n.Is_negative(); }
    
     /** is_positive, BOOLEAN CHECKS FOR BigInteger POSITIVE */ 
    /**  CODE: Num.print(Num.is_positive(new BigInteger("60")), "\r\n"); //true */
    public static boolean is_positive(BigInteger N) { Num n = new Num(N); return !n.Is_negative(); }
    
     /** is_negative, BOOLEAN CHECKS BY Num NEGATIVE */
    /**  CODE: Num a = new Num("-60.0"); Num.print(Num.is_negative(a), "\r\n"); //true */
    public static boolean is_negative(Num n) { return n.Is_negative(); }

     /** is_negative, BOOLEAN CHECKS BY String NEGATIVE */
    /**  CODE: Num.print(Num.is_negative("-60.0"), "\r\n"); //true */
    public static boolean is_negative(String N) { Num n = new Num(N); return n.Is_negative(); }
    
     /** is_negative, BOOLEAN CHECKS BY int NEGATIVE */
    /**  CODE: Num.print(Num.is_negative(-60), "\r\n"); //true */
    public static boolean is_negative(int N) { Num n = new Num(N); return n.Is_negative(); }
    
    /** is_negative, BOOLEAN CHECKS BY long NEGATIVE */
    /**  CODE: Num.print(Num.is_negative(-60L), "\r\n"); //true */
    public static boolean is_negative(long N) { Num n = new Num(N); return n.Is_negative(); }
    
     /** is_negative, BOOLEAN CHECKS BY BigInteger NEGATIVE */
    /**  CODE: Num.print(Num.is_negative(new BigInteger("-60")), "\r\n"); //true */
    public static boolean is_negative(BigInteger N) { Num n = new Num(N); return n.Is_negative(); }
    
     /** is_zero, BOOLEAN CHECKS BY ZERO Num VALUE */
    /**  CODE: Num a = new Num("0.0"); Num.print(Num.is_zero(a), "\r\n"); //true */
    public static boolean is_zero(Num n) { return n.Is_zero(); }
 
     /** is_zero, BOOLEAN CHECKS BY ZERO String VALUE */
    /**  CODE: Num.print(Num.is_zero("0.0"), "\r\n"); //true */
    public static boolean is_zero(String N) { Num n = new Num(N); return n.Is_zero(); }
    
     /** is_zero, BOOLEAN CHECKS BY ZERO int VALUE */
    /**  CODE: Num.print(Num.is_zero(0), "\r\n"); //true */
    public static boolean is_zero(int N) { Num n = new Num(N); return n.Is_zero(); }
    
     /** is_zero, BOOLEAN CHECKS BY ZERO long VALUE */
    /**  CODE: Num.print(Num.is_zero(0L), "\r\n"); //true */
    public static boolean is_zero(long N) { Num n = new Num(N); return n.Is_zero(); }
    
     /** is_zero, BOOLEAN CHECKS BY ZERO BigInteger VALUE */
    /**  CODE: Num.print(Num.is_zero(new BigInteger("0")), "\r\n"); //true */
    public static boolean is_zero(BigInteger N) { Num n = new Num(N); return n.Is_zero(); }
    
     /** is_numfloat, BOOLEAN CHECKS BY FLOATING POINT Num */
    /**  CODE: Num a = new Num("7.14"); Num.print(Num.is_numfloat(a), "\r\n"); //true */
    public static boolean is_numfloat(Num n) { return n.Is_numfloat(); }
    
     /** is_numfloat, BOOLEAN CHECKS BY FLOATING POINT String */
    /**  CODE: Num.print(Num.is_numfloat("7.14"), "\r\n"); //true */
    public static boolean is_numfloat(String n) { return new Num(n).Is_numfloat(); }
    
     /** is_numfloat, BOOLEAN CHECKS BY FLOATING POINT int */
    /**  CODE: Num.print(Num.is_numfloat(-7), "\r\n"); //false */
    public static boolean is_numfloat(int n) { return false; }
    
     /** is_numfloat, BOOLEAN CHECKS BY FLOATING POINT long */
    /**  CODE: Num.print(Num.is_numfloat(-7L), "\r\n"); //false */
    public static boolean is_numfloat(long n) { return false; }
    
     /** is_numfloat, BOOLEAN CHECKS BY FLOATING POINT BigInteger */
    /**  CODE: Num.print(Num.is_numfloat(new BigInteger("-7") ), "\r\n"); //false */
    public static boolean is_numfloat(BigInteger n) { return false; }
    
   /**  is_numint, BOOLEAN CHECKS IF INTEGER Num */
  /**   CODE: Num a = new Num("7.14"); Num.print(Num.is_numint(a), "\r\n"); //false */
  public static boolean is_numint(Num n) { return n.Is_numint(); }

   /**  is_numint, BOOLEAN CHECKS IF INTEGER String */
  /**  CODE: Num.print(Num.is_numint("7.14"), "\r\n"); //false */
  public static boolean is_numint(String n) {return new Num(n).Is_numint(); }

   /**  is_numint, BOOLEAN CHECKS IF INTEGER int */
  /**  CODE: Num.print(Num.is_numint(7), "\r\n"); //true */
  public static boolean is_numint(int n) {return true; }
  
   /**  is_numint, BOOLEAN CHECKS IF INTEGER long */
  /**  CODE: Num.print(Num.is_numint(7L), "\r\n"); //true */
  public static boolean is_numint(long n) {return true; }
  
   /**  is_numint, BOOLEAN CHECKS IF INTEGER long */
  /**  CODE: Num.print(Num.is_numint(new BigInteger("7")), "\r\n"); //true */
  public static boolean is_numint(BigInteger n) {return true; }
  
   /**  abs, RETURN THE ABSOLUTE VALUE OF Num */
  /**   CODE: Num a = new Num(-4); Num b = Num.abs(a); b.Print("\r\n"); //4.0 */
  public static Num abs(Num n) { return new Num(n).Abs(); }

   /**  abs, RETURN THE ABSOLUTE VALUE OF Num STRING */
  /**   CODE: Num.abs("-36.2").Print("\r\n"); //36.2 */
  public static Num abs(String n) { return new Num(n).Abs(); }

   /**  abs, RETURN THE ABSOLUTE VALUE OF int */
  /**   CODE: Num.abs(-36).Print("\r\n"); //36.0 */
  public static Num abs(int n) { return new Num(n).Abs(); }

   /**  abs, RETURN THE ABSOLUTE VALUE OF long */
  /**   CODE: Num.abs(-36L).Print("\r\n"); //36.0 */
  public static Num abs(long n) { return new Num(n).Abs(); }

   /**  abs, RETURN THE ABSOLUTE VALUE OF BigInteger */
  /**   CODE: Num.abs(new BigInteger("-36")).Print("\r\n"); //36.0 */
  public static Num abs(BigInteger n) { return new Num(n).Abs(); }

   /**  clear, CLEAR VARIABLE SETTING TO ZERO */
  /**   CODE: Num a = new Num(4); Num.clear(a); a.Print("\r\n"); //0.0 */
  public static void clear(Num v) { v.Clear(); }

    /** isDigit, CHECK FOR ONLY DIGITS IN A STRING */
   /**  HOW: true = "123" false = "123.0" "123a" */
  /**   CODE: String s = "123"; Num.print(Num.isDigit(s)); //true */
  public static boolean isDigit(String str) { return str.matches("^\\d+$"); }
  
   /** IN, BOOLEAN IN CHECKS CHARACTER IN A STRING */
  /**  CODE:  Num.print(Num.IN("123#4", "."), "\r\n"); //false */
  public static boolean IN(String s, String ch) { return Pattern.compile(Pattern.quote(ch)).matcher(s).find(); }

   /** shift, SHIFT Num LEFT AND RIGHT BY int TIMES (MULTIPLY AND DIVIDE BY TEN) */
  /**  CODE: Num a = new Num("123.456789"); Num.print(Num.shift(a, 3), "\r\n"); //123456.789 */
  public static Num shift(Num N, int zeros) {
      if      (zeros > 0) { return new Num(N.Mul(new Num(new BigInteger("10").pow(zeros)))); } 
      else if (zeros < 0) { return new Num(N.Div(new Num(new BigInteger("10").pow(-zeros), -zeros + N.L_n1))); }
      return N;
  }

   /** shift, SHIFT NUMERIC String LEFT AND RIGHT BY int TIMES (MULTIPLY AND DIVIDE BY TEN) */
  /**  CODE: Num.print(Num.shift("123.456789", 3), "\r\n"); //123456.789 */
  public static Num shift(String N, int zeros) { return Num.shift(new Num(N), zeros); }

   /** shift, SHIFT int LEFT AND RIGHT BY int TIMES (MULTIPLY AND DIVIDE BY TEN) */
  /**  CODE: Num.print(Num.shift(5, -3), "\r\n"); //0.005 */
  public static Num shift(int N, int zeros) { return Num.shift(new Num(N), zeros); }

   /** shift, SHIFT long LEFT AND RIGHT BY int TIMES (MULTIPLY AND DIVIDE BY TEN) */
  /**  CODE: Num.print(Num.shift(-5L, -3), "\r\n"); //-0.005 */
  public static Num shift(long N, int zeros) { return Num.shift(new Num(N), zeros); }

   /** shift, SHIFT BigInteger LEFT AND RIGHT BY int TIMES (MULTIPLY AND DIVIDE BY TEN) */
  /**  CODE: Num.print(Num.shift(new BigInteger("-5"), -3), "\r\n"); //-0.005 */
  public static Num shift(BigInteger N, int zeros) { return Num.shift(new Num(N), zeros); }

   /** in, BOOLEAN MATCH OPERATOR BY ARRAY */
  /**  CODE:  Num A[] = { new Num(3), new Num(-6), new Num(0), new Num("9.7"), new Num("6.1") }; Num.print(Num.in(A, new Num("9.7")), "\r\n"); //true */
  public static boolean in(Num[] L, Num v) { for(Num e : L) if (e.EQ(v)) return true; return false; }

   /** not_in, BOOLEAN NOT MATCH OPERATOR BY ARRAY */ 
  /**  CODE:  Num A[] = { new Num(3), new Num(-6), new Num(0), new Num("9.7"), new Num("6.1") }; Num.print(Num.not_in(A, new Num("9.7")), "\r\n"); //false */
  public static boolean not_in(Num[] L, Num v) { for(Num e : L) if (e.EQ(v)) return false; return true; }

   /** CALCULATOR MODE: _10y, TEN POWER BY int */
  /**  CODE: int a = 9; Num._10y(a).Print("\r\n"); //1000000000.0 */
  public static Num _10y(int E) { return new Num(10).Shift(E - 1); }

   /** CALCULATOR MODE: _10y, TEN POWER BY long */
  /**  CODE: long a = 9; Num._10y(a).Print("\r\n"); //1000000000.0 */
  public static Num _10y(long E) { return new Num(10).Shift((int) E - 1); }

   /** CALCULATOR MODE: _10y, TEN POWER BY BigInteger */
  /**  CODE: BigInteger a = new BigInteger("9"); Num._10y(a).Print("\r\n"); //1000000000.0 */
  public static Num _10y(BigInteger E) { return new Num(10).Shift(E.intValue() - 1); }

   /** CALCULATOR MODE: _10y, TEN POWER BY NUMERIC String */
  /**  CODE: String a = "9.0"; Num._10y(a).Print("\r\n"); //1000000000.0 */
  public static Num _10y(String E) { return new Num(10).Shift(new Num(E).toInt() - 1); }

   /** CALCULATOR MODE: _10y, TEN POWER BY Num */
  /** CODE: Num a = new Num("9.0"); Num._10y(a).Print("\r\n"); //1000000000.0 */
  public static Num _10y(Num E) { return new Num(10).Shift(E.toInt() - 1); }

   /** CALCULATOR MODE: _ey, e POWER BY int */
  /**  CODE: int a = 5; Num._ey(a).Round(30).Print("\r\n"); //148.413159102576603421115580040563 */
  public static Num _ey(int ex) { return Num.pow(Num.e(), ex); }

   /** CALCULATOR MODE: _ey, e POWER BY long */
  /**  CODE: long a = 5; Num._ey(a).Round(30).Print("\r\n"); //148.413159102576603421115580040563 */
  public static Num _ey(long  ex) { return Num.pow(Num.e(), (int) ex); }

   /** CALCULATOR MODE: _ey, e POWER BY BigInteger */
  /**  CODE: BigInteger a = new BigInteger("5"); Num._ey(a).Round(30).Print("\r\n"); //148.413159102576603421115580040563 */
  public static Num _ey(BigInteger ex) { return Num.pow(Num.e(), ex.intValue()); }

   /** CALCULATOR MODE: _ey, e POWER BY NUMERIC String */
  /**  CODE: String a = "5.0"; Num._ey(a).Round(30).Print("\r\n"); //148.413159102576603421115580040563 */
  public static Num _ey(String ex) { return Num.pow(Num.e(), new Num(ex).toInt()); }

   /** CALCULATOR MODE: _ey, e POWER BY Num */
  /**  CODE: Num a = new Num("5.0"); Num._ey(a).Round(30).Print("\r\n"); //148.413159102576603421115580040563 */
  public static Num _ey(Num ex) { return Num.pow(Num.e(), ex.toInt()); }

   /** CALCULATOR MODE: _2y, TWO POWER BY int */
  /**  CODE: Num._2y(5).Print("\r\n"); //32.0 */
  public static Num _2y(int E) { return Num.pow(2, E); }

   /** CALCULATOR MODE: _2y, TWO POWER BY long */
  /**  CODE: Num._2y(5L).Print("\r\n"); //32.0 */
  public static Num _2y(long E) { return Num.pow(2, (int) E); }

   /** CALCULATOR MODE: _2y, TWO POWER BY BigInteger */
  /**  CODE: Num._2y(new BigInteger("5")).Print("\r\n"); //32.0 */
  public static Num _2y(BigInteger E) { return Num.pow(2, E.intValue()); }

   /** CALCULATOR MODE: _2y, TWO POWER BY NUMERIC String */
  /**  CODE: Num._2y("5.0").Print("\r\n"); //32.0 */
  public static Num _2y(String E) { return Num.pow(2, new Num(E).toInt()); }

   /** CALCULATOR MODE: _2y, TWO POWER BY Num */
  /**  CODE: Num._2y(new Num("5.0")).Print("\r\n"); //32.0 */
  public static Num _2y(Num E) { return Num.pow(2, E.toInt()); }

   /** CALCULATOR MODE: fact, FACTORIAL COMPUTATION BY Num */
  /**  CODE: Num.print(Num.fact(new Num("5.0"))); //120 */
  public static Num fact(Num n) { return Num.fact(n.toInt()); }
  
   /** CALCULATOR MODE: fact, FACTORIAL COMPUTATION BY NUMERIC String */
  /**  CODE: Num.print(Num.fact("5.0")); //120 */
  public static Num fact(String n) { return Num.fact(new Num(n).toInt()); }
  
   /** CALCULATOR MODE: fact, FACTORIAL COMPUTATION BY int */
  /**  CODE: Num a = new Num(5); Num.print(Num.fact(a.toInt()).toString()); //120 */
  public static Num fact(int n) {
    if(n < 0) throw new ArithmeticException("Num.fact => FACTORIAL UNDEFINED: " + n);
    Num F = new Num(1);
    for (int i = 1; i <= n; i++) F = F.Mul(i); 
    return F;
  }
  
   /** CALCULATOR MODE: fact, FACTORIAL COMPUTATION BY long*/
  /**  CODE: Num.print(Num.fact(5L)); //120 */
  public static Num fact(long n) { return Num.fact(new Num(n).toInt()); }

   /** CALCULATOR MODE: fact, FACTORIAL COMPUTATION BY BigInteger */
  /** CODE: Num.print(Num.fact(new BigInteger("5"))); //120 */
  public static Num fact(BigInteger n) { return Num.fact(new Num(n).toInt()); }

   /** CALCULATOR MODE: x2, SQUARE POWER BY Num */
  /**  CODE: Num a = new Num("3.1415"); Num.x2(a).Print("\r\n"); //9.86902225 */
  public static Num x2(Num n) { return Num.mul(n, n); }

   /** CALCULATOR MODE: x2, SQUARE POWER BY String */
  /**  CODE: Num.x2("3.1415").Print("\r\n"); //9.86902225 */
  public static Num x2(String n) { return Num.mul(n, n); }

   /** CALCULATOR MODE: x2, SQUARE POWER BY int */
  /**  CODE: Num.x2(3).Print("\r\n"); //9.0 */
  public static Num x2(int n) { return Num.mul(n, n); }
  
   /** CALCULATOR MODE: x2, SQUARE POWER BY long */
  /**  CODE: Num.x2(3L).Print("\r\n"); //9.0 */
  public static Num x2(long n) { return Num.mul(n, n); }
  
   /** CALCULATOR MODE: x2, SQUARE POWER BY BigInteger */
  /**  CODE: Num.x2(new BigInteger("3")).Print("\r\n"); //9.0 */
  public static Num x2(BigInteger n) { return Num.mul(n, n); }
  
   /** CALCULATOR MODE: x3, CUBE POWER BY Num */
  /**  CODE: Num a = new Num("123.456"); Num.x3(a).Print("\r\n"); //1881640.295202816 */
  public static Num x3(Num n) { return Num.mul(n, n).Mul(n); }

   /** CALCULATOR MODE: x3, CUBE POWER by String */
  /**  CODE: Num.x3("123.456").Print("\r\n"); //1881640.295202816 */
  public static Num x3(String n) { return Num.mul(n, n).Mul(n); }

   /** CALCULATOR MODE: x3, CUBE POWER by int */
  /**  CODE: Num.x3(123).Print("\r\n"); //1860867.0 */
  public static Num x3(int n) { return Num.mul(n, n).Mul(n); }
  
   /** CALCULATOR MODE: x3, CUBE POWER by long */
  /**  CODE: Num.x3(123L).Print("\r\n"); //1860867.0 */
  public static Num x3(long n) { return Num.mul(n, n).Mul(n); }
  
   /** CALCULATOR MODE: x3, CUBE POWER by BigInteger */
  /**  CODE: Num.x3(new BigInteger("123")).Print("\r\n"); //1860867.0 */
  public static Num x3(BigInteger n) { return Num.mul(n, n).Mul(n); }
  
   /** CALCULATOR MODE: xe10 (shift), RETURN OBJECT MULTIPLIED OR DIVIDED FOR 10 POWER BY Num */
  /**  CODE: Num a = new Num("0.001"); Num.xe10(a, 6).Print("\r\n"); //1000.0 */
  public static Num xe10(Num a, int x) { return Num.shift(a, x); }

   /** CALCULATOR MODE: xe10 (shift), RETURN OBJECT MULTIPLIED OR DIVIDED FOR 10 POWER BY String */
  /**  CODE: Num.xe10("0.001", 6).Print("\r\n"); //1000.0 */
  public static Num xe10(String a, int x) { return Num.shift(a, x); }
  
   /** CALCULATOR MODE: xe10 (shift), RETURN OBJECT MULTIPLIED OR DIVIDED FOR 10 POWER BY int */
  /**  CODE: Num.xe10(1, 6).Print("\r\n"); //1000000.0 */
  public static Num xe10(int a, int x) { return Num.shift(a, x); }
  
   /** CALCULATOR MODE: xe10 (shift), RETURN OBJECT MULTIPLIED OR DIVIDED FOR 10 POWER BY long */
  /**  CODE: Num.xe10(1L, 6).Print("\r\n"); //1000000.0 */
  public static Num xe10(long a, int x) { return Num.shift(a, x); }
  
   /** CALCULATOR MODE: xe10 (shift), RETURN OBJECT MULTIPLIED OR DIVIDED FOR 10 POWER BY BigInteger */
  /**  CODE: Num.xe10(new BigInteger("1"), 6).Print("\r\n"); //1000000.0 */
  public static Num xe10(BigInteger a, int x) { return Num.shift(a, x); }
  
   /** CALCULATOR MODE: xy, POWER BY Num, Num */ 
  /**  CODE: Num.xy(new Num(4), new Num(30)).Print("\r\n"); //1152921504606846976.0 */
  public static Num xy(Num x, Num y) { return Num.pow(x.n, y.toInt()); }
  
   /** CALCULATOR MODE: xy, POWER BY Num, int */ 
  /**  CODE: Num.xy(new Num(4), 30).Print("\r\n"); //1152921504606846976.0 */
  public static Num xy(Num x, int y) { return Num.pow(x.n, y); }

   /** CALCULATOR MODE: xy, POWER BY String, String */ 
  /**  CODE: Num.xy("3.0", "4.0").Print("\r\n"); //81.0 */
  public static Num xy(String x, String y) { return Num.pow(x, y); }

   /** CALCULATOR MODE: xy, POWER String, int */ 
  /**  CODE: Num.xy("-3.14", 7).Print("\r\n"); //-3009.59139524799104 */
  public static Num xy(String x, int y) { return Num.pow(x, y); }

   /** CALCULATOR MODE: xy, POWER BY int, int */ 
  /**  CODE: Num.xy(-2, -4).Print("\r\n"); //0.0625 */
  public static Num xy(int x, int y) { return Num.pow(x, y); }

   /** CALCULATOR MODE: xy, POWER BY long, long */ 
  /**  CODE: Num.xy(-2L, -4L).Print("\r\n"); //0.0625 */
  public static Num xy(long x, long y) { return Num.pow((int) x, (int) y); }

   /** CALCULATOR MODE: xy, POWER BY BigInteger, BigInteger */ 
  /**  CODE: Num.xy(new BigInteger("-2"), new BigInteger("-4")).Print("\r\n"); //0.0625 */
  public static Num xy(BigInteger x, BigInteger y) { return Num.pow(new Num(x).toInt(), new Num(y).toInt()); }

   /** address, RETURN Num ADDRESS */
  /** CODE: Num.print(Num.address(new Num("3.0")), "\r\n"); //... (-523236767) */
  public static int address(Num n) { return n.hashCode(); } 

   /** CALCULATOR MODE: _2x, DOUBLED VALUE BY int */
  /** CODE: Num._2x(-2147483648).Print("\r\n"); //-4294967296.0 */
  public static Num _2x(int n) { return new Num(n).Add(n); }
  
   /** CALCULATOR MODE: _2x, DOUBLED VALUE BY long */
  /**  CODE: Num._2x(-2147483648L).Print("\r\n"); //-4294967296.0 */
  public static Num _2x(long n) { return new Num(n).Add(n); }
  
   /** CALCULATOR MODE: _2x, DOUBLED VALUE BY BigInteger */
  /**  CODE: Num._2x(new BigInteger("-2147483648")).Print("\r\n"); //-4294967296.0 */
  public static Num _2x(BigInteger n) { return new Num(n).Add(n); }
  
   /** CALCULATOR MODE: _2x, DOUBLED VALUE BY String */
  /**  CODE: Num._2x("123.0").Print("\r\n"); //246.0 */
  public static Num _2x(String n) { return new Num(n).Add(n); }
  
   /** CALCULATOR MODE: _2x, DOUBLED VALUE BY Num */
  /**  CODE: Num a = new Num ("123.0"); Num._2x(a).Print("\r\n"); //246.0 */
  public static Num _2x(Num n) { return n.Add(n); }

   /** CALCULATOR MODE: _3x, TRIPLED VALUE BY int */
  /** CODE: Num._3x(123).Print("\r\n"); //369.0 */
  public static Num _3x(int n) { return new Num(n).Add(n).Add(n); }

   /** CALCULATOR MODE: _3x, TRIPLED VALUE BY long */
  /**  CODE: Num._3x(123L).Print("\r\n"); //369.0 */
  public static Num _3x(long n) { return new Num(n).Add(n).Add(n); }

   /** CALCULATOR MODE: _3x, TRIPLED VALUE BY BigInteger */
  /**  CODE: Num._3x(new BigInteger("123")).Print("\r\n"); //369.0 */
  public static Num _3x(BigInteger n) { return new Num(n).Add(n).Add(n); }

  /** CALCULATOR MODE: _3x, TRIPLED VALUE BY String  */
  /** CODE: Num._3x("123.0").Print("\r\n"); //369.0 */
  public static Num _3x(String n) { return new Num(n).Add(n).Add(n); }

   /** CALCULATOR MODE: _3x, TRIPLED VALUE BY Num */
  /**  CODE: Num a = new Num ("123.0"); Num._3x(a).Print("\r\n"); //369.0 */
  public static Num _3x(Num n) { return n.Add(n).Add(n); }

   /** CALCULATOR MODE: _10x, MULTIPLY FOR TEN BY int */
  /**  CODE: Num._10x(3).Print("\r\n"); //30.0 */
  public static Num _10x(int n) { return new Num(n).Shift(1); }

   /** CALCULATOR MODE: _10x, MULTIPLY FOR TEN BY long */
  /** CODE: Num._10x(3L).Print("\r\n"); //30.0 */
  public static Num _10x(long n) { return new Num(n).Shift(1); }

   /** CALCULATOR MODE: _10x, MULTIPLY FOR TEN BY BigInteger */
  /**  CODE: Num._10x(new BigInteger("3")).Print("\r\n"); //30.0 */
  public static Num _10x(BigInteger n) { return new Num(n).Shift(1); }

   /** CALCULATOR MODE: _10x, MULTIPLY FOR TEN BY String */
  /**  CODE: Num._10x("3.2").Print("\r\n"); //32.0 */
  public static Num _10x(String n) { return new Num(n).Shift(1); }

   /** CALCULATOR MODE: _10x, MULTIPLY FOR TEN BY Num */
  /**  CODE: Num._10x(new Num("3.2")).Print("\r\n"); //32.0 */
  public static Num _10x(Num n) { return n.Shift(1); }

   /** CALCULATOR MODE: _100x, MULTIPLY FOR HUNDRED BY int */
  /**  CODE: Num._100x(3).Print("\r\n"); //300.0 */
  public static Num _100x(int n) { return new Num(n).Shift(2); }
  
   /** CALCULATOR MODE: _100x, MULTIPLY FOR HUNDRED BY long */
  /**  CODE: Num._100x(3L).Print("\r\n"); //300.0 */
  public static Num _100x(long n) { return new Num(n).Shift(2); }
  
   /** CALCULATOR MODE: _100x, MULTIPLY FOR HUNDRED BY BigInteger */
  /**  CODE: Num._100x(new BigInteger("3")).Print("\r\n"); //300.0 */
  public static Num _100x(BigInteger n) { return new Num(n).Shift(2); }

   /** CALCULATOR MODE: _100x, MULTIPLY FOR HUNDRED BY String */
  /**  CODE: Num._100x("3.2").Print("\r\n"); //320.0 */
  public static Num _100x(String n) { return new Num(n).Shift(2); }

   /** CALCULATOR MODE: _100x, MULTIPLY FOR HUNDRED BY Num  */
  /**  CODE: Num._100x(new Num("3.2")).Print("\r\n"); //320.0 */
  public static Num _100x(Num n) { return n.Shift(2); }

   /** CALCULATOR MODE: _1000x, MULTIPLY FOR THOUSAND BY int */
  /** CODE: Num._1000x(3).Print("\r\n"); //3000.0 */
  public static Num _1000x(int n) { return new Num(n).Shift(3); }
  
  /** CALCULATOR MODE: _1000x, MULTIPLY FOR THOUSAND BY long */
  /** CODE: Num._1000x(3L).Print("\r\n"); //3000.0 */
  public static Num _1000x(long n) { return new Num(n).Shift(3); }
  
   /** CALCULATOR MODE: _1000, MULTIPLY FOR THOUSAND BY BigInteger */
  /**  CODE: Num._1000x(new BigInteger("3")).Print("\r\n"); //3000.0 */
  public static Num _1000x(BigInteger n) { return new Num(n).Shift(3); }

   /** CALCULATOR MODE: _1000x, MULTIPLY FOR THOUSAND BY String */
  /**  CODE: Num._1000x("3.2").Print("\r\n"); //3200.0 */
  public static Num _1000x(String n) { return new Num(n).Shift(3); }

   /** CALCULATOR MODE: _1000x, MULTIPLY FOR THOUSAND BY Num */
  /**  CODE: Num._1000x(new Num("3.2")).Print("\r\n"); //3200.0 */
  public static Num _1000x(Num n) { return n.Shift(3); }

   /** CALCULATOR MODE: _10div, DIVIDE FOR TEN BY int */
  /**  CODE: Num._10div(3).Print("\r\n"); //0.3 */
  public static Num _10div(int n) { return new Num(n).Shift(-1); }

   /** CALCULATOR MODE: _10div, DIVIDE FOR TEN BY long */
  /**  CODE: Num._10div(3L).Print("\r\n"); //0.3 */
  public static Num _10div(long n) { return new Num(n).Shift(-1); }

   /** CALCULATOR MODE: _10div, DIVIDE FOR TEN BY BigInteger */
  /** CODE: Num._10div(new BigInteger("3")).Print("\r\n"); //0.3 */
  public static Num _10div(BigInteger n) { return new Num(n).Shift(-1); }

   /** CALCULATOR MODE: _10div, DIVIDE FOR TEN BY String */
  /** CODE: Num._10div("3.2").Print("\r\n"); //0.32 */
  public static Num _10div(String n) { return new Num(n).Shift(-1); }

   /** CALCULATOR MODE: _10div, DIVIDE FOR TEN BY Num */
  /**  CODE: Num._10div(new Num("3.2")).Print("\r\n"); //0.32 */
  public static Num _10div(Num n) { return n.Shift(-1); }

   /** CALCULATOR MODE: _100div, DIVIDE FOR HUNDRED BY int */
  /**  CODE: Num._100div(3).Print("\r\n"); //0.03 */
  public static Num _100div(int n) { return new Num(n).Shift(-2); }

   /** CALCULATOR MODE: _100div, DIVIDE FOR HUNDRED BY long */
  /**  CODE: Num._100div(3L).Print("\r\n"); //0.03 */
  public static Num _100div(long n) { return new Num(n).Shift(-2); }

   /** CALCULATOR MODE: _100div, DIVIDE FOR HUNDRED BY BigInteger */
  /**  CODE: Num._100div(new BigInteger("3")).Print("\r\n"); //0.03 */
  public static Num _100div(BigInteger n) { return new Num(n).Shift(-2); }

   /** CALCULATOR MODE: _100div, DIVIDE FOR HUNDRED BY String */
  /**  CODE: Num._100div("3.2").Print("\r\n"); //0.032 */
  public static Num _100div(String n) { return new Num(n).Shift(-2); }

   /** CALCULATOR MODE: _100div, DIVIDE FOR HUNDRED BY Num */
  /**  CODE: Num._100div(new Num("3.2")).Print("\r\n"); //0.032 */
  public static Num _100div(Num n) { return new Num(n).Shift(-2); }

   /** CALCULATOR MODE: _1000div, DIVIDE FOR THOUSAND BY int */
  /**  CODE: Num._1000div(3).Print("\r\n"); //0.003 */
  public static Num _1000div(int n) { return new Num(n).Shift(-3); }

   /** CALCULATOR MODE: _1000div, DIVIDE FOR THOUSAND BY long */
  /**  CODE: Num._1000div(3L).Print("\r\n"); //0.003 */
  public static Num _1000div(long n) { return new Num(n).Shift(-3); }

   /** CALCULATOR MODE: _1000div, DIVIDE FOR THOUSAND BY BigInteger */
  /**  CODE: Num._1000div(new BigInteger("3")).Print("\r\n"); //0.003 */
  public static Num _1000div(BigInteger n) { return new Num(n).Shift(-3); }

   /** CALCULATOR MODE: _1000div, DIVIDE FOR THOUSAND BY String */
  /**  CODE: Num._1000div("32.0").Print("\r\n"); //0.032 */
  public static Num _1000div(String n) { return new Num(n).Shift(-3); }

   /** CALCULATOR MODE: _1000div, DIVIDE FOR THOUSAND BY Num */
  /**  CODE: Num._1000div(new Num("32.0")).Print("\r\n"); //0.032 */
  public static Num _1000div(Num n) { return new Num(n).Shift(-3); }

   /** CALCULATOR MODE: pct, PERCENTAGE VALUE BY Num, Num */
  /**  CODE: Num.pct(new Num("10.00"), new Num("1_648.98")).Round().Print(" => DISCOUNT\r\n"); //164.9 => DISCOUNT */
  public static Num pct(Num rate, Num all) { return rate.Mul(all.Shift(-2)); }
    
   /** CALCULATOR MODE: pct, PERCENTAGE VALUE BY String, String */
  /**  CODE: Num.pct("10.00", "1_648.98").Round().Print(" => DISCOUNT\r\n"); //164.9 => DISCOUNT */
  public static Num pct(String rate, String all) { Num R = new Num(rate); Num A = new Num(all); return R.Mul(A.Shift(-2)); }

   /** CALCULATOR MODE: pct, PERCENTAGE VALUE FOR String -PCT BY ONE */
  /**  CODE: Num.pct("2.75").Print(" => PCT BY ONE\r\n"); //0.0275 => PCT BY ONE */
  public static Num pct(String rate) { Num R = new Num(rate); Num A = new Num(1); return R.Mul(A.Shift(-2)); }

   /** CALCULATOR MODE: rate, WITH THE ALL, RETURN THE RATE OF SPECIFIED PERCENTAGE BY Num, Num */
  /**  CODE: Num.rate(new Num("20.0"), new Num("1000.0")).Print("\r\n"); //2.0 */
  public static Num rate(Num pct, Num all) { return pct.Shift(2).Div(all); }
    
   /** CALCULATOR MODE: rate, WITH THE ALL, RETURN THE RATE OF SPECIFIED PERCENTAGE BY String, String */
  /**  CODE: Num.rate("20.0", "1000.0").Print("\r\n"); //2.0 */
  public static Num rate(String pct, String all) { Num PCT = new Num(pct); Num ALL = new Num(all); return PCT.Shift(2).Div(ALL); }
    
   /** CALCULATOR MODE: all, WITH THE RATE AND PERCENTAGE RETURN THE ALL BY Num, Num */ 
  /**  CODE: Num.all(new Num(2), new Num(20)).Print("\r\n"); //1000 */
  public static Num all(Num RATE, Num PCT) { return PCT.Shift(2).Div(RATE); }

   /** CALCULATOR MODE: all, WITH THE RATE AND PERCENTAGE RETURN THE ALL BY String, String */ 
  /**  CODE: Num.all("2.0", "20.0").Print("\r\n"); //1000 */
  public static Num all(String RATE, String PCT) { return new Num(PCT).Shift(2).Div(new Num(RATE)); }

   /** CALCULATOR MODE: pth, RETURN THE PERTHOUSAND TO SPECIFIED RATE AND ALL BY Num, Num */
  /**  CODE: Num.pth(new Num("2.0"), new Num("10000.0")).Print("\r\n"); //20.0 */
  public static Num pth(Num rate_th, Num all) { return rate_th.Mul(all.Shift(-3)); }
  
   /** CALCULATOR MODE: pth, RETURN THE PERTHOUSAND TO SPECIFIED RATE AMD ALL BY String, String */
  /**  CODE: Num.pth("2.0", "10000.0").Print("\r\n"); //20.0 */
  public static Num pth(String rate_th, String all) { return new Num(rate_th).Mul(new Num(all).Shift(-3)); }
  
   /** CALCULATOR MODE: pth, RETURN THE PERTHOUSAND TO SPECIFIED RATE -PTH BY ONE */
  /**  CODE: Num.pth("2.0").Print("\r\n"); //0.002 */
  public static Num pth(String rate_th) { return new Num(rate_th).Mul(new Num(1).Shift(-3)); }

   /** CALCULATOR MODE: rate_th, WITH PTH AND ALL RETURN THE RATE BY Num, Num */
  /**  CODE: Num.rate_th(new Num("20.0"), new Num("10000.0")).Round().Print(" => RATE_TH\r\n"); //2.0 => RATE_TH */
  public static Num rate_th(Num pth, Num all) { return pth.Shift(3).Div(all); }
  
   /** CALCULATOR MODE: rate_th, WITH PTH AND ALL RETURN THE RATE BY String, String */
  /**  CODE: Num.rate_th("20.0", "10000.0").Print(" => RATE_TH\r\n"); //2.0 => RATE_TH */
  public static Num rate_th(String pth, String all) { return new Num(pth).Shift(3).Div(new Num(all)); }
  
   /** CALCULATOR MODE: all_th, WITH RATE AND PERTHOUSAND RETURN THE ALL BY Num, Num */ 
  /**  CODE: Num.all_th(new Num("2.00"), new Num("20.00")).Print(" => ALL_TH\r\n"); //10000.0 => ALL_TH */
  public static Num all_th(Num rate, Num pth) { return pth.Shift(3).Div(rate); }
  
   /** CALCULATOR MODE: all_th, WITH RATE AND PERTHOUSAND RETURN THE ALL BY String, String */ 
  /**  CODE: Num.all_th("2.00", "20.00").Print(" => ALL_TH\r\n"); //10000.0 => ALL_TH */
  public static Num all_th(String rate, String pth) { return new Num(pth).Shift(3).Div(new Num(rate)); }

   /** price_sell, FINAL PRICE WITH DISCOUNTS */
  /** CODE: Num.print(Num.price_sell(new Num("1007.79"), new Num("5.75"), new Num("4.25") , new Num("3.75"), new Num("2.25"), new Num("22.0")) + "\r\n"); //1043.91 */
  public static Num price_sell(Num price_base, Num discount1, Num discount2, Num discount3, Num discount4, Num TAX) {
    Num PRICE_BASE  = new Num(price_base);
    Num PRICE_BASE2 = new Num(price_base);
    Num D1 = Num.pct(PRICE_BASE, discount1).Round(2);
    PRICE_BASE = PRICE_BASE.Sub(D1);
    Num D2 = Num.pct(PRICE_BASE, discount2).Round(2);
    PRICE_BASE = PRICE_BASE.Sub(D2);
    Num D3 = Num.pct(PRICE_BASE, discount3).Round(2);
    PRICE_BASE = PRICE_BASE.Sub(D3);
    Num D4 = Num.pct(PRICE_BASE, discount4).Round(2);
    PRICE_BASE = PRICE_BASE.Sub(D4);
    if(PRICE_BASE2.Sub(D1.Add(D2).Add(D3).Add(D4)).NE(PRICE_BASE)) throw new ArithmeticException("Num.price_sell( => SQUARENESS error: " + price_base);
    Num PCT  = Num.pct(PRICE_BASE, TAX).Round(2);
    return PRICE_BASE.Add(PCT);
  }
  
   /** len, RETURN Num STRING LENGTH */
  /**  CODE: Num a = new Num(100); Num.print(a, " "); Num.print(Num.len(a), "\r\n"); //100.0 5 */
  public static int len(Num n) { return n.length(); } 
  
   /** is OPERATOR, TWO VARIABLES WITH A SAME ADDRESS MEANS ONE OBJECT) */ 
  /**  CODE: Num a = new Num("3.14"); Num b = a; Num.print(Num.is(a, b), "\r\n"); //true */
  public static boolean is(Num a, Num b) { return a.hashCode() == b.hashCode(); }

   /** is_not OPERATOR, TWO VARIABLES WITH A DIFFERENT ADDRESS MEANS TWO OBJECT */
  /**  CODE: Num a = new Num("3.14"); Num b = a; Num.print(Num.is_not(a, b), "\r\n"); //false */
  public static boolean is_not(Num a, Num b) { return a.hashCode() != b.hashCode(); }

   /**  or, OR LOGIC BINARY OPERATOR BY Num */
  /**   CODE: Num a = new Num("0.0"); Num b = new Num("0.02"); if(Num.or(a, b) == true) Num.print(a.toString() + " or " + b.toString(), " => true\r\n"); else Num.print(a.toString() + " or " + b.toString(), " => false\r\n"); //0.0 or 0.02 => true */
  public static boolean or(Num a, Num b) { return a.Is_true() || b.Is_true(); }
  
   /**  or, OR LOGIC BINARY OPERATOR BY String */
  /**   CODE: String a = "0.0"; String b = "0.02"; if(Num.or(a, b) == true) Num.print(a.toString() + " or " + b.toString(), " => true\r\n"); //0.0 or 0.02 => true */
  public static boolean or(String A, String B) { Num a = new Num(A); Num b = new Num(B); return a.Is_true() || b.Is_true(); }
  
   /**  or, OR LOGIC BINARY OPERATOR BY int */
  /**   CODE: int a = 0; int b = 1; if(Num.or(a, b) == true) Num.print(a + " or " + b, " => true\r\n"); //0 or 1 => true */
  public static boolean or(int A, int B) { Num a = new Num(A); Num b = new Num(B); return a.Is_true() || b.Is_true(); }
  
   /**  or, OR LOGIC BINARY OPERATOR BY long */
  /**   CODE: long a = 0; long b = 1; if(Num.or(a, b) == true) Num.print(a + " or " + b, " => true\r\n"); //0 or 1 => true */
  public static boolean or(long A, long B) { Num a = new Num(A); Num b = new Num(B); return a.Is_true() || b.Is_true(); }
  
   /**  or, OR LOGIC BINARY OPERATOR BY BigInteger */
  /**   CODE: BigInteger a = new BigInteger("0"); BigInteger b = new BigInteger("1"); if(Num.or(a, b) == true) Num.print(a + " or " + b, " => true\r\n"); //0 or 1 => true */
  public static boolean or(BigInteger A, BigInteger B) { Num a = new Num(A); Num b = new Num(B); return a.Is_true() || b.Is_true(); }
  
   /**  and, AND LOGIC BINARY OPERATOR BY Num */
  /**   CODE: Num a = new Num("0.001"); Num b = new Num("0.02"); if(Num.and(a, b) == true) Num.print(a.toString() + " and " + b.toString(), " => true\r\n"); else Num.print(a.toString() + " and " + b.toString(), " => false\r\n"); //0.001 and 0.02 => true */
  public static boolean and(Num a, Num b) { return a.Is_true() && b.Is_true(); }

   /**  and, AND LOGIC BINARY OPERATOR BY String */
  /**   CODE:  String a = "0.001"; String b = "0.02"; if(Num.and(a, b) == true) Num.print(a.toString() + " and " + b.toString(), " => true\r\n"); else Num.print(a.toString() + " and " + b.toString(), " => false\r\n"); //0.001 and 0.02 => true */
  public static boolean and(String A, String B) { Num a = new Num(A); Num b = new Num(B); return a.Is_true() && b.Is_true(); }
  
   /**  and, AND LOGIC BINARY OPERATOR BY int */
  /**   CODE:  int a = 1; int b = 2; if(Num.and(a, b) == true) Num.print(a + " and " + b, " => true\r\n"); else Num.print(a + " and " + b, " => false\r\n"); //1 and 2 => true */
  public static boolean and(int A, int B) { Num a = new Num(A); Num b = new Num(B); return a.Is_true() && b.Is_true(); }
  
   /**  and, AND LOGIC BINARY OPERATOR BY long */
  /**   CODE:  long a = 1; long b = 2; if(Num.and(a, b) == true) Num.print(a + " and " + b, " => true\r\n"); else Num.print(a + " and " + b, " => false\r\n"); //1 and 2 => true */
  public static boolean and(long A, long B) { Num a = new Num(A); Num b = new Num(B); return a.Is_true() && b.Is_true(); }
  
   /**  and, AND LOGIC BINARY OPERATOR BY BigInteger */
  /**   CODE:  BigInteger a = new BigInteger("1"); BigInteger b = new BigInteger("2"); if(Num.and(a, b) == true) Num.print(a + " and " + b, " => true\r\n"); else Num.print(a + " and " + b, " => false\r\n"); //1 and 2 => true */
  public static boolean and(BigInteger A, BigInteger B) { Num a = new Num(A); Num b = new Num(B); return a.Is_true() && b.Is_true(); }
  
    /** f_price_over, ADD OR SUB A PERCENTAGE VALUE TO PRICE BY Num */
   /**  CODE: Num overPrice = Num.f_price_over(new Num(1000), new Num(22)); overPrice.Print("\r\n");   //1220.0 */
  /**   CODE: Num overPrice = Num.f_price_over(new Num(1000), new Num(-22)); overPrice.Print("\r\n"); //780.0  */
  public static Num f_price_over(Num price, Num t) { Num THIS = new Num(price.Mul(t).Shift(-2).Add(price)); return THIS; }

    /** f_price_over, ADD OR SUB A PERCENTAGE VALUE TO PRICE BY String */
   /**  CODE: Num overPrice = Num.f_price_over("1000.0", "22.0"); overPrice.Print("\r\n");   //1220.0 */
  /**   CODE: Num overPrice = Num.f_price_over("1000.0", "-22.0"); overPrice.Print("\r\n"); //780.0  */
  public static Num f_price_over(String price, String t) { Num THIS = new Num(new Num(price).Mul(t).Shift(-2).Add(price)); return THIS; }

   /** f_price_spinoff, SPIN OFF PERCENTAGE TAX VALUE FROM PRICE BY Num */  
  /**  CODE: Num priceRaw = Num.f_price_spinoff(new Num(100), new Num(22)).Round(2); priceRaw.Print("\r\n"); //81.97 */
  public static Num f_price_spinoff(Num price, Num t) { Num THIS = new Num(price.Div((t.Add(100).Shift(-2)))); return THIS; }

   /** f_price_spinoff, SPIN OFF PERCENTAGE TAX VALUE FROM PRICE BY String */  
  /**  CODE: Num priceRaw = Num.f_price_spinoff("100.0", "22.0").Round(2); priceRaw.Print("\r\n"); //81.97 */
  public static Num f_price_spinoff(String price, String t) { Num THIS = new Num(new Num(price).Div((new Num(t).Add(100).Shift(-2)))); return THIS; }

   /** f_fund_fr, FRENCH FINANCING MONTH MORTGAGE BY Num */
  /**  CODE: Num principal = new Num("80_000.00"); Num rate = new Num(3); int months = 120; Num.print(Num.f_fund_fr(principal, rate, months).Round(2), "\r\n"); //772.49 */
  public static Num f_fund_fr(Num asset, Num I, int n) {
	  Num i = I.Div(100).Div(12);
	  Num K = new Num(i.Add(1).Pow(n));
	  Num N = new Num(asset.Mul(i).Mul(K));
	  Num D = new Num(K.Sub(1));	  
	  return N.Div(D);
  } 
  
   /** f_perf, PERCENTAGE PERFORMANCE VALUE (DIRECT RATIO) BY Num */
  /**  CODE: Num.f_perf(new Num(50), new Num(75)).Print("\r\n"); //50.0 */
  public static Num f_perf(Num a, Num sob) { return (sob.Sub(a)).Div(a).Shift(2); }

   /** f_perf, PERCENTAGE PERFORMANCE VALUE (DIRECT RATIO) BY String */
  /**  CODE: Num.f_perf("50.0", "75.0").Print("\r\n"); //50.0 */
  public static Num f_perf(String a, String sob) { Num A = new Num(a); return (new Num(sob).Sub(A)).Div(A).Shift(new Num(2)); }

    /** f_perf_time, PERCENTAGE AND RELATIVE MAGNITUDE ORDER TIME PERFORMANCE VALUE (INVERSE RATIO) BY Num */
   /**  RETURN ARRAY BY TWO ELEMENTS */
  /**   CODE: Num A[] = Num.f_perf_time(new Num(50), new Num("37.5")); A[0].Round().Print("\r\n"); A[1].Round(2).Print("\r\n"); //33.33 0.33 */
  public static Num[] f_perf_time(Num a, Num sob) {
    Num[] A = new Num[2];
    Num THIS= new Num(a);
    Num R = ((THIS.Sub(sob)).Div(sob).Mul(100));
    if(sob.GT(THIS) == true) { A[0] = R; A[1] = sob.Invsign().Div(THIS).Add(1); return A;
    } else { A[0] = R; A[1] = THIS.Div(sob).Sub(1); return A; }
  }

    /** f_perf_time, PERCENTAGE AND RELATIVE MAGNITUDE ORDER TIME PERFORMANCE VALUE (INVERSE RATIO) BY String */
   /**  RETURN ARRAY BY TWO ELEMENTS */
  /**   CODE: Num A[] = Num.f_perf_time("50.0", "37.5"); A[0].Round().Print("\r\n"); A[1].Round(2).Print("\r\n"); //33.33 0.33 */
  public static Num[] f_perf_time(String a, String sob) {
    Num SOB = new Num(sob);
    Num[] A = new Num[2];
    Num THIS= new Num(new Num(a));
    Num R = ((THIS.Sub(SOB)).Div(SOB).Mul(100));
    if(SOB.GT(THIS) == true) { A[0] = R; A[1] = SOB.Invsign().Div(THIS).Add(1); return A;
    } else { A[0] = R; A[1] = THIS.Div(SOB).Sub(1); return A; }
  }

   /** f_fileread, READ A NUMBER STRINGS COLUMN FROM DISK FILE DEFAULT NAMED "nums.txt" => 10.0\r\n11.0\r\n12.0\r\n */
  /**  CODE: Num.print(Num.f_fileread(), "\r\n"); //READING FILE nums.txt => [10.0, 11.0, 12.0] */
  public static ArrayList<Num> f_fileread() {
    String filename = "nums.txt";
    ArrayList<Num> LN = new ArrayList<>();
    File file = new File(filename);
    Scanner in = null;
    try {
      in = new Scanner(file);
      while(in.hasNextLine()) LN.add(new Num(in.nextLine())); 
      in.close();
    } catch (Exception e) { Num.print("Num.f_fileread => data reading disk error: " + filename + "\r\n"); }
    return LN;
  }

   /** f_fileread, READ A NUMBER STRINGS COLUMN FROM DISK FILE => 10.0\r\n11.0\r\n12.0\r\n */
  /**  CODE: Num.print(Num.f_fileread("nums.txt"), "\r\n"); //READING FILE nums.txt => [10.0, 11.0, 12.0] */
  public static ArrayList<Num> f_fileread(String filename) {
    ArrayList<Num> LN = new ArrayList<>();
    File file = new File(filename);
    Scanner in = null;
    try {
      in = new Scanner(file);
      while(in.hasNextLine()) LN.add(new Num(in.nextLine()));
      in.close();
    } catch (Exception e) { Num.print("Num.f_fileread => File not found: " + filename + "\r\n"); }
    return LN;
  }
  
   /** f_filewriteString, WRITE OR APPEND A SINGLE STRING ON DISK */
  /**  CODE: Num.f_filewriteString("13.0" + "\r\n", "nums.txt"); */
  public static String f_filewriteString(String single, String filename) {
      try (FileWriter file = new FileWriter(filename, true)) {
        file.write(single);
      } catch (Exception e) { Num.print("Num.f_filewriteString => Unable to write to file: " + filename + "\r\n"); }
      return single;
  }

   /** f_filewrite, WRITE A NUMBER STRINGS COLUMN ON DISK BY ArrayList<Num> -DEFAULT FILE nums.txt */
  /**  CODE: ArrayList<Num> nums = new ArrayList<>(Arrays.asList(new Num("3.14"), new Num("2.72"), new Num("1.0"))); Num.f_filewrite(nums); //WRITING FILE nums.txt => 3.14\r\n2.72\r\n1.0\r\n */
  public static void f_filewrite(ArrayList<Num> nums) {
    String filename = "nums.txt";   
    try {
          try (BufferedWriter file = new BufferedWriter(new FileWriter(filename))) {
              for (Num num : nums) { file.write(num.toString()); file.newLine(); } //WRITES PLATFORM-INDEPENDENT NEWLINE
          }
    } catch (Exception e) { Num.print("Num.f_filewrite => Unable to write file: " + filename + "\r\n"); }
  }

   /** f_filewrite, WRITE A NUMBER STRINGS COLUMN ON DISK */
  /**  CODE: ArrayList<Num> nums = new ArrayList<>(Arrays.asList(new Num("3.14"), new Num("2.72"), new Num("1.0"))); Num.f_filewrite(nums, "nums.TXT"); //WRITING FILE nums.txt => 3.14\r\n2.72\r\n1.0\r\n */
  public static void f_filewrite(ArrayList<Num> nums, String filename) {
    //String filename = "nums.txt";   
    try {
      try (BufferedWriter file = new BufferedWriter(new FileWriter(filename))) {
          for (Num num : nums) { file.write(num.toString()); file.newLine(); } //WRITES PLATFORM-INDEPENDENT NEWLINE
      }
    } catch (Exception e) { Num.print("Num.f_filewrite => Unable to write file: " + filename + "\r\n"); }
    
  }

   /** f_filewrite_RandomIntFast, WRITE A RANDOM INTEGER NUMBER STRINGS COLUMN ON DISK -DEFAULT FILE nums.txt */
  /**  CODE: Num.f_filewrite_RandomIntFast(new Num("1000000000000000000000.0"), new Num("9000000000000000000000.0"), 10); */
  public static int f_filewrite_RandomIntFast(Num min, Num max, int qty) {
    String filename = "nums.txt"; 
    ArrayList<Num> L = new ArrayList<>();
    int i = 0;
    for ( ; i < qty; i++) L.add(Num.randInt(min, max)); 
    Num.f_filewrite(L, filename);
    return i;
  }

   /** f_filewrite_RandomIntFast, WRITE A RANDOM INTEGER NUMBER STRINGS COLUMN ON DISK BY FILE NAME */
  /**  CODE: Num.f_filewrite_RandomIntFast(new Num("1000000000000000000000.0"), new Num("9000000000000000000000.0"), 10, "nums.txt"); */
  public static int f_filewrite_RandomIntFast(Num min, Num max, int qty, String filename) {
	  //String filename = "nums.txt"; 
	  ArrayList<Num> L = new ArrayList<>();
	  int i = 0;
	  for ( ; i < qty; i++) L.add(Num.randInt(min, max)); 
	  Num.f_filewrite(L, filename);
	  return i;
  }
  
   /** f_filewrite_RandomInt, WRITE A RANDOM INTEGER NUMBER STRINGS COLUMN ON DISK -DEFAULT FILE nums.txt */
  /**  CODE: Num.print(Num.f_filewrite_RandomInt(new Num("1000000000000000000000000000000000000000000.0"), new Num("9000000000000000000000000000000000000000000.0"), 3) + "\r\n"); */
  public static int f_filewrite_RandomInt(Num min, Num max, int qty) {
    String filename = "nums.txt"; 
    int i = 0;
    for ( ; i < qty; i++) Num.f_filewriteString(Num.randInt(min, max).toString() + "\r\n", filename);
    return i;
  }

   /** f_filewrite_RandomInt, WRITE A RANDOM INTEGER NUMBER STRINGS COLUMN ON DISK BY FILE NAME */
  /**  CODE: Num.print(Num.f_filewrite_RandomInt(new Num("1000000000000000000000000000000000000000000.0"), new Num("9000000000000000000000000000000000000000000.0"), 3, "nums.txt") + "\r\n"); */
  public static int f_filewrite_RandomInt(Num min, Num max, int qty, String filename) {
	  //String filename = "nums.txt"; 
	  int i = 0;
	  for ( ; i < qty; i++) Num.f_filewriteString(Num.randInt(min, max).toString() + "\r\n", filename);
	  return i;
  }
  
   /** copy, COPY Num OBJECT */
  /**  CODE: Num a = new Num(3); Num b = Num.copy(a); if(a == b) Num.print("UNIQUE OBJECT\n"); else Num.print("DOUBLED OBJECT\n"); //DOUBLED OBJECT */
  public static Num copy(Num a) { return new Num(a); }

   /** randInt, RANDOM Num INTEGER BETWEEN MIN AND MAX BY Num, Num */
  /**  CODE: for (int i = 0; i < 3; i++) Num.print(i + " => " + Num.randInt(new Num("1000000000000000.0"), new Num("6000000000000000.0")), "\r\n"); //0 => ... */
  public static Num randInt(Num min, Num max) { 
    SecureRandom random = new SecureRandom();
    BigInteger MIN = min.toBigInt();
    BigInteger range = max.toBigInt().subtract(MIN).add(new BigInteger("1")); //MAX - MIN + 1
    BigInteger result;
    do result = new BigInteger(range.bitLength(), random);
    while (result.compareTo(range) >= 0); //ENSURE RESULT < RANGE
    return new Num(result.add(MIN));     //SHIFT INTO [MIN, MAX] RANGE
   }

   /** randInt, RANDOM Num INTEGER BETWEEN MIN AND MAX BY String, String */
  /**  CODE: for (int i = 0; i < 3; i++) Num.print(i + " => " + Num.randInt("1000000000000000.0", "6000000000000000.0"), "\r\n"); //0 => ... */
  public static Num randInt(String min, String max) { return Num.randInt(new Num(min), new Num(max)); }

   /** randInt, RANDOM Num INTEGER BETWEEN MIN AND MAX BY int, int */
  /**  CODE: for (int i = 0; i < 3; i++) Num.print(i + " => " + Num.randInt(100, 600), "\r\n"); //0 => ... */
  public static Num randInt(int min, int max) { return Num.randInt(new Num(min), new Num(max)); }

   /** randInt, RANDOM Number INTEGER BETWEEN MIN AND MAX BY long, long */
  /**  CODE: for (int i = 0; i < 3; i++) Num.print(i + " => " + Num.randInt(100L, 600L), "\r\n"); //0 => ... */
  public static Num randInt(long min, long max) { return Num.randInt(new Num(min), new Num(max)); }

   /** randInt, RANDOM Num INTEGER BETWEEN MIN AND MAX BY BigInteger, BigInteger */
  /**  CODE: for (int i = 0; i < 3; i++) Num.print(i + " => " + Num.randInt(new BigInteger("100"), new BigInteger("600")), "\r\n"); //0 => ... */
  public static Num randInt(BigInteger min, BigInteger max) { return Num.randInt(new Num(min), new Num(max)); }

   /** randFloat, RANDOM Number FLOAT BETWEEN MIN AND MAX */
  /**  CODE: for (int i = 0; i < 3; i++) Num.print(i + " => " + Num.randFloat(1953.14, 2000.0), "\r\n"); //0 => ... */
  public static Num randFloat(double min, double max) { 
    Random rand = new Random();
    double random = rand.nextDouble() * (max - min) + min;
    String tr = random + "";
    return new Num(tr.equals("-0.0") ? "0.0" : tr);
  }

     /** toFloat, Num TO float */
    /**  Num.print(Num.toFloat(new Num("-3.141592654")) - 0.000000002, "\r\n"); //-3.1415926560000003 */
    public static double toFloat(Num a) { return Double.parseDouble(a.n); }

     /** ieee754, FLOAT TO IEEE754 CONVERSION METHOD */
    /**  CODE: for(double i = 0; i < 1.0; i = i + 0.1) Num.print(Num.ieee754(i), "\r\n"); //0.0 0.1000000000000000055511151231257827021181583404541015625 0.200000000000000011102230246251565404236316680908203125 ... */
    public static Num ieee754(double a) { 
    	BigDecimal an = new BigDecimal(a); 
    	Num k = new Num(0);
    	try { k = new Num(an.toPlainString()); }
    	catch(Exception e) { return new Num(a + ""); }
    	return k;
    }
	
     /** sqrt, SQUARE ROOT METHOD BY Num -DEFAULT TEN DECIMALs */
    /**  CODE: Num.print(Num.sqrt(new Num("2.0")), "\r\n"); //1.4142135623 */
    public static Num sqrt(Num n) { return Num.sqrt(n, 10); }

     /** sqrt, SQUARE ROOT METHOD BY String -DEFAULT TEN DECIMALs */
    /**  CODE: Num.print(Num.sqrt("2.0"), "\r\n"); //1.4142135623 */
    public static Num sqrt(String n) { return Num.sqrt(new Num(n), 10); }

     /** sqrt, SQUARE ROOT METHOD BY String, int */
    /**  CODE: Num.print(Num.sqrt("3.14", 50), "\r\n"); //1.77200451466693504019911250975363152507360851616294 */
    public static Num sqrt(String n, int d) { return Num.sqrt(new Num(n), d); }

     /** sqrt, SQUARE ROOT METHOD BY Num, int */
    /**  CODE: Num.print(Num.sqrt(new Num("3.141592654"), 10), "\r\n"); //1.7724538510 */ 
    public static Num sqrt(Num n, int d) {
      d = d < 0 ? -d : d; //ABSOLUTE VALUE
      BigInteger TEN = new BigInteger("10");
      BigInteger TWO = new BigInteger("2");
      if (n.Is_numint() && d == 0) {
        //ONLY INTEGER SQUARE ROOT RESULT
        if (n.Is_negative()) throw new ArithmeticException("Num.sqrt => Negative number: " + n);
        if (Num.not(n)) return new Num("0.0");                 //ROOT ZERO
        int L = (n.toString().length() + 1) >> 1; //TWO DIVISION TO OBTAIN INTEGER ROOT SIZE
        BigInteger r = TEN.pow(L); //NEWTON'S METHOD ON BIGINT
        BigInteger N = new BigInteger(n.n0);
        BigInteger q = N.divide(r);
        while (r.compareTo(q) > 0) { //r > q
          r = r.add(q).divide(TWO);
          q = N.divide(r);
        } //BigInteger TWO DIVISION
        return new Num(r);
      }
      String[] nv = n.n.split("\\.");
      if (new Num(nv[0] + ".0").Is_negative()) throw new ArithmeticException("Num.sqrt => Negative number: " + n);
      BigInteger n0 = new BigInteger(nv[0]);
      int L_n1 = nv[1].length();             //FLOATING DIGIT NUMBER
      BigInteger n1 = new BigInteger(nv[1]);
      int L_rx = (n0.toString().length() + 1) >> 1; //X2DIVISION - ROOT DIGIT LENGTH
      int ds, shift;
      String root, temp;
      if (n0.toString().equals("0")) { //FLOATING-POINT OPERAND (0.d0-9) => 0 < n < 1 (0.000604569744 => 0.024588)
        shift = (L_n1 + 1) >> 1; //X2DIVISION - DECIMAL POINT POSITION
        ds = d - shift;
        String op;
        if ((L_n1 % 2) == 1) { //0.314 => 0.56035702904487599547714975217160596640514315474201026125944184339517442453951772
          if(ds <= 0) op = n1.toString() + "0"; //ODD (DISPARI)
          else op = n1.toString() + "0" + String.format("%0" + ds + "d", 0) + String.format("%0" + ds + "d", 0); //ODD (DISPARI)
        } 
        else  { //0.0314 => 0.17720045146669350401991125097536315250736085161629429668177719702909929723489025
          if(ds <= 0) op = n1.toString() + "";
          else op = n1.toString() + String.format("%0" + ds + "d", 0) + String.format("%0" + ds + "d", 0); //EVEN (PARI)
        }       
        String r1 = Num.sqrt(new Num(op + ".0"), 0).n0;
        int r1_len = r1.length();
        int CHECK = shift - r1_len + ds;
        if (ds > 0) { 
          if(CHECK <= 0) return new Num("0." + r1.substring(0, r1_len)); //0.328149 => 0.5728429104 d>=4
          else return new Num("0." + String.format("%0" + CHECK + "d", 0) + r1.substring(0, r1_len)); //0.00328149 => 0.05728 d>=5
        } 
        if(shift - r1_len <= 0) return new Num("0." + (r1.substring(0, d).equals("") ? "0" : r1.substring(0, d))); //0.328149 => 0.572 d<4 
        else return new Num("0." + (String.format("%0" + (shift - r1_len) + "d", 0) + r1).substring(0, d == 0 ? 1 : d)); //0.00328149 => 0.057 d<5 
      } else if (n1.toString().equals("0")) { //INTEGER OPERAND (1-9d.0) //9 => 3
        root = Num.sqrt( new Num(nv[0] + String.format("%0" + (d * 2) + "d", 0)  + ".0"), 0 ).n0;
        return new Num(root.substring(0, L_rx) + "." + root.substring(L_rx));     //257.0 => 16.03 (d=2)
      } else {//FLOATING-POINT OPERAND (1-9d.0-9)
        if ((L_n1 % 2) == 1) { //DECIMALS ODD (DISPARI) //25.9 => 5.08920425999978900434230163474327049632257029638808099114709622220026665997431335
          d = (d == 0 ? 1 : d);
          temp = Num.sqrt( new Num(nv[0] + nv[1] + "0" + String.format("%0" + (d * 2) + "d", 0) + ".0"), 0 ).n0;
          return new Num(temp.substring(0, L_rx) + "." + temp.substring(L_rx, L_rx + d));
        } //DECIMALS EVEN (PARI) //25.96 => 5.09509568114279871567534636517863967354699087400503518520374790623939934333586014
          temp = Num.sqrt( new Num(nv[0] + nv[1] + String.format("%0" + (d * 2) + "d", 0) + ".0"), 0 ).n0;
          return new Num(temp.substring(0, L_rx) + "." + temp.substring(L_rx, L_rx + d));
        } 
    } //END sqrt METHOD

   /** CALCULATOR MODE: ITH ROOT METHOD BY Num, int, int*/
  /**  CODE: Num a = new Num("27.3"); int root = -3; Num.print(a + "\t"); Num.print(root + "\r\n"); Num.print(Num.root_i(a, root).Round(32), "\r\n"); //27.3 -3 0.33210783207389089491177684293055 */
  public static Num root_i(Num n, int I, int d) { 
    if (I == 0) return new Num("1.0");
    Num i = new Num(I);
    if (i.Is_numeven() && !n.n2.equals("")) throw new ArithmeticException("Num.root_i => Negative number: " + n.n);
    if (i.Is_negative()) { n = new Num(1).Div(n); I = I < 0 ? -I : I; } //, n.L_n0 + n.L_n1
    String sign = (n.LT(0) ? "-"  : "");
    n = n.Abs();
    String[] N = n.n.split("\\.");
    String n0 = N[0]; String n1 = N[1];
    String n01 = n0 + n1;
    d = (d < (n.L_n0 + n.L_n1) ? (n.L_n0 + n.L_n1) : d);
    int W = I * d - n1.length(); //SET PRECISION  
    if(W > 0) n01 = n01 + String.format("%0" + W + "d", 0); //INTEGER CONVERSION
    BigInteger z = new BigInteger(n01);
    BigInteger iI = new BigInteger(I + "");
    BigInteger s = z.add(new BigInteger("1")); //BigInteger
    BigInteger nN = z;
    BigInteger t;
    BigInteger Ii = iI.subtract(new BigInteger("1"));
    while (z.compareTo(s) < 0) { //NEWTON'S METHOD z<s
      s = z;
      try {
        t = Ii.multiply(s).add(nN.divide(s.pow(Ii.intValue())));
      } catch(Exception e) {
        throw new ArithmeticException("Num.root_i => d parameter too low: " + d);
      }
      z = t.divide(iI);  
    }
    String S = s.toString(), r;
    int S_len = S.length(); 
    if(1 + d - S_len > 0) S = String.format("%0" + (1 + d - S_len) + "d", 0) + S;
    if(S_len-d <= 0) r = Num.rstrip(S.substring(0, 1) + "." + S.substring(1));
    else r = Num.rstrip(S.substring(0, S_len-d) + "." + S.substring(S_len-d));
    S = r.substring(r.length()-1).equals(".") ? r + "0" : r ;
    return new Num(sign + S);
  }

   /** CALCULATOR MODE: ITH ROOT METHOD BY Num, int -DEFAULT TEN DECIMALs */ 
  /**  CODE: Num a = new Num("3126.0"); Num.print(Num.root_i(a, 5), "\r\n"); //5.0003199590 */
  public static Num root_i(Num n, int I) { return Num.root_i(n, I, 10); }
  
   /** CALCULATOR MODE: CUBE ROOT METHOD BY Num -DEFAULT TEN DECIMALs */ 
  /**  CODE: Num a = new Num("27.006"); Num.print(Num.cube_root(a), "\r\n"); //3.0002222057 */
  public static Num cube_root(Num n) { return Num.root_i(n, 3, 10); }

     /** is_perfectSquare, PERFECT SQUARE METHOD BY Num -DEFAULT PRECISION TEN */
    /**  CODE: Num.print(Num.is_perfectSquare(new Num(9)), "\r\n"); //true */
    public static boolean is_perfectSquare(Num N) {
      Num SR = Num.sqrt(N, 10); //PRECISION
      Num SQ = SR.Mul(SR);
      return SQ.EQ(N);
    }

     /** is_perfectSquare, PERFECT SQUARE METHOD BY String -DEFAULT PRECISION TEN */
    /**  CODE: Num.print(Num.is_perfectSquare("347466104389379309947251979729.0"), "\r\n"); //true (589462555544777) */
    public static boolean is_perfectSquare(String n) {
      Num N = new Num(n);
      Num SR = Num.sqrt(N, 10); //PRECISION
      Num SQ = SR.Mul(SR);
      return SQ.EQ(N);
    }

     /** is_perfectSquare BY Num, int, PERFECT SQUARE METHOD */
    /**  CODE: Num.print(Num.is_perfectSquare(new Num("1.99996164"), 4), "\r\n"); //true (1.4142) */
    public static boolean is_perfectSquare(Num N, int d) {
      Num SR = Num.sqrt(N, d); //PRECISION
      Num SQ = SR.Mul(SR);
      return SQ.EQ(N);
    }

     /** is_perfectSquare, PERFECT SQUARE METHOD BY String, int */
    /**  CODE: Num.print(Num.is_perfectSquare("1.99996164", 4), "\r\n"); //true (1.4142) */
    public static boolean is_perfectSquare(String n, int d) {
      Num N = new Num(n);
      Num SR = Num.sqrt(N, d); //PRECISION
      Num SQ = SR.Mul(SR);
      return SQ.EQ(N);
    }
    
     /** sqrt_check, CHECK SQUARE ROOT OPERATION */
    /**  CODE: Num.print(Num.sqrt_check("1.73", "3.0"), "\r\n"); //true */
    public static boolean sqrt_check(String R, String N) { return new Num(N).Sqrt_check(R); }

     /** sqrt_check, CHECK SQUARE ROOT OPERATION */
    /**  CODE: Num.print(Num.sqrt_check(new Num("3.0"), new Num("9.0")), "\r\n"); //true */
    public static boolean sqrt_check(Num r, Num n) { return n.Sqrt_check(r); }

     /** sqrt_checkTable, CHECK SQUARE ROOT OPERATION BY TABLE */
    /**  CODE: if(Num.sqrt_checkTable()) Num.print("ERROR"); else Num.print("OK"); //OK */
    public static boolean sqrt_checkTable() { 
      Map<String, String> table = new LinkedHashMap<>();
      table.put("0.0", "0.0");
      table.put("1.0", "1.0");
      table.put("4.0", "2.0");
      table.put("100.0", "10.0");
      table.put("12321.0", "111.0");
      table.put("1.0e20", "1.0e10");
      table.put("2.0", "1.4142135623");
      table.put("3.0", "1.7320508075");
      table.put("10.0", "3.1622776601");
      table.put("123456789.0", "11111.1110605555");
      table.put("0.0001", "0.01");
      table.put("2.5", "1.58113883");
      table.put("123456789012345678901234567890.0", "351364182882014.4253111222");
      table.put("1.0e+20", "1.0e+10");
      table.put("1.0e+50", "1.0e+25");

      for (Map.Entry<String, String> entry : table.entrySet()) {
          if(Num.sqrt(entry.getKey()).NE(entry.getValue())) {
            Num.print(entry.getKey(), "\r\n");
            Num.print(Num.sqrt(entry.getKey()));
            Num.print( " => ", entry.getValue());
            return true; //ERROR
          } 
      }   
      return false; //OK
    }

     /** CHECK ADDITION OPERATION */
    /**  CODE: */
      /**  Num a = new Num(12); Num b = new Num(10); Num.print(a, " + "); Num.print(b, " = "); */
     /**   Num proof = new Num(22); Num.print(proof, " ADDITION RESULT => "); */
    /**    Num.print(Num.add_check(a, b, proof) ? "FAILURE" : "success", "\n"); //12.0 + 10.0 = 22.0 ADDITION RESULT => success */
    public static boolean add_check(Num A1, Num A2, Num SUM) { return !(A1.EQ(SUM.Sub(A2))); }

     /** CHECK ADDITION OPERATION */
    /**  CODE: Num.print(Num.add_check("12.0", "10.0", "22.0") ? "FAILURE" : "success", "\n"); //success */
    public static boolean add_check(String A1, String A2, String SUM) { return Num.add_check(new Num(A1), new Num(A2), new Num(SUM)); }

     /** CHECK SUBTRACTION OPERATION */
    /**  CODE: */
      /**  Num a = new Num(12); Num b = new Num(10); Num.print(a, " - "); Num.print(b, " = "); */
     /**   Num proof = new Num(2); Num.print(proof, " SUBTRACTION RESULT => "); */
    /**    Num.print(Num.sub_check(a, b, proof) ? "FAILURE" : "success", "\n"); //12.0 - 10.0 = 2.0 SUBTRACTION RESULT => success */
    public static boolean  sub_check(Num M, Num S, Num DIF) { return !(M.EQ(DIF.Add(S))); }

     /** CHECK SUBTRACTION OPERATION */
    /**  CODE: Num.print(Num.sub_check("12.0", "10.0", "2.0") ? "FAILURE" : "success", "\n"); //success */
    public static boolean  sub_check(String M, String S, String DIF) { return sub_check(new Num(M), new Num(S), new Num(DIF)); }

   /** CHECK MULTIPLICATION OPERATION */
  /**  CODE: */
    /**  Num a = new Num(12); Num b = new Num(10); Num.print(a, " * "); Num.print(b, " = "); */
   /**   Num proof = new Num(120); Num.print(proof, " PRODUCT RESULT => "); */
  /**    Num.print(Num.mul_check(a, b, proof) ? "FAILURE" : "success", "\n"); //12.0 * 10.0 = 120.0 PRODUCT RESULT => success */
  public static boolean mul_check(Num F1, Num F2, Num PRO) {
    //PRODUCT SIGN CHECKING...
    if(PRO.NE(0)) //ZERO PRODUCT NOT INCLUDED
        if      (F1.n2.equals("-") && F2.n2.equals("-") && PRO.n2.equals(""));     // - - => + 
        else if (F1.n2.equals("") && F2.n2.equals("") && PRO.n2.equals(""));      //  + + => +
        else if (F1.n2.equals("-") && F2.n2.equals("") && PRO.n2.equals("-"));   //   - + => -
        else if (F1.n2.equals("") && F2.n2.equals("-") && PRO.n2.equals("-"));  //    + - => - 
        else return true; //... FAILURE
    if ((F1.toString().equals("0.0") || F2.toString().equals("0.0")) &&  PRO.toString().equals("0.0")) return false;   //ZERO PRODUCT RESULT CHECKING... SUCCESS
    if ((F1.toString().equals("0.0") || F2.toString().equals("0.0")) && !PRO.toString().equals("0.0")) return true;   //NOT ZERO PRODUCT RESULT CHECKING... FAILURE
    if (PRO.toString().equals("0.0") && (!F1.toString().equals("0.0") || !F2.toString().equals("0.0"))) return true; //ZERO PRODUCT RESULT CHECKING... FAILURE
    String  F1_S = F1.n0   + F1.n1;
    int  F1_Slen = F1.L_n0 + F1.L_n1;
    int sum = 0;
    int sum2 = 0;
    for (int i = 0; i < F1_Slen; i++) {
      if (F1_S.charAt(i) == '0') continue; //SKIP ZERO DIGITS
      sum += (F1_S.charAt(i) - '0'); 
      if (sum > 9) sum -= 9;
    }
    String F2_S = F2.n0 + F2.n1;
    int F2_Slen = F2.L_n0 + F2.L_n1;
    for (int i = 0; i < F2_Slen; i++) {
      if (F2_S.charAt(i) == '0') continue; //SKIP ZERO DIGITS
      sum2 += (F2_S.charAt(i) - '0'); if (sum > 9) sum -= 9;
    }
    String s = sum * sum2 + "";
    int slen = s.length();
    sum = 0;
    for (int i = 0; i < slen; i++) {
      if (s.charAt(i) == '0') continue; //SKIP ZERO DIGITS
      sum += (s.charAt(i) - '0'); if (sum > 9) sum -= 9;
    }
    sum2 = 0;
    String PRO_S = PRO.n0 + PRO.n1;
    int PRO_Slen = PRO.L_n0 + PRO.L_n1;
    for (int i = 0; i < PRO_Slen; i++) {
      if (PRO_S.charAt(i) == '0') continue; //SKIP ZERO DIGITS
      sum2 += (PRO_S.charAt(i) - '0'); if (sum2 > 9) sum2 -= 9;
    }
    return !(sum2 == sum);
  }

   /** CHECK MULTIPLICATION OPERATION */
  /**  CODE: Num.print(Num.mul_check("12.0", "10.0", "120.0") ? "FAILURE" : "success", "\n"); //success */
  public static boolean mul_check(String F1, String F2, String PRO) { return Num.mul_check(new Num(F1), new Num(F2), new Num(PRO)); }

   /** div_check, CHECK DIVISION OPERATION */
  /**  CODE: */
    /**  Num a = new Num(-12); Num b = new Num(-10); Num.print(a, " mod "); Num.print(b, " = "); */
   /**   Num proof = new Num(-2); Num.print(proof, " DIVISION REM RESULT => "); */
  /**    Num.print(Num.div_check(a, b, proof) ? "FAILURE" : "success", "\n"); //-12.0 mod -10.0 = -2.0 DIVISION REM RESULT => success */
  public static boolean div_check(Num N, Num DIV, Num REM) {
    Num Q = Num.div(N, DIV).Round_floor();
    Num m = Q.Mul(DIV);
    Num s = m.Add(REM);
    if (Num.mul_check(Q, DIV, m)) return true;
    if (Num.add_check(m, REM, N)) return true;
    return !(N.EQ(s));
  }

   /** div_check, CHECK DIVISION OPERATION */
  /**  CODE: Num.print(Num.div_check("12.0", "10.0", "2.0") ? "FAILURE" : "success", "\n"); //success */
  public static boolean div_check(String N, String DIV, String REM) { return Num.div_check(new Num(N), new Num(DIV), new Num(REM)); }

   /** test_num7, NUMERIC STRING LIST FOR ARITHMETIC OPERATION TEST */
  /**  CODE: Num.test_num7(); //computing... */
  public static void test_num7() {
    String[] L = {
      "1.0",
      "-103.0",
      "954165405446.0",
      "-456789357444877954666666689389357444877954666665744487795466666666893893574448779546666657444877954666666666893574448779546666666893893574448779546666657444877954666666666893574448779546666666666666689357444877954666666644444495486470.0",
      "0.0000000000000000000000000000000000000000000000000000000000000000000008935744876408935744876446387797795466666935744487795466666574448779546666665466666463877089357448767795466666935744487795466666574448779546666664463877954666695466666",
      "-456789357444877954666666689389357444877954666665744487795466666666893893574448779546666657444877954666666666893574448779546666666893893574448779546666657444877954666666666893574448779546666666666666689357444877954666666644444495486470.0000000000000000000000000000000000000000000000000000000000000000000008935744876408935744876446387797795466666935744487795466666574448779546666665466666463877089357448767795466666935744487795466666574448779546666664463877954666695466666",
      "456789357444877954666666689389357444877954666665744487795466666666893893574448779546666657444877954666666666893574448779546666666893893574448779546666657444877954666666666893574448779546666666666666689357444877954666666644444495486470.0000000000000000000000000000000000000000000000000000000000000000000008935744876408935744876446387797795466666935744487795466666574448779546666665466666463877089357448767795466666935744487795466666574448779546666664463877954666695466666",
      "-893574489357444877954668938893574448779546666693574448779546666657444877954666666668935744487795466666487795466666.0",
      "8935744487795466666.65401",
      "-6577116546540.654981112370893574487644638779546666680893574487644638779546666695440456795132",
      "777549511321456795134440.0333",
      "-951089357448764089357448764463877954666664638089350893574487644638779546666674487644638779546666677954666666540.649821222230",
    };

    Num SUM = new Num(0), DIF = new Num(0), PRO = new Num(0), QUO = new Num(0), REM = new Num(0);
    
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {        
        SUM = Num.add(L[i], L[j]);
        if (Num.add_check(new Num(L[i]), new Num(L[j]), SUM)) {
          Num.print(L[i], " + "); Num.print(L[j], " = ");
          SUM.Print("\r\n");
          Num.print("\r\nFAILURE - THIS SYSTEM DOES NOT SUPPORT ARBITRARY PRECISION ARITHMETIC (add)!\r\n");
          System.exit(1);
        }
        else Num.print("addition passed.\r\n");
        Num.print("------------------------------\r\n");
      
        DIF = Num.sub(L[i], L[j]);
        if (Num.sub_check(L[i], L[j], DIF.toString())) {
          Num.print(L[i], " - "); Num.print(L[j], " = ");
          DIF.Print("\r\n");
          Num.print("\r\nFAILURE - THIS SYSTEM DOES NOT SUPPORT ARBITRARY PRECISION ARITHMETIC (sub)!\r\n");
          System.exit(1);
        }
        else Num.print("subtraction passed.\r\n");
        Num.print("------------------------------\r\n");
      
        PRO = Num.mul(L[i], L[j]);
        if (Num.mul_check(L[i], L[j], PRO.toString())) {
          Num.print(L[i], " * "); Num.print(L[j], " = ");
          PRO.Print("\r\n");
          Num.print("\r\nFAILURE - THIS SYSTEM DOES NOT SUPPORT ARBITRARY PRECISION ARITHMETIC (mul)!\r\n");
          System.exit(1);
        }
        else Num.print("multiplication passed.\r\n");
        Num.print("------------------------------\r\n");
      
        QUO = Num.div(L[i], L[j]).Round_floor();
        REM = Num.sub(L[i], Num.mul(QUO.toString(), L[j]).toString());
        if (Num.div_check(L[i], L[j], REM.toString())) {
          Num.print(L[i], " % "); Num.print(L[j], " = ");
          QUO.Print("\r\n");
          Num.print("\r\nFAILURE - THIS SYSTEM DOES NOT SUPPORT ARBITRARY PRECISION ARITHMETIC (div)!\r\n");
          System.exit(1);
        }
        else Num.print("division passed.\r\n");
        Num.print("------------------------------\r\n");
        
        Num.print(L[i], " / "); Num.print(L[j], " = ");
        Num.div(L[i], L[j]).Print("\r\n");
        Num.print("------------------------------\r\n");

        Num.print(L[j], " inv "); Num.print(" = ");
        Num.inv(L[j], L[j].length()).Print("\r\n");
        Num.print("------------------------------\r\n");

        Num.print(L[j], " x2 "); Num.print(" = ");
        Num.x2(L[j]).Print("\r\n");
        Num.print("------------------------------\r\n");

        Num.print(L[j], " x3 "); Num.print(" = ");
        Num.x3(L[j]).Print("\r\n");
        Num.print("------------------------------\r\n");

        Num.print(L[j], " ^ 8.0"); Num.print(" = ");
        Num.xy(L[j], "8.0").Print("\r\n");
        Num.print("------------------------------\r\n");
        Num.print("------------------------------\r\n");

      }            
    }
    Num.print("\r\nSUCCESS - THIS SYSTEM DOES SUPPORT ARBITRARY PRECISION ARITHMETIC (OK).\r\n");
  } //END METHOD

     /** andb, BITWISE OPERATOR BY Num */
    /**  CODE: Num a = new Num("255.0"); Num b = new Num("1.0"); Num.print(Num.andb(a, b), "\r\n"); //1.0 */
    public static Num andb(Num a, Num b) { return a.Andb(b); } 
  
     /** andb, BITWISE OPERATOR BY String */
    /**  CODE: String a = "255.0"; String b = "1.0"; Num.print(Num.andb(a, b), "\r\n"); //1.0 */
    public static Num andb(String a, String b) { return new Num(a).Andb(b); } 
    
     /** andb, BITWISE OPERATOR BY int */
    /**  CODE: Num.print(Num.andb(255, 1), "\r\n"); //1.0 */
    public static Num andb(int a, int b) { return new Num(a).Andb(b); } 
    
     /** andb, BITWISE OPERATOR BY long */
    /**  CODE: Num.print(Num.andb(255L, 1L), "\r\n"); //1.0 */
    public static Num andb(long a, long b) { return new Num(a).Andb(b); } 
    
     /** andb, BITWISE OPERATOR BY BigInteger */
    /**  CODE: Num.print(Num.andb(new BigInteger("255"), new BigInteger("1")), "\r\n"); //1.0 */
    public static Num andb(BigInteger a, BigInteger b) { return new Num(a).Andb(b); } 
    
     /** orb, BITWISE OPERATOR BY Num, Num */
    /**  CODE: Num a = new Num("0.0"); Num b = new Num("255.0"); Num.print(Num.orb(a, b), "\r\n"); //255.0 */
    public static Num orb(Num a, Num b) { return a.Orb(b); } 

     /** orb, BITWISE OPERATOR BY String, String */
    /**  CODE: Num.print(Num.orb("0.0", "255.0"), "\r\n"); //255.0 */
    public static Num orb(String a, String b) { return new Num(a).Orb(b); } 
    
     /** orb, BITWISE OPERATOR BY int, int */
    /**  CODE: Num.print(Num.orb(0, 255), "\r\n"); //255.0 */
    public static Num orb(int a, int b) { return new Num(a).Orb(b); } 
    
     /** orb, BITWISE OPERATOR BY long, long */
    /**  CODE: Num.print(Num.orb(0L, 255L), "\r\n"); //255.0 */
    public static Num orb(long a, long b) { return new Num(a).Orb(b); } 
    
     /** orb, BITWISE OPERATOR BY BigInteger, BigInteger */
    /**  CODE: Num.print(Num.orb(new BigInteger("0"), new BigInteger("255")), "\r\n"); //255.0 */
    public static Num orb(BigInteger a, BigInteger b) { return new Num(a).Orb(b); } 
    
     /** xorb BITWISE OPERATOR */
    /**  CODE: Num a = new Num("255.0"); Num b = new Num("255.0"); Num.print(Num.xorb(a, b), "\r\n"); //0.0 */
    public static Num xorb(Num a, Num b) { return a.Xorb(b); } 

     /** (~) notb, NOT UNARY BITWISE OPERATOR BY Num */
    /**  CODE: */
       /** Num op1 = new Num("10.0"); */
      /** Num.print(String.format("%0" + 4 + "d", 0) + op1.toBigInt().toString(2), " => " + op1 + "\r\n"); //00001010 => 10.0 */
     /** Num op2 = Num.notb(op1); */
    /** Num.print(String.format("%0" + 5 + "d", 0) + op2.toBigInt().toString(2), " => " + op2 + "\r\n"); //00000101 => 5.0 */
    public static Num notb(Num a) { return a.Notb(); } 

     /** (~) notb, NOT UNARY BITWISE OPERATOR BY String */
    /**  CODE: */
       /** String op1 = new String("10.0"); */
      /**  Num.print(String.format("%0" + 4 + "d", 0) + new Num(op1).toBin(), " => " + op1 + "\r\n"); //00001010 => 10 */ 
     /**   Num op2 = Num.notb(op1); */
    /**    Num.print(String.format("%0" + 5 + "d", 0) + op2.toBin(), " => " + op2 + "\r\n");        //00000101 => 5.0 */
    public static Num notb(String a) { return new Num(a).Notb(); } 
    
     /** (~) notb, NOT UNARY BITWISE OPERATOR BY int */
    /**  CODE: */
       /** int op1 = 10; */
      /** Num.print(String.format("%0" + 4 + "d", 0) + new Num(op1).toBin(), " => " + op1 + "\r\n"); //00001010 => 10 */
     /** Num op2 = Num.notb(op1); */
    /** Num.print(String.format("%0" + 5 + "d", 0) + op2.toBin(), " => " + op2 + "\r\n");          //00000101 => 5.0 */
    public static Num notb(int a) { return new Num(a).Notb(); } 
    
     /** (~) notb, NOT UNARY BITWISE OPERATOR BY long */
    /**  CODE: */
       /** long op1 = 10L; */
      /** Num.print(String.format("%0" + 4 + "d", 0) + new Num(op1).toBin(), " => " + op1 + "\r\n"); //00001010 => 10 */
     /** Num op2 = Num.notb(op1); */
    /** Num.print(String.format("%0" + 5 + "d", 0) + op2.toBin(), " => " + op2 + "\r\n");          //00000101 => 5.0 */
    public static Num notb(long a) { return new Num(a).Notb(); } 
    
     /** (~) notb, NOT UNARY BITWISE OPERATOR BY BigInteger */
    /**  CODE: */
       /** BigInteger op1 = new BigInteger("10"); */
      /** Num.print(String.format("%0" + 4 + "d", 0) + new Num(op1).toBin(), " => " + op1 + "\r\n"); //00001010 => 10 */
     /** Num op2 = Num.notb(op1); */
    /** Num.print(String.format("%0" + 5 + "d", 0) + op2.toBin(), " => " + op2 + "\r\n");          //00000101 => 5.0 */
    public static Num notb(BigInteger a) { return new Num(a).Notb(); } 
    
     /** gcd, GREATEST COMMON DIVISOR BETWEEN TWO INTEGERs BY Num */
    /**  CODE: Num a = new Num(12); Num b = new Num(8); Num.gcd(a, b).Print("\r\n"); //4.0  */
    public static Num gcd(Num a, Num sob) { return a.GCD(sob); }

     /** gcd, GREATEST COMMON DIVISOR BETWEEN TWO INTEGERs BY String */
    /**  CODE: Num.gcd("12.0", "8.0").Print("\r\n"); //4.0  */
    public static Num gcd(String a, String sob) { return new Num(a).GCD(sob); }
    
     /** gcd, GREATEST COMMON DIVISOR BETWEEN TWO INTEGERs BY int */
    /**  CODE: Num.gcd(12, 8).Print("\r\n"); //4.0  */
    public static Num gcd(int a, int sob) { return new Num(a).GCD(sob); }
    
     /** gcd, GREATEST COMMON DIVISOR BETWEEN TWO INTEGERs BY long */
    /**  CODE: Num.gcd(12L, 8L).Print("\r\n"); //4.0  */
    public static Num gcd(long a, long sob) { return new Num(a).GCD(sob); }
    
     /** gcd, GREATEST COMMON DIVISOR BETWEEN TWO INTEGERs BY BigInteger */
    /**  CODE: Num.gcd(new BigInteger("12"), new BigInteger("8")).Print("\r\n"); //4.0  */
    public static Num gcd(BigInteger a, BigInteger sob) { return new Num(a).GCD(sob); }
    
     /** is_probablePrime, CHECK FOR Num IS PROBABLY PRIME, OR IF IT'S DEFINITELY COMPOSITE. (DEFAULT CERTAINTY = 100: VERY SMALL CHANCE OF ERROR) */
    /**  CODE: Num a = new Num(13); Num.print(Num.is_probablePrime(a), "\r\n"); //true */
    public static boolean is_probablePrime(Num a) { return a.Is_probablePrime(); }

      /** is_probablePrime, CHECK FOR Num IS PROBABLY PRIME, OR IF IT'S DEFINITELY COMPOSITE. ( 0 <= CERTAINTY <= 100) */
     /** CERTAINTY = 1: CHANCE OF ERROR = 1/2, CERTAINTY = 10: CHANCE OF ERROR = 1/1024, CERTAINTY = 100: VERY SMALL CHANCE OF ERROR */
    /** CODE: Num a = new Num(13); Num.print(Num.is_probablePrime(a, 100), "\r\n"); //true */
    public static boolean is_probablePrime(Num a, int certainty) { return a.Is_probablePrime(certainty); }

     /** prime_gen, GENERATE PRIME NUMBER OF BITS SIZE */
    /** CODE: int bit = 8; Num.print(Num.prime_gen(bit), "\r\n"); //... */
    public static Num prime_gen(int bitSize) { return new Num(BigInteger.probablePrime(bitSize, new java.util.Random())); }

     /** PrimeNext, GENERATE NEXT PROBABLE PRIME NUMBER BY PRIME */
    /**  CODE: int bit = 8; Num a = Num.prime_gen(bit); Num.print(a, "\r\n"); Num.print(Num.primeNext(a), "\r\n"); //...149.0 ...151.0 */
    public static Num primeNext(Num p) { return new Num(p.toBigInteger().nextProbablePrime()); }
   
     /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////// OBJECT METHODs /////////////////////////////////////////////////////////
   /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /** equals, CHECK FOR SELF-COMPARISON, CHECK IF OBJ IS NULL OR NOT THE SAME CLASS, TYPE CAST AND COMPARE FIELDs */
    @Override
    public boolean equals(Object obj) {
        // STEP 1: CHECK FOR SELF-COMPARISON
        if (this == obj) return true;

        // STEP 2: CHECK IF OBJ IS NULL OR NOT THE SAME CLASS
        if (obj == null || getClass() != obj.getClass()) return false;

        // STEP 3: TYPE CAST AND COMPARE FIELDs
        Num other = (Num) obj;
        return this.n.equals(other.n);
    }

    /** compareTo, BY COLLECTION FRAMEWORK (ArrayList, LinkedList, HashMap, LinkedHashMap, TreeMap) */
    @Override
    public int compareTo(Num other) {
        if      (this.GT(other)) return 1;
        else if (this.EQ(other)) return 0;
        return -1;
    }

    /** hashCode, OPTIONALLY, ALSO OVERRIDE HASHCODE IF YOU OVERRIDE EQUALS */
    @Override
    public int hashCode() { return Objects.hash(n, n0, n1, n2, L_n0, L_n1, d); } //import java.util.Objects;


    /** GETTER METHODs */
    public String getValue()  { return this.n;    }        //COMPLETED STRING NUMBER
    public String get_n()     { return this.n;    }       //COMPLETED STRING NUMBER
    public String get_n0()    { return this.n0;   }      //STRING NUMBER INTEGER PART
    public String get_n1()    { return this.n1;   }     //STRING NUMBER FRACTIONAL PART
    public String get_n2()    { return this.n2;   }    //STRING NUMBER SIGN PART
    public int    get_L_n0()  { return this.L_n0; }   //STRING NUMBER INTEGER PART LENGTH
    public int    get_L_n1()  { return this.L_n1; }  //STRING NUMBER FRACTIONAL PART LENGTH
    public int    get_d()     { return this.d;    } //DECIMAL BY DIVISION
    
    /** Address, RETURN Num ADDRESS */
    public int Address() { return hashCode(); } 

    /** Class, Num CLASS TYPE */
    public String Class() { return Num.class.toString(); } //return "class num7.Num";
  
     /** Num2exp, CONVERT A NUM OBJECT TO SCIENTIFIC NOTATION STRING */
    /**  CODE: Num a = new Num("123.006789"); String S = a.Num2exp(); Num.print(S + "\r\n"); //1.23006789e2 */
    public String Num2exp() { return Num.num2exp(this); }
    
     /** toEXP, CONVERT A NUM OBJECT TO SCIENTIFIC NOTATION STRING LIKE Num2exp() */
    /**  CODE: Num a = new Num("123.006789"); String S = a.toEXP(); Num.print(S + "\r\n"); //1.23006789e2 */
    public String toEXP() { return Num.num2exp(this); } 

     /** CopyFrom, MAKE COPY FROM OTHER Num */
    /**  CODE: Num a = new Num(3); Num b = new Num("-5.14"); Num.print(a.CopyFrom(b), "\r\n"); b.Print("\r\n"); //-5.14 -5.14 */
    public Num CopyFrom(Num other) {
        this.n = other.n;
        this.n0 = other.n0;
        this.n1 = other.n1;
        this.n2 = other.n2;
        this.L_n0 = other.L_n0;
        this.L_n1 = other.L_n1;
        this.d = other.d;
        return this;
    }

     /** CopyTo, MAKE COPY TO OTHER Num */
    /**  CODE: Num a = new Num(3); Num b = new Num("-5.14"); Num.print(a.CopyTo(b), "\r\n"); b.Print("\r\n"); //3.0 3.0 */
    public Num CopyTo(Num other) {
        other.n = this.n;
        other.n0 = this.n0;
        other.n1 = this.n1;
        other.n2 = this.n2;
        other.L_n0 = this.L_n0;
        other.L_n1 = this.L_n1;
        other.d = this.d;
        return other;
    }
    
     /** CopySignFrom, COPY SIGN FROM OTHER Num */
    /**  CODE: Num a = new Num(+3); Num b = new Num("-5.14"); Num.print(a.CopySignFrom(b), "\r\n"); b.Print("\r\n"); //-3.0 -5.14 */
    public Num CopySignFrom(Num other) {
        if (this.n2.equals(other.n2)) return this;
        this.n2 = other.n2; //SIGN
        this.n = other.n2 + this.n0 + "." + this.n1;
        return this;
    }

     /** CopySignTo, COPY SIGN TO OTHER Num */
    /**  CODE: Num a = new Num(+3); Num b = new Num("-5.14"); Num.print(a.CopySignTo(b), "\r\n"); a.Print("\r\n"); //5.14 3.0 */
    public Num CopySignTo(Num other) {
        if (this.n2.equals(other.n2)) return other;
        other.n2 = this.n2; //SIGN
        other.n = this.n2 + other.n0 + "." + other.n1;
        return other;
    }

     /** Print, PRINT (Num VIDEO OUTPUT) */
    /**  CODE: Num a = new Num("-5005.77"); a.Print(); //-5005.77 */
    public void Print() { System.out.print(this.toString()); }

     /** Print, PRINT (Num VIDEO OUTPUT BY String) */
    /**  CODE:  Num a = new Num("-5005.77"); a.Print("\r\n"); //-5005.77 (RETURN) */
    public void Print(String str) { System.out.print(this.toString() + str); }

     /** Show, PRINT OBJECT PROPERTIES (Num VIDEO OUTPUT) */
    /**  CODE:  Num a = new Num("-5005.77"); a.Show(); //n: -5005.77 n0: 5005 n1: 77 n2: - L_n0: 4 L_n1: 2 d: 80 */
    public void Show() {
        System.out.println("n: " + this.n);
        System.out.println("n0: " + this.n0);
        System.out.println("n1: " + this.n1);
        System.out.println("n2: " + this.n2);
        System.out.println("L_n0: " + this.L_n0);
        System.out.println("L_n1: " + this.L_n1);
        System.out.println("d: " + this.d);
    }

     /** toString, Num DISPLAY BY System.out.println */
    /**  CODE: Num a = new Num("3.0"); System.out.println(a); //3.0 */
    @Override
    public String toString() { return this.n; }

     /** valueOf like toString, Num DISPLAY BY System.out.println */
    /**  CODE: Num a = new Num("3.0"); System.out.println(a.valueOf()); //3.0 */
    public String valueOf() { return this.n; }

     /** size, Num DIGIT CHARACTER SIZE (EXTIMATED RAM) */
    /**  CODE: Num a = new Num("-3.141592654"); Num.print(a.size(), "\n"); //39 */
    public int size() { return this.n.length() + this.L_n0 + this.L_n1 + this.n2.length() + 16; }

    /** length, Num.n PROPERTY STRING LENGTH */
    /**  CODE: Num a = new Num("-3.141592654"); Num.print(a.length(), "\n"); //39 */
    public int length() { return this.n.length(); }
    
   /** Len, RETURN AN ARRAY WITH NUM LENGTHS BEFORE AND AFTER FLOATING POINT DOT */
  /**  CODE: Num a = new Num("3.1415"); a.Print("\r\n") ; Num.print(a.Len()[0] + " " + a.Len()[1] + "\r\n"); //3.1415 1 4 */
  public int[] Len() { 
    int[] numbers = new int[2]; //TWO ELEMENTs
    numbers[0] = this.n0.length();
    numbers[1] = this.n1.length() == 1 && this.n1.equals("0") ? 0 : this.n1.length();
    return numbers; //int ARRAY
  } 

     /** toPrecision, PRECISION SET Num d PROPERTY (DIVISION OPERATION) */
    /**  CODE: Num a = new Num("3.14"); Num b = new Num(3); int digits = 6; a.toPrecision(digits); b.toPrecision(digits); Num.print(a.Div(b, digits).toString(), "\r\n"); //1.046666 */
    public Num toPrecision(int d) { this.d = d; return this; }

     /** Invsign, INVERTED SIGN OF this Num */
    /**  CODE: Num a = new Num("+3.14"); a.Print("\r\n"); a.Invsign().Print("\r\n"); //3.14 -3.14 */
    public Num Invsign() {
        this.n2 = (this.n2.equals("") ? "-" : "");
        this.n = this.n2 + this.n0 + "." + this.n1;
        return this;
    }

     /** Minus_unary, INVERTED SIGN OF this Num */
    /**  CODE: Num a = new Num("+3.14"); a.Print("\r\n"); a.Minus_unary().Print("\r\n"); //3.14 -3.14 */
    public Num Minus_unary() { this.Invsign(); return this; }

     /** Plus, PLUS SIGN OF this Num */
    /**  CODE: Num a = new Num("-3.14"); a.Print("\r\n"); a.Plus().Print("\r\n"); //-3.14 3.14 */
    public Num Plus() {
        if (this.n2.equals(""))
            return this;
        this.n2 = ""; //SET PLUS SIGN (+)
        this.n = this.n2 + this.n0 + "." + this.n1;
        return this;
    }

     /** Minus, MINUS SIGN OF Num */
    /**  CODE: Num a = new Num("+3.14"); a.Print("\r\n"); a.Minus().Print("\r\n"); //3.14 -3.14 */
    public Num Minus() {
        if((this.n0 + this.n1).equals("00")) throw new ArithmeticException("Num.Minus => zero can not be signed: " + this.n); //SIGNED ZERO ERROR
        if(this.n2.equals("-")) return this;
        this.n2 = "-"; //SET MINUS SIGN (-)
        this.n = this.n2 + this.n0 + "." + this.n1;
        return this;
    }

     /** toBigInt, NTEGER Num TO BigInt */
    /**  CODE: Num a = new Num("1000000000000000000000.0"); Num.print(a.toBigInt().subtract(new BigInteger("1")), "\r\n"); //999999999999999999999 */
    public BigInteger toBigInt() { //this.n1 != '0'
        if (!this.n1.equals("0"))
            throw new ArithmeticException("Num.toBigInt => TypeError, number must be integer: " + this.n);
        return new BigInteger(this.n2 + this.n0);
    }
    
     /** toBigInteger, INTEGER Num TO BigInteger LIKE toBigInt */
    /**  CODE: Num a = new Num("1000000000000000000000.0"); Num.print(a.toBigInteger().subtract(new BigInteger("1")), "\r\n"); //999999999999999999999 */
    public BigInteger toBigInteger() { //this.n1 != '0'
        if (!this.n1.equals("0"))
            throw new ArithmeticException("Num.toBigInteger => TypeError, number must be integer: " + this.n);
        return new BigInteger(this.n2 + this.n0);
    }
    
     /** toBin, INTEGER Num TO BINARY */
    /**  CODE:   Num a = new Num("3141592.0"); Num.print(a.toBin(), "\r\n"); //1011111110111111011000 */
    public String toBin() {
        if (!this.n1.equals("0"))
            throw new ArithmeticException("Num.toBin => TypeError, number must be integer: " + this.n);
        return new BigInteger(this.n2 + this.n0).toString(2);
    }

     /** toHex, INTEGER Num TO HEXADECIMAL */
    /**  CODE:   Num a = new Num("3141592.0"); Num.print(a.toHex().toUpperCase(), "\r\n"); //2FEFD8 */
    public String toHex() {
        if (!this.n1.equals("0"))
            throw new ArithmeticException("Num.toHex => TypeError, number must be integer: " + this.n);
        return new BigInteger(this.n2 + this.n0).toString(16);
    }

     /** toInt, INTEGER Num TO int */
    /**  CODE: Num a = new Num("3141592.0"); Num.print(a.toInt() - 1, "\r\n"); //3141591 */
    public int toInt() {
        if (!this.n1.equals("0"))
            throw new ArithmeticException("Num.toInt => TypeError, number must be integer: " + this.n);
        return Integer.parseInt(this.n2 + this.n0);
    }

     /** toLong, INTEGER Num TO long */
    /**  CODE: Num a = new Num("31415923141592.0"); Num.print(a.toLong() - 1, "\r\n"); //31415923141591 */
    public long toLong() {
        if (!this.n1.equals("0"))
            throw new ArithmeticException("Num.toLong => TypeError number must be long integer: " + this.n);
        return Long.parseLong(this.n2 + this.n0);
    }

     /** toFloat, Num TO float */
    /**  Num a = new Num("-3.1234567890123456789999"); Num.print(a.toFloat(), "\r\n"); Num.print(a.toFloat() - 2.0e-15, "\r\n"); //-3.1234567890123457 -3.123456789012348 */
    public double toFloat() { return Double.parseDouble(this.n); }

     /** toDouble, Num TO double LIKE toFloat */
    /**  Num a = new Num("-3.1234567890123456789999"); Num.print(a.toDouble(), "\r\n"); Num.print(a.toDouble() - 2.0e-15, "\r\n"); //-3.1234567890123457 -3.123456789012348 */
    public double toDouble() { return Double.parseDouble(this.n); }

     /** ieee754, FLOAT TO IEEE754 CONVERSION LIKE toFloat() METHOD */
    /**  CODE: Num a = new Num("-3.1234567890123456789999"); Num.print(a.ieee754(), "\r\n"); Num.print(a.ieee754() - 2.0e-15, "\r\n"); //-3.1234567890123457 -3.123456789012348 */
    public double ieee754() { return Double.parseDouble(this.n); }

     /** Shift, SHIFT Num BY int (MULTIPLY AND DIVIDE BY TEN) */
    /**  CODE: Num a = new Num(1); a = a.Shift(3);  a.Print("\r\n"); //1000.0 */
    public Num Shift(int zeros) { return Num.shift(this, zeros); } 

     /** Shift, SHIFT Num BY long (MULTIPLY AND DIVIDE BY TEN) */
    /**  CODE: Num a = new Num(1); a = a.Shift(-3L); a.Print("\r\n"); //0.001 */
    public Num Shift(long zeros) { return Num.shift(this, (int) zeros); } 

     /** Shift, SHIFT Num BY BigInteger (MULTIPLY AND DIVIDE BY TEN) */
    /**  CODE: Num a = new Num(1); a = a.Shift(new BigInteger("-3")); a.Print("\r\n"); //0.001 */
    public Num Shift(BigInteger zeros) { return Num.shift(this, zeros.intValueExact()); } 

     /** Shift, SHIFT Num BY Num (MULTIPLY AND DIVIDE BY TEN) */
    /**  CODE: Num a = new Num(1); a = a.Shift(new Num("-3.0")); a.Print("\r\n"); //0.001 */
    public Num Shift(Num zeros) { return Num.shift(this, zeros.toInt()); } 

     /** Shift, SHIFT Num BY String (MULTIPLY AND DIVIDE BY TEN) */
    /**  CODE: Num a = new Num(1); a = a.Shift("-3.0"); a.Print("\r\n"); //0.001 */
    public Num Shift(String zeros) { return Num.shift(this, new Num(zeros).toInt()); } 

     /** (==) EQ, EQUAL LOGIC BINARY OPERATOR BY int */
    /**  CODE: Num a = new Num(3); Num.print(a.EQ(3)); //true */
    public boolean EQ(int sob) { return EQ(new Num(sob)); }

     /** (==) EQ, EQUAL LOGIC BINARY OPERATOR BY long*/
    /**  CODE: Num a = new Num(3); Num.print(a.EQ(3L)); //true */
    public boolean EQ(long sob) { return EQ(new Num(sob)); }
    
     /** (==) EQ, EQUAL LOGIC BINARY OPERATOR BY BigInteger*/
    /**  CODE: Num a = new Num(3); Num.print(a.EQ(new BigInteger("3"))); //true */
    public boolean EQ(BigInteger sob) { return EQ(new Num(sob)); }

     /** (==) EQ, EQUAL LOGIC BINARY OPERATOR BY String */
    /**  CODE: Num a = new Num(3); Num.print(a.EQ("3.0")); //true */
    public boolean EQ(String sob) { return EQ(new Num(sob)); }

     /** (==) EQ, EQUAL LOGIC BINARY OPERATOR BY Num */
    /**  CODE: Num a = new Num(3); Num.print(a.EQ(new Num("3.0"))); //true */
    public boolean EQ(Num sob) { return this.n.equals(sob.n); }

     /** (!=) NE, NOT EQUAL LOGIC BINARY OPERATOR BY int */
    /**  CODE: Num a = new Num(3); Num.print(a.NE(3)); //false */
    public boolean NE(int sob) { return NE(new Num(sob)); }

    /** (!=) NE, NOT EQUAL LOGIC BINARY OPERATOR BY long */
    /** CODE: Num a = new Num(3); Num.print(a.NE(3L)); //false */
    public boolean NE(long sob) { return NE(new Num(sob)); }
    
     /** (!=) NE, NOT EQUAL LOGIC BINARY OPERATOR BY BigInteger */
    /**  CODE: Num a = new Num(3); Num.print(a.NE(new BigInteger("3"))); //false */
    public boolean NE(BigInteger sob) { return NE(new Num(sob)); }

     /** (!=) NE, NOT EQUAL LOGIC BINARY OPERATOR BY String */
    /**  CODE: Num a = new Num(3); Num.print(a.NE("3.0")); //false */
    public boolean NE(String sob) { return NE(new Num(sob)); }

     /** (!=) NE, NOT EQUAL LOGIC BINARY OPERATOR BY Num */
    /**  CODE: Num a = new Num(3); Num.print(a.NE(new Num("3.0"))); //false */
    public boolean NE(Num sob) { return !this.n.equals(sob.n); }

     /** (>) GT, GREATER LOGIC BINARY OPERATOR BY int */
    /**  CODE: Num a = new Num(333); Num.print(a.GT(33), "\r\n"); //true */
    public boolean GT(int sob) { return GT(new Num(sob)); }

     /** (>) GT, GREATER LOGIC BINARY OPERATOR BY long */
    /**  CODE: Num a = new Num(333); Num.print(a.GT(33L), "\r\n"); //true */
    public boolean GT(long sob) { return GT(new Num(sob)); }
    
     /** (>) GT, GREATER LOGIC BINARY OPERATOR BY BigInteger */
    /**  CODE: Num a = new Num(333); Num.print(a.GT(new BigInteger("33")), "\r\n"); //true */
    public boolean GT(BigInteger sob) { return GT(new Num(sob)); }

     /** (>) GT, GREATER LOGIC BINARY OPERATOR BY String */
    /**  CODE: Num a = new Num(333); Num.print(a.GT("33.0"), "\r\n"); //true */
    public boolean GT(String sob) { return GT(new Num(sob)); }

     /** (>) GT, GREATER LOGIC BINARY OPERATOR BY Num */
    /**  CODE: Num a = new Num(333); Num.print(a.GT(new Num("33.0")), "\r\n"); //true */
    public boolean GT(Num sob) {
        if (new BigInteger(this.n2 + this.n0).compareTo(new BigInteger(sob.n2 + sob.n0)) > 0)
            return true;
        if (new BigInteger(this.n2 + this.n0).compareTo(new BigInteger(sob.n2 + sob.n0)) == 0) {
            int d_L1 = this.L_n1 - sob.L_n1;
            if (d_L1 > 0) {
                BigInteger a = new BigInteger(this.n2 + this.n1);
                BigInteger b = new BigInteger(sob.n2 + sob.n1 + String.format("%0" + d_L1 + "d", 0));
                if (a.compareTo(b) > 0)
                    return true;
            } else if (d_L1 < 0) {
                BigInteger a = new BigInteger(this.n2 + this.n1 + String.format("%0" + (-d_L1) + "d", 0));
                BigInteger b = new BigInteger(sob.n2 + sob.n1);
                if (a.compareTo(b) > 0)
                    return true;
            } else
                return new BigInteger(this.n2 + this.n1).compareTo(new BigInteger(sob.n2 + sob.n1)) > 0;
        }
        return false;
    }

     /** (>=) GE, GREATER OR EQUAL LOGIC BINARY OPERATOR BY int */
    /**  CODE: Num a = new Num(333); Num.print(a.GE(333), "\r\n"); //true */
    public boolean GE(int sob) { return GE(new Num(sob)); }

     /** (>=) GE, GREATER OR EQUAL LOGIC BINARY OPERATOR BY long */
    /**  CODE: Num a = new Num(333); Num.print(a.GE(333L), "\r\n"); //true */
    public boolean GE(long sob) { return GE(new Num(sob)); }
    
     /** (>=) GE, GREATER OR EQUAL LOGIC BINARY OPERATOR BY BigInteger */
    /**  CODE: Num a = new Num(333); Num.print(a.GE(new BigInteger("333")), "\r\n"); //true */
    public boolean GE(BigInteger sob) { return GE(new Num(sob)); }

     /** (>=) GE, GREATER OR EQUAL LOGIC BINARY OPERATOR BY String */
    /**  CODE: Num a = new Num(333); Num.print(a.GE("333.0"), "\r\n"); //true */
    public boolean GE(String sob) { return GE(new Num(sob)); }
    
     /** (>=) GE, GREATER OR EQUAL LOGIC BINARY OPERATOR BY Num */
    /**  CODE: Num a = new Num(333); Num.print(a.GE(new Num("333.0")), "\r\n"); //true */
    public boolean GE(Num n) { Num sob = new Num(n); return this.GT(sob) || this.EQ(sob); }

     /** (<) LT, LESS LOGIC BINARY OPERATOR BY int */
    /**  CODE: Num a = new Num(33); Num.print(a.LT(333)); //true */
    public boolean LT(int sob) { return LT(new Num(sob)); }

     /** (<) LT, LESS LOGIC BINARY OPERATOR BY long */
    /**  CODE: Num a = new Num(33); Num.print(a.LT(333L)); //true */
    public boolean LT(long sob) { return LT(new Num(sob)); }

     /** (<) LT, LESS LOGIC BINARY OPERATOR BY BigInteger */
    /**  CODE: Num a = new Num(33); Num.print(a.LT(new BigInteger("333"))); //true */
    public boolean LT(BigInteger sob) { return LT(new Num(sob)); }

     /** (<) LT, LESS LOGIC BINARY OPERATOR BY String */
    /**  CODE: Num a = new Num(33); Num.print(a.LT("333.0")); //true */
    public boolean LT(String sob) { return LT(new Num(sob)); }
    
     /** (<) LT, LESS LOGIC BINARY OPERATOR BY Num */
    /**  CODE: Num a = new Num(33); Num.print(a.LT(new Num("333.0"))); //true */
    public boolean LT(Num n) { Num sob = new Num(n); return !this.GE(sob); }

     /** (<=) LE, LESS OR EQUAL LOGIC BINARY OPERATOR BY int */
    /**  CODE: Num a = new Num(333); Num.print(a.LE(333)); //true */
    public boolean LE(int sob) { return LE(new Num(sob)); }

     /** (<=) LE, LESS OR EQUAL LOGIC BINARY OPERATOR BY long */
    /**  CODE: Num a = new Num(333); Num.print(a.LE(333L)); //true */
    public boolean LE(long sob) { return LE(new Num(sob)); }
    
     /** (<=) LE, LESS OR EQUAL LOGIC BINARY OPERATOR BY BigInteger */
    /**  CODE: Num a = new Num(333); Num.print(a.LE(new BigInteger("333"))); //true */
    public boolean LE(BigInteger sob) { return LE(new Num(sob)); }

     /** (<=) LE, LESS OR EQUAL LOGIC BINARY OPERATOR BY String */
    /**  CODE: Num a = new Num(333); Num.print(a.LE("333.0")); //true */
    public boolean LE(String sob) { return LE(new Num(sob)); }

     /** (<=) LE, LESS OR EQUAL LOGIC BINARY OPERATOR BY Num */
    /**  CODE: Num a = new Num(333); Num.print(a.LE(new Num("333.0"))); //true */
    public boolean LE(Num n) { Num sob = new Num(n); return !this.GT(sob); }

     /** Is_true, TRUE LOGIC UNARY BY Num */
    /**  CODE: Num a = new Num("-3.14"); if(a.Is_true()) Num.print(a.toString(), " true"); else Num.print(a.toString(), " false"); //-3.14 true */
    public boolean Is_true() { return !(this.n.equals("0.0")); }

     /** Is_false, FALSE LOGIC UNARY BY Num */
    /**  CODE: Num a = new Num("0.00"); if (a.Is_false()) Num.print(a.toString(), " true"); else Num.print(a.toString(), " false"); //0.0 true */
    public boolean Is_false() { return this.n.equals("0.0"); }

     /**  And, AND LOGIC BINARY OPERATOR BY Num */
    /**   CODE: Num a = new Num("0.001"); Num b = new Num("0.02"); if(a.And(b) == true) Num.print(a.toString() + " And " + b.toString(), " => true\r\n"); else Num.print(a.toString() + " And " + b.toString(), " => false\r\n"); //0.001 And 0.02 => true */
    public boolean And(Num b) { return this.Is_true() && b.Is_true(); }

     /**  Or, OR LOGIC BINARY OPERATOR BY Num */
    /**   CODE: Num a = new Num("0.0"); Num b = new Num("0.02"); if(a.Or(b) == true) Num.print(a.toString() + " Or " + b.toString(), " => true\r\n"); else Num.print(a.toString() + " Or " + b.toString(), " => false\r\n"); //0.0 Or 0.02 => true */
    public boolean Or(Num b) { return this.Is_true() || b.Is_true(); }

     /** Not, NOT LOGIC UNARY OPERATOR BY Num */
    /**  CODE: Num a = new Num("0.0"); if(a.Not()) Num.print(a.toString(), " true"); else Num.print(a.toString(), " false"); //0.0 true */
    public boolean Not() { return this.n.equals("0.0"); }

     /** Abs, RETURN THE ABSOLUTE VALUE OF A Num */
    /**  CODE: Num a = new Num(-333); Num.print(a.Abs().toString()); //333.0 */
    public Num Abs() { return new Num(this.n2.equals("") ? this.n : this.n.substring(1)); }

     /** Is_negative, CHECK NEGATIVE Num  */
    /**  CODE: Num a = new Num(-333); Num.print(a.Is_negative()); //true */
    public boolean Is_negative() { return this.n2.equals("-"); }

     /** Is_positive, CHECK POSITIVE Num  */
    /**  CODE: Num a = new Num(-333); Num.print(a.Is_positive()); //false */
    public boolean Is_positive() { return this.n2.equals(""); } 

     /** Is_zero, CHECK Num ZERO  */
    /**  CODE: Num a = new Num(0); Num.print(a.Is_zero()); //true */
    public boolean Is_zero() { return this.n.equals("0.0"); }

     /** Is_numint, CHECKS INTEGER Num */
    /**  CODE: Num a = new Num("3.00"); Num.print(a.Is_numint()); //true */
    public boolean Is_numint() { return this.n1.equals("0"); }

     /** Is_numfloat, CHECKS Num FLOATING POINT */
    /**  CODE: Num a = new Num("3.14"); Num.print(a.Is_numfloat()); //true */
    public boolean Is_numfloat() { return !this.n1.equals("0"); }

     /** Clear, CLEAR VARIABLE SETTING ZERO */
    /**  CODE: Num a = new Num("2.72"); a.Print("\r\n"); a.Clear(); a.Print("\r\n"); //2.72 0.0 */
    public void Clear() { this.n = "0.0"; this.n0 = "0"; this.n1 = "0"; this.n2 = ""; this.L_n0 = 1; this.L_n1 = 1; }

     /** Is_numeven, CHECK Num EVEN (INTEGER ENDING 0 2 4 6 8) */
    /**  CODE: Num a = new Num("8.00"); Num.print(a.Is_numeven(), "\r\n"); //true */
    public boolean Is_numeven() {
        if (this.Is_numint()) { return this.Mod(2).Is_false(); }
        throw new IllegalArgumentException("Num.Is_numeven => Num, must be integer value: " + this.toString());
    }
    
   /** Is_numodd, CHECK Num ODD (INTEGER ENDING 1 3 5 7 9) */
  /**  CODE: Num a = new Num("3.00"); Num.print(a.Is_numodd(), "\r\n"); //true */
  public boolean Is_numodd() { return !this.Is_numeven(); }
  
   /** Inc, INCREMENT ADDING VARIABLE BY DEFAULT ONE -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num("2.72"); a.Print(" "); a.Inc().Print("\r\n"); //2.72 3.72 */
  public Num Inc() { 
    Num telf = this.Add(1);
    this.d = telf.d; this.n = telf.n; this.n0 = telf.n0; this.n1 = telf.n1; this.n2 = telf.n2; 
    this.L_n0 = telf.L_n0; this.L_n1 = telf.L_n1;
    return this;
  }

   /** Inc, INCREMENT ADDING VARIABLE BY int -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num("2.72"); a.Print(" "); a.Inc(2).Print("\r\n"); //2.72 4.72 */
  public Num Inc(int sob) {
    Num telf = this.Add(sob);
    this.d = telf.d; this.n = telf.n; this.n0 = telf.n0; this.n1 = telf.n1; this.n2 = telf.n2;
    this.L_n0 = telf.L_n0; this.L_n1 = telf.L_n1;
    return this;
  }

   /** Inc, INCREMENT ADDING VARIABLE BY long -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num("2.72"); a.Print(" "); a.Inc(2000L).Print("\r\n"); //2.72 2002.72 */
  public Num Inc(long sob) {
    Num telf = this.Add(sob); 
    this.d = telf.d; this.n = telf.n; this.n0 = telf.n0; this.n1 = telf.n1; this.n2 = telf.n2;
    this.L_n0 = telf.L_n0; this.L_n1 = telf.L_n1;
    return this;
  }

   /** Inc, INCREMENT ADDING VARIABLE BY BigInteger -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num("2.72"); a.Print(" "); a.Inc(new BigInteger("2000000000000")).Print("\r\n"); //2.72 2000000000002.72 */
  public Num Inc(BigInteger sob) {
    Num telf = this.Add(sob);
    this.d = telf.d; this.n = telf.n; this.n0 = telf.n0; this.n1 = telf.n1; this.n2 = telf.n2;
    this.L_n0 = telf.L_n0; this.L_n1 = telf.L_n1;
    return this;
  }

   /** Inc, INCREMENT ADDING VARIABLE BY String -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num("2.72"); a.Print(" "); a.Inc("2000.0").Print("\r\n"); //2.72 2002.72 */
  public Num Inc(String sob) {
    Num telf = this.Add(sob);
    this.d = telf.d; this.n = telf.n; this.n0 = telf.n0; this.n1 = telf.n1; this.n2 = telf.n2;
    this.L_n0 = telf.L_n0; this.L_n1 = telf.L_n1;
    return this;
  }

   /** Inc, INCREMENT ADDING VARIABLE BY Num -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num("2.72"); a.Print(" "); a.Inc(new Num("2000000000000.03")).Print("\r\n"); //2.72 2000000000002.75 */
  public Num Inc(Num sob) {
      Num telf = this.Add(sob);
      this.d = telf.d; this.n = telf.n; this.n0 = telf.n0; this.n1 = telf.n1; this.n2 = telf.n2;
      this.L_n0 = telf.L_n0; this.L_n1 = telf.L_n1;
      return this;
  }
  
   /** IncMul, INCREMENT MULTIPLYING VARIABLE BY DEFAULT TEN -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num("2.72"); a.Print(" "); a.IncMul().Print("\r\n"); //2.72 27.2 */
  public Num IncMul() { 
    Num telf = this.Mul(10);
    this.d    = telf.d; this.n = telf.n; this.n0 = telf.n0; this.n1 = telf.n1; this.n2 = telf.n2;
    this.L_n0 = telf.L_n0; this.L_n1 = telf.L_n1;
    return this;    
  }

   /** IncMul, INCREMENT MULTIPLYING VARIABLE BY int -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num("2.72"); a.Print(" "); a.IncMul(100).Print("\r\n"); //2.72 272.0 */
  public Num IncMul(int sob) {
    Num telf = this.Mul(sob);
    this.d = telf.d; this.n = telf.n; this.n0 = telf.n0; this.n1 = telf.n1; this.n2 = telf.n2;
    this.L_n0 = telf.L_n0; this.L_n1 = telf.L_n1;
    return this;
  }

   /** IncMul, INCREMENT MULTIPLYING VARIABLE BY long -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num("2.72"); a.Print(" "); a.IncMul(1000L).Print("\r\n"); //2.72 2720.0 */
  public Num IncMul(long sob) {
    Num telf = this.Mul(sob);
    this.d = telf.d; this.n = telf.n; this.n0 = telf.n0; this.n1 = telf.n1; this.n2 = telf.n2;
    this.L_n0 = telf.L_n0; this.L_n1 = telf.L_n1;
    return this;     
  }

   /** IncMul, INCREMENT MULTIPLYING VARIABLE BY BigInteger -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num("2.72"); a.Print(" "); a.IncMul(new BigInteger("1000000000000")).Print("\r\n"); //2.72 2720000000000.0 */
  public Num IncMul(BigInteger sob) {
    Num telf = this.Mul(sob);
    this.d = telf.d; this.n = telf.n; this.n0 = telf.n0; this.n1 = telf.n1; this.n2 = telf.n2; 
    this.L_n0 = telf.L_n0; this.L_n1 = telf.L_n1;
    return this;   
  }

   /** IncMul, INCREMENT MULTIPLYING VARIABLE BY String -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num("2.72"); a.Print(" "); a.IncMul("1000.0").Print("\r\n"); //2.72 2720.0 */
  public Num IncMul(String sob) {
    Num telf = this.Mul(sob);
    this.d = telf.d; this.n = telf.n; this.n0 = telf.n0; this.n1 = telf.n1; this.n2 = telf.n2; 
    this.L_n0 = telf.L_n0; this.L_n1 = telf.L_n1;
    return this;   
  }

   /** IncMul, INCREMENT MULTIPLYING VARIABLE BY Num -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num("2.72"); a.Print(" "); a.IncMul(new Num("1000.123")).Print("\r\n"); //2.72 2720.33456 */
  public Num IncMul(Num sob) {
      Num telf = this.Mul(sob);
      this.d = telf.d; this.n = telf.n; this.n0 = telf.n0; this.n1 = telf.n1; this.n2 = telf.n2;
      this.L_n0 = telf.L_n0; this.L_n1 = telf.L_n1;
      return this;
  }
  
   /** Dec, DECREMENT SUBTRACTING VARIABLE BY DEFAULT ONE -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num("2.72"); a.Print(" "); a.Dec().Print("\r\n"); //2.72 1.72 */
  public Num Dec() { 
    Num telf = this.Sub(1);
    this.d = telf.d; this.n = telf.n; this.n0 = telf.n0; this.n1 = telf.n1; this.n2 = telf.n2;
    this.L_n0 = telf.L_n0; this.L_n1 = telf.L_n1;
    return this;
  }
    
   /** Dec, DECREMENT SUBTRACTING VARIABLE BY int -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num("2.72"); a.Print(" "); a.Dec(2).Print("\r\n"); //2.72 0.72 */
  public Num Dec(int sob) {
    Num telf = this.Sub(sob);
    this.d = telf.d; this.n = telf.n; this.n0 = telf.n0; this.n1 = telf.n1; this.n2 = telf.n2;
    this.L_n0 = telf.L_n0; this.L_n1 = telf.L_n1;
    return this;
  }
    
   /** Dec, DECREMENT SUBTRACTING VARIABLE BY long -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num("2.72"); a.Print(" "); a.Dec(2L).Print("\r\n"); //2.72 0.72 */
  public Num Dec(long sob) {
    Num telf = this.Sub(sob);
    this.d = telf.d; this.n = telf.n; this.n0 = telf.n0; this.n1 = telf.n1; this.n2 = telf.n2;
    this.L_n0 = telf.L_n0; this.L_n1 = telf.L_n1;
    return this;
  }
    
   /** Dec, DECREMENT SUBTRACTING VARIABLE BY BigInteger -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num("2000.72"); a.Print(" "); a.Dec(new BigInteger("1000")).Print("\r\n"); //2000.72 1000.72 */
  public Num Dec(BigInteger sob) {
    Num telf = this.Sub(sob);
    this.d = telf.d; this.n = telf.n; this.n0 = telf.n0; this.n1 = telf.n1; this.n2 = telf.n2;
    this.L_n0 = telf.L_n0; this.L_n1 = telf.L_n1;
    return this;
  }
    
   /** Dec, DECREMENT SUBTRACTING VARIABLE BY String -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num("2000.72"); a.Print(" "); a.Dec("1000.0").Print("\r\n"); //2000.72 1000.72 */
  public Num Dec(String sob) {
    Num telf = this.Sub(sob);
    this.d = telf.d; this.n = telf.n; this.n0 = telf.n0; this.n1 = telf.n1; this.n2 = telf.n2;
    this.L_n0 = telf.L_n0; this.L_n1 = telf.L_n1;
    return this;
  }
    
   /** Dec, DECREMENT SUBTRACTING VARIABLE BY Num -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num("2000.72"); a.Print(" "); a.Dec(new Num("1000.0")).Print("\r\n"); //2000.72 1000.72 */
  public Num Dec(Num sob) {
      Num telf = this.Sub(sob);
      this.d = telf.d; this.n = telf.n; this.n0 = telf.n0; this.n1 = telf.n1; this.n2 = telf.n2;
      this.L_n0 = telf.L_n0; this.L_n1 = telf.L_n1;
      return this;
  }
  
   /** DecDiv, DECREMENT VARIABLE DIVIDING BY DEFAULT TEN -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num("272.0"); a.Print(" "); a.DecDiv().Print("\r\n"); //272.0 27.2 */
  public Num DecDiv() { 
    Num telf = this.Div(10);
    this.d = telf.d; this.n = telf.n; this.n0 = telf.n0; this.n1 = telf.n1; this.n2 = telf.n2;
    this.L_n0 = telf.L_n0; this.L_n1 = telf.L_n1;
    return this;   
  }

   /** DecDiv, DECREMENT VARIABLE DIVIDING BY int -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num("272.0"); a.Print(" "); a.DecDiv(100).Print("\r\n"); //272.0 2.72 */
  public Num DecDiv(int sob) {
    Num telf = this.Div(sob);
    this.d = telf.d; this.n = telf.n; this.n0 = telf.n0; this.n1 = telf.n1; this.n2 = telf.n2;
    this.L_n0 = telf.L_n0; this.L_n1 = telf.L_n1;
    return this;   
  }

   /** DecDiv, DECREMENT VARIABLE DIVIDING BY long -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num("272.0"); a.Print(" "); a.DecDiv(100L).Print("\r\n"); //272.0 2.72 */
  public Num DecDiv(long sob) {
    Num telf = this.Div(sob);
    this.d = telf.d; this.n = telf.n; this.n0 = telf.n0; this.n1 = telf.n1; this.n2 = telf.n2;
    this.L_n0 = telf.L_n0; this.L_n1 = telf.L_n1;
    return this;  
  }

   /** DecDiv, DECREMENT VARIABLE DIVIDING BY BigInteger -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num("272.0"); a.Print(" "); a.DecDiv(new BigInteger("100")).Print("\r\n"); //272.0 2.72 */
  public Num DecDiv(BigInteger sob) {
    Num telf = this.Div(sob);
    this.d = telf.d; this.n = telf.n; this.n0 = telf.n0; this.n1 = telf.n1; this.n2 = telf.n2;
    this.L_n0 = telf.L_n0; this.L_n1 = telf.L_n1;
    return this;    
  }

   /** DecDiv, DECREMENT VARIABLE DIVIDING BY String -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num("272.0"); a.Print(" "); a.DecDiv("100.0").Print("\r\n"); //272.0 2.72 */
  public Num DecDiv(String sob) {
    Num telf = this.Div(sob);
    this.d = telf.d; this.n = telf.n; this.n0 = telf.n0; this.n1 = telf.n1; this.n2 = telf.n2;
    this.L_n0 = telf.L_n0; this.L_n1 = telf.L_n1;
    return this;   
  }

   /** DecDiv, DECREMENT VARIABLE DIVIDING BY Num -OBJECT MODIFIED BY this REFERENCE */
  /**  CODE: Num a = new Num("272.0"); a.Print(" "); a.DecDiv(new Num("100.0")).Print("\r\n"); //272.0 2.72 */
  public Num DecDiv(Num sob) {
    Num telf = this.Div(sob);
    this.d = telf.d; this.n = telf.n; this.n0 = telf.n0; this.n1 = telf.n1; this.n2 = telf.n2; 
    this.L_n0 = telf.L_n0; this.L_n1 = telf.L_n1;
    return this;      
  }

   /** Add, (+) OBJECT ADDITION METHOD BY int */
  /**  CODE: Num a = new Num("15.1"); Num.print(a.Add(6)); //21.1 */
  public Num Add(int sob) { return this.Add(new Num(sob)); }

   /** Add, (+) OBJECT ADDITION METHOD BY long */
  /**  CODE: Num a = new Num("15.1"); Num.print(a.Add(6L)); //21.1 */
  public Num Add(long sob) { return this.Add(new Num(sob)); }

   /** Add, (+) OBJECT ADDITION METHOD BY BigInteger */
  /**  CODE: Num a = new Num("15.1"); Num.print(a.Add(new BigInteger("6"))); //21.1 */
  public Num Add(BigInteger sob) { return this.Add(new Num(sob)); }

   /** Add, (+) OBJECT ADDITION METHOD BY String */
  /**  CODE: Num a = new Num("15.1"); Num.print(a.Add("6.2")); //21.3 */
  public Num Add(String sob) { return this.Add(new Num(sob)); }

   /** Add, (+) OBJECT ADDITION METHOD BY Num */
  /**  CODE: Num a = new Num("15.1"); Num.print(a.Add(new Num("6.2"))); //21.3 */
  public Num Add(Num sob) {
    BigInteger x1;
    BigInteger x2;
    int ze;
    if (this.L_n1 < sob.L_n1) {
        x1 = new BigInteger(this.n2 + this.n0 + this.n1 + String.format("%0" + (sob.L_n1 - this.L_n1) + "d", 0));
        x2 = new BigInteger(sob.n2 + sob.n0 + sob.n1);
    } else if (this.L_n1 > sob.L_n1) {
        x1 = new BigInteger(this.n2 + this.n0 + this.n1);
        x2 = new BigInteger(sob.n2 + sob.n0 + sob.n1 + String.format("%0" + (this.L_n1 - sob.L_n1) + "d", 0));
    } else {
        x1 = new BigInteger(this.n2 + this.n0 + this.n1);
        x2 = new BigInteger(sob.n2 + sob.n0 + sob.n1);
    }
    BigInteger x3 = x1.add(x2); 
    if (x3.compareTo(new BigInteger("0")) == 0) return new Num("0.0"); //ZERO RESULT SUM 
    String xt = x3.toString();
    int xt_L = xt.length();
    int xt_D = (sob.L_n1 > this.L_n1 ? sob.L_n1 : this.L_n1);
    if (x3.compareTo(new BigInteger("0")) < 0) { //NEGATIVE SUM
        ze = xt_D - xt_L + 1;
        if (ze >= 0) { //-1 < Negative Add < 0
            String xtr;
            if (ze <= 0)
                xtr = "-0" + "." + xt.substring(1); //-0.1 + (-0.2) = -0.3
            else
                xtr = "-0" + "." + String.format("%0" + ze + "d", 0) + xt.substring(1); //0.01 + (-0.09) = -0.08
            return new Num(xtr);
        } 
    } else {
        ze = xt_D - xt_L;
        if (ze >= 0) { //0 < POSITIVE SUM < 1
            String xtr;
            if (ze <= 0)
                xtr = "0" + "." + xt; //0.1 + 0.2 = 0.3
            else
                xtr = "0" + "." + String.format("%0" + ze + "d", 0) + xt; //0.01 + 0.003 = 0.013
            return new Num(xtr);
        }
    }
    if (xt.charAt(0) == '-')
        return new Num(xt.substring(0, -ze + 1) + "." + xt.substring(-ze + 1)); //-12 + 3 = -9
    return new Num(xt.substring(0, -ze) + "." + xt.substring(-ze)); //15.1 + 6.2 = 21.3
  }

   /** Sub, (-) OBJECT SUBTRACTION METHOD BY int */
  /**  CODE: Num a = new Num("15.1"); Num.print(a.Sub(6)); //9.1 */
  public Num Sub(int sob) { return this.Sub(new Num(sob)); }

   /** Sub, (-) OBJECT SUBTRACTION METHOD BY long */
  /**  CODE: Num a = new Num("15.1"); Num.print(a.Sub(6L)); //9.1 */
  public Num Sub(long sob) { return this.Sub(new Num(sob)); }

   /** Sub, (-) OBJECT SUBTRACTION METHOD BY BigInteger */
  /**  CODE: Num a = new Num("15.1"); Num.print(a.Sub(new BigInteger("6"))); //9.1 */
  public Num Sub(BigInteger sob) { return this.Sub(new Num(sob)); }

   /** Sub, (-) OBJECT SUBTRACTION METHOD BY String */
  /**  CODE: Num a = new Num("15.1"); Num.print(a.Sub("6.2")); //8.9 */
  public Num Sub(String sob) { return this.Sub(new Num(sob)); }

   /** Sub, (-) OBJECT SUBTRACTION METHOD BY Num */
  /**  CODE: Num a = new Num("15.1"); Num.print(a.Sub(new Num("6.2"))); //8.9 */
  public Num Sub(Num sob) {
    BigInteger x1;
    BigInteger x2;
    int ze;
    if (this.L_n1 < sob.L_n1) {
        x1 = new BigInteger(this.n2 + this.n0 + this.n1 + String.format("%0" + (sob.L_n1 - this.L_n1) + "d", 0));
        x2 = new BigInteger(sob.n2 + sob.n0 + sob.n1);
    } else if (this.L_n1 > sob.L_n1) {
        x1 = new BigInteger(this.n2 + this.n0 + this.n1);
        x2 = new BigInteger(sob.n2 + sob.n0 + sob.n1 + String.format("%0" + (this.L_n1 - sob.L_n1) + "d", 0));
    } else {
        x1 = new BigInteger(this.n2 + this.n0 + this.n1);
        x2 = new BigInteger(sob.n2 + sob.n0 + sob.n1);
    }    
    BigInteger x3 = x1.subtract(x2); 
    if (x3.compareTo(new BigInteger("0")) == 0) return new Num("0.0"); //ZERO RESULT DIF 
    String xt = x3.toString();
    int xt_L = xt.length();
    int xt_D = (sob.L_n1 > this.L_n1 ? sob.L_n1 : this.L_n1);    
    
    if (x3.compareTo(new BigInteger("0")) < 0) { //NEGATIVE DIF
        ze = xt_D - xt_L + 1;
        if (ze >= 0) { //-1 < Negative DIF < 0
            String xtr;
            if (ze <= 0)
                xtr = "-0" + "." + xt.substring(1); //-0.1 - 0.2 = -0.3
            else
                xtr = "-0" + "." + String.format("%0" + ze + "d", 0) + xt.substring(1); //0.01 - 0.09 = -0.08
            return new Num(xtr);
        } 
    } else {
        ze = xt_D - xt_L;
        if (ze >= 0) { //0 < POSITIVE DIF < 1
            String xtr;
            if (ze <= 0)
                xtr = "0" + "." + xt; //-0.1 - -0.2 = 0.1
            else
                xtr = "0" + "." + String.format("%0" + ze + "d", 0) + xt; //0.01 - 0.003 = 0.007
            return new Num(xtr);
        }
    }
    if (xt.charAt(0) == '-')
        return new Num(xt.substring(0, -ze + 1) + "." + xt.substring(-ze + 1)); //-12.0 - 3.0 = -15.0
    return new Num(xt.substring(0, -ze) + "." + xt.substring(-ze)); //15.1 - 6.2 = 8.9
  }
  
   /** Mul, (*) OBJECT MULTIPLICATION METHOD BY int */
  /**  CODE: Num a = new Num("-15.1"); Num.print(a.Mul(-6)); //90.6 */
  public Num Mul(int sob) { return this.Mul(new Num(sob)); }

   /** Mul, (*) OBJECT MULTIPLICATION METHOD BY long */
  /**  CODE: Num a = new Num("-15.1"); Num.print(a.Mul(-6L)); //90.6 */
  public Num Mul(long sob) { return this.Mul(new Num(sob)); }

   /** Mul, (*) OBJECT MULTIPLICATION METHOD BY BigInteger */
  /**  CODE: Num a = new Num("-15.1"); Num.print(a.Mul(new BigInteger("-6"))); //90.6 */
  public Num Mul(BigInteger sob) { return this.Mul(new Num(sob)); }
  
   /** Mul, (*) OBJECT MULTIPLICATION METHOD BY String */
  /**  CODE: Num a = new Num("-15.1"); Num.print(a.Mul("-6.2")); //93.62 */
  public Num Mul(String sob) { return this.Mul(new Num(sob)); }
  
   /** Mul, (*) OBJECT MULTIPLICATION METHOD BY Num */
  /**  CODE: Num a = new Num("-15.1"); Num.print(a.Mul(new Num("-6.2"))); //93.62 */
  public Num Mul(Num sob) {
      BigInteger x1;
      BigInteger x2;
      int ze;
      x1 = new BigInteger(this.n2 + this.n0 + this.n1);
      x2 = new BigInteger(sob.n2 + sob.n0 + sob.n1);
      BigInteger x3 = x1.multiply(x2);
      if (x3.compareTo(new BigInteger("0")) == 0)
          return new Num("0.0"); //MULTIPLY BY 0
      String xt = x3.toString();
      int xt_L = xt.length();
      int xt_D = this.L_n1 + sob.L_n1;
      if (x3.compareTo(new BigInteger("0")) < 0) { //NEGATIVE MULTIPLICATION
          ze = xt_D - xt_L + 1;
          if (ze >= 0) { //-1 < Negative MUL < 0
              String xtr;
              if (ze <= 0)
                  xtr = "-0" + "." + xt.substring(1); //-0.1 * 2.0 = -0.2
              else
                  xtr = "-0" + "." + String.format("%0" + ze + "d", 0) + xt.substring(1); //-0.1 * 0.2 = -0.02
              return new Num(xtr);
          }
          return new Num(xt.substring(0, -ze + 1) + "." + xt.substring(-ze + 1)); //-12.0 * 3.0 = -36.0
      }
      ze = xt_D - xt_L;
      if (ze >= 0) { //0 < POSITIVE MUL < 1
          String xtr;
          if (ze <= 0)
              xtr = "0" + "." + xt; //0.1 * 2.0 = 0.2
          else
              xtr = "0" + "." + String.format("%0" + ze + "d", 0) + xt; //0.1 * 0.2 = 0.02 or -0.1 * -0.2 = 0.02
          return new Num(xtr);
      }
      return new Num(xt.substring(0, -ze) + "." + xt.substring(-ze)); //12.0 * 3.0 = 36.0  or -12.0 * -3.0 = 36.0
  }
  
   /** Div, (/) OBJECT DIVISION BY int */
  /**  CODE: Num a = new Num("9.9"); Num Q = a.Div(3); Q.Print(); //3.3 */
  public Num Div(int sob) { return this.Div(new Num(sob), 80); }
  
   /** Div, (/) OBJECT DIVISION BY long */
  /**  CODE: Num a = new Num("9.9"); Num Q = a.Div(3L); Q.Print(); //3.3 */
  public Num Div(long sob) { return this.Div(new Num(sob), 80); }
  
   /** Div, (/) OBJECT DIVISION BY BigInteger */
  /**  CODE: Num a = new Num("9.9"); Num Q = a.Div(new BigInteger("3")); Q.Print(); //3.3 */
  public Num Div(BigInteger sob) { return this.Div(new Num(sob), 80); }
  
   /** Div, (/) OBJECT DIVISION BY String */
  /**  CODE: Num a = new Num("9.9"); Num Q = a.Div("3.3"); Q.Print(); //3.0 */
  public Num Div(String sob) { return this.Div(new Num(sob), 80); }
  
   /** Div, (/) OBJECT DIVISION BY Num */
  /**  CODE: Num a = new Num("9.9"); Num Q = a.Div(new Num("3.3")); Q.Print(); //3.0 */
  public Num Div(Num sob) { return this.Div(sob, 80); }
  
   /** Div, (/) OBJECT DIVISION BY Num, int */
  /**  CODE: Num a = new Num("1.0", 6); Num b = new Num("3.0", 6); Num Q = a.Div(b, 9); Q.Print(); //0.333333333 */
  public Num Div(Num sob, int d) { //d => PRECISION DIGITS
      if (sob.n.equals("0.0"))
          throw new ArithmeticException("Num.Div => DIVISION BY ZERO: " + sob.toString());
      if (this.n.equals("0.0"))
          return new Num("0.0"); //ZERO DIVIDEND MEANS ZERO QUOTIENT RESULT
      BigInteger x1;
      BigInteger x2;
      String x3;
      int ze;
      int D = this.d > sob.d ? this.d : sob.d;
      if (this.L_n1 > sob.L_n1) {
          ze = this.L_n1 - sob.L_n1;
          x1 = new BigInteger(this.n2 + this.n0 + this.n1);
          x2 = new BigInteger(sob.n2 + sob.n0 + sob.n1 + String.format("%0" + ze + "d", 0));
      } else {
          ze = sob.L_n1 - this.L_n1;
          if (ze == 0)
              x1 = new BigInteger(this.n2 + this.n0 + this.n1);
          else
              x1 = new BigInteger(this.n2 + this.n0 + this.n1 + String.format("%0" + ze + "d", 0));
          x2 = new BigInteger(sob.n2 + sob.n0 + sob.n1);
      }
      x3 = Num.divi(x1.toString(), x2.toString(), (d > D ? d : D) + "");
      return new Num(x3);
  }
  
   /** Div, (/) OBJECT DIVISION BY String, int */
  /**  CODE: Num a = new Num("1.0", 6); Num Q = a.Div("3.0", 9); Q.Print();    //0.333333333 */
  public Num Div(String sob, int d) { return this.Div(new Num(sob, d), d); }  //d => PRECISION DIGITS

   /** Div, (/) OBJECT DIVISION BY int, int */
  /**  CODE: Num a = new Num("9.8", 6); Num Q = a.Div(3, 7); Q.Print("\r\n"); //3.2666666 */
  public Num Div(int sob, int d) { return this.Div(new Num(sob, d), d); }    //d => PRECISION DIGITS

   /** Div, (/) OBJECT DIVISION BY long, int */
  /**  CODE: Num a = new Num("9.8", 6); Num Q = a.Div(3L, 7); Q.Print("\r\n"); //3.2666666 */
  public Num Div(long sob, int d) { return this.Div(new Num(sob, d), d); }    //d => PRECISION DIGITS

   /** Div, (/) OBJECT DIVISION BY BigInteger, int */
  /**  CODE: Num a = new Num("9.8", 6); Num Q = a.Div(new BigInteger("3"), 7); Q.Print("\r\n"); //3.2666666 */
  public Num Div(BigInteger sob, int d) { return this.Div(new Num(sob, d), d); }               //d => PRECISION DIGITS

   /** Mod, (%) MODULE OPERATOR BY int (Num FLOATING POINT DIVISION REMAINDER) */
  /**  CODE: Num a = new Num("15.2"); Num.print(a.Mod(6), "\r\n"); //3.2 */
  public Num Mod(int sob) { return this.Mod(new Num(sob)); }

   /** Mod, (%) MODULE OPERATOR BY long (Num FLOATING POINT DIVISION REMAINDER) */
  /**  CODE: Num a = new Num("15.2"); Num.print(a.Mod(6L), "\r\n"); //3.2 */
  public Num Mod(long sob) { return this.Mod(new Num(sob)); }
  
   /** Mod, (%) MODULE OPERATOR BY BigInteger (Num FLOATING POINT DIVISION REMAINDER) */
  /**  CODE: Num a = new Num("15.2"); Num.print(a.Mod(new BigInteger("6")), "\r\n"); //3.2 */
  public Num Mod(BigInteger sob) { return this.Mod(new Num(sob)); }

   /** Mod, (%) MODULE OPERATOR BY String (Num FLOATING POINT DIVISION REMAINDER) */
  /**  CODE: Num a = new Num("15.2"); Num.print(a.Mod("6.0"), "\r\n"); //3.2 */
  public Num Mod(String sob) { return this.Mod(new Num(sob)); }

   /** Mod, (%) MODULE OPERATOR BY Num (Num FLOATING POINT DIVISION REMAINDER) */
  /**  CODE: Num a = new Num("15.2"); Num.print(a.Mod(new Num("6.0")), "\r\n"); //3.2 */
  public Num Mod(Num sob) {
      if (sob.n.equals("0.0"))
          throw new ArithmeticException("Num.Mod => DIVISION BY ZERO: " + sob.toString());
      if (this.n.equals("0.0"))
          return new Num("0.0"); //ZERO DIVIDEND MEANS ZERO MODULUS RESULT
      BigInteger x1;
      BigInteger x2;
      String x3;
      int ze;
      if (this.L_n1 > sob.L_n1) {
          ze = this.L_n1 - sob.L_n1;
          x1 = new BigInteger(this.n2 + this.n0 + this.n1);
          x2 = new BigInteger(sob.n2 + sob.n0 + sob.n1 + String.format("%0" + ze + "d", 0));
      } else {
          ze = sob.L_n1 - this.L_n1;
          if (ze == 0)
              x1 = new BigInteger(this.n2 + this.n0 + this.n1);
          else
              x1 = new BigInteger(this.n2 + this.n0 + this.n1 + String.format("%0" + ze + "d", 0));
          x2 = new BigInteger(sob.n2 + sob.n0 + sob.n1);
      }
      x3 = Num.divi(x1.toString(), x2.toString(), 0 + "");
      return this.Sub(new Num(x3).Mul(sob));
  }

   /** FloorDiv, (//) INTEGER DIVISION OPERATOR BY int */
  /**  CODE: Num a = new Num(15); Num.print(a.FloorDiv(6), "\r\n"); //2.0 */
  public Num FloorDiv(int sob) { return this.FloorDiv(new Num(sob)); }

   /** FloorDiv, (//) INTEGER DIVISION OPERATOR BY long */
  /**  CODE: Num a = new Num(15); Num.print(a.FloorDiv(6L), "\r\n"); //2.0 */
  public Num FloorDiv(long sob) { return this.FloorDiv(new Num(sob)); }
  
   /** FloorDiv, (//) INTEGER DIVISION OPERATOR BY BigInteger */
  /**  CODE: Num a = new Num(15); Num.print(a.FloorDiv(new BigInteger("6")), "\r\n"); //2.0 */
  public Num FloorDiv(BigInteger sob) { return this.FloorDiv(new Num(sob)); }

   /** FloorDiv, (//) INTEGER DIVISION OPERATOR BY String */
  /**  CODE: Num a = new Num(15); Num.print(a.FloorDiv("6.0"), "\r\n"); //2.0 */
  public Num FloorDiv(String sob) { return this.FloorDiv(new Num(sob)); }
  
   /** FloorDiv, (//) INTEGER DIVISION OPERATOR BY Num */
  /**  CODE: Num a = new Num(15); Num.print(a.FloorDiv(new Num(6)), "\r\n"); //2.0 */
  public Num FloorDiv(Num sob) {
      if (sob.n.equals("0.0"))
          throw new ArithmeticException("Num.FloorDiv => DIVISION BY ZERO: " + sob.toString());
      if (this.n.equals("0.0"))
          return new Num("0.0"); //ZERO DIVIDEND MEANS ZERO MODULUS RESULT
      BigInteger x1;
      BigInteger x2;
      String x3;
      int ze;
      if (this.L_n1 > sob.L_n1) {
          ze = this.L_n1 - sob.L_n1;
          x1 = new BigInteger(this.n2 + this.n0 + this.n1);
          x2 = new BigInteger(sob.n2 + sob.n0 + sob.n1 + String.format("%0" + ze + "d", 0));
      } else {
          ze = sob.L_n1 - this.L_n1;
          if (ze == 0)
              x1 = new BigInteger(this.n2 + this.n0 + this.n1);
          else
              x1 = new BigInteger(this.n2 + this.n0 + this.n1 + String.format("%0" + ze + "d", 0));
          x2 = new BigInteger(sob.n2 + sob.n0 + sob.n1);
      }
      x3 = Num.divi(x1.toString(), x2.toString(), 0 + "");
      return new Num(x3);
  }
    
   /** DivMod, (// %) CALCULATOR DIVMOD BY int, RETURN ARRAY (this // sob, this % sob) */
  /**  CODE: Num a = new Num(11); Num[] qr = a.DivMod(4); Num.print(qr[0], "\r\n"); Num.print(qr[1]); //2.0 3.0 */
  public Num[] DivMod(int sob) { return this.DivMod(new Num(sob)); }

   /** DivMod, (// %) CALCULATOR DIVMOD BY long, RETURN ARRAY (this // sob, this % sob) */
  /**  CODE: Num a = new Num(11); Num[] qr = a.DivMod(4L); Num.print(qr[0], "\r\n"); Num.print(qr[1]); //2.0 3.0 */
  public Num[] DivMod(long sob) { return this.DivMod(new Num(sob)); }

   /** DivMod, (// %) CALCULATOR DIVMOD BY BigInteger, RETURN ARRAY (this // sob, this % sob) */
  /**  CODE: Num a = new Num(11); Num[] qr = a.DivMod(new BigInteger("4")); Num.print(qr[0], "\r\n"); Num.print(qr[1]); //2.0 3.0 */
  public Num[] DivMod(BigInteger sob) { return this.DivMod(new Num(sob)); }
  
   /** DivMod, (// %) CALCULATOR DIVMOD BY String, RETURN ARRAY (this // sob, this % sob) */
  /**  CODE: Num a = new Num(11); Num[] qr = a.DivMod("4.0"); Num.print(qr[0], "\r\n"); Num.print(qr[1]); //2.0 3.0 */
  public Num[] DivMod(String sob) { return this.DivMod(new Num(sob)); }

   /** DivMod, (// %) CALCULATOR DIVMOD BY Num, RETURN ARRAY (this // sob, this % sob) */
  /**  CODE: Num a = new Num(11); Num[] qr = a.DivMod(new Num(4)); Num.print(qr[0], "\r\n"); Num.print(qr[1]); //2.0 3.0 */
  public Num[] DivMod(Num sob) {
    Num[] QR = new Num[2]; //CREATE TWO ELEMENT ARRAY
    QR[0] = new Num(this).FloorDiv(new Num(sob));
    QR[1] = new Num(this).Mod(new Num(sob));
    return QR;
  }

   /** Inv, (1/this) CALCULATOR NUMBER INVERSE METHOD -DEFAULT PRECISION BY 80 */ 
  /**  CODE: Num a = new Num(3); Num i = a.Inv();  i.Print("\r\n"); //0.33333333333333333333333333333333333333333333333333333333333333333333333333333333 */
  public Num Inv() { return Num.inv(this, 80); }

   /** Inv, (1/this) CALCULATOR NUMBER INVERSE METHOD BY int */
  /**  CODE: Num a = new Num(3, 6); Num i = a.Inv(6); i.Print("\r\n"); //0.333333 */
  public Num Inv(int precision) { return Num.inv(this, precision); }

   /** Inv, (1/this) CALCULATOR NUMBER INVERSE METHOD BY long */
  /**  CODE: Num a = new Num(3, 6); a.Inv(6L).Print("\r\n"); //0.333333 */
  public Num Inv(long precision) { return Num.inv(this, (int) precision); }

   /** Inv, (1/this) CALCULATOR NUMBER INVERSE METHOD BY BigInteger */
  /**  CODE: Num a = new Num(3, 6); a.Inv(new BigInteger("6")).Print("\r\n"); //0.333333 */
  public Num Inv(BigInteger precision) { return Num.inv(this, precision.intValue()); }

   /** Inv, (1/this) CALCULATOR NUMBER INVERSE METHOD BY String */
  /**  CODE: Num a = new Num(3, 6); a.Inv("6.0").Print("\r\n"); //0.333333 */
  public Num Inv(String precision) { return Num.inv(this, new Num(precision).toInt()); }

   /** Inv, (1/this) CALCULATOR NUMBER INVERSE METHOD BY Num */
  /**  CODE: Num a = new Num(3, 6); a.Inv(new Num("6.0")).Print("\r\n"); //0.333333 */
  public Num Inv(Num precision) { return Num.inv(this, precision.toInt()); }

   /** Pow, (**) (EXPONENTIATION) POWER OPERATOR BY int */
  /**  CODE: Num a = new Num("3.0"); Num.print(a.Pow(-3).toString());   //0.037037037037037037037037037037037037037037037037037037037037037037037037037037035925925925925925925925925925925925925925925925925925925925925925925925925925925937037037037037037037037037037037037037037037037037037037037037037037037037037037 */
  public Num Pow(int sob) { return this.Pow(new Num(sob), 80); } //DEFAULT PRECISION 80 DIGITS

   /** Pow, (**) (EXPONENTIATION) POWER OPERATOR BY int, int*/
  /**  CODE: Num a = new Num("3.00", 5); Num.print(a.Pow(-3, 5).toString()); //0.037035925937037 */
  public Num Pow(int sob, int d) { return this.Pow(new Num(sob), d); }

   /** Pow, (**) (EXPONENTIATION) POWER OPERATOR BY long */
  /** CODE: Num a = new Num("3.00"); Num.print(a.Pow(-3L).toString()); //0.037037037037037037037037037037037037037037037037037037037037037037037037037037035925925925925925925925925925925925925925925925925925925925925925925925925925925937037037037037037037037037037037037037037037037037037037037037037037037037037037 */
  public Num Pow(long sob) { return this.Pow(new Num(sob), 80); } //DEFAULT PRECISION 80 DIGITS

   /** Pow, (**) (EXPONENTIATION) POWER OPERATOR BY long, int */
  /**  CODE: Num a = new Num("3.00", 10); Num.print(a.Pow(-3L, 10).toString()); //0.037037037025925925927037037037 */
  public Num Pow(long sob, int d) { return this.Pow(new Num(sob), d); }

   /** Pow, (**) (EXPONENTIATION) POWER OPERATOR BY BigInteger */
  /**  CODE: Num a = new Num("3.00"); Num.print(a.Pow(new BigInteger("-3")).toString()); //0.037037037037037037037037037037037037037037037037037037037037037037037037037037035925925925925925925925925925925925925925925925925925925925925925925925925925925937037037037037037037037037037037037037037037037037037037037037037037037037037037 */
  public Num Pow(BigInteger sob) { return this.Pow(new Num(sob), 80); } //DEFAULT PRECISION 80 DIGITS

   /** Pow, (**) (EXPONENTIATION) POWER OPERATOR BY BigInteger, int */
  /**  CODE: Num a = new Num("3.00", 15); Num.print(a.Pow(new BigInteger("-3"), 15).toString()); //0.037037037037036925925925925926037037037037037 */
  public Num Pow(BigInteger sob, int d) { return this.Pow(new Num(sob), d); }

   /** Pow, (**) (EXPONENTIATION) POWER OPERATOR BY String */
  /**  CODE: Num a = new Num("2.00"); Num.print(a.Pow("-3.0").toString()); //0.125 */
  public Num Pow(String sob) { return this.Pow(new Num(sob), 80); } //DEFAULT PRECISION 80 DIGITS

   /** Pow, (**) (EXPONENTIATION) POWER OPERATOR BY String, int */
  /** CODE: Num a = new Num("3.141592654", 10); Num.print(a.Pow("-3.0", 10).toString()); //0.032251534407730179135947651381 */
  public Num Pow(String sob, int d) { return this.Pow(new Num(sob), d); }

   /** Pow, (**) (EXPONENTIATION) POWER OPERATOR BY Num */
  /** CODE: Num a = new Num("3.141592654"); Num.print(a.Pow(new Num("-3.0")).toString()); //0.032251534420565963183639149002551720690125440292359015196489504890950534923222233109802231581447827445602932813419361707479550481360189350007735777669147583242390950655805042844969620223426588563049210070608115909461033913823713370141745352 */
  public Num Pow(Num E) { return this.Pow(E, 80); } //DEFAULT PRECISION 80 DIGITS

   /** Pow, (**) (EXPONENTIATION) POWER OPERATOR BY Num, int */
  /**  CODE: Num a = new Num("3.141592654"); Num.print(a.Pow(new Num("-3.0"), 100).toString()); //0.032251534420565963183639149002551720690125440292359015196489504890950534923222234282251668209780700891532299757290618532257964791419581463825291341703430964852401063542114643420891069615470949708249698637210961812637035097811897303489579129515175108317332929349480082260496236866876709512103187034125 */
  public Num Pow(Num E, int d) {
      BigInteger e;
      if (this.Not() && E.Not())
          throw new ArithmeticException("Num.Pow => UNDETERMINED: " + this.n0 + '^' + E.n0);
      if (E.Not())
          return new Num(1); //POW ALWAYS ONE
      if (E.Is_numfloat())
          throw new ArithmeticException("Num.Pow => EXPONENT, must be integer value: " + E.toString()); //EXPONENT MUST BE INTEGER
      else
          e = new BigInteger(E.n2 + E.n0);
      if (e.compareTo(new BigInteger("0")) < 0) {
          e = e.abs();
          Num b = this.Inv(d);
          this.n2 = b.n2;
          this.n0 = b.n0;
          this.n1 = b.n1;
          this.L_n0 = b.L_n0;
          this.L_n1 = b.L_n1;
          this.n = b.n;
          this.d = b.d;
      }
      int DOT = new BigInteger(this.L_n1 + "").multiply(e).intValue();
      String result_str = new BigInteger(this.n0 + this.n1 + "").pow(e.intValue()).toString();
      int OFFSET = result_str.length() - DOT;
      if (OFFSET == 0)
          return new Num(
                  (e.remainder(new BigInteger("2")).equals(new BigInteger("0")) ? "+" : this.n2) + "0." + result_str);
      else if (OFFSET <= 0)
          return new Num((e.remainder(new BigInteger("2")).equals(new BigInteger("0")) ? "+" : this.n2) + "0."
                  + String.format("%0" + (-OFFSET) + "d", 0) + result_str);
      String decs_part = result_str.substring(OFFSET);
      String int_part = result_str.substring(0, OFFSET);
      String temp = (e.remainder(new BigInteger("2")).equals(new BigInteger("0")) ? "+" : this.n2) + int_part + "."
              + decs_part;
      return new Num(temp);
  }
  
   /** Trunc, Num FLOATING POINT TRUNCATION -DEFAULT PRECISION VALUE BY ZERO */
  /**  CODE:  Num a = new Num("3.14159"); a.Trunc().Print("\r\n"); //3.0 */
  public Num Trunc() { return new Num(new BigInteger(this.n2 + this.n0)); }

   /** Trunc, Num FLOATING POINT TRUNCATION BY long */
  /**  CODE:  Num a = new Num("3.14159"); a.Trunc(2L).Print("\r\n"); //3.14 */
  public Num Trunc(long d) { return this.Trunc((int) d); }

   /** Trunc, Num FLOATING POINT TRUNCATION BY BigInteger */
  /**  CODE:  Num a = new Num("3.14159"); a.Trunc(new BigInteger("2")).Print("\r\n"); //3.14 */
  public Num Trunc(BigInteger d) { return this.Trunc(d.intValue()); }

   /** Trunc, Num FLOATING POINT TRUNCATION BY String */
  /**  CODE:  Num a = new Num("3.14159"); a.Trunc("2.0").Print("\r\n"); //3.14 */
  public Num Trunc(String d) { return this.Trunc(new Num(d).toInt()); }

   /** Trunc, Num FLOATING POINT TRUNCATION BY Num */
  /**  CODE:  Num a = new Num("3.14159"); a.Trunc(new Num("2.0")).Print("\r\n"); //3.14 */
  public Num Trunc(Num d) { return this.Trunc(d.toInt()); }

   /** Trunc, Num FLOATING POINT TRUNCATION BY int */
  /**  CODE:  Num a = new Num("3.14159"); a.Trunc(4).Print("\r\n"); //3.1415 */
  public Num Trunc(int d) {
    Num m = new Num(10).Pow(d);
    Num t = this.Mul(m);
    return new Num(new BigInteger(t.n2 + t.n0)).Div(m);
  }

   /** Round_floor, Num FLOOR ROUNDING RELATIVE DOWN -DEFAULT PRECISION VALUE BY ZERO */
  /**  CODE: Num a = new Num("-3.14159"); a.Round_floor().Print("\r\n"); //-4.0 */
  public Num Round_floor() { return this.Round_floor(0); } 

   /** Round_floor, Num FLOOR ROUNDING RELATIVE DOWN BY long */
  /**  CODE: Num a = new Num("-3.14159"); a.Round_floor(4L).Print("\r\n"); //-3.1416 */
  public Num Round_floor(long d) { return this.Round_floor((int) d); } 

   /** Round_floor, Num FLOOR ROUNDING RELATIVE DOWN BY BigInteger */
  /**  CODE: Num a = new Num("-3.14159"); a.Round_floor(new BigInteger("4")).Print("\r\n"); //-3.1416 */
  public Num Round_floor(BigInteger d) { return this.Round_floor(d.intValue()); } 

   /** Round_floor, Num FLOOR ROUNDING RELATIVE DOWN BY String */
  /**  CODE: Num a = new Num("-3.155"); a.Round_floor("2.0").Print("\r\n"); //-3.16 */
  public Num Round_floor(String d) { return this.Round_floor(new Num(d).toInt()); } 

   /** Round_floor, Num FLOOR ROUNDING RELATIVE DOWN BY Num */
  /**  CODE: Num a = new Num("3.14159"); a.Round_floor(new Num("2.0")).Print("\r\n"); //3.14 */
  public Num Round_floor(Num d) { return this.Round_floor(d.toInt()); } 

   /** Round_floor, Num FLOOR ROUNDING RELATIVE DOWN BY int (d=1: 0.12 => 0.1 -0.12 => -0.2) */
  /**  CODE: Num a = new Num("-3.14151"); Num T = a.Round_floor(4); T.Print("\r\n"); //-3.1416 */
  public Num Round_floor(int d) { //-> RELATIVE VALUE (REAL NUMBER R) 
      if (this.GE("0.0")) return this.Trunc(d); //POSITIVES AND ZERO  
      Num e = new Num("1.0", d).Div(new Num("10.0").Pow(d)); //NEGATIVES
      Num t, t2;
      if (d >= 0) {
          t = this.Trunc(d).Sub(e);
          t2 = this.Sub(e);
          return t.EQ(t2) ? this : t;
      }
      if (e.LT(this))
          return this;
      t = this.Trunc(d).Sub(e);
      t2 = this.Sub(e);
      return t.EQ(t2) ? this : t;
  }
  
   /** Round, Num HALF UP ROUNDING COMMON STANDARD -RELATIVE ROUND_HALF_CEIL DEFAULT PRECISION BY 2 */
  /**  CODE: Num a = new Num("3.145"); a.Round().Print("\r\n"); //3.15 */
  public Num Round() { return Round(2); }
  
   /** Round, Num HALF UP ROUNDING COMMON STANDARD BY long -RELATIVE ROUND_HALF_CEIL */
  /**  CODE: Num a = new Num("3.141592654"); a.Round(4L).Print("\r\n"); //3.1416 */
  public Num Round(long d) { return Round((int) d); }
  
   /** Round, Num HALF UP ROUNDING COMMON STANDARD BY BigInteger -RELATIVE ROUND_HALF_CEIL */
  /**  CODE: Num a = new Num("3.141592654"); a.Round(new BigInteger("4")).Print("\r\n"); //3.1416 */
  public Num Round(BigInteger d) { return Round(d.intValue()); }
  
   /** Round, Num HALF UP ROUNDING COMMON STANDARD BY String -RELATIVE ROUND_HALF_CEIL */
  /**  CODE: Num a = new Num("3.141592654"); a.Round("4.0").Print("\r\n"); //3.1416 */
  public Num Round(String d) { return Round(new Num(d).toInt()); }
  
   /** Round, Num HALF UP ROUNDING COMMON STANDARD BY Num -RELATIVE ROUND_HALF_CEIL */
  /**  CODE: Num a = new Num("3.141592654"); a.Round(new Num("4.0")).Print("\r\n"); //3.1416 */
  public Num Round(Num d) { return Round(d.toInt()); }
  
   /** Round, Num HALF UP ROUNDING COMMON STANDARD BY int -RELATIVE ROUND_HALF_CEIL d=1: 0.15 => 0.2 -0.15 => -0.1 */ 
  /**  CODE: Num a = new Num("-0.15"); Num T = a.Round(1); T.Print("\r\n"); //-0.1 */
  public Num Round(int d) { 
    Num t = new Num("0.5").Mul(new Num(10).Pow(-d)).Add(this);
    return t.Round_floor(d);
  } 

   /** Round_ceil, Num CEIL ROUNDING RELATIVE UP -DEFAULT PRECISION BY 0 */ 
  /**  CODE: Num a = new Num("31.4159"); Num T = a.Round_ceil(); T.Print("\r\n"); //32.0 */
  public Num Round_ceil() { return Round_ceil(0); }
  
   /** Round_ceil, Num CEIL ROUNDING RELATIVE UP BY long */ 
  /**  CODE: Num a = new Num("-31.4159"); Num T = a.Round_ceil(3L); T.Print("\r\n"); //-31.415 */
  public Num Round_ceil(long d) { return Round_ceil((int) d); }
  
   /** Round_ceil, Num CEIL ROUNDING RELATIVE UP BY BigInteger */ 
  /**  CODE: Num a = new Num("+31.4152"); a.Round_ceil(new BigInteger("3")).Print("\r\n"); //31.416 */
  public Num Round_ceil(BigInteger d) { return Round_ceil(d.intValue()); }
  
   /** Round_ceil, Num CEIL ROUNDING RELATIVE UP BY String */ 
  /**  CODE: Num a = new Num("-31.4158"); a.Round_ceil("3.0").Print("\r\n"); //-31.415 */
  public Num Round_ceil(String d) { return Round_ceil(new Num(d).toInt()); }
  
   /** Round_ceil, Num CEIL ROUNDING RELATIVE UP BY Num */ 
  /**  CODE: Num a = new Num("-31.4152"); a.Round_ceil(new Num("3.0")).Print("\r\n"); //-31.415 */
  public Num Round_ceil(Num d) { return Round_ceil(d.toInt()); }
  
   /** Round_ceil, Num CEIL ROUNDING RELATIVE UP BY int (d=1: 0.12 => 0.2 -0.12 => -0.1) */
  /**  CODE: Num a = new Num("3.14159"); Num T = a.Round_ceil(2); T.Print("\r\n"); //3.15 */
  public Num Round_ceil(int d) {
      if (this.LE(0)) return this.Trunc(d); //NEGATIVES AND ZERO 
      Num e = new Num("1.0", d).Div(new Num("10.0").Pow(d)); //POSITIVES
      Num t, t2;
      if (d >= 0) {
          t = this.Trunc(d).Add(e);
          t2 = this.Add(e);
          return t.EQ(t2) ? this : t;
      }
      if (e.GT(this))
          return this;
      t = this.Trunc(d).Add(e);
      t2 = this.Add(e);
      return t.EQ(t2) ? this : t;
  }

   /** Round_Bank, Num HALF EVEN ROUNDING -DEFAULT PRECISION BY 2 (CLASSIC ALGORITHM) */
  /**  CODE: Num a = new Num("3.145"); Num T = a.Round_Bank(); T.Print("\r\n"); //3.14 */
  public Num Round_Bank() { return Round_Bank(2); }
  
   /** Round_Bank, Num HALF EVEN ROUNDING BY long (CLASSIC ALGORITHM) */
  /**  CODE: Num a = new Num("3.1405"); Num T = a.Round_Bank(3L); T.Print("\r\n"); //3.14 */
  public Num Round_Bank(long d) { return Round_Bank((int) d); }
  
   /** Round_Bank, Num HALF EVEN ROUNDING BY BigInteger (CLASSIC ALGORITHM) */
  /**  CODE: Num a = new Num("3.1405"); Num T = a.Round_Bank(new BigInteger("3")); T.Print("\r\n"); //3.14 */
  public Num Round_Bank(BigInteger d) { return Round_Bank(d.intValue()); }
  
   /** Round_Bank, Num HALF EVEN ROUNDING BY String (CLASSIC ALGORITHM) */
  /**  CODE: Num a = new Num("3.1405"); Num T = a.Round_Bank("3.0"); T.Print("\r\n"); //3.14 */
  public Num Round_Bank(String d) { return Round_Bank(new Num(d).toInt()); }
  
   /** Round_Bank, Num HALF EVEN ROUNDING BY Num (CLASSIC ALGORITHM) */
  /**  CODE: Num a = new Num("3.1405"); Num T = a.Round_Bank(new Num("3.0")); T.Print("\r\n"); //3.14 */
  public Num Round_Bank(Num d) { return Round_Bank(d.toInt()); }
  
   /** Round_Bank, Num HALF EVEN ROUNDING BY int (CLASSIC ALGORITHM) */
  /**  CODE: Num a = new Num("3.14159265"); Num T = a.Round_Bank(7); T.Print("\r\n"); //3.1415926 */
  public Num Round_Bank(int d) { 
    Num result;
    Num M = new Num(10).Pow(d);
    Num scaled = this.Mul(M);
    Num int_part = scaled.Trunc();
    Num fra_part = scaled.Sub(int_part);
    if(this.Is_positive())
        if(fra_part.LT("0.5")) result = int_part;
        else if(fra_part.GT("0.5")) result = int_part.Inc();
        else result = int_part.Mod(2).Is_false() ? int_part : int_part.Inc();
    else
        if(fra_part.GT("-0.5")) result = int_part;
        else if(fra_part.LT("-0.5")) result = int_part.Dec();
        else result = int_part.Mod(2).Is_false() ? int_part : int_part.Dec();
    return result.Div(M);
  }
  
   /** Round_bank, Num HALF EVEN ROUNDING -DEFAULT PRECISION BY 2 */
  /**  CODE: Num a = new Num("3.145"); Num T = a.Round_bank(); T.Print("\r\n"); //3.14 */
  public Num Round_bank() { return this.Round_bank(2); }

   /** Round_bank, Num HALF EVEN ROUNDING BY long */
  /**  CODE: Num a = new Num("-3.00145"); Num T = a.Round_bank(4L); T.Print("\r\n"); //-3.0014 */
  public Num Round_bank(long d) { return this.Round_bank((int) d); }

   /** Round_bank, Num HALF EVEN ROUNDING BY BigInteger */
  /**  CODE: Num a = new Num("-3.00145"); Num T = a.Round_bank(new BigInteger("4")); T.Print("\r\n"); //-3.0014 */
  public Num Round_bank(BigInteger d) { return this.Round_bank(d.intValue()); }

   /** Round_bank, Num HALF EVEN ROUNDING BY String */
  /**  CODE: Num a = new Num("-3.00145"); Num T = a.Round_bank("4.0"); T.Print("\r\n"); //-3.0014 */
  public Num Round_bank(String d) { return this.Round_bank(new Num(d).toInt()); }

   /** Round_bank, Num HALF EVEN ROUNDING BY Num */
  /**  CODE: Num a = new Num("-3.00145"); Num T = a.Round_bank(new Num("4.0")); T.Print("\r\n"); //-3.0014 */
  public Num Round_bank(Num d) { return this.Round_bank(d.toInt()); }

   /** Round_bank, Num HALF EVEN ROUNDING BY int */
  /**  CODE: Num a = new Num("3.14159265"); Num T = a.Round_bank(7); T.Print("\r\n"); //3.1415926 */
  public Num Round_bank(int d) { //d = 2
      if (d < 0) {
          d = -d;
          BigInteger e = new BigInteger("10").pow(d); //   let e = 10n**BigInt(d)         
          return ((this.Div(new Num(e))).Round_bank(0)).Mul(new Num(e)); //RECURSION
      }
      int of = d - this.L_n1;
      if (of >= 0)
          return new Num(this.n); //NO ROUND
      BigInteger a, b;
      String c;
      if (d == 0) { //INTEGER ROUNDING (d=0)
          a = new BigInteger(this.n0);
          b = new BigInteger(this.n1.substring(0, 1));
          c = Num.rstrip(this.n1.substring(1), "0");
          if (b.compareTo(new BigInteger("5")) > 0) { //b > 5n
              a = a.add(new BigInteger("1"));
              return new Num(this.n2 + a.toString() + ".0"); //12.6 => 13.0 INTEGER
          } else if (b.compareTo(new BigInteger("5")) == 0) { //b == 5n
              if (a.remainder(new BigInteger("2")).equals(new BigInteger("1"))) { //ODD //a % 2n
                  a = a.add(new BigInteger("1")); //a += 1n
                  return new Num(this.n2 + a.toString() + ".0"); //13.5 => 14.0 INTEGER
              } else if (!c.equals("")) { //EVEN OVERFLOW //c != ''
                  a = a.add(new BigInteger("1")); //a += 1n
                  return new Num(this.n2 + a.toString() + ".0"); //12.51 => 13.0 INTEGER
              } else { //EVEN
                  if (a.compareTo(new BigInteger("0")) == 0)
                      return new Num("0.0"); //a == 0
                  return new Num(this.n2 + a.toString() + ".0"); //12.5 => 12.0 INTEGER -0.5 => 0.0
              }
          } else {
              if (new BigInteger(this.n0).compareTo(new BigInteger("1")) >= 0)
                  return new Num(this.n2 + a.toString() + ".0"); //12.3 => 12.0 INTEGER   
              return new Num("0.0"); //0.3 => 0.0 
          }
      }
      //FLOATING POINT ROUNDING (d>0)
      a = new BigInteger(this.n1.substring(d - 1, d));
      b = new BigInteger(this.n1.substring(d, d + 1));
      c = Num.rstrip(this.n1.substring(d + 1), "0");
      int of2;
      String s;
      if (b.compareTo(new BigInteger("5")) > 0) { //b > 5n
          a = a.add(new BigInteger("1")); //a += 1n 
          of2 = 1;
          if (a.compareTo(new BigInteger("9")) > 0) { //FLAG CARRY //a > 9n
              while (a.compareTo(new BigInteger("9")) > 0) {
                  s = this.n1.substring(d - of2 - 1 < 0 ? 0 : d - of2 - 1, d - of2);
                  if (s.equals(""))
                      return new Num(this.n2 + (new BigInteger(this.n0).add(new BigInteger("1"))).toString() + ".0"); //3.99 => 4.0
                  a = new BigInteger(s);
                  a = a.add(new BigInteger("1")); //a += 1n 
                  of2 += 1;
              }
              return new Num(this.n2 + this.n0 + "." + this.n1.substring(0, d - of2) + a.toString()); //3.095 => 3.1        
          }
          return new Num(this.n2 + this.n0 + "." + this.n1.substring(0, d - 1) + a.toString()); //3.1476 => 3.148
      } else if (b.compareTo(new BigInteger("5")) == 0) { //b == 5n
          if (a.remainder(new BigInteger("2")).equals(new BigInteger("1"))) { //ODD //a % 2n
              a = a.add(new BigInteger("1")); //a += 1n 
              of2 = 1;
              while (a.compareTo(new BigInteger("9")) > 0) {
                  s = this.n1.substring(d - of2 - 1 < 0 ? 0 : d - of2 - 1, d - of2);
                  if (s.equals(""))
                      return new Num(this.n2 + (new BigInteger(this.n0).add(new BigInteger("1"))).toString() + ".0");//3.95 => 4.0
                  a = new BigInteger(s); //3.0955 d=2 => 3.1
                  a = a.add(new BigInteger("1")); //a += 1n  
                  of2 += 1;
              }
              return new Num(this.n2 + this.n0 + "." + this.n1.substring(0, d - of2) + a.toString()); //3.095 => 3.1 //3.1415 => 3.142
          } else if (!c.equals("")) { //EVEN OVERFLOW //c != ''
              a = a.add(new BigInteger("1")); //a += 1n
              return new Num(this.n2 + this.n0 + "." + this.n1.substring(0, d - 1) + a.toString());//12.051 => 12.1 INTEGER
          } else {
              if (new BigInteger(this.n0).equals(new BigInteger("0"))
                      && new BigInteger(this.n1).equals(new BigInteger("5")))
                  return new Num(0); // self.n0 == 0 and self.n1 == 5 (ex. -0.00000005) -ZERO SYMMETRIC MEETING
              return new Num(this.n2 + this.n0 + "." + this.n1.substring(0, d - 1) + a.toString()); //EVEN 5.65 => 5.6 -0.05 => 0.0        
          }
      } else {
          try {
              return new Num(this.n2 + this.n0 + "." + this.n1.substring(0, d - 1) + a.toString()); //3.1415 => 3.14 //-0.02 => -0.0 ERROR!        
          } catch (Exception e) {
              return new Num(0);
          } //-0.02 => 0.0 OK.
      }
  }
 
   /** _10x, CALCULATOR MODE: MULTIPLY BY TEN */
  /**  CODE: Num a = new Num("3.2"); a = a._10x(); Num.print(a.toString()); //32.0 */
  public Num _10x() { return this.Shift(1); }

   /** _100x, CALCULATOR MODE: MULTIPLY BY HUNDRED */
  /**  CODE: Num a = new Num("3.2"); a = a._100x(); Num.print(a.toString()); //320.0 */
  public Num _100x() { return this.Shift(2); }

   /** _1000x, CALCULATOR MODE: MULTIPLY BY THOUSAND */
  /**  CODE: Num a = new Num("3.2"); a = a._1000x(); Num.print(a.toString()); //3200.0 */
  public Num _1000x() { return this.Shift(3); }

   /** _10div, CALCULATOR MODE: DIVIDE BY TEN */
  /**  CODE: Num a = new Num("3.2"); a = a._10div(); Num.print(a.toString()); //0.32 */
  public Num _10div() { return this.Shift(-1); }

   /** _100div, CALCULATOR MODE: DIVIDE BY HUNDRED */
  /**  CODE: Num a = new Num("3.2"); a = a._100div(); Num.print(a.toString()); //0.032 */
  public Num _100div() { return this.Shift(-2); }

   /** _1000div, CALCULATOR MODE: DIVIDE BY THOUSAND */
  /**  CODE: Num a = new Num("3.2"); a = a._1000div(); Num.print(a.toString()); //0.0032 */
  public Num _1000div() { return this.Shift(-3); }

   /** _2x, CALCULATOR MODE: DOUBLED VALUE */
  /**  CODE: Num a = new Num ("123.0"); a._2x().Print("\r\n"); //246.0 */
  public Num _2x() { return this.Add(this); }

   /** _3x, CALCULATOR MODE: TRIPLED VALUE */
  /** CODE: Num a = new Num ("123.0"); a._3x().Print("\r\n"); //369.0 */
  public Num _3x() { return this.Add(this).Add(this); }

   /** Xe10, CALCULATOR MODE (LIKE Shift method): RETURN OBJECT MULTIPLIED OR DIVIDED FOR 10 POWER BY int */
  /**  CODE: Num a = new Num("0.001"); a.Xe10(6 ).Print("\r\n"); //1000.0 */
  public Num Xe10(int p) { return this.Shift(p); }

   /** Xe10, CALCULATOR MODE (LIKE Shift method): RETURN OBJECT MULTIPLIED OR DIVIDED FOR 10 POWER BY long */
  /**  CODE: Num a = new Num("1000.0"); a.Xe10(-6L).Print("\r\n");//0.001 */
  public Num Xe10(long p) { return this.Shift(p); }

   /** Xe10, CALCULATOR MODE (LIKE Shift method): RETURN OBJECT MULTIPLIED OR DIVIDED FOR 10 POWER BY BigInteger */
  /**  CODE: Num a = new Num("1000.0"); a.Xe10(new BigInteger("-6")).Print("\r\n");//0.001 */
  public Num Xe10(BigInteger p) { return this.Shift(p); }

   /** Xe10, CALCULATOR MODE (LIKE Shift method): RETURN OBJECT MULTIPLIED OR DIVIDED FOR 10 POWER BY String */
  /**  CODE: Num a = new Num("1000.0"); a.Xe10("-6.0").Print("\r\n");//0.001 */
  public Num Xe10(String p) { return this.Shift(p); }

   /** Xe10, CALCULATOR MODE (LIKE Shift method): RETURN OBJECT MULTIPLIED OR DIVIDED FOR 10 POWER BY Num */
  /**  CODE: Num a = new Num("1000.0"); a.Xe10(new Num("-6.0")).Print("\r\n");//0.001 */
  public Num Xe10(Num p) { return this.Shift(p); }

   /** Xy, CALCULATOR MODE: EXPONENTIATION (POWER) BY int */
  /**  CODE: Num a = new Num("4.0");  a.Xy(30).Print("\r\n");  //1152921504606846976.0 */
  public Num Xy(int y) { return this.Pow(y); }

   /** Xy, CALCULATOR MODE: EXPONENTIATION (POWER) BY long */
  /**  CODE: Num a = new Num("-4.0"); a.Xy(-3L).Print("\r\n"); //-0.015625 */
  public Num Xy(long y) { return this.Pow(y); }

   /** Xy, CALCULATOR MODE: EXPONENTIATION (POWER) BY BigInteger */
  /**  CODE: Num a = new Num("-4.0"); a.Xy(new BigInteger("-3")).Print("\r\n"); //-0.015625 */
  public Num Xy(BigInteger y) { return this.Pow(y); }

   /** Xy, CALCULATOR MODE: EXPONENTIATION (POWER) BY Num */
  /**  CODE: Num a = new Num("-4.0"); a.Xy(new Num("-3.0")).Print("\r\n"); //-0.015625 */
  public Num Xy(Num y) { return this.Pow(y); }

   /** Xy, CALCULATOR MODE: EXPONENTIATION (POWER) BY String */
  /**  CODE: Num a = new Num("-4.0"); a.Xy("-3.0").Print("\r\n"); //-0.015625 */
  public Num Xy(String y) { return this.Pow(y); }

   /** Fact, CALCULATOR MODE: FACTORIAL COMPUTATION */
  /**  CODE: Num a = new Num(5); Num.print(a.Fact()); //120 */
  public Num Fact() { return Num.fact(this.toInt()); }

   /** Pct, CALCULATOR MODE: BY Num ALL, RETURN THE PERCENTAGE VALUE OF this RATE */
  /**  CODE: Num rate = new Num("22.0"); rate.Pct(new Num("1_648.98")).Round().Print(" => PCT DISCOUNT\n"); //362.78 => PCT DISCOUNT */
  public Num Pct(Num ALL) { return Num.pct(ALL, this); }

   /** Pct, CALCULATOR MODE: BY String ALL, RETURN THE PERCENTAGE VALUE OF this RATE */
  /**  CODE: Num rate = new Num("22.0"); rate.Pct("1_648.98").Round().Print(" => PCT DISCOUNT\n"); //362.78 => PCT DISCOUNT */
  public Num Pct(String ALL) { return Num.pct(new Num(ALL), this); }

   /** Pct, CALCULATOR MODE: BY int ALL, RETURN THE PERCENTAGE VALUE OF this RATE */
  /**  CODE: Num rate = new Num("22.0"); rate.Pct(1648).Round().Print(" => PCT DISCOUNT\n"); //362.56 => PCT DISCOUNT */
  public Num Pct(int ALL) { return Num.pct(new Num(ALL), this); }
  
   /** Pct, CALCULATOR MODE: BY long ALL, RETURN THE PERCENTAGE VALUE OF this RATE */
  /**  CODE: Num rate = new Num("22.0"); rate.Pct(1648L).Round().Print(" => PCT DISCOUNT\n"); //362.56 => PCT DISCOUNT */
  public Num Pct(long ALL) { return Num.pct(new Num(ALL), this); }
  
   /** Pct, CALCULATOR MODE: BY BigInteger ALL, RETURN THE PERCENTAGE VALUE OF this RATE */
  /**  CODE: Num rate = new Num("22.0"); rate.Pct(new BigInteger("1648")).Round().Print(" => PCT DISCOUNT\n"); //362.56 => PCT DISCOUNT */
  public Num Pct(BigInteger ALL) { return Num.pct(new Num(ALL), this); }
  
   /** Rate_all, CALCULATOR MODE: BY Num ALL, RETURN THE RATE OF this PERCENTAGE */
  /**  CODE: Num pct = new Num("362.78"); pct.Rate_all(new Num("1_648.98")).Round().Print(" => rate\n"); //22.0 => rate */
  public Num Rate_all(Num all) { return Num.rate(this, all); }

   /** Rate_all, CALCULATOR MODE: BY String ALL, RETURN THE RATE OF this PERCENTAGE */
  /**  CODE: Num pct = new Num("362.78"); pct.Rate_all("1_648.98").Round().Print(" => rate\n"); //22.0 => rate */
  public Num Rate_all(String all) { return Num.rate(this, new Num (all)); }

   /** Rate_all, CALCULATOR MODE: BY int ALL, RETURN THE RATE OF this PERCENTAGE */
  /**  CODE: Num pct = new Num("200.00"); pct.Rate_all(1600).Print(" => rate\n"); //12.5 => rate */
  public Num Rate_all(int all) { return Num.rate(this, new Num (all)); }
  
   /** Rate_all, CALCULATOR MODE: BY long ALL, RETURN THE RATE OF this PERCENTAGE */
  /**  CODE: Num pct = new Num("200.00"); pct.Rate_all(1600L).Print(" => rate\n"); //12.5 => rate */
  public Num Rate_all(long all) { return Num.rate(this, new Num (all)); }
  
   /** Rate_all, CALCULATOR MODE: BY BigInteger ALL, RETURN THE RATE OF this PERCENTAGE */
  /**  CODE: Num pct = new Num("200.00"); pct.Rate_all(new BigInteger("1600")).Print(" => rate\n"); //12.5 => rate */
  public Num Rate_all(BigInteger all) { return Num.rate(this, new Num (all)); }
  
   /** Rate_pct, CALCULATOR MODE: BY Num PERCENTAGE, RETURN THE RATE OF this ALL */
  /**  CODE: Num all = new Num("1_648.98"); all.Rate_pct(new Num("362.78")).Round().Print(" => rate\n"); //22.0 => rate */
  public Num Rate_pct(Num pct) { return pct.Shift(2).Div(this); }

   /** Rate_pct, CALCULATOR MODE: BY String PERCENTAGE, RETURN THE RATE OF this ALL */
  /**  CODE: Num all = new Num("1_648.98"); all.Rate_pct("362.78").Round().Print(" => rate\n"); //22.0 => rate */
  public Num Rate_pct(String pct) { return new Num(pct).Shift(2).Div(this); }

   /** Rate_pct, CALCULATOR MODE: BY int PERCENTAGE, RETURN THE RATE OF this ALL */
  /**  CODE: Num all = new Num("1_650.00"); all.Rate_pct(363).Print(" => rate\n"); //22.0 => rate */
  public Num Rate_pct(int pct) { return new Num(pct).Shift(2).Div(this); }
  
   /** Rate_pct, CALCULATOR MODE: BY long PERCENTAGE, RETURN THE RATE OF this ALL */
  /**  CODE: Num all = new Num("1_650.00"); all.Rate_pct(363L).Print(" => rate\n"); //22.0 => rate */
  public Num Rate_pct(long pct) { return new Num(pct).Shift(2).Div(this); }
  
   /** Rate_pct, CALCULATOR MODE: BY BigInteger PERCENTAGE, RETURN THE RATE OF this ALL */
  /**  CODE: Num all = new Num("1_650.00"); all.Rate_pct(new BigInteger("363")).Print(" => rate\n"); //22.0 => rate */
  public Num Rate_pct(BigInteger pct) { return new Num(pct).Shift(2).Div(this); }
  
   /** All_pct, CALCULATOR MODE: BY Num PERCENTAGE RETURN THE ALL OF this RATE */ 
  /**  CODE: Num rate = new Num("22.00025"); rate.All_pct(new Num("362.78")).Round().Print(" => ALL\n"); //1648.98 => ALL */
  public Num All_pct(Num PCT) { return Num.all(this, PCT); }

   /** All_pct, CALCULATOR MODE: BY String PERCENTAGE RETURN THE ALL OF this RATE */ 
  /**  CODE: Num rate = new Num("22.00025"); rate.All_pct("362.78").Round().Print(" => ALL\n"); //1648.98 => ALL */
  public Num All_pct(String PCT) { return Num.all(this, new Num(PCT)); }

   /** All_pct, CALCULATOR MODE: BY int PERCENTAGE RETURN THE ALL OF this RATE */ 
  /**  CODE: Num rate = new Num("22.00"); rate.All_pct(363).Round().Print(" => ALL\n"); //1650.0 => ALL */
  public Num All_pct(int PCT) { return Num.all(this, new Num(PCT)); }
  
   /** All_pct, CALCULATOR MODE: BY long PERCENTAGE RETURN THE ALL OF this RATE */ 
  /**  CODE: Num rate = new Num("22.00"); rate.All_pct(363L).Round().Print(" => ALL\n"); //1650.0 => ALL */
  public Num All_pct(long PCT) { return Num.all(this, new Num(PCT)); }
  
   /** All_pct, CALCULATOR MODE: BY BigInteger PERCENTAGE RETURN THE ALL OF this RATE */ 
  /**  CODE: Num rate = new Num("22.00"); rate.All_pct(new BigInteger("363")).Round().Print(" => ALL\n"); //1650.0 => ALL */
  public Num All_pct(BigInteger PCT) { return Num.all(this, new Num(PCT)); }
  
   /** All_rate, CALCULATOR MODE: BY Num RATE RETURN THE ALL OF this PCT */ 
  /**  CODE: Num pct = new Num(new Num("362.78")); pct.All_rate(new Num("22.00025")).Round().Print(" => ALL\r\n"); //1648.98 => ALL */
  public Num All_rate(Num rate) { return this.Shift(2).Div(rate); }

   /** All_rate, CALCULATOR MODE: BY String RATE RETURN THE ALL OF this PCT */ 
  /**  CODE: Num pct = new Num("362.78"); pct.All_rate("22.00025").Round().Print(" => ALL\r\n"); //1648.98 => ALL */
  public Num All_rate(String rate) { return this.Shift(2).Div(new Num(rate)); }

   /** All_rate, CALCULATOR MODE: BY int RATE RETURN THE ALL OF this PCT */ 
  /**  CODE: Num pct = new Num("362.78"); pct.All_rate(22).Print(" => ALL\r\n"); //1649.0 => ALL */
  public Num All_rate(int rate) { return this.Shift(2).Div(new Num(rate)); }
  
   /** All_rate, CALCULATOR MODE: BY long RATE RETURN THE ALL OF this PCT */ 
  /**  CODE: Num pct = new Num("362.78"); pct.All_rate(22L).Print(" => ALL\r\n"); //1649.0 => ALL */
  public Num All_rate(long rate) { return this.Shift(2).Div(new Num(rate)); }
  
   /** All_rate, CALCULATOR MODE: BY BigInteger RATE RETURN THE ALL OF this PCT */ 
  /**  CODE: Num pct = new Num("362.78"); pct.All_rate(new BigInteger("22")).Print(" => ALL\r\n"); //1649.0 => ALL */
  public Num All_rate(BigInteger rate) { return this.Shift(2).Div(new Num(rate)); }
  
   /** Pth, CALCULATOR MODE: BY Num RATE-TH RETURN THE PERTHOUSAND OF this ALL */
  /**  CODE: Num all  = new Num("10_000.0"); all.Pth(new Num(2)).Print("\n"); //20.0 */
  public Num Pth(Num RA) { return Num.pth(RA, this); }

   /** Pth, CALCULATOR MODE: BY String RATE-TH RETURN THE PERTHOUSAND OF this ALL */
  /**  CODE: Num all  = new Num("10_000.0"); all.Pth("2.0").Print("\n"); //20.0 */
  public Num Pth(String RA) { return Num.pth(new Num(RA), this); }

   /** Pth, CALCULATOR MODE: BY int RATE-TH RETURN THE PERTHOUSAND OF this ALL */
  /**  CODE: Num all  = new Num("10_000.0"); all.Pth(2).Print("\n"); //20.0 */
  public Num Pth(int RA) { return Num.pth(new Num(RA), this); }
  
   /** Pth, CALCULATOR MODE: BY long RATE-TH RETURN THE PERTHOUSAND OF this ALL */
  /**  CODE: Num all  = new Num("10_000.0"); all.Pth(2L).Print("\n"); //20.0 */
  public Num Pth(long RA) { return Num.pth(new Num(RA), this); }
  
   /** Pth, CALCULATOR MODE: BY BigInteger RATE-TH RETURN THE PERTHOUSAND OF this ALL */
  /**  CODE: Num all  = new Num("10_000.0"); all.Pth(new BigInteger("2")).Print("\n"); //20.0 */
  public Num Pth(BigInteger RA) { return Num.pth(new Num(RA), this); }
  
   /** RateTH_all, CALCULATOR MODE: BY Num ALL, RETURN THE RATE-TH OF this PERTHOUSAND */
  /**  CODE: Num pth = new Num(20); pth.RateTH_all(new Num(10000)).Round().Print(" => rate_th\n"); //2.0 => rate_th */
  public Num RateTH_all(Num all) { return Num.rate_th(this, all); }

   /** RateTH_all, CALCULATOR MODE: BY String ALL, RETURN THE RATE-TH OF this PERTHOUSAND */
  /**  CODE: Num pth = new Num(20); pth.RateTH_all("10000.0").Round().Print(" => rate_th\n"); //2.0 => rate_th */
  public Num RateTH_all(String all) { return Num.rate_th(this, new Num(all)); } 

   /** RateTH_all, CALCULATOR MODE: BY int ALL, RETURN THE RATE-TH OF this PERTHOUSAND */
  /**  CODE: Num pth = new Num(20); pth.RateTH_all(10000).Round().Print(" => rate_th\n"); //2.0 => rate_th */
  public Num RateTH_all(int all) { return Num.rate_th(this, new Num(all)); } 
  
   /** RateTH_all, CALCULATOR MODE: BY long ALL, RETURN THE RATE-TH OF this PERTHOUSAND */
  /**  CODE: Num pth = new Num(20); pth.RateTH_all(10000L).Round().Print(" => rate_th\n"); //2.0 => rate_th */
  public Num RateTH_all(long all) { return Num.rate_th(this, new Num(all)); } 
  
   /** RateTH_all, CALCULATOR MODE: BY BigInteger ALL, RETURN THE RATE-TH OF this PERTHOUSAND */
  /**  CODE: Num pth = new Num(20); pth.RateTH_all(new BigInteger("10000")).Round().Print(" => rate_th\n"); //2.0 => rate_th */
  public Num RateTH_all(BigInteger all) { return Num.rate_th(this, new Num(all)); } 
  
   /** RateTH_pth, CALCULATOR MODE: BY Num PERTHOUSAND, RETURN THE RATE-TH OF this ALL */
  /**  CODE: Num all = new Num(10000); all.RateTH_pth(new Num(20)).Round().Print(" => rate_th\r\n"); //2.0 => rate_th */
  public Num RateTH_pth(Num PTH) { return PTH.Shift(3).Div(this); }

   /** RateTH_pth, CALCULATOR MODE: BY String PERTHOUSAND, RETURN THE RATE-TH OF this ALL */
  /**  CODE: Num all = new Num(10000); all.RateTH_pth("20.0").Round().Print(" => rate_th\r\n"); //2.0 => rate_th */
  public Num RateTH_pth(String PTH) { return new Num(PTH).Shift(3).Div(this); }

   /** RateTH_pth, CALCULATOR MODE: BY int PERTHOUSAND, RETURN THE RATE-TH OF this ALL */
  /**  CODE: Num all = new Num(10000); all.RateTH_pth(20).Round().Print(" => rate_th\r\n"); //2.0 => rate_th */
  public Num RateTH_pth(int PTH) { return new Num(PTH).Shift(3).Div(this); }
  
   /** RateTH_pth, CALCULATOR MODE: BY long PERTHOUSAND, RETURN THE RATE-TH OF this ALL */
  /**  CODE: Num all = new Num(10000); all.RateTH_pth(20L).Round().Print(" => rate_th\r\n"); //2.0 => rate_th */
  public Num RateTH_pth(long PTH) { return new Num(PTH).Shift(3).Div(this); }
  
   /** RateTH_pth, CALCULATOR MODE: BY BigInteger PERTHOUSAND, RETURN THE RATE-TH OF this ALL */
  /**  CODE: Num all = new Num(10000); all.RateTH_pth(new BigInteger("20")).Round().Print(" => rate_th\r\n"); //2.0 => rate_th */
  public Num RateTH_pth(BigInteger PTH) { return new Num(PTH).Shift(3).Div(this); }
  
   /** All_pth, CALCULATOR MODE: BY Num PERTHOUSAND RETURN THE ALL OF this RATE-TH */ 
  /**  CODE: Num rate = new Num(2); rate.All_pth(new Num("20.0")).Round().Print(" => ALL_th\r\n"); //10000.0 => ALL_th */
  public Num All_pth(Num PTH) { return Num.all_th(this, PTH); }

   /** All_pth, CALCULATOR MODE: BY String PERTHOUSAND RETURN THE ALL OF this RATE-TH */ 
  /**  CODE: Num rate = new Num(2); rate.All_pth("20.0").Round().Print(" => ALL_th\r\n"); //10000.0 => ALL_th */
  public Num All_pth(String PTH) { return Num.all_th(this, new Num(PTH)); }

   /** All_pth, CALCULATOR MODE: BY int PERTHOUSAND RETURN THE ALL OF this RATE-TH */ 
  /**  CODE: Num rate = new Num(2); rate.All_pth(20).Round().Print(" => ALL_th\r\n"); //10000.0 => ALL_th */
  public Num All_pth(int PTH) { return Num.all_th(this, new Num(PTH)); }
  
   /** All_pth, CALCULATOR MODE: BY long PERTHOUSAND RETURN THE ALL OF this RATE-TH */ 
  /**  CODE: Num rate = new Num(2); rate.All_pth(20L).Round().Print(" => ALL_th\r\n"); //10000.0 => ALL_th */
  public Num All_pth(long PTH) { return Num.all_th(this, new Num(PTH)); }
  
   /** All_pth, CALCULATOR MODE: BY BigInteger PERTHOUSAND RETURN THE ALL OF this RATE-TH */ 
  /**  CODE: Num rate = new Num(2); rate.All_pth(new BigInteger("20")).Round().Print(" => ALL_th\r\n"); //10000.0 => ALL_th */
  public Num All_pth(BigInteger PTH) { return Num.all_th(this, new Num(PTH)); }
  
   /** All_rateTH, CALCULATOR MODE: BY Num RATE-TH RETURN THE ALL OF this PERTHOUSAND */ 
  /**  CODE: Num pth = new Num(20); pth.All_rateTH(new Num(2)).Round().Print(" => ALL_th\r\n"); //10000.0 => ALL_th */ 
  public Num All_rateTH(Num rate_th) { return this.Shift(3).Div(rate_th); }

   /** All_rateTH, CALCULATOR MODE: BY String RATE-TH RETURN THE ALL OF this PERTHOUSAND */ 
  /**  CODE: Num pth = new Num(20); pth.All_rateTH("2.0").Round().Print(" => ALL_th\r\n"); //10000.0 => ALL_th */ 
  public Num All_rateTH(String rate_th) { return this.Shift(3).Div(rate_th); }

   /** All_rateTH, CALCULATOR MODE: BY int RATE-TH RETURN THE ALL OF this PERTHOUSAND */ 
  /**  CODE: Num pth = new Num(20); pth.All_rateTH(2).Round().Print(" => ALL_th\r\n"); //10000.0 => ALL_th */ 
  public Num All_rateTH(int rate_th) { return this.Shift(3).Div(rate_th); }
  
   /** All_rateTH, CALCULATOR MODE: BY long RATE-TH RETURN THE ALL OF this PERTHOUSAND */ 
  /**  CODE: Num pth = new Num(20); pth.All_rateTH(2L).Round().Print(" => ALL_th\r\n"); //10000.0 => ALL_th */ 
  public Num All_rateTH(long rate_th) { return this.Shift(3).Div(rate_th); }
  
   /** All_rateTH, CALCULATOR MODE: BY BigInteger RATE-TH RETURN THE ALL OF this PERTHOUSAND */ 
  /**  CODE: Num pth = new Num(20); pth.All_rateTH(new BigInteger("2")).Round().Print(" => ALL_th\r\n"); //10000.0 => ALL_th */ 
  public Num All_rateTH(BigInteger rate_th) { return this.Shift(3).Div(rate_th); }
  
    /** F_price_over, ADD OR SUB Num PERCENTAGE VALUE TO this PRICE */
   /**  CODE: Num price = new Num(1000); Num overPrice = price.F_price_over(new Num(22));  overPrice.Print("\r\n"); //1220.0 */
  /**   CODE: Num price = new Num(1000); Num subPrice  = price.F_price_over(new Num(-22)); subPrice.Print("\r\n"); //780.0 */
  public Num F_price_over(Num t) { Num THIS = new Num(this.Mul(t).Shift(-2).Add(this)); return THIS; }

    /** F_price_over, ADD OR SUB String PERCENTAGE VALUE TO this PRICE */
   /**  CODE: Num price = new Num(1000); Num overPrice = price.F_price_over("22.5");   overPrice.Print("\r\n"); //1225.0 */
  /**   CODE: Num price = new Num(1000); Num subPrice  = price.F_price_over("-21.75"); subPrice.Print("\r\n"); //782.5 */
  public Num F_price_over(String t) { Num THIS = new Num(this.Mul(t).Shift(-2).Add(this)); return THIS; }

    /** F_price_over, ADD OR SUB int PERCENTAGE VALUE TO this PRICE */
   /**  CODE: Num price = new Num(1000); Num overPrice = price.F_price_over(22);  overPrice.Print("\r\n");  //1220.0 */
  /**   CODE: Num price = new Num(1000); Num subPrice  = price.F_price_over(-21); subPrice.Print("\r\n");  //790.0 */
  public Num F_price_over(int t) { Num THIS = new Num(this.Mul(t).Shift(-2).Add(this)); return THIS; }
  
    /** F_price_over, ADD OR SUB long PERCENTAGE VALUE TO this PRICE */
   /**  CODE: Num price = new Num(1000); Num overPrice = price.F_price_over(22L);  overPrice.Print("\r\n");  //1220.0 */
  /**   CODE: Num price = new Num(1000); Num subPrice  = price.F_price_over(-21L); subPrice.Print("\r\n");  //790.0 */
  public Num F_price_over(long t) { Num THIS = new Num(this.Mul(t).Shift(-2).Add(this)); return THIS; }
  
    /** F_price_over, ADD OR SUB BigInteger PERCENTAGE VALUE TO this PRICE */
   /**  CODE: Num price = new Num(1000); Num overPrice = price.F_price_over(new BigInteger("22"));  overPrice.Print("\r\n");  //1220.0 */
  /**   CODE: Num price = new Num(1000); Num subPrice  = price.F_price_over(new BigInteger("-21")); subPrice.Print("\r\n");  //790.0 */
  public Num F_price_over(BigInteger t) { Num THIS = new Num(this.Mul(t).Shift(-2).Add(this)); return THIS; }
  
   /** F_price_spinoff, SPIN-OFF Num PERCENTAGE TAX VALUE FROM this PRICE */  
  /**  CODE: Num priceRaw = new Num(100).F_price_spinoff(new Num(22)).Round(2); priceRaw.Print("\r\n"); //81.97 */ 
  public Num F_price_spinoff(Num t) { Num THIS = new Num(this.Div((t.Add(100).Shift(-2)))); return THIS; }

   /** F_price_spinoff, SPIN-OFF String PERCENTAGE TAX VALUE FROM this PRICE */  
  /**  CODE: Num priceRaw = new Num(100).F_price_spinoff("22.0").Round(2); priceRaw.Print("\r\n"); //81.97 */ 
  public Num F_price_spinoff(String t) { Num THIS = new Num(this.Div((new Num(t).Add(100).Shift(-2)))); return THIS; }

   /** F_price_spinoff, SPIN-OFF int PERCENTAGE TAX VALUE FROM this PRICE */  
  /**  CODE: Num priceRaw = new Num(100).F_price_spinoff(22).Round(2); priceRaw.Print("\r\n"); //81.97 */ 
  public Num F_price_spinoff(int t) { Num THIS = new Num(this.Div((new Num(t).Add(100).Shift(-2)))); return THIS; }
  
   /** F_price_spinoff, SPIN-OFF long PERCENTAGE TAX VALUE FROM this PRICE */  
  /**  CODE: Num priceRaw = new Num(100).F_price_spinoff(22L).Round(2); priceRaw.Print("\r\n"); //81.97 */ 
  public Num F_price_spinoff(long t) { Num THIS = new Num(this.Div((new Num(t).Add(100).Shift(-2)))); return THIS; }
  
   /** F_price_spinoff, SPIN-OFF BigInteger PERCENTAGE TAX VALUE FROM this PRICE */  
  /**  CODE: Num priceRaw = new Num(100).F_price_spinoff(new BigInteger("22")).Round(2); priceRaw.Print("\r\n"); //81.97 */ 
  public Num F_price_spinoff(BigInteger t) { Num THIS = new Num(this.Div((new Num(t).Add(100).Shift(-2)))); return THIS; }
  
   /** F_perf, PERCENTAGE PERFORMANCE VALUE (DIRECT RATIO) BY Num */
  /**  CODE: Num a = new Num(50); Num b = new Num(75); a.F_perf(b).Print("\r\n"); //50.0 */
  public Num F_perf(Num sob) { return (sob.Sub(this)).Div(this).Shift(2); }

   /** F_perf, PERCENTAGE PERFORMANCE VALUE (DIRECT RATIO) BY String */
  /**  CODE: new Num(50).F_perf("75.0").Print("\r\n"); //50.0 */
  public Num F_perf(String sob) { return (new Num(sob).Sub(this)).Div(this).Shift(2); }

   /** F_perf, PERCENTAGE PERFORMANCE VALUE (DIRECT RATIO) BY int */
  /**  CODE: new Num(50).F_perf(75).Print("\r\n"); //50.0 */
  public Num F_perf(int sob) { return (new Num(sob).Sub(this)).Div(this).Shift(2); }
  
   /** F_perf, PERCENTAGE PERFORMANCE VALUE (DIRECT RATIO) BY long */
  /**  CODE: new Num(50).F_perf(75L).Print("\r\n"); //50.0 */
  public Num F_perf(long sob) { return (new Num(sob).Sub(this)).Div(this).Shift(2); }
  
   /** F_perf, PERCENTAGE PERFORMANCE VALUE (DIRECT RATIO) BY BigInteger */
  /**  CODE: new Num(50).F_perf(new BigInteger("75")).Print("\r\n"); //50.0 */
  public Num F_perf(BigInteger sob) { return (new Num(sob).Sub(this)).Div(this).Shift(2); }
  
    /** F_perf_time PERCENTAGE AND RELATIVE MAGNITUDE ORDER TIME PERFORMANCE VALUE (INVERSE RATIO) BY Num */
   /**  RETURN ARRAY BY TWO ELEMENTS */
  /**   CODE: Num A[] = new Num(50).F_perf_time(new Num("37.5")); A[0].Round().Print("\r\n"); A[1].Round(2).Print("\r\n"); //33.33 0.33 */
  public Num[] F_perf_time(Num sob) {
    Num[] A = new Num[2];
    Num THIS= new Num(this);
    Num R = ((THIS.Sub(sob)).Div(sob).Mul(100));
    if(sob.GT(THIS) == true) { A[0] = R; A[1] = sob.Invsign().Div(THIS).Add(1); return A;
    } else { A[0] = R; A[1] = THIS.Div(sob).Sub(1); return A; }
  }

    /** F_perf_time PERCENTAGE AND RELATIVE MAGNITUDE ORDER TIME PERFORMANCE VALUE (INVERSE RATIO) BY String */
   /**  RETURN ARRAY BY TWO ELEMENTS */
  /**   CODE: Num A[] = new Num(50).F_perf_time("37.5"); A[0].Round().Print("\r\n"); A[1].Round(2).Print("\r\n"); //33.33 0.33 */
  public Num[] F_perf_time(String sob) { Num SOB = new Num(sob); return this.F_perf_time(SOB); }
  
    /** F_perf_time PERCENTAGE AND RELATIVE MAGNITUDE ORDER TIME PERFORMANCE VALUE (INVERSE RATIO) BY int */
   /**  RETURN ARRAY BY TWO ELEMENTS */
  /**   CODE: Num A[] = new Num(50).F_perf_time(38); A[0].Round().Print("\r\n"); A[1].Round(2).Print("\r\n"); //31.58 0.32 */
  public Num[] F_perf_time(int sob) { Num SOB = new Num(sob); return this.F_perf_time(SOB); }
  
    /** F_perf_time PERCENTAGE AND RELATIVE MAGNITUDE ORDER TIME PERFORMANCE VALUE (INVERSE RATIO) BY long */
   /**  RETURN ARRAY BY TWO ELEMENTS */
  /**   CODE: Num A[] = new Num(50).F_perf_time(38L); A[0].Round().Print("\r\n"); A[1].Round(2).Print("\r\n"); //31.58 0.32 */
  public Num[] F_perf_time(long sob) { Num SOB = new Num(sob); return this.F_perf_time(SOB); }
  
    /** F_perf_time PERCENTAGE AND RELATIVE MAGNITUDE ORDER TIME PERFORMANCE VALUE (INVERSE RATIO) BY BigInteger */
   /**  RETURN ARRAY BY TWO ELEMENTS */
  /**   CODE: Num A[] = new Num(50).F_perf_time(new BigInteger("38")); A[0].Round().Print("\r\n"); A[1].Round(2).Print("\r\n"); //31.58 0.32 */
  public Num[] F_perf_time(BigInteger sob) { Num SOB = new Num(sob); return this.F_perf_time(SOB); }
  
   /** sqrt, SQUARE ROOT METHOD BY Num */
  /**  CODE: Num.print(new Num("3.14").Sqrt(new Num(50)), "\r\n"); //1.77200451466693504019911250975363152507360851616294 */
  public Num Sqrt(Num d) { return Num.sqrt(this, d.toInt()); }

   /** sqrt, SQUARE ROOT METHOD BY String */
  /**  CODE: Num.print(new Num("3.14").Sqrt("50"), "\r\n"); //1.77200451466693504019911250975363152507360851616294 */
  public Num Sqrt(String d) { return Num.sqrt(this, Integer.parseInt(d)); }
  
   /** sqrt, SQUARE ROOT METHOD BY int */
  /**  CODE: Num.print(new Num("3.14").Sqrt(50), "\r\n"); //1.77200451466693504019911250975363152507360851616294 */
  public Num Sqrt(int d) { return Num.sqrt(this, d); }
  
   /** sqrt, SQUARE ROOT METHOD BY long */
  /**  CODE: Num.print(new Num("3.14").Sqrt(50L), "\r\n"); //1.77200451466693504019911250975363152507360851616294 */
  public Num Sqrt(long d) { return Num.sqrt(this, (int) d); }
  
   /** sqrt, SQUARE ROOT METHOD BY BigInteger */
  /**  CODE: Num.print(new Num("3.14").Sqrt(new BigInteger("50")), "\r\n"); //1.77200451466693504019911250975363152507360851616294 */
  public Num Sqrt(BigInteger d) { return Num.sqrt(this, (int) d.intValue()); }
  
   /** sqrt, SQUARE ROOT METHOD -DEFAULT PRECISION TEN */
  /**  CODE: Num.print(new Num("3.14").Sqrt(), "\r\n"); //1.7720045146 */
  public Num Sqrt() { return Num.sqrt(this, 10); }

   /** Is_perfectSquare, PERFECT SQUARE METHOD -DEFAULT PRECISION TEN */
  /**  CODE: Num.print(new Num(9).Is_perfectSquare(), "\r\n"); //true */
  public boolean Is_perfectSquare() { return Num.is_perfectSquare(this); }
  
   /** is_perfectSquare, PERFECT SQUARE METHOD BY int */
  /**  CODE: Num.print(new Num("1.999999999999731161391129").Is_perfectSquare(12), "\r\n"); //true */
  public boolean Is_perfectSquare(int d) { return Num.is_perfectSquare(this, d); }

     /** Hypot, PYTHAGOREAN THEOREM BY Num -DEFAULT PRECISION TEN */
  	/**  CODE: Num a = new Num(3); Num.print(a.Hypot(new Num(4)), "\r\n"); //5.0 */
    public Num Hypot(Num b) { return Num.hypot(this, b); }
    
     /** Hypot, PYTHAGOREAN THEOREM BY String -DEFAULT PRECISION TEN */
    /**  CODE: Num a = new Num(3); Num.print(a.Hypot("4.0"), "\r\n"); //5.0 */
    public Num Hypot(String b) { return Num.hypot(this, new Num(b)); }
    
     /** Hypot, PYTHAGOREAN THEOREM BY int -DEFAULT PRECISION TEN */
    /**  CODE: Num a = new Num(3); Num.print(a.Hypot(4), "\r\n"); //5.0 */
    public Num Hypot(int b) { return Num.hypot(this, new Num(b)); }
    
     /** Hypot, PYTHAGOREAN THEOREM BY long -DEFAULT PRECISION TEN */
    /**  CODE: Num a = new Num(3); Num.print(a.Hypot(4L), "\r\n"); //5.0 */
    public Num Hypot(long b) { return Num.hypot(this, new Num(b)); }
    
     /** Hypot, PYTHAGOREAN THEOREM BY long -DEFAULT PRECISION TEN */
    /**  CODE: Num a = new Num(3); Num.print(a.Hypot(new BigInteger("4")), "\r\n"); //5.0 */
    public Num Hypot(BigInteger b) { return Num.hypot(this, new Num(b)); }
    
     /** Hypot, PYTHAGOREAN THEOREM BY Num, int */
    /**  CODE: Num a = new Num(4); Num.print(a.Hypot(new Num(5), 6), "\r\n"); //6.403124 */
    public Num Hypot(Num b, int d) { return Num.hypot(this, b, d); }
    
     /** Hypot, PYTHAGOREAN THEOREM BY String, int */
    /**  CODE: Num a = new Num(4); Num.print(a.Hypot("5.0", 6), "\r\n"); //6.403124 */
    public Num Hypot(String b, int d) { return Num.hypot(this, new Num(b), d); }

     /** Hypot, PYTHAGOREAN THEOREM BY int, int */
    /**  CODE: Num a = new Num(4); Num.print(a.Hypot(5, 6), "\r\n"); //6.403124 */
    public Num Hypot(int b, int d) { return Num.hypot(this, new Num(b), d); }
    
     /** Hypot, PYTHAGOREAN THEOREM BY long, int */
    /**  CODE: Num a = new Num(4); Num.print(a.Hypot(5L, 6), "\r\n"); //6.403124 */
    public Num Hypot(long b, int d) { return Num.hypot(this, new Num(b), d); }
    
     /** Hypot, PYTHAGOREAN THEOREM BY BigInteger, int */
    /**  CODE: Num a = new Num(4); Num.print(a.Hypot(new BigInteger("5"), 6), "\r\n"); //6.403124 */
    public Num Hypot(BigInteger b, int d) { return Num.hypot(this, new Num(b), d); }
    
     /** sqrt_check, CHECK SQUARE ROOT OPERATION BY Num */
    /**  CODE: Num a = new Num("6540003265.5950400032"); Num.print(a, " = a\r\n"); Num r = a.Sqrt(a.get_L_n1() * 2); Num.print(r, " = r\r\n"); Num.print(a.Sqrt_check(r), "\r\n"); //6540003265.5950400032 = a  80870.28666695228182063467 = r  true */ 
    public boolean Sqrt_check(Num r) { return this.Sqrt_check(r.toString()); }
    
     /** sqrt_check, CHECK SQUARE ROOT OPERATION BY String */
    /**  CODE:  Num a = new Num("3.0"); Num.print(a.Sqrt_check("1.73"), "\r\n"); //true */
    public boolean Sqrt_check(String r) { Num R = new Num(r); Num R2 = R.Mul(R).Round_ceil(this.L_n1); return R2.EQ(this); }
    
     /** Root_i, CALCULATOR MODE: ITH ROOT METHOD BY Num */ 
    /**  CODE: Num a = new Num("3125.0"); Num.print(a.Root_i(new Num(5)), "\r\n"); //5.0 */ 
    public Num Root_i(Num I) { return Num.root_i(this, I.toInt(), 10); }
  
     /** Root_i, CALCULATOR MODE: ITH ROOT METHOD BY String */ 
    /**  CODE: Num a = new Num("3125.0"); Num.print(a.Root_i("5.0"), "\r\n"); //5.0 */ 
    public Num Root_i(String I) { return Num.root_i(this, new Num(I).toInt(), 10); }
    
     /** Root_i, CALCULATOR MODE: ITH ROOT METHOD BY int */ 
    /**  CODE: Num a = new Num("3125.0"); Num.print(a.Root_i(5), "\r\n"); //5.0 */ 
    public Num Root_i(int I) { return Num.root_i(this, I, 10); }
    
     /** Root_i, CALCULATOR MODE: ITH ROOT METHOD BY long */ 
    /**  CODE: Num a = new Num("3125.0"); Num.print(a.Root_i(5L), "\r\n"); //5.0 */ 
    public Num Root_i(long I) { return Num.root_i(this, (int) I, 10); }
    
     /** Root_i, CALCULATOR MODE: ITH ROOT METHOD BY BigInteger */ 
    /**  CODE: Num a = new Num("3125.0"); Num.print(a.Root_i(new BigInteger("5")), "\r\n"); //5.0 */ 
    public Num Root_i(BigInteger I) { return Num.root_i(this, I.intValue(), 10); }
    
     /** Root_i, CALCULATOR MODE: ITH ROOT METHOD BY Num, int */ 
    /**  CODE: Num a = new Num("3125.000005"); Num.print(a.Root_i(new Num(5), 30), "\r\n"); //5.000000001599999998976000000983 */
    public Num Root_i(Num I, int d) { return Num.root_i(this, new Num(I).toInt(), d); }
  
     /** Root_i, CALCULATOR MODE: ITH ROOT METHOD BY String, int */ 
    /**  CODE: Num a = new Num("3125.000005"); Num.print(a.Root_i("5.0", 30), "\r\n"); //5.000000001599999998976000000983 */
    public Num Root_i(String I, int d) { return Num.root_i(this, new Num(I).toInt(), d); }
    
    /** Root_i, CALCULATOR MODE: ITH ROOT METHOD BY int, int */ 
    /**  CODE: Num a = new Num("3125.000005"); Num.print(a.Root_i(5, 30), "\r\n"); //5.000000001599999998976000000983 */
    public Num Root_i(int I, int d) { return Num.root_i(this, I, d); }
    
     /** Root_i, CALCULATOR MODE: ITH ROOT METHOD BY long, int */ 
    /**  CODE: Num a = new Num("3125.000005"); Num.print(a.Root_i(5L, 30), "\r\n"); //5.000000001599999998976000000983 */
    public Num Root_i(long I, int d) { return Num.root_i(this, (int) I, d); }
    
     /** Root_i, CALCULATOR MODE: ITH ROOT METHOD BY BigInteger, int */ 
    /**  CODE: Num a = new Num("3125.000005"); Num.print(a.Root_i(new BigInteger("5"), 30), "\r\n"); //5.000000001599999998976000000983 */
    public Num Root_i(BigInteger I, int d) { return Num.root_i(this, I.intValue(), d); }
    
     /** Cube_root, CALCULATOR MODE: CUBE ROOT METHOD */ 
    /**  CODE: Num a = new Num("27.0"); Num.print(a.Cube_root(), "\r\n"); //3.0 */ 
    public Num Cube_root() { return Num.root_i(this, 3, 10); }

     /** Cube_root, CALCULATOR MODE: CUBE ROOT METHOD BY int */ 
    /** CODE: Num a = new Num("27.00000003"); Num.print(a.Cube_root(32), "\r\n"); //3.00000000111111111069958847762028 */
    public Num Cube_root(int d) { return Num.root_i(this, 3, d); }

     /** Cube_root, CALCULATOR MODE: CUBE ROOT METHOD BY long */ 
    /** CODE: Num a = new Num("27.00000003"); Num.print(a.Cube_root(32L), "\r\n"); //3.00000000111111111069958847762028 */
    public Num Cube_root(long d) { return Num.root_i(this, 3, (int) d); }
    
     /** Cube_root, CALCULATOR MODE: CUBE ROOT METHOD BY BigInteger */ 
    /** CODE: Num a = new Num("27.00000003"); Num.print(a.Cube_root(new BigInteger("32")), "\r\n"); //3.00000000111111111069958847762028 */
    public Num Cube_root(BigInteger d) { return Num.root_i(this, 3, d.intValue()); }
    
     /** Andb, BITWISE OPERATOR BY Num */
    /**  CODE: Num a = new Num("255.0"); Num b = new Num("1.0"); Num.print(a.Andb(b), "\r\n"); //1.0 */
    public Num Andb(Num sob) { 
      if(!this.Is_numint() || this.n2.equals("-")) throw new IllegalArgumentException("Num.Andb => TypeError only positive integer allowed: " + this.n);
      if(!sob.Is_numint()  || sob.n2.equals("-"))  throw new IllegalArgumentException("Num.Andb => TypeError only positive integer allowed: " + sob.n);
      return new Num(new BigInteger(this.n0).and(new BigInteger(sob.n0)));
    } 
  
     /** Andb, BITWISE OPERATOR BY String */
    /**  CODE: Num a = new Num("255.0"); Num.print(a.Andb("1.0"), "\r\n"); //1.0 */
    public Num Andb(String n) { 
      Num sob = new Num(n);
      if(!this.Is_numint() || this.n2.equals("-")) throw new IllegalArgumentException("Num.Andb => TypeError only positive integer allowed: " + this.n);
      if(!sob.Is_numint()  || sob.n2.equals("-"))  throw new IllegalArgumentException("Num.Andb => TypeError only positive integer allowed: " + sob.n);
      return new Num(new BigInteger(this.n0).and(new BigInteger(sob.n0)));
    } 
  
     /** Andb, BITWISE OPERATOR BY int */
    /**  CODE: Num a = new Num("255.0"); Num.print(a.Andb(1), "\r\n"); //1.0 */
    public Num Andb(int n) { 
      Num sob = new Num(n);
      if(!this.Is_numint() || this.n2.equals("-")) throw new IllegalArgumentException("Num.Andb => TypeError only positive integer allowed: " + this.n);
      if(!sob.Is_numint()  || sob.n2.equals("-"))  throw new IllegalArgumentException("Num.Andb => TypeError only positive integer allowed: " + sob.n);
      return new Num(new BigInteger(this.n0).and(new BigInteger(sob.n0)));
    } 
  
     /** Andb, BITWISE OPERATOR BY long */
    /**  CODE: Num a = new Num("255.0"); Num.print(a.Andb(1L), "\r\n"); //1.0 */
    public Num Andb(long n) { 
      Num sob = new Num(n);
      if(!this.Is_numint() || this.n2.equals("-")) throw new IllegalArgumentException("Num.Andb => TypeError only positive integer allowed: " + this.n);
      if(!sob.Is_numint()  || sob.n2.equals("-"))  throw new IllegalArgumentException("Num.Andb => TypeError only positive integer allowed: " + sob.n);
      return new Num(new BigInteger(this.n0).and(new BigInteger(sob.n0)));
    } 
  
     /** Andb, BITWISE OPERATOR BY BigInteger */
    /**  CODE: Num a = new Num("255.0"); Num.print(a.Andb(new BigInteger("1")), "\r\n"); //1.0 */
    public Num Andb(BigInteger n) { 
      Num sob = new Num(n);
      if(!this.Is_numint() || this.n2.equals("-")) throw new IllegalArgumentException("Num.Andb => TypeError only positive integer allowed: " + this.n);
      if(!sob.Is_numint()  || sob.n2.equals("-"))  throw new IllegalArgumentException("Num.Andb => TypeError only positive integer allowed: " + sob.n);
      return new Num(new BigInteger(this.n0).and(new BigInteger(sob.n0)));
    } 
  
     /** Orb, BITWISE OPERATOR BY Num */
    /**  CODE: Num a = new Num("0.0"); Num b = new Num("255.0"); Num.print(a.Orb(b), "\r\n"); //255.0 */
    public Num Orb(Num sob) { 
      if(!this.Is_numint() || this.n2.equals("-")) throw new IllegalArgumentException("Num.Orb => TypeError only positive integer allowed: " + this.n);
      if(!sob.Is_numint()  ||  sob.n2.equals("-")) throw new IllegalArgumentException("Num.Orb => TypeError only positive integer allowed: " + sob.n);
      return new Num(new BigInteger(this.n0).or(new BigInteger(sob.n0)));
    } 

     /** Orb, BITWISE OPERATOR BY String */
    /**  CODE: Num a = new Num("0.0"); Num.print(a.Orb("255.0"), "\r\n"); //255.0 */
    public Num Orb(String n) { 
      Num sob = new Num(n);
      if(!this.Is_numint() || this.n2.equals("-")) throw new IllegalArgumentException("Num.Orb => TypeError only positive integer allowed: " + this.n);
      if(!sob.Is_numint()  ||  sob.n2.equals("-")) throw new IllegalArgumentException("Num.Orb => TypeError only positive integer allowed: " + sob.n);
      return new Num(new BigInteger(this.n0).or(new BigInteger(sob.n0)));
    } 

     /** Orb, BITWISE OPERATOR BY int */
    /**  CODE: Num a = new Num("0.0"); Num.print(a.Orb(255), "\r\n"); //255.0 */
    public Num Orb(int n) { 
      Num sob = new Num(n);
      if(!this.Is_numint() || this.n2.equals("-")) throw new IllegalArgumentException("Num.Orb => TypeError only positive integer allowed: " + this.n);
      if(!sob.Is_numint()  ||  sob.n2.equals("-")) throw new IllegalArgumentException("Num.Orb => TypeError only positive integer allowed: " + sob.n);
      return new Num(new BigInteger(this.n0).or(new BigInteger(sob.n0)));
    } 

     /** Orb, BITWISE OPERATOR BY long */
    /**  CODE: Num a = new Num("0.0"); Num.print(a.Orb(255L), "\r\n"); //255.0 */
    public Num Orb(long n) { 
      Num sob = new Num(n);
      if(!this.Is_numint() || this.n2.equals("-")) throw new IllegalArgumentException("Num.Orb => TypeError only positive integer allowed: " + this.n);
      if(!sob.Is_numint()  ||  sob.n2.equals("-")) throw new IllegalArgumentException("Num.Orb => TypeError only positive integer allowed: " + sob.n);
      return new Num(new BigInteger(this.n0).or(new BigInteger(sob.n0)));
    } 

     /** Orb, BITWISE OPERATOR BY BigInteger */
    /**  CODE: Num a = new Num("0.0"); Num.print(a.Orb(new BigInteger("255")), "\r\n"); //255.0 */
    public Num Orb(BigInteger n) { 
      Num sob = new Num(n);
      if(!this.Is_numint() || this.n2.equals("-")) throw new IllegalArgumentException("Num.Orb => TypeError only positive integer allowed: " + this.n);
      if(!sob.Is_numint()  ||  sob.n2.equals("-")) throw new IllegalArgumentException("Num.Orb => TypeError only positive integer allowed: " + sob.n);
      return new Num(new BigInteger(this.n0).or(new BigInteger(sob.n0)));
    } 

     /** Xorb, BITWISE OPERATOR BY Num */
    /**  CODE: Num a = new Num("255.0"); Num b = new Num("255.0"); Num.print(a.Xorb(b), "\r\n"); //0.0 */
    public Num Xorb(Num sob) { 
      if(!this.Is_numint() || this.n2.equals("-")) throw new IllegalArgumentException("Num.Xorb => TypeError only positive integer allowed: " + this.n);
      if(!sob.Is_numint()  || sob.n2.equals("-"))  throw new IllegalArgumentException("Num.Xorb => TypeError only positive integer allowed: " + sob.n);
      return new Num(new BigInteger(this.n0).xor(new BigInteger(sob.n0)));
    } 

     /** Xorb, BITWISE OPERATOR BY String */
    /**  CODE: Num a = new Num("255.0"); Num.print(a.Xorb("255.0"), "\r\n"); //0.0 */
    public Num Xorb(String n) { 
      Num sob = new Num(n);
      if(!this.Is_numint() || this.n2.equals("-")) throw new IllegalArgumentException("Num.Xorb => TypeError only positive integer allowed: " + this.n);
      if(!sob.Is_numint()  || sob.n2.equals("-"))  throw new IllegalArgumentException("Num.Xorb => TypeError only positive integer allowed: " + sob.n);
      return new Num(new BigInteger(this.n0).xor(new BigInteger(sob.n0)));
    } 

     /** Xorb, BITWISE OPERATOR BY int */
    /**  CODE: Num a = new Num("255.0"); Num.print(a.Xorb(255), "\r\n"); //0.0 */
    public Num Xorb(int n) { 
      Num sob = new Num(n);
      if(!this.Is_numint() || this.n2.equals("-")) throw new IllegalArgumentException("Num.Xorb => TypeError only positive integer allowed: " + this.n);
      if(!sob.Is_numint()  || sob.n2.equals("-"))  throw new IllegalArgumentException("Num.Xorb => TypeError only positive integer allowed: " + sob.n);
      return new Num(new BigInteger(this.n0).xor(new BigInteger(sob.n0)));
    } 

     /** Xorb, BITWISE OPERATOR BY long */
    /**  CODE: Num a = new Num("255.0"); Num.print(a.Xorb(255L), "\r\n"); //0.0 */
    public Num Xorb(long n) { 
      Num sob = new Num(n);
      if(!this.Is_numint() || this.n2.equals("-")) throw new IllegalArgumentException("Num.Xorb => TypeError only positive integer allowed: " + this.n);
      if(!sob.Is_numint()  || sob.n2.equals("-"))  throw new IllegalArgumentException("Num.Xorb => TypeError only positive integer allowed: " + sob.n);
      return new Num(new BigInteger(this.n0).xor(new BigInteger(sob.n0)));
    } 

     /** Xorb, BITWISE OPERATOR BY BigInteger */
    /**  CODE: Num a = new Num("255.0"); Num.print(a.Xorb(new BigInteger("255")), "\r\n"); //0.0 */
    public Num Xorb(BigInteger n) { 
      Num sob = new Num(n);
      if(!this.Is_numint() || this.n2.equals("-")) throw new IllegalArgumentException("Num.Xorb => TypeError only positive integer allowed: " + this.n);
      if(!sob.Is_numint()  || sob.n2.equals("-"))  throw new IllegalArgumentException("Num.Xorb => TypeError only positive integer allowed: " + sob.n);
      return new Num(new BigInteger(this.n0).xor(new BigInteger(sob.n0)));
    } 

         /** (~) Notb, NOT UNARY BITWISE OPERATOR FOR Num */
        /** CODE: */ 
       /** Num op1 = new Num("10.0"); */
      /** Num.print(String.format("%0" + 4 + "d", 0) + op1.toBigInt().toString(2), " => " + op1 + "\r\n"); //00001010 => 10.0 */
     /** Num op2 = op1.Notb(); */
    /** Num.print(String.format("%0" + 5 + "d", 0) + op2.toBigInt().toString(2), " => " + op2 + "\r\n"); //00000101 => 5.0 */
    public Num Notb() {
      if(!this.Is_numint() || this.n2.equals("-")) throw new IllegalArgumentException("Num.Notb => TypeError only positive integer allowed: " + this.n);
      String t = "";
      String bin = new BigInteger(this.n0).toString(2);
      for(int i = 0; i < bin.length(); i++) t += (bin.charAt(i) == '1' ? '0' : '1');
      return new Num(new BigInteger(t, 2));
    }

     /** GCD, GREATEST COMMON DIVISOR BETWEEN TWO INTEGERs BY Num */
    /**  CODE: Num a = new Num(12); Num b = new Num(8); a.GCD(b).Print("\r\n"); //4.0 */
    public Num GCD(Num sob) { 
      if(!this.Is_numint() || this.n2.equals("-")) throw new IllegalArgumentException("Num.GCD => TypeError only positive integer allowed: " + this.n);
      if(!sob.Is_numint()  ||  sob.n2.equals("-")) throw new IllegalArgumentException("Num.GCD => TypeError only positive integer allowed: " + sob.n);
      return new Num(this.toBigInt().gcd(sob.toBigInt())); 
    }

     /** GCD, GREATEST COMMON DIVISOR BETWEEN TWO INTEGERs BY String */
    /**  CODE: Num a = new Num(12); a.GCD("8.0").Print("\r\n"); //4.0 */
    public Num GCD(String n) { 
      Num sob = new Num(n);
      if(!this.Is_numint() || this.n2.equals("-")) throw new IllegalArgumentException("Num.GCD => TypeError only positive integer allowed: " + this.n);
      if(!sob.Is_numint()  ||  sob.n2.equals("-")) throw new IllegalArgumentException("Num.GCD => TypeError only positive integer allowed: " + sob.n);
      return new Num(this.toBigInt().gcd(sob.toBigInt())); 
    }

     /** GCD, GREATEST COMMON DIVISOR BETWEEN TWO INTEGERs BY int */
    /**  CODE: Num a = new Num(12); a.GCD(8).Print("\r\n"); //4.0 */
    public Num GCD(int n) { 
      Num sob = new Num(n);
      if(!this.Is_numint() || this.n2.equals("-")) throw new IllegalArgumentException("Num.GCD => TypeError only positive integer allowed: " + this.n);
      if(!sob.Is_numint()  ||  sob.n2.equals("-")) throw new IllegalArgumentException("Num.GCD => TypeError only positive integer allowed: " + sob.n);
      return new Num(this.toBigInt().gcd(sob.toBigInt())); 
    }

     /** GCD, GREATEST COMMON DIVISOR BETWEEN TWO INTEGERs BY long */
    /**  CODE: Num a = new Num(12L); a.GCD(8L).Print("\r\n"); //4.0 */
    public Num GCD(long n) { 
      Num sob = new Num(n);
      if(!this.Is_numint() || this.n2.equals("-")) throw new IllegalArgumentException("Num.GCD => TypeError only positive integer allowed: " + this.n);
      if(!sob.Is_numint()  ||  sob.n2.equals("-")) throw new IllegalArgumentException("Num.GCD => TypeError only positive integer allowed: " + sob.n);
      return new Num(this.toBigInt().gcd(sob.toBigInt())); 
    }

     /** GCD, GREATEST COMMON DIVISOR BETWEEN TWO INTEGERs BY BigInteger */
    /**  CODE: Num a = new Num(12); a.GCD(new BigInteger("8")).Print("\r\n"); //4.0 */
    public Num GCD(BigInteger n) { 
      Num sob = new Num(n);
      if(!this.Is_numint() || this.n2.equals("-")) throw new IllegalArgumentException("Num.GCD => TypeError only positive integer allowed: " + this.n);
      if(!sob.Is_numint()  ||  sob.n2.equals("-")) throw new IllegalArgumentException("Num.GCD => TypeError only positive integer allowed: " + sob.n);
      return new Num(this.toBigInt().gcd(sob.toBigInt())); 
    }

      /** Is_probablePrime, CHECK FOR Num IS PROBABLY PRIME, OR IF IT'S DEFINITELY COMPOSITE. ( 0 <= CERTAINTY <= 100) */
     /** CERTAINTY = 1: CHANCE OF ERROR = 1/2, CERTAINTY = 10: CHANCE OF ERROR = 1/1024, CERTAINTY = 100: VERY SMALL CHANCE OF ERROR */
    /** CODE: Num a = new Num(13); Num.print(a.Is_probablePrime(), "\r\n"); //true */
    public boolean Is_probablePrime() { 
      int certainty = 100;
      if(!this.Is_numint() || this.n2.equals("-")) throw new IllegalArgumentException("Num.Is_probablePrime => TypeError only positive integer allowed: " + this.n);
      return this.toBigInteger().isProbablePrime(certainty); 
    }

      /** Is_probablePrime, CHECK FOR Num IS PROBABLY PRIME, OR IF IT'S DEFINITELY COMPOSITE. ( 0 <= CERTAINTY <= 100) */
     /** CERTAINTY = 1: CHANCE OF ERROR = 1/2, CERTAINTY = 10: CHANCE OF ERROR = 1/1024, CERTAINTY = 100: VERY SMALL CHANCE OF ERROR */
    /** CODE: Num a = new Num(13); Num.print(a.Is_probablePrime(100), "\r\n"); //true */
    public boolean Is_probablePrime(int certainty) { 
      if(!this.Is_numint() || this.n2.equals("-")) throw new IllegalArgumentException("Num.Is_probablePrime => TypeError only positive integer allowed: " + this.n);
      return this.toBigInteger().isProbablePrime(certainty); 
    }

     /** PrimeNext, GENERATE NEXT PROBABLE PRIME NUMBER BY PRIME */
    /**  CODE: int bit = 8; Num a = Num.prime_gen(bit); Num.print(a, "\r\n"); Num.print(a.PrimeNext(), "\r\n"); //...173.0 ...179.0 */
    public Num PrimeNext() { return new Num(this.toBigInteger().nextProbablePrime()); }


} //END Num CLASS /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
