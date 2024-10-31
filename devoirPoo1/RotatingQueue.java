public class RotatingQueue extends Queue {
    // Appelle le constructeur de Queue pour initialiser la file rotative
    public RotatingQueue(int size) {
        super(size);
    }

    // Redéfinition de Push pour gérer la rotation si des cases sont libres au début
    public void Push(int valeur) {
        if (count == elements.length) {
            if (front > 0) {
                shiftElements();
            } else {
                expandArray();
            }
        }
        elements[count++] = valeur;
    }

    // Déplace les éléments vers le début si front est non nul
    private void shiftElements() {
        for (int i = front; i < count; i++) {
            elements[i - front] = elements[i];
        }
        count -= front;
        front = 0;
    }
}
