# num7 - SUPREME PRECISION GENERAL PURPOSE ARITHMETIC-LOGIC DECIMAL LIBRARY PACKAGE FOR JAVA
## _DESCRIPTION AND DOC_

- _**`Num`**_ is a lightweight floating point numeric class in num7/Num.java file for arbitrary precision results with always supreme precision.

Easy to use like school math and WITHOUT IEEE754 ISSUES or -0 FAILURES, it can be deployed  
for web e-commerce developing, accounting apps and general math programs included financial ones.  
Fairly portable to Python one (and vice-versa) also a jvm system can work with almost num7 capability.  

---

## Installation num7 package


- To install _**`num7 package`**_ enter the following:

  ```java
  mkdir num7 //FROM YOUR OWN App.java BASE DIRECTORY CREATE num7 FOLDER
  cp Num.java num7 //PUT Num.java FILE INSIDE num7 FOLDER
  ```

- Ok!

---


## Installation num7 package by MAVEN REPOSITORY:

	https://mvnrepository.com/artifact/io.github.giocip/num7

---

## HOW TO USE (integer numeric strings (ex. "2.0") MUST BE SUFFIXED WITH .0): 
--- CALCULATOR MODE ---   

	import num7.Num; 
	public class App { 
	 public static void main(String[] args) { 
	  /* ADDITION           */ Num.add("2.5", "3.6").Print(" = 2.5 + 3.6 (ADDITION)\n"); //6.1 = 2.5 + 3.6 (ADDITION)                 
	  /* SUBTRACTION        */ Num.sub("2.5", "3.6").Print(" = 2.5 - 3.6 (SUBTRACTION)\n"); //-1.1 = 2.5 - 3.6 (SUBTRACTION)               
	  /* MULTIPLICATION     */ Num.mul("2.5", "3.6").Print(" = 2.5 * 3.6 (MULTIPLICATION)\n"); //9.0 = 2.5 * 3.6 (MULTIPLICATION)                
	  /* DIVISION           */ Num.div("2.5", "3.6").Print(" = 2.5 / 3.6 (DIVISION)\n"); //(DIVISION) 0.69444444444444444444444444444444444444444444444444444444444444444444444444444444 = 2.5 / 3.6 
	  /* DIVISION REMAINDER */ Num.mod("11.0", "8.0").Print(" = 11 % 8 (DIVISION REMAINDER)\n"); //3.0 = 11 % 8 (DIVISION REMAINDER)               
	  /* DIVISION REMAINDER */ Num.mod("11.0", "8.0").Print(" = 11 % 8 (DIVISION REMAINDER)\n"); //3.0 = 11 % 8 (DIVISION REMAINDER)               
	  /* INV                */ Num.inv("3.0").Print(" = 1 / 3 (INV)\n");  //0.33333333333333333333333333333333333333333333333333333333333333333333333333333333 = 1 / 3 (INV)                
	  /* x2                 */ Num.x2("3.0").Print(" = 3 ^ 2 (x2)\n");   //9.0 = 3 ^ 2 (x2)
	  /* x3                 */ Num.x3("3.0").Print(" = 3 ^ 3 (x3)\n");  //27.0 = 3 ^ 3 (x3)
	  /* POWER              */ Num.xy("3.14", "8.0").Print(" = 3.14 ^ 8 (POWER)\n");  //9450.1169810786918656 = 3.14 ^ 8 (POWER)
	  /* POWER OF TEN       */ Num._10y(6).Print(" = 10^6 (TEN POWER)\n");           //1000000.0 = 10^6 (TEN POWER)
	  /* POWER OF TWO       */ Num._2y(5).Print(" = 2^5 (TWO POWER)\n");            //32.0 = 2^5 (TWO POWER)
	  /* POWER OF e         */ Num._ey(5).Round(29).Print(" = e^5 (e POWER)\n");   //148.41315910257660342111558004056 = e^5 (e POWER)
	  /* FACTORIAL          */ Num P = new Num(Num.fact(5)); P.Print(" = 5! (FACTORIAL)\n");//120.0 = 5! (FACTORIAL)
	  /* BINARY             */ Num.print(new Num(257).toBin(), " => BINARY\r\n");//100000001 => BINARY
	  /* HEXADECIMAL        */ Num.print(new Num(257).toHex(), " => HEXADECIMAL\r\n"); //101 => HEXADECIMAL
	  /* SQUARE ROOT        */ Num.sqrt("2.0").Print(" (SQUARE ROOT OF 2)\n"); 	      //1.4142135623 (SQUARE ROOT OF 2)        
	  /* ROUND 2 DIGITS     */ Num.round(Num.sqrt("2.0")).Print(" = SQUARE ROOT OF 2 (ROUND 2 DIGITS)\n");            //1.41 = SQUARE ROOT OF 2 (ROUND 2 DIGITS) 
	  /* ROUND 4 DIGITS     */ Num.round("3.14159265", 7).Print(" =~ 3.14159265 (ROUND 7 DIGITS)\n"); 	             //3.1415927 =~ 3.14159265 (ROUND 7 DIGITS)
	  /* ROUND BANKING      */ Num.round_bank("3.14159265", 7).Print(" =~ 3.14159265 (ROUND BANKING 7 DIGITS)\n");  //3.1415926 =~ 3.14159265 (ROUND BANKING 7 DIGITS)
	  /* ABS                */ Num.abs("-5.25").Print(" (ABSOLUTE VALUE OF -5.25)\n"); 		            //5.25 (ABSOLUTE VALUE OF -5.25)
	  /* SCIENTIFIC         */ Num.print(new Num("314.0e-2").toEXP()); Num.print(" (SCI) = 3.14\r\n"); //3.14e0 (SCI) = 3.14
	  /* pi                 */ Num.print(Num.pi(), " (pi)\n"); 						  //3.1415926535897932384626433832795 (pi)
	  /* e                  */ Num.print(Num.e(), " (e)\n");  						  //2.7182818284590452353602874713527 (e)
	  /* 10 TIME VALUE      */ Num._10x("5.25").Print(" (10 TIME VALUE OF 5.25)\n");                      //52.5 (10 TIME VALUE OF 5.25
	  /* 100 TIME VALUE     */ Num._100x("5.25").Print(" (100 TIME VALUE OF 5.25)\n");                   //525.0 (100 TIME VALUE OF 5.25)
	  /* 1000 TIME VALUE    */ Num._1000x("5.25").Print(" (1000 TIME VALUE OF 5.25)\n");                //5250.0 (1000 TIME VALUE OF 5.25)
	  /* DIVIDE FOR 10      */ Num._10div("5.25").Print(" (DIVISION FOR TEN OF 5.25)\n");              //0.525 (DIVISION FOR TEN OF 5.25)
	  /* DIVIDE FOR 100     */ Num._100div("5.25").Print(" (DIVISION FOR HUNDRED OF 5.25)\n");        //0.0525 (DIVISION FOR HUNDRED OF 5.25)
	  /* DIVIDE FOR 1000    */ Num._1000div("5.25").Print(" (DIVISION FOR THOUSAND OF 5.25)\n");     //0.00525 (DIVISION FOR THOUSAND OF 5.25)
	  /* PERCENTAGE         */ Num.pct("3.725", "150.00").Round(2).Print(" = 3.725% OF 150 (PERCENTAGE)\n");         //5.59 = 3.725% OF 150 (PERCENTAGE)
	  /* PERTHOUSAND        */ Num.pth("2.00", "20_000.00").Round(2).Print(" = 2PTH OF 20000 (PERTHOUSAND)\n");    //40.0 = 2PTH OF 20000 (PERTHOUSAND)
	  /* SPIN-OFF           */ Num.f_price_spinoff("1_299.00", "22.00").Round(2).Print(" = (-22%) 1299 (SPIN-OFF)\n"); 	//1064.75 = (-22%) 1299 (SPIN-OFF)
	  /* SPIN-ON            */ Num.f_price_over("1_064.75", "22.00").Round(2).Print(" = +22% OF 1064.75 (SPIN-ON)\n"); //1299.0 = +22% OF 1064.75 (SPIN-ON)
      /* HYPOT              */ Num.hypot("3.0","5.0", 40).Print("\r\n"); //5.8309518948453004708741528775455830765213
	  Num.print("----------------------\n"); //---------------------- 
	  Num[] cart = { new Num("19.31999"), new Num("19.32"), new Num("18.37"), new Num("-15.13"), new Num("-15.12") }; 
	  for (Num element : cart) Num.print(element, " "); Num.print("=> ELEMENTS\r\n"); //19.31999 19.32 18.37 -15.13 -15.12 => ELEMENTS
	  /* SUM   */ Num.print(Num.sum(cart), " => SUM\r\n");       //26.75999 => SUM
	  /* MEAN  */ Num.print(Num.mean(cart).Round(), " => MEAN\r\n"); //5.35 => MEAN
	  /* MIN   */ Num.print(Num.min(cart), " => MIN\r\n");         //-15.13 => MIN
	  /* MAX   */ Num.print(Num.max(cart), " => MAX\r\n");          //19.32 => MAX
	  /*MIN MAX*/ Num.print(Num.minmax(cart), " => MIN-MAX\r\n"); //[-15.13, 19.32] => MIN-MAX
   	  /*SUM MEAN MIN MAX*/ Num.print(Num.suminmax(cart), " => SUM-MEAN-MIN-MAX\r\n"); //[26.75999, 5.351998, -15.13, 19.32] => SUM-MEAN-MIN-MAX
	  Num.print("----------------------\n"); //---------------------- 
	  /* FORMAT  */ Num.printf("3005.141592654", 9, true, "\r\n");   //3.005,141592654
	  /* FORMAT  */ Num.printf("3005.141592654", 6, false, "\r\n"); //3,005.141593
	  /* FORMAT  */ Num.printf("3005.941592654", 0, true, "\r\n"); //3.006
	  Num.print("----------------------\n"); 
	  Num.print("*** num7.Num CHEATING TABLE FUNCTIONS LIBRARY ***\r\n");  
	 }
    }
	
