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
	Num.print(Num.in(L, new Num("-3.0001")), " ");      //true
	Num.print(Num.in(L, new Num("-3.00001")), " ");    //false
	Num.print(Num.in(L, new Num("-3.0001")), "\r\n"); //true
