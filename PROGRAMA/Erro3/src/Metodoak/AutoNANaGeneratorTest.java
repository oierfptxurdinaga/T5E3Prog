package Metodoak;

import Metodoak.AutoNANaGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AutoNANaGeneratorTest {

    @Test
    public void testGenerarNANaUnico() throws Exception {
        // 1. Crear los mocks
        Connection mockConn = mock(Connection.class);
        PreparedStatement mockStmt = mock(PreparedStatement.class);
        ResultSet mockRs = mock(ResultSet.class);

        // 2. Configurar el comportamiento esperado
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockRs);
        
        // Simulamos que rs.next() funciona y que rs.getInt(1) devuelve 0 (el DNI no existe)
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt(1)).thenReturn(0);

        // 3. Ejecutar el método
        String nuevoNANa = AutoNANaGenerator.generarNANaUnico(mockConn);

        // 4. Verificaciones
        assertNotNull(nuevoNANa, "El DNI generado no debe ser nulo.");
        assertEquals(9, nuevoNANa.length(), "El DNI debe tener 9 caracteres (8 números + 1 letra).");
        assertTrue(Character.isLetter(nuevoNANa.charAt(8)), "El último carácter debe ser una letra.");
        
        // Verificar que se hizo la consulta a la base de datos
        verify(mockConn, times(1)).prepareStatement(anyString());
    }
}