package GameEngine;
import java.util.Random;
import java.util.Scanner;

public class BattleShip {

    static int userShips = 5;
    static int computerShips = 5;
    static Scanner input = new Scanner(System.in);
    // 2D array representing grid 10x10;
    static String[][] ocean = new String[10][10];
    static int[][] shipsCoords = new int[10][10];



    public static void main(String[] args) {

        System.out.println("\tWitaj piracie w bitwie statkow");
        System.out.println("\t*******************************");

        //print empty ocean
        System.out.println("\n");
        printOcean(ocean);
        System.out.println("\n");
        System.out.println("Rozmiesc swoje statki");
        System.out.println();

        //prompting user`s input for ships coordinates
        userInput();
        System.out.println("\n");
        // genereating computers coordainates for ships
        try {
            computerInput();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //printing ocean with placed ships by both users
        printOcean(ocean);
        System.out.println("\n");
        battle();

    }

    //this method recieves 2D array and generates grid table representing ocean.
// each start and end of row and collumn is marked with according number
    private static void printOcean(String[][] ocean){

        // hardcoding column numbers
        System.out.println("  0123456789");
        for (int row = 0; row < ocean.length; row++){
            //for each start of the row print row number and demarkation line represented with '|'
            System.out.print(row+"|");
            //for loop iterating throug inner arrays representing columns
            //if column equals null replace it with blank space
            for (int col = 0; col < ocean[row].length; col++){
                if(ocean[row][col] == null){
                    // if cell representing column in 2D array passed in as arg eaquals null print blank space of two characters
                    System.out.print(" ");
                }else{
                    //else print whatever is placed in array, which would be ships
                    System.out.print(ocean[row][col]);
                }
            }
            //printing end of each row with delimitere sign '|' and number of row
            System.out.println("|"+row);
        }
        //hardcoding columns numbers
        System.out.println("  0123456789");
    }

    //method takes two inputs representing coordinates for his ships via Scanner class
    private static void userInput() {
        //for each ship
        for (int i = 1; i <= userShips; i++) {
            //users prompt for X coordinates
            System.out.print("Statek "+i+" ");
            int x = userX();
            //users prompt for Y coordinates
            System.out.print("Statek "+i+" ");
            int y = userY();
            //marking users ships coords with
            shipsCoords[x][y] = 1;
            System.out.println("________________________________\n");
            try{
                //check if coords are availabel and are in bounds of ocean
                //if they are - place ship there
                //else try again
                if (ocean[x][y] == null) {
                    ocean[x][y] = "@";
                } else{
                    System.out.println("Te koordynaty sa zajete");
                    System.out.println("Podaj inne");
                    i-=1;
                    continue;
                }
            }catch (ArrayIndexOutOfBoundsException ex){
                System.out.println("Przekroczyles granice oceanu !");
                System.out.println("Sprobuj jeszcze raz");
                System.out.println();
                i-=1;
                continue;
            }
        }
    }
    private static void computerInput() throws InterruptedException {
        Random random = new Random();
        System.out.println("Rozmieszczanie statkow przez komputer");
        System.out.println(".....");
        //for every ship
        for (int i = 1; i<=computerShips; i++){
            //pseudorandomly generate numbers from 0 to 9 for X and Y coords
            int x = random.nextInt(10);
            int y = random.nextInt(10);
            //check if coords are availabel
            //if they are - place ship there
            //else try again
            //no need to check if coords are in bound of ocean because generator will not generate numbers that are out of bounds
            if(ocean[x][y]==null){
                ocean[x][y] = " ";
                System.out.println("Statek "+i+" rozmieszczony");
            }else{
                i-=1;
                continue;
            }
            //marking enemy ships coordinates
            shipsCoords[x][y] = 2;
        }
        System.out.println("Komputer zakonczyl rozmieszczanie statkow");
        System.out.println("\n_________________\n");
    }

    public static void usersShot(){

        System.out.println("Strzal:");
        int x = userX();
        System.out.println("Strzal:");
        int y = userY();
        int coords = 0;
        //iterating over array where ships indexes are stored and looking for match with users prompt
        try{
            for (int row = 0; row < shipsCoords.length; row++) {
                for (int col = 0; col < shipsCoords[row].length; col++) {
                    coords = shipsCoords[x][y];
                }
            }
        }catch (ArrayIndexOutOfBoundsException ex){
            System.out.println("Przekroczyles granice oceanu");
            System.out.println("Podaj koordynaty w zakresie od 0 do 9");
            System.out.println("Strzal:");
            x = userX();
            System.out.println("Strzal:");
            y = userY();
            for (int row = 0; row < shipsCoords.length; row++) {
                for (int col = 0; col < shipsCoords[row].length; col++) {
                    coords = shipsCoords[x][y];
                }
            }
        }
        System.out.println("Pocisk zostal wystrzelony.... iiiii");
        //if user`s input matches index containing int 2 he hits enemy ship
        if (coords == 2) {
            System.out.println("TRAFIONY!! Zatopiles statek wroga");
            //mark place of the shot
            ocean[x][y] = "x";
            //decrease number of enemy ships by 1
            computerShips--;
            return;
            //if user`s input matches index containing int 1 he hits his own ship
        } else if (coords == 1) {
            System.out.println("Amatorze! zatopiles swoj wlasny statek");
            //mark place of the shot
            ocean[x][y] = "?";
            //decrease number of users ships by 1
            userShips--;
            return;
        } else {
            //mark place of the shot
            ocean[x][y] = "-";
            System.out.println("Pudlo, szkoda");
            return;
        }
    }

    private static int userX(){
        // validating X coords input, input can`t be String
        System.out.print("Wprowadz wspolrzedna X :");
        int x = 0;
        do {
            while (!input.hasNextInt()) {
                String invalid = input.next();
                System.out.println("Bledne wprowadzenie");
                System.out.println("Musisz podac numer od 0 do 9");
                System.out.println("Sprobuj jeszcze raz");
                System.out.println("Jeszcze raz podaj wspolrzedna X: ");
            }
            x = input.nextInt();
        } while (x < 0);
        return x;
    }

    private static int userY(){
        // validating Y coords input, input can`t be String
        System.out.print("Podaj wspolrzedna Y :");
        int y = 0;
        do {
            while (!input.hasNextInt()) {
                String invalid = input.next();
                System.out.println("Bledne wprowadzenie");
                System.out.println("Musisz podac numer od 0 do 9");
                System.out.println("Sprobuj jeszcze raz");
                System.out.println("Jeszcze raz podaj wspolrzedna Y: ");
            }
            y = input.nextInt();
        } while (y < 0);;
        return y;
    }

    public static void computerShot() {
        Random random = new Random();
        System.out.println("Komputer wybiera wspolrzedne");
        //pseudorandomly generating X and Y coordinates for computesrs shots
        int x = random.nextInt(10);
        int y = random.nextInt(10);
        System.out.println("........");
        //storing computers generated coordinated into index
        int coords = shipsCoords[x][y];
        System.out.println("Strzela, iiiii");
        //interating over array looking for a match with computers coordinates and users ships
        for (int row = 0; row < shipsCoords.length; row++) {
            for (int col = 0; col < shipsCoords[row].length; col++) {
                if (coords == 2) {
                    //if computers input matches index containing int 2 he hits his own ship
                    System.out.println("HaHaHa... zatopil jeden ze swoich statkow. Amator nieprawdaz ;)");
                    //mark place of the shot
                    ocean[x][y] = "!";
                    //decrease number of enemy ships by 1
                    computerShips--;
                    return;
                    //if computers input matches index containing int 1 he hits his users ship
                } else if (coords == 1) {
                    System.out.println("TRAFIONY. Tracisz jeden ze swoich statkow");
                    //mark place of the shot
                    ocean[x][y] = "x";
                    //decrease number of users ships by 1
                    userShips--;
                    return;
                } else {
                    //mark place of the shot
                    ocean[x][y] = "-";
                    System.out.println("Na szczescie pudlo");
                    return;
                }
            }
        }
    }

    public static void battle(){
        //battle continues untill one of player lost all ships
        while(userShips > 0 || computerShips > 0){
            System.out.println("Twoja kolej");
            usersShot();
            System.out.println("\n");
            System.out.println("Kolej wroga");
            computerShot();
            System.out.println("\n");
            System.out.println("Liczba statkow przeciwnika: "+computerShips+" | Twoja liczba statkow: "+userShips);
            System.out.println();
            printOcean(ocean);
            if(userShips == 0){
                System.out.println("Wygral Komputer :/");
            }else if(computerShips == 0){
                System.out.println("Zwyciezyles !!!");
            }
        }
    }
}
