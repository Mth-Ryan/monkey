import org.jline.reader.*
import org.jline.reader.impl.completer.StringsCompleter
import org.jline.terminal.TerminalBuilder
import syntax.Lexer

@main def main(args: String*): Unit = {
  val terminal = TerminalBuilder.builder().build()
  val reader = LineReaderBuilder.builder()
    .terminal(terminal)
    .completer(new StringsCompleter("let", "fn", "return", "exit()", "help()"))
    .build()

  var continue = true
  while (continue) {
    val line = reader.readLine("monkey> ")
    if (line == null) {
      continue = false
    } else {
      line.trim match {
        case "exit()" => continue = false
        case input => Lexer(input).foreach(t => println(s"${t}"))
      }
    }
  }

  terminal.close()
}