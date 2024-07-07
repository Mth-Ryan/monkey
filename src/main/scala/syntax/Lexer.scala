package syntax

import scala.annotation.tailrec

type Input = List[Char]

object Lexer:
  
  private def advanceWhile(input: Input, f: Char => Boolean): (String, Input) =
    @tailrec
    def aux(input: Input, acc: List[Char]): (String, Input) = input match
      case h::t if f(h) => aux(t, h::acc)
      case rest => (acc.reverse.mkString, rest)
    aux(input, Nil)
    
  private def tokenizeIdentifierOrKeyword(input: Input): (Token, Input) =
    val (literal, rest) = advanceWhile(input, Rules.isIdentifierChar)
    (Rules.lookupKeyword(literal), rest)
    
  private def tokenizeNumber(input: Input): (Token, Input) =
    val (literal, rest) = advanceWhile(input, Rules.isNumberChar)
    (Rules.lookupNumber(literal), rest)
    
  @tailrec
  private def nextToken(input: Input): (Token, Input) = input match
    case Nil            => (Token.EOF, Nil)
    case '='::'='::rest => (Token.Equal, rest)
    case '!'::'='::rest => (Token.NotEqual, rest)
    case '='::rest      => (Token.Assign, rest)
    case '!'::rest      => (Token.Bang, rest)
    case '+'::rest      => (Token.Plus, rest)
    case '-'::rest      => (Token.Minus, rest)
    case '*'::rest      => (Token.Star, rest)
    case '/'::rest      => (Token.Slash, rest)
    case '>'::rest      => (Token.GreaterThan, rest)
    case '<'::rest      => (Token.LessThan, rest)
    case ';'::rest      => (Token.SemiColon, rest)
    case '('::rest      => (Token.LeftParen, rest)
    case ')'::rest      => (Token.RightParen, rest)
    case ','::rest      => (Token.Comma, rest)
    case '{'::rest      => (Token.LeftBrace, rest)
    case '}'::rest      => (Token.RightBrace, rest)
    case c::rest if Rules.isWhitespace(c)          => nextToken(rest)
    case c::_    if Rules.isIdentifierFirstChar(c) => tokenizeIdentifierOrKeyword(input)
    case c::_    if Rules.isNumberFirstChar(c)     => tokenizeNumber(input)
    case c::rest => (Token.Illegal(c.toString), rest)
    
  def apply(input: String): List[Token] =
    @tailrec
    def aux(input: Input, acc: List[Token]): List[Token] = nextToken(input) match
      case (Token.EOF, _) => (Token.EOF::acc).reverse
      case (token, rest)  => aux(rest, token::acc)
    aux(input.toList, Nil)

end Lexer



    