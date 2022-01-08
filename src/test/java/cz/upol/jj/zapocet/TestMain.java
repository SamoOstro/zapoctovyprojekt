package cz.upol.jj.zapocet;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestMain {
    public Liga nahrajKluby(int pocet) throws IOException {
        Liga liga = new Liga(pocet);
        String pathToFile = "src/test/resources/zoznamKlubov.csv";
        File file = new File(pathToFile);
        String row;
        BufferedReader csvReader = null;
        try {
            csvReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while ((row = csvReader.readLine()) != null && pocet-- > 0) {
            liga.pridajKlubDoTabulky(new Klub(row));
        }
        csvReader.close();
        return liga;
    }

    @Test
    public void kontrolaPoctuKlubov() throws IOException {
        Liga slovakLiga = nahrajKluby(5);
        assertTrue(slovakLiga.plnaTabulka());
        IndexOutOfBoundsException exception =
                assertThrows(
                        IndexOutOfBoundsException.class,
                        () -> slovakLiga.pridajKlubDoTabulky(new Klub("Pov. Bystrica"))
                );
        assertEquals("Tabulka je plna", exception.getMessage());
        assertTrue(slovakLiga.plnaTabulka());
    }

    @Test
    public void kontrolaLigy() throws IOException {
        Liga slovakLiga = nahrajKluby(5);
        slovakLiga.inicializujRozpis();
        assertEquals(slovakLiga.pocetZapasov(), 20);
        slovakLiga.odohrajZapas(1, 21, 15);
        slovakLiga.odohrajZapas(2, 15, 36);
        slovakLiga.odohrajZapas(3, 21, 22);
        slovakLiga.odohrajZapas(4, 17, 28);
        slovakLiga.odohrajZapas(5, 20, 20);
        slovakLiga.odohrajZapas(6, 20, 100);
        assertEquals(slovakLiga.pocetZapasov(true), 6);
        assertEquals(1, slovakLiga.getTabulka().get(0).getId());
        assertEquals(4, slovakLiga.getTabulka().get(0).getPocetBodov());
        slovakLiga.vypisTabulku();

        slovakLiga.inicializujRozpis();
    }

    @Test
    public void kontrolaNacitaniaRozpisu() throws IOException {
        Liga liga6 = nahrajKluby(6);
        liga6.inicializujRozpis();
        assertEquals(30,liga6.pocetZapasov(false));

        Liga liga10 = nahrajKluby(10);
        liga10.inicializujRozpis();
        assertEquals(90,liga10.pocetZapasov(false));
    }


}
