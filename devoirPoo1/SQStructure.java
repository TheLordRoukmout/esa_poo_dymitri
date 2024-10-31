public abstract class SQStructure {
    protected int[] elements;
    protected int count;

    // Initialiser le tableau avec une taille donnée
    public SQStructure(int size) {
        elements = new int[size];
        count = 0;
    }

    // Agrandir le tableau lorsque nécessaire
    public void expandArray() {
        int newSize = elements.length * 2;
        int[] newElements = new int[newSize];
        for (int i = 0; i < elements.length; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }

    // Méthode pour ajouter un nombre à la fin du tableau
    public void Push(int valeur) {
        // Si le tableau est plein, on l'agrandit
        if (count == elements.length) {
            expandArray();
        }
        elements[count++] = valeur;
    }

    // Méthode pour retirer et obtenir le dernier élément ajouté
    public int Pop() {
        if (count == 0) {
            // Renvoie -1 si le tableau est vide
            return -1;
        }
        // Renvoie l'élément et décrémente count
        return elements[--count];
    }

    // Méthode pour connaître le nombre d'éléments actuels
    public int Count() {
        return count;
    }

    // Méthode pour connaître la capacité du tableau
    public int Size() {
        return elements.length;
    }

    public void Clear() {
        count = 0;
        elements = new int[elements.length];
    }

}

