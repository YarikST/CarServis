package Data.BaseInit;

import Data.BaseInit.BaseWrite.BaseWrite;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by admin-iorigins on 26.11.16.
 */
public class TestContext {
    public static Path машина = Paths.get("/home/admin-iorigins/Робочий стіл/project/base/машина.txt");
    public static Path замовник = Paths.get("/home/admin-iorigins/Робочий стіл/project/base/замовник.txt");
    public static Path замовлення = Paths.get("/home/admin-iorigins/Робочий стіл/project/base/замовлення.txt");
    public static Path фірма = Paths.get("/home/admin-iorigins/Робочий стіл/project/base/фірма.txt");
    public static Path робітник = Paths.get("/home/admin-iorigins/Робочий стіл/project/base/робітник.txt");
    public static Path список_Робітників = Paths.get("/home/admin-iorigins/Робочий стіл/project/base/список_Робітників.txt");
    public static Path список_Послуг = Paths.get("/home/admin-iorigins/Робочий стіл/project/base/список_Послуг.txt");
    public static Path відгуки = Paths.get("/home/admin-iorigins/Робочий стіл/project/base/відгуки.txt");
    public static Path послуга = Paths.get("/home/admin-iorigins/Робочий стіл/project/base/список_Замовлень.txt");
    public static Path список_Замовлень = Paths.get("/home/admin-iorigins/Робочий стіл/project/base/список_Замовлень.txt");
    public static Path історія = Paths.get("/home/admin-iorigins/Робочий стіл/project/base/історія.txt");
    public static Path автентифікація = Paths.get("/home/admin-iorigins/Робочий стіл/project/base/автентифікація.txt");
    public static Path черга = Paths.get("/home/admin-iorigins/Робочий стіл/project/base/черга.txt");


    public static Path машинаДані = Paths.get("/home/admin-iorigins/Робочий стіл/project/base/дані/машина_дані.txt");
    public static Path замовникДані = Paths.get("/home/admin-iorigins/Робочий стіл/project/base/дані/замовникДані.txt");
    public static Path фірмаДані = Paths.get("/home/admin-iorigins/Робочий стіл/project/base/дані/фірмаДані.txt");
    public static Path робітникДані = Paths.get("/home/admin-iorigins/Робочий стіл/project/base/дані/робітникДані.txt");
    public static Path списокРобітниківДані = Paths.get("/home/admin-iorigins/Робочий стіл/project/base/дані/списокРобітниківДані.txt");
    public static Path списокПослугДані = Paths.get("/home/admin-iorigins/Робочий стіл/project/base/дані/списокПослугДані.txt");
    public static Path послугаДані = Paths.get("/home/admin-iorigins/Робочий стіл/project/base/дані/послугаДані.txt");
    public static Path список_ЗамовленьДані = Paths.get("/home/admin-iorigins/Робочий стіл/project/base/дані/список_ЗамовленьДані.txt");
    public static Path історіяДані = Paths.get("/home/admin-iorigins/Робочий стіл/project/base/дані/історіяДані.txt");
    public static Path автентифікаціяДані = Paths.get("/home/admin-iorigins/Робочий стіл/project/base/дані/автентифікаціяДані.txt");
    public static Path відгукиДані = Paths.get("/home/admin-iorigins/Робочий стіл/project/base/дані/відгукиДані.txt");
    public static Path замовленнДані = Paths.get("/home/admin-iorigins/Робочий стіл/project/base/дані/замовлення_Дані.txt");

