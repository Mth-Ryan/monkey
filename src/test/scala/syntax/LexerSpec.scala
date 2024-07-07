package syntax

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class LexerSpec extends AnyFunSuite with Matchers:
  test("Test simple tokens") {
    val input = "=+(){},;"
    val output = Lexer(input)
    val expected = Seq(
      Token.Assign,
      Token.Plus,
      Token.LeftParen,
      Token.RightParen,
      Token.LeftBrace,
      Token.RightBrace,
      Token.Comma,
      Token.SemiColon,
      Token.EOF
    )

    output should equal(expected)
  }

  test("Test assignments") {
    val input =
      """
          let five = 5;
          let ten = 10;
          
          let add = fn(x, y) {
              x + y;
          };
          
          let result = add(five, ten);
        """

    val output = Lexer(input)
    val expected = Seq(
      // let five = 5;
      Token.Let,
      Token.Ident("five"),
      Token.Assign,
      Token.Integer(5),
      Token.SemiColon,
      // let ten = 10;
      Token.Let,
      Token.Ident("ten"),
      Token.Assign,
      Token.Integer(10),
      Token.SemiColon,
      // let add = fn(x, y) {
      Token.Let,
      Token.Ident("add"),
      Token.Assign,
      Token.Function,
      Token.LeftParen,
      Token.Ident("x"),
      Token.Comma,
      Token.Ident("y"),
      Token.RightParen,
      Token.LeftBrace,
      // x + y;
      Token.Ident("x"),
      Token.Plus,
      Token.Ident("y"),
      Token.SemiColon,
      Token.RightBrace,
      Token.SemiColon,
      // let result = add(five, ten);
      Token.Let,
      Token.Ident("result"),
      Token.Assign,
      Token.Ident("add"),
      Token.LeftParen,
      Token.Ident("five"),
      Token.Comma,
      Token.Ident("ten"),
      Token.RightParen,
      Token.SemiColon,

      Token.EOF
    )

    output should equal(expected)
  }

  test("Test tokens with operators") {
    val input =
      """
          !-/*5;
          5 < 10 > 5;
        """

    val output = Lexer(input)
    val expected = Seq(
      // !-/*5;
      Token.Bang,
      Token.Minus,
      Token.Slash,
      Token.Star,
      Token.Integer(5),
      Token.SemiColon,
      // 5 < 10 > 5;
      Token.Integer(5),
      Token.LessThan,
      Token.Integer(10),
      Token.GreaterThan,
      Token.Integer(5),
      Token.SemiColon,

      Token.EOF
    )

    output should equal(expected)
  }

  test("Test if and else tokens") {
    val input =
      """
          if (5 < 10) {
              return true;
          } else {
              return false;
          }
        """

    val output = Lexer(input)
    val expected = Seq(
      // if (5 < 10) {
      Token.If,
      Token.LeftParen,
      Token.Integer(5),
      Token.LessThan,
      Token.Integer(10),
      Token.RightParen,
      Token.LeftBrace,
      // return true;
      Token.Return,
      Token.True,
      Token.SemiColon,
      // } else {
      Token.RightBrace,
      Token.Else,
      Token.LeftBrace,
      // return false;
      Token.Return,
      Token.False,
      Token.SemiColon,
      // }
      Token.RightBrace,

      Token.EOF
    )

    output should equal(expected)
  }

  test("Test with compound operators tokens") {
    val input =
      """
          10 == 10;
          10 != 9;
        """

    val output = Lexer(input)
    val expected = Seq(
      // 10 == 10;
      Token.Integer(10),
      Token.Equal,
      Token.Integer(10),
      Token.SemiColon,
      // 10 != 9;
      Token.Integer(10),
      Token.NotEqual,
      Token.Integer(9),
      Token.SemiColon,

      Token.EOF
    )

    output should equal(expected)
  }

  test("Test a whole program tokens") {
    val input =
      """
          let five = 5;
          let ten = 10;
          
          let add = fn(x, y) {
              x + y;
          };
          
          let result = add(five, ten);
          
          !-/*5;
          5 < 10 > 5;
          
          if (5 < 10) {
              return true;
          } else {
              return false;
          }
          
          10 == 10;
          10 != 9;
        """

    val output = Lexer(input)
    val expected = Seq(
      // let five = 5;
      Token.Let,
      Token.Ident("five"),
      Token.Assign,
      Token.Integer(5),
      Token.SemiColon,
      // let ten = 10;
      Token.Let,
      Token.Ident("ten"),
      Token.Assign,
      Token.Integer(10),
      Token.SemiColon,
      // let add = fn(x, y) {
      Token.Let,
      Token.Ident("add"),
      Token.Assign,
      Token.Function,
      Token.LeftParen,
      Token.Ident("x"),
      Token.Comma,
      Token.Ident("y"),
      Token.RightParen,
      Token.LeftBrace,
      // x + y;
      Token.Ident("x"),
      Token.Plus,
      Token.Ident("y"),
      Token.SemiColon,
      Token.RightBrace,
      Token.SemiColon,
      // let result = add(five, ten);
      Token.Let,
      Token.Ident("result"),
      Token.Assign,
      Token.Ident("add"),
      Token.LeftParen,
      Token.Ident("five"),
      Token.Comma,
      Token.Ident("ten"),
      Token.RightParen,
      Token.SemiColon,
      // !-/*5;
      Token.Bang,
      Token.Minus,
      Token.Slash,
      Token.Star,
      Token.Integer(5),
      Token.SemiColon,
      // 5 < 10 > 5;
      Token.Integer(5),
      Token.LessThan,
      Token.Integer(10),
      Token.GreaterThan,
      Token.Integer(5),
      Token.SemiColon,
      // if (5 < 10) {
      Token.If,
      Token.LeftParen,
      Token.Integer(5),
      Token.LessThan,
      Token.Integer(10),
      Token.RightParen,
      Token.LeftBrace,
      // return true;
      Token.Return,
      Token.True,
      Token.SemiColon,
      // } else {
      Token.RightBrace,
      Token.Else,
      Token.LeftBrace,
      // return false;
      Token.Return,
      Token.False,
      Token.SemiColon,
      // }
      Token.RightBrace,
      // 10 == 10;
      Token.Integer(10),
      Token.Equal,
      Token.Integer(10),
      Token.SemiColon,
      // 10 != 9;
      Token.Integer(10),
      Token.NotEqual,
      Token.Integer(9),
      Token.SemiColon,

      Token.EOF
    )

    output should equal(expected)
  }