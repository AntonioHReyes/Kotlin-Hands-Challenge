
var memory = arrayListOf(0)
var dictionary = hashMapOf<Int, Int>()
var pointer = 0
var cursor = 0

fun main(args: Array<String>) {

    val result1 = decodeMessage(convertToArray("👇🤜👇👇👇👇👇👇👇👉👆👈🤛👉👇👊👇🤜👇👉👆👆👆👆👆👈🤛👉👆👆👊👆👆👆👆👆👆👆👊👊👆👆👆👊"))
    println("Result 1: $result1")

    restartVariables()
    val result2 = decodeMessage(convertToArray("👉👆👆👆👆👆👆👆👆🤜👇👈👆👆👆👆👆👆👆👆👆👉🤛👈👊👉👉👆👉👇🤜👆🤛👆👆👉👆👆👉👆👆👆🤜👉🤜👇👉👆👆👆👈👈👆👆👆👉🤛👈👈🤛👉👇👇👇👇👇👊👉👇👉👆👆👆👊👊👆👆👆👊👉👇👊👈👈👆🤜👉🤜👆👉👆🤛👉👉🤛👈👇👇👇👇👇👇👇👇👇👇👇👇👇👇👊👉👉👊👆👆👆👊👇👇👇👇👇👇👊👇👇👇👇👇👇👇👇👊👉👆👊👉👆👊"))
    println("Result 2: $result2")
}

fun restartVariables(){
    memory = arrayListOf(0)
    dictionary = hashMapOf<Int, Int>()
    pointer = 0
    cursor = 0
}

fun convertToArray(instructions: String): Array<String> {
    return instructions.codePoints()
        .toArray()
        .map { codePoint -> String(Character.toChars(codePoint)) }
        .toTypedArray()
}

fun decodeMessage(instructions: Array<String>): String {

    var output = ""

    val fistsStack = arrayListOf<Int>()
    instructions.forEachIndexed { index, instruction ->
        when(instruction){
            "🤜" -> fistsStack.add(index)
            "🤛" -> {
                val fist = fistsStack.removeAt(fistsStack.size - 1)
                dictionary[fist] = index
                dictionary[index] = fist
            }
        }
    }

    while (cursor < instructions.size){
        actions(instructions[cursor])?.let {
            output += it
        }

        cursor++
    }


    return output
}

fun actions(instruction: String): Char?{
    when(instruction){
        "👉" -> {
            pointer++
            if (pointer >= memory.size) memory.add(0)
        }
        "👈" -> pointer--
        "👆" -> {
            if (memory[pointer] == 255) memory[pointer] = 0
            else memory[pointer]++
        }
        "👇" -> {
            if (memory[pointer] == 0) memory[pointer] = 255
            else memory[pointer]--
        }
        "🤜" -> {
            if (memory[pointer] == 0) cursor = dictionary[cursor]!!
        }
        "🤛" -> {
            if (memory[pointer] != 0) cursor = dictionary[cursor]!!
        }
        "👊" -> return memory[pointer].toChar()
    }

    return null
}