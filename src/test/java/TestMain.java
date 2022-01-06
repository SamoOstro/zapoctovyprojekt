import cz.upol.jj.zapocet.Klub;
import cz.upol.jj.zapocet.Liga;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

//    private Liga nahrajKluby() {
//        Liga liga = new Liga(5);
//        liga.pridajKlubDoTabulky(new Klub("ŠK ZEMPLÍN Trebišov"));
//        liga.pridajKlubDoTabulky(new Klub("Tatran Prešov"));
//        liga.pridajKlubDoTabulky(new Klub("ŠKP Bratislava"));
//        liga.pridajKlubDoTabulky(new Klub("HK Košice"));
//        liga.pridajKlubDoTabulky(new Klub("Šaľa"));
//        return liga;
//    }

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
        slovakLiga.nahrajRozpis();
        assertEquals(slovakLiga.getNeodohraneZapasy().size(), 20);
        slovakLiga.odohrajZapas(1, 21, 15);
        slovakLiga.odohrajZapas(2, 15, 36);
        slovakLiga.odohrajZapas(3, 21, 22);
        slovakLiga.odohrajZapas(4, 17, 28);
        slovakLiga.odohrajZapas(5, 20, 20);
        slovakLiga.odohrajZapas(6, 20, 100);
        assertEquals(slovakLiga.getOdohraneZapasy().size(), 6);
        assertEquals(1, slovakLiga.getTabulka().get(0).getId());
        assertEquals(4, slovakLiga.getTabulka().get(0).getPocetBodov());
        slovakLiga.vypisTabulku();

        slovakLiga.nahrajRozpis();
    }

    @Test
    public void kontrolaNacitaniaRozpisu() throws IOException {
        Liga liga6 = nahrajKluby(6);
        liga6.nahrajRozpis();
        assertEquals(30,liga6.getNeodohraneZapasy().size());

        Liga liga10 = nahrajKluby(10);
        liga10.nahrajRozpis();
        assertEquals(90,liga10.getNeodohraneZapasy().size());
    }


}
