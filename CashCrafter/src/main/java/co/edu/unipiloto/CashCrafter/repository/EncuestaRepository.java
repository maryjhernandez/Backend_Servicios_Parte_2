/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unipiloto.CashCrafter.repository;

import co.edu.unipiloto.CashCrafter.entity.EncuestaSatisfaccion;
import co.edu.unipiloto.CashCrafter.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
/**
 *
 * @author maria
 */
public interface EncuestaRepository extends JpaRepository<EncuestaSatisfaccion, Long> {
    // Buscar encuestas por usuario
    List<EncuestaSatisfaccion> findByUsuario(Usuario usuario);
    
    // Buscar la última encuesta de un usuario
    Optional<EncuestaSatisfaccion> findTopByUsuarioOrderByFechaDesc(Usuario usuario);
    
    // Buscar encuestas por rango de fechas
    List<EncuestaSatisfaccion> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    // Buscar encuestas por calificación
    List<EncuestaSatisfaccion> findByCalificacion(Integer calificacion);
    
    // Query personalizada para obtener promedio de calificaciones
    @Query("SELECT AVG(e.calificacion) FROM EncuestaSatisfaccion e " +
           "WHERE e.fecha BETWEEN :fechaInicio AND :fechaFin")
    Double findPromedioCalificacionByFechaBetween(
        @Param("fechaInicio") LocalDate fechaInicio,
        @Param("fechaFin") LocalDate fechaFin
    );
    
    // Verificar si un usuario ya ha realizado una encuesta en una fecha específica
    boolean existsByUsuarioAndFecha(Usuario usuario, LocalDate fecha);
}
