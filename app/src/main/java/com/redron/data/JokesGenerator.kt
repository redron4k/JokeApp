package com.redron.data

class JokesGenerator {
    private val jokesList = listOf<Joke>(
        Joke("Что будет, если ворона сядет на оголённые провода?",
            "Электро кар", "Каламбуры"),
        Joke("Как называют сумасшедшего француза?",
            "Франшиза", "Каламбуры"),
        Joke("Почему черепашки ниндзя нападают вчетвером?",
            "У них учитель крыса", "За 300"),
        Joke("Что сказал слепой, когда вошел в бар?",
            "Всем привет, кого не видел!", "Черные"),
        Joke("Едут отец и сын на шестерки. машина перевернулась",
            "Теперь едут на девятке.", "За 300"),
        Joke("Заходят два дракона в бар. Один говорит другому: — Что-то здесь жарковато",
            "А тот отвечает: — Рот закрой", "За 300"),
        Joke("Как называется человек, который ворует собак?",
            "Договор", "Каламбуры"),
        Joke("Test Long Answer Joke",
            "AnswerAnswerAnswerAnswerAnswerAnswerAnswerAnswerAnswerAnswerAnswerAnswer",
            "Test")
    )

    public fun generate() : List<Joke> {
        return jokesList.shuffled()
    }
}