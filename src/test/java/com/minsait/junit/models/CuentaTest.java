package com.minsait.junit.models;

import com.minsait.junit.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    Cuenta cuenta;

    TestInfo testInfo;

    TestReporter testReporter;


    @BeforeEach //se ejecuta despues del before All es el 2do
    void setUp(TestInfo testInfo, TestReporter testReporter) {
        this.cuenta = new Cuenta("Cesar", new BigDecimal(1000));
        testReporter.publishEntry("Iniciando el metodo");
        this.testInfo = testInfo;
        this.testReporter = testReporter;
        testReporter.publishEntry("Ejecutando: " +testInfo.getTestMethod().orElse(null).getName());


    }

    @AfterEach //se ejecuta despues de BeforeEach es el 3ro
    void tearDown() {
        System.out.println("Finalizando el metodo de prueba");

    }

    @BeforeAll //es el que se ejecuta antes de todos los metodos, es el 1ro
    static void beforeAll() {
        System.out.println("Iniciando todos los test");

    }

    @AfterAll
    static void afterAll() { //Se ejecuta al final de todos el ciclo de vida, es el 4to

        System.out.println("Finalizando todos los test");

    }
    @Nested


    class CuentaNombreSaldo{
        @Test
        void testNombreCuenta() {
            String esperado = "Cesar";
            String real = cuenta.getPersona();
            assertNotNull(real);
            assertEquals(esperado, real);
            assertTrue(esperado.equals(real));
        }

        @Test
        void testSaldoCuenta() {
            assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO)<0);
            assertEquals(1000, cuenta.getSaldo().intValue());

        }

        @Test
        void testReferencia() {
            Cuenta cuenta2 = new  Cuenta("Cesar", new BigDecimal(1000));
            assertEquals(cuenta2, cuenta);
        }
    }

    @Nested
    class  CuentaOperacionesTest{
        @Test
        void testRetirarCuenta(){
            cuenta.retirar((new BigDecimal(500)));
            assertNotNull(cuenta.getSaldo());
            assertEquals(500, cuenta.getSaldo().doubleValue());
        }
        @Test
        void testDepositarCuenta(){
            cuenta.depositar((new BigDecimal(500)));
            assertNotNull(cuenta.getSaldo());
            assertEquals(1500, cuenta.getSaldo().doubleValue());

        }
        @Test
        void testTransferirEntreCuentas(){
            Cuenta cuenta2 = new Cuenta("Bill Gates", new BigDecimal(10000));
            Banco banco = new Banco();
            banco.setNombre("BBVA");
            banco.tranferir(cuenta2, cuenta, new BigDecimal(9000));

            assertEquals(1000, cuenta2.getSaldo().intValue());
            assertEquals(10000, cuenta.getSaldo().doubleValue());
        }

    }

    @Test
    void testException(){
        Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
            cuenta.retirar(new BigDecimal(1500));
        });
        assertEquals("Dinero Insuficiente", exception.getMessage());
        //Evalular si la exception es correcta
        assertEquals(DineroInsuficienteException.class, exception.getClass());
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO)>0);

    }

    @Test
    @DisplayName("Test de relacion entre cuentas y banco")
    void testRelacionBancoCuentas() {
        Cuenta cuenta2 = new Cuenta("Bill Gates", new BigDecimal(10000));
        Banco banco = new Banco();
        banco.addCuenta(cuenta);
        banco.addCuenta(cuenta2);
        banco.setNombre("BBVA");
        banco.tranferir(cuenta2, cuenta, new BigDecimal(9000));

        assertAll( //acepta expresiones lamda
                () -> assertEquals(10000, cuenta.getSaldo().doubleValue()),
                () -> assertEquals(1000, cuenta2.getSaldo().intValue()),
                () -> assertEquals(2, banco.getCuentas().size()),
                () -> assertEquals(banco.getNombre(), cuenta.getBanco().getNombre()),
                () -> assertEquals(banco.getNombre(), cuenta2.getBanco().getNombre()),
                () -> assertEquals(cuenta.getPersona(), banco.getCuentas().get(0).getPersona())
        );


    }
}