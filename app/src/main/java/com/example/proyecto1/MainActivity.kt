package com.example.proyecto1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : AppCompatActivity() {

    private val model: QuizViewModel by viewModels()

    private lateinit var btnPlay: Button
    private lateinit var btnOption: Button
    private lateinit var btnScores: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model.getModelContext = this
        model.startModelDatabase()

        btnPlay = findViewById(R.id.btnPlay)
        btnOption = findViewById(R.id.btnOption)
        btnScores = findViewById(R.id.btnScores)

        val alertDialog = AlertDialog.Builder(this)

        if (model.getAllNumberQuestions == 0) {
            getAllQuestions()
            getAllGameSettings()
        }

        btnPlay.setOnClickListener { _ ->

            if (model.getGameSettingsStatusId(4) == 1) {
                if (model.getAllNGameSet() != 0) {
                    model.deleteGameSet()
                    model.deleteSelectedQuestions()
                }

                val intent = Intent(this, GameActivity::class.java)
                val questionsInt = getThemeQuestions().shuffled()
                val randomInts =
                    generateSequence { Random.nextInt(1 until questionsInt.size) }.distinct().take(
                        model.getGameSettingsStatusId(1)
                    ).toSet().toList() as ArrayList<Int>

                for (i in 0 until randomInts.size) {
                    model.insertGameSet(GameSet(i, questionsInt[randomInts[i]]))
                }
                //Actual score
                model.insertGameSet(GameSet(97, 0))
                //Actual index
                model.insertGameSet(GameSet(98, 0))
                //Actual hints
                model.insertGameSet(GameSet(99, 5))
                //Actual hints used
                model.insertGameSet(GameSet(100, 0))
                //Actual hints streaks
                model.insertGameSet(GameSet(101, 0))

                for (i in 0 until randomInts.size) {
                    val questions = model.getQuestionsData(questionsInt[randomInts[i]])
                    model.insertSelectedQuestions(
                        SelectedQuestions(
                            questionsInt[randomInts[i]],
                            questions.correctAnswer,
                            questions.allAnswers0,
                            questions.allAnswers1,
                            questions.allAnswers2,
                            questions.allAnswers3,
                            difficulty = model.getGameSettingsStatusId(2)
                        )
                    )
                }

                startActivity(intent)
            } else {
                val intent = Intent(this, GameActivity::class.java)
                alertDialog.setTitle("Confirmaci??n")
                alertDialog.setMessage("??Quieres continuar la partida?").setPositiveButton(
                    "S??"
                ) { _, _ ->
                    startActivity(intent)
                }.setNegativeButton("No") { _, _ ->
                    if (model.getAllNGameSet() != 0) {
                        model.deleteGameSet()
                        model.deleteSelectedQuestions()
                    }

                    val questionsInt = getThemeQuestions().shuffled()
                    val randomInts =
                        generateSequence { Random.nextInt(1 until questionsInt.size) }.distinct()
                            .take(
                                model.getGameSettingsStatusId(1)
                            ).toSet().toList() as ArrayList<Int>

                    for (i in 0 until randomInts.size) {
                        model.insertGameSet(GameSet(i, questionsInt[randomInts[i]]))
                    }
                    //Actual score
                    model.insertGameSet(GameSet(97, 0))
                    //Actual index
                    model.insertGameSet(GameSet(98, 0))
                    //Actual hints
                    model.insertGameSet(GameSet(99, 5))
                    //Actual hints used
                    model.insertGameSet(GameSet(100, 0))
                    //Actual hints streaks
                    model.insertGameSet(GameSet(101, 0))

                    for (i in 0 until randomInts.size) {
                        val questions = model.getQuestionsData(questionsInt[randomInts[i]])
                        model.insertSelectedQuestions(
                            SelectedQuestions(
                                questionsInt[randomInts[i]],
                                questions.correctAnswer,
                                questions.allAnswers0,
                                questions.allAnswers1,
                                questions.allAnswers2,
                                questions.allAnswers3,
                                difficulty = model.getGameSettingsStatusId(2)
                            )
                        )
                    }

                    startActivity(intent)
                }.show()

            }
        }

        btnOption.setOnClickListener { _ ->
            val intent = Intent(this, OptionActivity::class.java)
            startActivity(intent)
        }

        btnScores.setOnClickListener { _ ->
            val intent = Intent(this, ScoreActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getAllQuestions() {
        model.insertQuestions(
            Questions(
                0,
                "??La invasi??n de qu?? fortaleza por parte de los revolucionarios es considerada como el punto de inicio de la Revoluci??n Francesa?",
                "Historia",
                "Bastilla",
                "Bastilla",
                "Palacio de Versalles",
                "El Castillo de Bonaguil",
                "La ciudadela de Carcasona"
            )
        )

        model.insertQuestions(
            Questions(
                1,
                "??En qu?? a??o el hombre pis?? la Luna por primera vez?",
                "Historia",
                "1969",
                "1979",
                "1969",
                "1959",
                "1971"
            )
        )

        model.insertQuestions(
            Questions(
                2,
                "??Qui??n fue el primer presidente de la democracia espa??ola tras el franquismo?",
                "Historia",
                "Adolfo Su??rez",
                "Adolfo Su??rez",
                "Leopoldo Calvo",
                "Felipe Gonz??lez",
                "Jos?? Mar??a Aznar"
            )
        )

        model.insertQuestions(
            Questions(
                3,
                "??Qui??n fue el primer presidente de Estados Unidos?",
                "Historia",
                "George Washington",
                "George Washington",
                "John Adams",
                "Thomas Jefferson",
                "James Madison"
            )
        )

        model.insertQuestions(
            Questions(
                4,
                "??Cu??nto dur?? la Guerra de los Cien A??os?",
                "Historia",
                "116",
                "100",
                "96",
                "116",
                "153"
            )
        )

        model.insertQuestions(
            Questions(
                5,
                "??Cu??l era la ciudad hogar de Marco Polo?",
                "Historia",
                "Venecia",
                "Venecia",
                "Portugal",
                "Mil??n",
                "Espa??a"
            )
        )

        model.insertQuestions(
            Questions(
                6,
                "??En qu?? a??o se disolvi?? la Uni??n Sovi??tica?",
                "Historia",
                "1991",
                "1998",
                "1990",
                "1991",
                "1995"
            )
        )

        model.insertQuestions(
            Questions(
                7,
                "??De qu?? pa??s se independiz?? Eslovenia?",
                "Historia",
                "Yugoslavia",
                "Liberia",
                "Yugoslavia",
                "Rusia",
                "Eslovaquia"
            )
        )

        model.insertQuestions(
            Questions(
                8,
                "??Qu?? carabela no regres?? del primer viaje de Col??n al Nuevo Mundo?",
                "Historia",
                "La Santa Mar??a",
                "La Ni??a",
                "La Pinta",
                "La Bonita",
                "La Santa Mar??a"
            )
        )

        model.insertQuestions(
            Questions(
                9,
                "??Qu?? presidente de la Uni??n Sovi??tica instaur?? la Perestroika?",
                "Historia",
                "Gorbachov",
                "Nikita",
                "Lenin",
                "Stalin",
                "Gorbachov"
            )
        )

        model.insertQuestions(
            Questions(
                10,
                "??Cu??l es el r??o m??s caudaloso del mundo?",
                "Geograf??a",
                "Amazonas",
                "Nilo",
                "Missisipi",
                "Amazonas",
                "Bravo"
            )
        )

        model.insertQuestions(
            Questions(
                11,
                "??Cu??l es el monte m??s alto del mundo?",
                "Geograf??a",
                "Everest",
                "Pico de Orizaba",
                "Everest",
                "Kilawea",
                "Olimpo"
            )
        )

        model.insertQuestions(
            Questions(
                12,
                "??Cu??l es la lengua m??s hablada del mundo?",
                "Geograf??a",
                "Chino",
                "Ingles",
                "Espa??ol",
                "Chino",
                "Hindi"
            )
        )

        model.insertQuestions(
            Questions(
                13,
                "??Cu??l es la capital de Brasil?",
                "Geograf??a",
                "Brasilia",
                "R??o de Janeiro",
                "Sao Paolo",
                "Brasilia",
                "Caracas"
            )
        )

        model.insertQuestions(
            Questions(
                14,
                "??Cu??l es el pa??s de mayor tama??o del mundo?",
                "Geograf??a",
                "Rusia",
                "Canada",
                "Brasil",
                "Estados Unidos",
                "Rusia"
            )
        )

        model.insertQuestions(
            Questions(
                15,
                "??Cu??l es el pa??s de mayor tama??o del mundo?",
                "Geograf??a",
                "Rusia",
                "Canada",
                "Brasil",
                "Estados Unidos",
                "Rusia"
            )
        )

        model.insertQuestions(
            Questions(
                16,
                "??Qu?? pa??s est?? entre Per?? y Colombia?",
                "Geograf??a",
                "Ecuador",
                "Per??",
                "Bolivia",
                "Panam??",
                "Ecuador"
            )
        )

        model.insertQuestions(
            Questions(
                17,
                "??En qu?? pa??s se encuentra el r??o Po?",
                "Geograf??a",
                "Italia",
                "Alemania",
                "Francia",
                "Espa??a",
                "Italia"
            )
        )

        model.insertQuestions(
            Questions(
                18,
                "??A qu?? pa??s pertenece la isla de Creta?",
                "Geograf??a",
                "Grecia",
                "Italia",
                "Francia",
                "Grecia",
                "Alemania"
            )
        )

        model.insertQuestions(
            Questions(
                19,
                "??Cu??l es el pa??s m??s visitado del mundo?",
                "Geograf??a",
                "Francia",
                "M??xico",
                "Francia",
                "Grecia",
                "Alemania"
            )
        )

        model.insertQuestions(
            Questions(
                20,
                "??Qui??n fue el famoso cantante del grupo musical Queen?",
                "Entretenimiento",
                "Freddy Mercury",
                "Freddy Mercury",
                "Bob Dylan",
                "John Lenon",
                "Bono"
            )
        )

        model.insertQuestions(
            Questions(
                21,
                "??C??mo se llama la madre de Simba en la pel??cula de Disney ???El Rey Le??n????",
                "Entretenimiento",
                "Sarabi",
                "Sarabi",
                "Kimba",
                "Mufasa",
                "Kaya"
            )
        )

        model.insertQuestions(
            Questions(
                22,
                "??A qu?? banda de m??sica metal pertenece el disco Master of Puppets?",
                "Entretenimiento",
                "Metallica",
                "Queen",
                "Kiss",
                "Metallica",
                "AC/DC"
            )
        )

        model.insertQuestions(
            Questions(
                23,
                "??C??mo se llama la protagonista de la saga de videojuegos \"The Legend of Zelda\"?",
                "Entretenimiento",
                "Link",
                "Zelda",
                "Ganon",
                "Link",
                "Furbo"
            )
        )

        model.insertQuestions(
            Questions(
                24,
                "??En qu?? pa??s transcurre la acci??n de la pel??cula \"Chappie\"?",
                "Entretenimiento",
                "Sudafrica",
                "Francia",
                "Mexico",
                "Estados Unidos",
                "Reino Unido"
            )
        )

        model.insertQuestions(
            Questions(
                25,
                "??En qu?? ciudad vive el mago de Oz?",
                "Entretenimiento",
                "Esmeralda",
                "Esmeralda",
                "Oz",
                "Kansas",
                "Ciudad m??gica"
            )
        )

        model.insertQuestions(
            Questions(
                26,
                "??En qu?? a??o se emiti?? el ??ltimo episodio de la serie \"The Office\"?",
                "Entretenimiento",
                "2013",
                "2015",
                "2016",
                "2013",
                "2010"
            )
        )

        model.insertQuestions(
            Questions(
                27,
                "??En qu?? calle ficticia viv??a Sherlock Holmes?",
                "Entretenimiento",
                "Baker Street",
                "Baker Street",
                "Savile Row",
                "Carnaby Street",
                "King's Road"
            )
        )

        model.insertQuestions(
            Questions(
                28,
                "??C??mo se llama el perro de Tint??n?",
                "Entretenimiento",
                "Mil??",
                "Clifford",
                "Rocko",
                "Mil??",
                "Lazy"
            )
        )

        model.insertQuestions(
            Questions(
                29,
                "??Cu??l fue la primera pel??cula de Disney?",
                "Entretenimiento",
                "Blancanieves",
                "La Cenicienta",
                "Blancanieves",
                "La sirenita",
                "Mickey Mouse"
            )
        )

        model.insertQuestions(
            Questions(
                30,
                "??Qui??n escribi?? la Il??ada y la Odisea?",
                "Arte y Literatura",
                "Homero",
                "Odiseo",
                "Homero",
                "Antipas",
                "Herodoto"
            )
        )

        model.insertQuestions(
            Questions(
                31,
                "??Qui??n pint?? el ???Guernica????",
                "Arte y Literatura",
                "Pablo Picasso",
                "Miguel ??ngel",
                "Andy Warhol",
                "Pablo Picasso",
                "Salvador Dal??"
            )
        )

        model.insertQuestions(
            Questions(
                32,
                "??Qu?? nombre ten??a el caballo de Don Quijote de la Mancha?",
                "Arte y Literatura",
                "Rocinante",
                "Blanco",
                "Rocinante",
                "Salvador",
                "Aventurero"
            )
        )

        model.insertQuestions(
            Questions(
                33,
                "??De qu?? pa??s es originario el tipo de poes??a conocido como haiku?",
                "Arte y Literatura",
                "Jap??n",
                "China",
                "Corea",
                "Jap??n",
                "India"
            )
        )

        model.insertQuestions(
            Questions(
                34,
                " ??Qu?? personaje del universo literario de Harry Potter tiene una rata llamada Scabbers?",
                "Arte y Literatura",
                "Ron",
                "Harry",
                "Ron",
                "Hermione",
                "Draco"
            )
        )

        model.insertQuestions(
            Questions(
                35,
                " ??Qui??n escribi?? ???La Guerra de los Mundos????",
                "Arte y Literatura",
                "H. G. Wells",
                "H. G. Wells",
                "George Orwell",
                "Aldous Huxley",
                "Ray Bradbury"
            )
        )

        model.insertQuestions(
            Questions(
                36,
                " ??Con qu?? nombre firmaba Van Gogh sus obras?",
                "Arte y Literatura",
                "vincent",
                "Van Gogh",
                "V. Van",
                "V.",
                "V. V. G."
            )
        )

        model.insertQuestions(
            Questions(
                37,
                "  ??Qu?? tipo de instrumento es una c??tara?",
                "Arte y Literatura",
                "Cuerda",
                "Viento",
                "Cuerda",
                "Percusi??n",
                "Baquetas"
            )
        )

        model.insertQuestions(
            Questions(
                38,
                "  ??Qu?? pintor noruego pint?? ???El grito????",
                "Arte y Literatura",
                "Edvard Munch",
                "Edvard Munch",
                "Vincent Van Gogh",
                "Claude Monet",
                "Vasili Kandinski"
            )
        )

        model.insertQuestions(
            Questions(
                39,
                " ??Qui??n escribi?? ???Sue??o de una noche de verano????",
                "Arte y Literatura",
                "Shakespare",
                "Shakespare",
                "Cervantes",
                "Dickens",
                "Maquiavelo"
            )
        )

        model.insertQuestions(
            Questions(
                40,
                "??Cu??l es el ave de mayor envergadura que sigue viva actualmente?",
                "Ciencias",
                "Albatros",
                "Aguila",
                "Pelicano",
                "Buitre",
                "Albatros"
            )
        )

        model.insertQuestions(
            Questions(
                41,
                "??C??mo se llama la planta a partir de la cual suele ser elaborado el tequila?",
                "Ciencias",
                "Agave",
                "Agave",
                "Maiz",
                "Pulque",
                "Cebada"
            )
        )

        model.insertQuestions(
            Questions(
                42,
                "??C??mo se llama el tipo de c??lula nerviosa m??s abundante en el cerebro humano?",
                "Ciencias",
                "Gl??a",
                "Gl??a",
                "Neuronas",
                "Fibroblastos",
                "Plaquetas"
            )
        )

        model.insertQuestions(
            Questions(
                43,
                "??Qu?? nombre recibe el sistema de transcripci??n fon??tica usado en el chino mandar??n?",
                "Ciencias",
                "Pinyin",
                "Pinyin",
                "Hiragana",
                "Katakana",
                "Ideograma"
            )
        )

        model.insertQuestions(
            Questions(
                44,
                "??Cu??l es el nombre t??cnico del miedo o fobia a las alturas?",
                "Ciencias",
                "Acrofobia",
                "Alturafobia",
                "Acrofobia",
                "Hirofobia",
                "Valofobia"
            )
        )

        model.insertQuestions(
            Questions(
                45,
                " ??En qu?? mes el Sol est?? m??s cerca de la Tierra?",
                "Ciencias",
                "Diciembre",
                "Enero",
                "Julio",
                "Agosto",
                "Diciembre"
            )
        )

        model.insertQuestions(
            Questions(
                46,
                " ??Cu??ntos elementos tiene la tabla peri??dica?",
                "Ciencias",
                "118",
                "115",
                "119",
                "118",
                "100"
            )
        )

        model.insertQuestions(
            Questions(
                47,
                " ??Qu?? n??mero viene despu??s del 14 en los decimales del Pi?",
                "Ciencias",
                "1",
                "5",
                "6",
                "9",
                "3"
            )
        )

        model.insertQuestions(
            Questions(
                48,
                " ??Qu?? elemento est?? presente en absolutamente todas las mol??culas org??nicas?",
                "Ciencias",
                "carbono",
                "Ox??geno",
                "Hidr??geno",
                "Carbono",
                "Fl??or"
            )
        )

        model.insertQuestions(
            Questions(
                49,
                " ??A partir de qu?? planta se elabora el tequila?",
                "Ciencias",
                "Agave",
                "S??vila",
                "Mag??ey",
                "Agave",
                "Penca"
            )
        )
    }

    private fun getAllGameSettings() {
        model.insertGameSettings(GameSettings(0, "Temas", 1))

        model.insertGameSettings(GameSettings(1, "No preguntas", 5))

        model.insertGameSettings(GameSettings(2, "Dificultad", 0))

        model.insertGameSettings(GameSettings(3, "Pistas", 0))

        model.insertGameSettings(GameSettings(4, "Finalizado", 1))

    }

    private fun getThemeQuestions(): List<Int> {
        when (model.getGameSettingsStatusId(0)) {
            1 -> return model.getQuestionsThemeSelected("Historia")

            5 -> return model.getQuestionsAllThemeSelected()

            10 -> return model.getQuestionsThemeSelected("Arte y Literatura")

            11 -> return model.getQuestionsThemeSelected2("Historia", "Arte y Literatura")

            100 -> return model.getQuestionsThemeSelected("Geograf??a")

            101 -> return model.getQuestionsThemeSelected2("Historia", "Geograf??a")

            110 -> return model.getQuestionsThemeSelected2("Arte y Literatura", "Geograf??a")

            111 -> return model.getQuestionsThemeSelected3(
                "Historia",
                "Arte y Literatura",
                "Geograf??a"
            )

            1000 -> return model.getQuestionsThemeSelected("Entretenimiento")

            1001 -> return model.getQuestionsThemeSelected2("Historia", "Entretenimiento")

            1010 -> return model.getQuestionsThemeSelected2("Arte y Literatura", "Entretenimiento")

            1011 -> return model.getQuestionsThemeSelected3(
                "Historia",
                "Arte y Literatura",
                "Entretenimiento"
            )

            1100 -> return model.getQuestionsThemeSelected2("Geograf??a", "Entretenimiento")

            1101 -> return model.getQuestionsThemeSelected3(
                "Historia",
                "Geograf??a",
                "Entretenimiento"
            )

            1110 -> return model.getQuestionsThemeSelected3(
                "Arte y Literatura",
                "Geograf??a",
                "Entretenimiento"
            )

            1111 -> return model.getQuestionsThemeSelected4(
                "Historia",
                "Arte y Literatura",
                "Geograf??a",
                "Entretenimiento"
            )

            10000 -> return model.getQuestionsThemeSelected("Ciencias")

            10001 -> return model.getQuestionsThemeSelected2("Historia", "Ciencias")

            10010 -> return model.getQuestionsThemeSelected2("Arte y Literatura", "Ciencias")

            10011 -> return model.getQuestionsThemeSelected3(
                "Historia",
                "Arte y Literatura",
                "Ciencias"
            )

            10100 -> return model.getQuestionsThemeSelected2("Geograf??a", "Ciencias")

            10101 -> return model.getQuestionsThemeSelected3("Historia", "Geograf??a", "Ciencias")

            10110 -> return model.getQuestionsThemeSelected3(
                "Arte y Literatura",
                "Geograf??a",
                "Ciencias"
            )

            10111 -> return model.getQuestionsThemeSelected4(
                "Historia",
                "Arte y Literatura",
                "Geograf??a",
                "Ciencias"
            )

            11000 -> return model.getQuestionsThemeSelected2("Entretenimiento", "Ciencias")

            11001 -> return model.getQuestionsThemeSelected3(
                "Historia",
                "Entretenimiento",
                "Ciencias"
            )

            11010 -> return model.getQuestionsThemeSelected3(
                "Arte y Literatura",
                "Entretenimiento",
                "Ciencias"
            )

            11011 -> return model.getQuestionsThemeSelected4(
                "Historia",
                "Arte y Literatura",
                "Entretenimiento",
                "Ciencias"
            )

            11100 -> return model.getQuestionsThemeSelected3(
                "Geograf??a",
                "Entretenimiento",
                "Ciencias"
            )

            11101 -> return model.getQuestionsThemeSelected4(
                "Historia",
                "Geograf??a",
                "Entretenimiento",
                "Ciencias"
            )

            11110 -> return model.getQuestionsThemeSelected4(
                "Arte y Literatura",
                "Geograf??a",
                "Entretenimiento",
                "Ciencias"
            )

            11111 -> return model.getQuestionsAllThemeSelected()

        }

        return model.getQuestionsAllThemeSelected()
    }

}
