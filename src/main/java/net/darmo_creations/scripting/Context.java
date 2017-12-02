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

import java.util.HashMap;
import java.util.Map;

import net.darmo_creations.scripting.exceptions.AlreadyDefinedException;
import net.darmo_creations.scripting.exceptions.EmptyStackException;
import net.darmo_creations.scripting.exceptions.StackOverflowException;
import net.darmo_creations.scripting.exceptions.UndefinedSymbolException;
import net.darmo_creations.scripting.statements.ReturnStatement;
import net.darmo_creations.scripting.types.FunctionObject;
import net.darmo_creations.scripting.types.Value;

/**
 * The context stores all defined variables and functions.
 *
 * @author Damien Vergnet
 */
public class Context {
  /** Script's context */
  public static final Context CONTEXT = new Context();

  private Scope stack;

  /**
   * Creates an empty context.
   */
  private Context() {
    this.stack = new Scope(null);
  }

  /**
   * Pushes a new scope.
   */
  public void pushScope() {
    this.stack = new Scope(this.stack);
  }

  /**
   * Pops the current scope and restores the parent one.
   */
  public void popScope() {
    this.stack = this.stack.getParentScope();
  }

  /**
   * Declares a variable in the current scope.
   * 
   * @param name the name
   * @throws AlreadyDefinedException if a variable with the same name has already been defined in
   *           the current scope
   */
  public void declareVariable(String name, Value value) {
    this.stack.declareVariable(name, value);
  }

  /**
   * Returns the value of a variable.
   * 
   * @param name variable's name
   * @return the value
   * @throws UndefinedSymbolException if no variable with this name is defined
   */
  public Value getVariableValue(String name) {
    return this.stack.getVariableValue(name);
  }

  /**
   * Sets the value of a variable.
   * 
   * @param name variable's name
   * @param value the new value
   * @throws UndefinedSymbolException if no variable with this name is defined
   */
  public void setVariableValue(String name, Value value) {
    this.stack.setVariableValue(name, value);
  }

  /**
   * Tells if the special return variable is set.
   * 
   * @see ReturnStatement#RETURN_VAR_NAME
   */
  public boolean isReturnVariableSet() {
    return this.stack.returnVariableExists();
  }

  /**
   * Declares a function in the current scope.
   * 
   * @param name function's name
   * @param function the function
   * @throws AlreadyDefinedException if a function with the same name has already been defined in
   *           the current scope
   */
  public void declareFunction(String name, FunctionObject function) {
    this.stack.declareFunction(name, function);
  }

  /**
   * Returns the function with the given name.
   * 
   * @param name function's name
   * @return the function
   * @throws UndefinedSymbolException if no function with this name is defined
   */
  public FunctionObject getFunction(String name) {
    return this.stack.getFunction(name);
  }

  private class Scope {
    public static final int MAX_DEPTH = 1000;

    private Scope parentScope;
    private Map<String, Value> variables;
    private Map<String, FunctionObject> functions;

    public Scope(Scope parentScope) {
      if (parentScope != null && parentScope.size() == MAX_DEPTH)
        throw new StackOverflowException("Max call stack size exceeded!");
      this.parentScope = parentScope;
      this.variables = new HashMap<>();
      this.functions = new HashMap<>();
    }

    public int size() {
      if (this.parentScope == null)
        return 1;
      return 1 + this.parentScope.size();
    }

    public Scope getParentScope() {
      if (this.parentScope != null)
        return this.parentScope;
      throw new EmptyStackException("No context to return to.");
    }

    public boolean returnVariableExists() {
      return this.variables.containsKey(ReturnStatement.RETURN_VAR_NAME);
    }

    public void declareVariable(String name, Value value) {
      if (this.variables.containsKey(name))
        throw new AlreadyDefinedException("Variable " + name + " is already defined in this scope.");
      this.variables.put(name, value);
    }

    public Value getVariableValue(String name) {
      if (!this.variables.containsKey(name) && this.parentScope != null)
        return this.parentScope.getVariableValue(name);
      requireVariableExists(name);
      return this.variables.get(name);
    }

    public void setVariableValue(String name, Value value) {
      if (!this.variables.containsKey(name) && this.parentScope != null)
        this.parentScope.setVariableValue(name, value);
      requireVariableExists(name);
      this.variables.put(name, value);
    }

    public void declareFunction(String name, FunctionObject function) {
      if (this.functions.containsKey(name))
        throw new AlreadyDefinedException("Function " + name + " is already defined in this scope.");
      this.functions.put(name, function);
    }

    public FunctionObject getFunction(String name) {
      if (!this.functions.containsKey(name)) {
        if (this.parentScope != null)
          return this.parentScope.getFunction(name);
        throw new UndefinedSymbolException("Function " + name + " is not defined.");
      }
      return this.functions.get(name);
    }

    private void requireVariableExists(String name) {
      if (!this.variables.containsKey(name))
        throw new UndefinedSymbolException("Variable " + name + " is not defined.");
    }
  }
}
