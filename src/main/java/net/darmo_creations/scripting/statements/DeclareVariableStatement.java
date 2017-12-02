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
package net.darmo_creations.scripting.statements;

import java.util.Objects;
import java.util.Optional;

import net.darmo_creations.scripting.Context;
import net.darmo_creations.scripting.expressions.Expression;
import net.darmo_creations.scripting.types.Value;

/**
 * This statement declares a variable.
 *
 * @author Damien Vergnet
 */
public class DeclareVariableStatement extends Statement {
  private final String name;
  private final Expression expr;

  /**
   * Creates a declaration statement.
   * 
   * @param name the variable's name
   * @param expr the optional expression to assign
   * @param next the next statement or null if there is none
   */
  public DeclareVariableStatement(String name, Expression expr) {
    this.name = Objects.requireNonNull(name);
    this.expr = expr;
  }

  @Override
  public Optional<Statement> execute(Context context) {
    Value value = null;
    if (this.expr != null)
      value = this.expr.eval(context);
    context.declareVariable(this.name, value);

    return Optional.ofNullable(getNext());
  }
}
