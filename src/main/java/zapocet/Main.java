package zapocet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {

        boolean inProgress = true;
        int input;
        Liga liga = null;
        Klub klub;
        Zapas zapas;

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
                            liga.nahrajRozpis();
                        break;
                    case 2:
                        if (liga == null) {
                            System.out.println("Liga nebola inicializovana, pouzi akciu 1");
                        } else {
                            System.out.print("Zadaj cislo zapasu: ");
                            int cisloZapasu = Integer.parseInt(br.readLine());
                            Optional<Zapas> optZapas = liga.getRozpisZapasov().stream().filter(zapas1 -> zapas1.getId() == cisloZapasu).findFirst();
                            if (optZapas.isEmpty()) {
                                System.out.println("Zapas neexistuje. Cisla zapasov su v rozsahu 1 - "
                                        + liga.pocetZapasov());
                                break;
                            }
                            if (optZapas.get().isSpracovany()) {
                                System.out.println("Zapas cislo" + cisloZapasu + "bol odohrany");
                                break;
                            }
                            optZapas.get().vypisZapas();
                            System.out.print("Zadaj goly domacich: ");
                            int golyDom = Integer.parseInt(br.readLine());
                            System.out.print("Zadaj goly hosti: ");
                            int golyHostia = Integer.parseInt(br.readLine());
                            liga.odohrajZapas(cisloZapasu,golyDom,golyHostia);
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
                            liga.vypisZapasy();
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