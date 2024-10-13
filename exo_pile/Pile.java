public class Pile {
    private int[] pile;
    private int nbElements;

    public Pile() {
        pile = new int[10];
        nbElements = 0;
    }

    public void Empiler(int x) {
        if (nbElements == pile.length) {
            int[] newPile = new int[pile.length + 10];
            System.arraycopy(pile, 0, newPile, 0, pile.length);
            pile = newPile;
        }
        pile[nbElements] = x;
        nbElements++;
    }

    public int Depiler() {
        if (nbElements == 0) {
            System.out.println("La pile est vide");
            return -1;
        }
        int x = pile[nbElements - 1];
        nbElements--;
        if (pile.length - nbElements >= 10) {
            int[] newPile = new int[pile.length - 5];
            System.arraycopy(pile, 0, newPile, 0, newPile.length);
            pile = newPile;
        }
        return x;
    }

    public int TaillePile() {
        return pile.length;
    }

    public int NbElements() {
        return nbElements;
    }
}
