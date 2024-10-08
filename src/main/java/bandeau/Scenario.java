package bandeau;
<<<<<<< HEAD
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
=======

>>>>>>> sb/correction-sb
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Classe utilitaire pour représenter la classe-association UML
 */
class ScenarioElement {

    Effect effect;
    int repeats;

    ScenarioElement(Effect e, int r) {
        effect = e;
        repeats = r;
    }
}

/**
 * Un scenario mémorise une liste d'effets, et le nombre de repetitions pour
 * chaque effet Un scenario sait se jouer sur un bandeau.
 */
public class Scenario extends Thread{

    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock verrouEnLecture = rwl.readLock();
    private final Lock verrouEnEcriture = rwl.writeLock();

    private final List<ScenarioElement> myElements = new LinkedList<>();
    private final ReentrantReadWriteLock verrouLE = new ReentrantReadWriteLock();
    //private final Lock verrouEnLecture = rwl.readLock();
    //private final Lock verrouEnEcriture = rwl.writeLock();
    /**
     * Ajouter un effect au scenario.
     *
     * @param e       l'effet à ajouter
     * @param repeats le nombre de répétitions pour cet effet
     */
    public void addEffect(Effect e, int repeats) {
<<<<<<< HEAD
        verrouLE.writeLock().lock();    
        myElements.add(new ScenarioElement(e, repeats));
       verrouLE.writeLock().unlock();
=======
        verrouEnEcriture.lock();
        try {
            myElements.add(new ScenarioElement(e, repeats));
        } finally {
            verrouEnEcriture.unlock();
        }
>>>>>>> sb/correction-sb
    }

    /**
     * Jouer ce scenario sur un bandeau
     *
     * @param b le bandeau ou s'afficher.
     */
    public void playOn(BandeauVerrouillable b) {
<<<<<<< HEAD
        Thread t = new Thread() {
            public void run() {
                b.verrouillage();
                
                try {
                    verrouLE.readLock().lock();
        System.out.println("Verrou de lecture acquis dans playOn. Compteur actuel : " + verrouLE.getReadLockCount());
            
=======
        new Thread(
            // "lambda-expression"
            () -> {
                b.verrouille();
                try {
                    play(b);
                } finally {
                    b.deverrouille();
                }
            }).start();
    }

    private void play(Bandeau b) {
        verrouEnLecture.lock();
>>>>>>> sb/correction-sb
        for (ScenarioElement element : myElements) {
            for (int repeats = 0; repeats < element.repeats; repeats++) {
                element.effect.playOn(b);
            }
        }
<<<<<<< HEAD
        verrouLE.readLock().unlock();
        System.out.println("Verrou de lecture libéré : " + verrouLE.getReadLockCount());

            } finally {
                b.deverrouillage();
            }
            }
        };
        t.start();
=======
        verrouEnLecture.unlock();
>>>>>>> sb/correction-sb
    }
    
}