    public static void main(String[] args) {


/*
        LinkedList<String> lc = new LinkedList<>();
        lc.add("Замовлення");

        lc.add( "id_фірми,"+
                "id_замовника,"+
                "id_послуги,"+
                "Дата_час,"+
                "id_відгука ");

        LinkedList<String> lp[] = new LinkedList[5];
        for (int i = 0; i < lp.length; i++) {
            lp[i] = new LinkedList<>();
        }

        lp[0].add("1");
        lp[0].add("2");
        lp[0].add("1");
        lp[0].add("2");
        lp[0].add("3");

        lp[1].add("1");
        lp[1].add("2");
        lp[1].add("1");
        lp[1].add("2");
        lp[1].add("3");

        lp[2].add("1");
        lp[2].add("2");
        lp[2].add("1");
        lp[2].add("2");
        lp[2].add("3");

        lp[3].add("\"2016-08-05 20:09:00\"");
        lp[3].add("\"2014-08-05 20:09:00\"");
        lp[3].add("\"2013-08-05 20:09:00\"");
        lp[3].add("\"2012-08-05 20:09:00\"");
        lp[3].add("\"2011-08-05 20:09:00\"");

        lp[4].add("1");
        lp[4].add("2");
        lp[4].add("1");
        lp[4].add("2");
        lp[4].add("3");







        RandomBaseWrite baseWrite = new RandomBaseWrite(new BaseWrite(замовленнДані), lc, lp);
        baseWrite.write();*/


         BaseWrite baseWrite = new BaseWrite(черга);

         baseWrite.addConst("Черга");
        baseWrite.addParam("id_Черги integer not null auto_increment,"+
                "id_Фірми integer not null,"+
                "id_Замовлення integer not null,"+
                "primary key(id_Черги)");

        baseWrite.write();

        /*baseWrite.addConst("Автентифікація");
        baseWrite.addParam("id_автентифікації integer not null auto_increment,"+
                "Email varchar(30) not null,"+
                "Password varchar(32) not null,"+
                "id_Машини integer not null,"+
                "primary key(id_автентифікації)");*/


        /* baseWrite.addConst("Історія");
        baseWrite.addParam("id_Історії integer not null auto_increment,"+
                "id_Автентифікації integer not null,"+
                "id_Машини integer not null,"+
                "primary key(id_Історії)");*/



        /*
 baseWrite.addConst("Список_Замовлень");
        baseWrite.addParam("id_Список_Замовлень integer not null auto_increment,"+
                "id_Робітника integer not null,"+
                "id_Замовлення integer not null,"+
                "Статус varchar(10) not null,"+
                "primary key(id_Список_Замовлень)");
                */

        /*
        baseWrite.addConst("Послуга");
        baseWrite.addParam("id_Послуга integer not null auto_increment,"+
                "Послуга varchar(35), "+
                "Ціна float not null, "+
                "Час_Роботи time not null, "+
                "primary key(id_Послуга)");
                */


        /*
        baseWrite.addConst("Відгуки");
        baseWrite.addParam("id_Відгуки integer not null auto_increment,"+
                "id_Фірми integer not null, "+
                "id_Замовлення integer not null, "+
                "Відгук varchar(256), "+
                "primary key(id_Відгуки)");
                */


        /*
         baseWrite.addConst("Список_Послуг");
        baseWrite.addParam("id_Список_Послуг integer not null auto_increment,"+
                "id_Фірми integer not null, "+
                "id_Послуги integer not null,"+
                "primary key(id_Список_Послуг)");
                */


        /*
        baseWrite.addConst("Список_Робітників");
        baseWrite.addParam("id_Список_Робітників integer not null auto_increment,"+
                "id_Робітника integer not null,"+
                "id_Фірми integer not null, "+
                "primary key(id_Список_Робітників)");
                */


        /*
        baseWrite.addConst("Робітник");
        baseWrite.addParam("id_Робітник integer not null auto_increment,"+
                "ПІП varchar(30) not null,"+
                "Освіта varchar(10) not null,"+
                "id_Фірми integer not null, "+
                "primary key(id_Робітник)");
                */


        /*
        baseWrite.addConst("Фірма");
        baseWrite.addParam("id_Фірма integer not null auto_increment,"+
                "Назва varchar(20) not null,"+
                "Місто varchar(20) not null,"+
                "Х float(11,8) not null,"+
                "У float(11,8). not null,"+
                "Графік varchar(128) not null,"+
                "id_Список_Робітників integer not null,"+
                "Власник varchar(15) not null, "+
                "primary key(id_Фірма)");
                */


        /*
        baseWrite.addConst("Замовлення");
        baseWrite.addParam("id_Замовлення integer not null auto_increment,"+
                "id_фірми integer not null,"+
                "id_замовника integer not null,"+
                "id_послуги integer not null,"+
                "Дата_час timestamp not null,"+
                "id_відгука integer, "+
                "Опис varchar(128) not null"+
                "primary key(id_Замовлення)");
                */


        /*
        baseWrite.addConst("Замовник");
        baseWrite.addParam("id_Замовника integer not null auto_increment," +
                        "id_Машини integer not null," +
                        "Email varchar(15) not null,"+
                "primary key(id_Замовника)");
                */

        /*
 baseWrite.addConst("Машина");
        baseWrite.addParam("id_Машини integer not null auto_increment," +
                "Марка varchar(20) not null," +
                "Модель varchar(20) not null," +
                "Рік_випуску date not null," +
                "Обєм_двигуна float(2,1) not null," +
                "Тип_палива varchar(10) not null," +
                "Передачі varchar(10) not null," +
                "primary key(id_Машини)");
                */

    }
}
