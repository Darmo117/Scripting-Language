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

import java.util.Optional;

import net.darmo_creations.scripting.Context;
import net.darmo_creations.scripting.expressions.Expression;

/**
 * Return statement.
 *
 * @author Damien Vergnet
 */
public final class ReturnStatement extends Statement {
  /** Special variable name used to return a result. */
  public static final String RETURN_VAR_NAME = "#return";

  private final Expression expr;

  /**
   * Creates a return statement.
   * 
   * @param expr the value to return
   */
  public ReturnStatement(Expression expr) {
    this.expr = expr;
  }

  @Override
  public Optional<Statement> execute(Context context) {
    context.declareVariable(RETURN_VAR_NAME, this.expr.eval(context));
    return Optional.empty();
  }

  @Override
  public void setNext(Statement next) {
    throw new RuntimeException("Return statement cannot have a following statement.");
  }
}
