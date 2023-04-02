import components.Ecran;
import components.Voiture.Voiture;

class Main {
  public Main() {
    Runnable runnable = new Runnable() {
      public void run() {
        new Voiture();
      }
    };
    Thread thread = new Thread(runnable);
    thread.start();
  }

  public static void main(String[] args) {
    new Ecran();
    new Main();
  }
}