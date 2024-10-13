//J'ai fait l'exercice avec l'aide d'un développeur java car je suis complètement perdu, je préfère etre honnète.

public class Main {
    public static void main(String[] args) {
        Pile p = new Pile();

        for (int x = 0; x < 25; x++) {
            p.Empiler(x + 1);
        }

        System.out.printf("Taille de la pile : %d, Nombre d'éléments : %s%n", p.TaillePile(), p.NbElements());

        for (int x = 0; x < 26; x++) {
            System.out.printf("Element : %d, Taille pile : %d, Nombre d'éléments : %d\n", p.Depiler(), p.TaillePile(), p.NbElements());
        }
    }
}