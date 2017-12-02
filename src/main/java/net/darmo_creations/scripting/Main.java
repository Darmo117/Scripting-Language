/*
 * Copyright © 2017 Damien Vergnet
 * 
 * This file is part of Scripting Language.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.darmo_creations.scripting;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Scanner;

import java_cup.runtime.Symbol;

public class Main {
  public static void main(String[] args) throws IOException, IllegalArgumentException, IllegalAccessException {
    String input = "";

    System.out.print("?> ");
    try (Scanner in = new Scanner(System.in)) {
      String s = "";
      while (!(s = in.nextLine()).equals("")) {
        input += s;
        if (!s.endsWith(";"))
          input += "\n";
        System.out.print("?> ");
      }
    }

    Lexer lexer = new Lexer(new StringReader(input));

    while (true) {
      Symbol sym = lexer.next_token();
      String name = getTokenName(sym.sym);

      if (sym.sym == Tokens.EOF)
        break;

      System.out.print(name);
      if (sym.value != null) {
        System.out.print(" = ");
        if (name.equals("STRING"))
          System.out.println("\"" + sym.value + "\"");
        else
          System.out.println(sym.value);
      }
      else
        System.out.println();
    }
  }

  private static String getTokenName(int value) throws IllegalArgumentException, IllegalAccessException {
    Field[] fields = Tokens.class.getDeclaredFields();

    for (Field f : fields) {
      int mod = f.getModifiers();
      if (Modifier.isStatic(mod) && Modifier.isFinal(mod) && f.getType() == int.class && f.getInt(null) == value) {
        return f.getName();
      }
    }
    return null;
  }

  private Main() {}
}
