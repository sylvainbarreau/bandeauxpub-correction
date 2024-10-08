package bandeau;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BandeauVerrouillable extends Bandeau {
    private final Lock verrou = new ReentrantLock();
<<<<<<< HEAD
    public void verrouillage() {
        verrou.lock();
    }
    public void deverrouillage() {
        verrou.lock();
=======
    public void verrouille() {
        verrou.lock();
    }
    
    public void deverrouille() {
        verrou.unlock();
>>>>>>> sb/correction-sb
    }
}
