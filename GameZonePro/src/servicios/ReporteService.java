/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

/**
 *
 * @author vqzjo
 */


import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import estructuras.ListaSimple;
import estructuras.NodoSimple;
import modelo.Juego;
import modelo.Compra;
import modelo.DetalleCompra;
import estructuras.Album;
import estructuras.NodoMatriz;
import modelo.Carta;
import modelo.Torneo;
import modelo.TicketVendido;

public class ReporteService {
    
    private static final String CARPETA_REPORTES = "reportes";

    public ReporteService() {
        asegurarCarpetaReportes();
    }
    
    public boolean generarReporteTorneos(ListaSimple torneos, ListaSimple ticketsVendidos) {
        StringBuilder body = new StringBuilder();

        body.append("<div class='tarjeta'>");
        body.append("<strong>Reporte:</strong> Torneos y Tickets Vendidos<br>");
        body.append("<strong>Descripción:</strong> Lista de torneos registrados y ventas de tickets.");
        body.append("</div>");

        body.append("<h2>Torneos</h2>");
        body.append("<table>");
        body.append("<tr>");
        body.append("<th>ID</th>");
        body.append("<th>Nombre</th>");
        body.append("<th>Juego</th>");
        body.append("<th>Fecha</th>");
        body.append("<th>Hora</th>");
        body.append("<th>Precio Ticket</th>");
        body.append("<th>Tickets Disponibles</th>");
        body.append("</tr>");

        if (torneos == null || torneos.getCabeza() == null) {
            body.append("<tr><td colspan='7'>No hay torneos registrados.</td></tr>");
        } else {
            NodoSimple actualTorneo = torneos.getCabeza();

            while (actualTorneo != null) {
                Torneo torneo = (Torneo) actualTorneo.getDato();

                body.append("<tr>");
                body.append("<td>").append(escaparHtml(torneo.getIdTorneo())).append("</td>");
                body.append("<td>").append(escaparHtml(torneo.getNombre())).append("</td>");
                body.append("<td>").append(escaparHtml(torneo.getJuego())).append("</td>");
                body.append("<td>").append(escaparHtml(torneo.getFecha())).append("</td>");
                body.append("<td>").append(escaparHtml(torneo.getHora())).append("</td>");
                body.append("<td>Q").append(formatearDecimal(torneo.getPrecioTicket())).append("</td>");
                body.append("<td>").append(torneo.getTicketsDisponibles()).append("</td>");
                body.append("</tr>");

                actualTorneo = actualTorneo.getSiguiente();
            }
        }

        body.append("</table>");

        body.append("<h2>Tickets Vendidos</h2>");
        body.append("<table>");
        body.append("<tr>");
        body.append("<th>Fecha y Hora</th>");
        body.append("<th>Taquilla</th>");
        body.append("<th>Usuario</th>");
        body.append("<th>ID Torneo</th>");
        body.append("<th>Nombre Torneo</th>");
        body.append("<th>Juego</th>");
        body.append("<th>Precio</th>");
        body.append("</tr>");

        if (ticketsVendidos == null || ticketsVendidos.getCabeza() == null) {
            body.append("<tr><td colspan='7'>No hay tickets vendidos registrados.</td></tr>");
        } else {
            NodoSimple actualTicket = ticketsVendidos.getCabeza();
            int contadorTickets = 0;
            double totalVendido = 0;

            while (actualTicket != null) {
                TicketVendido ticket = (TicketVendido) actualTicket.getDato();

                body.append("<tr>");
                body.append("<td>").append(escaparHtml(ticket.getFechaHoraVenta())).append("</td>");
                body.append("<td>").append(escaparHtml(ticket.getTaquilla())).append("</td>");
                body.append("<td>").append(escaparHtml(ticket.getNombreUsuario())).append("</td>");
                body.append("<td>").append(escaparHtml(ticket.getIdTorneo())).append("</td>");
                body.append("<td>").append(escaparHtml(ticket.getNombreTorneo())).append("</td>");
                body.append("<td>").append(escaparHtml(ticket.getJuego())).append("</td>");
                body.append("<td>Q").append(formatearDecimal(ticket.getPrecio())).append("</td>");
                body.append("</tr>");

                contadorTickets++;
                totalVendido += ticket.getPrecio();
                actualTicket = actualTicket.getSiguiente();
            }

            body.append("</table>");
            body.append("<div class='total'>");
            body.append("Tickets vendidos: ").append(contadorTickets).append("<br>");
            body.append("Total recaudado: Q").append(formatearDecimal(totalVendido));
            body.append("</div>");

            return generarYMostrarReporte(
                    "Torneos",
                    "Reporte de Torneos",
                    body.toString()
            );
        }

        body.append("</table>");

        return generarYMostrarReporte(
                "Torneos",
                "Reporte de Torneos",
                body.toString()
        );
    }
    
