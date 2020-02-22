package com.cabal.app.utils

import com.cabal.app.database.entities.Event
import com.cabal.app.database.entities.Hobby
import com.cabal.app.database.entities.User
import io.reactivex.Single
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
                    null,
                    random.nextInt(20),
                    DoubleArray(2)
                            .also {
                                it[0] = random.nextDouble()*100
                                it[1] = random.nextDouble()*100
                            },
                    generateHobbies(3),
                    0,
                    false
            ),
            User("2",
                    "Kuba",
                    "kuba_nazwisko@o2.pl",
                    null,
                    random.nextInt(20),
                    DoubleArray(2)
                            .also {
                                it[0] = random.nextDouble()*100
                                it[1] = random.nextDouble()*100
                            },
                    generateHobbies(3),
                    0,
                    false
            ),
            User("3",
                    "Olena",
                    "olena_nazwisko@o2.pl",
                    null,
                    random.nextInt(20),
                    DoubleArray(2)
                            .also {
                                it[0] = random.nextDouble()*100
                                it[1] = random.nextDouble()*100
                            },
                    generateHobbies(3),
                    0,
                    false
            ),
            User("4",
                    "Adrian",
                    "adrian_nazwisko@o2.pl",
                    null,
                    random.nextInt(20),
                    DoubleArray(2)
                            .also {
                                it[0] = random.nextDouble()*100
                                it[1] = random.nextDouble()*100
                            },
                    generateHobbies(3),
                    0,
                    false
            ),
            User("5",
                    "Malwina",
                    "malwina_nazwisko@o2.pl",
                    null,
                    random.nextInt(),
                    DoubleArray(2)
                            .also {
                                it[0] = random.nextDouble()*100
                                it[1] = random.nextDouble()*100
                            },
                    generateHobbies(3),
                    0,
                    false
            ),
            User("6",
                    "Ola",
                    "ola_nazwisko@o2.pl",
                    null,
                    random.nextInt(),
                    DoubleArray(2)
                            .also {
                                it[0] = random.nextDouble()*100
                                it[1] = random.nextDouble()*100
                            },
                    generateHobbies(3),
                    0,
                    false
            ),
            User("7",
                    "Paweł",
                    "paweł_nazwisko@o2.pl",
                    null,
                    random.nextInt(),
                    DoubleArray(2)
                            .also {
                                it[0] = random.nextDouble()*100
                                it[1] = random.nextDouble()*100
                            },
                    generateHobbies(3),
                    0,
                    false
            ),
            User("8",
                    "Paulina",
                    "paulina_nazwisko@o2.pl",
                    null,
                    random.nextInt(),
                    DoubleArray(2)
                            .also {
                                it[0] = random.nextDouble()*100
                                it[1] = random.nextDouble()*100
                            },
                    generateHobbies(3),
                    0,
                    false
            ),
            User("9",
                    "Ewelina",
                    "ewelina_nazwisko@o2.pl",
                    null,
                    random.nextInt(),
                    DoubleArray(2)
                            .also {
                                it[0] = random.nextDouble()*100
                                it[1] = random.nextDouble()*100
                            },
                    generateHobbies(3),
                    0,
                    false
            ),
            User("10",
                    "Iza",
                    "iza_nazwisko@o2.pl",
                    null,
                    random.nextInt(),
                    DoubleArray(2)
                            .also {
                                it[0] = random.nextDouble()*100
                                it[1] = random.nextDouble()*100
                            },
                    generateHobbies(3),
                    0,
                    false
            )
    )

    private val allEvents = listOf(
            Event(1,
                    null,
                    "Granie w gałę",
                    generateUsers(1)[0],
                    Date(),
                    generateUsers(3),
                    "Bar za rogiem",
                    "Granie w barze",
                    false,
                    null
                    ),
            Event(2,
                    null,
                    "Siatkówka",
                    generateUsers(1)[0],
                    Date(),
                    generateUsers(3),
                    "Plaża na helu",
                    "Siatka na słońcu",
                    false,
                    null
            ),
            Event(3,
                    null,
                    "Partyjka w szachy",
                    generateUsers(1)[0],
                    Date(),
                    generateUsers(3),
                    "Klub szachowy Wiking",
                    "Partyjka z mastermindem",
                    false,
                    null
            ),
            Event(4,
                    null,
                    "Wspólny film",
                    generateUsers(1)[0],
                    Date(),
                    generateUsers(3),
                    "Kino Helios",
                    "365 dni, ten syf",
                    false,
                    null
            ),
            Event(5,
                    null,
                    "Inny wspólny film",
                    generateUsers(1)[0],
                    Date(),
                    generateUsers(3),
                    "Multikino",
                    "Zjawa, piękny film",
                    false,
                    null
            ),
            Event(6,
                    null,
                    "Sniadanie na trawie",
                    generateUsers(1)[0],
                    Date(),
                    generateUsers(1),
                    "Trawa",
                    "Pyszne jedzonko we dwoje",
                    false,
                    null
            ),
            Event(7,
                    null,
                    "Już mnie nudzi to wpisywanie",
                    generateUsers(1)[0],
                    Date(),
                    generateUsers(3),
                    "Gdziekolwiek",
                    "Cokolwiek",
                    false,
                    null
            ),
            Event(8,
                    null,
                    "EKG mózgu",
                    generateUsers(1)[0],
                    Date(),
                    generateUsers(3),
                    "Szpital im. Jana Barana",
                    "Zmierzymy puls fal mózgowych",
                    false,
                    null
            ),
            Event(9,
                    null,
                    "Pisanie aplikacji",
                    generateUsers(1)[0],
                    Date(),
                    generateUsers(3),
                    "Piwnica w Gondorze",
                    "Fascynujące",
                    false,
                    null
            ),
            Event(10,
                    null,
                    "Kontrowersyjne wydarzenie",
                    generateUsers(1)[0],
                    Date(),
                    generateUsers(3),
                    "Pola ryżowe",
                    "Przyjmujemy tylko czarnych",
                    false,
                    null
            )
    )

    fun generateEvents(amount: Int): Single<List<Event>> = Single.just(ArrayList<Event>().also {
        for (x in 1..amount) {
            it.add(allEvents[random.nextInt(allEvents.size)])
        }
    })

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