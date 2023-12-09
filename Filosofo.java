import java.util.concurrent.Semaphore;

public class Filosofo implements Runnable {
    private Semaphore[] garfos;
    private int id;

    public Filosofo(int id, Semaphore[] garfos) {
        this.id = id;
        this.garfos = garfos;
    }

   private void pegarGarfos() throws InterruptedException {
    // Adquire o semáforo do garfo à esquerda
    garfos[id].acquire();
    System.out.println("Filosofo " + (id + 1) + " pegou o garfo da esquerda.");
    Thread.sleep(1000);

    // Tenta adquirir o semáforo do garfo à direita
    if (!garfos[(id + 1) % garfos.length].tryAcquire()) {
        // Se não conseguir, libera o garfo da esquerda e espera um momento antes de tentar novamente
        garfos[id].release();
        System.out.println("Filosofo " + (id + 1) + " nao conseguiu pegar o garfo da direita. Soltou o garfo da esquerda.");
        Thread.sleep(1000);
        pegarGarfos(); // Tenta pegar os garfos novamente
    } else {
        System.out.println("Filosofo " + (id + 1) + " pegou o garfo da direita.");
    }
}

    private void soltarGarfos() throws InterruptedException {
    garfos[id].release();
    System.out.println("Filosofo " + (id + 1) + " soltou o garfo da esquerda.");
    Thread.sleep(1000); 
    garfos[(id + 1) % garfos.length].release();
    System.out.println("Filosofo " + (id + 1) + " soltou o garfo da direita.");
}
    private void comer() throws InterruptedException {
        System.out.println("Filosofo " + (id + 1) + " esta comendo.");
        Thread.sleep(2500); // Tempo simulando o ato de comer
    }

    private void pensar() throws InterruptedException {
        System.out.println("Filosofo " + (id + 1) + " esta pensando.");
        Thread.sleep(2500); // Tempo simulando o ato de pensar
    }

    @Override
    public void run() {
        try {
            while (true) {
                pensar();
                pegarGarfos();
                comer();
                soltarGarfos();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

