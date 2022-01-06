package zapocet;

public class Zapas {
    private static int counter = 1;
    private int id;
    private Klub domaci;
    private Klub hostia;
    private int kolo;
    private int golyDomaci;
    private int golyHostia;
    private boolean spracovany;


    public Zapas(Klub domaci, Klub hostia, int kolo) {
        this.id = counter++;
        this.domaci = domaci;
        this.hostia = hostia;
        this.kolo = kolo;
        this.golyDomaci = 0;
        this.golyHostia = 0;
        this.spracovany = false;
    }

    public boolean vyhodnot(int golyDomaci, int golyHostia) {
        if (spracovany) {
            return false;
        }
        this.golyDomaci = golyDomaci;
        this.golyHostia = golyHostia;
        if (golyDomaci == golyHostia) {
            domaci.zapisVysledok(Hodnotenie.REMIZA, golyDomaci, golyHostia);
            hostia.zapisVysledok(Hodnotenie.REMIZA, golyHostia, golyDomaci);
        } else if (golyDomaci > golyHostia) {
            domaci.zapisVysledok(Hodnotenie.VYHRA, golyDomaci, golyHostia);
            hostia.zapisVysledok(Hodnotenie.PREHRA, golyHostia, golyDomaci);
        } else {
            domaci.zapisVysledok(Hodnotenie.PREHRA, golyDomaci, golyHostia);
            hostia.zapisVysledok(Hodnotenie.VYHRA, golyHostia, golyDomaci);
        }
        spracovany = true;
        return true;
    }

    public void vypisZapas() {
        if (this.isSpracovany()) {
            System.out.println(this.getId() + ". "
                    + this.getDomaci().getMeno() + " : "
                    + this.getHostia().getMeno() + " "
                    + this.getGolyDomaci() + " : "
                    + this.getGolyHostia());
        } else {
            System.out.println(this.getId() + ". "
                    + this.getDomaci().getMeno() + " : "
                    + this.getHostia().getMeno());
        }
    }

    public Klub getDomaci() {
        return domaci;
    }

    public int getId() {
        return id;
    }

    public Klub getHostia() {
        return hostia;
    }

    public int getKolo() {
        return kolo;
    }

    public int getGolyDomaci() {
        return golyDomaci;
    }

    public int getGolyHostia() {
        return golyHostia;
    }

    public boolean isSpracovany() {
        return spracovany;
    }
}
