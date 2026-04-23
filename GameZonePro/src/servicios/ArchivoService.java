/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

/**
 *
 * @author vqzjo
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import modelo.Juego;
import estructuras.ListaSimple;
import estructuras.NodoSimple;
import java.io.BufferedWriter;
import java.io.FileWriter;
import modelo.Compra;
import modelo.DetalleCompra;


public class ArchivoService {
    
    public ListaSimple cargarCatalogoDesdeArchivo() {
        ListaSimple catalogo = new ListaSimple();
        String ruta = "src/recursos/catalogo.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|");

                if (partes.length == 7) {
                    String codigo = partes[0];
                    String nombre = partes[1];
                    String genero = partes[2];
                    double precio = Double.parseDouble(partes[3]);
                    String plataforma = partes[4];
                    int stock = Integer.parseInt(partes[5]);
                    String descripcion = partes[6];

                    Juego juego = new Juego(
                            codigo,
                            nombre,
                            genero,
                            precio,
                            plataforma,
                            stock,
                            descripcion
                    );

                    catalogo.insertarAlFinal(juego);
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer el catálogo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error en formato numérico: " + e.getMessage());
        }

        return catalogo;
    }
    
    public void guardarHistorialCompras(ListaSimple historialCompras) {
        String ruta = "src/recursos/historial.txt";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {
            NodoSimple actualCompra = historialCompras.getCabeza();

            while (actualCompra != null) {
                Compra compra = (Compra) actualCompra.getDato();

                NodoSimple actualDetalle = compra.getDetalles().getCabeza();

                while (actualDetalle != null) {
                    DetalleCompra detalle = (DetalleCompra) actualDetalle.getDato();

                    bw.write(compra.getFechaHora() + "|"
                            + compra.getTotal() + "|"
                            + detalle.getCodigoJuego() + "|"
                            + detalle.getNombreJuego() + "|"
                            + detalle.getCantidad() + "|"
                            + detalle.getPrecioUnitario());
                    bw.newLine();

                    actualDetalle = actualDetalle.getSiguiente();
                }

                actualCompra = actualCompra.getSiguiente();
            }

        } catch (IOException e) {
            System.out.println("Error al guardar historial: " + e.getMessage());
        }
    }
    
    public ListaSimple cargarHistorialCompras() {
        ListaSimple historial = new ListaSimple();
        String ruta = "src/recursos/historial.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;

            String ultimaClaveCompra = "";
            Compra compraActual = null;
            ListaSimple detallesActuales = null;

            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|");

                if (partes.length == 6) {
                    String fechaHora = partes[0];
                    double total = Double.parseDouble(partes[1]);
                    String codigoJuego = partes[2];
                    String nombreJuego = partes[3];
                    int cantidad = Integer.parseInt(partes[4]);
                    double precioUnitario = Double.parseDouble(partes[5]);

                    String claveCompra = fechaHora + "|" + total;

                    if (!claveCompra.equals(ultimaClaveCompra)) {
                        detallesActuales = new ListaSimple();
                        compraActual = new Compra(fechaHora, detallesActuales, total);
                        historial.insertarAlFinal(compraActual);
                        ultimaClaveCompra = claveCompra;
                    }

                    DetalleCompra detalle = new DetalleCompra(
                            codigoJuego,
                            nombreJuego,
                            cantidad,
                            precioUnitario
                    );

                    detallesActuales.insertarAlFinal(detalle);
                }
            }

        } catch (IOException e) {
            System.out.println("Error al cargar historial: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error en formato numérico del historial: " + e.getMessage());
        }

        return historial;
    }
    
    public void guardarCatalogo(ListaSimple catalogo) {
        String ruta = "src/recursos/catalogo.txt";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {
            NodoSimple actual = catalogo.getCabeza();

            while (actual != null) {
                Juego juego = (Juego) actual.getDato();

                bw.write(juego.getCodigo() + "|"
                        + juego.getNombre() + "|"
                        + juego.getGenero() + "|"
                        + juego.getPrecio() + "|"
                        + juego.getPlataforma() + "|"
                        + juego.getStock() + "|"
                        + juego.getDescripcion());
                bw.newLine();

                actual = actual.getSiguiente();
            }

        } catch (IOException e) {
            System.out.println("Error al guardar catálogo: " + e.getMessage());
        }
    }
    
}
