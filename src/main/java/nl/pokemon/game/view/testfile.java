package nl.pokemon.game.view;

public class testfile {

    public static void main(String[] args) {

        String fileStringName = "3_|1x2|stairs-up.png";
        int sqmSizeX = Integer.valueOf(fileStringName.replace("_|([0-9]+)", ""));
        System.out.println(sqmSizeX);
    }
}
