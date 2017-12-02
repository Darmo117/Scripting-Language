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
package net.darmo_creations.scripting.expressions;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import net.darmo_creations.scripting.Context;
import net.darmo_creations.scripting.types.FunctionObject;
import net.darmo_creations.scripting.types.Value;

public class FunctionCall implements Expression {
  private final String name;
  private final List<Expression> args;

  public FunctionCall(String name, List<Expression> args) {
    this.name = name;
    this.args = Collections.unmodifiableList(args);
  }

  /**
   * Calls a function. The context is prepared before the function is actually called.
   * 
   * @return the function's result
   */
  @Override
  public Value eval(Context context) {
    // Evaluating argument before pushing the local function scope.
    List<Value> args = this.args.stream().map(arg -> arg.eval(context)).collect(Collectors.toList());

    context.pushScope();

    FunctionObject function = context.getFunction(this.name);
    List<String> argsNames = function.getArgsNames();

    if (this.args.size() != argsNames.size())
      throw new RuntimeException(String.format("Invalid number of arguments for function %s: expected %d, given %d.", function.getName(),
          argsNames.size(), this.args.size()));

    for (int i = 0; i < argsNames.size(); i++) {
      context.declareVariable(argsNames.get(i), args.get(i));
    }

    Value value = function.run(context);
    context.popScope();

    return value;
  }
}
