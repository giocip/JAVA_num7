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

          /* ADDITION           */ Num.add("2.5", "3.6").Print(" = 2.5 + 3.6 (ADDITION)\n"); 	           //6.1                 
          /* SUBTRACTION        */ Num.sub("2.5", "3.6").Print(" = 2.5 - 3.6 (SUBTRACTION)\n"); 	      //-1.1               
          /* MULTIPLICATION     */ Num.mul("2.5", "3.6").Print(" = 2.5 * 3.6 (MULTIPLICATION)\n"); 	   //9.0                 
          /* DIVISION           */ Num.div("2.5", "3.6").Print(" = 2.5 / 3.6 (DIVISION)\n"); 	        //0.6944444444444444444444444444444444444444
          /* DIVISION REMAINDER */ Num.mod("11.0", "8.0").Print(" = 11 % 8 (DIVISION REMAINDER)\n"); //3.0                 
          /* INV                */ Num.inv("3.0").Print(" = 1 / 3 (INV)\n");  //0.33333333333333333333333333333333                
          /* x2                 */ Num.x2("3.0").Print(" = 3 ^ 2 (x2)\n");   //9.0
          /* x3                 */ Num.x3("3.0").Print(" = 3 ^ 3 (x3)\n");  //27.0
          /* POWER              */ Num.xy("3.14", "8.0").Print(" = 3.14 ^ 8 (POWER)\n");  //9450.1169810786918656
          /* POWER OF TEN       */ Num._10y(6).Print(" = 10^6 (TEN POWER)\n");           //1000000.0
          /* POWER OF TWO       */ Num._2y(5).Print(" = 2^5 (TWO POWER)\n");            //32.0
          /* POWER OF e         */ Num._ey(5).Round(29).Print(" = e^5 (e POWER)\n");   //148.41315910257660342111558004056
          /* FACTORIAL          */ Num P = new Num(Num.fact(5)); P.Print(" = 5! (FACTORIAL)\n");//120
          /* BINARY             */ Num.print(new Num(257).toBin(), " => BINARY\r\n"); //100000001 => BINARY
          /* HEXADECIMAL        */ Num.print(new Num(257).toHex(), " => HEXADECIMAL\r\n"); //101 => HEXADECIMAL
          /* SQUARE ROOT        */ Num.sqrt("2.0").Print(" (SQUARE ROOT OF 2)\n"); 	   		                //1.414213562         
          /* ROUND 2 DIGITS     */ Num.round(Num.sqrt("2.0")).Print(" = SQUARE ROOT OF 2 (ROUND 2 DIGITS)\n");            //1.41  
          /* ROUND 4 DIGITS     */ Num.round("3.14159265", 7).Print(" =~ 3.14159265 (ROUND 7 DIGITS)\n"); 	             //3.1415927
          /* ROUND BANKING      */ Num.round_bank("3.14159265", 7).Print(" =~ 3.14159265 (ROUND BANKING 7 DIGITS)\n");   //3.1415926
          /* ABS                */ Num.abs("-5.25").Print(" (ABSOLUTE VALUE OF -5.25)\n"); 		            //5.25 
          /* SCIENTIFIC         */ Num.print(new Num("314.0e-2").toEXP()); Num.print(" (SCI) = 3.14\r\n"); //3.14e0 (SCI) = 3.14
          /* pi                 */ Num.print(Num.pi(), " (pi)\n"); 						  //3.1415926535897932384626433832795
          /* e                  */ Num.print(Num.e(), " (e)\n");  							//2.7182818284590452353602874713527
          /* 10 TIME VALUE      */ Num._10x("5.25").Print(" (10 TIME VALUE OF 5.25)\n");                      //52.5
          /* 100 TIME VALUE     */ Num._100x("5.25").Print(" (100 TIME VALUE OF 5.25)\n");                   //525.0
          /* 1000 TIME VALUE    */ Num._1000x("5.25").Print(" (1000 TIME VALUE OF 5.25)\n");                //5250.0
          /* DIVIDE FOR 10      */ Num._10div("5.25").Print(" (DIVISION FOR TEN OF 5.25)\n");              //0.525
          /* DIVIDE FOR 100     */ Num._100div("5.25").Print(" (DIVISION FOR HUNDRED OF 5.25)\n");        //0.0525
          /* DIVIDE FOR 1000    */ Num._1000div("5.25").Print(" (DIVISION FOR THOUSAND OF 5.25)\n");     //0.00525
          /* PERCENTAGE         */ Num.pct("3.725", "150.00").Round(2).Print(" = 3.725% OF 150 (PERCENTAGE)\n");        //5.59
          /* PERTHOUSAND        */ Num.pth("2.00", "20_000.00").Round(2).Print(" = 2PTH OF 20000 (PERTHOUSAND)\n");    //40.0
          /* SPIN-OFF           */ Num.f_price_spinoff("1_299.00", "22.00").Round(2).Print(" = (-22%) 1299 (SPIN-OFF)\n"); 	//1064.75
          /* SPIN-ON            */ Num.f_price_over("1_064.75", "22.00").Round(2).Print(" = +22% OF 1064.75 (SPIN-ON)\n"); //1299.0
          Num.print("----------------------\n"); 								      //---------------------- 
          Num[] cart = { new Num("19.31999"), new Num("19.32"), new Num("18.37"), new Num("-15.13"), new Num("-15.12") }; 
          for (Num element : cart) Num.print(element, " "); Num.print("=> ELEMENTS\r\n");   //19.31999 19.32 18.37 -15.13 -15.12 => ELEMENTS
          /* SUM   */ Num.print(Num.sum(cart), " => SUM\r\n");       //26.75999 => SUM
          /* MEAN  */ Num.print(Num.mean(cart).Round(), " => MEAN\r\n"); //5.35 => MEAN
          /* MIN   */ Num.print(Num.min(cart), " => MIN\r\n");         //-15.13 => MIN
          /* MAX   */ Num.print(Num.max(cart), " => MAX\r\n");          //19.32 => MAX
          /*MIN MAX*/ Num.print(Num.minmax(cart), "\r\n");    //[-15.13, 19.32] => MIN-MAX
          Num.print("----------------------\n"); 								      //---------------------- 
          /* FORMAT  */ Num.printf("3005.141592654", 9, true, "\r\n");   //3.005,141592654
          /* FORMAT  */ Num.printf("3005.141592654", 6, false, "\r\n"); //3,005.141593
          /* FORMAT  */ Num.printf("3005.941592654", 0, true, "\r\n"); //3.006
          
          Num.print("----------------------\n"); 
          Num.print("*** num7 LIBRARY TABLE MAIN FUNCTIONS OVER ***");  
      }
    }
  
