/**
CODE CHALLENGE
Develop a calculator that can take in a string of the form "1 + 2 =" and produce the result.
The solution should use a state machine and the equal sign will act as the exit transition which displays the result.

Requirements:
1. Simple arithmetic operators should be supported (i.e. +, -, *, /)
2. At least two operands should be supported.
3. Operands can be fractional or whole numbers.
4. Operands can be negative or positive.
5. Result should display with necessary precision and sign (-/+).

Instructions:
1. Add all the components of your application in the space provided below, including any classes, objects and functions.
2. Refactor the main() method stub below to invoke your calculator with a sample calculation.
3. Use the blue "Play" button (top right) to test your work.
4. When you're satisfied with the results, press the "Share" button (top right, 3rd down), and generate a shareable link with your work.
5. Send the sharable link back to us for review.

Good luck, and have fun!

 **/

/**
 * A group of *members*.
 *
 * This class has no useful logic; it's just a documentation example.
 *
 * @param T the type of a member in this group.
 * @property name the name of this group.
 * @constructor Creates an empty group.
 */

class Calculator() {

    // STRING CONSTANTS
    private val Empty = ""

    private val Invalid = "INVALID"

    private val Exit = "EXIT"

    private val InvalidLog = "Statement was invalid, use this format: '<operandA><operator><operandB>=', spaces outside of term won't affect the output."

    // PROPERTIES
    private var Operation: Char? = null

    private var OperandA: Double? = null

    private var OperandB: Double? = null

    private var HasDecimal: Boolean = false

    private var LeadingZero: Boolean = false

    /*
    * Adds a [member] to this group.
    * @return the new size of the group.
    */
    fun ResetProperties() {

        Operation = null

        OperandA = null

        OperandB = null

        HasDecimal = false

        LeadingZero = false
    }

    /*
    * Adds a [member] to this group.
    * @return the new size of the group.
    */
    fun Statemachine(char: Char, operand: String): String {

        if (char in '1'..'9') {

            // gets rid of invalid leading zero
            if (LeadingZero) {

                return Empty + char

            // appends char to the operand
            } else {

                return operand + char

            }

        } else if (char == '0') {

            // ignores additional leading zero
            if (LeadingZero && operand.first() == '0' && HasDecimal == false) {

                return operand

            // appends zero to operand string
            } else {

                return operand + char

            }

        } else if (char == '.') {

            // decimal is appended to operand
            if(HasDecimal == false && (this.OperandA == null || this.OperandB == null)) {

                HasDecimal = true

                return operand + char

            // statement is invalid
            } else {

                return Invalid
            }

        } else if (char == ' ') {

            // whitespace is ignored
            if (operand.length == 0) {

                return operand

            } else {

                // operandA is assigned and operand is returned empty, HasDecimal and Leading Zero are set to false
                if (this.OperandA == null) {

                    OperandA = operand.toDoubleOrNull() // this is where it's getting set to null

                    if (OperandA == null) { return Invalid }

                    this.HasDecimal = false

                    this.LeadingZero = false

                    return Empty

                // operandA is assigned and operand is returned empty, HasDecimal and Leading Zero are set to false
                } else if (this.OperandB == null && this.OperandA != null && this.Operation != null && operand.length > 0) {

                    OperandB = operand.toDoubleOrNull()

                    if (OperandB == null) { return Invalid }

                    this.HasDecimal = false

                    this.LeadingZero = false

                    return Empty

                } else {

                    return Empty
                }

            }

        } else if (char == '-') {

            // add a negative sign to the new operand
            if (operand.length == 0 && OperandB == null && ((Operation == null && OperandA == null) || (Operation != null && OperandA != null))) {

                return "" + char

            // assign operation to char
            } else if (Operation == null && operand.length == 0 && OperandA != null && OperandB == null) {

                Operation = char

                return operand

            // statemet is invalid
            } else {

                return Invalid

            }

        } else if (char == '+' || char == '*' || char == '/') {

            if (Operation != null) {

                return Invalid

            } else {

                this.Operation = char

                return operand
            }

        } else if (char == '=') {

            return Exit

        } else {

            return Invalid
        }
    }

    /*
    * Adds a [member] to this group.
    * @return the new size of the group.
    */
    fun PerformCalculation(operandA: Double?, operandB: Double?, operation: Char?): Double? {

        var result: Double?

        if (operandA != null && operandB != null && operation != null) {

            result = when (operation) {

                '+' -> operandA + operandB

                '-' -> operandA - operandB

                '*' -> operandA * operandB

                '/' -> operandA / operandB

                else -> null
            }
            return result

        } else {

            return null
        }
    }

    /*
    * Adds a [member] to this group.
    * @return the new size of the group.
    */
    fun ParseStatement(statement: String): String {

        var result = InvalidLog

        var operand = ""

        for(char in statement) {

            operand = Statemachine(char, operand)

            if (operand == Exit) {

                result = "$statement ${PerformCalculation(OperandA, OperandB, Operation)}"

            }
        }
        ResetProperties()

        return result
    }
}

fun main() {
    println("Invoke your calculator class here")

    val TI_84 = Calculator()

    val testCases = listOf("-4.444 + 4.24802 =", "4 + -4 =", "4 + 4.2548308 =", "-4 + 4 =",
                            "5.678 * -4 =", "4.43579 * 4.45380 =", "-5432978 * 4 =", "-75.34580 * -12 =",
                            "455.352 / -432 =", "-4 / -4 =", "54793 / 4 =", "-4 / -4 =",
                            "4341 - -4 =", "-4 - -4 =", "4 - 4 =", "4.431 - 4 =")

    for(test in testCases) {
        println(TI_84.ParseStatement(test))
    }


}