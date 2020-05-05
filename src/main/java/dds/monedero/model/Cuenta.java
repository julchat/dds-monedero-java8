package dds.monedero.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

public class Cuenta {
  private double saldo;
  private List<Movimiento> movimientos = new ArrayList<>();
  

  public Cuenta(double saldo, List<Movimiento> movimientos) {
	this.saldo = saldo;
	this.movimientos = movimientos;
}

public void poner(double cuanto) {
	  this.validarPositivo(cuanto);
	  this.validarCantDepositos();
      this.agregarMovimiento(LocalDate.now(), cuanto, true);
  }

  public void sacar(double cuanto) {
    this.validarPositivo(cuanto);
    this.validarSaldoMayor(cuanto);
    this.validarExtraccionDiaria(cuanto);
    this.agregarMovimiento(LocalDate.now(), cuanto, false);
  }

  public void agregarMovimiento(LocalDate fecha, double cuanto, boolean esDeposito) {
    Movimiento movimiento = new Movimiento(fecha, cuanto, esDeposito);
    movimientos.add(movimiento);
  }

  public double getMontoExtraidoA(LocalDate fecha) {
    return movimientos.stream()
        .filter(movimiento -> !movimiento.isDeposito() && movimiento.getFecha().equals(fecha))
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

  public void validarPositivo(double numero) {
	  if (numero <= 0) {
	    throw new MontoNegativoException(numero + ": el monto a ingresar debe ser un valor positivo");
	    }
  }
  
  public void validarCantDepositos() {
	  if (movimientos.stream().filter(movimiento -> movimiento.isDeposito()).count() >= 3) {
	      throw new MaximaCantidadDepositosException("Ya excedio los " + 3 + " depositos diarios");
	    }
  }
  
  public void validarSaldoMayor(double cuanto) {
	    if (getSaldo() - cuanto < 0) {
	        throw new SaldoMenorException("No puede sacar mas de " + getSaldo() + " $");
  }
}
  public void validarExtraccionDiaria(double cuanto) {
	double montoExtraidoHoy = getMontoExtraidoA(LocalDate.now());
	    double limite = 1000 - montoExtraidoHoy;
	    if (cuanto > limite) {
	      throw new MaximoExtraccionDiarioException("No puede extraer mas de $ " + 1000  + " diarios, límite: " + limite);
	    }
  }
  }
