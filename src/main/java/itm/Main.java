package itm;

import java.io.IOException;

public class Main {
    public static void main(String[] args)  throws IOException {
        System.out.println("Hello world!");

        // 1 - 1
        System.out.println("\nPOST users");
        One.sendPOST();

        // 1 - 2
        System.out.println("\nPUT user:");
        One.sendPUT();

        // 1 - 3
        System.out.println("\nDELETE user by id 1:");
        One.sendDELETE();

        // 1 - 4
        System.out.println("\nGET all users");
        One.sendGET();

        // 1 - 5
        System.out.println("\nGET user by id 9:");
        One.sendGET("9");

        // 1 - 6
        System.out.println("\nGET user by username Bret:");
        One.sendGET("username", "Bret");

        // 2
        System.out.println("\nWrite all comments to the last post of the user to file");
        Two.dumpComments("3");

        // 3
        System.out.println("\n");
        Three.getTodosFiltered("1", "completed", false);
    }
}