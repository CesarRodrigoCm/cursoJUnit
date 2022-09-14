package com.minsait.junit.models;

import com.minsait.junit.exceptions.DineroInsuficienteException;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

/*@Getter //Actua como el Get para nuestras 3 variables
@Setter//Actua como el Set para nuestras 3 variables
@EqualsAndHashCode //Sobreescribir el metodo*/

@Data

public class Cuenta {

    @NonNull
    private String persona;
    @NonNull
    private BigDecimal saldo;
    private Banco banco;

   /* public Cuenta(String persona, BigDecimal saldo) {
        this.persona = persona;
        this.saldo = saldo;

    }*/

    public void retirar(BigDecimal monto){
        BigDecimal saldoAux = this.saldo.subtract(monto);
        if (saldoAux.compareTo(BigDecimal.ZERO)<0){
            throw new DineroInsuficienteException("Dinero Insuficiente");

        }
        this.saldo = saldoAux;

    }
    public void depositar(BigDecimal monto){
        this.saldo =this.saldo.add(monto);
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cuenta cuenta = (Cuenta) o;
        return Objects.equals(persona, cuenta.persona) && Objects.equals(saldo, cuenta.saldo) && Objects.equals(banco, cuenta.banco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persona, saldo, banco);
    }*/
}
