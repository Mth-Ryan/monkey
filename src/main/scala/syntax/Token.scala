package syntax

enum Token:
  case EOF
  case Illegal(value: String)
  
  // Literal
  case Ident(value: String)
  case Integer(value: Int)
  
  // Operators
  case Assign
  case Plus
  case Minus
  case Bang
  case Star
  case Slash
  case LessThan
  case GreaterThan
  case Equal
  case NotEqual
  
  // Delimiters
  case Comma
  case SemiColon
  case LeftParen
  case RightParen
  case LeftBrace
  case RightBrace
  
  // Keywords
  case Let
  case Function
  case Return
  case If
  case Else
  case True
  case False