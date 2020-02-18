package com.cabal.app.utils

import com.cabal.app.database.entities.Event
import com.cabal.app.database.entities.Hobby
import com.cabal.app.database.entities.User
import java.util.*
import kotlin.collections.ArrayList

object StaticDataGenerator {

    private val random = Random()

    private val allHobbies = listOf(
            Hobby("Jedzenie ziemniaków", "Bo jestem żarłocznym człowiekiem"),
            Hobby("Nolifienie w gry online", "Bo jestem słabym człowiekiem"),
            Hobby("Obrażanie innych na forach", "Bo jestem niepewnym człowiekiem"),
            Hobby("Odmawianie prośbom", "Bo jestem strachliwym człowiekiem"),
            Hobby("Kolekcjonowanie kubków", "Bo jestem oryginalnym człowiekiem"),
            Hobby("Pomaganie innym", "Bo jestem szlachetnym człowiekiem"),
            Hobby("Stalkowanie innych na fb", "Bo jestem zazdrosnym człowiekiem"),
            Hobby("Nauczanie w szkołach", "Bo jestem wielkodusznym człowiekiem"),
            Hobby("Zbieranie pieniędzy", "Bo jestem próżnym człowiekiem"),
            Hobby("Brak hobby", "Bo jestem pustym człowiekiem")
    )

    private val allUsers = listOf(
            User("1",
                    "Maciek",
                    "maciek_nazwisko@o2.pl",
                    "",
                    random.nextInt(20),
                    DoubleArray(2)
                            .also {
                                it[0] = random.nextDouble()*100
                                it[1] = random.nextDouble()*100
                            },
                    generateHobbies(3),
                    false
            ),
            User("2",
                    "Kuba",
                    "kuba_nazwisko@o2.pl",
                    "",
                    random.nextInt(20),
                    DoubleArray(2)
                            .also {
                                it[0] = random.nextDouble()*100
                                it[1] = random.nextDouble()*100
                            },
                    generateHobbies(3),
                    false
            ),
            User("3",
                    "Olena",
                    "olena_nazwisko@o2.pl",
                    "",
                    random.nextInt(20),
                    DoubleArray(2)
                            .also {
                                it[0] = random.nextDouble()*100
                                it[1] = random.nextDouble()*100
                            },
                    generateHobbies(3),
                    false
            ),
            User("4",
                    "Adrian",
                    "adrian_nazwisko@o2.pl",
                    "",
                    random.nextInt(20),
                    DoubleArray(2)
                            .also {
                                it[0] = random.nextDouble()*100
                                it[1] = random.nextDouble()*100
                            },
                    generateHobbies(3),
                    false
            ),
            User("5",
                    "Malwina",
                    "malwina_nazwisko@o2.pl",
                    "",
                    random.nextInt(),
                    DoubleArray(2)
                            .also {
                                it[0] = random.nextDouble()*100
                                it[1] = random.nextDouble()*100
                            },
                    generateHobbies(3),
                    false
            ),
            User("6",
                    "Ola",
                    "ola_nazwisko@o2.pl",
                    "",
                    random.nextInt(),
                    DoubleArray(2)
                            .also {
                                it[0] = random.nextDouble()*100
                                it[1] = random.nextDouble()*100
                            },
                    generateHobbies(3),
                    false
            ),
            User("7",
                    "Paweł",
                    "paweł_nazwisko@o2.pl",
                    "",
                    random.nextInt(),
                    DoubleArray(2)
                            .also {
                                it[0] = random.nextDouble()*100
                                it[1] = random.nextDouble()*100
                            },
                    generateHobbies(3),
                    false
            ),
            User("8",
                    "Paulina",
                    "paulina_nazwisko@o2.pl",
                    "",
                    random.nextInt(),
                    DoubleArray(2)
                            .also {
                                it[0] = random.nextDouble()*100
                                it[1] = random.nextDouble()*100
                            },
                    generateHobbies(3),
                    false
            ),
            User("9",
                    "Ewelina",
                    "ewelina_nazwisko@o2.pl",
                    "",
                    random.nextInt(),
                    DoubleArray(2)
                            .also {
                                it[0] = random.nextDouble()*100
                                it[1] = random.nextDouble()*100
                            },
                    generateHobbies(3),
                    false
            ),
            User("10",
                    "Iza",
                    "iza_nazwisko@o2.pl",
                    "",
                    random.nextInt(),
                    DoubleArray(2)
                            .also {
                                it[0] = random.nextDouble()*100
                                it[1] = random.nextDouble()*100
                            },
                    generateHobbies(3),
                    false
            )
    )

    private val allEvents = listOf(
            Event(1,
                    "",
                    "Granie w gałę",
                    generateUsers(1)[0],
                    Date(),
                    generateUsers(3),
                    "Bar za rogiem",
                    "Granie w barze",
                    false
                    ),
            Event(2,
                    "",
                    "Siatkówka",
                    generateUsers(1)[0],
                    Date(),
                    generateUsers(3),
                    "Plaża na helu",
                    "Siatka na słońcu",
                    false
            ),
            Event(3,
                    "",
                    "Partyjka w szachy",
                    generateUsers(1)[0],
                    Date(),
                    generateUsers(3),
                    "Klub szachowy Wiking",
                    "Partyjka z mastermindem",
                    false
            ),
            Event(4,
                    "",
                    "Wspólny film",
                    generateUsers(1)[0],
                    Date(),
                    generateUsers(3),
                    "Kino Helios",
                    "365 dni, ten syf",
                    false
            ),
            Event(5,
                    "",
                    "Inny wspólny film",
                    generateUsers(1)[0],
                    Date(),
                    generateUsers(3),
                    "Multikino",
                    "Zjawa, piękny film",
                    false
            ),
            Event(6,
                    "",
                    "Sniadanie na trawie",
                    generateUsers(1)[0],
                    Date(),
                    generateUsers(1),
                    "Trawa",
                    "Pyszne jedzonko we dwoje",
                    false
            ),
            Event(7,
                    "",
                    "Już mnie nudzi to wpisywanie",
                    generateUsers(1)[0],
                    Date(),
                    generateUsers(3),
                    "Gdziekolwiek",
                    "Cokolwiek",
                    false
            ),
            Event(8,
                    "",
                    "EKG mózgu",
                    generateUsers(1)[0],
                    Date(),
                    generateUsers(3),
                    "Szpital im. Jana Barana",
                    "Zmierzymy puls fal mózgowych",
                    false
            ),
            Event(9,
                    "",
                    "Pisanie aplikacji",
                    generateUsers(1)[0],
                    Date(),
                    generateUsers(3),
                    "Piwnica w Gondorze",
                    "Fascynujące",
                    false
            ),
            Event(10,
                    "",
                    "Kontrowersyjne wydarzenie",
                    generateUsers(1)[0],
                    Date(),
                    generateUsers(3),
                    "Pola ryżowe",
                    "Przyjmujemy tylko czarnych",
                    false
            )
    )

    fun generateEvents(amount: Int): List<Event> = ArrayList<Event>().also {
        for (x in 1..amount) {
            it.add(allEvents[random.nextInt(allEvents.size)])
        }
    }

    fun generateUsers(amount: Int): List<User> = ArrayList<User>().also {
        for (x in 1..amount) {
            it.add(allUsers[random.nextInt(allUsers.size)])
        }
    }

    fun generateHobbies(amount: Int): List<Hobby> = ArrayList<Hobby>().also {
        for (x in 1..amount) {
            it.add(allHobbies[random.nextInt(allHobbies.size)])
        }
    }

}