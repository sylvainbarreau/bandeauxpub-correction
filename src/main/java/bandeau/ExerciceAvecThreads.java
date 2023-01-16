package bandeau;

public class ExerciceAvecThreads {

    public static void main(String[] args) {
        ExerciceAvecThreads instance = new ExerciceAvecThreads();
        instance.exemple();
    }

    public void exemple() {

        Scenario s = makeScenario();
        // On cree les bandeaux
        var b1 = new BandeauVerrouillable();
        var b2 = new BandeauVerrouillable();
        var b3 = new BandeauVerrouillable();
        // On doit jouer le scénario en même temps sur les trois bandeaux
        s.playOn(b1);
        s.playOn(b2);
        // On rejoue le scénario sur b1 quand le premier jeu est fini
        s.playOn(b1);
        modifieScenario(s);
        // Le scénario joué sur b1 a-t-il été modifié ? pourquoi ?
        s.playOn(b3);
        System.out.println("CTRL-C pour terminer le programme");
    }

    private Scenario makeScenario() {
        // On crée un scenario
        Scenario s = new Scenario();
        // On lui ajoute des effets
        s.addEffect(new RandomEffect("Le jeu du pendu", 700), 1);
        // s.addEffect(new TeleType("Je m'affiche caractère par caractère", 100), 1);
        // s.addEffect(new Blink("Je clignote 10x", 100), 10);
        // s.addEffect(new Zoom("Je zoome", 50), 1);
        // s.addEffect(new FontEnumerator(10), 1);
        s.addEffect(new Rainbow("Comme c'est joli !", 30), 1);
        // s.addEffect(new Rotate("2 tours à droite", 180, 4000, true), 2);
        // s.addEffect(new Rotate("2 tours à gauche", 180, 4000, false), 2);
        return s;
    }

    private void modifieScenario(Scenario s) {
        System.out.println("On commence à modifier le scénario");
        // On ne peut pas changer un scénario quand il est en train de se jouer
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
        }
        s.addEffect(new FontEnumerator(10), 1);
        System.out.println("On a fini de modifier le scénario");
    }
}
