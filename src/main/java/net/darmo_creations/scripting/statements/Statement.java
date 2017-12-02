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

/**
 * A statement can be executed and will return the next one.
 *
 * @author Damien Vergnet
 */
public abstract class Statement {
  private Statement next;

  protected Statement getNext() {
    return this.next;
  }

  /**
   * Sets the next statement when building the program.
   * 
   * @param next the next statement
   */
  public void setNext(Statement next) {
    this.next = Objects.requireNonNull(next);
  }

  /**
   * Executes this statement then returns the next one. If the result is empty, the end of the
   * current scope has been reached.
   * 
   * @param context the context
   * @return the next statement
   */
  public abstract Optional<Statement> execute(Context context);
}
