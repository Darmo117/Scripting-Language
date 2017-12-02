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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import net.darmo_creations.scripting.Context;
import net.darmo_creations.scripting.expressions.Expression;
import net.darmo_creations.scripting.types.TypeUtils;

/**
 * This class can represent 'while' or 'for' loops.
 *
 * @author Damien Vergnet
 */
public class LoopStatement extends ControlStatement {
  private final Statement body;

  /**
   * Creates a while loop.
   * 
   * @param condition loop's condition
   * @param body loop's body
   */
  public LoopStatement(Expression condition, Statement body) {
    this(Collections.emptyList(), condition, Collections.emptyList(), body);
  }

  /**
   * Creates a for loop.
   * 
   * @param initStmts loop's init statements
   * @param condition loop's condition
   * @param incStmts loop's iteration end statements
   * @param body loop's body
   */
  public LoopStatement(List<Statement> initStmts, Expression condition, List<Statement> incStmts, Statement body) {
    super(condition);
    this.body = body;
  }

  @Override
  public Optional<Statement> execute(Context context) {
    boolean cond = TypeUtils.getBoolean(getCondition().eval(context));

    if (cond) {
      context.pushScope();
      return Optional.of(this.body);
    }

    return Optional.of(getNext());
  }
}
