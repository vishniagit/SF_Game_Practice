
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Realm {
    private static BufferedReader br;
    private static Character player = null;
    private static Battle battle = null;
    private static Traider traider = null;
    private static String string;
    private static String money;

    public static void main(String[] args) {
        //Инициализируем BufferedReader
        br = new BufferedReader(new InputStreamReader(System.in));
        //Инициализируем класс для боя
        battle = new Battle();
        traider = new Traider();
        //Первое, что нужно сделать при запуске игры, это создать персонажа, поэтому мы предлагаем ввести его имя
        System.out.println("Введите имя персонажа:");
        //Далее ждем ввод от пользователя
        try {
            command(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void command(String string) throws IOException {
        //Если это первый запуск, то мы должны создать игрока, именем будет служить первая введенная строка из консоли
        if (player == null) {
            player = new Hero(
                    string,
                    200,
                    20,
                    20,
                    0,
                    0,
                    1
            );
            System.out.printf("Новый герой %s! %n", player.getName());
            //Menu
            printNavigation();
        }
        //Варианты для команд
        switch (string) {
            case "1": {
                //buy POTION
                printTrader();
            }
            break;
            case "2": {
                commitFight();
            }
            break;
            case "3":
                System.exit(1);
                break;
            case "выход":
                command("3");
                break;
            case "да":
                command("2");
                break;
            case "нет": {
                printNavigation();
                command(br.readLine());
            }
            case "Купить": {
                Traide();
            }
            case "Уйти":{
                printNavigation();
            }
        }
        //Снова ждем команды от пользователя
        command(br.readLine());
    }

    private static void Traide() {
        if (player.getGold() >= 10) {
            System.out.println( "-10 золота");
            player.setGold(-10);
            System.out.println(traider.sell(Traider.Goods.POTION));
        } else {
            System.out.println("У вас не хватает золото");
        }

    }

    private static void commitFight() {
        battle.fight(player, createMonster(), new FightCallback() {
        @Override
        public void fightWin() {
            System.out.println(String.format("%s победил! Теперь у вас %d опыта и %d золота, а также осталось %d едениц здоровья (Ваш уровень %d).", player.getName(), player.getXp(), player.getGold(), player.getHp(), player.getLvl()));
            System.out.println("Желаете продолжить поход или вернуться в город? (да/нет), закончить странствие (выход)");
            try {
                command(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void fightLost() {

        }
    });
    }

    private static Character createMonster() {


            int random = (int) (Math.random() * 10);

            if (random % 2 == 0) return new Goblin(
                    "Гоблин",
                    50,
                    20,
                    10,
                    100,
                    10,
                    1
            );
            else return new Skelet(
                    "Скелет",
                    45,
                    20,
                    20,
                    70,
                    20,
                    1
            );
        }

    private static void printNavigation() {
        System.out.println("Куда вы хотите пойти?");
        System.out.println("1. К Торговцу");
        System.out.println("2. В темный лес");
        System.out.println("3. Выход");
    }
    private static void printTrader() {
        System.out.println("Лечебное зелье (Купить/Уйти)");
        try {
            command(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface FightCallback {
        void fightWin();
        void fightLost();
    }
}