package bandeau;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.LinkedList;

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
 * Un scenario mémorise une liste d'effets, et le nombre de repetitions pour chaque effet
 * Un scenario sait se jouer sur un bandeau.
 */
public class Scenario extends Thread{

    private final List<ScenarioElement> myElements = new LinkedList<>();
    private final ReentrantReadWriteLock verrouLE = new ReentrantReadWriteLock();
    //private final Lock verrouEnLecture = rwl.readLock();
    //private final Lock verrouEnEcriture = rwl.writeLock();
    /**
     * Ajouter un effect au scenario.
     *
     * @param e l'effet à ajouter
     * @param repeats le nombre de répétitions pour cet effet
     */
    public void addEffect(Effect e, int repeats) {
        verrouLE.writeLock().lock();    
        myElements.add(new ScenarioElement(e, repeats));
       verrouLE.writeLock().unlock();
    }

    /**
     * Jouer ce scenario sur un bandeau
     *
     * @param b le bandeau ou s'afficher.
     */
    public void playOn(BandeauVerrouillable b) {
        Thread t = new Thread() {
            public void run() {
                b.verrouillage();
                
                try {
                    verrouLE.readLock().lock();
        System.out.println("Verrou de lecture acquis dans playOn. Compteur actuel : " + verrouLE.getReadLockCount());
            
        for (ScenarioElement element : myElements) {
            for (int repeats = 0; repeats < element.repeats; repeats++) {
                element.effect.playOn(b);
            }
        }
        verrouLE.readLock().unlock();
        System.out.println("Verrou de lecture libéré : " + verrouLE.getReadLockCount());

            } finally {
                b.deverrouillage();
            }
            }
        };
        t.start();
    }
    
}
