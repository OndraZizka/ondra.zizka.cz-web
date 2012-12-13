package cz.oz.web.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Ondrej Zizka
 */
public class Svatek {

    public static final Map<String, String> SVATKY = new HashMap();

    /**
     * Najde jmeno podle data.
     */
    public static final String getSvatekByDate( Date date ) {
        return SVATKY.get( date.getMonth() + "/" + date.getDay() );
    }

    static {
        SVATKY.put( "1/1", "Nový rok" );
        SVATKY.put( "1/2", "Karina" );
        SVATKY.put( "1/3", "Radmila" );
        SVATKY.put( "1/4", "Diana" );
        SVATKY.put( "1/5", "Dalimil" );
        SVATKY.put( "1/6", "Tři králové" );
        SVATKY.put( "1/7", "Vilma" );
        SVATKY.put( "1/8", "Čestmír" );
        SVATKY.put( "1/9", "Vladan" );
        SVATKY.put( "1/10", "Břetislav" );
        SVATKY.put( "1/11", "Bohdana" );
        SVATKY.put( "1/12", "Pravoslav" );
        SVATKY.put( "1/13", "Edita" );
        SVATKY.put( "1/14", "Radovan" );
        SVATKY.put( "1/15", "Alice" );
        SVATKY.put( "1/16", "Ctirad" );
        SVATKY.put( "1/17", "Drahoslav" );
        SVATKY.put( "1/18", "Vladislav" );
        SVATKY.put( "1/19", "Doubravka" );
        SVATKY.put( "1/20", "Ilona" );
        SVATKY.put( "1/21", "Běla" );
        SVATKY.put( "1/22", "Slavomír" );
        SVATKY.put( "1/23", "Zdeněk" );
        SVATKY.put( "1/24", "Milena" );
        SVATKY.put( "1/25", "Miloš" );
        SVATKY.put( "1/26", "Zora" );
        SVATKY.put( "1/27", "Ingrid" );
        SVATKY.put( "1/28", "Otýlie" );
        SVATKY.put( "1/29", "Zdislava" );
        SVATKY.put( "1/30", "Robin" );
        SVATKY.put( "1/31", "Marika" );
        SVATKY.put( "2/1", "Hynek" );
        SVATKY.put( "2/2", "Nela" );
        SVATKY.put( "2/3", "Blažej" );
        SVATKY.put( "2/4", "Jarmila" );
        SVATKY.put( "2/5", "Dobromila" );
        SVATKY.put( "2/6", "Vanda" );
        SVATKY.put( "2/7", "Veronika" );
        SVATKY.put( "2/8", "Milada" );
        SVATKY.put( "2/9", "Apolena" );
        SVATKY.put( "2/10", "Mojmír" );
        SVATKY.put( "2/11", "Božena" );
        SVATKY.put( "2/12", "Slavěna" );
        SVATKY.put( "2/13", "Věnceslav" );
        SVATKY.put( "2/14", "Valentýn" );
        SVATKY.put( "2/15", "Jiřina" );
        SVATKY.put( "2/16", "Ljuba" );
        SVATKY.put( "2/17", "Miloslava" );
        SVATKY.put( "2/18", "Gizela" );
        SVATKY.put( "2/19", "Patrik" );
        SVATKY.put( "2/20", "Oldřich" );
        SVATKY.put( "2/21", "Lenka" );
        SVATKY.put( "2/22", "Petr" );
        SVATKY.put( "2/23", "Svatopluk" );
        SVATKY.put( "2/24", "Matěj" );
        SVATKY.put( "2/25", "Liliana" );
        SVATKY.put( "2/26", "Dora" );
        SVATKY.put( "2/27", "Alexandr" );
        SVATKY.put( "2/28", "Lumír" );
        SVATKY.put( "2/29", "Horymír" );
        SVATKY.put( "3/1", "Bedřich" );
        SVATKY.put( "3/2", "Anežka" );
        SVATKY.put( "3/3", "Kamil" );
        SVATKY.put( "3/4", "Stela" );
        SVATKY.put( "3/5", "Kazimír" );
        SVATKY.put( "3/6", "Miroslav" );
        SVATKY.put( "3/7", "Tomáš" );
        SVATKY.put( "3/8", "Gabriela" );
        SVATKY.put( "3/9", "Františka" );
        SVATKY.put( "3/10", "Viktorie" );
        SVATKY.put( "3/11", "Anděla" );
        SVATKY.put( "3/12", "Řehoř" );
        SVATKY.put( "3/13", "Růžena" );
        SVATKY.put( "3/14", "Růt" );
        SVATKY.put( "3/15", "Ida" );
        SVATKY.put( "3/16", "Elena" );
        SVATKY.put( "3/17", "Vlastimil" );
        SVATKY.put( "3/18", "Eduard" );
        SVATKY.put( "3/19", "Josef" );
        SVATKY.put( "3/20", "Světlana" );
        SVATKY.put( "3/21", "Radek" );
        SVATKY.put( "3/22", "Leona" );
        SVATKY.put( "3/23", "Ivona" );
        SVATKY.put( "3/24", "Gabriel" );
        SVATKY.put( "3/25", "Marian" );
        SVATKY.put( "3/26", "Emanuel" );
        SVATKY.put( "3/27", "Dita" );
        SVATKY.put( "3/28", "Soňa" );
        SVATKY.put( "3/29", "Tatana" );
        SVATKY.put( "3/30", "Arnošt" );
        SVATKY.put( "3/31", "Kvido" );
        SVATKY.put( "4/1", "Hugo" );
        SVATKY.put( "4/2", "Erika" );
        SVATKY.put( "4/3", "Richard" );
        SVATKY.put( "4/4", "Ivana" );
        SVATKY.put( "4/5", "Miroslava" );
        SVATKY.put( "4/6", "Vendula" );
        SVATKY.put( "4/7", "Heřman" );
        SVATKY.put( "4/8", "Ema" );
        SVATKY.put( "4/9", "Dušan" );
        SVATKY.put( "4/10", "Darja" );
        SVATKY.put( "4/11", "Izabela" );
        SVATKY.put( "4/12", "Julius" );
        SVATKY.put( "4/13", "Aleš" );
        SVATKY.put( "4/14", "Vincenc" );
        SVATKY.put( "4/15", "Anastázie" );
        SVATKY.put( "4/16", "Irena" );
        SVATKY.put( "4/17", "Rudolf" );
        SVATKY.put( "4/18", "Valérie" );
        SVATKY.put( "4/19", "Rostislav" );
        SVATKY.put( "4/20", "Marcela" );
        SVATKY.put( "4/21", "Alexandra" );
        SVATKY.put( "4/22", "Evženie" );
        SVATKY.put( "4/23", "Vojtěch" );
        SVATKY.put( "4/24", "Jiří" );
        SVATKY.put( "4/25", "Marek" );
        SVATKY.put( "4/26", "Oto" );
        SVATKY.put( "4/27", "Jaroslav" );
        SVATKY.put( "4/28", "Vlastislav" );
        SVATKY.put( "4/29", "Robert" );
        SVATKY.put( "4/30", "Blahoslav" );
        SVATKY.put( "5/1", "Svátek práce" );
        SVATKY.put( "5/2", "Zigmund" );
        SVATKY.put( "5/3", "Alexej" );
        SVATKY.put( "5/4", "Květoslav" );
        SVATKY.put( "5/5", "Klaudia" );
        SVATKY.put( "5/6", "Radoslav" );
        SVATKY.put( "5/7", "Stanislav" );
        SVATKY.put( "5/8", "Ctibor" );
        SVATKY.put( "5/9", "Hermus" );
        SVATKY.put( "5/10", "Blažena" );
        SVATKY.put( "5/11", "Svatava" );
        SVATKY.put( "5/12", "Pankrác" );
        SVATKY.put( "5/13", "Servác" );
        SVATKY.put( "5/14", "Bonifác" );
        SVATKY.put( "5/15", "Žofie" );
        SVATKY.put( "5/16", "Přemysl" );
        SVATKY.put( "5/17", "Aneta" );
        SVATKY.put( "5/18", "Nataša" );
        SVATKY.put( "5/19", "Ivo" );
        SVATKY.put( "5/20", "Zbyšek" );
        SVATKY.put( "5/21", "Monika" );
        SVATKY.put( "5/22", "Emil" );
        SVATKY.put( "5/23", "Vladimír" );
        SVATKY.put( "5/24", "Jana" );
        SVATKY.put( "5/25", "Viola" );
        SVATKY.put( "5/26", "Filip" );
        SVATKY.put( "5/27", "Valdemar" );
        SVATKY.put( "5/28", "Vilem" );
        SVATKY.put( "5/29", "Maxmilián" );
        SVATKY.put( "5/30", "Ferdinand" );
        SVATKY.put( "5/31", "Kamila" );
        SVATKY.put( "6/1", "Laura" );
        SVATKY.put( "6/2", "Jarmil" );
        SVATKY.put( "6/3", "Tamara" );
        SVATKY.put( "6/4", "Dalibor" );
        SVATKY.put( "6/5", "Dobroslav" );
        SVATKY.put( "6/6", "Norbert" );
        SVATKY.put( "6/7", "Iveta" );
        SVATKY.put( "6/8", "Medard" );
        SVATKY.put( "6/9", "Stanislava" );
        SVATKY.put( "6/10", "Gita" );
        SVATKY.put( "6/11", "Bruno" );
        SVATKY.put( "6/12", "Antonie" );
        SVATKY.put( "6/13", "Antonín" );
        SVATKY.put( "6/14", "Roland" );
        SVATKY.put( "6/15", "Vít" );
        SVATKY.put( "6/16", "Zbyněk" );
        SVATKY.put( "6/17", "Adolf" );
        SVATKY.put( "6/18", "Milan" );
        SVATKY.put( "6/19", "Leoš" );
        SVATKY.put( "6/20", "Květa" );
        SVATKY.put( "6/21", "Alois" );
        SVATKY.put( "6/22", "Pavla" );
        SVATKY.put( "6/23", "Zdeňka" );
        SVATKY.put( "6/24", "Jan" );
        SVATKY.put( "6/25", "Ivan" );
        SVATKY.put( "6/26", "Adriana" );
        SVATKY.put( "6/27", "Ladislav" );
        SVATKY.put( "6/28", "Lubomír" );
        SVATKY.put( "6/29", "Petr a Pavel" );
        SVATKY.put( "6/30", "Šárka" );
        SVATKY.put( "7/1", "Jaroslava" );
        SVATKY.put( "7/2", "Patrície" );
        SVATKY.put( "7/3", "Radomír" );
        SVATKY.put( "7/4", "Prokop" );
        SVATKY.put( "7/5", "Cyril a Metoděj" );
        SVATKY.put( "7/6", "Mistr Jan Hus" );
        SVATKY.put( "7/7", "Bohuslava" );
        SVATKY.put( "7/8", "Nora" );
        SVATKY.put( "7/9", "Drahoslava" );
        SVATKY.put( "7/10", "Libuše" );
        SVATKY.put( "7/11", "Olga" );
        SVATKY.put( "7/12", "Bořek" );
        SVATKY.put( "7/13", "Markéta" );
        SVATKY.put( "7/14", "Karolína" );
        SVATKY.put( "7/15", "Jindřich" );
        SVATKY.put( "7/16", "Luboš" );
        SVATKY.put( "7/17", "Martina" );
        SVATKY.put( "7/18", "Drahomíra" );
        SVATKY.put( "7/19", "Čenek" );
        SVATKY.put( "7/20", "Ilja" );
        SVATKY.put( "7/21", "Vítězslav" );
        SVATKY.put( "7/22", "Magdalena" );
        SVATKY.put( "7/23", "Libor" );
        SVATKY.put( "7/24", "Kristýna" );
        SVATKY.put( "7/25", "Jakub" );
        SVATKY.put( "7/26", "Anna" );
        SVATKY.put( "7/27", "Věroslav" );
        SVATKY.put( "7/28", "Viktor" );
        SVATKY.put( "7/29", "Marta" );
        SVATKY.put( "7/30", "Bořivoj" );
        SVATKY.put( "7/31", "Ignác" );
        SVATKY.put( "8/1", "Oskar" );
        SVATKY.put( "8/2", "Gustav" );
        SVATKY.put( "8/3", "Miluše" );
        SVATKY.put( "8/4", "Dominik" );
        SVATKY.put( "8/5", "Kristián a Milivoj" );
        SVATKY.put( "8/6", "Oldřiska" );
        SVATKY.put( "8/7", "Lada" );
        SVATKY.put( "8/8", "Soběslav" );
        SVATKY.put( "8/9", "Roman" );
        SVATKY.put( "8/10", "Vavřinec" );
        SVATKY.put( "8/11", "Zuzana" );
        SVATKY.put( "8/12", "Klára" );
        SVATKY.put( "8/13", "Alena" );
        SVATKY.put( "8/14", "Alan a Sylva" );
        SVATKY.put( "8/15", "Hana" );
        SVATKY.put( "8/16", "Jáchym" );
        SVATKY.put( "8/17", "Petra" );
        SVATKY.put( "8/18", "Helena" );
        SVATKY.put( "8/19", "Ludvík" );
        SVATKY.put( "8/20", "Bernard" );
        SVATKY.put( "8/21", "Johana" );
        SVATKY.put( "8/22", "Bohuslav" );
        SVATKY.put( "8/23", "Sandra" );
        SVATKY.put( "8/24", "Bartoloměj" );
        SVATKY.put( "8/25", "Radim" );
        SVATKY.put( "8/26", "Luděk" );
        SVATKY.put( "8/27", "Otakar" );
        SVATKY.put( "8/28", "Augustýn" );
        SVATKY.put( "8/29", "Evelína" );
        SVATKY.put( "8/30", "Vladěna" );
        SVATKY.put( "8/31", "Pavlína" );
        SVATKY.put( "9/1", "Linda" );
        SVATKY.put( "9/2", "Adéla" );
        SVATKY.put( "9/3", "Bronislav" );
        SVATKY.put( "9/4", "Jindřiška" );
        SVATKY.put( "9/5", "Boris" );
        SVATKY.put( "9/6", "Boleslav" );
        SVATKY.put( "9/7", "Regina" );
        SVATKY.put( "9/8", "Mariana" );
        SVATKY.put( "9/9", "Daniela" );
        SVATKY.put( "9/10", "Irma" );
        SVATKY.put( "9/11", "Denisa" );
        SVATKY.put( "9/12", "Marie" );
        SVATKY.put( "9/13", "Lubor" );
        SVATKY.put( "9/14", "Radka" );
        SVATKY.put( "9/15", "Jolana" );
        SVATKY.put( "9/16", "Ludmila" );
        SVATKY.put( "9/17", "Naděžda" );
        SVATKY.put( "9/18", "Kryštof" );
        SVATKY.put( "9/19", "Zita" );
        SVATKY.put( "9/20", "Oleg" );
        SVATKY.put( "9/21", "Matouš" );
        SVATKY.put( "9/22", "Darina" );
        SVATKY.put( "9/23", "Berta" );
        SVATKY.put( "9/24", "Jaromír" );
        SVATKY.put( "9/25", "Zlata" );
        SVATKY.put( "9/26", "Andrea" );
        SVATKY.put( "9/27", "Jonáš" );
        SVATKY.put( "9/28", "Václav" );
        SVATKY.put( "9/29", "Michal" );
        SVATKY.put( "9/30", "Jeroným" );
        SVATKY.put( "10/1", "Igor" );
        SVATKY.put( "10/2", "Olivie" );
        SVATKY.put( "10/3", "Bohumil" );
        SVATKY.put( "10/4", "František" );
        SVATKY.put( "10/5", "Eliška" );
        SVATKY.put( "10/6", "Hanuš" );
        SVATKY.put( "10/7", "Justýna" );
        SVATKY.put( "10/8", "Věra" );
        SVATKY.put( "10/9", "Štefan" );
        SVATKY.put( "10/10", "Marina" );
        SVATKY.put( "10/11", "Andrej" );
        SVATKY.put( "10/12", "Marcel" );
        SVATKY.put( "10/13", "Renáta" );
        SVATKY.put( "10/14", "Agáta" );
        SVATKY.put( "10/15", "Tereza" );
        SVATKY.put( "10/16", "Havel" );
        SVATKY.put( "10/17", "Hedvika" );
        SVATKY.put( "10/18", "Lukáš" );
        SVATKY.put( "10/19", "Michaela" );
        SVATKY.put( "10/20", "Vendelín" );
        SVATKY.put( "10/21", "Brigita" );
        SVATKY.put( "10/22", "Sabina" );
        SVATKY.put( "10/23", "Teodora" );
        SVATKY.put( "10/24", "Nina" );
        SVATKY.put( "10/25", "Beáta" );
        SVATKY.put( "10/26", "Erik" );
        SVATKY.put( "10/27", "Šarlota" );
        SVATKY.put( "10/28", "Státní svátek" );
        SVATKY.put( "10/29", "Silvie" );
        SVATKY.put( "10/30", "Tadeáš" );
        SVATKY.put( "10/31", "Štěpánka" );
        SVATKY.put( "11/1", "Felix" );
        SVATKY.put( "11/2", "Památka zesnulých" );
        SVATKY.put( "11/3", "Hubert" );
        SVATKY.put( "11/4", "Karel" );
        SVATKY.put( "11/5", "Miriam" );
        SVATKY.put( "11/6", "Libena" );
        SVATKY.put( "11/7", "Saskia" );
        SVATKY.put( "11/8", "Bohumír" );
        SVATKY.put( "11/9", "Bohdan" );
        SVATKY.put( "11/10", "Evžen" );
        SVATKY.put( "11/11", "Martin" );
        SVATKY.put( "11/12", "Benedikt" );
        SVATKY.put( "11/13", "Tibor" );
        SVATKY.put( "11/14", "Sáva" );
        SVATKY.put( "11/15", "Leopold" );
        SVATKY.put( "11/16", "Otmar" );
        SVATKY.put( "11/17", "Mahulena" );
        SVATKY.put( "11/18", "Romana" );
        SVATKY.put( "11/19", "Alžbeta" );
        SVATKY.put( "11/20", "Nikola" );
        SVATKY.put( "11/21", "Albert" );
        SVATKY.put( "11/22", "Cecílie" );
        SVATKY.put( "11/23", "Klement" );
        SVATKY.put( "11/24", "Emílie" );
        SVATKY.put( "11/25", "Kateřina" );
        SVATKY.put( "11/26", "Artur" );
        SVATKY.put( "11/27", "Xenie" );
        SVATKY.put( "11/28", "René" );
        SVATKY.put( "11/29", "Zina" );
        SVATKY.put( "11/30", "Ondřej" );
        SVATKY.put( "12/1", "Iva" );
        SVATKY.put( "12/2", "Blanka" );
        SVATKY.put( "12/3", "Svatoslav" );
        SVATKY.put( "12/4", "Barbora" );
        SVATKY.put( "12/5", "Jitka" );
        SVATKY.put( "12/6", "Mikuláš" );
        SVATKY.put( "12/7", "Ambrož" );
        SVATKY.put( "12/8", "Květoslava" );
        SVATKY.put( "12/9", "Vratislav" );
        SVATKY.put( "12/10", "Julie" );
        SVATKY.put( "12/11", "Dana" );
        SVATKY.put( "12/12", "Simona" );
        SVATKY.put( "12/13", "Lucie" );
        SVATKY.put( "12/14", "Lýdie" );
        SVATKY.put( "12/15", "Radana" );
        SVATKY.put( "12/16", "Albína" );
        SVATKY.put( "12/17", "Daniel" );
        SVATKY.put( "12/18", "Miloslav" );
        SVATKY.put( "12/19", "Ester" );
        SVATKY.put( "12/20", "Dagmar" );
        SVATKY.put( "12/21", "Natálie" );
        SVATKY.put( "12/22", "Šimon" );
        SVATKY.put( "12/23", "Vlasta" );
        SVATKY.put( "12/24", "Adam a Eva" );
        SVATKY.put( "12/25", "1. svátek vánoční" );
        SVATKY.put( "12/26", "Štěpán" );
        SVATKY.put( "12/27", "Žaneta" );
        SVATKY.put( "12/28", "Bohumila" );
        SVATKY.put( "12/29", "Judita" );
        SVATKY.put( "12/30", "David" );
        SVATKY.put( "12/31", "Silvestr" );
    }
}