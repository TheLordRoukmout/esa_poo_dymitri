public class Queue extends SQStructure {

    // Indice du premier élément de la file (FIFO)
    protected int front;

    // Appelle le constructeur de SQStructure pour initialiser la file
    public Queue(int size) {
        super(size);
        front = 0; // Commence au début
    }

    // Redéfinir Pop pour retirer l'élément le plus ancien ajouté (FIFO)
    public int Pop() {
        if (front == count) {
            // Si front atteint count, la file est vide
            return -1;
        }
        // Renvoie l'élément en front et avance front
        return elements[front++];
    }

    // Si la file devient vide après plusieurs Pops, réinitialiser front
    public void resetFrontIfEmpty() {
        // Plus d'éléments, on remet front et count à 0
        if (front == count) {
            front = 0;
            count = 0;
        }
    }
}
