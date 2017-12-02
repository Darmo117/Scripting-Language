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
package net.darmo_creations.scripting.types;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import net.darmo_creations.scripting.Context;
import net.darmo_creations.scripting.statements.ReturnStatement;
import net.darmo_creations.scripting.statements.Statement;

/**
 * Type for functions.
 *
 * @author Damien Vergnet
 */
public class FunctionObject extends Value {
  private final List<String> argsNames;
  private final Statement firstStmt;

  public FunctionObject(String name, List<String> argsNames, Statement firstStmt) {
    super("Function");
    this.argsNames = Collections.unmodifiableList(argsNames);
    this.firstStmt = firstStmt;
  }

  public List<String> getArgsNames() {
    return this.argsNames;
  }

  /**
   * Executes this function.
   * 
   * @param context the context
   * @param args arguments values
   * @return the result of this function
   */
  public Value run(Context context) {
    Optional<Statement> currentStmt = this.firstStmt.execute(context);
    Statement previousStmt = null;

    while (currentStmt.isPresent()) {
      previousStmt = currentStmt.get();
      currentStmt = previousStmt.execute(context);
      // If we reached a RETURN, return the value
      if (!currentStmt.isPresent() && context.isReturnVariableSet())
        return context.getVariableValue(ReturnStatement.RETURN_VAR_NAME);
    }

    // No RETURN has been encountered, return nothing
    return NoneObject.NONE;
  }

  @Override
  protected String str() {
    return "<function @ " + Integer.toHexString(hashCode()) + ">";
  }
}
