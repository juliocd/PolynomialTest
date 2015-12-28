package test;

import java.util.Arrays;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

public abstract class OLSTrendLine implements TrendLine {

    RealMatrix coef = null; // will hold prediction coefs once we get values

    protected abstract double[] xVector(double x); // Se crea vector de x, solo para polinomios
    protected abstract boolean logY(); // true para calcular logaritmo de y, false para no calcularlo

    @Override
    public void setValues(double[] y, double[] x) {
        if (x.length != y.length) {
            throw new IllegalArgumentException(String.format("El numero de elemntos en y debe ser igual al de x (%d != %d)",y.length,x.length));
        }
        
        //Se genera matriz de potencias de x para cada elemento
        double[][] xData = new double[x.length][]; 
        for (int i = 0; i < x.length; i++) {
            xData[i] = xVector(x[i]);
        }
        
        //Se calcula logaritmo natural de y, esto para modelos no lineales o polinomicos
        if(logY()) {
            y = Arrays.copyOf(y, y.length);
            for (int i = 0; i < x.length; i++) {
                y[i] = Math.log(y[i]);
            }
        }
        
        //Se instancia la clase de minimos cuadrados ordianrios 
        OLSMultipleLinearRegression ols = new OLSMultipleLinearRegression();
        ols.setNoIntercept(true); // Establecer un punto de intercepto en la grafica
        ols.newSampleData(y, xData); // Se ingresan datos al modelo
        coef = MatrixUtils.createColumnRealMatrix(ols.estimateRegressionParameters()); // Se ejecuta operacion para calculo de la ecuacion del 
        //polinomio por minimos cuadrados.
        System.out.println(coef);
        double[] residual = ols.estimateResiduals();
        System.out.println(ols.calculateResidualSumOfSquares());
        System.out.println(ols.calculateTotalSumOfSquares());
//        System.out.println("Y=" + coef.getEntry(2, 0) + "X^2 " 
//        + (coef.getEntry(1, 0) > 0 ? "+" : "") + coef.getEntry(1, 0) + "X " + (coef.getEntry(0, 0) > 0 ? "+" : "") + coef.getEntry(0, 0));
    }

    @Override
    public double predict(double x) {
        double yhat = coef.preMultiply(xVector(x))[0]; // apply coefs to xVector
        if (logY()) yhat = (Math.exp(yhat)); // if we predicted ln y, we still need to get y
        return yhat;
    }
    
}
