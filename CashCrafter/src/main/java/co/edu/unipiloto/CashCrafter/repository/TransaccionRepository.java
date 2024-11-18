/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unipiloto.CashCrafter.repository;
import co.edu.unipiloto.CashCrafter.entity.TipoTransaccion;
import co.edu.unipiloto.CashCrafter.entity.TransaccionFinanciera;
import co.edu.unipiloto.CashCrafter.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
/**
 *
 * @author maria
 */
public interface TransaccionRepository extends JpaRepository<TransaccionFinanciera, Long> {
    // Buscar transacciones por usuario ordenadas por fecha descendente
    List<TransaccionFinanciera> findByUsuarioOrderByFechaDesc(Usuario usuario);
    
    // Buscar todas las transacciones de un usuario
    List<TransaccionFinanciera> findByUsuario(Usuario usuario);
    
    // Buscar transacciones por tipo y usuario
    List<TransaccionFinanciera> findByTipoAndUsuario(TipoTransaccion tipo, Usuario usuario);
    
    // Buscar transacciones por rango de fechas
    List<TransaccionFinanciera> findByUsuarioAndFechaBetween(
        Usuario usuario, 
        LocalDate fechaInicio, 
        LocalDate fechaFin
    );
    
    // Buscar transacciones por categoría
    List<TransaccionFinanciera> findByUsuarioAndCategoria(Usuario usuario, String categoria);
    
    // Query personalizada para obtener el total de transacciones por tipo
    @Query("SELECT t.tipo, SUM(t.monto) FROM TransaccionFinanciera t " +
           "WHERE t.usuario = :usuario AND t.fecha BETWEEN :fechaInicio AND :fechaFin " +
           "GROUP BY t.tipo")
    List<Object[]> findTotalsByTipoAndFechaBetween(
        @Param("usuario") Usuario usuario,
        @Param("fechaInicio") LocalDate fechaInicio,
        @Param("fechaFin") LocalDate fechaFin
    );
}

