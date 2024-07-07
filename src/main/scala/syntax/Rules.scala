package syntax

import scala.util.{Try, Success, Failure}

object Rules:
  def isWhitespace(c: Char): Boolean = c.isWhitespace
  
  def isIdentifierFirstChar(c: Char): Boolean = c.isLetter || c == '_'
  
  def isIdentifierChar(c: Char): Boolean = isIdentifierFirstChar(c) || c.isDigit
  
  def isNumberFirstChar(c: Char): Boolean = c.isDigit
  
  def isNumberChar: Char => Boolean = isNumberFirstChar
  
  def lookupKeyword(literal: String): Token = literal match
    case "let"    => Token.Let
    case "fn"     => Token.Function
    case "return" => Token.Return
    case "if"     => Token.If
    case "else"   => Token.Else
    case "true"   => Token.True
    case "false"  => Token.False
    case other    => Token.Ident(other)
    
  def lookupNumber(literal: String): Token = Try(literal.toInt) match
    case Success(n) => Token.Integer(n)
    case Failure(_) => Token.Illegal(literal)
    