    public boolean generarReporteAlbum(Album album) {
        StringBuilder body = new StringBuilder();

        body.append("<div class='tarjeta'>");
        body.append("<strong>Reporte:</strong> Estado actual del Álbum<br>");
        body.append("<strong>Descripción:</strong> Visualización matricial del álbum de cartas.");
        body.append("</div>");

        if (album == null || album.getCabeza() == null) {
            body.append("<div class='tarjeta'>No hay álbum disponible.</div>");
        } else {
            body.append("<table>");

            NodoMatriz filaActual = album.getCabeza();

            while (filaActual != null) {
                body.append("<tr>");

                NodoMatriz columnaActual = filaActual;

                while (columnaActual != null) {
                    Carta carta = columnaActual.getDato();

                    if (carta == null) {
                        body.append("<td class='vacia'>");
                        body.append("Vacía");
                        body.append("<br><small>[")
                            .append(columnaActual.getFila())
                            .append(",")
                            .append(columnaActual.getColumna())
                            .append("]</small>");
                        body.append("</td>");
                    } else if (carta.esLegendaria()) {
                        body.append("<td class='legendaria'>");
                        body.append("<strong>").append(escaparHtml(carta.getNombre())).append("</strong><br>");
                        body.append("Tipo: ").append(escaparHtml(carta.getTipo())).append("<br>");
                        body.append("Rareza: ").append(escaparHtml(carta.getRareza())).append("<br>");
                        body.append("ATQ: ").append(carta.getAtaque()).append(" | ");
                        body.append("DEF: ").append(carta.getDefensa()).append(" | ");
                        body.append("PS: ").append(carta.getPs()).append("<br>");
                        body.append("<small>[")
                            .append(columnaActual.getFila())
                            .append(",")
                            .append(columnaActual.getColumna())
                            .append("]</small>");
                        body.append("</td>");
                    } else {
                        body.append("<td>");
                        body.append("<strong>").append(escaparHtml(carta.getNombre())).append("</strong><br>");
                        body.append("Tipo: ").append(escaparHtml(carta.getTipo())).append("<br>");
                        body.append("Rareza: ").append(escaparHtml(carta.getRareza())).append("<br>");
                        body.append("ATQ: ").append(carta.getAtaque()).append(" | ");
                        body.append("DEF: ").append(carta.getDefensa()).append(" | ");
                        body.append("PS: ").append(carta.getPs()).append("<br>");
                        body.append("<small>[")
                            .append(columnaActual.getFila())
                            .append(",")
                            .append(columnaActual.getColumna())
                            .append("]</small>");
                        body.append("</td>");
                    }

                    columnaActual = columnaActual.getDerecha();
                }

                body.append("</tr>");
                filaActual = filaActual.getAbajo();
            }

            body.append("</table>");
        }

        return generarYMostrarReporte(
                "Album",
                "Reporte del Álbum",
                body.toString()
        );
    }
    
    public boolean generarReporteVentas(ListaSimple historialCompras) {
        StringBuilder body = new StringBuilder();

        body.append("<div class='tarjeta'>");
        body.append("<strong>Reporte:</strong> Historial de Ventas<br>");
        body.append("<strong>Descripción:</strong> Compras realizadas con detalle de items.");
        body.append("</div>");

        if (historialCompras == null || historialCompras.getCabeza() == null) {
            body.append("<div class='tarjeta'>No hay compras registradas.</div>");
        } else {
            NodoSimple actualCompra = historialCompras.getCabeza();
            int contadorCompras = 0;

            while (actualCompra != null) {
                Compra compra = (Compra) actualCompra.getDato();
                contadorCompras++;

                body.append("<div class='tarjeta'>");
                body.append("<h3>Compra #").append(contadorCompras).append("</h3>");
                body.append("<p>").append(escaparHtml(compra.toString())).append("</p>");

                body.append("<table>");
                body.append("<tr>");
                body.append("<th>Detalle</th>");
                body.append("</tr>");

                NodoSimple actualDetalle = compra.getDetalles().getCabeza();

                if (actualDetalle == null) {
                    body.append("<tr><td>Sin detalles registrados.</td></tr>");
                } else {
                    while (actualDetalle != null) {
                        DetalleCompra detalle = (DetalleCompra) actualDetalle.getDato();

                        body.append("<tr>");
                        body.append("<td>").append(escaparHtml(detalle.toString())).append("</td>");
                        body.append("</tr>");

                        actualDetalle = actualDetalle.getSiguiente();
                    }
                }

                body.append("</table>");
                body.append("</div>");

                actualCompra = actualCompra.getSiguiente();
            }

            body.append("<div class='total'>Total de compras registradas: ")
                .append(contadorCompras)
                .append("</div>");
        }

        return generarYMostrarReporte(
                "Ventas",
                "Reporte de Ventas",
                body.toString()
        );
    }
    
    private String formatearDecimal(double valor) {
        return String.format("%.2f", valor);
    }

