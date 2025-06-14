import java.awt.event.KeyEvent;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Finch bird = new Finch();

        GameEngine ge = new GameEngine();

        boolean left = false;

        while (!bird.getButton("A")) {
            if (ge.getInput().isKeyDown(KeyEvent.VK_W))
                bird.setMove("F", 100, 100);
            if (ge.getInput().isKeyDown(KeyEvent.VK_S))
                bird.setMove("B", 100, 100);
            if (ge.getInput().isKeyDown(KeyEvent.VK_A))
                bird.setTurn("L", 30, 100);
            if (ge.getInput().isKeyDown(KeyEvent.VK_D))
                bird.setTurn("R", 30, 100);
            if (ge.getInput().isKeyDown(KeyEvent.VK_1))
                for (int i = 1; i < 5; i++)
                    bird.setTail(i, 100, 0, 0);
            if (ge.getInput().isKeyDown(KeyEvent.VK_2))
                for (int i = 1; i < 5; i++)
                    bird.setTail(i, 0, 100, 0);
            if (ge.getInput().isKeyDown(KeyEvent.VK_3))
                for (int i = 1; i < 5; i++)
                    bird.setTail(i, 0, 0, 100);
            if (ge.getInput().isKeyDown(KeyEvent.VK_4))
                for (int i = 1; i < 5; i++)
                    bird.setTail(i, (int)(Math.random()*100), (int)(Math.random()*100), (int)(Math.random()*100));
            if (ge.getInput().isKeyDown(KeyEvent.VK_5))
                bird.setBeak(100, 0, 0);
            if (ge.getInput().isKeyDown(KeyEvent.VK_6))
                bird.setBeak(0, 100, 0);
            if (ge.getInput().isKeyDown(KeyEvent.VK_7))
                bird.setBeak(0, 0, 100);
            if (ge.getInput().isKeyDown(KeyEvent.VK_8))
                bird.setBeak((int)(Math.random()*100), (int)(Math.random()*100), (int)(Math.random()*100));
            if (ge.getInput().isKeyDown(KeyEvent.VK_Q))
                bird.setTurn("L", 360, 100);
            if (ge.getInput().isKeyDown(KeyEvent.VK_E))
                bird.playNote(100, .5);
            if (ge.getInput().isKeyDown(KeyEvent.VK_Z)) {
                var p1 = Arrays.asList(76, 12, 76, 12, 20, 12, 76, 12, 20, 12, 72, 12, 76, 12, 20, 12, 79, 12, 20, 36, 67, 12, 20, 36 );
                var p2 = Arrays.asList( 72, 12, 20, 24, 67, 12, 20, 24, 64, 12, 20, 24, 69, 12, 20, 12, 71, 12, 20, 12, 70, 12, 69, 12, 20, 12, 67, 16, 76, 16, 79, 16, 81, 12, 20, 12, 77, 12, 79, 12, 20, 12, 76, 12, 20, 12, 72, 12, 74, 12, 71, 12, 20, 24 );
                var p3 = Arrays.asList(48, 12, 20, 12, 79, 12, 78, 12, 77, 12, 75, 12, 60, 12, 76, 12, 53, 12, 68, 12, 69, 12, 72, 12, 60, 12, 69, 12, 72, 12, 74, 12, 48, 12, 20, 12, 79, 12, 78, 12, 77, 12, 75, 12, 55, 12, 76, 12, 20, 12, 84, 12, 20, 12, 84, 12, 84, 12);
                var p4 = Arrays.asList( 55, 12, 20, 12, 48, 12, 20, 12, 79, 12, 78, 12, 77, 12, 75, 12, 60, 12, 76, 12, 53, 12, 68, 12, 69, 12, 72, 12, 60, 12, 69, 12, 72, 12, 74, 12, 48, 12, 20, 12, 75, 24, 20, 12, 74, 24, 20, 12, 72, 24, 20, 12, 55, 12, 55, 12, 20, 12, 48, 12 );
                var p5 = Arrays.asList(72, 12, 72, 12, 20, 12, 72, 12, 20, 12, 72, 12, 74, 12, 20, 12, 76, 12, 72, 12, 20, 12, 69, 12, 67, 12, 20, 12, 43, 12, 20, 12, 72, 12, 72, 12, 20, 12, 72, 12, 20, 12, 72, 12, 74, 12, 76, 12, 55, 12, 20, 24, 48, 12, 20, 24, 43, 12, 20, 12, 72, 12, 72, 12, 20, 12, 72, 12, 20, 12, 72, 12, 74, 12, 20, 12, 76, 12, 72, 12, 20, 12, 69, 12, 67, 12, 20, 12, 43, 12, 20, 12, 76, 12, 76, 12, 20, 12, 76, 12, 20, 12, 72, 12, 76, 12, 20, 12, 79, 12, 20, 36, 67, 12, 20, 36);
                var p6 = Arrays.asList(76, 12, 72, 12, 20, 12, 67, 12, 55, 12, 20, 12, 68, 12, 20, 12, 69, 12, 77, 12, 53, 12, 77, 12, 69, 12, 60, 12, 53, 12, 20, 12, 71, 16, 81, 16, 81, 16, 81, 16, 79, 16, 77, 16, 76, 12, 72, 12, 55, 12, 69, 12, 67, 12, 60, 12, 55, 12, 20, 12, 76, 12, 72, 12, 20, 12, 67, 12, 55, 12, 20, 12, 68, 12, 20, 12, 69, 12, 77, 12, 53, 12, 77, 12, 69, 12, 60, 12, 53, 12, 20, 12, 71, 12, 77, 12, 20, 12, 77, 12, 77, 16, 76, 16, 74, 16, 72, 12, 64, 12, 55, 12, 64, 12, 60, 12, 20, 36);
                var p7 = Arrays.asList(72, 12, 20, 24, 67, 12, 20, 24, 64, 24, 69, 16, 71, 16, 69, 16, 68, 24, 70, 24, 68, 24, 67, 12, 65, 12, 67, 48);
                for (int i = 0; i < p1.size(); i+=2) {
                    bird.playNote(p1.get(i) + 32, p1.get(i+1));
                    bird.pause(.1);
                }
                for (int i = 0; i < p2.size(); i+=2) {
                    bird.playNote(p2.get(i) + 32, p2.get(i+1));
                    bird.pause(.1);
                }
                for (int i = 0; i < p2.size(); i+=2) {
                    bird.playNote(p2.get(i) + 32, p2.get(i+1));
                    bird.pause(.1);
                }
                for (int i = 0; i < p3.size(); i+=2) {
                    bird.playNote(p3.get(i) + 32, p3.get(i+1));
                    bird.pause(.1);
                }
                for (int i = 0; i < p4.size(); i+=2) {
                    bird.playNote(p4.get(i) + 32, p4.get(i+1));
                    bird.pause(.1);
                }
                for (int i = 0; i < p5.size(); i+=2) {
                    bird.playNote(p5.get(i) + 32, p5.get(i+1));
                    bird.pause(.1);
                }
                for (int i = 0; i < p2.size(); i+=2) {
                    bird.playNote(p2.get(i) + 32, p2.get(i+1));
                    bird.pause(.1);
                }
                for (int i = 0; i < p2.size(); i+=2) {
                    bird.playNote(p2.get(i) + 32, p2.get(i+1));
                    bird.pause(.1);
                }
                for (int i = 0; i < p6.size(); i+=2) {
                    bird.playNote(p6.get(i) + 32, p6.get(i+1));
                    bird.pause(.1);
                }
                for (int i = 0; i < p6.size(); i+=2) {
                    bird.playNote(p6.get(i) + 32, p6.get(i+1));
                    bird.pause(.1);
                }
                for (int i = 0; i < p5.size(); i+=2) {
                    bird.playNote(p5.get(i) + 32, p5.get(i+1));
                    bird.pause(.1);
                }
                for (int i = 0; i < p6.size(); i+=2) {
                    bird.playNote(p6.get(i) + 32, p6.get(i+1));
                    bird.pause(.1);
                }
                for (int i = 0; i < p7.size(); i+=2) {
                    bird.playNote(p7.get(i) + 32, p7.get(i+1));
                    bird.pause(.1);
                }
            }
            
            // int distance = bird.getDistance();
            // while (distance > 50) {
            //     System.out.println(bird.getDistance());
            //     bird.setMove("F", distance / 2.0, 75);
            //     if (bird.getDistance() > distance)
            //         break;
            //     distance = bird.getDistance();
            //     for (int i = 1; i < 5; i++)
            //         bird.setTail(i, (int)(Math.random()*100), (int)(Math.random()*100), (int)(Math.random()*100));
            //     bird.setBeak((int)(Math.random()*100), (int)(Math.random()*100), (int)(Math.random()*100));
            // }
            // bird.setTurn(left ? "L" : "R", 90, 100);
            // left = !left;
        }

        bird.stopAll();
        bird.disconnect();
    }
}
