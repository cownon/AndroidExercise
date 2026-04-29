package com.example.servicedemo.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================
 * BOUND SERVICE - MAY TINH (CALCULATOR) (Slide 5)
 * ============================================================
 * Cong viec CU THE:
 * - Activity (Client) gui phep tinh: 5 + 3, 10 * 7, 100 / 4...
 * - Service (Server) tinh toan va tra ve ket qua
 * - Luu lai lich su phep tinh
 * - Activity co the xem lich su bat ky luc nao
 *
 * Day la mo hinh CLIENT-SERVER that su (Slide 5):
 * - Client = Activity (gui yeu cau)
 * - Server = Service (xu ly va tra ket qua)
 * ============================================================
 */
public class CalculatorService extends Service {

    private static final String TAG = "CalculatorService";
    private final IBinder binder = new CalculatorBinder();
    private final List<String> history = new ArrayList<>();

    /**
     * Binder - Cau noi giua Activity va Service
     */
    public class CalculatorBinder extends Binder {
        public CalculatorService getService() {
            return CalculatorService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() - Calculator Service san sang");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind() - Client da ket noi");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind() - Client da ngat ket noi");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
        history.clear();
    }

    // ============================================================
    // CAC PHEP TINH - Activity goi truc tiep (Client-Server)
    // ============================================================

    public double calculate(double a, String operator, double b) {
        double result;
        String expression;

        switch (operator) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
            case "x":
                result = a * b;
                operator = "x";
                break;
            case "/":
                if (b == 0) {
                    expression = formatNum(a) + " / 0 = LOI (chia cho 0)";
                    history.add(0, expression);
                    Log.d(TAG, expression);
                    return Double.NaN;
                }
                result = a / b;
                break;
            default:
                return Double.NaN;
        }

        // Luu lich su
        expression = formatNum(a) + " " + operator + " " + formatNum(b) + " = " + formatNum(result);
        history.add(0, expression);
        Log.d(TAG, "Tinh: " + expression);

        return result;
    }

    /** Tinh luy thua */
    public double power(double base, double exponent) {
        double result = Math.pow(base, exponent);
        String expression = formatNum(base) + " ^ " + formatNum(exponent) + " = " + formatNum(result);
        history.add(0, expression);
        Log.d(TAG, expression);
        return result;
    }

    /** Tinh can bac hai */
    public double squareRoot(double number) {
        if (number < 0) {
            history.add(0, "sqrt(" + formatNum(number) + ") = LOI (so am)");
            return Double.NaN;
        }
        double result = Math.sqrt(number);
        String expression = "sqrt(" + formatNum(number) + ") = " + formatNum(result);
        history.add(0, expression);
        Log.d(TAG, expression);
        return result;
    }

    /** Tinh giai thua */
    public long factorial(int n) {
        if (n < 0 || n > 20) {
            history.add(0, n + "! = LOI (ngoai pham vi)");
            return -1;
        }
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        history.add(0, n + "! = " + result);
        Log.d(TAG, n + "! = " + result);
        return result;
    }

    // ============================================================
    // LICH SU & TRANG THAI
    // ============================================================

    /** Lay lich su phep tinh */
    public List<String> getHistory() {
        return new ArrayList<>(history);
    }

    /** Xoa lich su */
    public void clearHistory() {
        history.clear();
        Log.d(TAG, "Da xoa lich su");
    }

    /** Tong so phep tinh da thuc hien */
    public int getTotalCalculations() {
        return history.size();
    }

    private String formatNum(double n) {
        if (n == (long) n) {
            return String.valueOf((long) n);
        }
        return String.format("%.4f", n);
    }
}
