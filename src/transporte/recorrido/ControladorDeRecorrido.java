package transporte.recorrido;

import java.util.Arrays;

public class ControladorDeRecorrido {

    private Punto este_bus;
    private Punto punto_actual; // el punto al que el bus se dirige

    private int longitud_de_puntos;
    private Punto[] puntos;
    private boolean[] estado_de_cada_punto_para_saber_si_el_bus_ha_pasado_por_alguno;
    private int indice_de_la_posicion_en_el_arreglo_de_puntos;

    public ControladorDeRecorrido(Punto un_bus) {
	este_bus = un_bus;
	longitud_de_puntos = 1;
	puntos = new Punto[longitud_de_puntos];
	estado_de_cada_punto_para_saber_si_el_bus_ha_pasado_por_alguno = new boolean[longitud_de_puntos];
	indice_de_la_posicion_en_el_arreglo_de_puntos = 0;
    }

    public void agregarUnPunto(Punto un_punto) {
	if (indice_de_la_posicion_en_el_arreglo_de_puntos == longitud_de_puntos) {
	    expandir_el_arreglo_de_puntos();
	}
	puntos[indice_de_la_posicion_en_el_arreglo_de_puntos] = un_punto;
	estado_de_cada_punto_para_saber_si_el_bus_ha_pasado_por_alguno[indice_de_la_posicion_en_el_arreglo_de_puntos++] = false;

    }

    private void expandir_el_arreglo_de_puntos() {
	longitud_de_puntos <<= 1;
	puntos = Arrays.copyOf(puntos, longitud_de_puntos);
	estado_de_cada_punto_para_saber_si_el_bus_ha_pasado_por_alguno = Arrays.copyOf(estado_de_cada_punto_para_saber_si_el_bus_ha_pasado_por_alguno, longitud_de_puntos);
    }

    public boolean estaElBusEnElRadioDeAlcanceDeAlgunPunto() {
	boolean respuesta = false;
	for (int i = 0; i < indice_de_la_posicion_en_el_arreglo_de_puntos ; i++) {
	    if (esta_este_bus_en_el_radio_de_alcance_de_este_punto(puntos[i])) {
		respuesta = true;
		break;
	    }
	}
	return respuesta;
    }

    public int numero_de_puntos() {
	return indice_de_la_posicion_en_el_arreglo_de_puntos;
    }

    private boolean esta_este_bus_en_el_radio_de_alcance_de_este_punto(Punto punto) {
	if (este_bus.latitud == punto.latitud && este_bus.longitud == punto.longitud) {
	    return actualizar_estado_de_alcance(true, punto);
	} else if (este_bus.latitud > punto.latitud && este_bus.latitud < punto.latitud + punto.tolerancia_latitud_norte) {
	    return actualizar_estado_de_alcance(true, punto);
	} else if (este_bus.latitud < punto.latitud && este_bus.latitud > punto.latitud - punto.tolerancia_latitud_sur) {
	    return actualizar_estado_de_alcance(true, punto);
	} else if (este_bus.longitud > punto.longitud && este_bus.longitud < punto.longitud + punto.tolerancia_longitud_este) {
	    return actualizar_estado_de_alcance(true, punto);
	} else if (este_bus.longitud < punto.longitud && este_bus.longitud > punto.longitud - punto.tolerancia_longitud_oeste) {
	    return actualizar_estado_de_alcance(true, punto);
	} else {
	    return actualizar_estado_de_alcance(false, null);
	}
    }

    private boolean actualizar_estado_de_alcance(boolean el_retorno, Punto punto) {
	if (el_retorno) {
	    punto_actual = punto;
	} else {
	    punto_actual = null;
	}
	return el_retorno;
    }

    public String nombre_del_punto_al_que_el_bus_se_dirije() {
	return punto_actual.nombre;
    }

}
