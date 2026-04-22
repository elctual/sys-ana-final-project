/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import Model.DatabaseManager;
import java.sql.Connection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DatabaseManagerTest {

    public DatabaseManagerTest() {
    }

    @AfterAll
    public static void tearDownClass() {
        
        DatabaseManager.getInstance().closeConnection();
    }

    /**
     *  checks singleton pattern
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance testi");
        DatabaseManager result1 = DatabaseManager.getInstance();
        DatabaseManager result2 = DatabaseManager.getInstance();
        
        assertNotNull(result1, "Instance null olmamalı");
        assertSame(result1, result2, "Singleton yapısı gereği iki instance aynı olmalı");
    }

    /**
     * tests connection
     */
    @Test
    public void testGetConnection() {
        System.out.println("getConnection testi");
        DatabaseManager instance = DatabaseManager.getInstance();
        Connection result = instance.getConnection();
        
        assertNotNull(result, "Veritabanı bağlantısı kurulamadı (null)");
        
        try {
            assertFalse(result.isClosed(), "Bağlantı açık olmalıydı");
        } catch (Exception e) {
            fail("Bağlantı durumu kontrol edilirken hata oluştu: " + e.getMessage());
        }
    }

    @Test
    public void testCloseConnection() {
        System.out.println("closeConnection testi");
        DatabaseManager instance = DatabaseManager.getInstance();
        
        assertNotNull(instance.getConnection());
        
        instance.closeConnection();
        
        try {
            assertTrue(instance.getConnection().isClosed(), "Bağlantı başarıyla kapatılmalıydı");
        } catch (Exception e) {
            fail("Bağlantı kapatılırken hata oluştu");
        }
    }
}