package com.redron

enum class BiteShape {
    STRAIGHT, OVERSHOT, UNDERSHOT
}

enum class Behavior {
    ACTIVE, PASSIVE
}

interface Animal {
    val weight: Int
    val age: Int
}

interface Dog : Animal {
    val biteShape: BiteShape
}

interface Cat : Animal {
    val behavior: Behavior
}

class Husky(override val weight: Int, override val age: Int) : Dog {
    override val biteShape = BiteShape.STRAIGHT
}

class Corgi(override val weight: Int, override val age: Int) : Dog {
    override val biteShape = BiteShape.STRAIGHT
}

class ScottishCat(override val weight: Int, override val age: Int) : Cat {
    override val behavior = Behavior.PASSIVE
}

class SiameseCat(override val weight: Int, override val age: Int) : Cat {
    override val behavior = Behavior.ACTIVE
}

class PetShop {
    fun isCatOrDog(animal: Animal): String {
        return when (animal) {
            is Dog -> "Собака"
            is Cat -> "Кошка"
            else -> "Неизвестное животное"
        }
    }
}


fun main() {
    val petshop = PetShop()

    val husky = Husky(10, 5)
    val siameseCat = SiameseCat(4, 2)

    println(petshop.isCatOrDog(husky))
    println(petshop.isCatOrDog(siameseCat))
}