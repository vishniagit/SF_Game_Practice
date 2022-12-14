import static java.lang.String.format;

public class Battle {

    public void fight(Character hero, Character monster, Realm.FightCallback fightCallback){
            Runnable runnable = () -> {
                int turn = 1;
                boolean isFightEnded = false;
                while (!isFightEnded) {
                    System.out.println("Move" + " " + turn + ".");
                    if (turn++ % 2 != 0){
                        isFightEnded = makeHit(monster,hero,fightCallback);
                    }else {
                        isFightEnded = makeHit(hero,monster,fightCallback);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
    }
    //hit
    private Boolean makeHit(Character defender, Character attacker, Realm.FightCallback fightCallback) {
        int hit = attacker.attack();
        int defenderHP = defender.getHp() - hit;
        int lvlup = attacker.getLvl() * 100;
        if (hit == attacker.getStr()) {
            System.out.printf(format("%s Нанес удар в %d единиц!%n", attacker.getName(), hit));
            System.out.printf("%s осталось %d единиц здоровья...%n", defender.getName(), defenderHP);
        } else if (hit == attacker.getStr()*2) {
            System.out.printf(format("%s Нанес критичический удар в %d единиц!%n", attacker.getName(), hit));
            System.out.printf("у %s осталось %d единиц здоровья...%n", defender.getName(), defenderHP);
        }
        {
            System.out.printf("%s промахнулся!%n", attacker.getName());
        }
        if (defenderHP <= 0 && defender instanceof Hero) {
            System.out.println("Извините, вы пали в бою...");
            fightCallback.fightLost();
            return true;
        } else if(defenderHP <= 0) {
            System.out.printf("Враг повержен! Вы получаете %d опыт и %d золота%n", defender.getXp(), defender.getGold());
            attacker.setXp(attacker.getXp() + defender.getXp());
            attacker.setGold(attacker.getGold() + defender.getGold());
            //lvlup + anylogic
            if(attacker.getXp() >= lvlup ){
                attacker.setLvl(attacker.getLvl() + 1);
                System.out.printf("Вы получили новый уроверень %d %n", attacker.getLvl());
            }
            fightCallback.fightWin();
            return true;
        } else {
            //если защищающийся не повержен, то мы устанавливаем ему новый уровень здоровья
            defender.setHp(defenderHP);
            return false;
        }
    }
}
