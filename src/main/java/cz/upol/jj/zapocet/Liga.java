package cz.upol.jj.zapocet;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Liga {
    private final int pocetKlubov;
    private List<Zapas> rozpisZapasov = new ArrayList<Zapas>();
    private List<Klub> tabulka = new ArrayList<Klub>();

    public Liga(int pocetKlubov) {
        this.pocetKlubov = pocetKlubov;
    }

    public int getPocetKlubov() {
        return pocetKlubov;
    }

    public Klub getKlub(int id) {
        Optional<Klub> findKlub = tabulka.stream().filter(klub -> klub.getId() == id).findFirst();
        if (findKlub.isPresent()) {
            return findKlub.get();
        }
        return null;
    }

    public void pridajKlubDoTabulky(Klub klub) {
        if (plnaTabulka()) {
            throw new IndexOutOfBoundsException("Tabulka je plna");
        }
        tabulka.add(klub);
    }

    public void vypisKluby() {
        for (int i = 1; i <= pocetKlubov; i++) {
            Klub klub = this.getKlub(i);
            System.out.println(klub.getId() + " " + klub.getMeno());
        }
    }

    public int pocetZapasov() {
        if (rozpisZapasov == null) {
            return 0;
        }
        return rozpisZapasov.size();
    }

    public int pocetZapasov(boolean odohrane) {
        if (rozpisZapasov == null) {
            return 0;
        }
        return rozpisZapasov.stream().filter(zapas1 -> zapas1.isSpracovany() == odohrane).toList().size();
    }

    public Zapas getZapas(int id) {
        Optional<Zapas> optZapas = rozpisZapasov.stream().filter(zapas1 -> zapas1.getId() == id).findFirst();
        if (optZapas.isEmpty()) {
            System.out.println("Zapas neexistuje. Cisla zapasov su v rozsahu 1 - "
                    + this.pocetZapasov());
            return null;
        }
        return optZapas.get();
    }

    public boolean pridajZapasDoRozpisu(Zapas zapas) {
        if (rozpisZapasov.contains(zapas)) {
            return false;
        }
        rozpisZapasov.add(zapas);
        return true;
    }

    public boolean odohrajZapas(int idZapasu, int golyDomaci, int golyHostia) {
        Optional<Zapas> zapasRozpis = rozpisZapasov.stream().filter(zapas -> zapas.getId() == idZapasu).findFirst();
        if (zapasRozpis.isEmpty()) {
            System.out.println("Zapas s cislom: " + idZapasu + " nebol najdeny.");
            return false;
        } else {
            boolean vysledok = zapasRozpis.get().vyhodnot(golyDomaci, golyHostia);
            usporiadajTabulku();
            return vysledok;
        }
    }

    public void vypisVsetkyZapasy() {
        for (Zapas zapas : rozpisZapasov) {
            zapas.vypisZapas();
        }
    }

    public void vypisOdohraneZapasy() {
        for (Zapas zapas : rozpisZapasov.stream().filter(zapas -> zapas.isSpracovany()).toList()) {
            zapas.vypisZapas();
        }
    }

    public void vypisNeodohraneZapasy() {
        for (Zapas zapas : rozpisZapasov.stream().filter(zapas -> !zapas.isSpracovany()).toList()) {
            zapas.vypisZapas();
        }
    }

    public List<Klub> getTabulka() {
        return tabulka;
    }

    public boolean plnaTabulka() {
        return tabulka.size() == pocetKlubov;
    }

    public void usporiadajTabulku() {
        tabulka.sort(Klub::porovnajKluby);
    }

    private void vypisRiadokTabulky(int poradie, Klub klub) {
        System.out.printf("%2d. %-35s%4d%4d%4d%4d%6d%6d%6d%4d%n",
                poradie, klub.getMeno(), klub.getPocetZapasov(), klub.getPocetVyhier(), klub.getPocetRemiz(),
                klub.getPocetPrehier(), klub.getStreleneGoly(), klub.getInkasovaneGoly(), klub.getRozdielGolov(),
                klub.getPocetBodov());
    }

    public void vypisTabulku() {
        System.out.printf("%2s  %-35s%4s%4s%4s%4s%6s%6s%6s%4s%n", " #", "Klub", "Z", "V", "R", "P", "GD", "GH", "GR", "B");
        System.out.printf("-----------------------------------------------------------------------------%n");
        tabulka.stream().forEach(klub -> vypisRiadokTabulky(tabulka.indexOf(klub) + 1, klub));
    }

    public void nahrajRozpis() throws IOException {
        String pathToFile = "src/main/resources/rozpis_" + getPocetKlubov() + ".csv";
        File file = new File(pathToFile);
        String row;
        BufferedReader csvReader = null;
        try {
            csvReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(";");
            Zapas zapas = new Zapas(getKlub(Integer.parseInt(data[1])),
                    getKlub(Integer.parseInt(data[2])),
                    Integer.parseInt(data[0])
            );
            pridajZapasDoRozpisu(zapas);
        }
        csvReader.close();
    }
}
