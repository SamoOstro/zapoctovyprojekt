package zapocet;

public enum Hodnotenie {
    VYHRA(2),
    REMIZA(1),
    PREHRA(0);
    private final int body;

    Hodnotenie(int body) {
        this.body = body;
    }

    public int getBody() {
        return body;
    }
}
