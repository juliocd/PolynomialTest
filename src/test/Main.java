package test;

public class Main {
	
	public static void main(String[] args) {
	    TrendLine t = new PolyTrendLine(2);
	    double[] x = {50,50,50,70,70,70,80,80,80,90,90,90,100,100,100};
	    double[] y = {3.3,2.8,2.9,2.3,2.6,2.1,2.5,2.9,2.4,3,3.1,2.8,3.3,3.5,3};
	    t.setValues(y,x);
	    System.out.println(t.predict(5)); 
	}
	
}

	