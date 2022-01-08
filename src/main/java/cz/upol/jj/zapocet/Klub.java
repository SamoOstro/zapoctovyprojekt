package cz.upol.jj.zapocet;

public class Klub {
    private static int counter = 1;
    private int id;
    private String meno;
    private int pocetBodov = 0;
    private int pocetVyhier = 0;
    private int pocetRemiz = 0;
    private int pocetPrehier = 0;
    private int streleneGoly = 0;
    private int inkasovaneGoly = 0;

    public Klub(String meno) {
        this.id = counter++;
        this.meno = meno;
    }

    public Klub(int id, String meno, int pocetBodov, int pocetVyhier, int pocetRemiz, int pocetPrehier,
                int streleneGoly, int inkasovaneGoly) {
        this.id = id;
        this.meno = meno;
        this.pocetBodov = pocetBodov;
        this.pocetVyhier = pocetVyhier;
        this.pocetRemiz = pocetRemiz;
        this.pocetPrehier = pocetPrehier;
        this.streleneGoly = streleneGoly;
        this.inkasovaneGoly = inkasovaneGoly;
    }

    public int getId() {
        return id;
    }

    public String getMeno() {
        return meno;
    }

    public int getPocetBodov() {
        return pocetBodov;
    }

    public int getPocetZapasov() {
        return pocetVyhier + pocetRemiz + pocetPrehier;
    }

    public int getPocetVyhier() {
        return pocetVyhier;
    }

    public int getPocetRemiz() {
        return pocetRemiz;
    }

    public int getPocetPrehier() {
        return pocetPrehier;
    }

    public int getStreleneGoly() {
        return streleneGoly;
    }

    public int getInkasovaneGoly() {
        return inkasovaneGoly;
    }

    public int getRozdielGolov() {
        return streleneGoly - inkasovaneGoly;
    }

    public void zapisVysledok(Hodnotenie body, int klubGoly, int superGoly) {
        pocetBodov = pocetBodov + body.getBody();
        streleneGoly = streleneGoly + klubGoly;
        inkasovaneGoly = inkasovaneGoly + superGoly;
        switch (body) {
            case VYHRA:
                pocetVyhier++;
                break;
            case REMIZA:
                pocetRemiz++;
                break;
            case PREHRA:
                pocetPrehier++;
                break;
        }
    }

    public static int porovnajKluby(Klub klub2, Klub klub1) {
        if (klub1.pocetBodov != klub2.pocetBodov) {
            return klub1.pocetBodov - klub2.pocetBodov;
        }
        if (klub1.getRozdielGolov() != klub2.getRozdielGolov()) {
            return klub1.getRozdielGolov() - klub2.getRozdielGolov();
        }
        if (klub1.streleneGoly != klub2.streleneGoly) {
            return klub1.streleneGoly - klub2.streleneGoly;
        }
        if (klub1.pocetVyhier != klub2.pocetVyhier) {
            return klub1.pocetVyhier - klub2.pocetVyhier;
        }
        if (klub1.pocetRemiz != klub2.pocetRemiz) {
            return klub1.pocetRemiz - klub2.pocetRemiz;
        }
        return 0;
    }
}
