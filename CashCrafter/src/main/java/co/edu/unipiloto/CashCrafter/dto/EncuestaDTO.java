/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unipiloto.CashCrafter.dto;
import java.time.LocalDate;
/**
 *
 * @author maria
 */
public class EncuestaDTO {
    public static class EncuestaRequest {
        private Integer calificacion;
        private String comentario;

        public Integer getCalificacion() {
            return calificacion;
        }

        public void setCalificacion(Integer calificacion) {
            this.calificacion = calificacion;
        }

        public String getComentario() {
            return comentario;
        }

        public void setComentario(String comentario) {
            this.comentario = comentario;
        }
    }

    public static class EncuestaResponse {
        private Long id;
        private Integer calificacion;
        private String comentario;
        private LocalDate fecha;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Integer getCalificacion() {
            return calificacion;
        }

        public void setCalificacion(Integer calificacion) {
            this.calificacion = calificacion;
        }

        public String getComentario() {
            return comentario;
        }

        public void setComentario(String comentario) {
            this.comentario = comentario;
        }

        public LocalDate getFecha() {
            return fecha;
        }

        public void setFecha(LocalDate fecha) {
            this.fecha = fecha;
        }

        
    }
}