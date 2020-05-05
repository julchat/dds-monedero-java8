package dds.monedero.model;

import java.util.ArrayList;
import java.util.List;

public class builderCuenta {
 private double saldo;
 private List<Movimiento> movimientos = new ArrayList<>();
 
 public void agregarSaldo(double saldo) {
	 this.saldo = saldo;
 }
 
 
 public void agregarMovimientos(List<Movimiento> movimientos){
	 this.movimientos = movimientos;
 }
 
 public Cuenta construite() {
	 return new Cuenta (this.saldo,this.movimientos);
 }
 
 public double getSaldo() {
	 return this.saldo;
 }
 
 public List<Movimiento> getMovimientos(){
	 return this.movimientos;
 }

}
