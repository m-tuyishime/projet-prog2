import components.Ecran;
import components.Voiture.Voiture;

class Main {
  public static void main(String[] args) {
    new Ecran();
    Runnable runnable = new Runnable() {
      public void run() {
        // Your code here
        new Voiture();
      }
    };
    Thread thread = new Thread(runnable);
    thread.start();
  }
}