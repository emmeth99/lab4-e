package lab4;

import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;
import lab4.gui.GomokuGUI;

public class GomokuMain {
    public static void main(String[] args) {
        final int PORT = args.length == 1 ? Integer.parseInt(args[0]) : 4000;

        GomokuClient gomokuClient = new GomokuClient(PORT);
        GomokuGameState gameState = new GomokuGameState(gomokuClient);
        GomokuGUI gui = new GomokuGUI(gomokuClient, gameState);
    }
}
