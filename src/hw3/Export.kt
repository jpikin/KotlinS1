package hw3

public class Export(val name: String) : Command {
    override fun isValid(): Boolean {
        return true
    }
}
