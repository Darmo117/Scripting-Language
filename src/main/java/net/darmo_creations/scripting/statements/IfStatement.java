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
import net.darmo_creations.scripting.types.TypeUtils;

/**
 * @author Damien Vergnet
 */
public class IfStatement extends ControlStatement {
  private final Statement ifBlock, elseBlock;

  /**
   * Creates an If statement.
   * 
   * @param condition the condition
   * @param ifBlock the block to execute if the condition is true
   */
  public IfStatement(Expression condition, Statement ifBlock) {
    this(condition, ifBlock, null);
  }

  /**
   * Creates an If statement.
   * 
   * @param condition the condition
   * @param ifBlock the block to execute if the condition is true
   * @param elseBlock the block to execute if the condition is false
   */
  public IfStatement(Expression condition, Statement ifBlock, Statement elseBlock) {
    super(condition);
    this.ifBlock = Objects.requireNonNull(ifBlock);
    this.elseBlock = elseBlock;
  }

  @Override
  public Optional<Statement> execute(Context context) {
    boolean cond = TypeUtils.getBoolean(getCondition().eval(context));

    if (cond) {
      context.pushScope();
      return Optional.of(this.ifBlock);
    }
    else if (this.elseBlock != null) {
      context.pushScope();
      return Optional.of(this.elseBlock);
    }

    return Optional.of(getNext());
  }
}
