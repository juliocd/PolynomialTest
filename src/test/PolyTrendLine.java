package test;

public class PolyTrendLine extends OLSTrendLine {
    final int degree;//Grados del polinomio
    
    //Se valida grado de polinomio
    public PolyTrendLine(int degree) {
        if (degree < 0) {
        	throw new IllegalArgumentException("Los grados de un polinomio no pueden ser negativos");
        }else{
        	this.degree = degree;
        }
    }
    
    //Se construye vector de x con base en grado de polinomio, de cada uno de los valores de x
    //Grado 0: y=x^0 = 1
    //Grado 1: y=ax+b
    //Grado 2: y=ax^2+bx+c
    //Grado 3: y=ax^3+bx^2+cx+d
    //Retorna {1, x, x*x, x*x*x, ...}
    protected double[] xVector(double x) { 
        double[] poly = new double[degree+1];
        double xi=1;//Grado 0 de x
        for(int i=0; i<=degree; i++) {
            poly[i]=xi;
            xi*=x;
        }
        return poly;
    }
    
    @Override
    protected boolean logY() {return false;}
}