## CODING:  
 
(=) assignment:  

	Num a = new Num("3.0"); Num b = new Num("5.0"); Num c = new Num("0.0"); 
	Num.print("a = ", a.toString(), "  b = ");
	Num.print(b.toString(), "  c = ", c.toString()); //a = 3.0 b = 5.0 c = 0.0 

(+) adding:  

	Num R = a.Add(b).Add(c); Num.print("\r\n", R.toString()); //8.0  
	a = new Num("0.1"); b = new Num("0.2"); c = new Num("0.0"); Num.print("\r\n", a.Add(b).Add(c).toString()); //0.3  

(-) subtracting:  

	a = new Num("0.1"); b = new Num("0.2"); c = new Num("0.3");  
	Num.print("\r\n", a.Add(b).Sub(c).toString()); //0.0  
	R = new Num("-3.99").Sub(new Num("-5.20")).Sub(new Num("+3.01")); Num.print("\r\n", R.toString()); //-1.8  
 
(*) multiplying:  

	Num.print("\r\n", new Num("-3.99").Mul(new Num("-5.20")).Mul(new Num("+3.01")).toString()); //-3.99 * (-5.20) * (+3.01 ) = new Num("62.45148")

(/) dividing (80 decimal digits default gets only for division operation):  

	Num.print("\r\n", new Num("3.0").Div(new Num("5.7")).toString()); //3 : 5.7 = new Num("0.52631578947368421052631578947368421052631578947368421052631578947368421052631578")  