    public boolean generarReporteInventario(ListaSimple catalogo) {
        StringBuilder body = new StringBuilder();

        body.append("<div class='tarjeta'>");
        body.append("<strong>Reporte:</strong> Inventario de Tienda<br>");
        body.append("<strong>Descripción:</strong> Lista de juegos del catálogo con stock, precio y plataforma.");
        body.append("</div>");

        body.append("<table>");
        body.append("<tr>");
        body.append("<th>Código</th>");
        body.append("<th>Nombre</th>");
        body.append("<th>Género</th>");
        body.append("<th>Precio</th>");
        body.append("<th>Plataforma</th>");
        body.append("<th>Stock</th>");
        body.append("<th>Descripción</th>");
        body.append("</tr>");

        if (catalogo == null || catalogo.getCabeza() == null) {
            body.append("<tr>");
            body.append("<td colspan='7'>No hay juegos en el catálogo.</td>");
            body.append("</tr>");
        } else {
            NodoSimple actual = catalogo.getCabeza();

            while (actual != null) {
                Juego juego = (Juego) actual.getDato();

                body.append("<tr>");
                body.append("<td>").append(escaparHtml(juego.getCodigo())).append("</td>");
                body.append("<td>").append(escaparHtml(juego.getNombre())).append("</td>");
                body.append("<td>").append(escaparHtml(juego.getGenero())).append("</td>");
                body.append("<td>Q").append(formatearDecimal(juego.getPrecio())).append("</td>");
                body.append("<td>").append(escaparHtml(juego.getPlataforma())).append("</td>");
                body.append("<td>").append(juego.getStock()).append("</td>");
                body.append("<td>").append(escaparHtml(juego.getDescripcion())).append("</td>");
                body.append("</tr>");

                actual = actual.getSiguiente();
            }
        }

        body.append("</table>");

        return generarYMostrarReporte(
                "Inventario",
                "Reporte de Inventario de Tienda",
                body.toString()
        );
    }

    private void asegurarCarpetaReportes() {
        File carpeta = new File(CARPETA_REPORTES);

        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
    }

    public String generarNombreReporte(String tipo) {
        String timestamp = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss")
        );

        return timestamp + "_" + tipo + ".html";
    }

    public File crearArchivoReporte(String tipo) {
        String nombreArchivo = generarNombreReporte(tipo);
        return new File(CARPETA_REPORTES + File.separator + nombreArchivo);
    }

    public boolean escribirReporte(File archivo, String contenidoHtml) {
        if (archivo == null || contenidoHtml == null) {
            return false;
        }

        FileWriter writer = null;

        try {
            writer = new FileWriter(archivo, false);
            writer.write(contenidoHtml);
            writer.flush();
            return true;
        } catch (IOException e) {
            System.out.println("Error al escribir reporte HTML: " + e.getMessage());
            return false;
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
            }
        }
    }

    public void abrirReporteEnNavegador(File archivo) {
        if (archivo == null || !archivo.exists()) {
            return;
        }

        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                URI uri = archivo.toURI();
                desktop.browse(uri);
            }
        } catch (Exception e) {
            System.out.println("No se pudo abrir el reporte en el navegador: " + e.getMessage());
        }
    }

    public String construirHtmlBase(String titulo, String contenidoBody) {
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>");
        html.append("<html lang='es'>");
        html.append("<head>");
        html.append("<meta charset='UTF-8'>");
        html.append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        html.append("<title>").append(escaparHtml(titulo)).append("</title>");
        html.append("<style>");
        html.append("body { font-family: Arial, sans-serif; background: #f4f6f8; color: #222; margin: 0; padding: 20px; }");
        html.append(".contenedor { max-width: 1100px; margin: 0 auto; background: #ffffff; border-radius: 12px; padding: 24px; box-shadow: 0 2px 10px rgba(0,0,0,0.08); }");
        html.append("h1 { margin-top: 0; color: #1f4e79; }");
        html.append(".subtitulo { color: #666; margin-bottom: 20px; }");
        html.append("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
        html.append("th, td { border: 1px solid #d0d7de; padding: 10px; text-align: left; }");
        html.append("th { background: #1f4e79; color: white; }");
        html.append("tr:nth-child(even) { background: #f8fafc; }");
        html.append(".tarjeta { background: #f8fafc; border: 1px solid #d0d7de; border-radius: 10px; padding: 14px; margin-top: 15px; }");
        html.append(".vacia { background: #d9d9d9; color: #555; text-align: center; font-weight: bold; }");
        html.append(".legendaria { background: #d4af37; color: #222; font-weight: bold; }");
        html.append(".total { margin-top: 18px; font-weight: bold; font-size: 18px; }");
        html.append("</style>");
        html.append("</head>");
        html.append("<body>");
        html.append("<div class='contenedor'>");
        html.append("<h1>").append(escaparHtml(titulo)).append("</h1>");
        html.append("<div class='subtitulo'>Generado automáticamente por GameZone Pro</div>");
        html.append(contenidoBody);
        html.append("</div>");
        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }

    public boolean generarYMostrarReporte(String tipo, String titulo, String contenidoBody) {
        File archivo = crearArchivoReporte(tipo);
        String html = construirHtmlBase(titulo, contenidoBody);

        boolean escrito = escribirReporte(archivo, html);

        if (escrito) {
            abrirReporteEnNavegador(archivo);
            return true;
        }

        return false;
    }

    public String escaparHtml(String texto) {
        if (texto == null) {
            return "";
        }

        return texto.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
    
}
