import java.util.*;

public class DotComBust {

    private GameHelper helper = new GameHelper();
    private ArrayList<DotCom> dotComsList = new ArrayList<DotCom>();
    private int numOfGuesses = 0;

    private void setUpGame() {
        DotCom one = new DotCom();
        DotCom two = new DotCom();
        DotCom three = new DotCom();

        if (helper.getUserInput("Добавить свои сайты? (y - Да, n - Нет, использовать шаблон)").equals("y")) {
            one.setName(helper.getUserInput("Введите адрес первого сайта:"));
            two.setName(helper.getUserInput("Введите адрес второго сайта:"));
            three.setName(helper.getUserInput("Введите адрес третьего сайта:"));
        }
        else {
            one.setName("portal.usue.ru");
            two.setName("usue.ru");
            three.setName("portfolio.usue.ru");
        }

        System.out.println();

        dotComsList.add(one);
        dotComsList.add(two);
        dotComsList.add(three);

        System.out.println("Ваша задача - потопить три сайта:");
        System.out.printf("%s, %s и %s%n", one.getName(), two.getName(), three.getName());
        System.out.println();
        System.out.println("Попытайтесь их уничтожить за минимальное кол-во ходов!");
        System.out.println();

        for (DotCom dotComToSet : dotComsList) {
            ArrayList<String> newLocation = helper.placeDotCom(3);
            dotComToSet.setLocationCells(newLocation);
        }
    }

    private void startPlaying() {
        while(!dotComsList.isEmpty()) {
            String userGuess = helper.getUserInput("Сделайте ход:");
            checkUserGuess(userGuess);
        }
        finishGame();
    }

    private void checkUserGuess(String userGuess) {
        numOfGuesses++;
        String result = "Мимо";
        String out = result;
        for (DotCom dotComToTest : dotComsList) {
            result = dotComToTest.checkYourself(userGuess);

            if (result.equals("Попал")) {
                out = result;
                break;
            }
            if (result.equals("Потопил")) {
                out = String.format("Ура, ты потопил %s!%n", dotComToTest.getName());
                dotComsList.remove(dotComToTest);
                break;
            }
        }
        //showCoords();
        System.out.println(out);
    }

    private void finishGame() {
        System.out.println("Все сайты ушли ко дну, поздравляю!");
        System.out.println();
        if (numOfGuesses <= 18) {
            System.out.printf("Всего за %d ходов вы уничтожили сайты, отличный результат!%n", numOfGuesses);
        }
        else {
            System.out.printf("Вас почти не уничтожили! Однако за %d ходов вы успели нейтрализовать врагов.%n", numOfGuesses);
        }
    }

    private void showCoords() {
        for (DotCom site : dotComsList) {
            for (String coords : site.getLocationCells()) {
                System.out.print(coords + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        DotComBust game = new DotComBust();
        game.setUpGame();
        game.startPlaying();
    }
}