Division precision (ex. 128 decs) may be specified as parameter after numeric string as: 
 	    
	Num.print("\r\n", new Num("3.0", 128).Div(new Num("5.7", 128)).toString()); //3 : 5.7 = new Num("0.52631578947368421052631578947368421052631578947368421052631578947368421052631578947368421052631578947368421052631578947368421052")  

(// % operators by divmod function) integer division and remainder:  

	a = new Num("14.0"); b = new Num("4.0"); //  
	Num QR[] = Num.divmod(a, b); Num.print("\r\nQuotient = ", QR[0].toString()); Num.print( "\r\nRemainder = ", QR[1].toString());   //Quotient = 3.0 Remainder = 2.0  

(divmod function) floating division and remainder:  

	a = new Num("10.123456789"); b = new Num("2.0"); // 
	QR = Num.divmod(a, b); Num.print("\r\nQuotient = ", QR[0].toString()); Num.print("\r\nRemainder = ", QR[1].toString());   //Quotient = 5.0 Remainder = 0.123456789  

(sqrt) square root function: 

	a = new Num("123_456_789.1234567890123456789"); Num root = a.Sqrt(); // new Num("11111.1110661111")  
	Num.print("\r\nresult digits number Array => "); Num.print(root.Len()[0], " "); Num.print(root.Len()[1]); //result digits number Array => 5 10  

(**) power pow function:  

	Num.print("\r\n");
	a = new Num("2.22123").Pow(64); Num.print(a.toString()); // 15204983311631674774944.65147209888660757554174463321311015807893679105748958794491681177995203669698667160837739445605536688871012507194541849848681968140805876570485027380472936734094801420552285940765338219588362327695177798251793912104057999943308320501195784173135380826413054938730768027747418766018606636039075568645106645889100039914241  
	Num.print("\r\n");
	Num.print(a.Len()[0], " "); Num.print(a.Len()[1]);     //(23, 320) digits len Array  
	Num.print("\r\n");
	Num.print(new Num(Num.PI).toString(), "\r\n");              //3.1415926535897932384626433832795
	Num.print(Num.pow(new Num(Num.PI), 8).toString(), "\r\n"); //9488.53101607057400712857550390669610772775299223490285321770379105987141030240149336078150385043158469349154685725483405285555113328286167973377675298803815055897927366999722001973502924844693920864092029684743210236562107994363509552636547934500390625

logic in, not in, is, is not, LT, LE, GT, GE, EQ, NE and relational operators (and, or, not).  

(in):  

	Num L[] = { new Num("0.1"), new Num("1.0"), new Num("5.5"), new Num("-3.0"), new Num("-2.9"), new Num("-3.0001"), new Num("2.2") };  
	Num.print("--------------------------\r\n");
	Num.print(Num.in(L, new Num("-3.0001")), "\r\n");   //true
	Num.print(Num.in(L, new Num("-3.00001")), "\r\n"); //false
	Num.print(Num.in(L, new Num("-3.0001")), "\r\n"); //true

(not_in):

	Num.print("--------------------------\r\n");
	Num.print(Num.not_in(L, new Num("-3.0001")), "\r\n");   //false
	Num.print(Num.not_in(L, new Num("-3.00001")), "\r\n"); //true
	Num.print(Num.not_in(L, new Num("-3.0001")), "\r\n"); //false

(is, is_not):

	Num.print("--------------------------\r\n");
	Num M = new Num("0.0"); 
	Num.print(Num.is(new Num("0.0"), M), "\r\n");     //true
	M = new Num("0.0"); 
	Num.print(Num.is_not(M.Inc("0.1"), M), "\r\n"); //false
	Num N = M; N.Dec("0.1"); 
	Num.print(Num.is(N, M), "\r\n");  	  //true
	Num.print(Num.is_not(N, M), "\r\n"); //false

 LT, LE, GT, GE, EQ, NE (< <= > >= == !=)

	Num.print("--------------------------\r\n");
	a = new Num("0.0"); b = new Num("0.1"); c = new Num("-0.2");
	Num.print(a.LT(b), " "); Num.print(a.LT(c), " "); Num.print(b.LT(c), "\r\n");    //true false false 
	Num.print(a.LE(b), " "); Num.print(a.LE(c), " "); Num.print(b.LE(c), "\r\n");   //true false false 
	Num.print(a.GT(b), " "); Num.print(a.GT(c), " "); Num.print(b.GT(c), "\r\n");  //false true true 
	Num.print(a.GE(a), " "); Num.print(a.GE(c), " "); Num.print(b.GE(c), "\r\n"); //true true true 
	Num.print(c.EQ(new Num("-2.0").Mul(b)), " "); Num.print(a.EQ(c.Add(b.Mul("2.0"))), " "); Num.print(a.NE(a.Add(b).Add(c)), "\r\n"); //true true true 
	Num.print(a.And(b), " "); Num.print(a.Or(b), " "); Num.print(a.Not(), "\r\n"); //false true true 
	Num.print(a.And(b) ? true : false, "\r\n");  //false
	Num.print(a.Or(b)  ? true : false, "\r\n"); //true

  (+ - unary operators)

	Num.print("--------------------------\r\n");
	a = new Num("2.5521"); //Num { type: "Num", n2: "", n0: "2", n1: "5521", L_n0: 1, L_n1: 4, n: "2.5521", d: 80 }
	Num.print(a.toString(), "\r\n");               //2.5521
	Num.print(a.Minus().toString(), "\r\n");      //-2.5521
	Num.print(a.Plus().toString(), "\r\n");      //2.5521
	Num.print(a.Invsign().toString(), "\r\n");  //-2.5521
	Num.print(a.Invsign().toString(), "\r\n"); //2.5521

  bitwise operators code:

	Num.print("--- (&) AND ---\r\n");
	Num op1 = new Num("3.0");
	Num op2 = new Num("5.0");
	Num.print("000000"); Num.print(op1.toBin(), " => 3.0\r\n"); //00000011 => 3.0
	op1 = op1.Andb(op2); 										//AND
	Num.print( "00000"); Num.print(op2.toBin(), " => 5.0\r\n"); //00000101 => 5.0
	Num.print("0000000");Num.print(op1.toBin(), " => 1.0\r\n"); //00000001 => 1.0

	Num.print("--- (|) OR  ---\r\n");
	Num op1 = new Num("3.0");
	Num op2 = new Num("5.0");
	Num.print("000000"); Num.print(op1.toBin(), " => 3.0\r\n"); //00000011 => 3.0
	op1 = op1.Orb(op2); 										//OR
	Num.print( "00000"); Num.print(op2.toBin(), " => 5.0\r\n"); //00000101 => 5.0
	Num.print( "00000"); Num.print(op1.toBin(), " => 7.0\r\n"); //00000111 => 7.0

	Num.print("--- (^) XOR ---\r\n");
	Num op1 = new Num("3.0");
	Num op2 = new Num("5.0");
	Num.print("000000"); Num.print(op1.toBin(), " => 3.0\r\n"); //00000011 => 3.0
	op1 = op1.Xorb(op2); 										//XOR
	Num.print( "00000"); Num.print(op2.toBin(), " => 5.0\r\n"); //00000101 => 5.0
	Num.print( "00000"); Num.print(op1.toBin(), " => 6.0\r\n"); //00000110 => 6.0

	Num.print("--- (<<) LEFT SHIFT -X10 MULTIPLIER ---\r\n");
	Num op1 = new Num("1.0");
	Num op2 = new Num("2.0");
	Num.print("0000000"); Num.print(op1.toBin(), " => 1.0\r\n");  //00000001 => 1.0
	op1 = op1.Shift(op2); 										  //LEFT SHIFT -X10 MULTIPLIER
	Num.print( "000000"); Num.print(op2.toBin(), " => 2.0\r\n");  //00000010 => 2.0
	Num.print(      "0"); Num.print(op1.toBin(), " => 100.0\r\n");//01100100 => 100.0

	Num.print("--- (>>) RIGHT SHIFT -X10 DIVIDER ---\r\n");
	Num op1 = new Num("250.0");
	Num op2 = new Num("-1.0");
	Num.print(""); Num.print(op1.toBin(), " => 250.0\r\n");                //11111010 250.0 
	op1 = op1.Shift(op2); 										           //RIGHT SHIFT -X10 DIVIDER
	Num.print( "      "); Num.print(op2.toBin(), " => -1 (decimal)\r\n");  //-1 (decimal)
	Num.print(      "000"); Num.print(op1.toBin(), " => 25.0\r\n");        //00011001 25.0

	Num.print("--- (~) NOT ---\r\n");
	Num op1 = new Num("10.0");
	Num op2 = new Num("0.0");
	Num.print("0000"); Num.print(op1.toBin(), " => 10.0\r\n"); //00001010 10.0 
	op2 = op1.Notb(); 										   //(~) NOT 
	Num.print("00000"); Num.print(op2.toBin(), " => 5.0\r\n"); //00000101 5.0  

 On a given variable the following arithmetic methods are available:
	
	Num a = new Num("10.25");  
	Num.print(a.toString(), "\r\n");           //10.25  
	a.Inc();        						  //increment (default) by one  
	Num.print(a.toString(), "\r\n");         //11.25   
	a.Dec(2);       						//decrement (optional) 2 units  
	Num.print(a.toString(), "\r\n");       //9.25  
	a.IncMul();     					  //multiply (default) 10 times  
	Num.print(a.toString(), "\r\n");     //92.5  
	a.DecDiv(100);  					//x100 (optional) division  
	Num.print(a.toString(), "\r\n");   //0.925  
	a.Clear();     					  //a variable set to zero   
	Num.print(a.toString(), "\r\n"); //0.0 

EVEN ODD numbering methods:
	
	Num a = new Num(6); Num b = new Num(3); Num c = new Num("3.14");
	Num.print(a, "  INTEGER => "); Num.print(a.Is_numint(), " "); Num.print("EVEN => ") ; Num.print(a.Is_numeven(), "\r\n");  //6.0 INTEGER => true EVEN => true  
	Num.print(b, "  INTEGER => "); Num.print(b.Is_numint(), " "); Num.print("ODD  => "); Num.print(b.Is_numodd(), "\r\n");   //3.0  INTEGER => true ODD  => true 
	Num.print(c, " FLOAT  => "); Num.print(c.Is_numfloat(), "\r\n");               										    //3.14  FLOAT   => true     

# Advanced logic programming snippet

LOOP EXAMPLE >

	Num i = new Num(0);
	while (i.LT(new Num("1.0"))) {
		i.Inc("0.1");                   //i += Num("0.1")
		if (i.LE(new Num("0.5"))) continue;
		Num.print(i.toString(), " "); //0.6 0.7 0.8 0.9 1.0  
	} 
	while (i.Is_true()) {
		i.Dec("0.1");                   //i -= Num("0.1") 
		if (i.GE(new Num("0.5"))) continue;
		Num.print(i.toString(), " "); //0.4 0.3 0.2 0.1 0.0  
	} 

ROUNDING AND ACCOUNTING >

	Num p = new Num("11.19");                         //PRICE -Toslink cable for soundbar  
	Num pd = Num.round(p.F_price_over(-7));          //PRICE DISCOUNTED 7%
	Num d = Num.round(p.Sub(pd));                   //DISCOUNT
	Num p_noTAX = Num.round(p.F_price_spinoff(22));//ITEM COST WITHOUT TAX 22%  
	Num TAX = Num.round(p.Sub(p_noTAX));          //TAX 22% 
	Num.print("price=" + pd.toString() + " discount=" + d.toString() + " COST=" + p_noTAX.toString() + " TAX=" + TAX.toString()); //price=10.41 discount=0.78 COST=9.17 TAX=2.02

OUTPUT FORMATTING AND LOCALIZATION >

	import num7.Num;
	import java.text.NumberFormat;
	import java.util.Locale;
	
	public class App {
	    public static void main(String[] args) {
			NumberFormat US = NumberFormat.getCurrencyInstance(Locale.US);
			NumberFormat IT = NumberFormat.getCurrencyInstance(Locale.ITALY);
			double amount = 1234567.89;
			Num.print(US.format(amount), "\r\n");  							 //$1,234,567.89
			Num.print(IT.format(amount), "\r\n"); 							//1.234.567,89 €
			Num asset = new Num("100_000.0"); Num rate = new Num("6.5"); Num years = new Num("20.0");
			Num monthly_payment = Num.f_fund_fr(asset, rate, years.toInt()).Round(); 
			Num.print(US.format(asset.toDouble()), "\r\n");              //$100,000.00
			Num.print(US.format(monthly_payment.toDouble()), "\r\n");   //$756.30
			Num.print(IT.format(asset.toDouble()), "\r\n"); 		   //100.000,00 €
			Num.print(IT.format(monthly_payment.toDouble()), "\r\n"); //756,30 €
	    }
	}

ROUNDING TYPES >

	import num7.Num;

	public class App {
		public static void main(String[] args) {
			//""" Num floor rounding """  
			Num.print("--------------------------\r\n" + " Num floor rounding\r\n"); 
			Num n = new Num(Num.PI);                        // 3.1415926535897932384626433832795
			Num.print(n, "\r\n"); Num.print(n.Round_floor(2).toString(), "\r\n"); // 3.14  
			n = new Num(Num.PI).Invsign();                  //-3.1415926535897932384626433832795 
			Num.print(n, "\r\n"); Num.print(n.Round_floor(2).toString(), "\r\n"); //-3.15
			n = new Num(Num.PI).Sub(3);                     // 0.1415926535897932384626433832795  
			Num.print(n, "\r\n"); Num.print(n.Round_floor(2).toString(), "\r\n"); // 0.14  
			n = new Num(Num.PI).Invsign().Add(3);           //-0.1415926535897932384626433832795  
			Num.print(n, "\r\n"); Num.print(n.Round_floor(2).toString(), "\r\n"); //-0.15  
			
			//""" Num ceil rounding """  
			Num.print("--------------------------\r\n" +  " Num ceil rounding\r\n");  
			n = new Num(Num.PI);                            // 3.1415926535897932384626433832795 
			Num.print(n, "\r\n"); Num.print(n.Round_ceil(2).toString(), "\r\n"); //3.15 
			n = new Num(Num.PI).Invsign();                  //-3.1415926535897932384626433832795  
			Num.print(n, "\r\n"); Num.print(n.Round_ceil(2).toString(), "\r\n"); //-3.14
			n = new Num(Num.PI).Sub(3);                     // 0.1415926535897932384626433832795 
			Num.print(n, "\r\n"); Num.print(n.Round_ceil(2).toString(), "\r\n"); //0.15 
			n = new Num(Num.PI).Invsign().Add(3);           //-0.1415926535897932384626433832795 
			Num.print(n, "\r\n"); Num.print(n.Round_ceil(2).toString(), "\r\n"); //-0.14 
			
			//""" Num standard rounding """  
			Num.print("--------------------------\r\n" +  " Num standard rounding\r\n");
			n = new Num(Num.PI);                            // 3.1415926535897932384626433832795 
			Num.print(n, "\r\n"); Num.print(n.Round(4).toString(), "\r\n"); //3.1416
			n = new Num(Num.PI).Invsign();                  //-3.1415926535897932384626433832795  
			Num.print(n, "\r\n"); Num.print(n.Round(4).toString(), "\r\n"); //-3.1416
			n = new Num(Num.PI).Sub(3);                     // 0.1415926535897932384626433832795 
			Num.print(n, "\r\n"); Num.print(n.Round(4).toString(), "\r\n"); //0.1416
			n = new Num(Num.PI).Invsign().Add(3);           //-0.1415926535897932384626433832795 
			Num.print(n, "\r\n"); Num.print(n.Round(4).toString(), "\r\n"); //-0.1416 
			
			//""" Num half even rounding """  
			Num.print("--------------------------\r\n" +   " Num half to even rounding (statistic, zero symmetric)\r\n");  
			n = new Num(Num.PI).Round_floor(4) ;             						// 3.1415  
			Num.print(n, "\r\n"); Num.print(n.Round_bank(3).toString(), "\r\n") ;   // 3.142  
			n = new Num(Num.PI).Round_floor(4).Invsign(); 							//-3.1415  
			Num.print(n, "\r\n"); Num.print(n.Round_bank(3).toString(), "\r\n");    //-3.142  
			n = new Num(Num.PI).Sub(3).Round_floor(8);              				// 0.14159265  
			Num.print(n, "\r\n"); Num.print(n.Round_bank(7).toString(), "\r\n");    // 0.1415926  
			n = new Num(Num.PI).Round_floor(8).Invsign().Add(3); 					//-0.14159265  
			Num.print(n, "\r\n");Num.print(n.Round_bank(7).toString(), "\r\n");     //-0.1415926  
		}
	}

PERFORMANCE EVALUATION AND SQUARENESS >

	import num7.Num;

	public class App {
		public static void main(String[] args) {

					long tic = System.nanoTime();             //Start Time 
					Num a = new Num("-1.123456789"+"e-100"); //Calculating division 10**100 assignment time by exponential notation... 
					long toc = System.nanoTime();           //Stop Time
					Num T1 = new Num(((toc - tic) / 1000000.0) + ""); 
					Num.print("a finished ms "); Num.print(T1.Round(3), "\r\n"); //a finished ms 43.476
								
					tic = System.nanoTime();                      //Start Time
					Num b = new Num("-1.123456789").Shift(-100); //Calculating division 10**100 assignment time by shift method...
					toc = System.nanoTime();                    //Stop Time           
					Num T2 = new Num(((toc - tic) / 1000000.0) + "");
					Num.print("b finished ms "); Num.print(T2.Round(3), "\r\n"); //b finished ms 4.124
					
					
					Num[] R = Num.f_perf_time(T1, T2);
					
					Num.print("PCT => ", Num.round(R[0]).toString(), "  ");  
					Num.print("SCALE => ", Num.round(R[1]).toString(), "  "); Num.print( "SQUARENESS => ");
					Num.print(a.EQ(b), "\r\n"); //PCT => 974.46  SCALE => 9.74  SQUARENESS => true
					
					
					//stock exchange assets performance 
					Num previous = new Num("26.96"); Num now = new Num("27.27"); 
					Num var_pct = new Num(Num.f_perf(previous, now)).Round();
					Num.print((var_pct.GT(0) ? "+" : ""));
					Num.print(var_pct.Round(2), "\r\n"); //+1.15
		}
	} 

 SCIENTIFIC NOTATION AND HIGH PRECISION RESULTS >

	import num7.Num;

	public class App {
		public static void main(String[] args) {

					Num a = new Num("1_000_000_000_000_000_000_000.0"); //standard notation  
					Num b = new Num("1.0e21");                         //scientific notation  
					Num SUM = a.Add(b);                               //SUM  
					double ieee754 = a.toFloat() + b.toFloat();      //double
					Num.print("SUM == ieee754 "); Num.print(SUM.EQ(new Num(ieee754 + "")), "   "); 
					Num.print( "SUM => ", SUM.Num2exp());          //SUM == ieee754 true   SUM => 2.0e21

					a = new Num("1_000_000_000_000_000_000_000.0"); //standard notation  
					b = new Num("1.0e21");                         //scientific notation  
					Num MUL = a.Mul(b);                           //MUL  
					ieee754 = a.toFloat() * b.toFloat();         //double
					Num.print("\r\nMUL == ieee754 ");
					Num.print(MUL.EQ(new Num(ieee754 + "")), "   MUL => "); 
					Num.print(MUL.Num2exp()); //MUL == ieee754 true   MUL => 1.0e42
									
					String as = "1.23456789";  
					String bs = "9.87654321" ; 
					MUL = new Num(as).Mul(new Num(bs)); //MUL                        
					ieee754 = Double.parseDouble(as) * Double.parseDouble(bs);
					Num.print("\r\nMUL == ieee754 ");
					Num.print(MUL.EQ(new Num(ieee754 + "")), "  MUL => " + MUL.toString() + "\r\n                             "); 
					Num.print(Double.parseDouble(as)*Double.parseDouble(bs), " => IEEE754 PRECISION FAILURE!"); //MUL == ieee754 False MUL => 12.1932631112635269 12.193263111263525 => IEEE754 PRECISION FAILURE!  
									
					as = "1.23456789e320";  //scientific notation  
					bs = "9.87654321e320";   
					MUL = new Num(as).Mul(new Num(bs)); //MUL                      
					ieee754 = Double.parseDouble(as) * Double.parseDouble(bs);
					Num.print("\r\nMUL != ieee754 ");				
					Num.print("MUL => ", MUL.Num2exp() + " ");
					Num.print(Double.parseDouble(as)*Double.parseDouble(bs), " => IEEE754 Infinity FAILURE!"); //MUL != ieee754 MUL => 1.21932631112635269e641 Infinity => IEEE754 Infinity FAILURE!

					as = "2.0e320";  //scientific notation  
					bs = "3.0e-320";   
					MUL = new Num(as).Mul(new Num(bs)); //MUL                      
					ieee754 = Double.parseDouble(as) * Double.parseDouble(bs);
					Num.print("\r\nMUL != ieee754 ");				
					Num.print("MUL => ", MUL + " ");
					Num.print(Double.parseDouble(as)*Double.parseDouble(bs), " => IEEE754 Infinity FAILURE!"); //MUL != ieee754 MUL => 6.0 Infinity => IEEE754 Infinity FAILURE!
					
					as = "1.0e200"; //scientific notation  
					bs = "5.0e1200";  
					Num T1 = new Num(as); 
					Num T2 = new Num(bs); 
					Num DIV = T1.Div(T2); //DIV
					ieee754 = Double.parseDouble(as) / Double.parseDouble(bs); //DIVISION
					Num.print("\r\nDIV != ieee754 ");				
					Num.print("DIV => ", DIV.toEXP() + " ");
					Num.print(Double.parseDouble(as)/Double.parseDouble(bs), " => IEEE754 Infinity small FAILURE!"); //DIV != ieee754 DIV => 2.0e-1001 0.0 => IEEE754 Infinity small FAILURE!
					
		}
	}

 SAVING CALCULATOR:

	import num7.Num;
	import java.text.NumberFormat;
	import java.util.Locale;

	public class App {
		public static void main(String[] args) {

			NumberFormat US = NumberFormat.getCurrencyInstance(Locale.US);
			
			Num DEPOSIT = new Num("10_000.00");
			Num ANNUAL_CONTRIBUTION = new Num("1_000.00");
			Num RATE = new Num("7.25"); 
			Num YEARS = new Num(10);
			Num ANNUALS[][] = new Num [10][4]; 
			Num SQUARENESS_1 = new Num(0), SQUARENESS_2 = new Num(0), SQUARENESS_3  = new Num(0); 
			for (int i = 0; i < YEARS.toInt(); i++) { 
				ANNUALS[i][0] = new Num(i + 1);   //year 
				ANNUALS[i][1] = DEPOSIT;         //DEPOSIT 
				ANNUALS[i][2] = DEPOSIT.Mul(RATE).Div(100).Round(); 
				ANNUALS[i][3] = ANNUALS[i][1].Add(ANNUALS[i][2]).Add(ANNUAL_CONTRIBUTION); 
				DEPOSIT = ANNUALS[i][3]; 
			} 	
			Num.print("---------------------------------------------------\n");  
			for (int i = 0; i < YEARS.toInt(); i++) { 
				Num.print(ANNUALS[i][0].toInt(), "\t"); //INDEX
				Num.print(US.format(ANNUALS[i][1].toDouble()), "\t");
				if(ANNUALS[i][2].get_n0().length() < 4) { Num.print("##"); Num.print(US.format(ANNUALS[i][2].toDouble()) + "\t"); }
				else Num.print(US.format(ANNUALS[i][2].toDouble()), "\t");
				Num.print(US.format(ANNUALS[i][3].toDouble()), "\t"); 
				Num.print("\r\n");
			} 
			
			for (int i = 0; i < YEARS.toInt(); i++) { 
				SQUARENESS_1 = SQUARENESS_1.Add(ANNUALS[i][1]);
				SQUARENESS_2 = SQUARENESS_2.Add(ANNUALS[i][2]);
				SQUARENESS_3 = SQUARENESS_3.Add(ANNUALS[i][3]);
			}
			
			Num.print("---------------------------------------------------\n"); 
			Num.print("       "); 
			Num.print(US.format(SQUARENESS_1.toDouble()), "     ");
			Num.print(US.format(SQUARENESS_2.toDouble()), "      ");
			Num.print(US.format(SQUARENESS_3.toDouble()), " ");
			Boolean SQUARENESS = (SQUARENESS_1.Add(SQUARENESS_2)).EQ(SQUARENESS_3.Sub(ANNUAL_CONTRIBUTION.Mul(YEARS)));
			Num.print(" => SQUARENESS=", (SQUARENESS ? "SUCCESS" : "FAILURE"), "\r\n");
					
		}
	}

	/* VIDEO OUTPUT
	---------------------------------------------------
	1	$10,000.00	##$725.00	$11,725.00	
	2	$11,725.00	##$850.06	$13,575.06	
	3	$13,575.06	##$984.19	$15,559.25	
	4	$15,559.25	$1,128.05	$17,687.30	
	5	$17,687.30	$1,282.33	$19,969.63	
	6	$19,969.63	$1,447.80	$22,417.43	
	7	$22,417.43	$1,625.26	$25,042.69	
	8	$25,042.69	$1,815.60	$27,858.29	
	9	$27,858.29	$2,019.73	$30,878.02	
	10	$30,878.02	$2,238.66	$34,116.68	
	---------------------------------------------------
		   $194,712.67     $14,116.68      $218,829.35  => SQUARENESS=SUCCESS	*/

FLOAT TO NUM CONVERSION LIST ARRAY >

	import num7.Num;
	import java.util.ArrayList;
	import java.util.Arrays;
	
	public class App {
		public static void main(String[] args) {
	
			Num L[] = {new Num(1011), new Num("0.0"), new Num("9.998412"), new Num("7.0"), new Num("0.123"), new Num("-2.0123"), new Num(10), new Num(6)};
			ArrayList<Num> LN = new ArrayList<>(Arrays.asList(L));
			for(Num i : LN) Num.print(i.n, " "); //1011.0 0.0 9.998412 7.0 0.123 -2.0123 10.0 6.0
					
		}
	}

SAVE NUMERIC LIST TO DISK FILE >

	import num7.Num;
	import java.util.ArrayList;
	
	public class App {
		public static void main(String[] args) {
	
			double[] D = { -110.0, +0.14, -20.456120, 1200.0654, 0.0, 3.141592654, 2.7182818281234567899 }; 
			ArrayList<Num> A = Num.float2num_list(D); 
			for(Num a : A) Num.print(a, "\r\n"); //-110.0 0.14 -20.45612 1200.0654 0.0 3.141592654 2.7182818281234566
					
		}
	}

