package bandeau;

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
public class Scenario {

    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock verrouEnLecture = rwl.readLock();
    private final Lock verrouEnEcriture = rwl.writeLock();

    private final List<ScenarioElement> myElements = new LinkedList<>();

    /**
     * Ajouter un effect au scenario.
     *
     * @param e       l'effet à ajouter
     * @param repeats le nombre de répétitions pour cet effet
     */
    public void addEffect(Effect e, int repeats) {
        verrouEnEcriture.lock();
        try {
            myElements.add(new ScenarioElement(e, repeats));
        } finally {
            verrouEnEcriture.unlock();
        }
    }

    /**
     * Jouer ce scenario sur un bandeau
     *
     * @param b le bandeau ou s'afficher.
     */
    public void playOn(Bandeau b) {
        new Thread(
            // "lambda-expression"
            () -> {
                // On se synchronise sur le bandeau 
                // pour assurer l'exclusion mutuelle
                synchronized (b) {
                    play(b);
                }
            }
        ).start();
    }

    private void play(Bandeau b) {
        verrouEnLecture.lock();
        for (ScenarioElement element : myElements) {
            for (int repeats = 0; repeats < element.repeats; repeats++) {
                element.effect.playOn(b);
            }
        }
        verrouEnLecture.unlock();
    }
}
