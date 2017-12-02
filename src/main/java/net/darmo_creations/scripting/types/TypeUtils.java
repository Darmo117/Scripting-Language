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

import net.darmo_creations.scripting.exceptions.ValueException;

/**
 * This class provides usefull methods to handle values and types.
 *
 * @author Damien Vergnet
 */
public class TypeUtils {
  /**
   * Returns the boolean representation of a value.<br />
   * The rules are the following:
   * <ul>
   * <li>NoneType -> always false</li>
   * <li>Number -> false if 0, true otherwise</li>
   * <li>String -> false if empty, true otherwise</li>
   * <li>Function -> always true</li>
   * </ul>
   * 
   * @param value the value
   * @return the boolean
   */
  public static boolean getBoolean(Value value) {
    if (isNone(value))
      return false;
    if (isNumber(value))
      return castToNumber(value).getValue() != 0;
    if (isString(value))
      return !castToString(value).isEmpty();
    if (isFunction(value))
      return true;
    throw new ValueException("Unknown type " + value);
  }

  /**
   * Tries to cast a value as a Number.
   * 
   * @param value the value to cast
   * @return the cast value
   * @throws ValueException if the value could not be cast
   */
  public static NumberObject castToNumber(Value value) {
    if (isNumber(value))
      return (NumberObject) value;
    if (isNone(value))
      return NumberObject.ZERO;
    throw new ValueException(String.format("%s cannot be cast to a Number", value));
  }

  /**
   * Cast a value as a String.
   * 
   * @param value the value to cast
   * @return the cast value
   */
  public static StringObject castToString(Value value) {
    return (StringObject) value;
  }

  public static boolean isNone(Value value) {
    return value instanceof NoneObject;
  }

  public static boolean isNumber(Value value) {
    return value instanceof NumberObject;
  }

  public static boolean isString(Value value) {
    return value instanceof StringObject;
  }

  public static boolean isFunction(Value value) {
    return value instanceof FunctionObject;
  }

  private TypeUtils() {}
}
