package com.example.calculadora_funcional.operacion;

public class Division extends Operacion {
    public Division(Double a, Double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Division por cero no está permitida");
        }
        setResultado(a / b);
    }
}
