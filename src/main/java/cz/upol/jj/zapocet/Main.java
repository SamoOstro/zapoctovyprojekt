package cz.upol.jj.zapocet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;


public class Main {

    public static void main(String[] args) {

        boolean inProgress = true;
        int input;
        Liga liga = null;
        try {
            int pocet = Liga.nacitajPocetKlubov();
            if (pocet < 5 && pocet > 10) {
                System.out.print("Nespravny pocet klubov (min:5 - max:10). Musis inicializovat ligu: Volba c.1");
            } else {
                liga = new Liga(pocet);
                liga.nacitajKluby();
                liga.nacitajRozpis();
            }
        } catch (IOException ex) {
            System.out.println("Datove subory neexistuju alebo su poskodene. Musis inicializovat ligu: Volba c.1");
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (inProgress) {
            try {

                System.out.println(System.lineSeparator()
                        + "Uživatel zadá číslo akcie: " +
                        "0 (exit), " +
                        "1 (Inicializuj Ligu), " +
                        "2 (Zapis vysledok), " +
                        "3 (Zoznam klubov), " +
                        "4 (Zoznam zapasov), " +
                        "5 (Tabulka) ");
                input = Integer.parseInt(br.readLine());

                switch (input) {
                    case 0:
                        if (liga != null) {
                            liga.ulozPocetKlubov();
                            liga.ulozKluby();
                            liga.ulozRozpis();
                        }
                        System.out.println("Dovidenia");
                        inProgress = false;
                        break;
                    case 1:
                        System.out.println("Inicializacia ligy");
                        System.out.print("Zadaj pocet klubov (min:5 - max:10): ");
                        int pocetKlubov;
                        pocetKlubov = Integer.parseInt(br.readLine());
                        if (pocetKlubov < 5 && pocetKlubov > 10) {
                            System.out.print("Zadal si nespravny pocet klubov (min:5 - max:10)");
                        } else {
                            liga = new Liga(pocetKlubov);
                            int i = 1;
                            while (i <= pocetKlubov) {
                                System.out.print("Zadaj nazov " + i + ". klubu: ");
                                liga.pridajKlubDoTabulky(new Klub(br.readLine()));
                                i++;
                            }
                            System.out.print("Liga je pripravena na start");
                        }
                        liga.inicializujRozpis();
                        break;
                    case 2:
                        if (liga == null) {
                            System.out.println("Liga nebola inicializovana, pouzi akciu 1");
                        } else {
                            System.out.print("Zobrazit zoznam neodohranych zapasov? (A/[N]): ");
                            if (br.readLine().toUpperCase(Locale.ROOT).equals("A")){
                                System.out.println("Aktualne neodohrane zapasy:");
                                liga.vypisNeodohraneZapasy();
                            }
                            System.out.print("Zadaj cislo zapasu: ");
                            int cisloZapasu = Integer.parseInt(br.readLine());
                            Zapas zapas = liga.getZapas(cisloZapasu);
                            if (zapas == null) {
                                System.out.println("Zapas neexistuje. Cisla zapasov su v rozsahu 1 - "
                                        + liga.pocetZapasov());
                                break;
                            }
                            if (zapas.isSpracovany()) {
                                System.out.println("Zapas cislo: " + cisloZapasu + " bol odohrany");
                                break;
                            }
                            zapas.vypisZapas();
                            System.out.printf("Zadaj goly klubu %s: ",zapas.getDomaci().getMeno() );
                            int golyDom = Integer.parseInt(br.readLine());
                            System.out.printf("Zadaj goly klubu %s: ", zapas.getHostia().getMeno());
                            int golyHostia = Integer.parseInt(br.readLine());
                            liga.odohrajZapas(cisloZapasu, golyDom, golyHostia);
                        }
                        break;
                    case 3:
                        if (liga == null) {
                            System.out.println("Liga nebola inicializovana, pouzi akciu 1");
                        } else {
                            System.out.println("Kluby zucastnene v lige: ");
                            liga.vypisKluby();
                        }
                        break;
                    case 4:
                        if (liga == null) {
                            System.out.println("Liga nebola inicializovana, pouzi akciu 1");
                        } else {
                            System.out.println("Zoznam zapasov: ");
                            liga.vypisVsetkyZapasy();
                        }
                        break;
                    case 5:
                        if (liga == null) {
                            System.out.println("Liga nebola inicializovana, pouzi akciu 1");
                        } else {
                            System.out.println("Tabulka: ");
                            liga.vypisTabulku();
                        }
                        break;

                }
            } catch (NumberFormatException | IOException ex) {
                if (ex instanceof NumberFormatException) {
                    System.out.println("Na vstupe musi byt cislo");
                } else {
                    System.out.println("Nespravny vstup");
                }
            }
        }
    }
}