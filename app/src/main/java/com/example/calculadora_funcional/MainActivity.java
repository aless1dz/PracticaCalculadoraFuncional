package com.example.calculadora_funcional;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.example.calculadora_funcional.operacion.Suma;
import com.example.calculadora_funcional.operacion.Resta;
import com.example.calculadora_funcional.operacion.Multiplicacion;
import com.example.calculadora_funcional.operacion.Division;

public class MainActivity extends AppCompatActivity {

    // Elementos de la interfaz
    private TextView tvResultado;
    private StringBuilder inputBuilder;
    private boolean decimalAdded = false; // Para evitar múltiples puntos decimales

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflar el diseño
        setContentView(R.layout.activity_main);

        // Inicializar elementos de la interfaz
        tvResultado = findViewById(R.id.tvResultado);
        inputBuilder = new StringBuilder();

        // Configuración de los botones para la calculadora
        setButtonListeners();
    }

    private void setButtonListeners() {
        // Números
        findViewById(R.id.btn_0).setOnClickListener(view -> appendToInput("0"));
        findViewById(R.id.btn_1).setOnClickListener(view -> appendToInput("1"));
        findViewById(R.id.btn_2).setOnClickListener(view -> appendToInput("2"));
        findViewById(R.id.btn_3).setOnClickListener(view -> appendToInput("3"));
        findViewById(R.id.btn_4).setOnClickListener(view -> appendToInput("4"));
        findViewById(R.id.btn_5).setOnClickListener(view -> appendToInput("5"));
        findViewById(R.id.btn_6).setOnClickListener(view -> appendToInput("6"));
        findViewById(R.id.btn_7).setOnClickListener(view -> appendToInput("7"));
        findViewById(R.id.btn_8).setOnClickListener(view -> appendToInput("8"));
        findViewById(R.id.btn_9).setOnClickListener(view -> appendToInput("9"));

        // Punto decimal
        findViewById(R.id.btn_dot).setOnClickListener(view -> appendDecimal());

        // Operadores
        findViewById(R.id.btn_add).setOnClickListener(view -> appendOperator("+"));
        findViewById(R.id.btn_subtract).setOnClickListener(view -> appendOperator("-"));
        findViewById(R.id.btn_multiply).setOnClickListener(view -> appendOperator("*"));
        findViewById(R.id.btn_divide).setOnClickListener(view -> appendOperator("/"));

        // Otros botones
        findViewById(R.id.btn_clear).setOnClickListener(view -> clearInput());
        findViewById(R.id.btn_delete).setOnClickListener(view -> deleteLastCharacter());
        findViewById(R.id.btn_equals).setOnClickListener(view -> calculateResult());
    }

    private void appendToInput(String value) {
        inputBuilder.append(value);
        tvResultado.setText(inputBuilder.toString());
    }

    private void appendDecimal() {
        // Solo agregar el punto decimal si no hay uno ya presente en el número actual
        if (!decimalAdded) {
            inputBuilder.append(".");
            tvResultado.setText(inputBuilder.toString());
            decimalAdded = true; // Marcar que ya se añadió el punto decimal
        }
    }

    private void appendOperator(String operator) {
        if (inputBuilder.length() > 0 && isOperator(inputBuilder.charAt(inputBuilder.length() - 1))) {
            inputBuilder.setCharAt(inputBuilder.length() - 1, operator.charAt(0)); // Reemplazar el operador si ya hay uno al final
        } else {
            inputBuilder.append(operator);
        }
        tvResultado.setText(inputBuilder.toString());
        decimalAdded = false; 
    }

    private void clearInput() {
        inputBuilder.setLength(0);
        tvResultado.setText("0");
        decimalAdded = false;
    }

    private void deleteLastCharacter() {
        if (inputBuilder.length() > 0) {
            if (inputBuilder.charAt(inputBuilder.length() - 1) == '.') {
                decimalAdded = false;
            }
            inputBuilder.deleteCharAt(inputBuilder.length() - 1);
            if (inputBuilder.length() == 0) {
                tvResultado.setText("0");
            } else {
                tvResultado.setText(inputBuilder.toString());
            }
        }
    }

    private void calculateResult() {
        try {
            String expression = inputBuilder.toString();
            double result = evaluateExpression(expression);
            tvResultado.setText(String.valueOf(result));
            inputBuilder.setLength(0);
            inputBuilder.append(result);
            decimalAdded = String.valueOf(result).contains("."); // Actualizar el estado del decimal
        } catch (Exception e) {
            tvResultado.setText("Error");
            inputBuilder.setLength(0);
            decimalAdded = false;
        }
    }

    private double evaluateExpression(String expression) {
        // Aquí se asume que la expresión es muy simple, solo dos operandos y un operador
        String[] parts;
        double result = 0.0;

        if (expression.contains("+")) {
            parts = expression.split("\\+");
            if (parts.length == 2) {
                double a = Double.parseDouble(parts[0]);
                double b = Double.parseDouble(parts[1]);
                Suma suma = new Suma(a, b);
                result = suma.getResultado();
            }
        } else if (expression.contains("-")) {
            parts = expression.split("-");
            if (parts.length == 2) {
                double a = Double.parseDouble(parts[0]);
                double b = Double.parseDouble(parts[1]);
                Resta resta = new Resta(a, b);
                result = resta.getResultado();
            }
        } else if (expression.contains("*")) {
            parts = expression.split("\\*");
            if (parts.length == 2) {
                double a = Double.parseDouble(parts[0]);
                double b = Double.parseDouble(parts[1]);
                Multiplicacion multiplicacion = new Multiplicacion(a, b);
                result = multiplicacion.getResultado();
            }
        } else if (expression.contains("/")) {
            parts = expression.split("/");
            if (parts.length == 2) {
                double a = Double.parseDouble(parts[0]);
                double b = Double.parseDouble(parts[1]);
                Division division = new Division(a, b);
                result = division.getResultado();
            }
        }

        return result;
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
}
